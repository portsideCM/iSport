package src.API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clouds")
public class Cloud {
    @XmlAttribute(name = "value")
    public int Clouds;
    @XmlAttribute(name = "name")
    public String CloudName;
}