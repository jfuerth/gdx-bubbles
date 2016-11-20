package ca.fuerth.synther;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Synther {

    private static final AudioFormat AUDIO_FORMAT = new AudioFormat(44100, 16, 1, true, true);

    public static void main(String[] args) throws IOException {
        System.out.println("Audio file types:");
        AudioFileFormat.Type[] audioFileTypes = AudioSystem.getAudioFileTypes();
        for (int i = 0; i < audioFileTypes.length; i++) {
            System.out.println(" " + audioFileTypes[i]);
        }
        new Synther().writeSample();
    }

    public void writeSample() throws IOException {
        AudioInputStream generatedSample = getAudio(
                new FallingSineSynth(1000, 44100, .1),
                new LinearFadeOutEnvelope(44100, .1));
        AudioSystem.write(generatedSample, AudioFileFormat.Type.WAVE, new File("sound.wav"));
    }

    public AudioInputStream getAudio(final Synth synth, final Envelope envelope) {
        InputStream audioData = new InputStream() {

            /**
             * The current sample data. Only the lower 16 bits are significant.
             */
            int currentSample;

            /**
             * Flag to indicate if the current byte being read from the input stream
             * is the high byte or the low byte of a single 16-bit sample.
             */
            boolean currentByteHigh = true;

            @Override
            public int available() throws IOException {
                return Integer.MAX_VALUE;
            }

            @Override
            public int read() throws IOException {
                if (!synth.hasNext()) {
                    return -1;
                } else if (currentByteHigh) {
                    double v = envelope.transform(synth.nextDouble());
                    currentSample = (int) (v * 32767.0);
//                    System.out.println("Next sample " + currentSample);
                    currentByteHigh = false;
                    return (currentSample >> 8) & 0xff;
                } else {
                    currentByteHigh = true;
                    return currentSample & 0xff;
                }

            }

        };
        return new AudioInputStream(audioData, AUDIO_FORMAT, AudioSystem.NOT_SPECIFIED);
    }

}
