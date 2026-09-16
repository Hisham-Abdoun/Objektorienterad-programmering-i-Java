package BestGymEver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberReader {

    public static List<Member> readMembersFromFile(String filePath) {        List<Member> members = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = line.split(";");

                if (tokens.length >= 7) {
                    String name = tokens[0].trim();
                    String personalNumber = tokens[3].trim();

                    LocalDate lastPayment = LocalDate.parse(tokens[5].trim());
                    String membershipType = tokens[6].trim();

                    members.add(new Member(name, personalNumber, lastPayment, membershipType));
                }
            }
        } catch (IOException e) {
            System.out.println("Fel vid inläsning av filen: " + e.getMessage());
        }

        return members;
    }
}