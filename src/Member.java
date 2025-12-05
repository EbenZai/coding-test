public class Member {
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private int registrationYear;
    private String membershipType;
    private static int totalMembers = 0;

    public Member() {
        totalMembers++;
        this.memberId = String.format("MBR%03d", totalMembers);
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.registrationYear = 2025;
        this.membershipType = "Silver";
    }

    public Member(String name, String email, String phoneNumber, int registrationYear, String membershipType) {
        totalMembers++;
        this.memberId = String.format("MBR%03d", totalMembers);
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setRegistrationYear(registrationYear);
        setMembershipType(membershipType);
    }

    public void displayInfo() {
        System.out.println("[" + memberId + "] " + name);
        System.out.println("Email         : " + email);
        System.out.println("Phone         : " + phoneNumber);
        System.out.println("Membership    : " + membershipType + " " + getMembershipStars());
        System.out.println("Tahun Daftar  : " + registrationYear);
        System.out.println("Durasi Member : " + getMembershipDuration() + " tahun");
        System.out.println("Batas Pinjam  : " + getMaxBorrowLimit() + " buku");
        System.out.println("Diskon Denda  : " + (int) (getMembershipDiscount() * 100) + "%");
        System.out.println("--------------------------------------------");
    }

    public void upgradeMembership(String newType) {
        if (!newType.equals("Silver") && !newType.equals("Gold") && !newType.equals("Platinum")) {
            System.out.println("✗ Error: Tipe membership tidak valid!");
            return;
        }

        if (this.membershipType.equals("Silver") && (newType.equals("Gold") || newType.equals("Platinum"))) {
            this.membershipType = newType;
            System.out.println("✓ " + name + " berhasil di-upgrade ke " + newType + "!");
        } else if (this.membershipType.equals("Gold") && newType.equals("Platinum")) {
            this.membershipType = newType;
            System.out.println("✓ " + name + " berhasil di-upgrade ke " + newType + "!");
        } else if (this.membershipType.equals(newType)) {
            System.out.println("✗ Error: Sudah memiliki membership " + newType);
        } else {
            System.out.println("✗ Error: Upgrade tidak valid! (Silver → Gold → Platinum)");
        }
    }

    public int getMaxBorrowLimit() {
        switch (this.membershipType) {
            case "Platinum":
                return 10;
            case "Gold":
                return 7;
            case "Silver":
                return 5;
            default:
                return 3;
        }
    }

    public int getMembershipDuration() {
        return 2025 - this.registrationYear;
    }

    public double getMembershipDiscount() {
        switch (this.membershipType) {
            case "Platinum":
                return 0.50;
            case "Gold":
                return 0.30;
            case "Silver":
                return 0.10;
            default:
                return 0.0;
        }
    }

    private String getMembershipStars() {
        switch (this.membershipType) {
            case "Platinum":
                return "⭐⭐⭐";
            case "Gold":
                return "⭐⭐";
            case "Silver":
                return "⭐";
            default:
                return "";
        }
    }


    public static int getTotalMembers() {
        return totalMembers;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("✗ Error: Nama tidak boleh kosong");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("✗ Error: Email tidak valid (harus mengandung @ dan .)");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 10 || phoneNumber.length() > 13) {
            throw new IllegalArgumentException("✗ Error: Nomor telepon harus 10-13 digit");
        }
        this.phoneNumber = phoneNumber;
    }

    public int getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(int registrationYear) {
        if (registrationYear < 2015 || registrationYear > 2025) {
            throw new IllegalArgumentException("✗ Error: Tahun registrasi harus antara 2015-2025");
        }
        this.registrationYear = registrationYear;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        if (!membershipType.equals("Silver") && !membershipType.equals("Gold") && !membershipType.equals("Platinum")) {
            throw new IllegalArgumentException("✗ Error: Membership type harus Silver/Gold/Platinum");
        }
        this.membershipType = membershipType;
    }
}