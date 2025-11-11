package övningsuppgift2;

import java.util.Scanner;

public class BestGymEver {
    public static void main(String[] args) {
        övningsuppgift2.MemberHandler handler = new övningsuppgift2.MemberHandler();
        handler.loadMembers("customers.txt");

        Scanner input = new Scanner(System.in);
        System.out.print("Ange namn eller personnummer: ");
        String userInput = input.nextLine();

        Member m = handler.findMember(userInput);

        if (m == null) {
            System.out.println("Personen finns inte i systemet (obehörig).");
        } else if (m.isActive()) {
            System.out.println(m.getName() + " är en nuvarande medlem (" + m.getMembershipType() + ").");
            handler.logWorkout(m, "PTlog.txt");
        } else {
            System.out.println(m.getName() + " är en före detta medlem (senast betalad: " + m.getLastPaymentDate() + ").");
        }

        input.close();
    }
}