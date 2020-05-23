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

import domains.Item;
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

@WebServlet("/ItemDAO")
public class ItemDAO extends HttpServlet {
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
	

	public List<Item> searchItem(String searchKey) throws SQLException {
		
		List<Item> listItems = new ArrayList<Item>();
	    connect_func();  
	    
	    String getItemIDs = "SELECT * FROM Item WHERE category = ?";
	    PreparedStatement s = connect.prepareStatement(getItemIDs);
	    s.setString(1, searchKey);

	    ResultSet resultSet = s.executeQuery();
	     
	    while (resultSet.next()) {
	    	int itemID = resultSet.getInt("itemID");
	        ItemDAO itemDAO = new ItemDAO();
	        itemDAO.connect_func();  

	        String getItems = "SELECT * FROM Item WHERE itemID = ?";
	        PreparedStatement statement = connect.prepareStatement(getItems);
	        statement.setInt(1, itemID);
	        
	        ResultSet resultSet2 = statement.executeQuery();
	            
	        while(resultSet2.next()) {


	            String title = resultSet.getString("title");
	            String description = resultSet.getString("description");
	            String seller = resultSet.getString("seller");
	            String date = resultSet.getString("date");
	            double price = resultSet.getDouble("price");
	            String category = resultSet.getString("category");
	    		             
	            Item item = new Item(itemID,title,description,seller,date,price,category);
	            listItems.add(item);
	        }
	    }        
	    resultSet.close();
	    statement.close();         
	    disconnect();        
	    return listItems;
	}
	
	public List<Item> listExpensive() throws SQLException {
		
		List<Item> listExpensive = new ArrayList<Item>();
		String sql = "SELECT * FROM Item ORDER BY price DESC";
		connect_func();
		statement = (Statement) connect.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		

		while(resultSet.next()){
			
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String seller = resultSet.getString("seller");
            String date = resultSet.getString("date");
            double price = resultSet.getDouble("price");
            String category = resultSet.getString("category");
			
            Item item = new Item(title,description,seller,date,price,category);
            listExpensive.add(item);	
		}
		resultSet.close();
		statement.close();
		disconnect();
		
		
		return listExpensive;
	}
	
	public List<Item> ItemsbySeller(String username) throws SQLException {

		List<Item> Userposteditem = new ArrayList<Item>();        

	    String sql = "SELECT * FROM Item WHERE seller = ?";
	     
	    connect_func();
	     
	    preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
	    preparedStatement.setString(1, username);
	     
	    ResultSet resultSet = preparedStatement.executeQuery();
	     
	    if (resultSet.next()) {
	    	int itemID = resultSet.getInt("itemID");
	        String title = resultSet.getString("title");
	        String description = resultSet.getString("description");
	        String seller = resultSet.getString("seller");
	        String date = resultSet.getString("date");
	        double price = resultSet.getDouble("price");
	        String category = resultSet.getString("category");
	         
	        Item item = new Item(itemID,title,description,seller,date,price,category);
	        Userposteditem.add(item);
	    }
	     
	    resultSet.close();
	    statement.close();
	     
	    return Userposteditem;
	}
	
	
    public List<Item> listAllItems() throws SQLException {
        List<Item> listItems = new ArrayList<Item>();        
        String sql = "SELECT * FROM Item";      
        connect_func();      
        statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int itemID = resultSet.getInt("itemID");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String seller = resultSet.getString("seller");
            String date = resultSet.getString("date");
            Double price = resultSet.getDouble("price");
            String category = resultSet.getString("category");
             
            Item item = new Item(itemID,title,description,seller,date,price,category);
            listItems.add(item);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listItems;
    }
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    
	
	
	public boolean loadItems() throws SQLException {
    	connect_func();
        statement =  (Statement) connect.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS Item");
        String createItemsTb = "CREATE TABLE IF NOT EXISTS Item " +
            "(itemID INT NOT NULL AUTO_INCREMENT, " +
            " title VARCHAR(50)," +
            " description VARCHAR(50)," +
            " seller VARCHAR(50) NOT NULL," +
            " date Date ,"+
            " price DOUBLE, " +
            " category VARCHAR(50), " +
            " PRIMARY KEY ( itemID ))";
		statement.executeUpdate(createItemsTb);    
        
        String insertItems = "INSERT INTO Item(itemID, title, description, seller, date, price, category) " +
        				"VALUES(1, 'Leggings', 'Black leggings' ,'John','2019-06-02',8.00,'bottoms'),"+
        				"(2,'Skirt','blue mini-skirt','Maya25', '2019-06-02', 15.00,'bottoms'),"+
        				"(3, 'Pants','short green pant','Ria5', '2019-07-01', 20.00, 'bottoms'),"+
        				"(4, 'Yoga','Active Cropped Sweatshirt','Rach1', '2019-05-16', 25.00, 'ActiveWears'),"+       				
        				"(5, 'Stripe','Abstract Stripe sweater dress','Joey5', '2019-10-16', 30.00, 'dresses'),"+ 
        				"(6, 'Maxi','Glittered Sheer Cami dress','Rach1', '2019-12-16', 36.00, 'dresses'),"+    
        				"(7, 'Blazers','Brushed Plaid Jacket','Ross2', '2019-11-17', 50.00, 'Jackets'),"+
        				"(8, 'Bomber','Hooded Bomber Jacket','Monica', '2019-12-25', 40.00, 'Jackets'),"+
        				"(9, 'Crop Top','Sheer Floral Lace crop top','Chand23', '2019-12-01', 34.00, 'tops'),"+
        				"(10, 'Romper','Tube Biker short Romper','Pheob95', '2019-10-25', 26.00, 'Rompers')";
        				
        				
        boolean itemsInserted = statement.executeUpdate(insertItems) > 0;
        statement.close();

        System.out.println("Table Item initialized");
        return (itemsInserted);     
    }

    
    public boolean insert(Item item) throws SQLException {
    	connect_func();   
    	
	    long time = System.currentTimeMillis();

		String insert = "insert into  Item(title, description, seller, date, price, category) values (?, ?, ?, ?, ?,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(insert);
		preparedStatement.setString(1, item.getTitle());
		preparedStatement.setString(2, item.getDescription());	
		preparedStatement.setString(3, item.getSeller());
		preparedStatement.setString(4, java.time.LocalDate.now().toString());
		preparedStatement.setDouble(5, item.getPrice());
		preparedStatement.setString(6, item.getCategory());
//		preparedStatement.executeUpdate();
		
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowInserted;
    	
    	
    }

    
    
    public boolean delete(int itemid) throws SQLException {
        String sql = "DELETE FROM Item WHERE itemID = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, itemid);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowDeleted;     
    }
     
	
    public Item getItem(int itemID) throws SQLException {
    	Item item = null;
        String sql = "SELECT * FROM Item WHERE itemID = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, itemID);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String seller = resultSet.getString("seller");
            String date = resultSet.getString("date");
            double price = resultSet.getDouble("price");
            String category = resultSet.getString("category");

             
            item = new Item(itemID,title,description,seller,date,price,category);
        }
       
        
        resultSet.close();
        statement.close();
         
        return item;
    }


}