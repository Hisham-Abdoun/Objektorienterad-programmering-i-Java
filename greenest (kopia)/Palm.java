package greenest;


public class Palm extends Vaxt {


    private static final double LITER_PER_METER = 0.5;

    public Palm(String namn, double langdMeter) {
        super(namn, langdMeter, VatskeTyp.KRANVATTEN);
    }

    @Override
    public double beraknaVatska() {
        return LITER_PER_METER * getLangdMeter();
    }
}
