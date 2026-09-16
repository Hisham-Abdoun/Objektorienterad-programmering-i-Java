package BestGymEver;

import java.util.Scanner;

public class BestGymEver {

    public static void main(String[] args) {
        String customersFilePath = "BestGymEver/customers.txt";
        String ptLogFilePath = "BestGymEver/PTlog.txt";
        MemberHandler handler = new MemberHandler(customersFilePath);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ange namn eller personnummer: ");
        String input = scanner.nextLine();

        Member member = handler.findMember(input);

        if (member == null) {
            System.out.println("Personen finns inte i systemet (obehörig).");
        } else if (member.isActive()) {
            System.out.println("Kunden är en nuvarande medlem.");
            System.out.println("Medlemsnivå: " + member.getMembershipType());
            handler.logWorkout(member, ptLogFilePath);
            System.out.println("Träningspass har registrerats i PT-filen.");
        } else {
            System.out.println("Kunden är en före detta medlem (årsavgiften utgången).");
        }

        scanner.close();
    }
}