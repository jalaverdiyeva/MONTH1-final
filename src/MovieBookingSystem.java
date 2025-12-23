import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import java.util.ArrayList;
import java.util.List;

public class MovieBookingSystem extends Application {

    private Movie[] movies = new Movie[27];
    private Session[][] sessions;
    private int selectedMovieIdx = -1;
    private int selectedSessionIdx = 0;
    private List<Integer> currentlySelectedSeats = new ArrayList<>();

    // UI Elements for History
    private VBox purchaseHistoryDisplay = new VBox(10);

    private BorderPane root = new BorderPane();
    private ImageView posterView = new ImageView();
    private GridPane seatGrid = new GridPane();
    private Label infoLabel = new Label("Tap the Search Bar or a Genre to start");
    private Pane tintPane = new Pane();
    private Label priceLabel = new Label("Total: $0.00");
    private HBox sessionBar = new HBox(15);

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(createView(), 1250, 850);
        stage.setTitle("Cine-Premium | Digital Booking Kiosk");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Pane createView() {
        initializeData();
        StackPane layoutStack = new StackPane();

        // 1. BACKGROUND & TINT
        Region backgroundRegion = new Region();
        try {
            String bgPath = "file:/Users/aniceperson/Desktop/kino.jpeg";
            backgroundRegion.setBackground(new Background(new BackgroundImage(new Image(bgPath),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, true))));
        } catch (Exception e) { System.out.println("BG Missing"); }
        tintPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");

        // 2. SEARCH & LIST (RIGHT COLUMN)
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Browse Movies...");
        searchField.setStyle(
                "-fx-background-radius: 25; " +
                        "-fx-border-radius: 25; " +
                        "-fx-padding: 10 15; " +
                        "-fx-background-color: #ffe4e1; " +
                        "-fx-border-color: #ffb6c1; " +
                        "-fx-border-width: 2; " +
                        "-fx-faint-focus-color: transparent; " +
                        "-fx-focus-color: transparent;"
        );
        searchField.setMaxWidth(250);

        ListView<String> movieListView = new ListView<>();
        ObservableList<String> visibleMovies = FXCollections.observableArrayList();
        movieListView.setItems(visibleMovies);
        movieListView.setMaxHeight(150);
        movieListView.setMaxWidth(250);
        movieListView.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #ffb6c1; -fx-control-inner-background: #fff0f5; -fx-background-insets: 0;");
        movieListView.setVisible(false);
        movieListView.managedProperty().bind(movieListView.visibleProperty());

        FlowPane genreBar = new FlowPane(8, 8);
        genreBar.setMaxWidth(250);
        genreBar.setAlignment(Pos.CENTER_RIGHT);
        String[] genres = {"All", "Horror", "Thriller", "Drama", "Comedy", "Action", "Sci-Fi"};
        // Locate the genre button loop (around line 90-105)
        for (String g : genres) {
            Button gBtn = new Button(g);
            gBtn.setStyle("-fx-background-color: #ffb6c1; -fx-text-fill: white; -fx-font-size: 11; -fx-background-radius: 10;");

            gBtn.setOnAction(e -> {
                movieListView.setVisible(true);
                visibleMovies.clear();
                for (Movie m : movies) {
                    // Updated Logic: If the button is "Sci-Fi", also check for "Science Fiction"
                    boolean isSciFiMatch = g.equals("Sci-Fi") && m.hasGenre("Science Fiction");

                    if (g.equals("All") || m.hasGenre(g) || isSciFiMatch) {
                        visibleMovies.add(m.getTitle());
                    }
                }
            });
            genreBar.getChildren().add(gBtn);
        }

        searchField.setOnMouseClicked(e -> movieListView.setVisible(!movieListView.isVisible()));
        searchField.textProperty().addListener((obs, old, query) -> {
            if (!movieListView.isVisible()) movieListView.setVisible(true);
            visibleMovies.clear();
            for (Movie m : movies) if (m.getTitle().toLowerCase().contains(query.toLowerCase())) visibleMovies.add(m.getTitle());
        });

        movieListView.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTitle().equals(newVal)) {
                        updateMovieSelection(i);
                        movieListView.setVisible(false);
                        break;
                    }
                }
            }
        });

        // 3. PURCHASE HISTORY BOX (Bottom Right)
        Label historyTitle = new Label("RECENT BOOKINGS");
        historyTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");

        ScrollPane historyScroll = new ScrollPane(purchaseHistoryDisplay);
        historyScroll.setPrefHeight(250);
        historyScroll.setFitToWidth(true);
        historyScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: #ffb6c1; -fx-border-radius: 10;");

        VBox rightColumn = new VBox(15, new Label("SEARCH"), searchField, new Label("GENRES"), genreBar, movieListView, new Region(), historyTitle, historyScroll);
        rightColumn.setAlignment(Pos.TOP_RIGHT);
        rightColumn.setPadding(new Insets(30, 40, 30, 20));
        VBox.setVgrow(movieListView, Priority.NEVER);

        // 4. CENTER UI
        posterView.setFitHeight(250);
        posterView.setPreserveRatio(true);
        infoLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-alignment: center;");
        sessionBar.setAlignment(Pos.CENTER);

        Arc screenArc = new Arc(0, 0, 220, 25, 0, 180);
        screenArc.setFill(Color.TRANSPARENT);
        screenArc.setStroke(Color.LIGHTPINK);
        screenArc.setStrokeWidth(4);

        seatGrid.setAlignment(Pos.CENTER);
        seatGrid.setHgap(12); seatGrid.setVgap(12);

        HBox legend = new HBox(20, createLegendItem("Premium ($15.50)", "#ffb6c1"), createLegendItem("Standard ($12.50)", "#34c759"), createLegendItem("Selected", "#ff69b4"));
        legend.setAlignment(Pos.CENTER);

        Button bookBtn = new Button("CONFIRM PURCHASE");
        bookBtn.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 15 40; -fx-background-radius: 30;");
        bookBtn.setOnAction(e -> handlePurchase());
        priceLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22; -fx-font-weight: bold;");

        VBox centerBox = new VBox(20, posterView, infoLabel, new Label("SELECT TIME:"), sessionBar, screenArc, seatGrid, legend, priceLabel, bookBtn);
        centerBox.setAlignment(Pos.CENTER);

        root.setRight(rightColumn);
        root.setCenter(centerBox);
        layoutStack.getChildren().addAll(backgroundRegion, tintPane, root);
        return layoutStack;
    }

    private void handlePurchase() {
        if (selectedMovieIdx == -1 || currentlySelectedSeats.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a movie and seats first!");
            alert.show();
            return;
        }

        String movieTitle = movies[selectedMovieIdx].getTitle();
        String time = sessions[selectedMovieIdx][selectedSessionIdx].getTime();
        String seats = currentlySelectedSeats.toString();
        String price = priceLabel.getText();

        // 1. Show Confirmation Popup
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Booking Confirmed");
        success.setHeaderText("Enjoy the Show!");
        success.setContentText("Movie: " + movieTitle + "\nTime: " + time + "\nSeats: " + seats + "\n" + price);
        success.showAndWait();

        // 2. Add to Visual History on the Right
        Label historyItem = new Label("🎟 " + movieTitle + "\n" + time + " | Seats: " + seats + "\n" + price);
        historyItem.setStyle("-fx-background-color: rgba(255,182,193,0.3); -fx-text-fill: white; -fx-padding: 8; -fx-background-radius: 8; -fx-font-size: 11; -fx-border-color: #ffb6c1; -fx-border-radius: 8;");
        historyItem.setMaxWidth(230);
        purchaseHistoryDisplay.getChildren().add(0, historyItem); // Adds newest to the top

        // 3. Mark seats as booked
        for (int num : currentlySelectedSeats) {
            sessions[selectedMovieIdx][selectedSessionIdx].bookSeat(num);
        }

        currentlySelectedSeats.clear();
        refreshSeatGrid();
    }

    private void updateMovieSelection(int index) {
        selectedMovieIdx = index;
        selectedSessionIdx = 0;
        currentlySelectedSeats.clear();
        Movie m = movies[index];
        infoLabel.setText(m.getTitle().toUpperCase() + "\nDir: " + m.getDirector());
        refreshSessionBar();
        try {
            Image img = new Image("file:" + m.getImagePath(), true);
            posterView.setImage(img);
            img.progressProperty().addListener((obs, old, newVal) -> {
                if (newVal.doubleValue() == 1.0) {
                    Color avgColor = getAverageColor(img);
                    String rgba = String.format("rgba(%d, %d, %d, 0.75)", (int)(avgColor.getRed()*255), (int)(avgColor.getGreen()*255), (int)(avgColor.getBlue()*255));
                    tintPane.setStyle("-fx-background-color: " + rgba + ";");
                }
            });
        } catch (Exception e) {}
        refreshSeatGrid();
    }

    private void refreshSessionBar() {
        sessionBar.getChildren().clear();
        for (int i = 0; i < sessions[selectedMovieIdx].length; i++) {
            final int sIdx = i;
            Button sBtn = new Button(sessions[selectedMovieIdx][i].getTime());
            if (i == selectedSessionIdx) {
                sBtn.setStyle("-fx-background-color: #ff1493; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 15; -fx-border-color: white; -fx-border-radius: 15;");
            } else {
                sBtn.setStyle("-fx-background-color: #ffe4e1; -fx-text-fill: #ff69b4; -fx-background-radius: 15; -fx-border-color: #ffb6c1; -fx-border-radius: 15;");
            }
            sBtn.setOnAction(e -> {
                selectedSessionIdx = sIdx;
                currentlySelectedSeats.clear();
                refreshSessionBar();
                refreshSeatGrid();
            });
            sessionBar.getChildren().add(sBtn);
        }
    }

    private void refreshSeatGrid() {
        seatGrid.getChildren().clear();
        if (selectedMovieIdx == -1) return;
        Session s = sessions[selectedMovieIdx][selectedSessionIdx];
        final double[] total = {0.0};
        priceLabel.setText("Total: $0.00");
        for (int i = 0; i < 20; i++) {
            int row = i / 5;
            int seatNum = i + 1;
            double price = (row < 2) ? 15.50 : 12.50;
            Button btn = new Button("" + seatNum);
            btn.setPrefSize(50, 45);
            if (s.isSeatOccupied(seatNum)) {
                btn.setStyle("-fx-background-color: #ff3b30; -fx-opacity: 0.4;");
                btn.setDisable(true);
            } else {
                String baseColor = (row < 2) ? "#ffb6c1" : "#34c759";
                btn.setStyle("-fx-background-color: " + baseColor + "; -fx-text-fill: white; -fx-background-radius: 8;");
            }
            btn.setOnAction(e -> {
                if (btn.getStyle().contains("#ff69b4")) {
                    btn.setStyle("-fx-background-color: " + ((row < 2) ? "#ffb6c1" : "#34c759") + "; -fx-text-fill: white; -fx-background-radius: 8;");
                    currentlySelectedSeats.remove(Integer.valueOf(seatNum));
                    total[0] -= price;
                } else {
                    btn.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white; -fx-background-radius: 8;");
                    currentlySelectedSeats.add(seatNum);
                    total[0] += price;
                }
                priceLabel.setText(String.format("Total: $%.2f", total[0]));
            });
            seatGrid.add(btn, i % 5, i / 5);
        }
    }

    private HBox createLegendItem(String text, String color) {
        Region dot = new Region(); dot.setPrefSize(14, 14);
        dot.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 4;");
        Label lbl = new Label(text); lbl.setStyle("-fx-text-fill: white; -fx-font-size: 11;");
        return new HBox(8, dot, lbl);
    }

    private Color getAverageColor(Image img) {
        javafx.scene.image.PixelReader pr = img.getPixelReader();
        if (pr == null) return Color.BLACK;
        double r = 0, g = 0, b = 0;
        int count = 0;
        for (int y = 0; y < img.getHeight(); y += 20) {
            for (int x = 0; x < img.getWidth(); x += 20) {
                Color c = pr.getColor(x, y);
                r += c.getRed(); g += c.getGreen(); b += c.getBlue();
                count++;
            }
        }
        return Color.color(r/count, g/count, b/count).darker();
    }

    private void initializeData() {
        movies[0] = new Movie("No Country for Old Men", 2007, "Joel Coen, Ethan Coen", new String[]{"Crime", "Thriller", "Western"}, "/Users/aniceperson/Desktop/posters/NoCountryforOldMen2007.jpg");
        movies[1] = new Movie("Teeth", 2007, "Mitchell Lichtenstein", new String[]{"Comedy", "Horror"},"/Users/aniceperson/Desktop/posters/Teeth2007.jpg");
        movies[2] = new Movie("Kill Bill: The Whole Bloody Affair", 2006, "Quentin Tarantino", new String[]{"Action", "Crime"}, "/Users/aniceperson/Desktop/posters/KillBill2006.jpg");
        movies[3] = new Movie("Little Miss Sunshine", 2006, "Valerie Faris, Jonathan Dayton", new String[]{"Drama", "Comedy"},"/Users/aniceperson/Desktop/posters/LittleMissSunshine2006.jpg");
        movies[4] = new Movie("The King and the Clown", 2005, "Lee Joon-ik", new String[]{"Drama", "Thriller", "History"},"/Users/aniceperson/Desktop/posters/TheKingandtheClown2005.jpg");
        movies[5] = new Movie("Brokeback Mountain", 2005, "Ang Lee", new String[]{"Drama", "Romance"},"/Users/aniceperson/Desktop/posters/BrokebackMountain2005.jpg");
        movies[6] = new Movie("Farewell My Concubine", 1993, "Chen Kaige", new String[]{"Drama"},"/Users/aniceperson/Desktop/posters/FarewellMyConcubine1993.jpg");
        movies[7] = new Movie("Pearl", 2022, "Ti West", new String[]{"Horror", "Thriller"}, "/Users/aniceperson/Desktop/posters/Pearl2022.jpg");
        movies[8] = new Movie("Mad Max: Fury Road", 2015, "George Miller", new String[]{"Science Fiction", "Adventure", "Action"}, "/Users/aniceperson/Desktop/posters/madmax.jpg");
        movies[9] = new Movie("The Witch", 2015, "Robert Eggers", new String[]{"Horror", "Drama", "Fantasy"}, "/Users/aniceperson/Desktop/posters/TheWitch2015.jpg");
        movies[10] = new Movie("Gone Girl", 2014, "David Fincher", new String[]{"Drama", "Mystery", "Thriller"}, "/Users/aniceperson/Desktop/posters/GoneGirl2014.jpg");
        movies[11] = new Movie("Vivarium", 2019, "Lorcan Finnegan", new String[]{"Mystery", "Thriller", "Horror", "Science Fiction"}, "/Users/aniceperson/Desktop/posters/Vivarium2019.jpg");
        movies[12] = new Movie("Mary Queen of Scots", 2018, "Josie Rourke", new String[]{"Drama", "History"}, "/Users/aniceperson/Desktop/posters/MaryQueenOfScots2018.jpg");
        movies[13] = new Movie("Call Me by Your Name", 2017, "Luca Guadagnino", new String[]{"Romance", "Drama"}, "/Users/aniceperson/Desktop/posters/CallMebyYourName2017.jpg");
        movies[14] = new Movie("The Lighthouse", 2019, "Robert Eggers", new String[]{"Thriller", "Fantasy", "Horror", "Drama"}, "/Users/aniceperson/Desktop/posters/TheLighthouse2019.jpg");
        movies[15] = new Movie("mother!", 2017, "Darren Aronofsky", new String[]{"Drama", "Horror"}, "/Users/aniceperson/Desktop/posters/mother!2017.jpg");
        movies[16] = new Movie("The Empty Man", 2020, "David Prior", new String[]{"Horror", "Mystery"}, "/Users/aniceperson/Desktop/posters/TheEmptyMan2020.jpg");
        movies[17] = new Movie("The Call", 2020, "Lee Chung-hyun", new String[]{"Thriller", "Mystery", "Science Fiction"},"/Users/aniceperson/Desktop/posters/TheCall2020.jpg");
        movies[18] = new Movie("Hereditary", 2018, "Ari Aster", new String[]{"Thriller", "Horror", "Mystery"}, "/Users/aniceperson/Desktop/posters/Hereditary2018.jpg");
        movies[19] = new Movie("Masquerade", 2012, "Choo Chang-min", new String[]{"History", "Drama"}, "/Users/aniceperson/Desktop/posters/Masquerade2012.jpg");
        movies[20] = new Movie("Parasite", 2019, "Bong Joon Ho", new String[]{"Thriller", "Comedy", "Drama"},"/Users/aniceperson/Desktop/posters/Parasite2019.jpg");
        movies[21] = new Movie("The Cursed", 2021, "Sean Ellis", new String[]{"Horror", "Thriller", "Drama"}, "/Users/aniceperson/Desktop/posters/TheCursed2021.jpg");
        movies[22] = new Movie("Fresh", 2022, "Mimi Cave", new String[]{"Horror", "Thriller"}, "/Users/aniceperson/Desktop/posters/Fresh2022.jpg");
        movies[23] = new Movie("When Evil Lurks", 2023, "Demián Rugna", new String[]{"Horror", "Thriller"}, "/Users/aniceperson/Desktop/posters/WhenEvilLurks2023.jpg");
        movies[24] = new Movie("Mickey 17", 2025, "Bong Joon Ho", new String[]{"Comedy", "Adventure", "Science Fiction"}, "/Users/aniceperson/Desktop/posters/Mickey172025.jpg");
        movies[25] = new Movie("The Passenger", 2023, "Carter Smith", new String[]{"Horror", "Thriller"}, "/Users/aniceperson/Desktop/posters/ThePassenger2023.jpg");
        movies[26] = new Movie("She Is Conann", 2023, "Bertrand Mandico", new String[]{"Science Fiction", "Fantasy"}, "/Users/aniceperson/Desktop/posters/SheIsConann2023.jpg");

        sessions = new Session[movies.length][2];
        for (int i = 0; i < movies.length; i++) {
            sessions[i][0] = new Session("14:00", 20);
            sessions[i][1] = new Session("19:00", 20);
        }
    }
}
