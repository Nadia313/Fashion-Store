package dao;

import java.io.IOException;


import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domains.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UserDAO")
public class UserDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public UserDAO() {}
	
	protected void connect_func() throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/projectdb?"
  			          + "user=john&password=pass1234");
            System.out.println(connect);
        }
    }
	
    public List<User> listAllUsers() throws SQLException {
    	System.out.println("start list users");
    	List<User> listUsers = new ArrayList<User>();
    	String getAllPeople = "SELECT * FROM User";
    	connect_func();
    	statement =  (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(getAllPeople);
    	
    	while(resultSet.next()) {
    		String username = resultSet.getString("username");
    		System.out.println(username);
    		String password = resultSet.getString("password");
    		String firstName = resultSet.getString("firstName");
    		String lastName = resultSet.getString("lastName");
    		String email = resultSet.getString("email");
    		String gender = resultSet.getString("gender");
            int age = resultSet.getInt("age");
            
            User user = new User(username, password, firstName, lastName, email, gender, age);
            listUsers.add(user);
    	}
        resultSet.close();
        statement.close();         
        disconnect();
        System.out.println("End list users");
        return listUsers;
    }
    
    
    
    public List<User> samedate(String category1, String category2) throws SQLException {
    	List<User>listUsers = new ArrayList<User>();
    	String sql = "SELECT DISTINCT u.* " +
    			"FROM User u, item item1, item item2 " +
    			"WHERE item1.itemID != item2.itemID AND item1.date = item2.date " +
    			"AND item1.username = item2.username " +
    			"AND item1.itemCategory = ? " +
    			"AND item2.itemCategory = ? " +
    			"AND item1.username = u.username;";
	    connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1,category1);
    	preparedStatement.setString(2,category2);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	
    	while(resultSet.next()){
    		listUsers.add(resultSet(resultSet));		
    	}
    	resultSet.close();
    	statement.close();
    	disconnect();
    	return listUsers;
    }

	
	public String getUsername(String firstName, String lastName) throws SQLException {
		
		String sql = "SELECT username FROM User WHERE firstName = ? AND lastName = ?";
        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);

        ResultSet resultSet = preparedStatement.executeQuery();
        String username = null;
         
        if (resultSet.next()) {
    		username = resultSet.getString("username");
        }
         
        resultSet.close();
        statement.close();
        return username;
	}
	
	public ArrayList<String> listUsersFullNames() throws SQLException {
		
        ArrayList<String> listUsers = new ArrayList<String>();        
        String getAllUsers = "SELECT * FROM User";      
        connect_func();      
        statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(getAllUsers);
         
        while (resultSet.next()) {

    		String firstName = resultSet.getString("firstName");
    		String lastName = resultSet.getString("lastName");
    		String fullName = firstName + " " + lastName;
    		
            listUsers.add(fullName);
            System.out.println(fullName);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listUsers;
    }
	

	

	public User getUser(String username) throws SQLException {
	    	User user = null;
	        String sql = "SELECT * FROM User WHERE username = ?";
	         
	        connect_func();
	         
	        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
	        preparedStatement.setString(1, username);
	         
	        ResultSet resultSet = preparedStatement.executeQuery();
	         
	        if (resultSet.next()) {
	    		String password = resultSet.getString("password");
	    		String firstName = resultSet.getString("firstName");
	    		String lastName = resultSet.getString("lastName");
	    		String email = resultSet.getString("email");
	    		String gender = resultSet.getString("gender");
	            int age = resultSet.getInt("age");
	             
	            user = new User(username, password, firstName, lastName, email, gender, age);
	        }
	         
	        resultSet.close();
	        statement.close();
	         
	        return user;
	    }
	
	protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
	
	// Checking for duplicate username
	public boolean duplicateUsername(String username) throws SQLException {
		String getAllUsers = "SELECT * FROM User";
    	connect_func();
    	statement =  (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(getAllUsers);
    	
		while(resultSet.next()) {
			if(resultSet.getString("username").equals(username)) {
				// If username is taken, print message
				System.out.println("This username is already taken");
				return true;
			}
		}
		// If username is not found, return false
		return false;
	}
	
	// function to check for duplicate email in system
	public boolean duplicateEmail(String email) throws SQLException {
		String getAllUsers = "SELECT * FROM User";
    	connect_func();
    	statement =  (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(getAllUsers);
    	
		while(resultSet.next()) {
			if(resultSet.getString("email").equals(email)) {
				// If username is taken, print message
				System.out.println("This email is already in use by another account.");
				return true;
			}
		}

		return false;
	}

	
	// validating user
	public boolean validateUser(String username, String password) throws SQLException {
		String getAllPeople = "SELECT * FROM User";
    	connect_func();
    	statement =  (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(getAllPeople);
		while(resultSet.next()) {
			String user = resultSet.getString("username");
			String pass = resultSet.getString("password");
			if(user.equals(username) && pass.equals(password)) { 
				return true;
			}
		}
		return false;
	}
	
    public boolean loadUsers() throws SQLException {
    	connect_func();
    	System.out.println("start load users");
        statement =  (Statement) connect.createStatement();
    	statement.executeUpdate("DROP TABLE IF EXISTS User ");
		String userTable = "CREATE TABLE IF NOT EXISTS User " +
                   "(username VARCHAR(30), " +
                   " password VARCHAR(50), " + 
                   " firstName VARCHAR(50), " +
                   " lastName VARCHAR(50), " + 
                   " email VARCHAR(50), " + 
                   " gender VARCHAR(10), " + 
				   " age TINYINT, " + 
		           " banned Boolean, "+
				   " PRIMARY KEY ( username )," +
				   " UNIQUE (email))";
		statement.executeUpdate(userTable);
    	
    	String hardCode =  "INSERT INTO User(username, password, firstName, lastName, email, gender, age,banned) "+
        		" VALUES('root', 'pass1234', 'Nadia', 'Chowdhury', 'nadiac@gmail.com', 'Male', 32,false), "+
       		 "('John', 'Smith', 'John', 'Smith', 'JohnSmith@gmail.com', 'Male', 27,false), "	+
       		" ('Ria5', 'pass1234', 'Ria', 'Sen', 'riasen@smith.com', 'Female', 27,false), " +
       		" ('Maya25', 'nopass', 'Maya', 'Taylor', 'mayataylor4@hotmail.com', 'Female',25,false)," +
       		"('Ross2', '3221','Ross','Geller','geller23@gmail.com','Male',28,false),"+
       		"('Monica','mn23','Monica','Geller','moni25@hotmail.com','Female',30,false),"+
       		"('Chand23','ch1234','Chandler','Bing','chandler34@hotmail.com','Male',32,false),"+
       		"('Joey5','jo66','Joey','Tribianni','jo45@hotmail.com','Male',26,false),"+
       		"('Rach1','rac4','Rachel','Green','rachgreen@yahoo.com','Female',26,false),"+
       		"('Pheob95','phb4','Pheobe','Buffay','pheobe@gmail.com','Female',26,false)";
       	        
        boolean tableLoaded = statement.executeUpdate(hardCode) > 0;
        
        
        statement.close();
//        disconnect();
        System.out.println("End load users");
        return tableLoaded;     
    }
    
	public boolean insert(User user) throws SQLException {
	   	connect_func();         
		String insert = "insert into  User(username, password, firstName,"
			+ "lastName, email, gender, age) values (?, ?, ?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(insert);
        preparedStatement.setString(1, user.getUsername());
	    preparedStatement.setString(2, user.getPassword());	
		preparedStatement.setString(3, user.getFirstName());
		preparedStatement.setString(4, user.getLastName());
		preparedStatement.setString(5, user.getEmail());
		preparedStatement.setString(6, user.getGender());
		preparedStatement.setInt(7, user.getAge());

        // preparedStatement.executeUpdate();
		boolean rowInserted = preparedStatement.executeUpdate() > 0;
	    preparedStatement.close();
        // disconnect();
        return rowInserted;
    }	
   
	 
	public boolean delete(String username) throws SQLException {
	    String delete = "DELETE FROM User WHERE username = ?";        
	    connect_func();
	         
	    preparedStatement = (PreparedStatement) connect.prepareStatement(delete);
	    preparedStatement.setString(1, username);
	         
	    boolean rowDeleted = preparedStatement.executeUpdate() > 0;
	    preparedStatement.close();
        // disconnect();
	    return rowDeleted;     
	}
 
    
   
	public boolean isBanned(String username) throws SQLException {
		
		String sql = "SELECT banned FROM User WHERE username = ?";
        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        Boolean banned = false;
         
        if (resultSet.next()) {
    		banned = resultSet.getBoolean("banned");
        }
         
        resultSet.close();
        statement.close();		
		
		return banned;
	}
	
	public boolean banUser(String userToBan) throws SQLException {
        String sql = "update User set banned=? where username = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setBoolean(1, true);
        preparedStatement.setString(2, userToBan);
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowUpdated;     
    }
	public boolean unbanUser(String userToUnban) throws SQLException {
		String sql = "update User set banned=? where username = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setBoolean(1, false);
        preparedStatement.setString(2, userToUnban);
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowUpdated;  
	}
	
	public User resultSet(ResultSet resultSet) throws SQLException {
		
		String username = resultSet.getString("username");
		String password = resultSet.getString("password");
		String firstName = resultSet.getString("firstName");
		String lastName = resultSet.getString("lastName");
		String email = resultSet.getString("email");
		String gender = resultSet.getString("gender");
		int age = resultSet.getInt("age");
		
		User user = new User(username, password, firstName, lastName, email, gender, age);
		return user;
		
	}


}
