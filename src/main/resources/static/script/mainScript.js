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
    const id = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch('/admin/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username : id,
            password : password,
        })
    })
    .then(res => res.json())
    .then(data => {
        if (data.redirectUrl) {
            window.location.href = data.redirectUrl;
        }
    });
 }

/**
 * 메인화면 차트 출력
 */
document.addEventListener("DOMContentLoaded", function() {
    const ctx = document.getElementById('userChart').getContext('2d');

    new Chart(ctx, {
        type: 'bar', // 차트 형태 (bar, line, pie, doughnut 등)
        data: {
            labels: ['1월', '2월', '3월', '4월', '5월', '6월'],
            datasets: [{
                label: '신규 가입자 수',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            responsive: true,
            maintainAspectRatio: false
        }
    });
});