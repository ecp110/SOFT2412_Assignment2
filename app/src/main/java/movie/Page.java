public abstract class Page {
    // Boolean status
    private boolean loggedIn = false;
    private boolean admin = false;

    // Database locations
    private String movieLocation;
    private String cinemasLocation;
    private String creditCardLocation;
    private String giftCardLocation;
    private String usersLocation;

    public Page(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation) {
        this.movieLocation = movieLocation;
        this.cinemasLocation = cinemasLocation;
        this.creditCardLocation = creditCardLocation;
        this.giftCardLocation = giftCardLocation;
        this.usersLocation = usersLocation;
    }

    /**
     * Logs anyone in
     * If they are a customer, take them to customer page
     * If they are an admin, take them to admin page
     */
    public boolean logIn(String username, String password) {
        if (username == null || password == null) {
            return false
        } else if (username.equals("") || password.equals("")) {
            return false;
        }
        
        // TODO: Write login function for users to login 
        return true;
    }

    public void logOut() {
        this.loggedIn = false;
        this.admin = false;
    }

    // GETTERS

    /**
     * Returns if any user is logged in or not
     */
    public boolean loggedIn() {
        return this.loggedIn;
    }

    /**
     * Returns if this is an admin page or not
     */
    public boolean isAdmin() {
        return this.admin;
    }


}