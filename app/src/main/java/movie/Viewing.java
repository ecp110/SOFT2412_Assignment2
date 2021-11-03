package movie;

import java.util.*;

public class Viewing{

    private Movie movie;
    private Cinema cinema;
    private int screenType; // 0, 1,2 - bronze, silver, gold
    private int timeOfDay; // 0, 1, 2 - morning, midday, evening
    private String day;
    


    public Viewing(Cinema cinema, Movie movie, int screenType, int timeOfDay, String day){
        this.cinema = cinema;
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

    public Cinema getCinema(){
        return this.cinema;
    }

    public String getScreenName() {
        if (this.screenType == 0) {
            return "Bronze";
        } else if (this.screenType == 1) {
            return "Silver";
        } else if (this.screenType == 2) {
            return "Gold";
        }

        return null;
    }

    public int getTimeOfDay(){
        return this.timeOfDay;
    }

    public String getTimeOfDayName() {
        if (this.timeOfDay == 0) {
            return "Morning";
        } else if (this.timeOfDay == 1) {
            return "Midday";
        } else if (this.timeOfDay == 2) {
            return "Evening";
        }

        return null;
    }

    public String getDay(){
        return this.day;
    }

    public String toString(){
        String returnString = 
            this.getCinema().getName() + ": "+
            this.getDay() +" "+ this.getTimeOfDayName() +
            " ("+this.getScreenName()+" class) "+
            this.getMovie().getTitle();

        return returnString;
    }

    public void setTimeOfDay(int i) {
        this.timeOfDay = i;
    }

    public void setScreen(int i) {
        this.screenType = i;
    }

    public void setMovie(Movie mov) {
        this.movie = mov;
    }
}