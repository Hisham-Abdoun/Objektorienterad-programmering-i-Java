package game15;

import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        // Kör Swing på Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new FifteenGame());
    }
}

