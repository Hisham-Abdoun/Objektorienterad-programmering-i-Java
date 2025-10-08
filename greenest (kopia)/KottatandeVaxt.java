package greenest;


import greenest.Vaxt;

public class KottatandeVaxt extends Vaxt {

    private static final double BAS_LITER = 0.1;
    private static final double PER_METER = 0.2;

    public KottatandeVaxt(String namn, double langdMeter) {
        super(namn, langdMeter, VatskeTyp.PROTEINDRYCK);
    }

    @Override
    public double beraknaVatska() {
        return BAS_LITER + PER_METER * getLangdMeter();
    }
}
