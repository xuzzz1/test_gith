package pe.rate;

public class RateDTO {
    private int rateId;
    private int userId;
    private int bookId;
    private String username; // Để hiển thị tên user
    private int rateStar;
    private String comment;

    public RateDTO(int rateId, int userId, int bookId, String username, int rateStar, String comment) {
        this.rateId = rateId;
        this.userId = userId;
        this.bookId = bookId;
        this.username = username;
        this.rateStar = rateStar;
        this.comment = comment;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRateStar() {
        return rateStar;
    }

    public void setRateStar(int rateStar) {
        this.rateStar = rateStar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
