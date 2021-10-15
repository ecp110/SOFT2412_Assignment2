public class User {

    // Attributes
    private String name;
    private int password;
    private boolean isAdmin;

    public User (String name, int password, boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return this.name;
    }

    public int getPassword() {
        return this.password;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }
}