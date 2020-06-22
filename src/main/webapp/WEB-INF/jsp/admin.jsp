<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
    <script src="../../static/js/script.js"></script>
    <script src="../../static/js/main2.js"></script>

    <link rel="stylesheet" href="../../static/css/user.css">
</head>
<body>
<jsp:include page="part/header_v2.jsp"/>
<div class="container_right" style="left: 0; margin-left: 30px">
    <h2>Expected orders</h2>
    <c:forEach items="${applicationScope.cache.mapOrders}" var="order">
        <c:forEach items="${order.value}" var="ord">
        <c:if test="${ord.status eq 'PAID' or ord.status eq 'EXPECTED'}">
                <div id="delete${ord.order_id}"  style="background-color: #767676; min-height: 40px;max-width: 500px; max-height: 300px; margin-top: 20px; border-radius: 15px; margin-left: 50px">
                    <div style="display:inline-block; color: white; margin-left: 30px">${ord.time}</div>
                    <div style="float: right; display: inline-block;">
                        <button type="button" style="outline: none; border: 0;  background: transparent;">
                                <svg class="123"  data-id="${ord.order_id}" xmlns="http://www.w3.org/2000/svg" width="22" height="26" viewBox="0 0 22 22">
                                    <g fill="#FFF">
                                        <path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"></path>
                                    </g>
                                </svg>
                        </button>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </c:forEach>

</div>

<div class="container_right" style="right: 0; margin-right: 30px">
    <h2>Users</h2>
    <c:forEach items="${applicationScope.cache.mapUser}" var="user">
            <div id="users"  style="background-color: #767676; min-height: 40px;max-width: 500px; max-height: 300px; margin-top: 20px; border-radius: 15px; margin-left: 50px">
                <div style="display:inline-block; color: white; margin-left: 30px">${user.value.id} ${user.value.mail}</div>
                <div style="float: right; display: inline-block;">
                    <button data-id="${user.value.id}" type="button" style="outline: none; border: 0;  background: transparent;">
                        <svg class="show_info" data-id="${user.value.id}" xmlns="http://www.w3.org/2000/svg" width="22" height="26" viewBox="0 0 22 22">
                            <g fill="#FFF">
                                <path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"></path>
                            </g>
                        </svg>
                    </button>
                </div>
            </div>
    </c:forEach>
</div>

<div class="overlay user_overlay">
    <div class="popup user_popup">

        <input type="text" class="form-control" id="user_id" disabled>
        <input type="text" class="form-control" id="user_email" disabled>
        <input type="text" class="form-control" id="user_phone" disabled>
        <input type="text" class="form-control" id="user_money" disabled>
        <input type="text" class="form-control" id="user_point" disabled>

    </div>
</div>


</body>
</html>
