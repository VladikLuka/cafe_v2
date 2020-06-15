$(document).ready(function(){
    $("#login_button").click(function(){
        var data = {};
        data = {"login":$("#login").val(), "password":$("#password").val()};
        //
        $.ajax
        ({
            type: "POST",//Метод передачи
            data: data,//Передаваемые данные в JSON - формате
            url: '/Registration',//Название сервлета
            success:function(serverData)//Если запрос удачен
            {
                console.log('okasd '+ serverData)
                // $("#auth-info").css({"background-color":serverData.backgroundColor, "height": "50px", "color":"white"});
                // $("#auth-info").html(serverData.serverInfo);
                $("#auth-info").css({"background-color":"#CC6666", "height": "50px", "color":"white"});
                $("#auth-info").html("Запрос werqqttytd удался!");
            },
            error: function(e)//Если запрос не удачен
            {
                console.info('not ok');
                $("#auth-info").css({"background-color":"#CC6666", "height": "50px", "color":"white"});
                $("#auth-info").html("Запрос не удался!");
            }
        });
    });
});