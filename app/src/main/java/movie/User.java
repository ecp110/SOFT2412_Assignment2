package movie;

public class User {

    // Attributes
    private String name;
    private String password;
    private String status;

    public User (String name, String password, String status) {
        this.name = name;
        this.password = password;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getType() {
        return this.status;
    }

    public boolean isAdmin() {
        if (this.status.equals("staff") || this.status.equals("manager")) {
            return true;
        }

        return false;
    }
}