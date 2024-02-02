function popup(obj) {
    $(obj).css("display", "flex");
}

function closePopup(obj) {
    $(obj).css("display", "none");
}


function modalInfo() {
    $(".modal-info").css("display", "none");
}

function modalImage() {
    $(".modal-image").css("display", "none");
}

function modalClose() {
    $(".modal-subscribe").css("display", "none");
    location.reload();
}


