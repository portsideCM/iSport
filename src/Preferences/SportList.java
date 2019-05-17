package src.Preferences;

import java.util.ArrayList;
import java.util.List;

public class SportList {
    static private List<Sport> sportList = new ArrayList<>();

    public static List<Sport> get() {
        if (sportList == null) {
            sportList = new ArrayList<>();
        }

        return sportList;
    }

    public static void add(Sport sport) {
        if (sportList == null) {
            sportList = new ArrayList<>();
        }

        sportList.add(sport);
    }

    public static void remove(Sport sport) {
        if (sportList != null)
            sportList.remove(sport);
    }

    public static void clear() {
        if (sportList != null)
            sportList.clear();
    }
}
