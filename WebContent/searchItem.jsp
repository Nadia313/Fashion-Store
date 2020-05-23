//Author Nadia Chowdhury

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="dao.ItemDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search your item</title>
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
			
			<li style="float:right"><a href="search">Search</a></li>		
			<br><li style="float:right"><a href="showLoginForm">Log Out</a></li><br>


		</ul>

 	<main>
		<h1>Search Items here</h1>
		<form action="searchItem" method="post">

						<input type="text" placeholder="Find your item here" name="searchKey" value="${searchKey}">

			<button type="submit" id="searchItem" value="searchItem">Search</button> 
		</form>	
	</main>
	

</body>
</html>