package src.API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "precipitation")
public class Rain {
    @XmlAttribute(name = "value")
    public int RainAmount;
    @XmlAttribute(name = "mode")
    public String RainType;
}
