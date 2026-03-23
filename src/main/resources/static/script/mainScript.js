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
    const ctx1 = document.getElementById('regChart').getContext('2d'); // 시험현황
    const ctx4 = document.getElementById('diffyChart').getContext('2d'); // 난이도별 현황

    var regInfo = document.getElementById('regChart').dataset.statInfo;
    var difyInfo = document.getElementById('diffyChart').dataset.statInfo;

    console.log(difyInfo);

    // 시험정보 현황
    new Chart(ctx1, {
        type: 'bar', // 차트 형태 (bar, line, pie, doughnut 등)
        data: {
            labels: ['자격증', '시험', '시험지', '문제'],
            datasets: [{
                label: '등록 건수',
                data: [regInfo.certCnt, regInfo.pageCnt, regInfo.paperCnt, regInfo.problemCnt],
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

    // 문제풀이 현황

    // 난이도별 현황
    var diffLabel = [];
    var diffRightCount = [];
    var diffWrongCount = [];

    for(var i = 0; i < difyInfo.length; i++) {
        diffLabel.push(difyInfo[i].difficulty);
        diffRightCount.push(difyInfo[i].rightCnt);
        diffWrongCount.push(difyInfo[i].wrongCnt);
    }

    new Chart(ctx4, {
            type: 'bar', // 차트 형태 (bar, line, pie, doughnut 등)
            data: {
                labels: diffLabel,
                datasets: [
                    {
                        label: '정답',
                        data: diffRightCount,
                        backgroundColor: 'blue',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    },
                    {
                        label: '오답',
                        data: diffWrongCount,
                        backgroundColor: 'red',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }
                ]
            },
            options: {
                scales: {
                    y: {
                        stacked : true,
                        beginAtZero: true
                    }
                },
                responsive: true,
                maintainAspectRatio: false
            }
    });
});

/**
 * 삭제버튼 클릭
 */
function btn_delete_onclick(userId) {
    fetch('/admin/user/delete?memberId=' + userId, {
      method : 'DELETE',
    }).then(res => res.json())
    .then(data => {
      console.log(data);
    });
  }