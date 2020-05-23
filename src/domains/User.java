package domains;

public class User {
	
	protected String username;
	protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String gender;
    protected int age;
    protected Boolean banned;
    public int dailyPostCount;
    public int dailyReviewCount;


    public User() {}
     
    public User(String username, String password, String firstName, String lastName,
    		String email, String gender, int age) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.banned = false;
        this.dailyPostCount = 0;
        this.dailyReviewCount = 0;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getGender() {
        return gender;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getBanned() {
        return banned;
    }
 
    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
    
    public int getDailyPostCount() {
        return dailyPostCount;
    }
 
    public void setDailyPostCount(int dailyPostCount) {
        this.dailyPostCount = dailyPostCount;
    }
    
    public int getDailyReviewCount() {
        return dailyReviewCount;
    }
 
    public void setDailyReviewCount(int dailyReviewCount) {
        this.dailyReviewCount = dailyReviewCount;
    }

}

