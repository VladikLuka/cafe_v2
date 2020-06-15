<%--
  Created by IntelliJ IDEA.
  User: vladi
  Date: 21.05.2020
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin panel</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="../../static/css/user.css">
</head>
<body>
<jsp:include page="part/header_v2.jsp"/>

<form enctype="multipart/form-data" method="post">
    <p>Загрузите ваши фотографии на сервер</p>
    <p><input type="file" name="photo" multiple accept="image/png,image/jpeg">
        <input type="submit" value="Отправить"></p>
</form>


<jsp:include page="part/footer.jsp"/>
</body>
</html>
