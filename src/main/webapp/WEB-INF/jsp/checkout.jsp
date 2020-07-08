<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Transaction</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="../../static/css/checkout.css"/>

</head>
<body>
<header class="main">
    <div class="container wide">
        <div class="content slim">
            <div class="set">
                <div class="fill">
                    <a class="pseudoshop" href="/">PSEUDO<strong>SHOP</strong></a>
                </div>

                <div class="fit">
                    <a class="braintree" href="https://developers.braintreepayments.com/guides/drop-in" target="_blank">Braintree</a>
                </div>
            </div>
        </div>
    </div>
</header>

<div class="wrapper">
    <div class="response container">
        <div class="content">

            <c:choose>
                <c:when test="${requestScope.order.status ne 'CANCELED'}">
                    <div class="icon">
                        <img src="../../static/img/success.svg"/>
                    </div>
                    <div>
                        <h1>Sweet Success!</h1>
                        <section>Your test transaction has been successfully processed.
                            <c:if test="${empty requestScope.order.order_review}">
                                <c:if test="${requestScope.order.status eq 'CLOSED'}">
                                    <br> Here you can leave feedback</section>
                                </c:if>
                                <c:if test="${requestScope.order.status eq 'CLOSED'}">
<%--                                    <form action="${pageContext.request.contextPath}/controller" method="post">--%>
                                        <textarea name="feedback" id="feedback_text" cols="70" rows="3" required></textarea>
                                            <div id="reviewStars-input" style="margin-right: 180px;">
                                                <input id="star-4" type="radio" name="stars" value="5"/>
                                                <label title="gorgeous" for="star-4"></label>

                                                <input id="star-3" type="radio" name="stars" value="4"/>
                                                <label title="good" for="star-3"></label>

                                                <input id="star-2" type="radio" name="stars" value="3"/>
                                                <label title="regular" for="star-2"></label>

                                                <input id="star-1" type="radio" name="stars" value="2"/>
                                                <label title="poor" for="star-1"></label>

                                                <input id="star-0" type="radio" name="stars" value="1"/>
                                                <label title="bad" for="star-0"></label>
                                            </div>
                                        <section>
                                            <input type="hidden" name="command" value="feedback">
                                            <input type="hidden" name="order_id" value="${requestScope.order.order_id}">
                                            <button type="submit" class="button primary back" id="feedback_submit">
                                                <span style="width: 350px">leave feedback</span>
                                            </button>
                                        </section>
<%--                                    </form>--%>
                                </c:if>

                            <c:if test="${requestScope.order.status ne 'CLOSED'}">
                                <section>
                                    <form action="${pageContext.request.contextPath}/controller" method="post">
                                        <input type="hidden" name="command" value="cancel_order">
                                        <input type="hidden" name="order_id" value="${requestScope.order.order_id}">
                                        <button type="submit" class="button primary back" id="cancel_order" style="background-color:#dd571c">
                                            <span style="width: 350px">cancel order</span>
                                        </button>
                                    </form>
                                </section>
                            </c:if>
                        </c:if>
                        <c:if test="${not empty requestScope.order.order_review}">
                            <section>Your feedback</section>
                            <section>${requestScope.order.order_review}</section>
                        </c:if>

                    </div>
                </c:when>
                <c:otherwise>
                    <div class="icon">
                        <img src="../../static/img/fail.svg"/>
                    </div>
                    <section>
                        <div>
                            <br>Order cancelled
                        </div>
                    </section>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<aside class="drawer dark">
    <header>
        <div class="content compact">
            <a href="https://developers.braintreepayments.com" class="braintree" target="_blank">Braintree</a>
            <h3>API Response</h3>
        </div>
    </header>

    <article class="content compact">
        <section>
            <h5>Transaction</h5>
            <table cellpadding="0" cellspacing="0">
                <tbody>
                <tr>
                    <td>id</td>
                    <td id="order_id" data-id ="${requestScope.order.order_id}">${requestScope.order.order_id}</td>
                </tr>
                <tr>
                    <td>type</td>
                    <td>${requestScope.order.method}</td>
                </tr>
                <tr>
                    <td>amount</td>
                    <td>${requestScope.order.amount}</td>
                </tr>
                <tr>
                    <td>status</td>
                    <td>${requestScope.order.status}</td>
                </tr>
                <tr>
                    <td>created at</td>
                    <td>${requestScope.order.time}</td>
                </tr>
                <tr>
                    <td>delivery time</td>
                    <td>${requestScope.order.delivery_time}</td>
                </tr>
                </tbody>
            </table>
        </section>

        <section>
            <c:if test="${not empty requestScope.order.address}">
                <h5>Address</h5>

                <table cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <td>City</td>
                        <td>${requestScope.order.address.city}</td>
                    </tr>
                    <tr>
                        <td>Street</td>
                        <td>${requestScope.order.address.street}</td>
                    </tr>
                    <tr>
                        <td>House</td>
                        <td>${requestScope.order.address.house}</td>
                    </tr>
                    <tr>
                        <td>Flat</td>
                        <td>${requestScope.order.address.flat}</td>
                    </tr>
                    </tbody>
                </table>
            </c:if>
        </section>


        <c:if test="${not empty requestScope.order.dishes}">
            <section>
                <h5>Dishes</h5>
                <table cellpadding="0" cellspacing="0">
                    <tbody>
                    <c:forEach items="${requestScope.order.dishes}" var="dish">
                        <tr>
                            <td>dish name</td>
                            <td>${dish.key.name}</td>
                        </tr>
                        <tr>
                            <td>quantity</td>
                            <td>${dish.value}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </section>
        </c:if>
    </article>
</aside>

<jsp:include page="part/footer.jsp"/>
</body>
<script src="../../static/js/sweetalert2.all.js"></script>

</html>
