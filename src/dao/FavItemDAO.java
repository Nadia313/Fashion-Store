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

import domains.FavItem;
import domains.Item;

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


@WebServlet("/FavItemDAO")
public class FavItemDAO extends HttpServlet {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
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
	
	public List<FavItem> listFavItem(String username) throws SQLException {  
		
		List<FavItem> listFavItem = new ArrayList<FavItem>();
        connect_func();  
        
        String getFavItems = "SELECT * FROM favItem WHERE username = ?";
        PreparedStatement s = connect.prepareStatement(getFavItems);
        s.setString(1, username);

        ResultSet resultSet = s.executeQuery();
        while (resultSet.next()) {
            int itemID = resultSet.getInt("itemID");
             
            FavItem FavItem = new FavItem(itemID, username);
            listFavItem.add(FavItem);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listFavItem;
    }

	
	protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }

	
	public boolean loadFavItem() throws SQLException {
    	connect_func();
    	System.out.println("load fav items");
        statement =  (Statement) connect.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS FavItem");
        String createFavItem = "CREATE TABLE IF NOT EXISTS FavItem " +
            "(itemID INT NOT NULL AUTO_INCREMENT," +
            " username VARCHAR(50), " +
            " PRIMARY KEY ( itemID, username ))";
		statement.executeUpdate(createFavItem); 
        
    	String insertFavItems = "INSERT INTO FavItem(itemID, username) "+
    		" VALUES(1, 'Maya25'), "+
    		" (2, 'Ross2'), "+
    		" (2, 'Joey5'), " +
    		" (2, 'Monica'), " +
    		" (4, 'Chand23'), " +
    		" (3, 'Ross2'), " +
    		" (8, 'Rach1'), " +
    		" (9, 'Pheob95'), " +
    		" (10, 'Ria5'), " +
    		" (9, 'Joey5') ";

         
        boolean FavItemsInserted = statement.executeUpdate(insertFavItems) > 0;
        statement.close();
        // disconnect();
        System.out.println("Initialized favorite item");
        return (FavItemsInserted);     
    }
	
	public boolean insert(int itemID, String username) throws SQLException {
	   	connect_func();         
		String insert = "insert into FavItem(itemID, username) "
			+ "values (?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(insert);
	    preparedStatement.setInt(1, itemID);	
		preparedStatement.setString(2, username);

        // preparedStatement.executeUpdate();
		boolean rowInserted = preparedStatement.executeUpdate() > 0;
	    preparedStatement.close();
        // disconnect();
        return rowInserted;
    } 
	 
	public boolean delete(int itemID, String username) throws SQLException {
	    String delete = "DELETE FROM FavItem WHERE (itemID = ?) AND (username = ?)";        
	    connect_func();
	         
	    preparedStatement = (PreparedStatement) connect.prepareStatement(delete);
	    preparedStatement.setInt(1, itemID);
	    preparedStatement.setString(2, username);

	    boolean rowDeleted = preparedStatement.executeUpdate() > 0;
	    preparedStatement.close();
        // disconnect();
	    return rowDeleted;     
	}

}
