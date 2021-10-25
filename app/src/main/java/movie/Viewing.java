package movie;

import java.util.*;

public class Viewing{

    private Movie movie;
    private int screenType; // 0, 1,2 - bronze, silver, gold
    private int timeOfDay; // 0, 1, 2 - morning, midday, evening
    private String day;


    public Viewing(Movie movie, int screenType, int timeOfDay, String day){
        this.movie = movie;
        this.screenType = screenType;
        this.timeOfDay = timeOfDay;
        this.day = day;
    }

    public Movie getMovie(){
        return this.movie;
    }

    public int getScreenType(){
        return this.screenType;
    }

    public int getTimeOfDay(){
        return this.timeOfDay;
    }

    public String getDay(){
        return this.day;
    }
}