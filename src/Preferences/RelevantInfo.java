package src.Preferences;

import javafx.scene.layout.Priority;

import java.util.*;

public class RelevantInfo {

    public List<Param> getTop3 (List<Sport> interestSports) {
        Map<Param, Integer> paramScores = new HashMap<>();

        for (Sport s : interestSports) {
            List<Param> influenceParam = ImpactSport.getImpactParams(s);
            for (Param p : influenceParam) {
                if (paramScores.containsKey(p)) {
                    paramScores.put(p, paramScores.get(p) + 1);
                }
                else {
                    paramScores.put(p, 1);
                }
            }
        }

        //getting the three parameters with maximal score
        //through one pass through the array
        //if there is equality for the third place, pick any
        Param max1 = Param.VOID, max2 = Param.VOID, max3 = Param.VOID;

        //put 0 in the map for VOID to have an initial comparison score value
        paramScores.put(Param.VOID, 0);

        for (Param p : paramScores.keySet()) {
            if (paramScores.get(p) >= paramScores.get(max1)) {
                max3 = max2;
                max2 = max1;
                max1 = p;
            }
            else {
                if (paramScores.get(p) >= paramScores.get(max2)) {
                    max3 = max2;
                    max2 = p;
                }
                else {
                    if (paramScores.get(p) >= paramScores.get(max3)) {
                        max3 = p;
                    }
                }
            }
        }

        return new ArrayList<>(List.of(max1, max2, max3));
    }
}
