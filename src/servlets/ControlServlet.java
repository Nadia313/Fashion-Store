package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ItemDAO;
import dao.UserDAO;
import dao.FavItemDAO;
import dao.FavoriteSellerDAO;
import dao.ReviewDAO;
import domains.User;
import domains.Item;
import domains.FavItem;
import domains.FavoriteSeller;
import domains.Review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.PreparedStatement;
 

public class ControlServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private ItemDAO itemDAO;
    private FavItemDAO favitemDAO;
    private ReviewDAO reviewDAO;
    private FavoriteSellerDAO favoritesellerDAO;
    private User sessionUser = null;
	private String username;
    private int itemForReview;
    private Long postStartTime;
    private Long reviewStartTime;
    private String sellerOfReviewedItem;
    private String userX;
    private String userY;


 
    public void init() {
        userDAO = new UserDAO();
        itemDAO = new ItemDAO();
        favitemDAO = new FavItemDAO();
        favoritesellerDAO = new FavoriteSellerDAO();
        reviewDAO = new ReviewDAO();
    }
    
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        try { 
        	RequestDispatcher dispatcher;
            switch (action) {
            case "/loadAllTables":
            	loadAllTables(request, response);
                System.out.println(sessionUser.getUsername());
                break;
            case "/showLoginForm":
            	dispatcher = request.getRequestDispatcher("login.jsp");
	    		dispatcher.forward(request, response);
	            break;
	        case "/login":
	            validateUserLogin(request, response);
                System.out.println(sessionUser.getUsername());
	            break;
	        case "/showRegistrationForm":
	        	dispatcher = request.getRequestDispatcher("register.jsp");
	    		dispatcher.forward(request, response);
                System.out.println(sessionUser.getUsername());
	    		break;
	        case "/register":
	            registerUser(request, response);
                System.out.println(sessionUser.getUsername());
	            break;
	        case "/ItemForm":
	        	dispatcher = request.getRequestDispatcher("ItemForm.jsp");
	    		dispatcher.forward(request, response);
	    		break;
	        case "/search":
	        	dispatcher = request.getRequestDispatcher("searchItem.jsp");
	    		dispatcher.forward(request, response);
	    		break;
        	case "/searchItem":
        		searchItem(request, response);
        		break;
	        case "/postItem":
	        	postItem(request, response);
	        case "/WriteReview":
	        	showReviewForm(request, response);
	    		break;
	        case "/postReview":
	        	postReview(request, response);
	    		break;    		
	        case "/home":
	        	displayHome(request, response);
                System.out.println(sessionUser.getUsername());
	    		break;	    		
	        case "/listExpensive":
	        	listExpensive(request, response);
	        case "/FavoriteItem":
	        	showFavItemList(request, response);
	    		break;
	        case "/FavSeller":
	        	showFavSellerList(request, response);
	    		break;
	        case "/addFavitem":
	        	addFavitem(request, response);
	    		break;
	        case "/deleteFavItem":
	        	deleteFavItem(request, response);
	    		break;
	        case "/addFavSeller":
	        	addFavoriteseller(request, response);
	    		break;
	        case "/deleteFavSeller":
	        	deleteFavoriteSeller(request, response);
	    		break;
	        case "/profiledata":
	        	showProfile(request, response);
	    		break;
			case "/banUser":
	        	banUser(request, response);
	    		break;
	        case "/unbanUser":
	        	unbanUser(request, response);
	    		break;  		
	        case "/samedate":
	        	samedate(request, response);
	        	break;	        	
	        case "/info":
	        	showAllInfo(request, response);
	        	break;
	        case "/BothFav":
	        	showBothFav(request, response);
	    		break;

            default:
            	dispatcher = request.getRequestDispatcher("initializeDB.jsp");       
                dispatcher.forward(request, response);
	        }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }   




	private void loadAllTables(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException, ServletException {
    	//PrintWriter out = response.getWriter();
    	userDAO.loadUsers();
    	itemDAO.loadItems();
    	favitemDAO.loadFavItem();
    	favoritesellerDAO.loadFavSeller();
    	reviewDAO.initReviewTable();
    	//out.println("Tables have been reinitilized");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");       
        dispatcher.forward(request, response);
    }
    
    private void postItem(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException, ServletException {
    	System.out.println("post your item here");
    	

    	checkElapsedTime();

    	if(sessionUser.getDailyPostCount() < 5) {
	        String title = request.getParameter("title");
	        String description = request.getParameter("description");
	        String seller = sessionUser.getUsername();
	        String date = getCurrentDate();
	        double price = Double.parseDouble(request.getParameter("price"));
	        String category = request.getParameter("category");
	        Item newItem = new Item(title,description,seller,date,price,category);
	        boolean id = itemDAO.insert(newItem);
	        sessionUser.setDailyPostCount(sessionUser.getDailyPostCount() + 1);
    	}
    	else {
    		System.out.println("Only 5 post per day, you have already met.");
        	RequestDispatcher redirect = request.getRequestDispatcher("ItemForm.jsp");
        	redirect.forward(request,response);
    	}
        
    	displayHome(request, response);
    }
    
    
    private String getCurrentDate() {
    	LocalDate today = LocalDate.now( ZoneId.of( "America/Montreal" ) ) ;
    	String currentDate = today.toString() ; 
    	return currentDate;
    }



	private void displayHome(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	List<Item> listItems = itemDAO.listAllItems();
        request.setAttribute("listItems", listItems); 
        
        
        
        
		request.setAttribute("sessionUser", sessionUser.getUsername());  
		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");     
        dispatcher.forward(request, response);
    }
	

	private void showProfile(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		
		
        String favsellerusername = request.getParameter("favsellerusername");
    	List<Item> Userposteditem = itemDAO.ItemsbySeller(favsellerusername);
        request.setAttribute("Userposteditem", Userposteditem);
        request.setAttribute("name", favsellerusername);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("sellerprofile.jsp");     
        dispatcher.forward(request, response);
    }
    

    private void searchItem(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException, ServletException {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("searchItem.jsp");       
    	String searchKey = request.getParameter("searchKey");
    	System.out.println(searchKey);
        if(searchKey == "") {

        	System.out.println("No search key entered.");
        	request.setAttribute("searchError", "No search key entered.");
        	RequestDispatcher redirect = request.getRequestDispatcher("searchItem.jsp");
        	redirect.forward(request,response);
        }
        else {
        	List<Item> searchResuts = itemDAO.searchItem(searchKey);
            request.setAttribute("listItems", searchResuts);       
            dispatcher = request.getRequestDispatcher("searchResult.jsp");     
            dispatcher.forward(request, response);
        }
    }


    public void listExpensive(HttpServletRequest request, HttpServletResponse response)
    		throws SQLException, IOException, ServletException {
    	List<Item> listExpensive = itemDAO.listExpensive();
    	request.setAttribute("listItems", listExpensive);
		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");     
        dispatcher.forward(request, response);
    }

    private void showFavItemList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	List<FavItem> listFavItem = favitemDAO.listFavItem(sessionUser.getUsername());
        request.setAttribute("listFavItem", listFavItem);  
        RequestDispatcher dispatcher = request.getRequestDispatcher("FavitemList.jsp");     
        dispatcher.forward(request, response);
    }

    private void showFavSellerList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	System.out.println(sessionUser.getUsername());
    	
    	
    	List<FavoriteSeller> listFavoriteSeller = favoritesellerDAO.searchFavSeller(sessionUser.getUsername());
        request.setAttribute("listFavoriteSeller", listFavoriteSeller);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("FavoriteSeller.jsp");     
        dispatcher.forward(request, response);
    }

    private void postReview(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException, ServletException {
    	
    	System.out.println("This page posts a review by: " + sessionUser.getUsername());
    	System.out.println("On the item by: " + sellerOfReviewedItem);

     	
    	checkElapsedTime();

    	
    	if(sessionUser.getUsername() == sellerOfReviewedItem) {
    		System.out.println("You cannot review your own item.");
    		request.setAttribute("reviewError", "You cannot review your own item.");
        	RequestDispatcher redirect = request.getRequestDispatcher("WriteReview.jsp");
        	redirect.forward(request,response);
    	}
    	if(sessionUser.getDailyReviewCount() < 5) {
	        String score = request.getParameter("score");
	        String reviewText = request.getParameter("reviewText");
	        String seller = sessionUser.getUsername();
	        String date = getCurrentDate();
	        Review newReview = new Review(itemForReview, score, reviewText, seller, date);
	        reviewDAO.insert(newReview);
	        sessionUser.setDailyReviewCount(sessionUser.getDailyReviewCount() + 1);
	    	displayHome(request, response);
    	}
    	else {
    		System.out.println("Daily review limit has been met.");
    		request.setAttribute("reviewError", "Already done with dality review limit.");
        	RequestDispatcher redirect = request.getRequestDispatcher("WriteReview.jsp");
        	redirect.forward(request,response);
    	}
    }
    
    private void showReviewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        itemForReview = Integer.parseInt(request.getParameter("itemID"));
        Item item = itemDAO.getItem(itemForReview);
        sellerOfReviewedItem = item.getSeller();
        System.out.println("Review for item: " + itemForReview);
        Item existingItem = itemDAO.getItem(itemForReview);
        
        List<Review> itemReviews = reviewDAO.searchReviews(itemForReview);
        request.setAttribute("listReviews", itemReviews);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("WriteReview.jsp");  
        request.setAttribute("item", existingItem);
        dispatcher.forward(request, response);
    }
    
    private void checkElapsedTime() {
    	
    	// If the user has not posted yet, begin tracking time
    	if(sessionUser.getDailyPostCount() == 0)
    		postStartTime = System.nanoTime();
    	if(sessionUser.getDailyReviewCount() == 0)
    		reviewStartTime = System.nanoTime();
    	
    	// Calculate Elapsed time
    	long endTime = System.nanoTime();
    	long postTimeElapsed = endTime - postStartTime;
    	long reviewTimeElapsed = endTime - reviewStartTime;
        double elapsedPostTimeInSeconds = (double) postTimeElapsed / 1_000_000_000;
        double elapsedReviewTimeInSeconds = (double) reviewTimeElapsed / 1_000_000_000;

        // If a day has passed then reset the post limit and restart the start time
        if(elapsedPostTimeInSeconds > 86400) {
        	sessionUser.setDailyPostCount(0);
        	postStartTime = System.nanoTime();
        }
        if(elapsedReviewTimeInSeconds > 86400) {
        	sessionUser.setDailyReviewCount(0);
        	postStartTime = System.nanoTime();
        }
        else
        	return;
    }


    
    private void addFavitem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int itemID = Integer.parseInt(request.getParameter("itemID"));
        favitemDAO.insert(itemID, sessionUser.getUsername());
    	try {
			showFavItemList(request, response);
		} catch (ServletException e) {

			e.printStackTrace();
		}
    }


    private void addFavoriteseller(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String favsellersername = request.getParameter("seller");
        favoritesellerDAO.insert(sessionUser.getUsername(), favsellersername);
        try {
			showFavSellerList(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    private void deleteFavItem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int itemID = Integer.parseInt(request.getParameter("itemID"));
        favitemDAO.delete(itemID, sessionUser.getUsername());
    	try {
    		showFavItemList(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void deleteFavoriteSeller(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String favsellerusername = request.getParameter("favsellerusername");
        favoritesellerDAO.delete(sessionUser.getUsername(), favsellerusername);
        try {
			showFavSellerList(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    private void validateUserLogin(HttpServletRequest request, HttpServletResponse response)
    		throws SQLException, IOException, ServletException {

    	RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
    	String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(userDAO.isBanned(username)) {
    		request.setAttribute("loginError", "You have been banned.");
        	RequestDispatcher redirect = request.getRequestDispatcher("login.jsp");
        	redirect.forward(request,response);
    	}

        if(userDAO.validateUser(username, password)) {
        	sessionUser = userDAO.getUser(username);
        	displayHome(request, response);
        }
        else {
	       	request.setAttribute("loginError", "The username/password is invalid or the user has been banned.");
	       	RequestDispatcher redirect = request.getRequestDispatcher("login.jsp");
	       	redirect.forward(request,response);
        	
        }
    }
    

    private void registerUser(HttpServletRequest request, HttpServletResponse response) 
    		throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher; 
		
    	String username = request.getParameter("username");
    	System.out.println(username);
    	String password = request.getParameter("password");
    	String confirmPassword = request.getParameter("confirmPassword");
    	String firstName = request.getParameter("firstName");
    	String lastName = request.getParameter("lastName");
    	String email = request.getParameter("email");
    	String confirmEmail = request.getParameter("confirmEmail");
    	String gender = request.getParameter("gender");
    	int age = Integer.parseInt(request.getParameter("age"));
    	
		if(!(password.equals(confirmPassword))) {
			dispatcher = request.getRequestDispatcher("register.jsp");
    		dispatcher.forward(request, response);
		}
		
		if(!(email.equals(confirmEmail))) {
			dispatcher = request.getRequestDispatcher("register.jsp");
    		dispatcher.forward(request, response);
		}
		
		else if (!userDAO.duplicateUsername(username) && !userDAO.duplicateEmail(email)) {
    		User user = new User(username, password, firstName, lastName, email, gender, age);
    		userDAO.insert(user);
    		sessionUser = user;
//    		sessionUser.setDailyPostCount(0);
//    		sessionUser.setDailyReviewCount(0);
        	displayHome(request, response);
        }
    	else  {
    		request.setAttribute("registerError", "email already in use with another account.");
        	RequestDispatcher redirect = request.getRequestDispatcher("register.jsp");
        	redirect.forward(request,response); }
    	}
		
    	


	private void banUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String userToBan = request.getParameter("seller");
        userDAO.banUser(userToBan);
        try {
			displayHome(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
    }
    
    private void unbanUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String userToUnban = request.getParameter("seller");
        userDAO.unbanUser(userToUnban);
        try {
			displayHome(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
    }
    
    
    
    private void showAllInfo(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	
    	List<String> allUsersByName = userDAO.listUsersFullNames(); 
        request.setAttribute("AllUsers", allUsersByName);
    
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("info.jsp");     
        dispatcher.forward(request, response);
    	
    }
    
    private void showBothFav(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("info.jsp");

    	String userX = request.getParameter("userX");
    	String userY = request.getParameter("userY");
    	System.out.println("UserX: " + userX);
    	System.out.println("UserY: " + userY);
    	
    	String[] nameSplit = userX.split(" ");
        String firstX = nameSplit[0];
        String lastX = nameSplit[1];
        
        nameSplit = userY.split(" ");
        String firstY = nameSplit[0];
        String lastY = nameSplit[1];
        
        userX = userDAO.getUsername(firstX, lastX);
        userY = userDAO.getUsername(firstY, lastY);
        
    	List<String> BothFav = favoritesellerDAO.getBothfav(userX, userY);
        request.setAttribute("BothFav", BothFav);       
        dispatcher = request.getRequestDispatcher("bothfavlist.jsp");     
        dispatcher.forward(request, response);
    }


private void samedate(HttpServletRequest request, HttpServletResponse response) 
		throws SQLException, IOException, ServletException {
	
	String category1 = request.getParameter("category1");
	String category2 = request.getParameter("category2");
	List<User> samedate = userDAO.samedate(category1, category2);
	request.setAttribute("listUsers", samedate);
	RequestDispatcher dispatcher = request.getRequestDispatcher("samedate.jsp");
	dispatcher.forward(request, response);
       }	
	
	}




