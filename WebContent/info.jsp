<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.ItemDAO"%>

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
			<a href="info.jsp">All data informations</a>

			<br><li style="float:right"><a href="showLoginForm">Log Out</a></li><br>

		</ul>
		
		
		<h1>List user who are favorited by both X and Y.</h1>
		<form action="BothFav" method="post">
					<label><b>User X</b></label>
						<br><br><select name = "userX" value = "${userX}">
							<c:forEach var="user" items="${AllUsers}">
            					<option>
                					<c:out value="${user}"/>
            					</option>
        					</c:forEach>
		              	</select><br><br>
					<label><b>User Y</b></label>
						<br><br><select name = "userY" value = "${userY}">
							<c:forEach var="user" items="${AllUsers}">
            					<option>
                					<c:out value="${user}"/>
            					</option>
        					</c:forEach>
		              	</select><br><br>
		              	
		              	
			<button type="submit" id="BothFav" value="BothFav">View Both Favorite</button> 
		</form>	


    	<h1>List users with most Items since 5/1/2018</h1>
		<div align="center">
        <table border="0" cellpadding="5">
            <tr>
                <th>Username</th>
            </tr>
            <c:forEach var="user" items="${mostItems}">  
                <tr>
                    <td><c:out value="${user}" /></td>
                </tr>
            </c:forEach> 
        </table>


</body>
</html>