package ca.fuerth.synther;

public class RisingSineSynth implements Synth {

    private final double clockStep;
    private double clock;
    private double freqHz;
    private double lengthInSeconds;

    public RisingSineSynth(double freqHz, double samplesPerSecond, double lengthInSeconds) {
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
        return Math.sin(clock*clock * Math.PI * freqHz);
    }
}
