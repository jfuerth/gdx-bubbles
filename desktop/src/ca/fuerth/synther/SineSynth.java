package ca.fuerth.synther;

public class SineSynth implements Synth {

    private final double clockStep;
    private double clock;
    private double freqHz;
    private double lengthInSeconds;

    public SineSynth(double freqHz, double samplesPerSecond, double lengthInSeconds) {
        this.freqHz = freqHz;
        this.lengthInSeconds = lengthInSeconds;

        clockStep = 1.0 / samplesPerSecond;
    }

    @Override
    public boolean hasNext() {
        return clock < lengthInSeconds;
    }

    @Override
    public double nextDouble() {
        clock += clockStep;
        return Math.sin(clock * Math.PI * freqHz);
    }
}
