<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.UserDAO"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Favorite Items</title>
</head>

<body>	
	
		<ul>
			 <a href="home">Home</a>
			 &nbsp;
			<a href="ItemForm">Post an Item</a>
			&nbsp;
			<a href="FavSeller">Favorite Sellers</a>
			&nbsp;
			<a href="FavoriteItem">Favorite Item</a>
			<li style="float:right"><a href="Search">Search</a></li>
			<br><li style="float:right"><a href="showLoginForm">Log Out</a></li><br>

		</ul>
		
<main>
		<h1>Favorite Items</h1>
		<div align="center">
        <table border="1" cellpadding="5">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Actions</th>
         </div>
            </tr>
            <c:forEach var="FavItem" items="${listFavItem}">
            <tr>
                    <td><c:out value="${FavItem.itemID}" /></td>
                    <td><c:out value="${FavItem.username}" /></td>
              
                    <td>
                        <a href="deleteFavItem?itemID=<c:out value='${FavItem.itemID}' />">Delete</a> 
                    </td>
            </tr>
            </c:forEach>
		</table>
		</div>
	
</body>
</html>
