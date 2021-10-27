package movie;

import java.util.*;
import java.io.*;

public class Cinema {
    
    private String name;
    private final String LOCATION_PATH;
    private ArrayList<Movie> movies;

    private ArrayList<Viewing> monday;
    private ArrayList<Viewing> tuesday;
    private ArrayList<Viewing> wednesday;
    private ArrayList<Viewing> thursday;
    private ArrayList<Viewing> friday;
    private ArrayList<Viewing> saturday;
    private ArrayList<Viewing> sunday;
    


    public Cinema(String name, String locationPath, ArrayList<Movie> movies){
        this.name = name;
        this.LOCATION_PATH = locationPath;
        this.movies = movies;
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
                
                if (day.equals("Monday")){
                    this.monday.add(new Viewing(targetMovie, screenType, timeOfDay, day));
                }

                else if (day.equals("Tuesday")){
                    this.tuesday.add(new Viewing(targetMovie, screenType, timeOfDay, day));
                }

                else if (day.equals("Wednesday")){
                    this.wednesday.add(new Viewing(targetMovie, screenType, timeOfDay, day));
                }

                else if (day.equals("Thursday")){
                    this.thursday.add(new Viewing(targetMovie, screenType, timeOfDay, day));
                }

                else if (day.equals("Friday")){
                    this.friday.add(new Viewing(targetMovie, screenType, timeOfDay, day));
                }

                else if (day.equals("Saturday")){
                    this.saturday.add(new Viewing(targetMovie, screenType, timeOfDay, day));
                }

                else if (day.equals("Sunday")){
                    this.sunday.add(new Viewing(targetMovie, screenType, timeOfDay, day));
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
}