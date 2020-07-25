<%--
  Created by IntelliJ IDEA.
  User: vladi
  Date: 29.06.2020
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pizza</title>
    <link rel="shortcut icon" href="../../static/img/favico.svg" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/catalog.css">
    <script src="../../static/js/sweetalert2.all.js"></script>


</head>
<body>

<jsp:include page="part/header_v2.jsp"/>

    <h1 id="ban_text" style="text-align: center">Sorry, you have been banned</h1>
    <button type="submit"  class="btn btn-link js-button-logout">Log out</button>


    <jsp:include page="part/footer.jsp"/>
</body>
</html>
