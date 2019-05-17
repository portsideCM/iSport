package src.Images;

import javafx.scene.image.Image;

public class Icons {

    public Image CLOUD_IC;
    public Image HUMID_IC;
    public Image PRESSURE_IC;
    public Image RAIN_IC;
    public Image RAINDROPS_IC;
    public Image SUN_IC;
    public Image SUN_CLOUD_IC;
    public Image TEMP_IC;
    public Image VISIBILITY_IC;
    public Image WIND_IC;

    //Display backgrounds
    public Icons() {
        CLOUD_IC = new Image(".idea/Icons/CLOUD.png");
        HUMID_IC = new Image(".idea/Icons/HUMID.png");
        PRESSURE_IC = new Image(".idea/Icons/PRESSURE.png");
        RAIN_IC = new Image(".idea/Icons/RAIN.png");
        RAINDROPS_IC = new Image(".idea/Icons/RAINDROPS.png");
        SUN_IC = new Image(".idea/Icons/SUN.png");
        SUN_CLOUD_IC = new Image(".idea/Icons/SUN_CLOUD.png");
        TEMP_IC = new Image(".idea/Icons/TEMP.png");
        VISIBILITY_IC = new Image(".idea/Icons/VISIBILITY.png");
        WIND_IC = new Image(".idea/Icons/WIND.png");
    }

    public Image iconCalc(int ID) {
        //Rain
        if (ID > 100 && ID < 650) {
            return RAIN_IC;
        }
        //Sun
        if (ID == 800) {
            return SUN_IC;
        }
        //Partly Cloudy
        if (ID == 801 || ID == 802) {
            return SUN_CLOUD_IC;
        }

        //All else return cloudy
        return CLOUD_IC;
    }
}
