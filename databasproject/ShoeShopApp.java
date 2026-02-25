package main;

import database.DatabaseConnection;
import model.Kund;

import java.sql.*;
import java.util.*;

public class ShoeShopApp {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection;

    public static void main(String[] args) {
        printHeader();

        try {

            connection = DatabaseConnection.getConnection();
            System.out.println("✓ Ansluten till databasen\n");


            Kund kund = login();
            if (kund == null) {
                System.out.println("\n✗ Felaktigt användarnamn eller lösenord.");
                System.out.println("Programmet avslutas.");
                return;
            }

            System.out.println("\n✓ Välkommen " + kund.getFornamn() + "!");

            // Huvudmeny
            boolean running = true;
            while (running) {
                printMenu();
                int choice = getIntInput();

                switch (choice) {
                    case 1:
                        visaAllaProdukter();
                        break;
                    case 2:
                        laggTillProdukt(kund);
                        break;
                    case 3:
                        visaVarukorg(kund);
                        break;
                    case 4:
                        running = false;
                        System.out.println("\n✓ Tack för ditt besök! Välkommen åter!");
                        break;
                    default:
                        System.out.println("\n✗ Ogiltigt val. Välj 1-4.");
                }
            }

        } catch (SQLException e) {
            System.err.println("\n✗ Databasfel: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private static void printHeader() {
        System.out.println("=");
        System.out.println("   Välkommen till Shoe Shop Online!");
        System.out.println("= \n");
    }

    private static void printMenu() {
        System.out.println("\n =");
        System.out.println("HUVUDMENY");
        System.out.println("=");
        System.out.println("1. Visa alla produkter i lager");
        System.out.println("2. Lägg till produkt i varukorgen");
        System.out.println("3. Visa min varukorg");
        System.out.println("4. Avsluta");
        System.out.print("\nVälj alternativ (1-4): ");
    }


    private static Kund login() throws SQLException {
        System.out.println("- LOGIN -");
        System.out.print("Användarnamn: ");
        String username = scanner.nextLine().trim();

        System.out.print("Lösenord: ");
        String password = scanner.nextLine().trim();

        String sql = "SELECT * FROM Kund WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Kund(
                        rs.getInt("kund_id"),
                        rs.getString("fornamn"),
                        rs.getString("efternamn"),
                        rs.getString("ort"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        }

        return null;
    }


    private static void visaAllaProdukter() throws SQLException {
        System.out.println("\n =");
        System.out.println("TILLGÄNGLIGA PRODUKTER");
        System.out.println("=");

        String sql = "SELECT marke, farg, storlek, pris, lagerantal " +
                "FROM Produkt WHERE lagerantal > 0 " +
                "ORDER BY marke, farg, storlek";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-15s %-12s %-8s %-10s %-8s%n",
                    "Märke", "Färg", "Storlek", "Pris (kr)", "I lager");
            System.out.println("-");

            boolean hasProducts = false;
            while (rs.next()) {
                hasProducts = true;
                System.out.printf("%-15s %-12s %-8d %-10.2f %-8d%n",
                        rs.getString("marke"),
                        rs.getString("farg"),
                        rs.getInt("storlek"),
                        rs.getDouble("pris"),
                        rs.getInt("lagerantal")
                );
            }

            if (!hasProducts) {
                System.out.println("Inga produkter tillgängliga.");
            }
        }
    }


    private static void laggTillProdukt(Kund kund) throws SQLException {
        System.out.println("\n =");
        System.out.println("LÄGG TILL PRODUKT I VARUKORGEN");
        System.out.println("=");


        System.out.println("\n - Steg 1: Välj märke -");
        List<String> marken = getDistinctValues(
                "SELECT DISTINCT marke FROM Produkt WHERE lagerantal > 0 ORDER BY marke"
        );

        if (marken.isEmpty()) {
            System.out.println("✗ Inga produkter i lager.");
            return;
        }

        String valdMarke = selectFromList(marken, "märke");
        if (valdMarke == null) return;


        System.out.println("\n - Steg 2: Välj färg -");
        List<String> farger = getDistinctValues(
                "SELECT DISTINCT farg FROM Produkt " +
                        "WHERE marke = '" + valdMarke + "' AND lagerantal > 0 ORDER BY farg"
        );

        if (farger.isEmpty()) {
            System.out.println("✗ Inga färger tillgängliga.");
            return;
        }

        String valdFarg = selectFromList(farger, "färg");
        if (valdFarg == null) return;


        System.out.println("\n--- Steg 3: Välj storlek ---");
        List<String> storlekar = getDistinctValues(
                "SELECT DISTINCT storlek FROM Produkt " +
                        "WHERE marke = '" + valdMarke + "' AND farg = '" + valdFarg + "' " +
                        "AND lagerantal > 0 ORDER BY storlek"
        );

        if (storlekar.isEmpty()) {
            System.out.println("✗ Inga storlekar tillgängliga.");
            return;
        }

        String valdStorlek = selectFromList(storlekar, "storlek");
        if (valdStorlek == null) return;


        int produktId = getProduktId(valdMarke, valdFarg, Integer.parseInt(valdStorlek));

        if (produktId == -1) {
            System.out.println("\n ✗ Produkten kunde inte hittas.");
            return;
        }


        callAddToCart(kund.getKundId(), produktId, valdMarke, valdFarg, valdStorlek);
    }


    private static void callAddToCart(int kundId, int produktId,
                                      String marke, String farg, String storlek) {
        System.out.println("\n - Lägger till i varukorgen -");

        String callSql = "{CALL AddToCart(?, ?, ?)}";

        try (CallableStatement stmt = connection.prepareCall(callSql)) {
            stmt.setInt(1, kundId);
            stmt.setNull(2, Types.INTEGER);  // NULL = hitta/skapa aktiv beställning
            stmt.setInt(3, produktId);

            boolean hasResults = stmt.execute();

            if (hasResults) {
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    String status = rs.getString("Status");
                    String message = rs.getString("Message");

                    if ("SUCCESS".equals(status)) {
                        System.out.println("\n✓ " + message);
                        System.out.println("  Produkt: " + marke + " " + farg + " storlek " + storlek);
                    } else {
                        System.out.println("\n ✗ " + message);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("\n ✗ Fel vid tillägg: " + e.getMessage());
        }
    }


    private static void visaVarukorg(Kund kund) throws SQLException {
        System.out.println("\n =");
        System.out.println("DIN VARUKORG");
        System.out.println("=");

        String sql = "SELECT p.marke, p.farg, p.storlek, br.antal, " +
                "br.pris_vid_kop, (br.antal * br.pris_vid_kop) AS totalt " +
                "FROM Bestallning b " +
                "JOIN Bestallningsrad br ON b.bestallning_id = br.bestallning_id " +
                "JOIN Produkt p ON br.produkt_id = p.produkt_id " +
                "WHERE b.kund_id = ? AND b.status = 'ACTIVE'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, kund.getKundId());
            ResultSet rs = stmt.executeQuery();

            double grandTotal = 0;
            boolean hasItems = false;

            System.out.printf("%-15s %-12s %-8s %-6s %-10s %-10s%n",
                    "Märke", "Färg", "Storlek", "Antal", "Pris", "Totalt");
            System.out.println("-");

            while (rs.next()) {
                hasItems = true;
                double totalt = rs.getDouble("totalt");
                grandTotal += totalt;

                System.out.printf("%-15s %-12s %-8d %-6d %-10.2f %-10.2f%n",
                        rs.getString("marke"),
                        rs.getString("farg"),
                        rs.getInt("storlek"),
                        rs.getInt("antal"),
                        rs.getDouble("pris_vid_kop"),
                        totalt
                );
            }

            if (!hasItems) {
                System.out.println("Din varukorg är tom.");
            } else {
                System.out.println("-");
                System.out.printf("TOTALT: %.2f kr%n", grandTotal);
            }
        }
    }


    private static int getProduktId(String marke, String farg, int storlek) throws SQLException {
        String sql = "SELECT produkt_id FROM Produkt " +
                "WHERE marke = ? AND farg = ? AND storlek = ? AND lagerantal > 0";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, marke);
            stmt.setString(2, farg);
            stmt.setInt(3, storlek);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("produkt_id");
            }
        }

        return -1;
    }


    private static List<String> getDistinctValues(String sql) throws SQLException {
        List<String> values = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                values.add(rs.getString(1));
            }
        }
        return values;
    }


    private static String selectFromList(List<String> items, String itemType) {
        if (items.isEmpty()) {
            System.out.println("Inga alternativ tillgängliga.");
            return null;
        }

        System.out.println("Tillgängliga " + itemType + ":");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }

        System.out.print("\nVälj " + itemType + " (1-" + items.size() +
                ") eller 0 för att avbryta: ");
        int choice = getIntInput();

        if (choice == 0) {
            System.out.println("Avbrutet.");
            return null;
        }

        if (choice < 1 || choice > items.size()) {
            System.out.println("✗ Ogiltigt val.");
            return null;
        }

        return items.get(choice - 1);
    }


    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("✗ Ange ett giltigt nummer: ");
            }
        }
    }


    private static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}