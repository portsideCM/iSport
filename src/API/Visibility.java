package API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "visibility")
public class Visibility {
    @XmlAttribute(name = "value")
    public int Visibility;
}
