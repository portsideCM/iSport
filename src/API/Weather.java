package API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "weather")
public class Weather {
    @XmlAttribute(name = "number")
    public int WeatherId;
    @XmlAttribute(name = "value")
    public String WeatherName;
    @XmlAttribute(name = "icon")
    public String WeatherIconId;
}

