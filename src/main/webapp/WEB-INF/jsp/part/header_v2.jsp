<%@ taglib prefix="local" uri="/tld/localization.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Test</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="../../static/css/style_header_v2.css">
    <link rel="stylesheet" href="../../static/css/main.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">


</head>

<body style="background-color: #f7fcff">

<nav class="navbar navbar-default "   role="navigation" >
    <div class="container-fluid" id="header_container">
        <div class="col-md-1">
            <img src="../../../static/img/java_icon.svg" alt="" id="logo_icon">
        </div>
        <div class="container" id="first-header">
            <div>
                <img src="../../../static/img/epam_logo_test_v3.svg" alt="" id="logo">
            </div>
        </div>

        <div id="header_line">

        </div>
    </div>


    <div class="container-fluid">
        <div id="second-header">

            <div  id="header_mini_button" class="collapse navbar-collapse">

                <div id="test_menu">

                    <ul class="row" id="menu">
                        <li class="col-xl-1"><a href="${pageContext.request.contextPath}/drink/1"><local:Localization message="header.menu.drinks"/></a></li>
                        <li class="clearfix hidden-xs hidden-sm"></li>
                        <li class="col-xl-1"><a href="${pageContext.request.contextPath}/garnish/1"><local:Localization message="header.menu.garnish"/></a></li>
                        <li class="clearfix hidden-xs hidden-sm"></li>
                        <li class="col-xl-1"><a href="${pageContext.request.contextPath}/meat/1"><local:Localization message="header.menu.meat.dishes"/></a></li>
                        <li class="clearfix hidden-xs hidden-sm"></li>
                        <li class="col-xl-1"><a href="${pageContext.request.contextPath}/pizza/1"><local:Localization message="header.menu.pizzas"/></a></li>
                        <li class="clearfix hidden-xs hidden-sm"></li>
                        <li class="col-xl-1"><a href="${pageContext.request.contextPath}/salad/1"><local:Localization message="header.menu.salads"/></a></li>
                        <li class="clearfix hidden-xs hidden-sm"></li>
                        <li class="col-xl-1"><a href="${pageContext.request.contextPath}/sushi/1"><local:Localization message="header.menu.sushi"/></a></li>
                    </ul>

                </div>

                <div id="test_login">
                    <ul class="row" id="login">
                        <li class="col-xl-3 dropdown"><a href=""  class="dropdown-toggle" data-toggle="dropdown"><img src="../../static/img/world-icon.svg" alt=""></a>
                            <ul id="locale" class="dropdown-menu">
                                <li>
                                    <div class="row">
                                        <div class="col-md-12">
                                                <button type="submit" id="locale_ru" value="ru">RU</button>
                                                <button type="submit" id="locale_en" value="en">EN</button>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </li>


                        <c:if test="${not empty sessionScope.userId}">
                            <c:if test="${applicationScope.cache.getUser(sessionScope.userId).role eq 'ADMIN'}">
                            <li>
                                <div class="button "><span><a href="${pageContext.request.contextPath}/admin"><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="21" height="21" viewBox="0 0 16 16">
<path fill="#ffffff" d="M15.2 6l-1.1-0.2c-0.1-0.2-0.1-0.4-0.2-0.6l0.6-0.9 0.5-0.7-2.6-2.6-0.7 0.5-0.9 0.6c-0.2-0.1-0.4-0.1-0.6-0.2l-0.2-1.1-0.2-0.8h-3.6l-0.2 0.8-0.2 1.1c-0.2 0.1-0.4 0.1-0.6 0.2l-0.9-0.6-0.7-0.4-2.5 2.5 0.5 0.7 0.6 0.9c-0.2 0.2-0.2 0.4-0.3 0.6l-1.1 0.2-0.8 0.2v3.6l0.8 0.2 1.1 0.2c0.1 0.2 0.1 0.4 0.2 0.6l-0.6 0.9-0.5 0.7 2.6 2.6 0.7-0.5 0.9-0.6c0.2 0.1 0.4 0.1 0.6 0.2l0.2 1.1 0.2 0.8h3.6l0.2-0.8 0.2-1.1c0.2-0.1 0.4-0.1 0.6-0.2l0.9 0.6 0.7 0.5 2.6-2.6-0.5-0.7-0.6-0.9c0.1-0.2 0.2-0.4 0.2-0.6l1.1-0.2 0.8-0.2v-3.6l-0.8-0.2zM15 9l-1.7 0.3c-0.1 0.5-0.3 1-0.6 1.5l0.9 1.4-1.4 1.4-1.4-0.9c-0.5 0.3-1 0.5-1.5 0.6l-0.3 1.7h-2l-0.3-1.7c-0.5-0.1-1-0.3-1.5-0.6l-1.4 0.9-1.4-1.4 0.9-1.4c-0.3-0.5-0.5-1-0.6-1.5l-1.7-0.3v-2l1.7-0.3c0.1-0.5 0.3-1 0.6-1.5l-1-1.4 1.4-1.4 1.4 0.9c0.5-0.3 1-0.5 1.5-0.6l0.4-1.7h2l0.3 1.7c0.5 0.1 1 0.3 1.5 0.6l1.4-0.9 1.4 1.4-0.9 1.4c0.3 0.5 0.5 1 0.6 1.5l1.7 0.3v2z"></path>
<path fill="#ffffff" d="M8 4.5c-1.9 0-3.5 1.6-3.5 3.5s1.6 3.5 3.5 3.5 3.5-1.6 3.5-3.5c0-1.9-1.6-3.5-3.5-3.5zM8 10.5c-1.4 0-2.5-1.1-2.5-2.5s1.1-2.5 2.5-2.5 2.5 1.1 2.5 2.5c0 1.4-1.1 2.5-2.5 2.5z"></path>
</svg></a></span></div>
                            </li>
                            </c:if>
                        </c:if>
                        <c:choose>
                        <c:when test="${empty sessionScope.userId}">
                        <li>
                            <main>
                                <div class="button js-button-campaign-signup"><span><svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 21 21" icon="account" class="default-header-account-icon"><path d="M10.5 0C6.82 0 3.82 3 3.82 6.68c0 2.29 1.16 4.31 2.92 5.52A9.534 9.534 0 00.95
  21h1.91c0-4.26 3.37-7.64 7.64-7.64s7.64 3.37 7.64 7.64h1.91c0-3.95-2.37-7.35-5.79-8.8a6.68
  6.68 0 002.92-5.52C17.18 3 14.18 0 10.5 0zm0 1.91c2.65 0 4.77 2.13 4.77 4.77s-2.13 4.77-4.77
  4.77-4.77-2.12-4.77-4.77 2.12-4.77 4.77-4.77z" id="login_icon"></path></svg></span></div>
                            </main>
                        </li>
                            </c:when>
                            <c:when test="${not empty sessionScope.userId}">
                            <li>
                                <main>
                                    <a href="/user"><div class="button"><span><svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 21 21" icon="account" class="default-header-account-icon"><path d="M10.5 0C6.82 0 3.82 3 3.82 6.68c0 2.29 1.16 4.31 2.92 5.52A9.534 9.534 0 00.95
  21h1.91c0-4.26 3.37-7.64 7.64-7.64s7.64 3.37 7.64 7.64h1.91c0-3.95-2.37-7.35-5.79-8.8a6.68
  6.68 0 002.92-5.52C17.18 3 14.18 0 10.5 0zm0 1.91c2.65 0 4.77 2.13 4.77 4.77s-2.13 4.77-4.77
  4.77-4.77-2.12-4.77-4.77 2.12-4.77 4.77-4.77z" id="login_icon"></path></svg></span></div></a>
                                </main>
                            </li>
                        </c:when>
                        </c:choose>
                        <li>
                        <div class="button js-button-cart"><span><svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 21 21" icon="cart" class="default-header-cart-icon"><path d="M6.02 7L4.27 0H.11v1.75h2.84l3.5 14h11.81L20.89 7H6.02zm10.93 7H7.77L6.45 8.75h12.03L16.95
  14zM9.3 16.63c1.21 0 2.19.98 2.19 2.19S10.5 21 9.3 21s-2.19-.98-2.19-2.19.98-2.18 2.19-2.18zm3.93
  2.18a2.19 2.19 0 104.379.001 2.19 2.19 0 00-4.379-.001z" id="cart_icon"></path></svg></span></div>
                            <div id="cart_1">
                                <div class="container_cart">
                                    <button class="btn button-info" id="show_less_cart">Show less</button>

                                    <div id="cart_item">

                                    </div>

                                        <h2 id="total_price">Total 0</h2>

                                    <c:choose>
                                        <c:when test="${not empty sessionScope.userId}">
                                            <a href="${pageContext.request.contextPath}/order"><button type="button"  class="btn button-warning" id="order-button" >Order</button></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/order"><button type="button"  class="btn button-warning" id="order-button" disabled>Order</button></a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </li>
                        <c:choose>
                            <c:when test="${not empty sessionScope.userId}">
                                <li class="col-xl-3" id="header_balance">${applicationScope.cache.getUser(sessionScope.userId).money} BYN</li>
                            </c:when>
                            <c:otherwise>
                                <li class="col-xl-3" id="header_balance">0 BYN</li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>



            </div>
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#header_mini_button">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

    </div>
</nav>

<c:choose>
<c:when test="${empty sessionScope.userId}">
<div class="overlay js-overlay-campaign-signup">
    <div class="popup js-popup-campaign-signup">
        <h3>Register Epam-cafe's account</h3>
        <form action="${pageContext.request.contextPath}/controller" role="form" method="post" name="registration">
            <div id="id_name">
                <label for="name"><local:Localization message="signup.lable.name"/></label>
                <input type="name" id="name" class="form-control" name="email" placeholder=<local:Localization message="signup.lable.name"/>>
            </div>
            <div id="id_surname">
                <label for="email"><local:Localization message="signup.lable.surname"/></label>
                <input type="email" id="surname" class="form-control" name="email" placeholder=<local:Localization message="signup.lable.surname"/>>
            </div>
            <div id="id_email">
                <label for="email"><local:Localization message="signup.lable.email"/></label>
                <input type="email" id="email" class="form-control" name="email" placeholder=<local:Localization message="signup.lable.email"/>>
            </div>
            <div id="id_password">
                <label for="signUp_password"><local:Localization message="signup.lable.password"/></label>
                <input type="password" id="signUp_password" class="form-control"  name="password" autocomplete="on" placeholder=<local:Localization message="signup.lable.password"/>>
            </div>
            <div id="id_phone">
                <label for="signUp_phone"><local:Localization message="signup.lable.phone"/></label>
                <input type="phone" id="signUp_phone" class="form-control" name="phone" autocomplete="on" placeholder=<local:Localization message="signup.lable.phone"/>>
            </div>
            <button type="button" class="btn btn-success" id="registration_button">Submit</button>
        </form>

        <div class="close-popup js-close-campaign-signup"></div>
        <button type="submit"  class="btn btn-link" id="login-popup">Login</button>
    </div>
</div>

    <div class="overlay js-overlay-campaign-login">
        <div class="popup js-popup-campaign-login">
            <h3>Log in to Epam-cafe's account</h3>
            <form action="${pageContext.request.contextPath}/controller" role="form" method="post" aria-label="" >
                <div id="id_email_log">
                    <label for="email"><local:Localization message="signup.lable.email"/></label>
                    <input type="email" id="email_log" class="form-control" name="email" placeholder=<local:Localization message="signup.lable.email"/>>
                </div>
                <div id="id_password_log">
                    <label for="signUp_password"><local:Localization message="signup.lable.password"/></label>
                    <input type="password" id="signUp_password_log" class="form-control"  name="password" autocomplete="on" placeholder=<local:Localization message="signup.lable.password"/>>
                </div>
                <button type="button" class="btn btn-success" id="login_button">Submit</button>
            </form>

            <div class="close-popup js-close-campaign-login"></div>
            <button type="submit"  class="btn btn-link js-button-campaign-signup">Sign in</button>
        </div>
    </div>
</c:when>
</c:choose>



</body>
</html>