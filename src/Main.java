public class Main {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("LIBRARY MANAGEMENT SYSTEM");
        System.out.println("============================================\n");


        System.out.println("=== REGISTRASI ANGGOTA ===");
        Member member1 = new Member("Alice Johnson", "alice.j@email.com", "081234567890", 2020, "Platinum");
        System.out.println("✓ Anggota berhasil ditambahkan: " + member1.getMemberId() + " - " + member1.getName() + " (" + member1.getMembershipType() + ")");

        Member member2 = new Member("Bob Smith", "bob.smith@email.com", "081298765432", 2022, "Gold");
        System.out.println("✓ Anggota berhasil ditambahkan: " + member2.getMemberId() + " - " + member2.getName() + " (" + member2.getMembershipType() + ")");

        Member member3 = new Member("Charlie Brown", "charlie.b@email.com", "081223456789", 2024, "Silver");
        System.out.println("✓ Anggota berhasil ditambahkan: " + member3.getMemberId() + " - " + member3.getName() + " (" + member3.getMembershipType() + ")");

        Member member4 = new Member("Diana Prince", "diana.p@email.com", "081287654321", 2021, "Gold");
        System.out.println("✓ Anggota berhasil ditambahkan: " + member4.getMemberId() + " - " + member4.getName() + " (" + member4.getMembershipType() + ")");

        System.out.println();


        System.out.println("=== REGISTRASI BUKU ===");
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 1925, 5);
        System.out.println("✓ Buku berhasil ditambahkan: " + book1.getBookId() + " - \"" + book1.getTitle() + "\" by " + book1.getAuthor());

        Book book2 = new Book("Clean Code", "Robert C. Martin", "Technology", 2008, 8);
        System.out.println("✓ Buku berhasil ditambahkan: " + book2.getBookId() + " - \"" + book2.getTitle() + "\" by " + book2.getAuthor());

        Book book3 = new Book("Sapiens", "Yuval Noah Harari", "History", 2011, 6);
        System.out.println("✓ Buku berhasil ditambahkan: " + book3.getBookId() + " - \"" + book3.getTitle() + "\" by " + book3.getAuthor());

        Book book4 = new Book("1984", "George Orwell", "Fiction", 1949, 4);
        System.out.println("✓ Buku berhasil ditambahkan: " + book4.getBookId() + " - \"" + book4.getTitle() + "\" by " + book4.getAuthor());

        Book book5 = new Book("The Pragmatic Programmer", "Hunt & Thomas", "Technology", 1999, 3);
        System.out.println("✓ Buku berhasil ditambahkan: " + book5.getBookId() + " - \"" + book5.getTitle() + "\" by " + book5.getAuthor());

        Book book6 = new Book("Atomic Habits", "James Clear", "Non-Fiction", 2018, 10);
        System.out.println("✓ Buku berhasil ditambahkan: " + book6.getBookId() + " - \"" + book6.getTitle() + "\" by " + book6.getAuthor());

        System.out.println();


        System.out.println("=== TRANSAKSI PEMINJAMAN ===");
        Transaction trx1 = new Transaction(member1, book2, "01-12-2025", 14);
        Transaction trx2 = new Transaction(member2, book1, "05-12-2025", 14);
        Transaction trx3 = new Transaction(member3, book3, "10-11-2025", 14);
        Transaction trx4 = new Transaction(member4, book4, "20-11-2025", 14);
        Transaction trx5 = new Transaction(member1, book6, "15-11-2025", 14);
        Transaction trx6 = new Transaction(member2, book5, "18-11-2025", 14);
        Transaction trx7 = new Transaction(member3, book1, "25-11-2025", 14);
        Transaction trx8 = new Transaction(member4, book2, "28-11-2025", 14);

        System.out.println();


        System.out.println("=== PENGEMBALIAN BUKU ===");
        trx3.processReturn("04-12-2025");
        trx4.processReturn("03-12-2025");
        trx5.processReturn("02-12-2025");
        trx6.processReturn("01-12-2025");

        System.out.println();


        System.out.println("=== DEMONSTRASI ENCAPSULATION ===");
        System.out.println("Mengubah email member1 menggunakan setter:");
        System.out.println("Email lama: " + member1.getEmail());
        member1.setEmail("alice.johnson.new@email.com");
        System.out.println("Email baru: " + member1.getEmail());
        System.out.println();


        System.out.println("=== STATISTIK SISTEM (Static Variables) ===");
        System.out.println("Total Anggota Terdaftar    : " + Member.getTotalMembers() + " orang");
        System.out.println("Total Buku Terdaftar       : " + Book.getTotalBooks() + " judul");
        System.out.println("Total Transaksi            : " + Transaction.getTotalTransactions() + " transaksi");
        System.out.println();


        System.out.println("=== TEST UPGRADE MEMBERSHIP ===");
        System.out.println("Membership Charlie sebelum: " + member3.getMembershipType());
        System.out.println("Batas pinjam sebelum: " + member3.getMaxBorrowLimit() + " buku");
        member3.upgradeMembership("Gold");
        System.out.println("  Batas Pinjam Baru: " + member3.getMaxBorrowLimit() + " buku | Diskon Denda Baru: " + (int)(member3.getMembershipDiscount() * 100) + "%");
        System.out.println();


        System.out.println("=== TEST VALIDASI ===");

        try {
            Member invalidMember = new Member("Test User", "invalidemail", "081234567890", 2023, "Gold");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


        try {
            Member invalidMember2 = new Member("Test User", "test@email.com", "0812", 2023, "Gold");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


        try {
            Member invalidMember3 = new Member("Test User", "test@email.com", "081234567890", 2023, "Diamond");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


        Book unavailableBook = new Book("Test Book", "Test Author", "Fiction", 2020, 0);
        Transaction failedTrx = new Transaction(member1, unavailableBook, "01-12-2025", 14);


        try {
            Book invalidBook = new Book("Invalid Book", "Test Author", "Fiction", 1800, 5);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();


        System.out.println("\n============================================");
        System.out.println("DAFTAR ANGGOTA PERPUSTAKAAN");
        System.out.println("============================================");
        member1.displayInfo();
        member2.displayInfo();
        member3.displayInfo();
        member4.displayInfo();
        System.out.println("Total Anggota Terdaftar: " + Member.getTotalMembers());

        System.out.println("\n============================================");
        System.out.println("DAFTAR KOLEKSI BUKU");
        System.out.println("============================================");
        book1.displayBookInfo();
        book2.displayBookInfo();
        book3.displayBookInfo();
        book4.displayBookInfo();
        book5.displayBookInfo();
        book6.displayBookInfo();
        System.out.println("Total Buku Terdaftar: " + Book.getTotalBooks());

        System.out.println("\n============================================");
        System.out.println("DAFTAR TRANSAKSI PEMINJAMAN");
        System.out.println("============================================");
        trx1.displayTransaction();
        trx2.displayTransaction();
        trx3.displayTransaction();
        trx4.displayTransaction();
        trx5.displayTransaction();
        trx6.displayTransaction();
        trx7.displayTransaction();
        trx8.displayTransaction();


        System.out.println("============================================");
        System.out.println("STATISTIK SISTEM");
        System.out.println("============================================");

        int activeTransactions = 0;
        int overdueTransactions = 0;
        double totalFees = 0.0;

        Transaction[] allTransactions = {trx1, trx2, trx3, trx4, trx5, trx6, trx7, trx8};
        for (Transaction trx : allTransactions) {
            if (trx.getReturnDate() == null) {
                activeTransactions++;
                if (trx.isOverdue("05-12-2025")) {
                    overdueTransactions++;
                }
            }
            totalFees += trx.getLateFee();
        }

        System.out.println("Total Anggota Terdaftar    : " + Member.getTotalMembers() + " orang");
        System.out.println("Total Buku Tersedia        : " + Book.getTotalBooks() + " judul");
        System.out.println("Total Transaksi            : " + Transaction.getTotalTransactions() + " transaksi");
        System.out.println("Transaksi Aktif            : " + activeTransactions + " peminjaman");
        System.out.println("Transaksi Terlambat        : " + overdueTransactions + " peminjaman");
        System.out.println("Total Denda Terkumpul      : Rp " + String.format("%,.0f", totalFees));
        System.out.println();
        System.out.println("Anggota Paling Aktif       : " + member1.getName() + " (" + member1.getMembershipType() + ")");
        System.out.println("Buku Paling Populer        : " + book2.getTitle() + " (" + book2.getCategory() + ")");
        System.out.println("Kategori Favorit           : Technology & Fiction");
        System.out.println("============================================");

        System.out.println("\n============================================");
        System.out.println("PROGRAM SELESAI");
        System.out.println("============================================");
    }
}
