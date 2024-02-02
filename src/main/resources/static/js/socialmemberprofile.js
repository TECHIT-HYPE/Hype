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

// 모달 열기
function openPopup() {
    var modal = document.getElementById('profileModal');
    modal.style.display = 'block';
}

// 모달 닫기
function closePopup() {
    var modal = document.getElementById('profileModal');
    modal.style.display = 'none';
}

// 모달 외의 영역 클릭 시 모달 닫기
window.onclick = function (event) {
    var modal = document.getElementById('profileModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}





