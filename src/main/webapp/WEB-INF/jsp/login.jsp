<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>

<body>
${ERROR}
<form method="post" action="/login">
    用户：
    <label>
        <input type="text" name="userName">
    </label>
    <br />
    密码：
    <label>
        <input type="password" name="passWord">
    </label>
    <input type="submit" value="Submit">
</form>
</body>
</html>
