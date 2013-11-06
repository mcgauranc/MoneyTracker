<%@ page session="false" %>
<div id="divLogin">
    <p>Please enter your login credentials</p>

    <form id="frmLogin">
        <div>
            <label for="username">User:</label>
            <input type='text' id="username" name="username"/>
        </div>
        <div style="clear: both;"></div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password"/>
        </div>
        <div style="clear: both;"></div>
        <div id="divRememberMe">
            <input type="checkbox" name="rememberMe" id="rememberMe"/>
            <label for="rememberMe">Don't ask for my password for two weeks</label>
        </div>
        <div style="clear: both;"></div>
        <button id="btnLogin" type="button">Login</button>
    </form>

    <script>
        function ajaxLogin(form) {

            var myCallback = form.myCallback.value;
            var userName = form.username.value;
            var password = form.password.value;
            var rememberMe = form.rememberMe.value;
            $.ajax({
                cache: false,
                type: 'POST',
                url: "/server/j_spring_security_check",
                crossDomain: true,
                async: false,
                data: { j_username: userName, j_password: password, _spring_security_remember_me: rememberMe },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("x-ajax-call", "true");
                },
                success: function (result, xhr) {
                    if (result == "ok") {
                        $("#loginError").html("");
                        $("#frmLogin").hide();
                        if (myCallback != null && myCallback != 'undefined' && myCallback != '') {
                            eval(myCallback.replace(/_/g, '"'));
                        }
                        return true;
                    } else {
                        $("#loginError").html('<span  class="alert display_b clear_b center align">Bad user/password</span>');
                        return false;
                    }

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $("#loginError").html("Bad user/password");
                    return false;
                }
            });
        }
    </script>
</div>
