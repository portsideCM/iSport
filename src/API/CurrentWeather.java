package API;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "current")
public class CurrentWeather {
    @XmlElement(name = "city")
    public City CityInfo;
    @XmlElement(name = "clouds")
    public Cloud CloudInfo;
    @XmlElement(name = "humidity")
    public Humidity HumidityInfo;
    @XmlElement(name = "pressure")
    public Pressure PressureInfo;
    @XmlElement(name = "precipitation")
    public Rain RainInfo;
    @XmlElement(name = "temperature")
    public Temp TempInfo;
    @XmlElement(name = "weather")
    public Weather WeatherInfo;
    @XmlElement(name = "wind")
    public Wind WindInfo;
}
