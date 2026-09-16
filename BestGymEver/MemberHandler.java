package BestGymEver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MemberHandler {
    private List<Member> memberList;

    public MemberHandler(String filePath) {
        MemberReader reader = new MemberReader();
        this.memberList = reader.readMembersFromFile(filePath);
    }

    public Member findMember(String input) {
        String cleanInput = input.trim().replace("-", "");

        for (Member m : memberList) {
            String cleanPersonNum = m.getPersonalNumber().replace("-", "");
            if (m.getName().equalsIgnoreCase(input.trim()) || cleanPersonNum.equals(cleanInput)) {
                return m;
            }
        }
        return null;
    }

    public void logWorkout(Member member, String logFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(member.getName() + "; " + member.getPersonalNumber() + "; " + LocalDate.now());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Fel vid skrivning till PT-fil: " + e.getMessage());
        }
    }
}