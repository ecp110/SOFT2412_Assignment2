package movie;

import java.util.*;

public class Cinema {
    
    private String name;
    private String locationPath;
    private ArrayList<Movie> movies;

    private ArrayList<Viewing> monday;
    private ArrayList<Viewing> tueday;
    private ArrayList<Viewing> wednesday;
    private ArrayList<Viewing> thursday;
    private ArrayList<Viewing> friday;
    private ArrayList<Viewing> saturday;
    private ArrayList<Viewing> sunday;
    


    public Cinema(String name, String locationPath, ArrayList<Movies> movies){
        this.name = name;
        this.locationPath = locationPath;
        this.movies = movies;
        this.viewings = parseViewings();
    }

    public String getName(){
        return this.name;
    }

    public String locationPath(){
        return this.locationPath;
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
            Scanner sc = new Scanner(new File(this.locationPath));
            String[] line = new String[4];

            // Skips the initial line with column names
            sc.nextLine();

            // Reads in each of the lines into User objects and stores them in the page
            while (sc.hasNextLine()) {

                line = sc.nextLine().split(",");

                String timeOfDay = line[0];
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
            String session = null;

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
            String class = null;

            if (screenType == 0){
                class = "Bronze";
            }
            else if (screenType == 1){
                class = "Silver";
            }
            else if (screenType == 2){
                class = "Gold";
            }

            retString += class;
            
        }

        return retString;

    }
}