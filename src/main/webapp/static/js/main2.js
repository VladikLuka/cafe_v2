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
	jQuery(function($) {
		function fixDiv() {
			var $cache = $('#second-header');
			if ($(window).scrollTop() > 100)
				$cache.css({
					'z-index': '9999',
					'position': 'fixed',
					'top': '0px',
					'width': '100%'
				});
			else
				$cache.css({
					'position': 'relative',
				});
		}
		$(window).scroll(fixDiv);
		fixDiv();
	});

});