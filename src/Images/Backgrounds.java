package src.Images;

import javafx.scene.image.Image;
import src.Preferences.Sport;
import src.Preferences.SportList;

import java.time.LocalDate;
import java.util.List;

public class Backgrounds {

    public Image CRICKET_BG;
    public Image CYCLING_BG;
    public Image FOOTBALL_BG;
    public Image HIKING_BG;
    public Image ROWING_BG;
    public Image RUGBY_BG;
    public Image RUN_BG;
    public Image SAILING_BG;
    public Image TENNIS_BG;
    public Image VOLLEYBALL_BG;

    //Display backgrounds
    public Backgrounds(){
        CRICKET_BG = new Image(".idea/Backgrounds/CRICKET.jpg");
        CYCLING_BG = new Image(".idea/Backgrounds/CYCLING.jpg");
        FOOTBALL_BG = new Image(".idea/Backgrounds/FOOTBALL.jpg");
        HIKING_BG = new Image(".idea/Backgrounds/HIKING.jpg");
        ROWING_BG = new Image(".idea/Backgrounds/ROWING.jpg");
        RUGBY_BG = new Image(".idea/Backgrounds/RUGBY.jpg");
        RUN_BG = new Image(".idea/Backgrounds/RUN.jpg");
        SAILING_BG = new Image(".idea/Backgrounds/SAILING.jpg");
        TENNIS_BG = new Image(".idea/Backgrounds/TENNIS.jpg");
        VOLLEYBALL_BG = new Image(".idea/Backgrounds/VOLLEYBALL.jpg");
    }

    //Gets the best background
    public Image getBestBackground(){


        SportList.add(Sport.VOLLEYBALL);

        List<Sport> options = SportList.get();

        if (options.size() == 0){
            return SAILING_BG;
        }

        //Chooses a sport from the list based on the day, meaning the background will change daily
        int location = LocalDate.now().getDayOfMonth() % options.size();
        Sport choice = options.get(location);

        if (choice == Sport.CRICKET){
            return CRICKET_BG;
        } else if (choice == Sport.CYCLING){
            return CYCLING_BG;
        } else if (choice == Sport.FOOTBALL){
            return FOOTBALL_BG;
        } else if (choice == Sport.HIKING){
            return HIKING_BG;
        } else if (choice == Sport.ROWING){
            return ROWING_BG;
        } else if (choice == Sport.RUGBY){
            return RUGBY_BG;
        } else if (choice == Sport.RUNNING){
            return RUN_BG;
        } else if (choice == Sport.SAILING){
            return SAILING_BG;
        } else if (choice == Sport.TENNIS){
            return TENNIS_BG;
        } else {  //Must be volleyball
            return VOLLEYBALL_BG;
        }

    }

}
