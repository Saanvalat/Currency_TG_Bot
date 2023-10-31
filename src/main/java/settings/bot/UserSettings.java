package settings.bot;
public class UserSettings {
    private int bank;
    private boolean usd;
    private boolean eur;
    private int decimalPoints;
    private int notificationTime;
    public UserSettings(int bank, boolean usd, boolean eur, int decimalPoints, int notificationTime) {
        this.bank = bank;
        this.usd = usd;
        this.eur = eur;
        this.decimalPoints = decimalPoints;
        this.notificationTime = notificationTime;
    }
    public int getBank() {
        return bank;
    }
    public void setBank(int bank) {
        this.bank = bank;
    }
    public boolean getUsd() {
        return usd;
    }
    public void setEur(boolean eur) {
        this.eur = eur;
    }
    public boolean getEur() {
        return eur;
    }
    public void setUsd(boolean usd) {
        this.usd = usd;
    }
    public int getDecimalPoints() {
        return decimalPoints;
    }
    public void setDecimalPoints(int decimalPoints) {
        this.decimalPoints = decimalPoints;
    }
    public int getNotificationTime() {
        return notificationTime;
    }
    public void setNotificationTime(int notificationTime) {
        this.notificationTime = notificationTime;
    }
}
