package dao;

import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domains.FavoriteSeller;

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


@WebServlet("/FavoriteSellerDAO")
public class FavoriteSellerDAO extends HttpServlet {
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
	
	public List<FavoriteSeller> listFavoriteSeller(String username) throws SQLException {
		
		List<FavoriteSeller> listFavoriteSeller = new ArrayList<FavoriteSeller>();
		connect_func();
		
		String sql = "SELECT * FROM FavoriteSeller where username = ?";
		PreparedStatement s = connect.prepareStatement(sql);
        s.setString(1, username);
		
        ResultSet resultSet = s.executeQuery();
        
        while (resultSet.next()) {
    		String favsellerusername = resultSet.getString("favsellerusername");
             
            FavoriteSeller FavoriteSeller = new FavoriteSeller(username, favsellerusername);
            listFavoriteSeller.add(FavoriteSeller);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listFavoriteSeller;
    }
	
	public List<FavoriteSeller> searchFavSeller(String username) throws SQLException { 
		

		List<FavoriteSeller> listFavSellers = new ArrayList<FavoriteSeller>();
        connect_func();  
        
        String sql = "SELECT * FROM FavoriteSeller WHERE username = ?";
        PreparedStatement s = connect.prepareStatement(sql);
        s.setString(1, username);

        ResultSet resultSet = s.executeQuery();
         
        while (resultSet.next()) {
    		String favsellerusername = resultSet.getString("favsellerUsername");
             
    		FavoriteSeller FavoriteSeller = new FavoriteSeller(username, favsellerusername);
    		listFavSellers.add(FavoriteSeller);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listFavSellers;
    }

	
	public List<String> getBothfav(String userX, String userY) throws SQLException {
		
		List<String> BothFav = new ArrayList<String>();
		connect_func();
		
		String getBothfav = "SELECT favsellerusername FROM FavoriteSeller WHERE username = ? AND favsellerusername"
				+ " IN (SELECT favsellerusername FROM FavoriteSeller WHERE username = ?)";
		
		PreparedStatement s = connect.prepareStatement(getBothfav);
        s.setString(1, userX);
        s.setString(2, userY);

        ResultSet resultSet = s.executeQuery();
         
        while (resultSet.next()) {
    		String BothFavs = resultSet.getString("favsellerusername");
             
    		BothFav.add(BothFavs);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
		
		return BothFav;
	}

	
	protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
	
	public boolean loadFavSeller() throws SQLException {
    	connect_func();
    	System.out.println("load fav sellers");
        statement =  (Statement) connect.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS FavoriteSeller");
        String createFavSeller = "CREATE TABLE IF NOT EXISTS FavoriteSeller " +
            "(username VARCHAR(50), " +
        	"favsellerusername VARCHAR(50), "+
            " PRIMARY KEY (username, favsellerUsername ))";
//		statement.executeUpdate(createFavSeller); 
        boolean tableCreated = statement.executeUpdate(createFavSeller) > 0;
        
	   	String insertFavSeller = "INSERT INTO FavoriteSeller(username, favsellerusername) "+
	    		" VALUES('John', 'Monica')";
	   	
	   	
        boolean FavSellersInserted = statement.executeUpdate(insertFavSeller) > 0;
        statement.close();
        // disconnect();
        System.out.println("Initialized favorite seller");
        return (tableCreated && FavSellersInserted);     

    }
	public boolean insert(String username, String favsellerusername) throws SQLException {
	   	connect_func();         
		String insert = "insert into FavoriteSeller(username, favsellerusername ) "
			+ "values (?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(insert);	
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, favsellerusername);

        // preparedStatement.executeUpdate();
		boolean rowInserted = preparedStatement.executeUpdate() > 0;
	    preparedStatement.close();
        // disconnect();
        return rowInserted;
    } 
	 
	public boolean delete(String username, String favsellerusername) throws SQLException {
	    String delete = "DELETE FROM FavoriteSeller WHERE (username = ?) AND (favsellerusername = ?)";        
	    connect_func();
	         
	    preparedStatement = (PreparedStatement) connect.prepareStatement(delete);
	    preparedStatement.setString(1, username);
		preparedStatement.setString(2, favsellerusername);

	    boolean rowDeleted = preparedStatement.executeUpdate() > 0;
	    preparedStatement.close();
        // disconnect();
	    return rowDeleted;     
		
		
		
	}
}


	