<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="dao.UserDAO"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type content=" text/html; charest=utf-8">
<title>Home</title>

</head>

<body>
	<ul>
		<a href="home">Home</a> &nbsp;
		<a href="ItemForm">Post an Item</a> &nbsp;
		<a href="FavSeller">Favorite Seller</a> &nbsp;
		<a href="FavoriteItem">Favorite Item</a> &nbsp;
		<a href="info.jsp">All data informations</a>

		<br>
		<br>
		<form action="listExpensive">
			<input type="submit" value="List by Expensive">
		</form>
		</br>
		</br> &nbsp;
		<li style="float: right"><a href="search">Search</a></li>
		<br>
		<li style="float: right"><a href="showLoginForm">Log Out</a></li>
		<br>


	</ul>

	<main>

	<div align="center">
		<table border="1" cellpadding="5">
			<h2>List of items</h2>
			</div>

			<table border="1" cellpadding="5">

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
				<tr>
					<td><c:out value="${item.itemID}" /></td>
					<td><c:out value="${item.title}" /></td>
					<td><c:out value="${item.description}" /></td>
					<td><c:out value="${item.seller}" /></td>
					<td><c:out value="${item.date}" /></td>
					<td><c:out value="${item.price}" /></td>
					<td><c:out value="${item.category}" /></td>
					</div>
			</table>

			<h1>New Review</h1>
			<form action="postReview" method="post">

				<label><b>Score</b></label> <br>
				<br>
				<select name="score" value="${review.score}">
					<option>Excellent</option>
					<option>Good</option>
					<option>Fair</option>
					<option>Poor</option>
				</select><br>
				<br> <label><b>Comment</b></label> <input type="text"
					placeholder="Write a comment" name="reviewText"
					value="${review.reviewText}"> 
				</div>
				</div>
				<button type="submit" id="postReview" value="postReview">Post
					Review</button>
			</form>
			</main>
</body>
</html>