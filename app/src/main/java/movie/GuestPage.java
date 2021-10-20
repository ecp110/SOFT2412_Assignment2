package movie;

public class GuestPage extends Page {
    public GuestPage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, String pagePath) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation, pagePath);
    }

    public String displayInitial() {
        return this.parseTxt("/main.txt", 0) + "\n" + this.displayMovies();
    }

    /**
     * Required abstract function
     * returns HomePage for when page gets cancelled
     */

    public HomePage cancel() {
        return new HomePage(
            this.MOVIE_LOCATION, this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION, this.PAGE_PATH
            );
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