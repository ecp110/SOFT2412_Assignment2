public class User {

    // Attributes
    private String name;
    private int password;
    private boolean status;

    public User (String name, int password, String status) {
        this.name = name;
        this.password = password;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public int getPassword() {
        return this.password;
    }

    public boolean getType() {
        return this.status;
    }
}