package API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "direction")
public class WindDirection {
    @XmlAttribute(name = "value")
    public int WindDirection;
    @XmlAttribute(name = "name")
    public String WindDirectionName;
}
