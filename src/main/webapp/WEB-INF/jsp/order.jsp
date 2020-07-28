<%@ taglib prefix="local" uri="/tld/localization.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Order</title>
    <link rel="shortcut icon" href="../../static/img/favico.svg" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/user.css">
    <script src="../../static/js/moment.js"></script>

</head>
<body>

<jsp:include page="part/header_v2.jsp"/>

<div class="wrapper">

    <div class="user_info container_left" style="background-color:#fff; box-shadow: 0 0 5px rgba(0,0,0,0.5); margin-bottom: 100px; padding-left: 20px;">

        <h2><local:Localization message="order.checkout"/></h2>

        <div class="personal_address">
            <h2><local:Localization message="order.address"/></h2>
            <div class="addresses" style="padding: 20px">
                <c:forEach items="${applicationScope.cache.getAddresses(sessionScope.userId)}" var="address">
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
            <h3><local:Localization message="order.payment"/></h3>

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

            <h4><local:Localization message="order.type"/></h4>
                <button id="pay2" class="btn btn-success make_order" type="submit"><span><local:Localization message="order.balance"/></span></button>
                <button id="pay3" class="btn btn-success make_order" type="submit"><span><local:Localization message="order.cash"/></span></button>
                <button id="pay4" class="btn btn-success make_order" type="submit"><span><local:Localization message="order.credit"/></span></button>

        </div>
        <div>
                <section>
                    <div class="bt-drop-in-wrapper">
                        <div id="bt-dropin"></div>
                    </div>
                </section>
                <button id="pay" class="button make_order" type="submit"><span><local:Localization message="order.card"/></span></button>
        </div>

    </div>
    <div class="container_right">
        <h2 style="padding-left: 20px"><local:Localization message="order.order"/></h2>

        <c:forEach items="${sessionScope.cart.userCart}" var="dish">
            <div style="color:white;background-color: #767676; max-width: 500px; max-height: 300px; margin-top: 20px; border-radius: 15px; margin-left: 50px; display: inline-block">`
                <div>
                    <img src="${dish.picturePath}" alt="" style="max-width: 100px; margin-left: 10px; display: inline-block">
                </div>
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

<script src="https://js.braintreegateway.com/web/dropin/1.22.1/js/dropin.min.js"></script>
<script src="../../static/js/sweetalert2.all.js"></script>

<script src="../../static/js/dateTimePicker.js"></script>
</html>
