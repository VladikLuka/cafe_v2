<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="error.jsp"%>

<html>
<head>
    <title>Order</title>
</head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<link rel="stylesheet" href="../../static/css/user.css">
<script src="https://js.braintreegateway.com/js/braintree-2.32.1.min.js"></script>


<body>

<jsp:include page="part/header_v2.jsp"/>

<div class="wrapper">

    <div class="user_info container_left" style="background-color:#fff; box-shadow: 0 0 5px rgba(0,0,0,0.5);">

        <h2>Checkout</h2>

        <div class="personal_address">
            <h2>Address</h2>
            <div class="addresses" style="padding: 20px">
                <c:forEach items="${applicationScope.cache.getAddress(sessionScope.user_id)}" var="address">
                    <div class="address_wrapper" data-id=${address.id}>
                        <div class="user_address" >
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
                    </div>
                </c:forEach>
            </div>
            <h2>Payment</h2>
            <div>
                <h6 style="color: #aaaaaa">Payment type</h6>
            </div>
        </div>
        <div>

            <h4>Pay from balance</h4>
            <form id="payment-form2" action="${pageContext.request.contextPath}/controller" class="payment" method="POST">

                <input type="hidden" name="command" value="balance_order">
                <button id="pay2" class="btn btn-success make_order" type="submit"><span>Test Transaction</span></button>
            </form>

        </div>
        <div>
            <form id="payment-form" method="post" class="payment" action="${pageContext.request.contextPath}/controller">
                <section>
                    <div class="bt-drop-in-wrapper">
                        <div id="bt-dropin"></div>
                    </div>
                </section>

                <input type="hidden" id="nonce" name="payment_method_nonce" />
                <input type="hidden" name="command" value="make_order">
                <button id="pay" class="button make_order" type="submit"><span>Test Transaction</span></button>
            </form>
        </div>
    </div>
    <div class="container_right">
        <h2>Order</h2>

        <c:forEach items="${sessionScope.cart.cart}" var="dish">
            <div style="background-color: #767676; max-width: 500px; max-height: 300px; margin-top: 20px; border-radius: 15px; margin-left: 50px">
                <div>${dish.id}</div>
                <div><img src="${dish.picture_path}" alt="" style="max-width: 100px;"></div>
                <div>${dish.price}</div>
                <div>${dish.description}</div>
            </div>
        </c:forEach>

    </div>
</div>

<jsp:include page="part/footer.jsp"/>

<script src="../../static/js/pay.js"></script>
<script src="https://js.braintreegateway.com/web/dropin/1.22.1/js/dropin.min.js"></script>

<script src="../../static/js/demo.js"></script>

</body>
</html>
