package src.iSport.Frames;


//Provides a global lock, stops multiple transitions occurring at once
public class LockOut {
    private static final int DURATION = 800;

    private static long lockedAt = Long.MAX_VALUE;

    public static void lock() {
        lockedAt = System.currentTimeMillis();
    }

    public static boolean isLocked() {
        long activeFor = System.currentTimeMillis() - lockedAt;

        return activeFor >= 0 && activeFor <= DURATION;
    }
}
