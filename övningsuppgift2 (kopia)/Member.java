package övningsuppgift2;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Member {
    private String name;
    private String address;
    private String email;
    private String personalNumber;
    private LocalDate membershipDate;
    private LocalDate lastPaymentDate;
    private String membershipType;


    public Member(String name, String address, String email, String personalNumber) {}
    //
    public Member(String name, String address, String email, String personalNumber,
                  LocalDate membershipDate, LocalDate lastPaymentDate, String membershipType) {
        this.name = name.trim();
        this.address = address.trim();
        this.email = email.trim();
        this.personalNumber = personalNumber.trim();
        this.membershipDate = membershipDate;
        this.lastPaymentDate = lastPaymentDate;
        this.membershipType = membershipType.trim();
    }


    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPersonalNumber() { return personalNumber; }
    public LocalDate getMembershipDate() { return membershipDate; }
    public LocalDate getLastPaymentDate() { return lastPaymentDate; }
    public String getMembershipType() { return membershipType; }


    public boolean isActive() {
        return lastPaymentDate.isAfter(LocalDate.now().minusYears(1));
    }
    List<Member> members = MemberReader.readMembers("/Users/hishamabdoun/IdeaProjects/H1/src/övninguppgift2/customers.txt");



    @Override
    public String toString() {
        return name + " (" + personalNumber + "), Senaste betalning: " +
                lastPaymentDate + ", Typ: " + membershipType;
    }
}

