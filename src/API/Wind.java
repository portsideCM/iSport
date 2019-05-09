package API;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wind")
public class Wind {
    @XmlElement(name = "direction")
    public WindDirection WindDir;
    @XmlElement(name = "speed")
    public WindSpeed WindVel;
}