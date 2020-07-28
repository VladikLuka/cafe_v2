
$(document).ready(function(){


	getLocCart();

	$("#deposit").click(function () {

		var rad = document.getElementsByName("addr");

		for (var i = 0; i < rad.length; i++){
			if (rad[i].checked){
				var childNodes = rad[i].parentElement.childNodes;

				break;
			}
		}

	})


	$("#submit-button").click(function () {

		$.ajax({
			url:"/controller",
			data: {"command":"get_token"},
			type:"POST",
			success:function (response) {
				testPay(response);
			},
			statusCode:{
				200:function (response) {
					testPay(response);
				}
			}
		})

	})

	function testPay(token){
		// var button = document.querySelector('#submit-button3');

		braintree.dropin.create({
			authorization: token,
			container: '#dropin-container'
		}, function (createErr, instance) {
				instance.requestPaymentMethod(function (err, payload) {

					$.ajax({
						type:"POST",
						url:"/controller",
						data:{"command" : "deposit",
							"payment_method_nonce" : payload.nonce}
					}).done(function (result) {
						instance.teardown(function () {

						})
					})

				});
			});
	}

	$("#clean_cart").click(function () {

		$.ajax({
			type:"POST",
			url:"/controller",
			data: {
				"command": "CLEAN_CART",
			},
		})

	})

	function isCheck(name) {
		return document.querySelector('input[name="' + name + '"]:checked');
	}

	$("#feedback_submit").click(function () {

		let doc = isCheck("stars");

		if(doc === null){
			swal("Something went wrong", "fill feedback", "error");
			return;
		}

		if($("#feedback_text").val() === ""){
			swal("Something went wrong", "fill feedback", "error");
			return;
		}

		let data = {
			"command":"feedback",
			"feedback":$("#feedback_text").val(),
			"stars":doc.value,
			"order_id":document.getElementById("order_id").dataset.id
		}

		$.ajax({

			type:"POST",
			url:"/controller",
			data:data,
			success:function () {
				document.location.href = location.href;
			},
			statusCode:{
				400:function () {
					swal("Something went wrong", "chose address", "error");
				}
			}


		})

	})


	$("#login_button").click(function(e){
		e.preventDefault();
		var data = {"login_email":$("#email_log").val(),
			"login_password":$("#signUp_password_log").val(),
			"command": "login"
		};

		//var jsonData = JSON.stringify(data);
			console.log("normas");
			$.ajax({
				type:"POST",
				url:"/controller",
				data: data,
				success:function(serverData){
					document.location.href = "/pizza";
				},
				statusCode:{
					400: function () {
						$("#id_email_log").addClass("has-error");
					}
				}
			})

	});


	$("#ban_user").click(function(){

		let data = {
			"command":"ban_user",
			"user_id": $("#user_id").val()
		}

		$.ajax({

			type:"POST",
			url:"/controller",
			data:data,
			datatype:"json",
			success:function(response){
				let element = document.getElementById("isBanned")
				element.value = JSON.parse(response)["isBan"];
			}

		})


	})


	$("#unban_user").click(function(){

		let data = {
			"command":"unban_user",
			"user_id": $("#user_id").val()
		}

		$.ajax({

			type:"POST",
			url:"/controller",
			data:data,
			datatype:"json",
			success:function(response){
				let element = document.getElementById("isBanned")
				element.value = JSON.parse(response)["isBan"];
			}

		})


	})

	$("#add_money").click(function () {

		var add_mon = document.getElementById("add_money");
		add_mon.disabled = true;

		let check = isDecimal2($("#add_user_money").val());

		if(!check){
			$("#div_add_money").addClass("has-error");
		}

		let data = {
			"command":"add_money",
			"amount":$("#add_user_money").val(),
			"user_id":$("#user_id").val()
		}

		$.ajax({

			type:"POST",
			url:"/controller",
			data:data,
			datatype:"json",
			success:function (response) {
				$("#div_add_money").removeClass("has-error")
				let element = document.getElementById("user_money")
				element.value = JSON.parse(response)["money"]
				var add_mon = document.getElementById("add_money");
				add_mon.disabled = false;
			},
			statusCode:{
				400: function(){
					$("#div_add_money").addClass("has-error");
					let add_mon2 = document.getElementById("add_money");
					add_mon2.disabled = false;
				}
			}

		})

	})

	$("#add_points").click(function () {

		let data = {
			"command":"add_points",
			"points":$("#give_user_points").val(),
			"user_id":$("#user_id").val()
		}

		$.ajax({

			type:"POST",
			url:"/controller",
			data:data,
			datatype:"json",
			success:function (response) {
				$("#div_add_point").removeClass("has-error");
				let element = document.getElementById("user_point")
				element.value = JSON.parse(response)["loyaltyPoint"]
			},
			statusCode:{
				400: $("#div_add_point").addClass("has-error")
			}

		})

	})

	$("#subtract_money").click(function () {

		let check = isDecimal2($("#substr_user_money").val());

		if(!check){
			$("#div_add_money").addClass("has-error");
		}


		let data = {
			"command":"SUBTRACT_MONEY",
			"amount":$("#substr_user_money").val(),
			"user_id":$("#user_id").val()
		}

		$.ajax({

			type:"POST",
			url:"/controller",
			data:data,
			datatype:"json",
			success:function (response) {
				$("#div_grab_money").removeClass("has-error")
				let element = document.getElementById("user_money")
				element.value = JSON.parse(response)["money"]
			},
			statusCode:{
				400: $("#div_grab_money").addClass("has-error")
			}

		})

	})

	$("#grab_points").click(function () {

		let data = {
			"command":"SUBTRACT_POINTS",
			"points":$("#grab_user_points").val(),
			"user_id":$("#user_id").val()
		}

		$.ajax({

			type:"POST",
			url:"/controller",
			data:data,
			datatype:"json",
			success:function (response) {
				$("#div_grab_points").removeClass("has-error")
				let element = document.getElementById("user_point")
				element.value = JSON.parse(response)["loyaltyPoint"]
			},
			statusCode:{
				400: $("#div_grab_points").addClass("has-error")
			}

		})

	})

	$("#locale_ru").click(function(){

		$.ajax({

			type:"POST",
			url:document.location,
			data:{"command_locale":"ru"},
			success:function(){
				document.location.href = document.location;
			}

		})

	})

	$("#locale_en").click(function(){

		$.ajax({

			type:"POST",
			url:document.location,
			data:{"command_locale":"en"},
			success:function(){
				document.location.href = document.location;
			}

		})

	})


	$(".js-button-logout").click(function () {

		$.ajax({

			type:"POST",
			url:"/controller",
			data: {"command" : "logout",
			},
			success:function () {
				document.location.href = "/pizza";
			}

		})

	})


	$("#registration_button").click(function(){

		var data = {
					"email":$("#email").val(),
					"password":$("#signUp_password").val(),
					"phone":$("#signUp_phone").val(),
					"name":$("#name").val(),
					"surname":$("#surname").val(),
					"command": "signup"
		};



		if(checkCorrect()){

			$.ajax({
				type:"POST",
				url:"/controller",
				data: data,
				success:function(){
					document.location.href = "/pizza";
				},
				statusCode:{
					400: function () {
						$("#id_email").addClass("has-error");
						$("#id_signup").addClass("has-error");
					}
				}

			})

		}
	});




$("#submit_address").click(function () {

	var data = {
		"city":$("#user_city").val(),
		"street":$("#user_street").val(),
		"house":$("#user_house").val(),
		"flat":$("#user_flat").val(),
		"command":"add_address"
	}

	$.ajax({
		type:"POST",
		url:"/controller",
		data:data,
		success:function (response) {
			var statusCode = response["statusCode"];
			document.location.href="/user";
		},
		statusCode:{
			400: function () {
				$("#city").removeClass("has-error");
				$("#house").removeClass("has-error");
				$("#street").removeClass("has-error");
				$("#flat").removeClass("has-error");
				if($("#user_city").val() === "") {
					$("#city").addClass("has-error");
				}
				if($("#user_house").val() === "") {
					$("#house").addClass("has-error");
				}
				if($("#user_street").val() === "") {
					$("#street").addClass("has-error");
				}
				if($("#user_flat").val() === "") {
					$("#flat").addClass("has-error");
				}
			}
		}


	})

})

	let address={};
	let adr;

	document.onclick = event =>{
		if(event.target.classList.contains("delete2")){
			delete_dish(event.target.dataset.id);
		}
		if(event.target.classList.contains("hide2")){
			hide_dish(event.target.dataset.id);
		}
		if(event.target.classList.contains("show2")){
			show_dish(event.target.dataset.id);
		}
		if(event.target.classList.contains("close_ord")){
			close_order(event.target.dataset.id);
		}
		if(event.target.classList.contains("violate_ord")){
			violate_ord(event.target.dataset.id);
		}
		if(event.target.classList.contains("address")){
			delete_addr(event.target.dataset.id);
		}
		if(event.target.classList.contains("minus")){
			deleteItem(event.target.dataset.id);
		}
		if(event.target.classList.contains("plus")){
			addItem(event.target.dataset.id);
		}
		if(event.target.classList.contains("address_wrapper")){


			address[0] = address[1];
			address[1] = event.target.dataset.id;
			var dropdown_content_ths = document.querySelector('.address_wrapper[data-id="'+address[1]+'"]');
			dropdown_content_ths.style="background-color: #93eda5";
			if(address[0] != null && address[0] !== address[1]){
				var dropdown_content_ths2 = document.querySelector('.address_wrapper[data-id="'+address[0]+'"]');
				dropdown_content_ths2.style="e6a6fa";
			}

			// let element;
			// if (document.getElementById("chosen_address") == null){
			// element =
			// createInputAddressBefore("pay2", address[1], "payment-form2");
			// }else{
			// deleteAddressBefore("chosen_address");
			// createInputAddressBefore("pay2", address[1], "payment-form2");
			//  }
		}
}

function delete_dish(id){

		$.ajax({
			type:"POST",
			url:"/controller",
			data:{
				"command":"delete_dish",
				"id":id
			},
			success:function () {
				alert("ok");
				document.location.href = location.href;
			}
		})

	}



	$("#pay2").click(function () {


		if (address[1] === undefined) {
			swal("Something went wrong", "chose address", "error");
		}else{

			let data =
				{"address" : address[1],
				 "command" : "balance_order",
				 "delivery_time" : document.getElementById("delivery_time").value
				}

				$.ajax({
					type:"POST",
					url:"/controller",
					data:data,
					success:function (data, error, jqXHR) {
						localStorage.clear();
						document.location.href = jqXHR.getResponseHeader("Location");
					},
					statusCode:{
						400:function () {
							swal("Something went wrong", "check balance", "error");
						}
					}

			})
		}
	})

	$("#pay3").click(function () {


		if (address[1] === undefined) {
			swal("Something went wrong", "chose address", "error");
		}else{

			let data =
				{"address" : address[1],
					"command" : "cash_order",
					"delivery_time" : document.getElementById("delivery_time").value
				}

			$.ajax({
				type:"POST",
				url:"/controller",
				data:data,
				success:function (data, error, jqXHR) {
					localStorage.clear();
					document.location.href = jqXHR.getResponseHeader("Location");
				},
				statusCode:{
					400:function () {
						swal("Something went wrong", "check balance", "error");
					}
				}

			})
		}
	})

	$("#pay4").click(function () {


		if (address[1] === undefined) {
			swal("Something went wrong", "chose address", "error");
		}else{

			let data =
				{"address" : address[1],
					"command" : "CREDIT_ORDER",
				}

			$.ajax({
				type:"POST",
				url:"/controller",
				data:data,
				success:function (data, error, jqXHR) {
					localStorage.clear();
					document.location.href = jqXHR.getResponseHeader("Location");
				},
				statusCode:{
					400:function () {
						swal("Something went wrong", "check balance", "error");
					}
				}

			})
		}
	})


	$("#closeCredit").click(function () {

		let elementById = document.getElementById("closeCredit");

			let data = {
				"user_id": elementById.dataset.userid,
				"order_id": elementById.dataset.orderid,
				"command" : "close_credit",
				}

			$.ajax({
				type:"POST",
				url:"/controller",
				data:data,
				success:function (data, error, jqXHR) {
					document.location.href = jqXHR.getResponseHeader("Location");
				},
				statusCode:{
					400:function () {
						swal("Something went wrong", "check balance", "error");
					}
				}

			})
	})



	$("#submit_deposit").click(function () {

	})

	// var form = document.querySelector('#payment-form');
	var form = document.getElementById("pay");

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
					card: {
						overrides: {
							styles: {
								input: {
									color: 'blue',
									'font-size': '16px'
								},
								'.number': {
									'font-family': 'monospace'
									// Custom web fonts are not supported. Only use system installed fonts.
								},
								'.invalid': {
									color: 'red'
								}
							}
						}
					}
				}, function (createErr, instance) {

					var submit_deposit = document.getElementById("submit_deposit");

					if(submit_deposit !== null) {
						submit_deposit.addEventListener('click', function (event) {

							instance.requestPaymentMethod(function (err, payload) {
								event.preventDefault();

								if (err) {
									console.log('Error', err);
									return;
								}


								let data = {
									"command":"pay",
									"payment_method_nonce":payload.nonce,
									"amount":$("#amount").val()
								}


								$.ajax({
									type:"POST",
									url:"/controller",
									data:data,
									success:function (data, error, jqXHR) {
										localStorage.clear();
										document.location.href = jqXHR.getResponseHeader("Location");
									},
									statusCode:{
										400:function () {
											swal("Something went wrong", "check balance", "error");
										}
									}
								})
							});
						})
					}


					if(form !== null){

						form.addEventListener('click', function (event) {


							event.preventDefault();

							instance.requestPaymentMethod(function (err, payload) {
								if (err) {
									console.log('Error', err);
									return;
								}



								let data = {
									"command":"make_order",
									"payment_method_nonce":payload.nonce,
									"delivery_time" : document.getElementById("delivery_time").value,
									"address" : address[1]
								}

								$.ajax({
									type:"POST",
									url:"/controller",
									data:data,
									success:function (data, error, jqXHR) {
										localStorage.clear();
										document.location.href = jqXHR.getResponseHeader("Location");
									},
									statusCode:{
										400:function () {
											swal("Something went wrong", "check balance", "error");
										}
									}
								})
							});
						});

					}

				});
			}
		}

	})



	$("#cancel_order").click(function () {

		let data = {
			"order_id": $("#order_id").val(),
			"command": "cancel_order"
		}

		$.ajax({
			type:"POST",
			url:"/controller",
			data:data,
			success:function(response){
				window.location.reload(true);
			}

		})

	})


	function ban_user(id){

		let data = {
			"command" : "ban_user",
			"id":id
		}

		$.ajax({

			type:"post",
			url:"/controller",
			data:data,
			success:function () {

			}


		})
	}



    // heightBlock($("#product > div"));
});


function close_order(id){

	let data = {
		"command": "close_order",
		"id":id
	}

	$.ajax({
		type:"post",
		url:"/controller",
		data:data,
		success:function (response) {
			var el = document.getElementById("delete"+id);
			el.parentNode.removeChild(el);
		}
	})
}


function deleteItem(item){

	$.ajax({
		type:"POST",
		url:"/controller",
		data: {"command" : "delete_from_cart",
				"id" : item
		},
		success:function () {
			getCart();
		}
	})

}

function addItem(obj) {
	var id = {
		"id":obj,
		"command":"add_to_cart"
	}

	$.ajax({
		type: "POST",
		url:"/controller",
		data:id,
		success: function(response){
			getCart()
		},
		statusCode:{
			400:function () {
				console.log("BAD");
			}
		}

	})

}


function getdetails(obj) {
	var id = {
		"id":obj.id,
		"command":"add_to_cart"
	}

	$.ajax({
		type: "POST",
		url:"/controller",
		data:id,
		success: function(response){
			getCart()
		}

	})

}




function getLocCart() {

	var locCart = JSON.parse(localStorage.getItem("cart"));

	var total_price = 0;



	if(locCart != null && locCart.length !== undefined && document.getElementById("cart_item") !== null) {

		document.getElementById("cart_item").innerHTML = '';


		for (let i = 0; i < locCart.length; i++) {
			let counter1 = 1;

			for (let j = 0; j < i; j++) {
				if (locCart[i]["id"] === locCart[j]["id"]) {
					counter1++;
					var v = document.getElementById("count_" + locCart[i]["id"]);
					v.value = counter1;
					console.log(v.value);
				}
			}

			total_price = total_price + locCart[i]["price"];

			if (counter1 > 1) {
				continue;
			}

			var cart_item = document.createElement("div");
			cart_item.id = "crt_itm_" + locCart[i]["id"];
			cart_item.classList.add("item_class");
			document.getElementById("cart_item").appendChild(cart_item);

			var item_name = document.createElement("div");
			item_name.id = "item_name_id_" + locCart[i]["id"];
			item_name.classList.add("item_name_id");
			item_name.innerHTML = locCart[i]["name"];
			document.getElementById("crt_itm_" + locCart[i]["id"]).appendChild(item_name);

			var item_description = document.createElement("div");
			item_description.id = "item_description_id";
			item_description.classList.add("item_description_id");
			item_description.innerHTML = locCart[i]["description"];
			document.getElementById("crt_itm_" + locCart[i]["id"]).appendChild(item_description);

			var item_low_part = document.createElement("div");
			item_low_part.id = "low_part_" + locCart[i]["id"];
			document.getElementById("crt_itm_" + locCart[i]["id"]).appendChild(item_low_part);

			var weight_price = document.createElement("div");
			weight_price.id = "weight_price_" + locCart[i]["id"];
			weight_price.classList.add("weight_price");
			document.getElementById("crt_itm_" + locCart[i]["id"]).appendChild(weight_price);

			var weight = document.createElement("div");
			weight.innerHTML = locCart[i]["weight"];
			weight.id = "weight_id_" + locCart[i]["id"];
			weight.classList.add("item_weight");
			document.getElementById("weight_price_" + locCart[i]["id"]).appendChild(weight);


			var price = document.createElement("div");
			price.id = "price_id_" + locCart[i]["id"];
			price.innerHTML = locCart[i]["price"];
			price.classList.add("item_price");
			document.getElementById("weight_price_" + locCart[i]["id"]).appendChild(price);


			var minus = document.createElement("button");

			minus.classList.add("btn");
			minus.classList.add("button-info");
			minus.id = "minus_" + locCart[i]["id"];
			minus.classList.add("minus");
			minus.dataset.id = locCart[i]["id"];
			document.getElementById("low_part_" + locCart[i]["id"]).appendChild(minus);


			var count = document.createElement("input");
			count.id = "count_" + locCart[i]["id"];
			count.classList.add("count");
			count.dataset.id = locCart[i]["id"];
			count.disabled = true;
			count.value = 1;
			document.getElementById("low_part_" + locCart[i]["id"]).appendChild(count);


			var plus = document.createElement("button");

			plus.classList.add("btn");
			plus.classList.add("button-info");
			plus.id = "plus_" + locCart[i]["id"];
			plus.classList.add("plus");
			plus.dataset.id = locCart[i]["id"];
			document.getElementById("low_part_" + locCart[i]["id"]).appendChild(plus);

		}

		document.getElementById("total_price").innerHTML = "Total " + total_price.toFixed(2) + " BYN";
	}
}

function delete_addr(id){
	var id = {
		"id": id,
		"command": "delete_address"
	}
	$.ajax({

		type:"POST",
		data:id,
		url:"/controller",
		success:function () {
			document.location.href = "/user";
		}

	})

}

function hide_dish(id){

	let data = {
		"command":"hide_dish",
		"id":id
	}

	$.ajax({

		url:"/controller",
		type:"POST",
		data:data,
		success:function (response) {
			let element = document.getElementById(id);
			element.style = "background-color: #aaaaaa";
			document.location.href = location.href;
		}


	})

}

function violate_ord(id){

	let data = {
		"command": "violate_order",
		"id":id
	}

	$.ajax({
		type:"post",
		url:"/controller",
		data:data,
		success:function (response) {
			var el = document.getElementById("delete"+id);
			el.parentNode.removeChild(el);
		}
	})

}

function show_dish(id){

	let data = {
		"command":"show_dish",
		"id":id
	}

	$.ajax({

		url:"/controller",
		type:"POST",
		data:data,
		success:function (response) {
			let element = document.getElementById(id);
			element.style = "background-color: #ffffff";
			document.location.href = location.href;
		}


	})

}

// function show_info(id){
//
// 	let data = {
// 		"command":"show_user_info",
// 		"id":id
// 	}
//
// 	$.ajax({
// 		type:"POST",
// 		url:"/controller",
// 		data:data,
// 		success:function (response) {
//
// 		}
// 	})
//
// }

function getCart() {

	$.ajax({

		type: "POST",
		url:"/controller",
		dataType: "json",
		data:{"command":"get_cart"},
		success: function (element) {

			localStorage.clear();
			localStorage.setItem("cart", JSON.stringify(element));

			getLocCart();

		},


	})

}





function heightBlock(column){
    var myBlock = 0;

    column.each(function(){
        thisHeight = $(this).height;
        if(thisHeight > myBlock){
            myBlock = thisHeight;
        }
    });
    column.height(myBlock);
}

function checkCorrect(){

	var	count = 0;

	if(!isValidNameSurname($("#name").val())){
		$("#id_name").addClass("has-error");
		count++;
	}else{
		$("#id_name").removeClass("has-error");
	}
	if(!isValidNameSurname($("#surname").val())){
		$("#id_surname").addClass("has-error");
		count++;
	}else{
		$("#id_surname").removeClass("has-error");
	}
	if(!isValidMail($("#email").val())){
		$("#id_email").addClass("has-error");
		count++;
	}else{
		$("#id_email").removeClass("has-error");
		console.log(count);
	}
	if(!isValidPassword($("#signUp_password").val())){
		$("#id_password").addClass("has-error");
		count++;
	}else{
		$("#id_password").removeClass("has-error");
	}
	if(!isValidPhone($("#signUp_phone").val())){
		$("#id_phone").addClass("has-error");
		count++;
	}else{
		$("#id_phone").removeClass("has-error");
	}

	return count === 0;
}





function  isDecimal2(decimal) {
	return /\d+\.\d{0,2}/.test(decimal) || /\d/.test(decimal) ;
}

function isValidMail(email){
	return /^([a-zA-Z0-9_-]+\.)*[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)*\.[a-zA-Z]{2,6}$/.test(email);
}

function isValidPhone(myPhone) {
   return /^\+\d{12}$/.test(myPhone);
}

function isValidPassword(password){
		return /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s){8,}/.test(password);
}

function isValidNameSurname(name){
	return /[A-Za-zА-Я-а-я]{2,30}/.test(name);

}

function deleteAddressBefore(element) {
	let element2 = document.getElementById(element);
	element2.parentNode.removeChild(element2);
}

function createInputAddressBefore(element2, address, form) {
	var element = document.createElement("input");
	element.setAttribute("id", "chosen_address");
	element.setAttribute("type", "hidden");
	element.setAttribute("name", "address");
	element.setAttribute("value", address)
	document.getElementById(form).insertBefore(element, document.getElementById(element2));

}