package ca.fuerth.gdx.util;

public class Preconditions {
    private Preconditions() {}

    public static <T> T nonNull(T value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return value;
    }
}
