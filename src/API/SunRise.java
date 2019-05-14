package src.API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "sun")
public class SunRise {
    @XmlAttribute(name = "rise")
    public Date UTCSunRise;
    @XmlAttribute(name = "set")
    public Date UTCSunSet;
}
