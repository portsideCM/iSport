package src.iSport.Frames;

public class LockOut {
    // Provides a global lock, stops multiple transitions occurring at once
    private static final int DURATION = 800;

    private static long lockedAt = Long.MAX_VALUE;

    public static void lock() {
        lockedAt = System.currentTimeMillis();
    }

    public static boolean isLocked() {
        long activeFor = System.currentTimeMillis() - lockedAt;

        return activeFor >= 0 && activeFor <= DURATION;
    }

    // Provides also a lock for the HomePage stopping it from frequently reloading
    public static boolean refreshNeeded = true;
}
