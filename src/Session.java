// Session class
class Session {

    private String time;          // Seansın vaxtı
    private boolean[] seats;      // Oturacaqlar: true = dolu, false = boş


    // Konstruktor: seans vaxtını və oturacaq sayını təyin edir
    public Session(String time, int totalSeats) {
        this.time = time;
        seats = new boolean[totalSeats]; // bütün yerlər əvvəlcə boşdur (false)
    }

    // Seans vaxtını qaytarır
    public String getTime() {
        return time;
    }

    // Oturacaqların vəziyyətini ekranda göstərir
    public void displaySeats() {
        System.out.print("🪑Oturacaqlar: ");
        for (int i = 0; i < seats.length; i++) {
            // i → dolu i+1 → boş ...
            System.out.print((i + 1) + (seats[i] ? " (Dolu) " : "🪑 (Boş) "));
        }
        System.out.println();
    }

    // Seçilmiş oturacağı bron edir
    public boolean bookSeat(int seatNumber) {
        // Oturacaq nömrəsi səhvdirsə
        if (seatNumber < 1 || seatNumber > seats.length) return false;

        // Əgər artıq bron olunubsa
        if (seats[seatNumber - 1]) return false;

        // Oturacağı bron edirik
        seats[seatNumber - 1] = true;
        return true;
    }

    public boolean isSeatOccupied(int seatNumber) {
        if (seatNumber < 1 || seatNumber > seats.length) return true;
        return seats[seatNumber - 1];
    }

}
