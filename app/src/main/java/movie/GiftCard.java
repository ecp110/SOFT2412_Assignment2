package movie;

public class GiftCard  {

    String number;
    boolean redeemed;
    Calendar expiry;
    Calendar issue;

    public GiftCard (String number, boolean redeemed, Calendar expiry, Calendar issue) {
        this.number = number;
        this.redeemed = redeemed;
        this.expiry = expiry;
        this.issue = issue;
    }

    public boolean isRedeemed() {
        return this.redeemed;
    }

    public String getNumber() {
        return this.number;
    }

    public Calendar getExpiry() {
        return this.expiry;
    }

    public Calendar getIssue() {
        return this.issue;
    }

    public boolean isExpired() {
        return !this.expiry.isBeforeNow();
    }

    public String toString() {
        return this.number + " | Redeemed: " + String.valueOf(this.redeemed) + " Issue: " + this.issue.getStringShort() + " Expiry: " + this.expiry.getStringShort();
    }

}