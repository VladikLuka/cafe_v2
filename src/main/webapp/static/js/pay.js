var submit_deposit = document.getElementById("submit_deposit");

$.ajax({

    data:{"command" : "get_token"},
    type:"post",
    url:"/controller",
    success:function (token) {
    },
    statusCode:{
        200:function (response) {
            braintree.dropin.create({
                authorization: response,
                container: '#bt-dropin',

            }, function (createErr, instance) {



            });
        }
    }

})