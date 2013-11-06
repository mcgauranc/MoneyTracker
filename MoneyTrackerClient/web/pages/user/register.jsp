<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<script type="text/javascript" src="../../js/user.js"></script>

<h1>Login and/or Register</h1>


<div id="divRegister" class="default">
    <form id="frmRegister">
        <div id="divLoginDetails" class="box">
            <div>
                <label for="userName">Username:</label>
                <input type="text" id="userName" name="userName"/>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password"/>
            </div>
        </div>
        <br/>

        <div id="divPersonalDetails" class="box">
            <div>
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName"/>
            </div>
            <div>
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName"/>
            </div>
            <div>
                <label for="dateOfBirth">Date of Birth:</label>
                <input type="text" id="dateOfBirth" name="dateOfBirth"/>
            </div>
            <div>
                <label for="address1">Address 1:</label>
                <input type="text" id="address1" name="address[address1]"/>
            </div>
            <div>
                <label for="address2">Address 2:</label>
                <input type="text" id="address2" name="address[address2]"/>
            </div>
            <div>
                <label for="address3">Address 3:</label>
                <input type="text" id="address3" name="address[address3]"/>
            </div>
            <div>
                <label for="address4">Address 4:</label>
                <input type="text" id="address4" name="address[address4]"/>
            </div>
            <div>
                <label for="city">City:</label>
                <input type="text" id="city" name="address[city]"/>
            </div>
            <div>
                <label for="county">County:</label>
                <input type="text" id="county" name="address[county]"/>
            </div>
            <div>
                <label for="country">Country:</label>
                <select id="country" name="address[country][name]"></select>
            </div>
        </div>
        <button id="btnSave" type="button" onclick="addUser()">Save</button>
        <button id="btnCancel" type="button">Cancel</button>
    </form>
</div>

<script>
    function loadCountryDropdown(url) {
        $("#country").empty();
        $.ajax({
                    type: "GET",
                    url: url,
                    async: false,
                    success: function (data) {
                        $.each(data.content, function (index, value) {
                            $("#country").append(
                                    $('<option value = "' + value.iso + '">' + value.name + '</option>'));
                        });
                    }
                }
        );
    }

    $(function () {
        hideAllMessages();

        // When message is clicked, hide it
        $('.message').click(function () {
            $(this).animate({top: -$(this).outerHeight()}, 500);
        });

        loadCountryDropdown("/server/country");
    });
</script>
<%@ include file="../common/footer.jsp" %>