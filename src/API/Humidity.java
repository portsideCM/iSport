package API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "humidity")
public class Humidity {
    @XmlAttribute(name = "value")
    public float Humidity;
}

