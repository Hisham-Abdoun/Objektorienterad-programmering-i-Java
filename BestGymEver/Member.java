package BestGymEver;

import java.time.LocalDate;

public class Member {
    private String name;
    private String personalNumber;
    private LocalDate lastPayment;
    private String membershipType;

    public Member(String name, String personalNumber, LocalDate lastPayment, String membershipType) {
        this.name = name;
        this.personalNumber = personalNumber;
        this.lastPayment = lastPayment;
        this.membershipType = membershipType;
    }

    public String getName() {
        return name;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public LocalDate getLastPayment() {
        return lastPayment;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public boolean isActive() {
        return lastPayment.isAfter(LocalDate.now().minusYears(1));
    }
}