function CustomAlert() {
    let self = this;
    let isAlertShowing = false; // 경고창 표시 여부를 나타내는 상태 변수
    let autoDismissTimer; // 자동으로 사라지는 타이머의 ID를 저장할 변수

    this.alert = function (message, title) {
        // 경고창이 이미 표시 중이라면 동작하지 않음
        if (isAlertShowing) return;

        // 경고창을 생성하고 화면 중앙에 위치시킴
        document.body.innerHTML += '<div id="dialogoverlay"></div><div id="dialogbox" class="slit-in-vertical"><div><div id="dialogboxhead"></div><div id="dialogboxbody"></div><div id="dialogboxfoot"></div></div></div>';

        let dialogoverlay = document.getElementById('dialogoverlay');
        let dialogbox = document.getElementById('dialogbox');

        let winH = window.innerHeight;
        dialogoverlay.style.height = winH + "px";

        // dialogbox.style.top = "50%";
        // dialogbox.style.left = "50%";
        // dialogbox.style.transform = "translate(-50%, -50%)";

        dialogoverlay.style.display = "block";
        dialogbox.style.display = "block";

        document.getElementById('dialogboxhead').style.display = 'block';

        if (typeof title === 'undefined') {
            document.getElementById('dialogboxhead').style.display = 'none';
        } else {
            document.getElementById('dialogboxhead').innerHTML = '<i class="fa fa-exclamation-circle" aria-hidden="true"></i> ' + title;
        }
        document.getElementById('dialogboxbody').innerHTML = message;
        document.getElementById('dialogboxfoot').innerHTML = '<button class="pure-material-button-contained active" onclick="customAlert.ok()">OK</button>';

        // 경고창 표시 상태를 설정
        isAlertShowing = true;

        // 2초 후에 경고창 자동으로 사라지도록 설정
        autoDismissTimer = setTimeout(function () {
            self.ok(); // OK 버튼을 누르지 않고도 2초 후에 경고창이 사라지도록 self.ok() 호출
        }, 2000);
    }

    this.ok = function () {
        // 경고창 표시 중이 아니라면 동작하지 않음
        if (!isAlertShowing) return;

        let dialogoverlay = document.getElementById('dialogoverlay');
        let dialogbox = document.getElementById('dialogbox');
        if (dialogoverlay && dialogbox) {
            dialogoverlay.parentNode.removeChild(dialogoverlay);
            dialogbox.parentNode.removeChild(dialogbox);
        }

        // 경고창 표시 상태를 초기화
        isAlertShowing = false;

        // 자동으로 사라지는 타이머를 취소
        clearTimeout(autoDismissTimer);
    }
}

let customAlert = new CustomAlert();
