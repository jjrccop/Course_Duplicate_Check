<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: jjrcc
  Date: 2019/9/5
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table border="1">
    <tr>
        <th>起</th>
        <th>至</th>
        <th>姓名</th>
    </tr>
    <c:forEach items="${res}" var="partTime">
        <tr>
            <td><fmt:formatDate value="${partTime.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td><fmt:formatDate value="${partTime.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td><c:forEach items="${partTime.nameList}" var="name">
                ${name}&nbsp;
            </c:forEach></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
