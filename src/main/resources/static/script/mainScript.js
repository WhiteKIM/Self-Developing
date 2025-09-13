/*
 * 화면 메인 스크립트
 * 작성 시 각 기능은 최대한 재활용 가능하도록 쪼개야하며
 * 파라미터, 반환값, 기능에 대한 설명을 주석에 작성해야 함
 * */

const xhr = new XMLHttpRequest();
/*
 * 로그인 버튼 클릭
 * */
const btn_login_onclick = function () {
    const id = document.getElementById("username");
    const password = document.getElementById("password");
    const param = {
       username : id,
       password : password,
    }

    xhr.open("POST", "/api/admin/login")
    xhr.setRequestHeader("Content-Type", "application/json");

    // json데이터 전달
    xhr.send(JSON.stringify(param));
    xhr.onload = () => {
        if(xhr.status === 200) {
            // 넘어온 토큰 정보를 쿠키에 저장
            document.cookie = "Access-Token="+xhr.getResponseHeader("Access-Token")+";";

            window.replace = "/api/admin/main"
        }
    }
 }