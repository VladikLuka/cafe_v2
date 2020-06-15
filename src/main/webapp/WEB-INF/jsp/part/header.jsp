<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Test</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="../../../static/css/style_header.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">


</head>

<body style="background-color: #e6e6fa">


<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid" id="header_test"  style="background-color: #77ced9">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="#"><img src="/staticResources/img/epam_logo.svg"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Link</a></li>
                <li><a href="#">Link</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                        <li class="divider"></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>

            <form action="${pageContext.request.contextPath}${pageContext.request.getAttribute("uri")}" method="post">
                <div class="form-group">
                    <input type="hidden" name="command_locale" value="ru">
                </div>
                <button type="submit" value="ru">RU</button>
            </form>

            <form action="${pageContext.request.contextPath}${pageContext.request.getAttribute("uri")}" method="post">
                <div>
                    <input type="hidden" name="command_locale" value="en">
                </div>
                <button type="submit" value="en">EN</button>
            </form>

            <c:if test="${empty sessionScope.user_email}">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>Login</b> <span class="caret"></span></a>
                    <ul id="login-dp" class="dropdown-menu">
                        <li>
                            <div class="row">
                                <div class="col-md-12">
                                    <form class="form" role="form" accept-charset="UTF-8">
                                        <div class="form-group" id="header_login_email">
                                            <label class="sr-only" for="login_email">Email address</label>
                                            <input type="email" class="form-control" id="login_email" placeholder="Email address" required>
                                        </div>
                                        <div class="form-group" id="header_login_password">
                                            <label class="sr-only" for="login_password">Password</label>
                                            <input type="password" class="form-control" id="login_password" autocomplete="on" placeholder="Password" required>
                                            <div class="help-block text-right"><a href="">Forget the password ?</a></div>
                                        </div>
                                        <div class="form-group">
                                            <input type="button" class="btn btn-primary btn-block" id="login_button" value="Sign in">
                                        </div>
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox"> keep me logged-in
                                            </label>
                                        </div>
                                    </form>
                                </div>
                                <div class="bottom text-center">
                                    New here ? <a href="#"><b>Join Us</b></a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
            </c:if>

            <c:if test="${not empty sessionScope.user_email}">
            <ul class="nav navbar-nav navbar-right">
                        <li>
                            <p class="navbar-text"><a href="#">${sessionScope.user_email}</a> </p>
                        </li>
                    </ul>
            </c:if>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

</body>
</html>