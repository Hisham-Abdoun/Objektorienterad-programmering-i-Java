package övningsuppgift2;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class MemberHandler {

    private List<Member> members = new ArrayList<>();


    public void loadMembers(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // Hoppa över rubrikraden
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String name = data[0];
                String address = data[1];
                String email = data[2];
                String personalNumber = data[3];
                LocalDate membershipDate = LocalDate.parse(data[4]);
                LocalDate lastPaymentDate = LocalDate.parse(data[5]);
                String membershipType = data[6];

                members.add(new Member(name, address, email, personalNumber,
                        membershipDate, lastPaymentDate, membershipType));
            }
        } catch (IOException e) {
            System.out.println("Fel vid inläsning av filen: " + e.getMessage());
        }
    }


    public Member findMember(String input) {
        for (Member m : members) {
            if (m.getName().equalsIgnoreCase(input) || m.getPersonalNumber().equals(input)) {
                return m;
            }
        }
        return null;
    }


    public void logWorkout(Member m, String filePath) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(m.getName() + ";" + m.getPersonalNumber() + ";" + LocalDate.now() + "\n");
        } catch (IOException e) {
            System.out.println("Kunde inte skriva till PT-loggfilen.");
        }
    }
}
