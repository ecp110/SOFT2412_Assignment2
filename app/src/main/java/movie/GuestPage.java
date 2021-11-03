package movie;

public class GuestPage extends Page {
    public GuestPage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, String pagePath) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation, pagePath);
        this.parseAll();
    }

    public String displayInitial() {
        return this.parseTxt("/main.txt", 0) + "\n" +
        "\n------------------------------------------------------------------------" + "\n" +
        this.displayMovies()+
        "------------------------------------------------------------------------" + "\n";
    }

    /**
     * Required abstract function
     * returns HomePage for when page gets cancelled
     */

    public HomePage cancel() {
        Engine.logCancellation(null, "user cancellation", this.cancellationPath);
        return new HomePage(
            this.MOVIE_LOCATION, this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION, this.PAGE_PATH
            );
    }
}