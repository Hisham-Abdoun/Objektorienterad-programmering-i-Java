package greenest;


public abstract class Vaxt implements VatskeBehov {

    private final String namn;
    private final double langdMeter;
    private final VatskeTyp vatskeTyp;


    public Vaxt(String namn, double langdMeter, VatskeTyp vatskeTyp) {
        this.namn = namn;
        this.langdMeter = langdMeter;
        this.vatskeTyp = vatskeTyp;
    }


    public String getNamn() {
        return namn;
    }

    public double getLangdMeter() {
        return langdMeter;
    }

    public VatskeTyp getVatskeTypEnum() {
        return vatskeTyp;
    }

    @Override
    public String getVatskeTyp() {
        return vatskeTyp.getNiceName();
    }
}

