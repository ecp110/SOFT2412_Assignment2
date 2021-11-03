package movie;
import java.util.*;
import java.io.*;
import org.json.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.NumberFormat;

public abstract class Page {
    // Database locations
    protected final String MOVIE_LOCATION;
    protected final String CINEMAS_LOCATION;
    protected final String CREDIT_CARD_LOCATION;
    protected final String GIFT_CARD_LOCATION;
    protected final String USERS_LOCATION;
    protected final String PAGE_PATH;
    protected final Path currentPath = Paths.get(System.getProperty("user.dir"));
    protected String cancellationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "cancellations.csv").toString();


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

        this.users = new ArrayList<User>();
        this.movies = new ArrayList<Movie>();
        this.creditCards = new ArrayList<CreditCard>();
        this.giftCards = new ArrayList<GiftCard>();
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
                redeemed = false;
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

    public String displayInvalidInput() {
        return "Sorry, that is not a valid input. Please try again.";
    }

    public String lineBreak() {
        return "\n" + "------------------------------------------------------------------------" + "\n";
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

    public double getBookingPrice(String ticketType,String cinemaClass,int numTickets){
        double basePrice = 20;
        double typeMultiplier = 0;
        double classMultiplier = 0;

        if (ticketType.toLowerCase().equals("adult")) {
            typeMultiplier = 1;
        } else if (ticketType.toLowerCase().equals("child")) {
            typeMultiplier = 0.5;
        } else if (ticketType.toLowerCase().equals("student")) {
            typeMultiplier = 0.8;
        } else if (ticketType.toLowerCase().equals("pensioner")) {
            typeMultiplier = 0.7;
        }

        if (cinemaClass.toLowerCase().equals("bronze")) {
            classMultiplier = 1;
        } else if (cinemaClass.toLowerCase().equals("silver")) {
            classMultiplier = 1.5;
        } else if (cinemaClass.toLowerCase().equals("gold")) {
            classMultiplier = 2;
        }

        return (basePrice*typeMultiplier*classMultiplier*numTickets);
        
    }

    public String formatCurrencyString(double amount){

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        String myCurrency = currency.format(amount);
        return myCurrency;
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
        this.users.add(user);
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
        System.out.println("Removing "+username);
        //extracts all users from members.Json
        String usersJsonPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        ArrayList<User> allUsers = storeUsers(usersJsonPath);
        
        //extracts username if user exists
        
        String foundUserName = null;
        int i = 0;
        while (i < allUsers.size()){
            if(allUsers.get(i).getName().toLowerCase().equals(username.toLowerCase())){
                this.users.remove(allUsers.get(i));
                allUsers.remove(i);
                break;
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
        System.out.println("Staff member " + username + " removed.");
    }

    /** Viewings and stuff */


    public ArrayList<Viewing> storeViewings() {
        ArrayList<Viewing> viewings = new ArrayList<Viewing>();

        for(Cinema cinema : storeCinemas()) {
            viewings.addAll(cinema.getAllDays());
        }


        return viewings;
    }

    public String listLocations() {
        File locationsFolder =  new File(CINEMAS_LOCATION);
        String[] locations = locationsFolder.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        StringBuilder locationString = new StringBuilder();
        for(String location : locations) {
            locationString.append(location+"\n");
        }
        return locationString.toString();
        
    }

    public ArrayList<Cinema> storeCinemas() {
        File locationsFolder =  new File(CINEMAS_LOCATION);

        //GET ALL CINEMA LOCATION FOLDERS
        String[] locations = locationsFolder.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        ArrayList<Movie> movies = storeMovies(MOVIE_LOCATION);

        ArrayList<Cinema> cinemas = new ArrayList<Cinema>();

        for(String location : locations) {
            String cinemaPath = CINEMAS_LOCATION+"/"+location+"/Timetable.csv";
            
            Cinema cinema = new Cinema(location,cinemaPath,movies);

            cinemas.add(cinema);
            /*
            try {
                File timetable = new File(cinemaPath+"/Timetable.csv");
                FileReader fr = new FileReader(timetable);
                BufferedReader br = new BufferedReader(fr);

                String line;
                while((line=br.readLine()) != null) {
                    if (line.equals("start,day,id,screen_num")) {
                        continue;
                    }
                    List<String> lineArgs = Arrays.asList(line.split(","));
                    System.out.println(lineArgs.get(1));
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
            */

            //Cinema cinema = new Cinema(location, cinemaPath, )   
            //System.out.println(cinemaPath); 
        }

        return cinemas;
    }

    /* New filter stuff */

    public ArrayList<Viewing> filterViewings(String cinemaName, String movieID, int screenType, int timeOfDay, String day) {
        ArrayList<Viewing> results = storeViewings();

        if (!cinemaName.equals("")) {
            ArrayList<Viewing> instResults = new ArrayList<Viewing>();
            for(Viewing viewing : results) {
                if (viewing.getCinema().getName().toLowerCase().equals(cinemaName.toLowerCase())) {
                    instResults.add(viewing);
                }
            }
            results = instResults;
        }

        if(!movieID.equals("")) {
            ArrayList<Viewing> instResults = new ArrayList<Viewing>();
            for(Viewing viewing : results) {
                if (viewing.getMovie().getID().equals(movieID)) {
                    instResults.add(viewing);
                }
            }
            results = instResults;
        }

        if(screenType != -1) {
            ArrayList<Viewing> instResults = new ArrayList<Viewing>();
            for(Viewing viewing : results) {
                if (viewing.getScreenType() == screenType) {
                    instResults.add(viewing);
                }
            }
            results = instResults;    
        }

        if(timeOfDay != -1) {
            ArrayList<Viewing> instResults = new ArrayList<Viewing>();
            for(Viewing viewing : results) {
                if (viewing.getTimeOfDay() == timeOfDay) {
                    instResults.add(viewing);
                }
            }
            results = instResults;    
        }

        if(!day.equals("")) {
            ArrayList<Viewing> instResults = new ArrayList<Viewing>();
            for(Viewing viewing : results) {
                if (viewing.getDay().toLowerCase().equals(day.toLowerCase())) {
                    instResults.add(viewing);
                }
            }
            results = instResults;
        }


        return results;
    }

    public boolean removeMovie(Movie movie) {
        ArrayList<Movie> curMovies = this.storeMovies(this.MOVIE_LOCATION);
        boolean found = false;
        for (Movie m : curMovies) {
            if (m.getID().equals(movie.getID())) {
                found = true;
                this.movies.remove(movie);
                break;
            }
        }

        if (!found) {
            return false;
        } 
        
        try {
            JSONParser parser = new JSONParser();
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

    


}