<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="dao.UserDAO"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type content="text/html; charest=utf-8">
<title>Sign Up</title>
</head>
<body>

<ul>

			<li style="float:right"><a href="showLoginForm">Log in</a></li>
</ul>

	<h1>Sign Up</h1>
	<form action="register" method="post">
	
		<label><b>Username</b></label>
			<br><br><input type="text" placeholder="Enter Username" name="username" value="${user.username}"></br></br>
		<label><b>Password</b></label> 
			<br><br><input type="text" placeholder="Enter Password" name="password" value="${user.password}"></br></br>
		<label><b>Confirm Password</b></label>
			<br><br><input type="password" placeholder="Confirm Password" name="confirmPassword" value="${confirmPassword}"></br></br>
		
		<label><b>First Name</b></label> 
			<br><br><input type="text" placeholder="Enter First Name" name="firstName" value="${user.firstName}"></br></br>
		<label><b>Last Name</b></label> 
			<br><br><input type="text" placeholder="Enter Last Name" name="lastName" value="${user.lastName}"></br></br>
		<label><b>Email</b></label> 
			<br><br><input type="text" placeholder="Enter Email Address" name="email" value="${user.email}"></br></br>
		<label><b>Confirm E-Mail</b></label>
			<br><br><input type="text" placeholder="Confirm E-Mail Address" name="confirmEmail" value="${confirmEmail}"></br></br>
		
		<label><b>Gender</b></label> 
			<br><br><select name = "gender" value = "${user.gender}"></br></br>
			<option>Male</option>
			<option>Female</option>
			<option>Other</option>
		</select><br><br>
	   <label><b>Age</b></label>
	   		<br><br><input type="number" placeholder="Enter Age" name="age" value="${user.age}"></br></br>
	   		
	   	<button type="submit" id="register" value="register">Register</button>
	   	
	  	</form>
   </body>
</html>