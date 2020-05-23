<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.UserDAO"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type content="text/html; charest=utf-8">
<title>Home</title>

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
			
						<br><li style="float:right"><a href="showLoginForm">Log Out</a></li><br>


		</ul>
		
		
   <main>
		<h1>Favorite Sellers</h1>
		<div align="center">
        <table border="1" cellpadding="5">
            <tr>
                <th>UserName</th>
                <th>FavSellerUserName</th>
         </div>
            </tr>
            <c:forEach var="FavoriteSeller" items="${listFavoriteSeller}">
                <tr>
                    <td><c:out value="${FavoriteSeller.username}" /></td>
                    <td><c:out value="${FavoriteSeller.favsellerusername}" /></td>
                    <td>
                        <a href="profiledata?favsellerusername=<c:out value='${FavoriteSeller.favsellerusername}' />">View Profile</a> 
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deleteFavSeller?favsellerusername=<c:out value='${FavoriteSeller.favsellerusername}' />">Delete</a> 
                    </td>
                </tr>
            </c:forEach> 
        </table>
    	</div>
    </main>
	
</body>
</html>
		