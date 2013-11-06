var myMessages = ['info', 'warning', 'error', 'success'];

function hideAllMessages() {
    var messagesHeights = []; // this array will store height for each

    for (var index = 0; index < myMessages.length; index++) {
        var message = $('.' + myMessages[index]);
        messagesHeights[index] = message.outerHeight(); // fill array
        message.css('top', -messagesHeights[index]); //move element outside view port
    }
}

function showErrorMessage(message) {
    $('.error p').text(message);
    showMessage('error');
}

function showSuccessMessage(message) {
    $('.success p').text(message);
    showMessage('success');
}

function showMessage(type) {
    $('.' + type).animate({
            top: "0"
        },
        500);
}
