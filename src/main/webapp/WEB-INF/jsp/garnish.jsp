<%@ taglib prefix="local" uri="/tld/localization.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Drinks</title>

    <link rel="shortcut icon" href="../../static/img/favico.svg" type="image/x-icon"
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="../../static/css/catalog.css">
    <script src="../../static/js/sweetalert2.all.js"></script>


</head>
<body>

<jsp:include page="part/header_v2.jsp"/>

<div class="container" id="catalog">



    <c:forEach items="${requestScope.drinks}" var="dish">
        <c:choose>
            <c:when test="${dish.available eq false}">
                <div id="product2">
            </c:when>
            <c:otherwise>
                <div id="product">
            </c:otherwise>
        </c:choose>
        <div id="product_wrapper">
            <div>
                <img src="${dish.picturePath}" class="css-adaptive" alt="" id="dish_pic">
            </div>

            <fmt:setLocale value="${sessionScope.locale}"/>
            <fmt:setBundle basename="locale" var="lang"/>

            <div id="product_title"><fmt:message key="dish.name.${dish.name}" bundle="${lang}"/></div>
            <div id="product_description"><fmt:message key="dish.description.${dish.description}" bundle="${lang}"/></div>
            <div id="product_modification">
                <div id="product_info">
                    <div id="test-line">
                        <div id="product_price">${dish.price} BYN</div>
                        <div id="product_weight">${dish.weight} <fmt:message key="dish.gr" bundle="${lang}"/></div>
                    </div>
                    <div id="product_action">
                        <button class="btn btn-success" type="button" id="${dish.id}" onClick="getdetails(this)"><fmt:message key="dish.add.to.cart" bundle="${lang}"/></button>
                        <c:if test="${applicationScope.cache.getUser(sessionScope.userId).role eq 'ADMIN'}">
                            <c:if test="${dish.available eq true}">
                                <button class="btn btn-success hide2" type="submit" data-id="${dish.id}" id="${dish.id}"><fmt:message key="dish.hide" bundle="${lang}"/></button>
                            </c:if>
                            <c:if test="${dish.available eq false}">
                                <button class="btn btn-success show2" type="submit" data-id="${dish.id}" id="${dish.id}"><fmt:message key="dish.show" bundle="${lang}"/></button>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
                </div>
        </c:forEach>

        <c:if test="${not empty sessionScope.userId}">
            <c:if test="${applicationScope.cache.getUser(sessionScope.userId).role eq 'ADMIN'}">
                <div id="addProduct">
                    <button type="submit" id="add_dish">
                        <img src="https://img.icons8.com/pastel-glyph/256/000000/plus.png"/>
                    </button>
                </div>
            </c:if>
        </c:if>

        <c:set var="pages" value="${requestScope.pages}"/>
        <div id="dishes">
            <c:forEach var="page" begin="1" end="${pages}">
                <c:choose>
                    <c:when test="${requestScope.current_page eq page}">
                        ${page}
                    </c:when>
                    <c:otherwise>
                        <a href="/garnish/${page}">${page}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>





    <jsp:include page="part/footer.jsp"/>

</body>
</html>
