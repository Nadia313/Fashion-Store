<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Online item</title>
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
			<li style="float:right"><a href="search">Search</a></li>
			<br><li style="float:right"><a href="showLoginForm">Log Out</a></li><br>


		</ul>
    <center>
        <h1>Fashion Closet</h1>

		<form action="postItem" method="post">
					<p><label><b>Title</b></label></p>
						<input type="text" placeholder="Enter Clothe name" name="title" value="${item.title}">
					<p><label><b>Description</b></label></p>
						<input type="text" placeholder="Enter the description" name="description" value="${item.description}">

				    <p><label><b>Price</b></label></p>
						<input type="number" placeholder="Enter the price" name="price" value="${item.price}">
						
					<p> <label><b>Category</b></label> </p>
						<input type="text" placeholder="Enter the category  name" name="category" value="${item.category}">

			
			<p><button type="submit" id="postItem" value="postItem">Post</button> </p>

		</form>	

</body>
</html>