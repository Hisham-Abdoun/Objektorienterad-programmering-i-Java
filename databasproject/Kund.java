package model;
public class Kund {
    private int kundId;
    private String fornamn;
    private String efternamn;
    private String ort;
    private String username;
    private String password;


    public Kund() {
    }

    public Kund(int kundId, String fornamn, String efternamn, String ort,
                String username, String password) {
        this.kundId = kundId;
        this.fornamn = fornamn;
        this.efternamn = efternamn;
        this.ort = ort;
        this.username = username;
        this.password = password;
    }


    public int getKundId() { return kundId; }
    public String getFornamn() { return fornamn; }
    public String getEfternamn() { return efternamn; }
    public String getOrt() { return ort; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }


    public void setKundId(int kundId) { this.kundId = kundId; }
    public void setFornamn(String fornamn) { this.fornamn = fornamn; }
    public void setEfternamn(String efternamn) { this.efternamn = efternamn; }
    public void setOrt(String ort) { this.ort = ort; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }


    public String getFullName() {
        return fornamn + " " + efternamn;
    }

    @Override
    public String toString() {
        return "Kund{id=" + kundId + ", namn='" + getFullName() +
                "', ort='" + ort + "'}";
    }
}