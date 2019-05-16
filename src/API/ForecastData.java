package src.API;

import java.time.Instant;

public class ForecastData {
    public Instant Time;
    public double Temp;
    public double TempMin;
    public double TempMax;
    public String TempUnit;
    public double Pressure;
    public double Humidity;
    public String WeatherDescription;
    public String WeatherType;
    public double Clouds;
    public double Rain3h;
    public double WindSpeed;
    public double WindDir;
    public int WeatherId;

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
