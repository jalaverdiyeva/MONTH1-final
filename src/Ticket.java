// Ticket (Bilet) class-ı
class Ticket {

    // Add this inside your Ticket class
    public Movie getMovie() {
        return movie;
    }
    // Biletin aid olduğu film obyekti
    private Movie movie;

    // Filmin hansı seansda olduğu
    private Session session;

    // İstifadəçi tərəfindən seçilmiş oturacaq nömrəsi
    private int seatNumber;

    // Konstruktor — yeni Ticket obyekti yaradılan zaman işləyir
    // movie → seçilmiş film
    // session → seçilmiş seans
    // seatNumber → seçilmiş oturacaq nömrəsi
    public Ticket(Movie movie, Session session, int seatNumber) {
        this.movie = movie;          // Film məlumatını saxlayır
        this.session = session;      // Seans məlumatını saxlayır
        this.seatNumber = seatNumber;// Oturacaq nömrəsini saxlayır
    }

    // Bilet məlumatlarını ekrana çıxaran metod
    public void displayTicket() {

        // Filmin adını ekrana çap edir
        System.out.println("Film: " + movie.getTitle());

        // Seansın vaxtını ekrana çap edir
        System.out.println("Seans: " + session.getTime());

        // Oturacaq nömrəsini ekrana çap edir
        System.out.println("Oturacaq: " + seatNumber);

        // Vizual ayırıcı xətt (sadəcə görünüş üçündür)
        System.out.println("---------------------------");
    }
}
