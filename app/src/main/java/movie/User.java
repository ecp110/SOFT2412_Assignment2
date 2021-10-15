public class User {

    // Attributes
    private String name;
    private int password;
    private String status;

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