<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="error.jsp"%>

<html>
<head>
    <title>Order</title>
</head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<link rel="stylesheet" href="../../static/css/user.css">
<script src="https://code.jquery.com/jquery-3.5.0.js" integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc=" crossorigin="anonymous"></script>
<script src="../../static/js/moment.js"></script>


<body>

<jsp:include page="part/header_v2.jsp"/>

<div class="wrapper">

    <div class="user_info container_left" style="background-color:#fff; box-shadow: 0 0 5px rgba(0,0,0,0.5); margin-bottom: 100px; padding-left: 20px;">

        <h2>Checkout</h2>

        <div class="personal_address">
            <h2>Address</h2>
            <div class="addresses" style="padding: 20px">
                <c:forEach items="${applicationScope.cache.getAddresses(sessionScope.user_id)}" var="address">
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
            <h3>Payment</h3>

<%--            <label>--%>
<%--                <input type="datetime-local" placeholder="yyyy-MM-dd hh-mm" lang="en_Us" value="2020-06-19T12:05">--%>
<%--            </label>--%>

            <div class="form-group">
                <div class='input-group date' id='datetimepicker1'>
                    <input type='text' id="delivery_time" class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
        </div>



        <script>
            var calendar = document.getElementById("delivery_time");
            console.log(moment().format("YYYY-MM-DD hh:mm:ss"));
            var mom = moment().add(1, "hours").add(5, "minutes").format("DD-MM-YYYY hh:mm:ss");
            calendar.setAttribute("value", moment().add(1, "hours").add(5, "minutes").format("YYYY-MM-DD HH:mm:ss"));
            console.log(document.getElementById("delivery_time").value)
        </script>

        <div>

            <h4>Pay from balance</h4>
                <button id="pay2" class="btn btn-success make_order" type="submit"><span>Balance</span></button>
                <button id="pay3" class="btn btn-success make_order" type="submit"><span>Cash</span></button>

        </div>
        <div>
                <section>
                    <div class="bt-drop-in-wrapper">
                        <div id="bt-dropin"></div>
                    </div>
                </section>
                <button id="pay" class="button make_order" type="submit"><span>Test Transaction</span></button>
        </div>

    </div>
    <div class="container_right">
        <h2 style="padding-left: 20px">Order</h2>

        <c:forEach items="${sessionScope.cart.cart}" var="dish">
            <div style="color:white;background-color: #767676; max-width: 500px; max-height: 300px; margin-top: 20px; border-radius: 15px; margin-left: 50px; display: inline-block">
                <div><img src="${dish.picture_path}" alt="" style="max-width: 100px; margin-left: 10px; display: inline-block"></div>
                <div>
                    <div style="margin-left: 10px; display: inline-block">${dish.description}</div>
                    <div style="margin-left: 10px; display: inline-block">${dish.price}</div>
                </div>
            </div>
        </c:forEach>

    </div>
</div>



<script type="text/javascript">
    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: "YYYY-MM-DD HH:mm:ss",
        });
    });
</script>

<jsp:include page="part/footer.jsp"/>

</body>

<script src="https://js.braintreegateway.com/js/braintree-2.32.1.min.js"></script>

<script src="../../static/js/pay.js"></script>
<script src="https://js.braintreegateway.com/web/dropin/1.22.1/js/dropin.min.js"></script>
<script src="../../static/js/sweetalert2.all.js"></script>

<script src="../../static/js/dateTimePicker.js"></script>
</html>
