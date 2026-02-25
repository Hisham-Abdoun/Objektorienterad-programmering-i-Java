package model;


public class Produkt {
    private int produktId;
    private int storlek;
    private double pris;
    private String marke;
    private String farg;
    private int lagerantal;


    public Produkt() {
    }

    public Produkt(int produktId, int storlek, double pris, String marke,
                   String farg, int lagerantal) {
        this.produktId = produktId;
        this.storlek = storlek;
        this.pris = pris;
        this.marke = marke;
        this.farg = farg;
        this.lagerantal = lagerantal;
    }


    public int getProduktId() { return produktId; }
    public int getStorlek() { return storlek; }
    public double getPris() { return pris; }
    public String getMarke() { return marke; }
    public String getFarg() { return farg; }
    public int getLagerantal() { return lagerantal; }


    public void setProduktId(int produktId) { this.produktId = produktId; }
    public void setStorlek(int storlek) { this.storlek = storlek; }
    public void setPris(double pris) { this.pris = pris; }
    public void setMarke(String marke) { this.marke = marke; }
    public void setFarg(String farg) { this.farg = farg; }
    public void setLagerantal(int lagerantal) { this.lagerantal = lagerantal; }


    public String getDescription() {
        return marke + " " + farg + " storlek " + storlek;
    }

    public boolean isInStock() {
        return lagerantal > 0;
    }

    @Override
    public String toString() {
        return "Produkt{" + marke + " " + farg + " " + storlek +
                ", pris=" + pris + " kr, lager=" + lagerantal + "}";
    }
}