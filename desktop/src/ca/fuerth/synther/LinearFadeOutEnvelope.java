package ca.fuerth.synther;

public class LinearFadeOutEnvelope implements Envelope {

    private final double fadePerSample;
    private double multiplier;

    public LinearFadeOutEnvelope(double samplesPerSecond, double lengthInSeconds) {
        fadePerSample = 1.0 / (samplesPerSecond * lengthInSeconds);
        multiplier = 1.0;
    }

    @Override
    public double transform(double sample) {
        multiplier = Math.max(0.0, multiplier - fadePerSample);
        System.out.println("Mult by " + multiplier);
        return sample * multiplier;
    }
}
