package src.API;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "weatherdata")
public class Forecast {
    @XmlElement(name = "forecast")
    public ArrayList<ForecastData> ForecastList;
}
