import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Transaction {
    private String transactionId;
    private Member member;
    private Book book;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private int daysLate;
    private double lateFee;
    private static int totalTransactions = 0;
    private static final double LATE_FEE_PER_DAY = 2000.0;

    public Transaction(Member member, Book book, String borrowDate, int borrowDurationDays){
        totalTransactions++;
        this.transactionId = String.format("TRX%03d", totalTransactions);
        this.member = member;
        this.book = book;
        setBorrowDate(borrowDate);
        this.dueDate = calculateDueDate(borrowDate, borrowDurationDays);
        this.returnDate = null;
        this.daysLate = 0;
        this.lateFee = 0.0;

        if (book.borrowBook()){
            System.out.println("✓ Peminjaman berhasil: " + member.getName() + " meminjam \"" + book.getTitle() + "\"");
            System.out.println("  Tanggal Pinjam:  " + borrowDate + " | Jatuh Tempo: " + dueDate);
        } else {
            System.out.println("✗ Error: Buku tidak tersedia untuk dipinjam");
        }
    }

    private String calculateDueDate(String borrowDate, int durationDays){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date borrow = sdf.parse(borrowDate);
            long dueDateMillis = borrow.getTime() + (durationDays * 24L * 60 * 60 * 1000);
            Date due = new Date(dueDateMillis);
            return sdf.format(due);
        } catch (ParseException e){
            return borrowDate;
        }
    }

    public void processReturn(String returnDate){
        if (!isValidDate(returnDate)){
            System.out.println("✗ Error: Format tanggal tidak valid");
            return;
        }
        if (isReturnBeforeBorrow(returnDate)){
            System.out.println("✗ Error: Tanggal kembali tidak boleh sebelum tanggal pinjam");
            return;
        }

        this.returnDate = returnDate;
        this.daysLate = calculateDaysLate(returnDate);

        if (this.daysLate > 0){
            calculateLateFee();
        }

        book.returnBook();
        System.out.println("✓ " + member.getName() + " mengembalikan \"" + book.getTitle() + "\"");
        System.out.println("   Tanggal Kembali: " + returnDate + " | " +
                (daysLate > 0 ? "Terlambat: " + daysLate + " hari" : "Tepat Waktu"));
        System.out.println("   Denda: Rp " + String.format("%,.0f", lateFee) +
                (daysLate > 0 ? " (setelah diskon " + (int)(member.getMembershipDiscount() * 100) + "%)" : ""));
    }

    private int calculateDaysLate(String returnDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date due = sdf.parse(this.dueDate);
            Date returned = sdf.parse(returnDate);

            long diff = returned.getTime() - due.getTime();
            long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            return daysDiff > 0 ? (int) daysDiff : 0;
        } catch (ParseException e) {
            return 0;
        }
    }

    private void calculateLateFee(){
        double rawFee = daysLate * LATE_FEE_PER_DAY;
        this.lateFee = rawFee * (1 - member.getMembershipDiscount());
    }

    public void displayTransaction() {
        System.out.println("[" + transactionId + "] " + getTransactionStatus().toUpperCase());
        System.out.println("Peminjam      : " + member.getName() + " (" + member.getMemberId() + ") - " + member.getMembershipType());
        System.out.println("Buku          : " + book.getTitle() + " (" + book.getBookId() + ")");
        System.out.println("Tgl Pinjam    : " + borrowDate);
        System.out.println("Tgl Tempo     : " + dueDate);

        if (returnDate != null) {
            System.out.println("Tgl Kembali   : " + returnDate);
            System.out.println("Terlambat     : " + daysLate + " hari");
            System.out.println("Denda         : Rp " + String.format("%,.0f", lateFee) +
                    (daysLate > 0 ? " (Rp " + String.format("%,.0f", daysLate * LATE_FEE_PER_DAY) +
                            " - diskon " + (int)(member.getMembershipDiscount() * 100) + "%)" : ""));
        } else {
            int daysRemaining = calculateDaysRemaining("05-12-2025");
            if (daysRemaining >= 0) {
                System.out.println("Status        : Masih Dipinjam (" + daysRemaining + " hari lagi)");
            } else {
                System.out.println("Status        : Terlambat " + Math.abs(daysRemaining) + " hari");
            }
        }
        System.out.println("--------------------------------------------");
    }

    private int calculateDaysRemaining(String currentDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date due = sdf.parse(this.dueDate);
            Date current = sdf.parse(currentDate);

            long diff = due.getTime() - current.getTime();
            return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            return 0;
        }
    }

    public boolean isOverdue(String currentDate) {
        if (returnDate != null) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date due = sdf.parse(this.dueDate);
            Date current = sdf.parse(currentDate);
            return current.after(due);
        } catch (ParseException e) {
            return false;
        }
    }

    public String getTransactionStatus() {
        if (returnDate != null) {
            if (daysLate > 0) {
                return "Selesai - Terlambat ⚠️";
            } else {
                return "Selesai - Tepat Waktu ✓";
            }
        } else if (isOverdue("05-12-2025")) {
            return "Terlambat";
        } else {
            return "Aktif";
        }
    }

    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isReturnBeforeBorrow(String returnDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date borrow = sdf.parse(this.borrowDate);
            Date returned = sdf.parse(returnDate);
            return returned.before(borrow);
        } catch (ParseException e) {
            return false;
        }
    }

    // Getters and Setters
    public static int getTotalTransactions() {
        return totalTransactions;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        if (!isValidDate(borrowDate)) {
            throw new IllegalArgumentException("✗ Error: Format tanggal tidak valid (DD-MM-YYYY)");
        }
        this.borrowDate = borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getDaysLate() {
        return daysLate;
    }

    public double getLateFee() {
        return lateFee;
    }
}