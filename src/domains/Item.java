package domains;



public class Item {
	protected int itemID;
	protected String title;
	protected String description;
	protected String seller;
	protected String date;
	protected double price;
	protected String category;
	
	public Item() {
	}
	
	public Item(int itemID) {
		this.itemID = itemID;
	}
	
	public Item(int itemID, String title, String description, String seller,String date, double price, String category) {
		this(title, description, seller, date, price, category);
		this.itemID = itemID;
	}
	
	public Item(String title, String description, String seller, String date, double price, String category) {
		this.title = title;
		this.description = description;
		this.seller = seller;
		this.date = date;
		this.price = price;
		this.category = category;
	}
	
	public Item(String title, String description, String seller, double price, String category) {
		this.title = title;
		this.description = description;
		this.seller = seller;	
		this.price = price;
		this.category = category;
	}
	
	public int getItemID() {
		return itemID;
	}
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
    public String getSeller() {
        return seller;
    }
 
    public void setSeller(String seller) {
        this.seller = seller;
    }
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
		
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}