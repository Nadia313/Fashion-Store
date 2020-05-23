<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Favorite Seller Profile</title>
</head>
<body>

		<ul>
			 <a href="home">Home</a>
			 &nbsp;
			<a href="ItemForm">Post an Item</a>
			&nbsp;
			<a href="FavSeller">Favorite Seller</a>
			&nbsp;
			<a href="FavoriteItem">Favorite Item</a>
			&nbsp;
			
		    <li style="float:right"><a href="search">Search</a></li>
			<br><li style="float:right"><a href="showLoginForm">Log Out</a></li><br>


		</ul>
		
	<main>
	    <h1><c:out value="${name}" /> 's Profile</h1>
		<div align="center">
        <table border="1" cellpadding="5">
               <h2>List of items</h2>
        </div>
            
		<div align="center">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Seller</th>
                <th>Date</th>
                <th>Price</th>
                <th>Category</th>
                <th>Actions</th>
       </div>
            </tr>
            <c:forEach var="item" items="${listItems}">
                <tr>
                    <td><c:out value="${item.itemID}" /></td>
                    <td><c:out value="${item.title}" /></td>
                    <td><c:out value="${item.description}" /></td>
                    <td><c:out value="${item.seller}" /></td>
                    <td><c:out value="${item.date}" /></td>
                    <td><c:out value="${item.price}" /></td>
                    <td><c:out value="${item.category}" /></td>


                    <td>
                    	<a href="writeReview?itemID=<c:out value='${item.itemID}' />">Write Review</a>
                    	&nbsp;&nbsp;&nbsp;&nbsp; 
                    	<a href="addFavitem?itemID=<c:out value='${item.itemID}' />">Add Favorite Item</a>  <%--need to finish --%>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                        <a href="addFavSeller?seller=<c:out value='${item.seller}' />">Add Favorite Seller</a>                    
                    
                    </td>
                </tr>
            </c:forEach>
        </table>
    	</div>
    </main>
	
</body>
</html>
		