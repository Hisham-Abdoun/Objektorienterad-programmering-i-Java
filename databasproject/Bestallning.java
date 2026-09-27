package model;

import java.sql.Date;


public class Bestallning {
    private int bestallningId;
    private int kundId;
    private Date datum;
    private String status;


    public Bestallning() {
    }

    public Bestallning(int bestallningId, int kundId, Date datum, String status) {
        this.bestallningId = bestallningId;
        this.kundId = kundId;
        this.datum = datum;
        this.status = status;
    }


    public int getBestallningId() {
        return bestallningId;
    }

    public int getKundId() {
        return kundId;
    }

    public Date getDatum() {
        return datum;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setBestallningId(int bestallningId) {
        this.bestallningId = bestallningId;
    }

    public void setKundId(int kundId) {
        this.kundId = kundId;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    public boolean isPaid() {
        return "BETALD".equals(status);
    }

    @Override
    public String toString() {
        return "Bestallning{id=" + bestallningId + ", datum=" + datum +
                ", status=" + status + "}";
    }
}

