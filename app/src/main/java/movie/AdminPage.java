package movie;
import java.util.*;
import java.io.*;
import org.json.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AdminPage extends Page {

    // User details
    private User user;

    public AdminPage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, String pagePath, User user) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation, pagePath);
        this.user = user;
        this.parseAll();
    }

    public String displayInitial() {
        String output = "";
        String fileLoc = this.PAGE_PATH + "/main.txt";

        try {
            Scanner sc = new Scanner(new File(fileLoc));

            // Personalisation at start to user logged in
            output += sc.nextLine();
            output += " ";
            output += user.getName();
            output += "!";

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        output += this.parseTxt("/main.txt", 1);

        return output;
    }

    public String displayManageStaffPrompt() {
        return this.parseTxt("/manageStaff.txt", 0);
    }

    public String displayBookingLogPrompt() {
        return this.parseTxt("/askForLocation.txt", 0);
    }

    public String displayBookingReciepts(String loc) {
        if (loc == null || loc.equals("")) {
            return null;
        }

        String time;
        String customerName;
        String cardNum;
        String numSeats;
        String movieId;
        String screenType;

        String retString = "Displaying results for: " + loc + "\n\n";

        try {
            Scanner sc = new Scanner(new File(this.CINEMAS_LOCATION + "/" + loc + "/bookingLog.csv"));
            sc.nextLine(); // Skips first line in csv
            while (sc.hasNextLine()) {
                String[] vals = sc.nextLine().split(",");

                time = vals[0];
                if (time.equals("0")) {
                    time = "Morning";
                } else if (time.equals("1")) {
                    time = "Midday";
                } else if (time.equals("2")) {
                    time = "Evening";
                }

                customerName = vals[1];
                cardNum = vals[2];
                numSeats = vals[3];
                movieId = vals[4];
                screenType = vals[5];

                retString += this.formatLog(time, customerName, cardNum, numSeats, movieId, screenType);
                retString += "\n";
            } 
        } catch (IOException e) {
            return "FILE ERROR: " + this.CINEMAS_LOCATION + "/" + loc;
        }

        return retString;
    }

    private String formatLog(String time, String customerName, String cardNum, String numSeats, String movieId, String screenType) {
        String retString = "";
        // Format: [ID] (MovName): [Name] booked for [numSeats] in the [time] with card [cardNum] in the [screenType] screen.

        retString += movieId;
        for (Movie movie : this.movies) {
            if (movie.getID().equals(movieId)) {
                retString += " (" + movie.getTitle() + ") ";
                break;
            }
        }
        retString += "| " + customerName + " booked for " + numSeats + " in the " + time + " with card " + cardNum + " in the ";

        if (screenType.equals("0")) {
            retString += "Bronze screen.";
        } else if (screenType.equals("1")) {
            retString += "Silver screen.";
        } else if (screenType.equals("2")) {
            retString += "Gold screen.";
        }

        return retString;    
    }

    public String displayMovieEditPrompt() {
        return this.parseTxt("/movieEditPrompt.txt", 0);
    }

    public String displayFoundEditableMovie() {
        return this.parseTxt("/foundEditableMovie.txt", 0);
    }

    public String displayEditStringPrompt() {
        return this.parseTxt("/movieEditStringPrompt.txt", 0);
    }

    public String displayCompletedEdit() {
        return this.parseTxt("/completedEdit.txt", 0);
    }
    

    public boolean editMovie(Movie movie, String editData) {
        /*
        editData in format:
            title,id,classification,runtime,director,cast,releaseDate,synopsis
            If not applicable then leave blank (ie ,,)
        */

        if (movie == null || editData == null || editData.equals("")) {
            return false;
        }

        JSONParser parser = new JSONParser();
        String[] newAttributes = editData.split(",");

        String title = movie.getTitle();
        String id = movie.getID();
        String classification = movie.getClassification();

        int runtime = movie.getRunTime();
        String director = movie.getDirector();
        //String releaseDate = movie.getReleaseDate().getDate();
        String synopsis = movie.getSynopsis();
        try{ 
            for (int i = 0; i < newAttributes.length; i++) {
                if (!(newAttributes[i].equals(""))) {
                    if (i == 0) {
                        title = newAttributes[i];
                    } else if (i == 1) {
                        id = newAttributes[i];
                    } else if (i == 2) {
                        classification = newAttributes[i];
                    } else if (i == 3) {
                        runtime = Integer.valueOf(newAttributes[i]);
                    } else if (i == 4) {
                        director = newAttributes[i];
                    //} else if (i == 6) {
                        //releaseDate = newAttributes[i];
                    } else if (i == 7) {
                        synopsis = newAttributes[i];
                    }

                }
            }
        } catch (Exception e) {
            return false;
        }

        try {
            JSONObject jsonObjectInput = (JSONObject) parser.parse(new FileReader(MOVIE_LOCATION));
            JSONArray movieArray = (JSONArray) jsonObjectInput.get("movies");
            JSONArray editedMovieArray = (JSONArray) new JSONArray();
        
            JSONObject movieEntry = new JSONObject();
            JSONObject movies = new JSONObject();
            JSONObject m;
            for (Object o : movieArray) {
                m = (JSONObject) o;
                if (!(m.get("id").equals(movie.getID()))) {
                    editedMovieArray.add(m);
                } else {
                    movieEntry.put("name",title);
                    movieEntry.put("id",id);
                    movieEntry.put("classification",classification);
                    movieEntry.put("runtime",runtime);
                    movieEntry.put("director",director);
                    movieEntry.put("cast","TBD");
                    movieEntry.put("release date","TBD");
                    movieEntry.put("synopsis",synopsis);
    
                    editedMovieArray.add(movieEntry);
                }
            }
    
            movies.put("movies",editedMovieArray);
    
            try (FileWriter file = new FileWriter(MOVIE_LOCATION)) {
                file.write(movies.toJSONString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
    
       
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException p) {
            p.printStackTrace();
            return false;
        }
    }

    public String displayCancellations() {
        String status = this.user.getType();

        if (!(status.toLowerCase().equals("manager"))) {
            return "Sorry, you are not a manager. This functionality is not available to you.";
        }

        String cancellationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "cancellations.csv").toString();
        String retString = "";
        try {
            Scanner sc = new Scanner(new File(cancellationPath));
            sc.nextLine();

            String datetime;
            String user;
            String reason;
            
            while (sc.hasNextLine()) {
                String[] attribs = sc.nextLine().split(",");

                datetime = attribs[0];
                user = attribs[1];
                reason = attribs[2];

                retString += this.formatCancellation(datetime, user, reason);
            }
        } catch (IOException e) {}


        return retString;
    }

    private String formatCancellation(String datetime, String user, String reason) {
        if (datetime == null || user == null || reason == null) {
            return "";
        } else if (datetime.equals("") || user.equals("") || reason.equals("")) {
            return "";
        }

        String retString = "";

        retString += "On ";
        retString += datetime;
        retString += ", ";
        retString += user;
        retString += " cancelled the trasaction because ";
        retString += reason + ".";
        retString += "\n";

        return retString;
    }

    
    public HomePage cancel() {
        Engine.logCancellation(this.user, "user cancellation", this.cancellationPath);
        this.user = null;
        return new HomePage(
            this.MOVIE_LOCATION, this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION, this.PAGE_PATH
            );
    }

}