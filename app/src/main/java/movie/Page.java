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

    protected void parseAll() {
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
            this.movies = storeMovies(this.MOVIE_LOCATION);
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

    //finds User object given username
    public String getUserByUsername(String username){
        //extracts all users from members.Json
        String usersJsonPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        ArrayList<User> allUsers = storeUsers(usersJsonPath);
        
        //extracts username if user exists
        
        String foundUserName = null;
        int len = allUsers.size();
        int i = 0;
        while (i < len){
            if(allUsers.get(i).getName().toLowerCase().equals(username.toLowerCase())){
                foundUserName = allUsers.get(i).getName();
            }
            i += 1;
        }

        return foundUserName;
    
    }

    public String getStaffByUsername(String username){
        //extracts all users from members.Json
        String usersJsonPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        ArrayList<User> allUsers = storeUsers(usersJsonPath);
        
        //extracts username if user exists
        
        String foundUserName = null;
        int len = allUsers.size();
        int i = 0;
        while (i < len){
            if(
                allUsers.get(i).getName().toLowerCase().equals(username.toLowerCase()) &&
                allUsers.get(i).getType().equals("staff")){
                foundUserName = allUsers.get(i).getName();
            }
            i += 1;
        }

        return foundUserName;
    
    }
    public void addUser(User user){
        String name = user.getName();
        String password = user.getPassword();
        String status = user.getType();
        /*
        JSONObject userEntry = new JSONObject({
            "name":name,
            "password":password,
            "status":status
            }); 

        */
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObjectInput = (JSONObject) parser.parse(new FileReader(USERS_LOCATION));
            JSONArray usersArray = (JSONArray) jsonObjectInput.get("users");
        
            JSONObject userEntry = new JSONObject();
            JSONObject users = new JSONObject();
    
    
            userEntry.put("name",name);
            userEntry.put("password",password);
            userEntry.put("status",status);
    
            usersArray.add(userEntry);
    
            users.put("users",usersArray);
    
            try (FileWriter file = new FileWriter(USERS_LOCATION)) {
                file.write(users.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException p) {
            p.printStackTrace();
        }
    }

    public void removeUser(String username){
        //extracts all users from members.Json
        String usersJsonPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        ArrayList<User> allUsers = storeUsers(usersJsonPath);
        
        //extracts username if user exists
        
        String foundUserName = null;
        int len = allUsers.size();
        int i = 0;
        while (i < len){
            if(allUsers.get(i).getName().toLowerCase().equals(username.toLowerCase())){
                allUsers.remove(i);
            }
            i += 1;
        }

        JSONParser parser = new JSONParser();


        JSONObject jsonObjectInput = new JSONObject();
        JSONArray usersArray = new JSONArray();
    
        JSONObject users = new JSONObject();

        for (User user : allUsers) {
            JSONObject userEntry = new JSONObject();
            userEntry.put("name",user.getName());
            userEntry.put("password",user.getPassword());
            userEntry.put("status",user.getType());
    
            usersArray.add(userEntry);
        }
        users.put("users",usersArray);

        try (FileWriter file = new FileWriter(USERS_LOCATION)) {
            file.write(users.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


        /*
        JSONObject userEntry = new JSONObject({
            "name":name,
            "password":password,
            "status":status
            }); 

        */

    /*
    ************** All methods in the section below are for the filter method **************
    remember to make methods for converting movie name, location, and screen size to correct format to be inputed into the main filter method
    */
    //Extracts all users from Users.json
    public ArrayList<User> storeUsers(String locationPath){
        ArrayList<User> userList = new ArrayList<User>();

        JSONParser parser = new JSONParser();
    
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(locationPath));
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
    
                    uFull = new User(name,password,status);
                    userList.add(uFull);
                    uFull = null;
                }
    
            } catch (FileNotFoundException e) {
                System.out.println("NO FILE");
            } catch (IOException e) {
                System.out.println("ERROR");
            } catch (ParseException e) {
                System.out.println("ERROR");
            }

        return userList;
    }

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
    
                    mFull = new Movie(title, synopsis, runtime, classification, cast, director, id, release);
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
            if(allMovies.get(i).getTitle().toLowerCase().equals(movieName.toLowerCase())){
                movieId = allMovies.get(i).getID();
            }
            i += 1;
        }

        return movieId;
    
    }

    //finds movie object given movie title
    public Movie getMovieById(String movieId){
        //extracts all movies from Movies.Json
        String movieJsonPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        ArrayList<Movie> allMovies = storeMovies(movieJsonPath);
        
        //extracts movie id if movie title is given 
        
        Movie movie = null; 
        int len = allMovies.size();
        int i = 0;
        boolean found = false;
        while (i < len){
            if(allMovies.get(i).getID().equals(movieId)){
                movie = allMovies.get(i);
                found = true;
            }
            i += 1;
        }

        if (found == true){
            return movie;
        }
        else{
            return null;
        }
    }

    public String getMovieTime(String number){
        if (number.equals("0")){
            return "morning";
        }
        if (number.equals("1")){
            return "midday";
        }
        if (number.equals("2")){
            return "evening";
        }
        else{
            return "invalid movie time";
        }
    }

    public String getScreenByNum(String screenNum){
        if (screenNum.equals("0")){
            return "Bronze Screen";
        }
        if (screenNum.equals("1")){
            return "Silver Screen";
        }
        if (screenNum.equals("2")){
            return "Gold Screen";
        }
        else{
            return "invalid screen number";
        }
    }


    //returns a string of all movies given location and screen size
    public String findAllMoviesGivenLocationAndScreen(String location, String screen){

        //initiates location name message to identify which cinema the outputted movies belong to
        String locationName = location + ":\n";

        try { 

        //path to timetable.csv
        String timePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Timetable.csv").toString();

        File timeFile = new File(timePath);
        Scanner timeScan = new Scanner(timeFile);

        String filmId = new String();

        //gets all movies for parsing in the while loop below
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        ArrayList<Movie> allFilms = storeMovies(movieLocationPath);

        timeScan.nextLine();
        while (timeScan.hasNextLine()){
            String[] timeLine = timeScan.nextLine().split(",", 4); //splitting each timetable.csv line into 4 elements in a list

            if (timeLine[3].equals(screen)){ //if array of screen numbers contain the timetable screen num
                filmId = timeLine[2]; //extracts film id of the timetable line
                Movie foundMovie = getMovieById(filmId);
                if (foundMovie == null){
                    continue;
                }
                locationName +=  foundMovie.toString();
                locationName += "\n" + getScreenByNum(timeLine[3]) + "   " + timeLine[1] + " " + getMovieTime(timeLine[0]) + "\n\n";
            }
        }
        } catch (IOException e) {
            System.out.println("NO FILE");
        } 
        return locationName;
    }

    //returns a string of movies respective to all filters entered
    public String findAllMoviesGivenAllFilters(String movieId, String location, String screen){
        String locationName = location + ":\n";
        
        try { 

        String timePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Timetable.csv").toString();
        File timeFile = new File(timePath);
        Scanner timeScan = new Scanner(timeFile);
        timeScan.nextLine();
        while (timeScan.hasNextLine()){
            String[] timeLine = timeScan.nextLine().split(",", 4);
            if (timeLine[2].equals(movieId) && timeLine[3].equals(screen)){ //if screen number array contains the timetable line screen num and if the film id matches
                Movie foundMovie = getMovieById(movieId);
                if (foundMovie == null){
                    continue;
                }
                locationName +=  foundMovie.toString();
                locationName += "\n" + getScreenByNum(timeLine[3]) + "   " + timeLine[1] + " " + getMovieTime(timeLine[0]) + "\n\n";
            }
        }
        } catch (IOException e) {
            System.out.println("NO FILE");
        } 
        return locationName;
    }

    public String convertScreenToNum(String screenName){
        if (screenName.toLowerCase().equals("bronze")){
            return "0";
        }
        if (screenName.toLowerCase().equals("silver")){
            return "1";
        }
        if (screenName.toLowerCase().equals("gold")){
            return "2";
        }
        else{
            return "invalid screen name";
        }
    }

    public String convertLocationFormat(String location){

        //System.out.println("location inputted is " + location);

        if (location.toLowerCase().equals("bondi")){
            return "Bondi";
        }
        if (location.toLowerCase().equals("hurstville")){
            return "Hurstville";
        }
        if (location.toLowerCase().equals("george street")){
            return "George Street";
        }
        if (location.toLowerCase().equals("chatswood")){
            return "Chatswood";
        }
        else{
            return "invalid location name:" + location;
        }
    }

    public ArrayList<String> readFilterInput() throws IOException{
        //reads user filter input

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String temp = in.readLine();

        if (temp.toLowerCase().equals("q")){
            return null; 
        }

        String[] attributes = temp.split(",", 3);

        //collects each filter
        String selectMovie = attributes[0];
        String selectLocation = attributes[1];
        String selectScreen = attributes[2];

        String location = "";
        String movieID = "";
        String screenNum = "";

        if(!selectLocation.isEmpty()){
            location = convertLocationFormat(selectLocation);
        }
        if(!selectMovie.isEmpty()){
            movieID = findMovieId(selectMovie);
        }
        if(!selectScreen.isEmpty()){
            screenNum = convertScreenToNum(selectScreen);
        }

        ArrayList<String> filterAttributes = new ArrayList<String>();
        filterAttributes.add(movieID);
        filterAttributes.add(location);
        filterAttributes.add(screenNum);

        return filterAttributes;
    }

    //returns a string of movies given movie name and cinema location
    public String findAllMoviesGivenLocationAndMovie(String movieId, String location){

        String locationName = location + ":\n";
        try {

        String timePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Timetable.csv").toString();
        File timeFile = new File(timePath);
        Scanner timeScan = new Scanner(timeFile);

        String screenClass = new String();
        timeScan.nextLine();
        while (timeScan.hasNextLine()){
            String[] timeLine = timeScan.nextLine().split(",", 4);
            if (timeLine[2].equals(movieId)){
                
                Movie foundMovie = getMovieById(movieId);
                if (foundMovie == null){
                    continue;
                }
                locationName +=  foundMovie.toString();
                locationName += "\n" + getScreenByNum(timeLine[3]) + "   " + timeLine[1] + " " + getMovieTime(timeLine[0]) + "\n\n";
                
            }
        }
        } catch (IOException e) {
            System.out.println("NO FILE");
        } 
        return locationName;
    }

    //returns a string of movies given cinema location
    public String findAllMoviesGivenLocation(String location){

        String locationName = location + ":\n";

        try {

        String timePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", location, "Timetable.csv").toString();
        File timeFile = new File(timePath);
        Scanner timeScan = new Scanner(timeFile);
        timeScan.nextLine();
        while (timeScan.hasNextLine()){
            String[] timeLine = timeScan.nextLine().split(",", 4);
            Movie foundMovie = getMovieById(timeLine[2]);
            if (foundMovie == null){
                continue;
            }
            locationName +=  foundMovie.toString();
            locationName += "\n" + getScreenByNum(timeLine[3]) + "   " + timeLine[1] + " " + getMovieTime(timeLine[0]) + "\n\n";
        }
        } catch (IOException e) {
            System.out.println("NO FILE");
        } 
        return locationName;
    }

    public void displayFilterMessage(){
        try{
        String filterPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "filter", "main.txt").toString();
        File message = new File(filterPagePath);
        Scanner filterMessage = new Scanner(message);
        while(filterMessage.hasNextLine()){
            System.out.println(filterMessage.nextLine());
        }
        } catch (FileNotFoundException e){
            System.out.println("file not found");
        }
    }

    //main filtering method incorporating the scenario methods above 
    public void filterMovies(String movieID, String cinLocation, String screenSize) {

        //prints opening message
        this.displayFilterMessage();

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
            } catch (IOException e) {
                System.out.println("NO FILE");
            } 
        }
        */

        //finds time of all movies with only given screen size
        if (movieID.isEmpty() && cinLocation.isEmpty() && !screenSize.isEmpty()){

            String bondi = "Bondi";
            String chatswood = "Chatswood";
            String gs = "George Street";
            String Hurstville = "Hurstville";
            
            String bondiMovies = findAllMoviesGivenLocationAndScreen(bondi, screenSize);
            String chatswoodMovies = findAllMoviesGivenLocationAndScreen(chatswood, screenSize);
            String gsMovies = findAllMoviesGivenLocationAndScreen(gs, screenSize);
            String HurstvilleMovies = findAllMoviesGivenLocationAndScreen(Hurstville, screenSize);

            String finalMessage = bondiMovies + "\n" + chatswoodMovies + "\n" + gsMovies + "\n" + HurstvilleMovies + "\n";
            System.out.println(finalMessage);
        }

        //finds time of all movies given only location
        if (movieID.isEmpty() && !cinLocation.isEmpty() && screenSize.isEmpty()){
            String movieFromLocation = findAllMoviesGivenLocation(cinLocation);
            System.out.println(movieFromLocation);
        }

        //finds time of all movies given only movie id
        if (!movieID.isEmpty() && cinLocation.isEmpty() && screenSize.isEmpty()){

            String bondi = "Bondi";
            String chatswood = "Chatswood";
            String gs = "George Street";
            String Hurstville = "Hurstville";

            String bondiMovies = findAllMoviesGivenLocationAndMovie(movieID, bondi);
            String chatswoodMovies = findAllMoviesGivenLocationAndMovie(movieID, chatswood);
            String gsMovies = findAllMoviesGivenLocationAndMovie(movieID, gs);
            String HurstvilleMovies = findAllMoviesGivenLocationAndMovie(movieID, Hurstville);

            String finalMovies = bondiMovies + "\n" + chatswoodMovies + "\n" + gsMovies + "\n" + HurstvilleMovies + "\n";
            System.out.println(finalMovies);
        }

        //finds time of all movies given all filters 
        if (!movieID.isEmpty() && !screenSize.isEmpty() && !cinLocation.isEmpty()){
            String movieFromAllFilter = findAllMoviesGivenAllFilters(movieID, cinLocation, screenSize);
            System.out.println(movieFromAllFilter);
        }

        //finds time of all movies given location and screen
        if (movieID.isEmpty() && !screenSize.isEmpty() && !cinLocation.isEmpty()){
            String movieFromLocationAndScreen = findAllMoviesGivenLocationAndScreen(cinLocation, screenSize);
            System.out.println(movieFromLocationAndScreen);
        }

        //finds time of all movies given location and movie
        if (!movieID.isEmpty() && !cinLocation.isEmpty() && screenSize.isEmpty()){
            String movieFromLocationAndFilm = findAllMoviesGivenLocationAndMovie(movieID, cinLocation);
            System.out.println(movieFromLocationAndFilm);
        }

        //finds time of all movies given movie and screen
        if (!movieID.isEmpty() && cinLocation.isEmpty() && !screenSize.isEmpty()){

            String bondi = "Bondi";
            String chatswood = "Chatswood";
            String gs = "George Street";
            String Hurstville = "Hurstville";

            String bondiMovies = findAllMoviesGivenAllFilters(movieID, bondi, screenSize);
            String chatswoodMovies = findAllMoviesGivenAllFilters(movieID, chatswood, screenSize);
            String gsMovies = findAllMoviesGivenAllFilters(movieID, gs, screenSize);
            String HurstvilleMovies = findAllMoviesGivenAllFilters(movieID, Hurstville, screenSize);

            String finalMessage = bondiMovies + "\n" + chatswoodMovies + "\n" + gsMovies + "\n" + HurstvilleMovies + "\n";
            System.out.println(finalMessage);
        }

    } 

}