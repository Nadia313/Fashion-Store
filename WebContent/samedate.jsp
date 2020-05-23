<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h2>List the users who posted at least two items that are posted on the same day, one has a category of X and another has a category of Y.</h2>
<form action = "samedate" method = "post">
	<input type = "text" name = "category1" placeholder = "Enter category 1">
	<input type = "text" name = "category2" placeholder = "Enter category 2">
	<input type = "submit" value = "SUBMIT CATEGORY">
</form>
<br>
<br>
<table border="1" cellpadding="5">
            <caption>List of Users</caption>
            <tr>
            	<th>Username</th>
                <th>Favorite?</th>
            </tr>
            <c:forEach var="users" items = "${listUsers}">
            <tr>
            		<td><c:out value="${users.username}" /></td>
                       <td> <a href="addFavSeller?seller=<c:out value='${item.seller}' />">Add Favorite Seller</a> </td>
            </c:forEach>
		</table>
</div>

</body>
</html>