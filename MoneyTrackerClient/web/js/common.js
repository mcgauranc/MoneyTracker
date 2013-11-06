function verifyAuthentication(data, callbackString) {

    if (isNaN(data) && (data.indexOf("login_hidden_for_ajax") != -1)) {
        $("#myCallback".val(callbackString));

        var windowHeight = $(window).height();
        var windowWidth = $(window).width();

        var formLogin = $("#frmLogin");
        formLogin.css("top", windowHeight / 2 - formLogin.height() / 2);
        formLogin.css("left", windowWidth / 2 - formLogin.width() / 2);
        formLogin.fadeIn(2000);
        return false;
    }
    return true;
}