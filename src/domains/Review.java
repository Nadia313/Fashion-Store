package domains;

public class Review {
	
	protected int reviewerID;
	protected int itemID;
	protected String score;
    protected String reviewText;
    protected String seller;
    protected String date;
 
    public Review() {
    }
 
    public Review(int reviewID) {
        this.reviewerID = reviewID;
    }
 
    public Review(int reviewerID, int itemID, String score, String reviewText, String seller, String date) {
        this(itemID, score, reviewText, seller, date);
        this.reviewerID = reviewerID;
    }
     
    public Review(int itemID, String score, String reviewText, String seller, String date) {
        this.itemID = itemID;
        this.score = score;
        this.reviewText = reviewText;
        this.seller = seller;
        this.date = date;
    }
    
    public int getReviewerID() {
        return reviewerID;
    }
 
    public void setReviewID(int reviewID) {
        this.reviewerID = reviewID;
    }

 
    public int getitemID() {
        return itemID;
    }
 
    public void setitemID(int itemID) {
        this.itemID = itemID;
    }
    
    public String getScore() {
        return score;
    }
 
    public void setScore(String score) {
        this.score = score;
    }
    
    public String getReviewText() {
        return reviewText;
    }
 
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    
    public String getseller() {
        return seller;
    }
 
    public void setseller(String seller) {
        this.seller = seller;
    }
 
    public String getDate() {
        return date;
    }
 
    public void setDate(String date) {
        this.date = date;
    }
}
