package domains;

public class FavItem {
	
	
	protected int itemID;
    protected String username;
	
	public FavItem() {
	}


	public FavItem(int itemID, String username) {
		this.itemID = itemID;
		this.username = username;
	}
     	
    
    public int getItemID() {
        return itemID;
    }
 
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
    
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    
}
