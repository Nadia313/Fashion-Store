<!--Athor Nadia Chowdhury-->

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
		


	<br></br>
	</ul>


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


			<td><b>seller</b></td>



		</tr>
		<%
			try {
				connection = DriverManager.getConnection(connectionUrl + dbName, userId, password);
				statement = connection.createStatement();
				String sql = "select username from projectdb.user where username not in (select seller from review where score like '%poor%')";
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