// Movie class
class Movie {

    private String title;
    private int year;
    private String director;
    private String[] genres;


    private String imagePath; // This will store "/Users/aniceperson/Desktop/posters/1.jpg"

    public Movie(String title, int year, String director, String[] genres, String imagePath){
        this.title = title;
        this.imagePath = imagePath;
        this.year = year;
        this.director = director;
        this.genres = genres;

    }


    public String getImagePath() {
        return imagePath;
    }

    public String getTitle(){ return title; }

    public int getYear(){ return year; }

    public String getDirector(){ return director; }

    public String[] getGenres(){ return genres; }

    public void displayInfo() {
        //filmin adı ekrana çap olunur
        System.out.println("Filmin adı: " + title);

        //filmin buraxılış ili ekrana çap olunur
        System.out.println("Buraxılış ili: " + year);

        //rejissorun adı ekrana çap olunur
        System.out.println("Rejissor: " + director);

        //janrlar massivini vergüllə birləşdirib ekrana çıxarırıq
        System.out.println("Janrlar: " + String.join(", ", genres));

        //vizual ayırıcı xətt (sadəcə dizayn üçündür)
        System.out.println("────────────────────────────────────────");
        System.out.println("❐❐❐❐❐❐❐❐❐❐❐❐❐❐❐❐❍❐❐❐❐❐❐❐❐❐❐❐❐❐❐❐❐       🐝");
    }


    public boolean hasGenre(String genre) { //one genre from the array
        for (String g : genres){ //for-each loop that goes through all the genres of this movie
            if (g.equalsIgnoreCase(genre)) return true; //current genre (g) matches the input genre
        }
        return false;
    }


}
