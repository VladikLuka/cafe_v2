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
    <link rel="stylesheet" href="../../static/css/sweetalert2.css">
    <script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
    <script src="../../static/js/sweetalert2.all.js"></script>

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
                    <div style="display:inline-block;color: white; margin-left: 30px">${ord.time}</div>
                    <div style="float: right; display: inline-block;">
                        <button type="button" class="close_ord" style="padding-bottom: 10px; outline: none; border: 0; display: inline-block; background: transparent; ">
                            <svg class="close_ord" data-id="${ord.order_id}" width="20px" height="15px" viewBox="0 0 20 15" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">
                                <title>check</title>
                                <desc>Created with Sketch.</desc>
                                <defs></defs>
                                <g id="BT-PP-Checkout-Demo" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">
                                    <g id="1-–-Pick-Copy-5" sketch:type="MSArtboardGroup" transform="translate(-314.000000, -210.000000)" stroke-linecap="square" stroke="#009ADA" stroke-width="3">
                                        <g id="Pay-with-PayPal" sketch:type="MSLayerGroup" transform="translate(12.000000, 180.000000)">
                                            <g id="check" transform="translate(305.000000, 32.000000)" sketch:type="MSShapeGroup">
                                                <path d="M0.0363618827,6.53022119 L3.80930175,10.3999031" id="short"></path>
                                                <path d="M3.92708333,10.5658622 L14.1174145,0.114240504" id="long"></path>
                                            </g>
                                        </g>
                                    </g>
                                </g>
                            </svg>
                        </button>

                        <button class="violate_ord" data-id="${ord.order_id}" style="outline: none;border: 0; display: inline-block; background: transparent; ">
                            <svg class="violate_ord" data-id="${ord.order_id}" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48" width="24px" height="24px"><path class="violate_ord" data-id="${ord.order_id}" fill="#f44336" d="M44,24c0,11.045-8.955,20-20,20S4,35.045,4,24S12.955,4,24,4S44,12.955,44,24z"/><path class="violate_ord" data-id="${ord.order_id}" fill="#fff" d="M29.656,15.516l2.828,2.828l-14.14,14.14l-2.828-2.828L29.656,15.516z"/><path fill="#fff" d="M32.484,29.656l-2.828,2.828l-14.14-14.14l2.828-2.828L32.484,29.656z"/></svg>
                        </button>

                        <button data-id="${ord.order_id}" type="button" style="display: inline-block;outline: none; border: 0; background: transparent;">
                            <a href="/checkout/${ord.order_id}">
                            <svg class="order_info" data-id="${user.value.id}" xmlns="http://www.w3.org/2000/svg" width="22" height="26" viewBox="0 0 22 22">
                                    <g fill="#FFF">
                                        <path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"></path>
                                    </g>
                            </svg>
                            </a>
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
    <div class="popup user_popup" style="width: 800px; height: 800px;">
        <div class="col-md-6" style="width: 50%;">
            <label for="user_id">id</label><input type="text" class="form-control" id="user_id" disabled>
            <label for="user_name">name</label><input type="text" class="form-control" id="user_name" disabled>
            <label for="user_surname">surname</label><input type="text" class="form-control" id="user_surname" disabled>
            <label for="user_email">email</label><input type="text" class="form-control" id="user_email" disabled>
            <label for="user_phone">phone</label><input type="text" class="form-control" id="user_phone" disabled>
            <label for="user_money">money</label><input type="text" class="form-control" id="user_money" disabled>
            <label for="user_point">points</label><input type="text" class="form-control" id="user_point" disabled>
            <lable for="isBanned">is banned</lable><input type="text" class="form-control" id="isBanned" disabled>
        </div>
        <div class="col-md-6" style="width: 50%;">
            <label for="add_user_money">add money</label><input type="text" class="form-control" id="add_user_money">
            <button type="submit" class="btn btn-success" id="add_money" style="display: block">add money</button>
            <label for="substr_user_money">grab money</label><input type="text" class="form-control" id="substr_user_money">
            <button type="submit" class="btn btn-success" id="subtract_money" style="display: block">grab money</button>
            <label for="give_user_points">add points</label><input type="text" class="form-control" id="give_user_points">
            <button type="submit" class="btn btn-success" id="add_points" style="display: block">add points</button>
            <label for="grab_user_points">grab points</label><input type="text" class="form-control" id="grab_user_points">
            <button type="submit" class="btn btn-success" id="grab_points" style="display: block">grab points</button>
            <button type="submit" class="btn btn-success" id="ban_user" style="display:inline-block;">ban user</button>
            <button type="submit" class="btn btn-success" id="unban_user" style="display:inline-block;">unbun user</button>
        </div>

    </div>
</div>


</body>
</html>
