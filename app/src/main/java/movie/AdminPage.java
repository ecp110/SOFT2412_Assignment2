import java.util.*;
import java.io.*;

public class AdminPage extends Page {

    // User details
    private User user;

    public AdminPage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, User user) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation);
        this.user = user;
}