package API;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "temperature")
public class Temp {
    @XmlAttribute(name = "value")
    public double TempVal;
    @XmlAttribute(name = "max")
    public double TempMax;
    @XmlAttribute(name = "min")
    public double TempMin;
    @XmlAttribute(name = "unit")
    public String TempUnit;
}
