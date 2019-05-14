package src.API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "speed")
public class WindSpeed {
    @XmlAttribute(name = "value")
    public float WindSpeed;
    @XmlAttribute(name = "name")
    public String WindType;
}
