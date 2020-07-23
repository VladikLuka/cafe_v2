<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="local" uri="/tld/localization.tld" %>

<html>
<head>
    <title>User</title>

    <link rel="shortcut icon" href="../../static/img/favico.svg" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/user.css">

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="https://js.braintreegateway.com/js/braintree-2.32.1.min.js"></script>
</head>

<body>

<jsp:include page="part/header_v2.jsp"/>

<div class="wrapper">
    <div class="user_info container_left">
        <div class="inside_wrap">
            <div class="personal_info">
                <h2 id="account_label"><local:Localization message="user.account"/> | </h2>
                <button type="submit"  class="btn btn-link js-button-logout"><local:Localization message="user.log.out"/> </button>
                <div class="inside_wrap">
                    <h6 id="user_email_text"><local:Localization message="user.email"/></h6>
                    <div id="usr_mail">
                        <h5>${applicationScope.cache.getUser(sessionScope.userId).mail}</h5>
                    </div>
                    <h6><local:Localization message="user.loyalty.points"/></h6>
                    <div id="usr_points">
                        <h5>${applicationScope.cache.getUser(sessionScope.userId).loyaltyPoint}</h5>
                    </div>
                    <c:if test="${applicationScope.cache.getUser(sessionScope.userId).credit eq true}">

                        <c:forEach items="${applicationScope.cache.getOrders(sessionScope.userId)}" var="order">
                            <c:if test="${not empty order.creditTime}">
                                <c:if test="${order.status eq 'PAID'}">
                                    <div id="credit_text">
                                        <h5>You have a credit! <br>
                                        you should close credit before ${order.creditTime} <br>
                                        <button class="btn btn-info" type="submit" id="closeCredit" data-userId="${sessionScope.userId}" data-orderId="${order.orderId}">Close credit</button>
                                        </h5>
                                    </div>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>
                <div class="personal_address">
                    <h2><local:Localization message="user.addresses"/></h2>
                    <div class="addresses">
                        <c:forEach items="${applicationScope.cache.getAddresses(sessionScope.userId)}" var="address">
                            <c:if test="${address.available eq true}">
                                <div class="address_wrapper" data-id=${address.id} >
                                    <div class="user_address">
                                        <div class="user_city_street">
                                            <div class="user_city">
                                                <h5>${address.city}</h5>
                                            </div>
                                            <div class="user_street">
                                                <h5>${address.street}</h5>
                                            </div>
                                        </div>
                                        <div class="user_house_flat">
                                            <div class="user_house">
                                                <h6>House: ${address.house}</h6>
                                            </div>
                                            <div class="user_flat">
                                                <h6>Flat: ${address.flat}</h6>
                                            </div>
                                        </div>
                                    </div>
                                    <button class="btn btn-link address" id="delete_user_address" data-id=${address.id}>&#10005</button>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <button class="btn btn-success" id="add_address"><local:Localization message="user.add.address"/></button>
                </div>
            </div>
        </div>


        <div class="inside_wrap">
            <h2><local:Localization message="user.make.deposit"/></h2>
                <section>
                    <label for="amount">
                        <span class="input-label"><local:Localization message="user.amount"/></span>
                        <div class="input-wrapper amount-wrapper">
                            <input id="amount" name="amount" type="tel" min="1" placeholder="Amount" value="10" />
                        </div>
                    </label>

                    <div class="bt-drop-in-wrapper">
                        <div id="bt-dropin"></div>
                    </div>
                </section>

                <input type="hidden" id="nonce" name="payment_method_nonce" />
                <input type="hidden" name="command" value="pay">
                <button id="submit_deposit" class="button" type="submit"><span><local:Localization message="user.make.deposit"/></span></button>

        </div>

        <div class="inside_wrap">
            <h2><local:Localization message="user.change.pass"/></h2>
            <form action="${pageContext.request.contextPath}/controller" method="post"><div>
                    <lable id="old_pass"><local:Localization message="user.old.pass"/></lable>
                    <input type="password" id="old_password" class="form-control" name="old_pass">
                </div>
                <div>
                    <lable id="new_pass" style="color:#aaaaaa;"><local:Localization message="user.new.pass"/></lable>
                    <input type="password" id="new_password" class="form-control" name="new_pass">
                </div>
                <input type="hidden" name="command" value="CHANGE_PASS">
                <button class="btn" id="submit_change_pass"><local:Localization message="user.change"/></button>
            </form>
        </div>
    </div>

    <div class="container_right">
        <h2><local:Localization message="user.orders.history"/></h2>
        <c:forEach items="${applicationScope.cache.getOrders(sessionScope.userId)}" var="order">
            <div id="ord_history">
                <div id="wrapp">${order.time}</div>
                <div id="wrapp2">
                    <button class="order_info" data-id = "${order.orderId}" type="button">
                        <a href="/checkout/${order.orderId}">
                            <svg xmlns="http://www.w3.org/2000/svg" width="22" height="26" viewBox="0 0 22 22">
                                <g fill="#FFF">
                                    <path d="M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"></path>
                                </g>
                            </svg>
                        </a>
                    </button>
                </div>
            </div>
        </c:forEach>

    </div>
</div>

<div class="overlay2">
    <div class="js-button-address">
            <div>
                    <div id="city">
                        <label for="city"><local:Localization message="user.city"/> </label>
                        <input type="city" id="user_city" class="form-control" name="city" placeholder=<local:Localization message="user.city"/>>
                    </div>
                    <div id="street">
                        <label for="street"><local:Localization message="user.street"/></label>
                        <input type="street" id="user_street" class="form-control"  name="street" autocomplete="on" placeholder=<local:Localization message="user.street"/>>
                    </div>
                    <div id="house">
                        <label for="house"><local:Localization message="user.house"/></label>
                        <input type="house" id="user_house" class="form-control" name="house" placeholder=<local:Localization message="user.house"/>>
                    </div>
                    <div id="flat">
                        <label for="flat"><local:Localization message="user.flat"/></label>
                        <input type="flat" id="user_flat" class="form-control"  name="flat" autocomplete="on" placeholder=<local:Localization message="user.flat"/>>
                    </div>

                    <button class="btn btn-info" id="submit_address"><local:Localization message="user.submit"/></button>
            </div>
    </div>
</div>

<script src="https://js.braintreegateway.com/web/dropin/1.22.1/js/dropin.min.js"></script>
<script src="../../static/js/demo.js"></script>
<script src="../../static/js/sweetalert2.all.js"></script>


<jsp:include page="part/footer.jsp"/>

</body>
</html>
