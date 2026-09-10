package co.unicesar.edu.taller2.gamezoneunicesar.capas.model;

public class VideoGame extends Product{

    private String platform;
    private String genre;
    private String ageRating;

    public VideoGame(String id, String title, double price, int stockQuantity, String platform, String genre, String ageRating) {
        super(id, title, price, stockQuantity);
        this.platform = platform;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    @Override
    public String getDescription() {
        return "Video Game: " + getTitle() + ", Platform: " + platform + ", Genre: " + genre + ", Age Rating: " + ageRating + ", $" + getPrice();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }
}
