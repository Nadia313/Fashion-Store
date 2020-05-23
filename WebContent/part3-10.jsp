<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<%@page import="java.io.IOException"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>



<body>

<body>
	<ul>
		<a href="home">Home</a> &nbsp;
		<a href="ItemForm">Post an Item</a> &nbsp;
		<a href="FavSeller">Favorite Seller</a> &nbsp;
		<a href="FavoriteItem">Favorite Item</a> &nbsp;
		<a href="addreview.jsp">Display Review</a> &nbsp;
		</ul>


	<br></br>


	<%
		String id = request.getParameter("userId");
		String driverName = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://localhost:3306/";
		String dbName = "projectdb";
		String userId = "john";
		String password = "pass1234";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
	%>
	<table align="center" cellpadding="5" cellspacing="5" border="1">
		<tr>
		</tr>
		<tr bgcolor="#FFFFFF">


			<td><b>Seller1</b></td>
			<td><b>Seller2</b></td>			



		</tr>
		<%
			try {
				connection = DriverManager.getConnection(connectionUrl + dbName, userId, password);
				statement = connection.createStatement();
				String sql = "SELECT R.seller AS userWrite, I.seller userReceive " +
						"FROM review R " +
						"INNER JOIN item I ON I.itemID = R.itemID " +
			            "WHERE R.score = 'excellent'  " +
						"GROUP BY R.seller, I.seller";
				resultSet = statement.executeQuery(sql);
				while (resultSet.next()) {
		%>
		<tr bgcolor="#F0F0F0">


	
			<td><%=resultSet.getString("username")%></td>



		</tr>
		<%
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		%>
	</table>
	</form>

</body>
</html>

</body>
</html>