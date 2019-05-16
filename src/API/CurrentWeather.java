package src.API;

import java.time.Instant;
import java.util.Date;

public class CurrentWeather {
    public Instant CalcTime;
    public Instant Sunrise;
    public Instant Sunset;
    public double Temp;
    public String TempUnit;
    public double Pressure;
    public double Humidity;
    public double WindSpeed;
    public double Rain1h;
    public double Rain3h;
    public int WindDir;
    public int CloudCover;
    public int Visibility;
    public int WeatherId;
    public String WeatherType;
    public String Description;

    public double getTempInCelsius(double tmp) {
        if(TempUnit.equals("celsius")) {
            return tmp;
        }
        else if(TempUnit.equals("fahrenheit")) {
            return (tmp - 32) / 1.8;
        }
        else {
            // Kelvin stuff
            return (tmp - 273.15);
        }
    }

    public double getTempInFarenheit(double tmp) {
        if(TempUnit.equals("celsius")) {
            return (tmp * 1.8) + 32;
        }
        else if(TempUnit.equals("fahrenheit")) {
            return tmp;
        }
        else {
            //More Kelvin
            return (tmp - 273.15) * 1.8 + 32;
        }
    }
}