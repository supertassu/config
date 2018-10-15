package me.tassu.cfg;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple utilities.
 */
public class ConfigUtil {

    private ConfigUtil() {
        throw new IllegalStateException("no");
    }

    /**
     * Returns the given list without the first element
     */
    public static <T> List<T> withoutFirst(List<T> in) {
        if (in.size() < 1) throw new IllegalArgumentException("Given list should have at least one entry.");
        ArrayList<T> copy = new ArrayList<>(in);
        copy.remove(0);
        return copy;
    }

    /**
     * Utility to convert any exceptions to a {@link RuntimeException}.
     * @param in Runnable to run.
     */
    public static void run(ErrorRunnable in) {
        try {
            in.run();
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * A {@link Runnable} where {@link Runnable#run()} can throw anything.
     */
    @FunctionalInterface
    public interface ErrorRunnable {
        void run() throws Throwable;
    }

}
