package src.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ImpactSport { //package visible

    //a list of the most important parameters for each supported sport
    private static Map<Sport, List<Param>> factors = null;

    //important factors for each sport, as deduced from data gathering
    private static void loadFactors() {
        factors.put(Sport.RUNNING, new ArrayList<>(List.of(Param.RAIN, Param.WIND, Param.TEMPERATURE, Param.HUMIDITY)));
        factors.put(Sport.ROWING, new ArrayList<>(List.of(Param.WIND, Param.CLOUD, Param.VISIBILITY)));
        factors.put(Sport.VOLLEYBALL, new ArrayList<>(List.of(Param.RAIN, Param.WIND, Param.TEMPERATURE, Param.CLOUD)));
        factors.put(Sport.RUGBY, new ArrayList<>(List.of(Param.VISIBILITY, Param.WIND, Param.RAIN)));
        factors.put(Sport.FOOTBALL, new ArrayList<>(List.of(Param.VISIBILITY, Param.WIND, Param.HUMIDITY)));
        factors.put(Sport.SAILING, new ArrayList<>(List.of(Param.WIND, Param.VISIBILITY, Param.RAIN, Param.SUN)));
        factors.put(Sport.CYCLING, new ArrayList<>(List.of(Param.WIND, Param.TEMPERATURE, Param.HUMIDITY)));
        factors.put(Sport.HIKING, new ArrayList<>(List.of(Param.SUN, Param.CLOUD, Param.PRESSURE, Param.RAIN)));
        factors.put(Sport.CRICKET, new ArrayList<>(List.of(Param.WIND, Param.VISIBILITY)));
        factors.put(Sport.TENNIS, new ArrayList<>(List.of(Param.WIND, Param.SUN, Param.HUMIDITY)));
    }

    static List<Param> getImpactParams(Sport s) { //package visible

        //perform lazy instantiation, such that we only load factors once
        if (factors == null) {
            ImpactSport.loadFactors();
        }

        return factors.get(s);
    }
}
