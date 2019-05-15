package src.API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "temperature")
public class Temp {
    @XmlAttribute(name = "value")
    public double TempVal;
    @XmlAttribute(name = "max")
    public double TempMax;
    @XmlAttribute(name = "min")
    public double TempMin;
    @XmlAttribute(name = "unit")
    public String TempUnit;

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
