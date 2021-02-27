let tickets = new Map();
let count = 0;
let sum = 0;
$(".js-seats").on("change", ".js-seat-checkbox", function() {
    let row = $(this).data("row");
    let seat = $(this).data("seat");
    let price = $(this).data("price");
    let ticketId = $(this).data("ticket-id");
    let key = String(row) + String(seat);
    if (this.checked) {
        let ticket = {
            "row": row,
            "seat": seat,
            "price": price,
            "ticketId": ticketId
        }
        tickets.set(key, ticket);
        count++;
        sum += price;
    } else {
        tickets.delete(key);
        count--;
        sum -= price;
    }
    $(".js-order-seat-count").text(count);
    $(".js-order-sum").text(sum);
});

!function(e){"function"!=typeof e.matches&&(e.matches=e.msMatchesSelector||e.mozMatchesSelector||e.webkitMatchesSelector||function(e){for(var t=this,o=(t.document||t.ownerDocument).querySelectorAll(e),n=0;o[n]&&o[n]!==t;)++n;return Boolean(o[n])}),"function"!=typeof e.closest&&(e.closest=function(e){for(var t=this;t&&1===t.nodeType;){if(t.matches(e))return t;t=t.parentNode}return null})}(window.Element.prototype);

document.addEventListener('DOMContentLoaded', function() {
    /* Записываем в переменные массив элементов-кнопок и подложку.
       Подложке зададим id, чтобы не влиять на другие элементы с классом overlay*/
    let modalButtons = document.querySelectorAll('.js-open-modal'),
        overlay      = document.querySelector('.js-overlay-modal'),
        closeButtons = document.querySelectorAll('.js-modal-close');

    /* Перебираем массив кнопок */
    modalButtons.forEach(function(item){
        /* Назначаем каждой кнопке обработчик клика */
        item.addEventListener('click', function(e) {
            /* Предотвращаем стандартное действие элемента. Так как кнопку разные
               люди могут сделать по-разному. Кто-то сделает ссылку, кто-то кнопку.
               Нужно подстраховаться. */
            e.preventDefault();
            /* При каждом клике на кнопку мы будем забирать содержимое атрибута data-modal
               и будем искать модальное окно с таким же атрибутом. */
            let modalId;
            if (tickets.size === 0) {
                modalId = 2;
            } else {
                // modalId = this.getAttribute('data-modal');
                modalId = 1;
            }
            let modalElem = document.querySelector('.modal-custom[data-modal="' + modalId + '"]');
            /* После того как нашли нужное модальное окно, добавим классы
               подложке и окну чтобы показать их. */
            if (modalId === 1) {
                showOrder();
            }
            modalElem.classList.add('active');
            overlay.classList.add('active');
        }); // end click
    }); // end foreach

    function showOrder() {
        let orderText = "";
        for (let value of tickets.values()) {
            orderText +=
                "<tr>" +
                    "<td>Ряд " + value.row + " место " + value.seat + "</td>" +
                    "<td class='right-align'>" + value.price + "</td>" +
                "</tr>";
        }
        orderText +=
            "<tr>" +
                "<td><strong>Итого: </strong></td>" +
                "<td class='right-align'><strong>" + sum + "</strong></td>" +
            "</tr>";
        $(".js-order-info").html(orderText);
    }

    closeButtons.forEach(function(item) {
        item.addEventListener('click', function(e) {
            let parentModal = this.closest('.modal-custom');
            parentModal.classList.remove('active');
            overlay.classList.remove('active');
        });
    }); // end foreach

    document.body.addEventListener('keyup', function (e) {
        let key = e.keyCode;
        if (key == 27) {
            document.querySelector('.modal-custom.active').classList.remove('active');
            document.querySelector('.overlay').classList.remove('active');
        };
    }, false);

    overlay.addEventListener('click', function() {
        document.querySelector('.modal-custom.active').classList.remove('active');
        this.classList.remove('active');
    });
}); // end ready