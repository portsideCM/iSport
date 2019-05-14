package src.API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "time")
public class ForecastData {
    @XmlAttribute(name = "from")
    public Date From;

    @XmlAttribute(name = "to")
    public Date To;

    @XmlElement(name = "precipitation")
    public Rain Rain;

    @XmlElement(name = "windDirection")
    public WindDirection WindDir;

    @XmlElement(name = "windSpeed")
    public WindSpeed WindVel;

    @XmlElement(name = "temperature")
    public Temp Temperature;

    @XmlElement(name = "pressure")
    public Pressure Pressure;

    @XmlElement(name = "humidity")
    public Humidity Humidity;

    @XmlElement(name = "clouds")
    public Cloud Clouds;
}