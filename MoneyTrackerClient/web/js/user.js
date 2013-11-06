function addUser() {
    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: "/server/users",
        crossDomain: true,
        data: JSON.stringify($("#divRegister").serializeJSON()),
        success: function (xhr, data) {
            if (xhr.status == 201) {
                showSuccessMessage("User created successfully.")
            }
            return true;
        },
        error: function (error) {
            var errorMessage = error.responseJSON.message;
            if (errorMessage == null || errorMessage == "") {
                showErrorMessage("Unknown error occurred.");
            } else {
                showErrorMessage(errorMessage);
            }
            return false;
        }
    });
}