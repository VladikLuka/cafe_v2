// Модальное окно


$(document).ready(function() {

	// открыть по кнопке
	$('.js-button-campaign-signup').click(function () {
		$('.js-overlay-campaign-login').fadeOut();
		$('.js-overlay-campaign-signup').fadeIn().addClass('disabled');
	});

// закрыть на крестик
	$('.js-close-campaign-signup').click(function () {
		$('.js-overlay-campaign-signup').fadeOut();

	});

// закрыть по клику вне окна
	$(document).mouseup(function (e) {
		var popup = $('.js-popup-campaign-signup');
		if (e.target != popup[0] && popup.has(e.target).length === 0) {
			$('.js-overlay-campaign-signup').fadeOut();

		}
	});


// открыть по кнопке
	$("#login-popup").click(function () {

		$('.js-overlay-campaign-signup').fadeOut();
		$('.js-overlay-campaign-login').fadeIn().addClass('disabled');

	})

// закрыть на крестик
	$('.js-close-campaign-login').click(function () {
		$('.js-overlay-campaign-login').fadeOut();

	});

// закрыть по клику вне окна
	$(document).mouseup(function (e) {
		var popup = $('.js-popup-campaign-login');
		if (e.target !== popup[0] && popup.has(e.target).length === 0) {
			$('.js-overlay-campaign-login').fadeOut();

		}
	});

	$('.js-button-cart').click(function () {
		$('#cart_1').fadeIn().addClass('disabled');
	});


	$('#show_less_cart').click(function () {
		$('#cart_1').fadeOut();
	});

	$('#add_address').click(function () {
		$('.overlay2').fadeIn().addClass('disabled');
	});


	$(document).mouseup(function (e) {
		var popup = $('.js-button-address');
		if (e.target !== popup[0] && popup.has(e.target).length === 0) {
			$('.overlay2').fadeOut();

		}
	});


	////////////////////////////////////////////
	// jQuery(function($) {
	// 	function fixDiv() {
	// 		var $cache = $('#second-header');
	// 		if ($(window).scrollTop() > 95)
	// 			$cache.css({
	// 				'z-index': '9999',
	// 				'position': 'fixed',
	// 				'top': '0px',
	// 				'width': '100%'
	// 			});
	// 		else
	// 			$cache.css({
	// 				'position': 'relative',
	// 			});
	// 	}
	// 	$(window).scroll(fixDiv);
	// 	fixDiv();
	// });



	$('.show_info').click(function (e) {
		$('.user_overlay').fadeIn().addClass('disabled');

		let id = e.target.dataset.id;

		let data = {
			"command":"show_user_info",
			"id":id
		}

		$.ajax({
			type:"POST",
			url:"/controller",
			data:data,
			dataType:"json",
			success:function (response) {

				$("#div_add_money").removeClass("has-error")
				$("#div_add_point").removeClass("has-error");
				$("#div_grab_money").removeClass("has-error")
				$("#div_grab_points").removeClass("has-error")

				let user_id = document.getElementById("user_id");
				user_id.value = response["id"];
				let user_name = document.getElementById("user_name");
				user_name.value = response["name"];
				let user_surname = document.getElementById("user_surname");
				user_surname.value = response["surname"];
				let user_phone = document.getElementById("user_phone");
				user_phone.value = response["phone"];
				let user_mail = document.getElementById("user_email");
				user_mail.value = response["mail"];
				let user_point = document.getElementById("user_point");
				user_point.value = response["loyaltyPoint"];
				let user_money = document.getElementById("user_money");
				user_money.value = response["money"];
				let user_isBan = document.getElementById("isBanned");
				user_isBan.value = response["isBan"];
				let user_role = document.getElementById("user_role");
				user_role.value = response["role"];

			}
		})

	});

// закрыть по клику вне окна
	$(document).mouseup(function (e) {
		var popup = $('.user_popup');
		if (e.target != popup[0] && popup.has(e.target).length === 0) {
			$('.user_overlay').fadeOut();

		}
	});
});


// открыть по кнопке
$("#addProduct").click(function () {

	let path = document.location.pathname;
	let strings = path.split("/");
	let page = strings[1];

	let id = "submit_dish_" + page;

	swal({
		title: "An input!",
		text: "Write something interesting:",
		showCancelButton: true,
		closeOnConfirm: true,
		animation: "slide-from-top",
		inputPlaceholder: "Write something",
		html:"<input type='file' name='file' id='dish_picture' value='add_picture' multiple accept='image/*'> " +
			"<input type='submit' id='send_picture' value='add picture'>" +
			"<lable for='swal-input1'>dish name</lable>" +
			"<input id=\"swal-input1\" class=\"swal2-input\" placeholder='Write smth'> \n" +
			"<lable for='swal-input2'>dish description</lable>" +
			"<input id=\"swal-input2\" class=\"swal2-input\" placeholder='Write smth'>" +
			"<lable for='swal-input3'>dish weight</lable>" +
			"<input id=\"swal-input3\" class=\"swal2-input\" placeholder='Write smth'> \n" +
			"<lable for='swal-input4'>dish price</lable>" +
			"<input id=\"swal-input4\" class=\"swal2-input\" placeholder='Write smth'> \n" +
			"<input type='submit' id='submit_dish'  value='submit_dish'/>" +
			" "
	})

	let picturePath;

	$("#send_picture").click(function () {

		let data = new FormData();
		jQuery.each(jQuery('#dish_picture')[0].files, function(i, file) {
			data.append('file-'+i, file);
		});

		$.ajax({
			type:"POST",
			url:"/upload",
			data:data,
			cache: false,
			contentType: false,
			processData: false,
			success:function (response) {

				picturePath = response;

				if(document.getElementById("picture") !== null){
					let elementById = document.getElementById("picture");
					elementById.parentNode.removeChild(elementById);
				}
				let image = document.createElement("img");
				image.setAttribute("style", "height: 200px; width 150px;");

				image.id = "picture";
				image.src = response;
				document.getElementById("dish_picture").before(image);
			}
		})



	})


	$("#submit_dish").click(function(){

		if(page === "pizza"){
			page = 4;
		}else if(page === "drink"){
			page = 1;
		}else if(page === "meat"){
			page = 5;
		}else if(page === "garnish"){
			page = 3;
		}else if(page === "sushi"){
			page = 6;
		}else if(page === "salad"){
			page = 2;
		}


		let data = {
			"command":"create_dish",
			"dishName":$("#swal-input1").val(),
			"dishDescription":$("#swal-input2").val(),
			"dishWeight":$("#swal-input3").val(),
			"dishPrice":$("#swal-input4").val(),
			"dishCategory":page,
			"dishPicture": picturePath,

		}

		$.ajax({
			url:"/controller",
			type:"post",
			data:data,
			success:function () {
				document.location.href = location.href;
			}

		})


	})

})





