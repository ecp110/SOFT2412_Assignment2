package movie;

public class CreditCard  {

    String name;
    String number;

    public CreditCard (String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return this.number;
    }

}