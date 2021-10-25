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

public abstract class Page {
    // Database locations
    protected final String MOVIE_LOCATION;
    protected final String CINEMAS_LOCATION;
    protected final String CREDIT_CARD_LOCATION;
    protected final String GIFT_CARD_LOCATION;
    protected final String USERS_LOCATION;
    protected final String PAGE_PATH;
    protected final Path currentPath = Paths.get(System.getProperty("user.dir"));

    // User information; stored in an arraylist of User objects
    protected ArrayList<User> users = new ArrayList<User>();

    // Movie information; stored in an arralist of Movie objects
    protected ArrayList<Movie> movies = new ArrayList<Movie>();

    // CreditCard information; stored in an arralist of CreditCard objects
    protected ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();

    // GiftCard information; stored in an arralist of GiftCard objects
    protected ArrayList<GiftCard> giftCards = new ArrayList<GiftCard>();

    public Page(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, String pagePath) {
        this.MOVIE_LOCATION = movieLocation;
        this.CINEMAS_LOCATION = cinemasLocation;
        this.CREDIT_CARD_LOCATION = creditCardLocation;
        this.GIFT_CARD_LOCATION = giftCardLocation;
        this.USERS_LOCATION = usersLocation;
        this.PAGE_PATH = pagePath;

        this.parseAll();
    }

    /**
     * Displays the home / initial page which a user lands on for the given page
     */
    public abstract String displayInitial();

    /**
     * Page to return to & display if at any time the user cancels the current operation
     */
    public abstract HomePage cancel();

    //public void JSONObjectCreator(String folder, String filename) throws FileNotFoundException {
       // System.err.println(filename);
        //json = new JSONObject(new JSONTokener(new FileReader(folder + "/" + filename)));
    //}

    private void parseAll() {
        this.parseUsers();
        this.parseMovies();
        this.parseCreditCards();
        this.parseGiftCards();
    }

    /** 
     * Parses users into arraylist of users
     */
    protected void parseUsers() {
        // Initiates scanner for users file
        JSONParser parser = new JSONParser();
    
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(this.USERS_LOCATION));
            JSONArray users = (JSONArray) jsonObject.get("users");

            String name;
            String password;
            String status;
            User uFull;
            
            for (Object user : users) {
                JSONObject u = (JSONObject) user;

                name = (String) u.get("name");
                password = (String) u.get("password");
                status = (String) u.get("status");

                uFull = new User(name, password, status);
                this.users.add(uFull);
                uFull = null;
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } catch (ParseException e) {
            System.out.println("ERROR");
        }
  
    }

    protected void parseMovies(){
            JSONParser parser = new JSONParser();
    
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(this.MOVIE_LOCATION));
                JSONArray movies = (JSONArray) jsonObject.get("movies");
    
                String title;
                String id;
                String classification;
                int runtime;
                String director;
                ArrayList<String> cast = null;
                Calendar release = null;
                String synopsis;
                Movie mFull;
                
                for (Object movie : movies) {
                    JSONObject m = (JSONObject) movie;
    
                    title = (String) m.get("name");
                    id = String.valueOf(m.get("id"));
                    classification = (String) m.get("classification");
                    runtime = (int) (long) m.get("runtime");
                    director = (String) m.get("director");
                    synopsis = (String) m.get("synopsis");
    
                    mFull = new Movie(title, synopsis, runtime, classification, cast, id, director, release);
                    this.movies.add(mFull);
                    mFull = null;
                }
    
            } catch (FileNotFoundException e) {
                System.out.println("NO FILE");
            } catch (IOException e) {
                System.out.println("ERROR");
            } catch (ParseException e) {
                System.out.println("ERROR");
            }
 
    }

    protected void parseCreditCards(){
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(this.CREDIT_CARD_LOCATION));
            JSONArray creditCards = (JSONArray) jsonObject.get("creditcards");

            String name;
            String number;
            CreditCard cFull;
            
            for (Object card : creditCards) {
                JSONObject c = (JSONObject) card;

                name = (String) c.get("name");
                number = (String) c.get("number");

                cFull = new CreditCard(name, number);
                this.creditCards.add(cFull);
                cFull = null;
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } catch (ParseException e) {
            System.out.println("ERROR");
        }

    }

    protected void parseGiftCards(){
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(this.GIFT_CARD_LOCATION));
            JSONArray giftCards = (JSONArray) jsonObject.get("giftcards");

            String number;
            boolean redeemed = false;

            String cParsing;
            int day;
            int month;
            int year;

            Calendar issue;
            Calendar expiry;
            GiftCard gFull;
            
            for (Object card : giftCards) {
                JSONObject g = (JSONObject) card;

                number = (String) g.get("number");
                if ((int) (long) g.get("redeemed") == 1) {
                    redeemed = true;
                }
                cParsing = (String) g.get("issue");
                day = Integer.valueOf(cParsing.substring(0, 2));
                month = Integer.valueOf(cParsing.substring(2, 4));
                year = Integer.valueOf(cParsing.substring(4));
                issue = new Calendar(day, month, year);

                cParsing = (String) g.get("expiry");
                day = Integer.valueOf(cParsing.substring(0, 2));
                month = Integer.valueOf(cParsing.substring(2, 4));
                year = Integer.valueOf(cParsing.substring(4));
                expiry = new Calendar(day, month, year);

                gFull = new GiftCard(number, redeemed, issue, expiry);
                this.giftCards.add(gFull);
                gFull = null;
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } catch (ParseException e) {
            System.out.println("ERROR");
        }

    }

    /**
     * Parses a txt file into a String which it then returns
     */
    protected String parseTxt(String fileExtension, int lineSkip) {
        String output = "";
        String fileLoc = this.PAGE_PATH + fileExtension;
        try {
            Scanner sc = new Scanner(new File(fileLoc));
            for (int i = 0; i < lineSkip; i++) {
                sc.nextLine();
            }

            while (sc.hasNextLine()) {
                output += sc.nextLine();
                output += "\n";
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        return output;
    }

    /**
     * Returns string with all movies, one movie per line
     */
    protected String displayMovies() {
        String retString = "";
        for (Movie movie : this.movies) {
            retString += movie.toString();
            retString += "\n";
        }
        return retString;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    /*
    ************** All methods in the section below are for the filter method **************
    remember to make methods for converting movie name, location, and screen size to correct format to be inputed into the main filter method
    */

    //Extracts all movies from Movies.json
    public ArrayList<Movie> storeMovies(String locationPath){
        ArrayList<Movie> films = new ArrayList<Movie>();

        JSONParser parser = new JSONParser();
    
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(locationPath));
                JSONArray movies = (JSONArray) jsonObject.get("movies");
    
                String title;
                String id;
                String classification;
                int runtime;
                String director;
                ArrayList<String> cast = null;
                Calendar release = null;
                String synopsis;
                Movie mFull;
                
                for (Object movie : movies) {
                    JSONObject m = (JSONObject) movie;
    
                    title = (String) m.get("name");
                    id = String.valueOf(m.get("id"));
                    classification = (String) m.get("classification");
                    runtime = (int) (long) m.get("runtime");
                    director = (String) m.get("director");
                    synopsis = (String) m.get("synopsis");
    
                    mFull = new Movie(title, synopsis, runtime, classification, cast, id, director, release);
                    films.add(mFull);
                    mFull = null;
                }
    
            } catch (FileNotFoundException e) {
                System.out.println("NO FILE");
            } catch (IOException e) {
                System.out.println("ERROR");
            } catch (ParseException e) {
                System.out.println("ERROR");
            }

        return films;
    }

    //finds movieID given movie title
    public String findMovieId(String movieName){
        //extracts all movies from Movies.Json
        String movieJsonPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        ArrayList<Movie> allMovies = storeMovies(movieJsonPath);
        
        //extracts movie id if movie is given 
        
        String movieId = new String(); 
        int len = allMovies.size();
        int i = 0;
        while (i < len){
            if(allMovies.get(i).getTitle().toLowerCase() == movieName.toLowerCase()){
                movieId = allMovies.get(i).getID();
            }
            i += 1;
        }

        return movieId;
    
    }

    //convert 24hr time in the timetable.csv to 12hour time. eg: 0930 -> 9:30AM 
    public String convertTime(String fourDigits){
        final int mid = fourDigits.length() / 2; //get the middle of the String
        String[] parts = {fourDigits.substring(0, mid),fourDigits.substring(mid)};

        int hr = Integer.parseInt(parts[0]);

        if (hr == 12){
            String convertedHr = Integer.toString(hr);
            String convertedTime = convertedHr + ":" + parts[1] + "PM";
            return convertedTime;
        }

        if (hr > 12){
            hr = hr - 12;
            String convertedHr = Integer.toString(hr);
            String convertedTime = convertedHr + ":" + parts[1] + "PM";
            return convertedTime;
        }

        if (hr < 12){
            String convertedTime = parts[0] + ":" + parts[1] + "AM";
            return convertedTime;
        }

        else{
            return "invalid time";
        }
    }


    //returns a string of all movies given location and screen size
    public String findAllMoviesGivenLocationAndScreen(String location, String screen){

        //initiates location name message to identify which cinema the outputted movies belong to
        String locationName = location + ":\n";

        //arraylist to store all screen numbers respective to given screen size
        ArrayList<String> screens = new ArrayList<String>();

        //path to screen.csv
        String screenPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Screens.csv").toString();

        try { 

        File screenFile = new File(screenPath);
        Scanner screenScan = new Scanner(screenFile);
        
        //adds all screen numbers which correspond to the given screen size
        while (screenScan.hasNextLine()){
            String[] screenLine = screenScan.nextLine().split(",", 3);
            if (screenLine[2] == screen){
                screens.add(screenLine[1]);
            }
        }

        //path to timetable.csv
        String timePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Timetable.csv").toString();

        File timeFile = new File(timePath);
        Scanner timeScan = new Scanner(timeFile);

        String filmId = new String();

        //gets all movies for parsing in the while loop below
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        ArrayList<Movie> allFilms = storeMovies(movieLocationPath);


        while (timeScan.hasNextLine()){
            String[] timeLine = timeScan.nextLine().split(",", 4); //splitting each timetable.csv line into 4 elements in a list

            if (screens.contains(timeLine[3])){ //if array of screen numbers contain the timetable screen num
                filmId = timeLine[2]; //extracts film id of the timetable line

                for (Movie film : allFilms){ //parses all movies from Json
                    if (film.getID() == filmId){ 
                        String showTime = convertTime(timeLine[0]); //converts 24hr time in timetable line to 12hr
                        locationName += "\n";
                        locationName += timeLine[1] + " " + showTime + ":\n";
                        locationName += film.toString();
                    }
                }
            }
        }
        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } 
        return locationName;
    }

    //returns a string of movies respective to all filters entered
    public String findAllMoviesGivenAllFilters(String title, String location, String screen){
        String locationName = location + ":\n";
        ArrayList<String> screens = new ArrayList<String>();
        String screenPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Screens.csv").toString();

        try { 

        File screenFile = new File(screenPath);
        Scanner screenScan = new Scanner(screenFile);

        while (screenScan.hasNextLine()){
            String[] screenLine = screenScan.nextLine().split(",", 3);
            if (screenLine[2] == screen){
                screens.add(screenLine[1]);
            }
        }

        String timePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Timetable.csv").toString();
        File timeFile = new File(timePath);
        Scanner timeScan = new Scanner(timeFile);

        String filmId = findMovieId(title);
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        ArrayList<Movie> allFilms = storeMovies(movieLocationPath);

        while (timeScan.hasNextLine()){
            String[] timeLine = timeScan.nextLine().split(",", 4);
            if (screens.contains(timeLine[3]) && timeLine[2] == filmId){ //if screen number array contains the timetable line screen num and if the film id matches
                for (Movie film : allFilms){
                    if (film.getID() == filmId){
                        String showTime = convertTime(timeLine[0]);
                        locationName += "\n";
                        locationName += timeLine[1] + " " + showTime + ":\n";
                        locationName += film.toString();
                    }
                }
            }
        }
        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } 
        return locationName;
    }

    //returns a string of movies given movie name and cinema location
    public String findAllMoviesGivenLocationAndMovie(String title, String location){

        String locationName = location + ":\n";
        ArrayList<String> screens = new ArrayList<String>();
        String screenPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Screens.csv").toString();

        try {

        File screenFile = new File(screenPath);
        Scanner screenScan = new Scanner(screenFile);
        screenScan.nextLine();
        while (screenScan.hasNextLine()){
            String[] screenLine = screenScan.nextLine().split(",", 3);
            screens.add(screenLine[1] + ":" + screenLine[2]);
        }

        String timePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Timetable.csv").toString();
        File timeFile = new File(timePath);
        Scanner timeScan = new Scanner(timeFile);

        String filmId = findMovieId(title);
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        ArrayList<Movie> allFilms = storeMovies(movieLocationPath);
        String screenClass = new String();

        while (timeScan.hasNextLine()){
            String[] timeLine = timeScan.nextLine().split(",", 4);
            if (timeLine[2] == filmId){

                for(String numAndClass : screens){
                    String[] splittedScreen = numAndClass.split(":", 2);
                    if (splittedScreen[0] == timeLine[3]){
                        screenClass = splittedScreen[1]; //matches the screen number to the class (eg gold)
                    }
                }

                for (Movie film : allFilms){
                    if (film.getID() == filmId){
                        String showTime = convertTime(timeLine[0]);
                        locationName += "\n";
                        locationName += timeLine[1] + " " + showTime + ":\n";
                        locationName += "Screen: " + screenClass + "\n"; //shows the screen class of the movie 
                        locationName += film.toString();
                    }
                }
            }
            screenClass = null;
        }
        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } 
        return locationName;
    }

    //returns a string of movies given cinema location
    public String findAllMoviesGivenLocation(String location){
        String locationName = location + ":\n";
        ArrayList<String> screens = new ArrayList<String>();
        String screenPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Screens.csv").toString();

        try {

        File screenFile = new File(screenPath);
        Scanner screenScan = new Scanner(screenFile);
        screenScan.nextLine();
        while (screenScan.hasNextLine()){
            String[] screenLine = screenScan.nextLine().split(",", 3);
            screens.add(screenLine[1] + ":" + screenLine[2]);
        }

        String timePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Timetable.csv").toString();
        File timeFile = new File(timePath);
        Scanner timeScan = new Scanner(timeFile);

        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        ArrayList<Movie> allFilms = storeMovies(movieLocationPath);
        String screenClass = new String();

        while (timeScan.hasNextLine()){
            String[] timeLine = timeScan.nextLine().split(",", 4);
            
            for(String numAndClass : screens){
                String[] splittedScreen = numAndClass.split(":", 2);
                if (splittedScreen[0] == timeLine[3]){
                    screenClass = splittedScreen[1];
                }
            }
            for (Movie film : allFilms){
                if (film.getID() == timeLine[2]){
                    String showTime = convertTime(timeLine[0]);
                    locationName += "\n";
                    locationName += timeLine[1] + " " + showTime + ":\n";
                    locationName += "Screen: " + screenClass + "\n";
                    locationName += film.toString();
                }
            }
            screenClass = null;
        }
        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } 
        return locationName;
    }

    //main filtering method incorporating the scenario methods above 
    public void filterMovies(String movieName, String cinLocation, String screenSize) {

        //prints opening message
        String filterPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "pages", "filter").toString();
        this.parseTxt("/main.txt", 1);

        /*
        extract movie id from json
        filter the screen number at the location cinema 

        if we are given all attributes:
            then we filter the location timetable for the times with movie id and screen number

        if we are given movie and location:
            then we filter the time table in the location folder and look for all movies with the id

        if we are given location and screen:
            then we filter the screen in the location folder and find the 1 or more screen number 
            then we the time table in the location folder and look for all times with screen number 
        
        if we are given movie and screen:
            then we filter the screens for their numbers 
            then we filter timetable for the matching movie id and screen number in the screen number array

        //reads user filter input
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String temp = in.readLine();
        String[] attributes = temp.split(",", 3);

        //collects each filter
        String selectMovie = attributes[0];
        String selectLocation = attributes[1];
        String selectScreen = attributes[2];
        */

        //extracts all movies from Movies.Json
        String movieJsonPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        ArrayList<Movie> allMovies = storeMovies(movieJsonPath);

        //gets movie id
        if (!movieName.isEmpty()){
            String movieId = findMovieId(movieName);
        }
        
        
        //extracts screen num from location if both filters given
        if (!screenSize.isEmpty() && !cinLocation.isEmpty()){

            //find cinema location screen path (Screens.csv)
            String screenPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", cinLocation, "Screens.csv").toString();

            //prepare to store all screen nums in an arraylist
            ArrayList<String> screenNumbers = new ArrayList<String>();

            //reading Screens.csv
            try {
            File file = new File(screenPath);
            Scanner scan =  new Scanner(file);
        

            while (scan.hasNextLine()) {
                //we split each line into a elements in an array
                String[] line = scan.nextLine().split(",", 3);
                if (line[2] == screenSize){
                    screenNumbers.add(line[1]);
                }
            }
            } catch (FileNotFoundException e) {
                System.out.println("NO FILE");
            } catch (IOException e) {
                System.out.println("ERROR");
            } 
        }

        //finds time of all movies with only given screen size
        if (movieName.isEmpty() && cinLocation.isEmpty() && !screenSize.isEmpty()){

            String bondi = "Bondi";
            String chatswood = "Chatswood";
            String gs = "Geroge Street";
            String hurtsville = "Hurtsville";

            String bondiMovies = findAllMoviesGivenLocationAndScreen(bondi, screenSize);
            String chatswoodMovies = findAllMoviesGivenLocationAndScreen(chatswood, screenSize);
            String gsMovies = findAllMoviesGivenLocationAndScreen(gs, screenSize);
            String hurtsvilleMovies = findAllMoviesGivenLocationAndScreen(hurtsville, screenSize);

            String finalMessage = bondiMovies + "\n" + chatswoodMovies + "\n" + gsMovies + "\n" + hurtsvilleMovies + "\n";
            System.out.println(finalMessage);
        }

        //finds time of all movies given only location
        if (movieName.isEmpty() && !cinLocation.isEmpty() && screenSize.isEmpty()){
            String movieFromLocation = findAllMoviesGivenLocation(cinLocation);
            System.out.println(movieFromLocation);
        }

        //finds time of all movies given only movie title
        if (!movieName.isEmpty() && cinLocation.isEmpty() && screenSize.isEmpty()){

            String bondi = "Bondi";
            String chatswood = "Chatswood";
            String gs = "Geroge Street";
            String hurtsville = "Hurtsville";

            String bondiMovies = findAllMoviesGivenLocationAndMovie(movieName, bondi);
            String chatswoodMovies = findAllMoviesGivenLocationAndMovie(movieName, chatswood);
            String gsMovies = findAllMoviesGivenLocationAndMovie(movieName, gs);
            String hurtsvilleMovies = findAllMoviesGivenLocationAndMovie(movieName, hurtsville);

            String finalMovies = bondiMovies + "\n" + chatswoodMovies + "\n" + gsMovies + "\n" + hurtsvilleMovies + "\n";
            System.out.println(finalMovies);
        }

        //finds time of all movies given all filters 
        if (!movieName.isEmpty() && !screenSize.isEmpty() && !cinLocation.isEmpty()){
            String movieFromAllFilter = findAllMoviesGivenAllFilters(movieName, cinLocation, screenSize);
            System.out.println(movieFromAllFilter);
        }

        //finds time of all movies given location and screen
        if (movieName.isEmpty() && !screenSize.isEmpty() && !cinLocation.isEmpty()){
            String movieFromLocationAndScreen = findAllMoviesGivenLocationAndScreen(cinLocation, screenSize);
            System.out.println(movieFromLocationAndScreen);
        }

        //finds time of all movies given location and movie
        if (!movieName.isEmpty() && !cinLocation.isEmpty() && screenSize.isEmpty()){
            String movieFromLocationAndFilm = findAllMoviesGivenLocationAndMovie(movieName, cinLocation);
            System.out.println(movieFromLocationAndFilm);
        }

        //finds time of all movies given movie and screen
        if (!movieName.isEmpty() && cinLocation.isEmpty() && !screenSize.isEmpty()){

            String bondi = "Bondi";
            String chatswood = "Chatswood";
            String gs = "Geroge Street";
            String hurtsville = "Hurtsville";

            String bondiMovies = findAllMoviesGivenAllFilters(movieName, bondi, screenSize);
            String chatswoodMovies = findAllMoviesGivenAllFilters(movieName, chatswood, screenSize);
            String gsMovies = findAllMoviesGivenAllFilters(movieName, gs, screenSize);
            String hurtsvilleMovies = findAllMoviesGivenAllFilters(movieName, hurtsville, screenSize);

            String finalMessage = bondiMovies + "\n" + chatswoodMovies + "\n" + gsMovies + "\n" + hurtsvilleMovies + "\n";
            System.out.println(finalMessage);
        }

    } 
}