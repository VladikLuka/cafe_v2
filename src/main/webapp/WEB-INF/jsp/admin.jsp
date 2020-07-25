<%@ taglib prefix="local" uri="/tld/localization.tld" %>
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
    <link rel="shortcut icon" href="../../static/img/favico.svg" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/sweetalert2.css">
    <script src="../../static/js/sweetalert2.all.js"></script>
    <script src="../../static/js/jquery-3.5.0.js"></script>

    <link rel="stylesheet" href="../../static/css/user.css">
</head>
<body>
<jsp:include page="part/header_v2.jsp"/>
<div class="admin_panel col-md-4">
    <h2><local:Localization message="admin.expected.orders"/></h2>
    <c:forEach items="${applicationScope.cache.mapOrders}" var="order">
        <c:forEach items="${order.value}" var="ord">
        <c:if test="${ord.status eq 'PAID' or ord.status eq 'EXPECTED' and ord.method ne 'CREDIT'}">
                <div id="delete${ord.orderId}" class="del-div">
                    <div id="wrap">${ord.time}</div>
                    <div id="prod_wrap">
                        <button type="button" class="close_ord col-md-1" id="close_btn">
                            <svg class="close_ord" data-id="${ord.orderId}" width="20px" height="40px" viewBox="0 0 20 15" xmlns="http://www.w3.org/2000/svg">
                                <title>check</title>
                                <desc>Created with Sketch.</desc>
                                <defs></defs>
                                <g id="BT-PP-Checkout-Demo" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <g id="1-–-Pick-Copy-5" transform="translate(-314.000000, -210.000000)" stroke-linecap="square" stroke="#009ADA" stroke-width="3">
                                        <g id="Pay-with-PayPal" transform="translate(12.000000, 180.000000)">
                                            <g id="check" transform="translate(305.000000, 32.000000)">
                                                <path d="M0.0363618827,6.53022119 L3.80930175,10.3999031" id="short"></path>
                                                <path d="M3.92708333,10.5658622 L14.1174145,0.114240504" id="long"></path>
                                            </g>
                                        </g>
                                    </g>
                                </g>
                            </svg>
                        </button>

                        <button class="violate_ord col-md-1" data-id="${ord.orderId}">
                            <svg class="violate_ord" data-id="${ord.orderId}" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48" width="24px" height="24px">
                                <path class="violate_ord" data-id="${ord.orderId}" fill="#f44336" d="M44,24c0,11.045-8.955,20-20,20S4,35.045,4,24S12.955,4,24,4S44,12.955,44,24z"/>
                                <path class="violate_ord" data-id="${ord.orderId}" fill="#fff" d="M29.656,15.516l2.828,2.828l-14.14,14.14l-2.828-2.828L29.656,15.516z"/>
                                <path fill="#fff" d="M32.484,29.656l-2.828,2.828l-14.14-14.14l2.828-2.828L32.484,29.656z"/>
                            </svg>
                        </button>

                        <button class="col-md-1" data-id="${ord.orderId}" type="button" id="ord">
                            <a href="/checkout/${ord.orderId}">
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

<div class="admin_panel col-md-4">

    <h2><local:Localization message="admin.credit.orders"/></h2>
    <c:forEach items="${applicationScope.cache.mapOrders}" var="order">
        <c:forEach items="${order.value}" var="ord">
            <c:if test="${ord.status eq 'PAID' and ord.method eq 'CREDIT'}">
                <div id="delete${ord.orderId}" class="del">
                    <div id="credit_order_time">${ord.time}</div>
                    <div id="time_warp">
                        <button type="button" class="close_ord">
                            <svg class="close_ord" data-id="${ord.orderId}" width="20px" height="15px" viewBox="0 0 20 15" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">
                                <title>check</title>
                                <desc>Created with Sketch.</desc>
                                <defs></defs>
                                <g id="BT-PP-Checkout-Demo" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <g id="1-–-Pick-Copy-5" transform="translate(-314.000000, -210.000000)" stroke-linecap="square" stroke="#009ADA" stroke-width="3">
                                        <g id="Pay-with-PayPal" transform="translate(12.000000, 180.000000)">
                                            <g id="check" transform="translate(305.000000, 32.000000)">
                                                <path d="M0.0363618827,6.53022119 L3.80930175,10.3999031" id="short"></path>
                                                <path d="M3.92708333,10.5658622 L14.1174145,0.114240504" id="long"></path>
                                            </g>
                                        </g>
                                    </g>
                                </g>
                            </svg>
                        </button>

                        <button class="violate_ord" data-id="${ord.orderId}">
                            <svg class="violate_ord" data-id="${ord.orderId}" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48" width="24px" height="24px">
                                <path class="violate_ord" data-id="${ord.orderId}" fill="#f44336" d="M44,24c0,11.045-8.955,20-20,20S4,35.045,4,24S12.955,4,24,4S44,12.955,44,24z"/>
                                <path class="violate_ord" data-id="${ord.orderId}" fill="#fff" d="M29.656,15.516l2.828,2.828l-14.14,14.14l-2.828-2.828L29.656,15.516z"/>
                                <path fill="#fff" d="M32.484,29.656l-2.828,2.828l-14.14-14.14l2.828-2.828L32.484,29.656z"/>
                            </svg>
                        </button>

                        <button data-id="${ord.orderId}" type="button" id="checkout">
                            <a href="/checkout/${ord.orderId}">
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

<div class="admin_panel col-md-4">
    <h2><local:Localization message="admin.users"/></h2>
    <c:forEach items="${applicationScope.cache.mapUser}" var="user">
            <div id="users" >
                <div id="info">${user.value.id} ${user.value.mail}</div>
                <div id="info_wrap">
                    <button data-id="${user.value.id}" type="button" id="info_btn">
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
        <div class="col-md-6" id="popup_wrap">
            <label for="user_id"><local:Localization message="admin.id"/></label><input type="text" class="form-control" id="user_id" disabled>
            <label for="user_name"><local:Localization message="admin.name"/></label><input type="text" class="form-control" id="user_name" disabled>
            <label for="user_surname"><local:Localization message="admin.surname"/></label><input type="text" class="form-control" id="user_surname" disabled>
            <label for="user_email"><local:Localization message="admin.email"/></label><input type="text" class="form-control" id="user_email" disabled>
            <label for="user_phone"><local:Localization message="admin.phone"/></label><input type="text" class="form-control" id="user_phone" disabled>
            <label for="user_role"><local:Localization message="admin.role"/></label><input type="text" class="form-control" id="user_role" disabled>
            <label for="user_money"><local:Localization message="admin.balance"/></label><input type="text" class="form-control" id="user_money" disabled>
            <label for="user_point"><local:Localization message="admin.loyalty.points"/></label><input type="text" class="form-control" id="user_point" disabled>
            <lable for="isBanned"><local:Localization message="admin.is.banned"/></lable><input type="text" class="form-control" id="isBanned" disabled>
        </div>
        <div class="col-md-6 usr_info">
            <div id="div_add_money">
                <label for="add_user_money"><local:Localization message="admin.add.money"/></label><input type="text" class="form-control" id="add_user_money">
                <button type="submit" class="btn btn-success" id="add_money" style=""><local:Localization message="admin.add.money"/></button>
            </div>
            <div id="div_grab_money">
                <label for="substr_user_money"><local:Localization message="admin.grab.money"/></label><input type="text" class="form-control" id="substr_user_money">
                <button type="submit" class="btn btn-success" id="subtract_money"><local:Localization message="admin.grab.money"/></button>
            </div>
            <div id="div_add_point">
                <label for="give_user_points"><local:Localization message="admin.add.points"/></label><input type="text" class="form-control" id="give_user_points">
                <button type="submit" class="btn btn-success" id="add_points"><local:Localization message="admin.add.points"/></button>
            </div>
            <div id="div_grab_points">
                <label for="grab_user_points"><local:Localization message="admin.grab.points"/></label><input type="text" class="form-control" id="grab_user_points">
                <button type="submit" class="btn btn-success" id="grab_points"><local:Localization message="admin.grab.points"/></button>
            </div>
            <div style="margin-top: 12px">
                <button type="submit" class="btn btn-success" id="ban_user"><local:Localization message="admin.ban.user"/></button>
                <button type="submit" class="btn btn-success" id="unban_user"><local:Localization message="admin.unban.user"/></button>
            </div>
            <div style="margin-top: 12px">
                <button type="submit" class="btn btn-success" id="make_admin"><local:Localization message="admin.make.admin"/></button>
                <button type="submit" class="btn btn-success" id="make_user"><local:Localization message="admin.make.user"/></button>
            </div>
        </div>

    </div>
</div>

<jsp:include page="part/footer.jsp"/>

</body>
</html>
