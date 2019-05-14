package src.API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "city")
public class City {
    @XmlAttribute(name = "name")
    public String CityName;
    @XmlAttribute(name = "id")
    public int CityId;
    @XmlElement(name = "sun")
    public SunRise SunTiming;
}
