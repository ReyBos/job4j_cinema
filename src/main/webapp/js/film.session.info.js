$(document).ready(function() {
    getFilmSessionInfo();
});

function getFilmSessionInfo() {
    $('.refresh-button').prop('disabled', true);
    $.ajax({
        type: "POST",
        url: "film/session"
    }).done(function(data) {
        let filmSession = JSON.parse(data.filmSession);
        let hall = JSON.parse(data.hall);
        let allTickets = JSON.parse(data.tickets);
        $(".js-film-session-name").text(filmSession.name);
        $(".js-film-session-date").text((new Date(filmSession.date)).toLocaleString());
        $(".js-film-session-hall").text(hall.name);
        $(".js-refresh-time").text((new Date()).toLocaleString());
        drawSeats(allTickets);
        $('.refresh-button').prop('disabled', false);
        tickets = new Map();
        count = 0;
        sum = 0;
        $(".js-order-seat-count").text(count);
        $(".js-order-sum").text(sum);
    }).fail(function(err){
        alert(err);
    });
}

function drawSeats(allTickets) {
    let currRow = 0;
    let rsl = '';
    allTickets.forEach(function(item, i, arr) {
        if (currRow !== item.row) {
            if (currRow !== 0) {
                rsl += '</div>';
                rsl += '<div class="col s2 row-title"><p>' + currRow + ' ряд</p></div>';
                rsl += '</div>';
            }
            currRow = item.row;
            rsl += '<div class="row">';
            rsl += '<div class="col s2 row-title"><p>' + currRow + ' ряд</p></div>';
            rsl += '<div class="col s8 row-seats">';
        }
        rsl += '<p>';
        rsl += '<input class="js-seat-checkbox" type="checkbox" id="seat' + String(item.row) + String(item.seat) + '" data-row="' + item.row + '" data-seat="' + item.seat + '" data-price="' + item.price + '" data-ticket-id="' + item.id + '" ' + (item.accountId !== 0 ? 'disabled' : '') + '>';
        rsl += '<label for="seat' + String(item.row) + String(item.seat) + '">' + item.seat + '</label>';
        rsl += '</p>';
    });
    rsl += '</div>';
    rsl += '<div class="col s2 row-title"><p>' + currRow + ' ряд</p></div>';
    rsl += '</div>';
    $('.js-seats').html(rsl);
}