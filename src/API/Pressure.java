package API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pressure")
public class Pressure {
    @XmlAttribute(name = "value")
    public float Pressure;
}