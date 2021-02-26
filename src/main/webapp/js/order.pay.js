$('.js-order-pay').click(function () {
    let userName = $('.js-user-name').val();
    let userPhone = $('.js-user-phone').val();
    if (userName === '' || userPhone === '') {
        $('.js-error').text('Заполните все данные покупателя');
    } else {
        let orderTickets = [];
        for (let val of tickets.values()) {
            orderTickets.push(val);
        }
        let data = {
            "tickets": orderTickets,
            "name": userName,
            "phone": userPhone
        }
        let loader =
            '   <p class="modal__title-pay">Пожалуйста подождите, идет обработка...</p>' +
            '   <div class="preloader-wrapper active">' +
            '    <div class="spinner-layer spinner-red-only">' +
            '      <div class="circle-clipper left">' +
            '        <div class="circle"></div>' +
            '      </div><div class="gap-patch">' +
            '        <div class="circle"></div>' +
            '      </div><div class="circle-clipper right">' +
            '        <div class="circle"></div>' +
            '      </div>' +
            '    </div>' +
            '  </div>';
        $(".js-modal-content").html(loader);
        $.ajax({
            type: "POST",
            url: "ticket/buy",
            contentType: "application/json",
            data: JSON.stringify(data),
        }).done(function(answer) {
            $(".js-modal-content").html("<p>" + answer + "</p>");
            setTimeout("window.location.reload();", 8000);
        }).fail(function(err){
            $(".js-modal-content").html("<p>" + err.responseText + "</p>");
            setTimeout("window.location.reload();", 8000);
        });
    }
});