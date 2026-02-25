package database;


public class Bestallningsrad {
    private int bestallningId;
    private int produktId;
    private int antal;
    private double prisVidKop;


    public Bestallningsrad() {
    }

    public Bestallningsrad(int bestallningId, int produktId, int antal, double prisVidKop) {
        this.bestallningId = bestallningId;
        this.produktId = produktId;
        this.antal = antal;
        this.prisVidKop = prisVidKop;
    }


    public int getBestallningId() { return bestallningId; }
    public int getProduktId() { return produktId; }
    public int getAntal() { return antal; }
    public double getPrisVidKop() { return prisVidKop; }


    public void setBestallningId(int bestallningId) { this.bestallningId = bestallningId; }
    public void setProduktId(int produktId) { this.produktId = produktId; }
    public void setAntal(int antal) { this.antal = antal; }
    public void setPrisVidKop(double prisVidKop) { this.prisVidKop = prisVidKop; }


    public double getTotalPrice() {
        return antal * prisVidKop;
    }

    @Override
    public String toString() {
        return "Bestallningsrad{produktId=" + produktId + ", antal=" + antal +
                ", pris=" + prisVidKop + " kr, totalt=" + getTotalPrice() + " kr}";
    }
}
