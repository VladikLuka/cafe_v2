var form = document.querySelector('#payment-form');

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
                    form.addEventListener('submit', function (event) {
                    event.preventDefault();

                    instance.requestPaymentMethod(function (err, payload) {
                        if (err) {
                            console.log('Error', err);
                            return;
                        }
                        document.querySelector('#nonce').value = payload.nonce;
                        form.submit();
                    });
                });
            });
        }
    }

})