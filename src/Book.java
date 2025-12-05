public class Book {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private int publicationYear;
    private boolean isAvailable;
    private int totalCopies;
    private int availableCopies;
    private static int totalBooks = 0;

    public Book() {
        totalBooks++;
        this.bookId = "BK" + String.format("%03d", totalBooks);
    }

    public Book(String title, String author, String category, int publicationYear, int totalCopies) {
        totalBooks++;
        this.bookId = "BK" + String.format("%03d", totalBooks);
        this.title = title;
        this.author = author;
        this.category = category;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.isAvailable = totalCopies > 0;
    }

    public void displayBookInfo() {
        System.out.println("[" + bookId + "] " + title);
        System.out.println("Penulis       : " + author);
        System.out.println("Kategori      : " + category);
        System.out.println("Tahun Terbit  : " + publicationYear);
        System.out.println("Umur Buku     : " + getBookAge() + " tahun");
        System.out.println("Total Copy    : " + totalCopies + " eksemplar");
        System.out.print("Tersedia      : " + availableCopies + " eksemplar | Status: " + getAvailabilityStatus());

        if (isNewRelease()) {
            System.out.print(" [NEW RELEASE]");
        }
        System.out.println();
        System.out.println("--------------------------------------------");
    }

    public boolean borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
            if (availableCopies == 0) {
                isAvailable = false;
            }
            return true;
        }
        return false;
    }

    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            isAvailable = true;
        }
    }

    public int getBookAge() {
        return 2025 - publicationYear;
    }

    public boolean isNewRelease() {
        return getBookAge() <= 2;
    }

    public String getAvailabilityStatus() {
        if (availableCopies > 5) {
            return "Banyak Tersedia ✓";
        } else if (availableCopies >= 1) {
            return "Terbatas ⚠️";
        } else {
            return "Tidak Tersedia ✗";
        }
    }

    public static int getTotalBooks() {
        return totalBooks;
    }


    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getPublicationYear() { return publicationYear; }
    public boolean isAvailable() { return isAvailable; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }


    public void setTitle(String title) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        } else {
            System.out.println("✗ Error: Judul buku tidak boleh kosong");
        }
    }

    public void setAuthor(String author) {
        if (author != null && !author.isEmpty()) {
            this.author = author;
        } else {
            System.out.println("✗ Error: Nama penulis tidak boleh kosong");
        }
    }

    public void setCategory(String category) {
        if (category.equals("Fiction") || category.equals("Non-Fiction") ||
                category.equals("Science") || category.equals("Technology") || category.equals("History")) {
            this.category = category;
        } else {
            System.out.println("✗ Error: Kategori harus salah satu dari: Fiction, Non-Fiction, Science, Technology, History");
        }
    }

    public void setPublicationYear(int year) {
        if (year >= 1900 && year <= 2025) {
            this.publicationYear = year;
        } else {
            System.out.println("✗ Error: Tahun terbit tidak valid (1900-2025)");
        }
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void setTotalCopies(int totalCopies) {
        if (totalCopies >= 0) {
            this.totalCopies = totalCopies;
        } else {
            System.out.println("✗ Error: Total copies tidak boleh negatif");
        }
    }

    public void setAvailableCopies(int availableCopies) {
        if (availableCopies >= 0 && availableCopies <= totalCopies) {
            this.availableCopies = availableCopies;
        } else {
            System.out.println("✗ Error: Available copies tidak valid");
        }
    }
}

