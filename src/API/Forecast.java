package src.API;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class Forecast {
    public ArrayList<ForecastData> ForecastList;

    public ForecastData nextDayWeather(LocalDate today, int daysForward, ZoneId timeZone) {
        today = today.plusDays(daysForward);
        for(int i = 0; i < ForecastList.size(); ++i) {
            //Check if the time is greater than or equal to the date passed in
            if(ForecastList.get(i).Time.atZone(timeZone).toLocalDate().compareTo(today) >= 0) {
                return ForecastList.get(i);
            }
        }
        //Otherwise just return the very last one we have
        return ForecastList.get(ForecastList.size() - 1);
    }
}
