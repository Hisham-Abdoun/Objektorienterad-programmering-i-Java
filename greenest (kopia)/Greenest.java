package greenest;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class Greenest {


    private static final String TITEL = "Greenest Växthotell";
    private static final String FRAGA = "Vilken växt ska få vätska?";
    private static final String FEL = "Växten finns inte på hotellet.";

    public static void main(String[] args) {
        List<Vaxt> vaxter = skapaVaxter();

        while (true) {
            String input = JOptionPane.showInputDialog(null, FRAGA, TITEL, JOptionPane.QUESTION_MESSAGE);
            if (input == null) break;

            Vaxt vald = hittaVaxt(input.trim(), vaxter);
            if (vald != null) {
                double mangd = vald.beraknaVatska();
                String typ = vald.getVatskeTyp();
                String meddelande = vald.getNamn() + " ska få " +
                        String.format("%.2f", mangd) + " liter " + typ + " per dag.";
                JOptionPane.showMessageDialog(null, meddelande, TITEL, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, FEL, TITEL, JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private static List<Vaxt> skapaVaxter() {
        List<Vaxt> lista = new ArrayList<>();

        lista.add(new Kaktus("Igge", 0.20));
        lista.add(new Kaktus.Palm("Laura", 5.0));
        lista.add(new KottatandeVaxt("Meatloaf", 0.7));
        lista.add(new Kaktus.Palm("Olof", 1.0));
        return lista;
    }

    private static Vaxt hittaVaxt(String namn, List<Vaxt> lista) {
        for (Vaxt v : lista) {
            if (v.getNamn().equalsIgnoreCase(namn)) {
                return v;
            }
        }
        return null;
    }
}
