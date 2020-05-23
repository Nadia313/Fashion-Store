<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="dao.UserDAO"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Online Store Application</title>
</head>
<body>

    <center>
    <h2>Login</h2>
    </center>    
    <main>
    	<form action="login" method="post">
    	<label><b>Username</b></label>
    	<br><br><input type="text" placeholder="Enter Username" name="username" value="${user.username}"></br></br>
		<label><b>Password</b></label> 
		<br><br><input type="text" placeholder="Enter Password" name="password" value="${user.password}"></br></br>
	   	<button type="submit" id="login" value="login">Log in</button>
    	</form>	
    	
    	<form action = "showRegistrationForm" method ="post">
      		<button type="submit" id="showRegistrationForm" value="showRegistrationForm">Sign Up</button>
    	</form>
	</main>
</body>
</html>