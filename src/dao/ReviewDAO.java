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

import domains.Review;

//import domains.Store;
//import domains.Review;

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

@WebServlet("/ReviewDAO")
public class ReviewDAO extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
	
	public ReviewDAO() {}
	
	/**
     * @see HttpServlet#HttpServlet()
     */
	protected void connect_func() throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            }
            catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
    			      .getConnection("jdbc:mysql://127.0.0.1:3306/projectdb?"
    	  			          + "user=john&password=pass1234");
            System.out.println(connect);
        }
    }
	
	public List<Review> searchReviews(int ItemId) throws SQLException {
		
        List<Review> listReviews = new ArrayList<Review>();        
        String sql = "SELECT * FROM review";      
        connect_func();      
        statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
        	if(ItemId == resultSet.getInt("ItemId")) {
	            int reviewID = resultSet.getInt("reviewID");
	            String score = resultSet.getString("score");
	            String reviewText = resultSet.getString("reviewText");
	            String seller = resultSet.getString("seller");
	            String date = resultSet.getString("date");
	             
	            Review review = new Review(reviewID, ItemId, score, reviewText, seller, date);
	            listReviews.add(review);
	            System.out.println("Listing review: " + reviewID);
        	}
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listReviews;
    }

	public List<String> listUserPairs() throws SQLException {
		
		List<String> users = new ArrayList<String>();
		connect_func();
		
		String getUsers = "SELECT A.username as A, B.username as B"
				+ " FROM user_tb A, user_tb B"
				+ " WHERE A.username IN"
					+ " (SELECT r1.seller"
					+ " FROM review r1, item j1"
					+ " WHERE A.username = r1.seller"
					+ " AND r1.score = 'Excellent'"
					+ " AND j1.ItemId = r1.ItemId"
					+ " AND j1.seller = B.username"
					+ " GROUP BY r1.seller"
					+ " HAVING COUNT(*) ="
						+ " (SELECT COUNT(*)"
						+ " FROM item j2"
						+ " WHERE j2.seller = B.username))"
				+ " AND B.username IN"
					+ " (SELECT r1.seller"
					+ " FROM review r1, item j1"
					+ " WHERE B.username = r1.seller"
					+ " AND r1.score = 'Excellent'"
					+ " AND j1.ItemId = r1.ItemId"
					+ " AND j1.seller = A.username"
					+ " GROUP BY r1.seller"
					+ " HAVING COUNT(*) ="
						+ " (SELECT COUNT(*)"
						+ " FROM item j2"
						+ " WHERE j2.seller = A.username));";

		PreparedStatement s = connect.prepareStatement(getUsers);
     
        ResultSet resultSet = s.executeQuery();
         
        while (resultSet.next()) 
        {
        	String userA = (resultSet.getString("A"));
			String userB = (resultSet.getString("B"));
			
			String pair1 = userA + ", " + userB;
			String pair2 = userB + ", " + userA;
			
			boolean duplicate = false;
			
			for(int i = 0; i < users.size(); i++) 
			{
				if(users.get(i).matches(pair1) || users.get(i).matches(pair2)) 
				{
					duplicate = true;
					break;
				}
			}
			
			if(!duplicate) 
			{
				users.add(pair1);
			}
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
		
		return users;
	}
	

	
	protected void disconnect() throws SQLException 
	{
        if (connect != null && !connect.isClosed()) 
        {
        	connect.close();
        }
    }
	
	public boolean initReviewTable() throws SQLException {
    	connect_func();
        statement =  (Statement) connect.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS review");
        String createReviewTb = "CREATE TABLE IF NOT EXISTS review "
            + "(reviewID INT NOT NULL AUTO_INCREMENT, "
            + " Itemid INT NOT NULL, "
            + " score VARCHAR(10) NOT NULL, "
            + " reviewText VARCHAR(300), "
            + " seller VARCHAR(50) NOT NULL, "
            + " date Date, " 
            + " PRIMARY KEY ( reviewID, itemID ))";
//            + " CHECK (score IN (‘Excellent’, ‘Good’, ‘Fair’, ‘Poor’)))";
        statement.executeUpdate(createReviewTb);  
    	
    	String insertReviews = "INSERT INTO review(reviewID, ItemId, score, reviewText, seller, date) " +
    		" VALUES(1, 1, 'Excellent', 'I love this shirt!', 'Monica', '2019-09-09'), " +
    		"(2, 2 , 'Poor', 'Very bad material' , 'Rach1' , '2019-08-10'), "+
    		"(3, 3 , 'Poor', 'Very bad material' , 'Maya25' , '2019-08-10'), "+
    		"(4, 4 , 'Poor', 'Very bad ' , 'Joey5' , '2019-09-10'), " +
    		"(5, 5,  'Excellent', 'beautiful' , 'Ross2', '2019-09-10'), " +
    		"(6, 6,  'Excellent', 'beautiful' , 'Chand23', '2019-08-10')"; 		
    		
         
        boolean reviewsInserted = statement.executeUpdate(insertReviews) > 0;
        statement.close();
        // disconnect();
        System.out.println("Review table initialized");
        return (reviewsInserted);     
    }
	
	public boolean insert(Review review) throws SQLException {
	   	connect_func();         
		String insert = "insert into  review(ItemId, score,"
			+ "reviewText, seller, date) values (?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(insert);
	    preparedStatement.setInt(1, review.getitemID());	
		preparedStatement.setString(2, review.getScore());
		preparedStatement.setString(3, review.getReviewText());
		preparedStatement.setString(4, review.getseller());
		preparedStatement.setString(5, review.getDate());

        // preparedStatement.executeUpdate();
		boolean rowInserted = preparedStatement.executeUpdate() > 0;
	    preparedStatement.close();
        // disconnect();
        return rowInserted;
    }   
	 
	public boolean delete(int reviewID) throws SQLException {
	    String delete = "DELETE FROM review WHERE reviewID = ?";        
	    connect_func();
	         
	    preparedStatement = (PreparedStatement) connect.prepareStatement(delete);
	    preparedStatement.setInt(1, reviewID);
	         
	    boolean rowDeleted = preparedStatement.executeUpdate() > 0;
	    preparedStatement.close();
        // disconnect();
	    return rowDeleted;     
	}
}
