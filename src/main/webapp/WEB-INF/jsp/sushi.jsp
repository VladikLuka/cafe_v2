<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Sushi</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="../../static/css/catalog.css">

</head>
<body>
<jsp:include page="part/header_v2.jsp"/>


<div class="container" id="catalog">

    <c:forEach items="${applicationScope.cache.dishes}" var="dish">
        <c:if test="${dish.category_id == 6 and dish.available == true}">
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
                                <div id="product_price">${dish.price}</div>
                                <div id="product_weight">${dish.weight}</div>
                            </div>
                            <div id="product_action">
                                    <%--                            <input type="hidden" name="dish" value="${dish.id}">--%>
                                <button class="btn btn-success" type="submit" id="${dish.id}" onClick="getdetails(this)">Add to cart</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>
</div>

<jsp:include page="part/footer.jsp"/>

</body>
</html>
