package greenest;


public class Kaktus extends Vaxt {

    private static final double LITER_PER_DAY = 0.02; // 2 cl = 0.02 liter

    public Kaktus(String namn, double langdMeter) {
        super(namn, langdMeter, VatskeTyp.MINERALVATTEN);
    }

    @Override
    public double beraknaVatska() {
        return LITER_PER_DAY;
    }

    public static class Palm extends Vaxt {


        private static final double LITER_PER_METER = 0.5;

        public Palm(String namn, double langdMeter) {
            super(namn, langdMeter, VatskeTyp.KRANVATTEN);
        }

        @Override
        public double beraknaVatska() {
            return LITER_PER_METER * getLangdMeter();
        }
    }
}

