function copy(target, id) {
    var button = $(target);
    var clipboard = button.parent().find("input[type='text'][id='" + id + "']");

    clipboard.select();

    try {
        document.execCommand('copy');

        button.blur();
        button.addClass('copied');

        setTimeout(function () {
            button.removeClass('copied');
        }, 1500);
    } catch (err) {
        console.log(err);
    }
}
