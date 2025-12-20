//scanner sinfini istifadə etmək üçün import edirik (istifadəçidən input almaq üçün)
import java.util.Scanner;

//Əsas (Main) sinif -> proqram buradan başlayır
public class MovieBookingSystem{

    //main metodu -> Java proqramının giriş nöqtəsi
    public static void main(String[] args) {

        //scanner obyekti -> klaviaturadan məlumat oxumaq üçün
        Scanner sc=new Scanner(System.in);

        //movie tipində 27 elementlik massiv yaradırıq
        //(massiv mövzusu istifadə olunur)
        Movie[] movies = new Movie[27];

        //hər bir massiv elementinə Movie obyekti mənimsədilir
        movies[0] = new Movie("No Country for Old Men", 2007, "Joel Coen, Ethan Coen",
                new String[]{"Crime", "Thriller", "Western"});
        movies[1] = new Movie("Teeth", 2007, "Mitchell Lichtenstein",
                new String[]{"Comedy", "Horror"});
        movies[2] = new Movie("Kill Bill: The Whole Bloody Affair", 2006, "Quentin Tarantino",
                new String[]{"Action", "Crime"});
        movies[3] = new Movie("Little Miss Sunshine", 2006, "Valerie Faris, Jonathan Dayton",
                new String[]{"Drama", "Comedy"});
        movies[4] = new Movie("The King and the Clown", 2005, "Lee Joon-ik",
                new String[]{"Drama", "Thriller", "History"});
        movies[5] = new Movie("Brokeback Mountain", 2005, "Ang Lee",
                new String[]{"Drama", "Romance"});
        movies[6] = new Movie("Farewell My Concubine", 1993, "Chen Kaige",
                new String[]{"Drama"});
        movies[7] = new Movie("Pearl", 2022, "Ti West",
                new String[]{"Horror", "Thriller"});
        movies[8] = new Movie("Mad Max: Fury Road", 2015, "George Miller",
                new String[]{"Science Fiction", "Adventure", "Action"});
        movies[9] = new Movie("The Witch", 2015, "Robert Eggers",
                new String[]{"Horror", "Drama", "Fantasy"});
        movies[10] = new Movie("Gone Girl", 2014, "David Fincher",
                new String[]{"Drama", "Mystery", "Thriller"});
        movies[11] = new Movie("Vivarium", 2019, "Lorcan Finnegan",
                new String[]{"Mystery", "Thriller", "Horror", "Science Fiction"});
        movies[12] = new Movie("Mary Queen of Scots", 2018, "Josie Rourke",
                new String[]{"Drama", "History"});
        movies[13] = new Movie("Call Me by Your Name", 2017, "Luca Guadagnino",
                new String[]{"Romance", "Drama"});
        movies[14] = new Movie("The Lighthouse", 2019, "Robert Eggers",
                new String[]{"Thriller", "Fantasy", "Horror", "Drama"});
        movies[15] = new Movie("mother!", 2017, "Darren Aronofsky",
                new String[]{"Drama", "Horror"});
        movies[16] = new Movie("The Empty Man", 2020, "David Prior",
                new String[]{"Horror", "Mystery"});
        movies[17] = new Movie("The Call", 2020, "Lee Chung-hyun",
                new String[]{"Thriller", "Mystery", "Science Fiction"});
        movies[18] = new Movie("Hereditary", 2018, "Ari Aster",
                new String[]{"Thriller", "Horror", "Mystery"});
        movies[19] = new Movie("Masquerade", 2012, "Choo Chang-min",
                new String[]{"History", "Drama"});
        movies[20] = new Movie("Parasite", 2019, "Bong Joon Ho",
                new String[]{"Thriller", "Comedy", "Drama"});
        movies[21] = new Movie("The Cursed", 2021, "Sean Ellis",
                new String[]{"Horror", "Thriller", "Drama"});
        movies[22] = new Movie("Fresh", 2022, "Mimi Cave",
                new String[]{"Horror", "Thriller"});
        movies[23] = new Movie("When Evil Lurks", 2023, "Demián Rugna",
                new String[]{"Horror", "Thriller"});
        movies[24] = new Movie("Mickey 17", 2025, "Bong Joon Ho",
                new String[]{"Comedy", "Adventure", "Science Fiction"});
        movies[25] = new Movie("The Passenger", 2023, "Carter Smith",
                new String[]{"Horror", "Thriller"});
        movies[26] = new Movie("She Is Conann", 2023, "Bertrand Mandico",
                new String[]{"Science Fiction", "Fantasy"});

        //hər film üçün 2 seans saxlamaq üçün 2 ölçülü massiv
        Session[][] sessions = new Session[movies.length][2];

        //for dövrü – bütün filmlər üçün seanslar yaradılır
        for (int i = 0; i < movies.length; i++) {
            sessions[i][0] = new Session("14:00", 10); // 14:00 seansı, 10 yer
            sessions[i][1] = new Session("19:00", 10); // 19:00 seansı, 10 yer
        }

        //maksimum 50 bilet saxlamaq üçün Ticket massivi
        Ticket[] tickets = new Ticket[50];
        int ticketCount = 0; // neçə bilet alındığını sayır

        //sonsuz dövr – istifadəçi çıxana qədər menyu göstərilir
        while (true) {

            //bizim menyu
            System.out.println("\n        Film və Seans Sistemi\n");
            System.out.println("1 - bütün filmlər");
            System.out.println("2 - janra görə axtarış");
            System.out.println("3 - ilə görə axtarış");
            System.out.println("4 - rejissora görə axtarış");
            System.out.println("5 - bilet sifarişi");
            System.out.println("6 - alınan biletlər");
            System.out.println("7 - çıxış");
            System.out.print("🪹");
            //İstifadəçidən seçim alınır
            int choice = sc.nextInt();
            sc.nextLine(); // nextInt-dən sonra boş sətri təmizləyir
            System.out.println("────────────────────⛰️🏔️⛰️────────────────────");
            //if-else control flow istifadəsi
            if (choice == 1) {

                //for-each dövrü – bütün filmləri göstərir
                for (Movie m : movies) {
                    m.displayInfo();
                }
            } else if (choice == 2) {
                System.out.print("Axtarmaq istədiyiniz janrı daxil edin: ");
                String genre = sc.nextLine();
                boolean found = false;
                for (Movie m : movies) {
                    if (m.hasGenre(genre)) {
                        m.displayInfo();
                        found = true;
                    }
                }
                if (!found) System.out.println("Bu janr üzrə film tapılmadı!");
            }
            else if (choice == 3) {
                System.out.print("Axtarmaq istədiyiniz ili daxil edin: ");
                int year = sc.nextInt();
                sc.nextLine();
                boolean found = false;
                for (Movie m : movies) {
                    if (m.getYear() == year) {
                        m.displayInfo();
                        found = true;
                    }
                }
                if (!found) System.out.println("Bu ilə uyğun film tapılmadı!");
            }

            else if (choice == 4) {
                System.out.print("Rejissor adı daxil edin: ");
                String director = sc.nextLine();
                boolean found = false;
                for (Movie m : movies) {
                    if (m.getDirector().equalsIgnoreCase(director)) {
                        m.displayInfo();
                        found = true;
                    }
                }
                if (!found) System.out.println("Bu rejissora uyğun film tapılmadı!");
            }
            else if (choice == 6) {
                if (ticketCount == 0) {
                    System.out.println("Hələ bilet alınmayıb!");
                } else {
                    System.out.println("Alınan biletlər:");
                    for (int i = 0; i < ticketCount; i++) {
                        tickets[i].displayTicket();
                    }
                }
            } else if (choice == 5) {
                //film seçimi
                for (int i = 0; i < movies.length; i++) {
                    //exm. 1... 19. Hereditary 27...
                    System.out.println((i + 1) + ". " + movies[i].getTitle());
                }

                System.out.print("🗳️");
                int movieIndex = sc.nextInt() - 1; //so here movieIndex = 2 corresponds to the 3rd movie in the array

                //seans seçimi
                for (int j = 0; j < 2; j++) { //two sessions and prints their time, e.g., 14:00 and 19:00.
                    //1. 14:00
                    //2. 19:00
                    System.out.println((j + 1) + ". " + sessions[movieIndex][j].getTime());
                }
                System.out.print("⏰");
                int sessionIndex = sc.nextInt() - 1; //again subtract 1 for 0-based index

                //oturacaqlar göstərilir
                Session selectedSession = sessions[movieIndex][sessionIndex];
                selectedSession.displaySeats(); //from Session class

                //yer seçimi
                System.out.print("Yer nömrəsi daxil edin: ");
                int seat = sc.nextInt();

                //yer boşdursa bilet yaradılır
                if (selectedSession.bookSeat(seat)) {
                    tickets[ticketCount++] =
                            new Ticket(movies[movieIndex], selectedSession, seat);
                    System.out.println("Bilet uğurla alındı!");
                } else {
                    System.out.println("Bu yer artıq doludur!");
                }

            } else if (choice == 7) {
                //proqramdan çıxış
                System.out.println("Proqram dayandırıldı.");
                break;
            }
        }

        //scanner bağlanır (resursların azad edilməsi)
        sc.close();
    }
}
