<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jjrcc
  Date: 2019/9/5
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/query" method="post">
    <label>
        <input type="text" name="part">
    </label>
    <label>
        <input type="submit" value="+">
    </label>
</form>
<table border="1">
    <th>学号</th>

    <c:forEach items="${participants}" var="part">
    <tr>
        <td>${part}</td>
    </tr>
    </c:forEach>

</table>
<form action="/start" method="post">
    <input type="date" name="start">
    <input type="date" name="end">
    <input type="submit" value="查询">
</form>
</body>
</html>
