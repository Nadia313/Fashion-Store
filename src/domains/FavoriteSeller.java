package domains;

public class FavoriteSeller {
	
	protected String username;
	protected String favsellerusername;

	public FavoriteSeller(String username, String favsellerusername) {
        this.username = username;
        this.favsellerusername = favsellerusername;
        
	}

        public String getUsername() {
            return username;
        }
     
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getfavsellerusername() {
            return favsellerusername;
        }
     
        public void setfavsellerusername(String favsellerusername) {
            this.favsellerusername = favsellerusername;
        }

    }
