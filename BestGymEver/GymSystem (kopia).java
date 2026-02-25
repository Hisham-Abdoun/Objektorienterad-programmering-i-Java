/*package övningsuppgift2;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GymSystem {

    public static void main(String[] args) {
        String filePath = "customers.txt";

        List<Member> members = övningsuppgift2.MemberReader.readMembersFromFile(filePath); // anta att du har denna metod

        Scanner input = new Scanner(System.in);
        System.out.print("Ange namn eller personnummer: ");
        String search = input.nextLine().trim();

        Member found = null;

        for (Member m : members) {

            String name = m.getName().trim().toLowerCase();
            String pn = m.getPersonalNumber().replace("-", "").trim();

            String searchLower = search.toLowerCase();
            String searchPn = search.replace("-", "").trim();

            if (name.equals(searchLower) || pn.equals(searchPn)) {
                found = m;
                break;
            }
        }

        if (found == null) {
            System.out.println("🚫 Personen finns inte i systemet (obehörig).");
        } else if (found.isActive()) {
            System.out.println("✅ " + found.getName() + " är en nuvarande medlem (" +
                    found.getMembershipType() + ").");


            try (FileWriter writer = new FileWriter("src/övninguppgift2/training_log.txt", true)) {
                LocalDate today = LocalDate.now();
                writer.write(found.getName() + ";" + found.getPersonalNumber() + ";" + today + "\n");
            } catch (IOException e) {
                System.out.println("Fel vid skrivning till PT-fil: " + e.getMessage());
            }

        } else {
            System.out.println("⚠️ " + found.getName() + " är en före detta medlem.");
        }
    }
}

*/

