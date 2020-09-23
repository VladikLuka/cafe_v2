<%@ taglib prefix="local" uri="/tld/localization.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Transaction</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="shortcut icon" href="../../static/img/favico.svg" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="../../static/css/checkout.css"/>

</head>
<body>

<header class="main">
    <div class="container wide">
        <div class="content slim">
            <div class="set">
                <div class="fill">
                    <a class="pseudoshop" href="/">EPAM<strong>CAFE</strong></a>
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
                <c:when test="${requestScope.order.status ne 'CANCELED' and requestScope.order.status ne 'VIOLATED'}">
                    <div class="icon">
                        <img src="../../static/img/success.svg"/>
                    </div>
                    <div>
                        <h1><local:Localization message="sweet.success"/></h1>
                        <section><local:Localization message="checkout.description"/>
                            <c:if test="${empty requestScope.order.orderReview}">
                                <c:if test="${requestScope.order.status eq 'CLOSED'}">
                                    <br><local:Localization message="checkout.leave.feedback"/></section>
                                </c:if>
                                <c:if test="${requestScope.order.status eq 'CLOSED'}">
<%--                                    <form action="${pageContext.request.contextPath}/controller" method="post">--%>
                                        <textarea name="feedback" id="feedback_text" cols="70" rows="3" required></textarea>
                                            <div id="reviewStars-input">
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
                                            <input type="hidden" name="order_id" value="${requestScope.order.orderId}">
                                            <button type="submit" class="button primary back" id="feedback_submit">
                                                <span id="leave_feedback"><local:Localization message="checkout.button.feedback"/></span>
                                            </button>
                                        </section>
<%--                                    </form>--%>
                                </c:if>

                            <c:if test="${requestScope.order.status ne 'CLOSED'}">
                                <c:if test="${requestScope.order.orderId eq requestScope.order.userId}">
                                <section>
                                    <form action="${pageContext.request.contextPath}/controller" method="post">
                                        <input type="hidden" name="command" value="cancel_order">
                                        <input type="hidden" name="order_id" value="${requestScope.order.orderId}">
                                        <button type="submit" class="button primary back" id="cancel_order">
                                            <span id="cnac_older">cancel order</span>
                                        </button>
                                    </form>
                                </section>
                                </c:if>
                            </c:if>
                        </c:if>
                        <c:if test="${not empty requestScope.order.orderReview}">
                            <section><local:Localization message="checkout.your.order"/></section>
                            <section>${requestScope.order.orderReview}</section>
                        </c:if>

                    </div>
                </c:when>
                <c:otherwise>
                    <div class="icon">
                        <img src="../../static/img/fail.svg"/>
                    </div>
                    <section>
                        <div>
                            <br><local:Localization message=""/>
                        </div>
                    </section>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<aside class="drawer dark">
    <article class="content compact">
        <section>
            <h5><local:Localization message="checkout.transaction"/></h5>
            <table>
                <tbody>
                <tr>
                    <td>id</td>
                    <td id="order_id" data-id ="${requestScope.order.orderId}">${requestScope.order.orderId}</td>
                </tr>
                <tr>
                    <td><local:Localization message="checkout.status"/></td>
                    <td>${requestScope.order.method}</td>
                </tr>
                <tr>
                    <td><local:Localization message="checkout.amount"/></td>
                    <td>${requestScope.order.amount} BYN</td>
                </tr>
                <tr>
                    <td><local:Localization message="checkout.status"/></td>
                    <td>${requestScope.order.status}</td>
                </tr>
                <tr>
                    <td><local:Localization message="checkout.created"/></td>
                    <td>${requestScope.order.time}</td>
                </tr>
                <c:if test="${empty requestScope.order.creditTime}">
                <tr>
                    <td><local:Localization message="checkout.delivery"/></td>
                    <td>${requestScope.order.deliveryTime}</td>
                </tr>
                </c:if>
                <c:if test="${not empty requestScope.order.creditTime}">
                <tr>
                    <td><local:Localization message="checkout.credit"/></td>
                    <td>${requestScope.order.creditTime}</td>
                </tr>
                </c:if>
                </tbody>
            </table>
        </section>

        <section>
            <c:if test="${not empty requestScope.order.address}">
                <h5><local:Localization message="order.address"/></h5>

                <table>
                    <tbody>
                    <tr>
                        <td><local:Localization message="user.city"/></td>
                        <td>${requestScope.order.address.city}</td>
                    </tr>
                    <tr>
                        <td><local:Localization message="user.street"/></td>
                        <td>${requestScope.order.address.street}</td>
                    </tr>
                    <tr>
                        <td><local:Localization message="user.house"/></td>
                        <td>${requestScope.order.address.house}</td>
                    </tr>
                    <tr>
                        <td><local:Localization message="user.flat"/></td>
                        <td>${requestScope.order.address.flat}</td>
                    </tr>
                    </tbody>
                </table>
            </c:if>
        </section>


        <c:if test="${not empty requestScope.order.dishes}">
            <section>
                <h5><local:Localization message="checkout.dishes"/></h5>
                <table>
                    <tbody>
                    <c:forEach items="${requestScope.order.dishes}" var="dish">
                        <tr>
                            <td><local:Localization message="checkout.dish.name"/></td>
                            <td><local:Localization message="dish.name.${dish.key.name}"/></td>
                        </tr>
                        <tr>
                            <td><local:Localization message="checkout.dish.quantity"/></td>
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
