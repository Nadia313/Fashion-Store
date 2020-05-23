<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<main>
		<h1>Mutual Friends</h1>
		<div align="center">
        <table border="0" cellpadding="5">
            <tr>
                <th>Username</th>
            </tr>
            <c:forEach var="FavoriteSeller" items="${BothFav}">
                <tr>
                    <td><c:out value="${FavoriteSeller}" /></td>
                </tr>
            </c:forEach> 
        </table>
    	</div>
    </main>


</body>
</html>