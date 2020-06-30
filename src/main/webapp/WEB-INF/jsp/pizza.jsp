<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/5/2020
  Time: 1:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pizza</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="../../static/css/catalog.css">


</head>
<body>

<jsp:include page="part/header_v2.jsp"/>

<div class="container" id="catalog">

    <c:forEach items="${applicationScope.cache.dishes}" var="dish">
        <c:if test="${dish.category_id == 4}">
            <c:choose>
                <c:when test="${dish.available eq true}">
                    <div id="product">
                        <div id="product_wrapper">
                            <div>
                                <img src="${dish.picture_path}"  class="css-adaptive" alt="">
                            </div>

                            <div id="product_title">${dish.name}</div>
                            <div id="product_description">${dish.description}</div>
                            <div id="product_modification">
                                <div id="product_info">
                                    <div id="test-line">
                                        <div id="product_price">${dish.price} BYN</div>
                                        <div id="product_weight">${dish.weight} gr</div>
                                    </div>
                                    <div id="product_action">
                                        <button class="btn btn-success" type="button" id="${dish.id}" onClick="getdetails(this)">Add to cart</button>
                                        <c:if test="${applicationScope.cache.getUser(sessionScope.user_id).role eq 'ADMIN'}">
                                            <c:if test="${dish.available eq true}">
                                                <button class="btn btn-success hide2" type="submit" data-id="${dish.id}" id="${dish.id}">hide</button>
                                            </c:if>
                                            <c:if test="${dish.available eq false}">
                                                <button class="btn btn-success show2" type="submit" data-id="${dish.id}" id="${dish.id}">show</button>
                                            </c:if>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${applicationScope.cache.getUser(sessionScope.user_id).role eq 'ADMIN'}">
                    <div id="product" style="background-color:#aaaaaa;">
                        <div id="product_wrapper">
                            <div>
                                <img src="${dish.picture_path}"  class="css-adaptive" alt="">
                            </div>

                            <div id="product_title">${dish.name}</div>
                            <div id="product_description">${dish.description}</div>
                            <div id="product_modification">
                                <div id="product_info">
                                    <div id="test-line">
                                        <div id="product_price">${dish.price}</div>
                                        <div id="product_weight">${dish.weight}</div>
                                    </div>
                                    <div id="product_action">
                                        <button class="btn btn-success" type="button" id="${dish.id}" onClick="getdetails(this)">Add to cart</button>

                                        <c:if test="${applicationScope.cache.getUser(sessionScope.user_id).role eq 'ADMIN'}">
                                            <c:if test="${dish.available eq true}">
                                                    <button class="btn btn-success hide2" type="submit" data-id="${dish.id}" id="${dish.id}">hide</button>
                                            </c:if>
                                            <c:if test="${dish.available eq false}">
                                                        <button class="btn btn-success show2" type="submit" data-id="${dish.id}" id="${dish.id}">show</button>
                                            </c:if>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </c:if>
    </c:forEach>
</div>



<jsp:include page="part/footer.jsp"/>

</body>
</html>
