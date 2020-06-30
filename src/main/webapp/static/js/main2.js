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
				user_point.value = response["loyalty_point"];
				let user_money = document.getElementById("user_money");
				user_money.value = response["money"];
				let user_isBan = document.getElementById("isBanned");
				user_isBan.value = response["isBan"];


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