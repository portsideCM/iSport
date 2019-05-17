package src.iSport.Frames.HomePage;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import src.API.APIConnectionSingleton;
import src.API.CurrentWeather;
import src.API.Forecast;
import src.API.ForecastData;
import src.Images.Backgrounds;
import src.Images.Icons;
import src.Preferences.Param;
import src.Preferences.RelevantInfo;
import src.Preferences.Sport;
import src.Preferences.SportList;
import src.iSport.Frames.LockOut;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static src.iSport.Frames.LockOut.refreshNeeded;
import static src.iSport.Main.refreshListenerAdded;

public class HomePageController {

    @FXML
    private AnchorPane anchorHomePage;
    @FXML
    private ImageView backgroundHome;
    @FXML
    private ImageView dayIcon1;
    @FXML
    private ImageView dayIcon2;
    @FXML
    private ImageView dayIcon3;
    @FXML
    private ImageView dayIcon4;
    @FXML
    private Button burgerButton;
    @FXML
    private StackPane masterContainer;
    @FXML
    private Label celsiusLabel;
    @FXML
    private Label fahrenheitLabel;
    @FXML
    private Pane mainTempPane;
    @FXML
    private Label mainTempLabel;
    @FXML
    private ImageView mainTempIcon;
    @FXML
    private Label locationLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private ImageView info1Icon;
    @FXML
    private ImageView info2Icon;
    @FXML
    private ImageView info3Icon;
    @FXML
    private Label info1Label;
    @FXML
    private Label info2Label;
    @FXML
    private Label info3Label;
    @FXML
    private Label day1Label;
    @FXML
    private Label day2Label;
    @FXML
    private Label day3Label;
    @FXML
    private Label day4Label;
    @FXML
    private Label day1TempLabel;
    @FXML
    private Label day2TempLabel;
    @FXML
    private Label day3TempLabel;
    @FXML
    private Label day4TempLabel;

    // Loads up all background images
    private Backgrounds bg = new Backgrounds();
    // Load up all weather icons
    private Icons ic = new Icons();

    private static final String cityName = "Cambridge,uk";

    // C for celsius, F for fahrenheit
    private static String tempUnit = "C";

    // Called when page is loaded
    @FXML
    public void initialize() {
        if (!refreshNeeded) {
            return;
        }

        // Creates a map from params to icons
        Map<Param, Image> paramIconMap = new HashMap<>(Map.of(
                Param.CLOUD, ic.CLOUD_IC,
                Param.HUMIDITY, ic.HUMID_IC,
                Param.PRESSURE, ic.PRESSURE_IC,
                Param.RAIN, ic.RAINDROPS_IC,
                Param.SUN, ic.SUN_IC,
                Param.TEMPERATURE, ic.TEMP_IC,
                Param.VISIBILITY, ic.VISIBILITY_IC,
                Param.WIND, ic.WIND_IC
        ));

        // Loads weather info and displays it
        try {
            //updates Display
            Image backgroundImage = bg.getBestBackground();
            backgroundHome.setImage(backgroundImage);

            //Sets location and date
            locationLabel.setText("Cambridge");
            timeLabel.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));

            APIConnectionSingleton conn = APIConnectionSingleton.getAPIConnection();
            CurrentWeather currentWeather = conn.getCurrentWeather(cityName, true);
            Forecast forecast = conn.getForecast(cityName, true);

            // Sets main weather info
            if (tempUnit.equals("C")) {
                mainTempLabel.setText(Math.round(currentWeather.getTempInCelsius(currentWeather.Temp)) + "°");
            } else {
                mainTempLabel.setText(Math.round(currentWeather.getTempInFarenheit(currentWeather.Temp)) + "°");
            }
            mainTempIcon.setImage(ic.iconCalc(currentWeather.WeatherId));

            // Sets key weather info
            List<Sport> options = SportList.get();
            if (options.isEmpty()) {
                info1Icon.setImage(ic.RAIN_IC);
                info1Label.setText(getWeatherInfo(currentWeather, forecast, Param.RAIN));
                info2Icon.setImage(ic.WIND_IC);
                info2Label.setText(getWeatherInfo(currentWeather, forecast, Param.WIND));
                info3Icon.setImage(ic.HUMID_IC);
                info3Label.setText(getWeatherInfo(currentWeather, forecast, Param.HUMIDITY));
            } else {
                List<Param> top3Factors = RelevantInfo.computeTop3(options);
                info1Icon.setImage(paramIconMap.get(top3Factors.get(0)));
                info1Label.setText(getWeatherInfo(currentWeather, forecast, top3Factors.get(0)));
                info2Icon.setImage(paramIconMap.get(top3Factors.get(1)));
                info2Label.setText(getWeatherInfo(currentWeather, forecast, top3Factors.get(1)));
                info3Icon.setImage(paramIconMap.get(top3Factors.get(2)));
                info3Label.setText(getWeatherInfo(currentWeather, forecast, top3Factors.get(2)));
            }

            // Sets day 1 weather info
            ForecastData day1 = forecast.nextDayWeather(LocalDate.now(), 1, ZoneId.of("GMT"));
            dayIcon1.setImage(ic.iconCalc(day1.WeatherId));
            String tempDay1 = String.valueOf(Math.round(convert(day1.Temp, day1)));
            day1TempLabel.setText(tempDay1 + "°");
            day1Label.setText(dayOfWeekAsString(LocalDate.now().plusDays(1).getDayOfWeek()));

            // Set day 2 weather info
            ForecastData day2 = forecast.nextDayWeather(LocalDate.now(), 2, ZoneId.of("GMT"));
            dayIcon2.setImage(ic.iconCalc(day2.WeatherId));
            String tempDay2 = String.valueOf(Math.round(convert(day2.Temp, day2)));
            day2TempLabel.setText(tempDay2 + "°");
            day2Label.setText(dayOfWeekAsString(LocalDate.now().plusDays(2).getDayOfWeek()));

            // Set day 3 weather info
            ForecastData day3 = forecast.nextDayWeather(LocalDate.now(), 3, ZoneId.of("GMT"));
            dayIcon3.setImage(ic.iconCalc(day3.WeatherId));
            String tempDay3 = String.valueOf(Math.round(convert(day3.Temp, day3)));
            day3TempLabel.setText(tempDay3 + "°");
            day3Label.setText(dayOfWeekAsString(LocalDate.now().plusDays(3).getDayOfWeek()));

            // Set day 4 weather info
            ForecastData day4 = forecast.nextDayWeather(LocalDate.now(), 4, ZoneId.of("GMT"));
            dayIcon4.setImage(ic.iconCalc(day4.WeatherId));
            String tempDay4 = String.valueOf(Math.round(convert(day4.Temp, day4)));
            day4TempLabel.setText(tempDay4 + "°");
            day4Label.setText(dayOfWeekAsString(LocalDate.now().plusDays(4).getDayOfWeek()));
        } catch (IOException e) {
            // Have some nice error message b/c the API failed here
            System.out.println("ERROR ON LOADING WEATHER");
            e.printStackTrace();
        }

        if (!refreshListenerAdded) {
            refreshListenerAdded = true;
            anchorHomePage.hoverProperty().addListener(observable -> this.initialize());
        }

        refreshNeeded = false;
    }

    @FXML
    private void loadSportPage(MouseEvent event) throws IOException {
        // Stops other transitions occurring at the same time
        if (LockOut.isLocked()) {
            return;
        }
        LockOut.lock();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/iSport/Frames/SportPage/SportPage.fxml"));
        Parent anchorSport = loader.load();
        Scene scene = burgerButton.getScene();

        anchorSport.translateXProperty().set(-scene.getWidth());
        masterContainer.getChildren().add(anchorSport);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(anchorSport.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    private void loadWeatherInfoPage(MouseEvent event) throws IOException {
        // Stops other transitions occurring at the same time
        if (LockOut.isLocked()) {
            return;
        }
        LockOut.lock();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/iSport/Frames/WeatherInfoPage/WeatherInfoPage.fxml"));
        Parent anchorWeatherInfo = loader.load();
        Scene scene = mainTempPane.getScene();

        anchorWeatherInfo.translateYProperty().set(scene.getHeight());
        masterContainer.getChildren().add(anchorWeatherInfo);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(anchorWeatherInfo.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);

        timeline.play();
    }

    @FXML
    private void switchTempUnit(MouseEvent event) throws MalformedURLException {
        if (celsiusLabel.getTextFill() == Color.WHITESMOKE) { // Convert Celsius to Fahrenheit
            tempUnit = "F";
            celsiusLabel.setTextFill(Color.web("#b2b2b2"));
            celsiusLabel.setFont(new Font("Consolas Bold", 18));
            fahrenheitLabel.setTextFill(Color.WHITESMOKE);
            fahrenheitLabel.setFont(new Font("Consolas Bold", 23));
        } else { // Convert Fahrenheit to Celsius
            tempUnit = "C";
            fahrenheitLabel.setTextFill(Color.web("#b2b2b2"));
            fahrenheitLabel.setFont(new Font("Consolas Bold", 18));
            celsiusLabel.setTextFill(Color.WHITESMOKE);
            celsiusLabel.setFont(new Font("Consolas Bold", 23));
        }

        //updates Display
        refreshNeeded = true;
        this.initialize();
    }

    // Decides what unit to display the temp in
    public static double convert(double T, ForecastData FD) {
        if (tempUnit.equals("C")) {
            return FD.getTempInCelsius(T);
        } else {
            return FD.getTempInFarenheit(T);
        }
    }

    // Query the correct info for the weather factor
    private String getWeatherInfo(CurrentWeather currentWeather, Forecast forecast, Param param) {
        if (param.equals(Param.CLOUD))
            return currentWeather.CloudCover + "%";
        else if (param.equals(Param.HUMIDITY))
            return Math.round(currentWeather.Humidity) + "%";
        else if (param.equals(Param.PRESSURE))
            return currentWeather.Pressure + " hPa";
        else if (param.equals(Param.RAIN))
            return currentWeather.Rain1h + " mm";
        else if (param.equals(Param.SUN)) {
            LocalTime localSunRise = LocalTime.from(currentWeather.Sunrise.atZone(ZoneId.systemDefault()));
            String sunrise = localSunRise.format(DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime localSunSet = LocalTime.from(currentWeather.Sunset.atZone(ZoneId.systemDefault()));
            String sunset = localSunSet.format(DateTimeFormatter.ofPattern("HH:mm"));
            return sunrise + "\n" + sunset;
        } else if (param.equals(Param.TEMPERATURE)) {
            ForecastData today = forecast.nextDayWeather(LocalDate.now(), 0, ZoneId.systemDefault());
            String temp = String.valueOf(Math.round(convert(today.Temp, today)));
            return temp + "°";
        } else if (param.equals(Param.VISIBILITY))
            return currentWeather.Visibility + " m";
        else // Must be WIND // ASSERT: param can't be VOID
            return windAsString(currentWeather.WindSpeed, currentWeather.WindDir);
    }

    // Formats day of the week
    private String dayOfWeekAsString(DayOfWeek day) {
        String dayString = String.valueOf(day);
        String a = dayString.substring(0, 1);
        String b = dayString.substring(1).toLowerCase();
        return a + b;
    }

    // Formats wind speed and direction
    public static String windAsString(double windSpeed, int windDir) {
        String windSpeedStr = windSpeed + " m/s";
        String windDirStr = windDir + "° ";
        if (windDir >= 23 && windDir < 68)
            windDirStr += "NE";
        else if (windDir >= 68 && windDir < 113)
            windDirStr += "E";
        else if (windDir >= 113 && windDir < 158)
            windDirStr += "SE";
        else if (windDir >= 158 && windDir < 203)
            windDirStr += "S";
        else if (windDir >= 203 && windDir < 248)
            windDirStr += "SW";
        else if (windDir >= 248 && windDir < 293)
            windDirStr += "W";
        else if (windDir >= 293 && windDir < 338)
            windDirStr += "NW";
        else // Must be North
            windDirStr += "N";
        return windSpeedStr + "\n" + windDirStr;
    }
}
