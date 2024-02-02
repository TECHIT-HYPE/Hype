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


function handleFileSelect() {
    // 파일 선택 로직 구현

    // 선택한 파일 정보를 FormData에 추가
    var formData = new FormData();
    formData.append('multipartFiles', document.getElementById('userProfileImageInput').files[0]);

    // Ajax를 사용하여 파일 업로드
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/social/profile/' + profileDto.id, true);
    xhr.onload = function() {
        // 업로드 완료 후의 처리 로직
        console.log('Upload complete!');
    };

    // FormData를 전송
    xhr.send(formData);
}



