<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="local" uri="/tld/localization.tld" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Test</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">


</head>
<body>
<jsp:include page="part/header_v2.jsp"/>

<div class="container">
    <div class="col-md-4" style="margin-top: 200px; margin-left: 380px">
        <form action="" role="form" name="registration">
            <div id="id_name" class="">
                <label for="name"><local:Localization message="signup.lable.name"/></label>
                <input type="name" id="name" class="form-control has-error" placeholder=<local:Localization message="signup.lable.name"/>>
            </div>
            <div id="id_surname" style="margin-top: 8px">
                <label for="surname"><local:Localization message="signup.lable.surname"/></label>
                <input type="surname" id="surname" class="form-control" placeholder=<local:Localization message="signup.lable.surname"/>>
            </div>
            <div id="id_email" style="margin-top: 8px">
                <label for="email"><local:Localization message="signup.lable.email"/></label>
                <input type="email" id="email" class="form-control" placeholder=<local:Localization message="signup.lable.email"/>>
            </div>
            <div id="id_password" style="margin-top: 8px">
                <label for="signUp_password"><local:Localization message="signup.lable.password"/></label>
                <input type="password" id="signUp_password" class="form-control" autocomplete="on" placeholder=<local:Localization message="signup.lable.password"/>>
            </div>
            <div id="id_phone" style="margin-top: 8px">
                <label for="phone"><local:Localization message="signup.lable.phone"/></label>
                <input type="phone" id="phone" class="form-control" placeholder=<local:Localization message="signup.lable.phone"/>>
            </div>
            <button type="button" class="btn btn-success" id="registration_button" style="margin-top: 8px">Submit</button>
        </form>

        <form action="/controller" method="post">
            <div>
                <lable for="text">TEST</lable>
                <label>
                    <input type="text" class="form-control">
                </label>
            </div>
            <button type="submit" class="btn btn-success" id="test">Отправить</button>
        </form>
    </div>
</div>

<jsp:include page="part/footer.jsp"/>

</body>
</html>