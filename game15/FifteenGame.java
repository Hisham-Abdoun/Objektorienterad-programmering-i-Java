package game15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class FifteenGame extends JFrame implements ActionListener {

    private JButton[][] buttons = new JButton[4][4];
    private int emptyRow = 3;
    private int emptyCol = 3;

    public FifteenGame() {
        setTitle("15-spelet");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 4, 4, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // skapa knappen
        int number = 1;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                JButton btn;
                if (r == 3 && c == 3) {
                    btn = new JButton("");
                } else {
                    btn = new JButton(String.valueOf(number));
                    number++;
                }
                btn.setFont(new Font("SansSerif", Font.BOLD, 24));
                btn.addActionListener(this);
                buttons[r][c] = btn;
                panel.add(btn);
            }
        }

        JButton newGameButton = new JButton("Nytt spel");
        newGameButton.addActionListener(e -> {
            shuffleTiles();
            // Om spelet av misstag är löst direkt efter, blanda igen
            if (isSolved()) shuffleTiles();
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.add(newGameButton);

        add(panel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        pack(); // anpassa fönstrets storlek efter innehåll
        setLocationRelativeTo(null); // centrera på skärmen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        // Hitta position för klickad knapp
        int clickedRow = -1, clickedCol = -1;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (buttons[r][c] == clicked) {
                    clickedRow = r;
                    clickedCol = c;
                }
            }
        }

        // Om index inte hittades, returnera
        if (clickedRow == -1) return;

        // Kontrollera om klickad knapp ligger intill tom ruta
        boolean adjacent = (Math.abs(clickedRow - emptyRow) == 1 && clickedCol == emptyCol) ||
                (Math.abs(clickedCol - emptyCol) == 1 && clickedRow == emptyRow);

        if (adjacent) {
            // Flytta text från klickad till tom ruta och töm klickad
            buttons[emptyRow][emptyCol].setText(clicked.getText());
            clicked.setText("");

            // uppdatera tom platsens koordinater
            emptyRow = clickedRow;
            emptyCol = clickedCol;

            // kontrollera vinst
            if (isSolved()) {
                JOptionPane.showMessageDialog(this, "🎉 Grattis, du vann!");
            }
        }
    }

    private void shuffleTiles() {
        // Skapa lista och blanda
        java.util.List<String> tiles = new ArrayList<>();
        for (int i = 1; i <= 15; i++) tiles.add(String.valueOf(i));
        tiles.add(""); // tom ruta
        Collections.shuffle(tiles);

        // Sätt tillbaka på knapparna
        Iterator<String> it = tiles.iterator();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                String value = it.next();
                buttons[r][c].setText(value);
                if (value.equals("")) {
                    emptyRow = r;
                    emptyCol = c;
                }
            }
        }
    }

    private boolean isSolved() {
        int expected = 1;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                String text = buttons[r][c].getText();
                if (r == 3 && c == 3) {
                    return text.equals("");
                }
                if (!text.equals(String.valueOf(expected))) return false;
                expected++;
            }
        }
        return true;
    }
}
