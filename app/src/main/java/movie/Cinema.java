package movie;

import java.util.*;
import java.io.*;

public class Cinema {
    
    private String name;
    private final String LOCATION_PATH;
    private ArrayList<Movie> movies;

    private ArrayList<Viewing> monday = new ArrayList<Viewing>();
    private ArrayList<Viewing> tuesday = new ArrayList<Viewing>();
    private ArrayList<Viewing> wednesday = new ArrayList<Viewing>();
    private ArrayList<Viewing> thursday = new ArrayList<Viewing>();
    private ArrayList<Viewing> friday = new ArrayList<Viewing>();
    private ArrayList<Viewing> saturday = new ArrayList<Viewing>();
    private ArrayList<Viewing> sunday = new ArrayList<Viewing>();
    


    public Cinema(String name, String locationPath, ArrayList<Movie> movies){
        this.name = name;
        this.LOCATION_PATH = locationPath;
        this.movies = movies;

        this.monday = new ArrayList<Viewing>();
        this.tuesday = new ArrayList<Viewing>();
        this.wednesday = new ArrayList<Viewing>();
        this.thursday = new ArrayList<Viewing>();
        this.friday = new ArrayList<Viewing>();
        this.saturday = new ArrayList<Viewing>();
        this.sunday = new ArrayList<Viewing>();

        parseViewings();

        
    }

    public String getName(){
        return this.name;
    }

    public String getLocationPath(){
        return this.LOCATION_PATH;
    }

    public ArrayList<Movie> getMovies(){
        return this.movies;
    }

    public ArrayList<Viewing> getMonday(){
        return this.monday;
    }

    public ArrayList<Viewing> getTuesday(){
        return this.tuesday;
    }

    public ArrayList<Viewing> getWednesday(){
        return this.wednesday;
    }

    public ArrayList<Viewing> getThursday(){
        return this.thursday;
    }

    public ArrayList<Viewing> getFriday(){
        return this.friday;
    }

    public ArrayList<Viewing> getSaturday(){
        return this.saturday;
    }

    public ArrayList<Viewing> getSunday(){
        return this.sunday;
    }

    public ArrayList<Viewing> getAllDays(){
        ArrayList<Viewing> all = new ArrayList<Viewing>();
        
        all.addAll(getMonday());
        all.addAll(getTuesday());
        all.addAll(getWednesday());
        all.addAll(getThursday());
        all.addAll(getFriday());
        all.addAll(getSaturday());
        all.addAll(getSunday());
        
        return all;
    }

    private void parseViewings(){
        // Initiates scanner for users file
        try {
            Scanner sc = new Scanner(new File(this.LOCATION_PATH));
            String[] line = new String[4];

            // Skips the initial line with column names
            sc.nextLine();

            // Reads in each of the lines into User objects and stores them in the page
            while (sc.hasNextLine()) {

                line = sc.nextLine().split(",");

                int timeOfDay = Integer.valueOf(line[0]);
                String day = line[1];
                String movieID = line[2];
                int screenType = Integer.valueOf(line[3]);

                Movie targetMovie = null;

                for (Movie movie : this.movies){
                    if (movieID.equals(movie.getID())){
                        targetMovie = movie;
                    }
                }

                Viewing viewingInstance = new Viewing(this, targetMovie, screenType, timeOfDay, day);
                if (day.equals("Monday")){
                    monday.add(viewingInstance);
                }

                else if (day.equals("Tuesday")){
                    this.tuesday.add(viewingInstance);
                }

                else if (day.equals("Wednesday")){
                    this.wednesday.add(viewingInstance);
                }

                else if (day.equals("Thursday")){
                    this.thursday.add(viewingInstance);
                }

                else if (day.equals("Friday")){
                    this.friday.add(viewingInstance);
                }

                else if (day.equals("Saturday")){
                    this.saturday.add(viewingInstance);
                }

                else if (day.equals("Sunday")){
                    this.sunday.add(viewingInstance);
                }
                
            }


        } catch (FileNotFoundException e) {
            return;
        }
    }


    public String dayToString(String day){

        if (day == null || day.equals("")) {
            return null;
        }

        ArrayList<Viewing> currDay = null;
        String retString = null;

        if (day.equals("Monday")){
            currDay = this.monday;
        }

        else if (day.equals("Tuesday")){
            currDay = this.tuesday;
        }

        else if (day.equals("Wednesday")){
            currDay = this.wednesday;
        }

        else if (day.equals("Thursday")){
            currDay = this.thursday;
        }

        else if (day.equals("Friday")){
            currDay = this.friday;
        }

        else if (day.equals("Saturday")){
            currDay = this.saturday;
        }

        else if (day.equals("Sunday")){
            currDay = this.sunday;
        }

        for (Viewing viewing : currDay){

            int timeOfDay = viewing.getTimeOfDay();
            String session = "";

            if (timeOfDay == 0){
                session = "Morning";
            }
            else if (timeOfDay == 1){
                session = "Midday";
            }
            else if (timeOfDay == 2){
                session = "Evening";
            }

            retString += session + " | ";
            retString += viewing.getMovie().toString() + " | ";
            
            int screenType = viewing.getScreenType();
            String classType = "";

            if (screenType == 0){
                classType = "Bronze";
            }
            else if (screenType == 1){
                classType = "Silver";
            }
            else if (screenType == 2){
                classType = "Gold";
            }

            retString += classType;
            
        }

        return retString;

    }

    public String toString(){
        return this.getName();
    }
}