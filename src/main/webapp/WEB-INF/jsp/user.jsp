<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>User</title>
</head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<link rel="stylesheet" href="../../static/css/user.css">


<script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<script src="https://js.braintreegateway.com/js/braintree-2.32.1.min.js"></script>

<body>

<jsp:include page="part/header_v2.jsp"/>


<div class="wrapper">
    <div class="user_info container_left">
        <div class="inside_wrap">
            <div class="personal_info">
                <h2 style="display: inline-block">Account | </h2>
                <button type="submit"  class="btn btn-link js-button-logout" style="display: inline-block">Log out</button>
                <div class="inside_wrap" style="margin-top: 20px">
                    <h6 style="color: #aaaaaa">email</h6>
                    <div style="text-decoration-line: underline; width: 100%">
                        <h5>${applicationScope.cache.getUser(sessionScope.user_id).mail}</h5>
                    </div>
                    <h6>loyalty points</h6>
                    <div style="text-decoration-line: underline; width: 100%">
                        <h5>${applicationScope.cache.getUser(sessionScope.user_id).loyalty_point}</h5>
                    </div>
                </div>
                <div class="personal_address">
                    <h2>Address</h2>
                    <div class="addresses">
                        <c:forEach items="${applicationScope.cache.getAddress(sessionScope.user_id)}" var="address">
                            <div class="address_wrapper" data-id=${address.id} >
                                <div class="user_address">
                                    <div class="user_city_street">
                                        <div class="user_city">
                                            <h5>${address.city}, </h5>
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
                        </c:forEach>
                    </div>
                    <button class="btn btn-success" id="add_address">add address</button>
                </div>
            </div>
        </div>


        <div class="inside_wrap" style="margin-top: 20px">
            <h2>Make deposit</h2>
            <form id="payment-form" method="post" action="${pageContext.request.contextPath}/controller">
                <section>
                    <label for="amount">
                        <span class="input-label">Amount</span>
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
                <button class="button" type="submit"><span>Test Transaction</span></button>
            </form>

        </div>

        <div class="inside_wrap" style="margin-top: 20px">
            <h2>Change password</h2>
            <form action="${pageContext.request.contextPath}/controller" method="post"><div>
                    <lable style="color:#aaaaaa;">Old password</lable>
                    <input type="password" id="old_password" class="form-control" name="old_pass">
                </div>
                <div>
                    <lable style="color:#aaaaaa;">New password</lable>
                    <input type="password" id="new_password" class="form-control" name="new_pass">
                </div>
                <input type="hidden" name="command" value="CHANGE_PASS">
                <button class="btn" style="background-color:#fbec5d; margin-top: 10px" id="submit_change_pass">Change</button>
            </form>
        </div>
    </div>

    <div class="container_right">
        <h2>Orders history</h2>
        <c:forEach items="${applicationScope.cache.getOrders(sessionScope.user_id)}" var="order">
            <div style="background-color: #767676; min-height: 40px;max-width: 500px; max-height: 300px; margin-top: 20px; border-radius: 15px; margin-left: 50px">
                <div style="display:inline-block; color: white; margin-left: 30px">${order.time}</div>
                <div style="float: right; display: inline-block;">
                    <button class="order_info" data-id = "${order.order_id}" type="button" style="outline: none; border: 0;  background: transparent;">
                        <a href="/checkout/${order.order_id}">
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
                    <div id="city" style="margin-top: 8px">
                        <label for="city">name</label>
                        <input type="city" id="user_city" class="form-control" name="city" placeholder="city">
                    </div>
                    <div id="street" style="margin-top: 8px">
                        <label for="street">street</label>
                        <input type="street" id="user_street" class="form-control"  name="street" autocomplete="on" placeholder="street">
                    </div>
                    <div id="house" style="margin-top: 8px">
                        <label for="house">house	</label>
                        <input type="house" id="user_house" class="form-control" name="house" placeholder="house">
                    </div>
                    <div id="flat" style="margin-top: 8px">
                        <label for="flat">flat</label>
                        <input type="flat" id="user_flat" class="form-control"  name="flat" autocomplete="on" placeholder="flat">
                    </div>

                    <button class="btn btn-info" id="submit_address">Submit</button>
            </div>
    </div>
</div>

<script src="../../static/js/pay.js"></script>
<script src="https://js.braintreegateway.com/web/dropin/1.22.1/js/dropin.min.js"></script>

<script src="../../static/js/demo.js"></script>

<jsp:include page="part/footer.jsp"/>

</body>
</html>
