function getFilmSessionInfo() {
    $.ajax({
        type: "POST",
        url: "film/session"
    }).done(function(data) {
        console.log(data);
    }).fail(function(err){
        alert(err);
    });
}

$(document).ready(function() {
    getFilmSessionInfo();
});