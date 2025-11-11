package övningsuppgift2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberReader {

    public static List<Member> readMembersFromFile(String filePath) {
        List<Member> members = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true; // hoppa över header-raden
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length != 7) {
                    System.out.println("Felaktig rad i filen: " + line);
                    continue;
                }

                String name = parts[0];
                String address = parts[1];
                String email = parts[2];
                String personalNumber = parts[3];
                LocalDate membershipDate = LocalDate.parse(parts[4]);
                LocalDate lastPaymentDate = LocalDate.parse(parts[5]);
                String membershipType = parts[6];

                Member member = new Member(name, address, email, personalNumber,
                        membershipDate, lastPaymentDate, membershipType);
                members.add(member);
            }
        } catch (IOException e) {
            System.out.println("Fel vid läsning av fil: " + e.getMessage());
        }

        return members;
    }
    File file = new File("src/customers.txt");


    public static List<Member> readMembers(String s) {
        return List.of();
    }
}

