<%@page import="java.io.IOException"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>


<body>
	<ul>
		<a href="home">Home</a> &nbsp;
		<a href="ItemForm">Post an Item</a> &nbsp;
		<a href="FavSeller">Favorite Seller</a> &nbsp;
		<a href="FavoriteItem">Favorite Item</a> &nbsp;
		<a href="addreview.jsp">Display Review</a> &nbsp;
		<a href="Part3.jsp">All data informations</a> &nbsp;

		<br>


	<br></br>


	<%
		String id = request.getParameter("userid");
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


			<td><b>Item ID</b></td>
			<td><b>title</b></td>
			<td><b>Description</b></td>
			<td><b>Category</b></td>
			<td><b>Price</b></td>
			<td><b>score</b></td>
			<td><b>remark</b></td>
			<td><b>seller</b></td>
			<td><b>date</b></td>


		</tr>
		<%
			try {
				connection = DriverManager.getConnection(connectionUrl + dbName, userId, password);
				statement = connection.createStatement();
				String sql = "select * from item J, review R where R.itemid = J.itemid";
				resultSet = statement.executeQuery(sql);
				while (resultSet.next()) {
		%>
		<tr bgcolor="#F0F0F0">



			<td><%=resultSet.getString("itemid")%></td>
			<td><%=resultSet.getString("title")%></td>
			<td><%=resultSet.getString("description")%></td>
			<td><%=resultSet.getString("Category")%></td>
			<td><%=resultSet.getString("price")%></td>
			<td><%=resultSet.getString("score")%></td>
			<td><%=resultSet.getString("reviewtext")%></td>
			<td><%=resultSet.getString("seller")%></td>
			<td><%=resultSet.getString("date")%></td>



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