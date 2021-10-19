public class GuestPage extends Page {
    public GuestPage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation);
        this.homePageString = "../pages/guest/main.txt";
    }

    public String displayInitial() {
        String output = "";

        try {
            Scanner sc = new Scanner(new File(this.homePageString));

            // Personalisation at start to user logged in
            output += sc.nextLine();
            output += " ";
            output += user.getName();
            output += "!";
            output += "\n";

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        output += this.parseTxt(this.homePageString, 1);
        output += this.listMovies();
        return output;
    }

    /**
     * Required abstract function
     * returns HomePage for when page gets cancelled
     */

    public HomePage cancel() {
        this.user = null;
        return new HomePage(
            this.MOVIE_LOCATION, this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION);
    }

    /**
      * Registers a new user
      * If user alreay exists, return 0
      * If user created, return 1
      * If error, return -1
      * @param username
      * @param password
      * @return
      */
      public int register(String username, String password) {
        // Error checking
        if (username == null) {
            return -1;
        } else if (username.equals("")) {
            return -1;
        }

        if (password == null) {
            return -1;
        } else if (password.equals("")) {
            return -1;
        }

        // Checks doesn't exist already
        for (User user : this.users) {
            if (user.getName().equals(username)) {
                return 0;
            }
        }
        
        // Creates new user and adds to page list
        User newUser = new User(username, password, "customer");
        this.users.add(newUser);

        // Adds new user to database
        //TODO
        return -1;
     }
}