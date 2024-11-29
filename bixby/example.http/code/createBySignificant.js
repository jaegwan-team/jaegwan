// // 필요한 모듈 불러오기
// var http = require('http'); // HTTP 요청을 위한 http 모듈
// var console = require('console'); // 로그 출력을 위한 console 모듈

// // 이 함수는 'data' 데이터를 외부 서버에 POST 요청으로 전송하고, 응답을 반환합니다.
// module.exports.function = function createBySignificant(IngredientNameInput,AmountInput) {
//   // 전송할 데이터를 'significant' 객체로 정의
//   var data = {
//     "detail": IngredientNameInput + " " + AmountInput + "만큼 사용했습니다.", // 특이사항 설명
//     "restaurantId": 1, // 식당 ID
//     "ingredientName": IngredientNameInput, // 사용된 재료 이름
//     "amount": AmountInput // 사용된 재료의 양
//   };

//   // HTTP 요청 옵션 설정
//   var options = {
//     cacheTime: 0,
//     passAsJson: true, // 데이터를 JSON 형식으로 전송
//     returnHeaders: true, // 응답 헤더를 포함하여 반환
//     format: 'json' // 응답을 JSON 형식으로 수신
//   };

//   // POST 요청을 보낼 URL을 설정하고 요청 전송
//   var response = http.postUrl(
//     'https://k11a501.p.ssafy.io/api/significant', // 요청을 보낼 URL
//     data, // 요청 본문에 보낼 데이터
//     options // 요청 옵션
//   );
  

//   // 응답 데이터를 로그에 출력
//   console.log(response);

//   // 응답의 parsed 데이터를 반환
//   return response.parsed;
// }




module.exports.function = function createBySignificant(IngredientNameInput, AmountInput) {

  var http = require('http');
  var console = require('console');

  var options = {
        cacheTime: 0,
        passAsJson: true,
        returnHeaders: true,
        format: 'json'
    };

  // var kakaoLoginUri='https://k11a501.p.ssafy.io/api/auth/kakao/login'
  //   console.log("요청시작");
  // var result = http.getUrl(kakaoLoginUri, options);
  // console.log("요청완료");
  // console.log("Response for " +Object.entries(result));

  var responses = [];

  // 입력 배열을 반복하여 각각의 HTTP 요청 실행
  for (var i = 0; i < IngredientNameInput.length; i++) {
    var data = {
      "detail": IngredientNameInput[i] + " " + AmountInput[i] + "만큼 사용했습니다.",
      "restaurantId": 1,
      "ingredientName": IngredientNameInput[i],
      "amount": AmountInput[i]
    };

    

    try {
      // 각 데이터를 개별 HTTP 요청으로 전송
      var response = http.postUrl('https://k11a501.p.ssafy.io/api/significant', data, options);
      responses.push(response.parsed); // 각 응답을 배열에 저장
      console.log("Response for " + IngredientNameInput[i] + ": " + JSON.stringify(response.parsed));
    } catch (error) {
      console.error("Error with " + IngredientNameInput[i] + ": " + error.message);
      responses.push({ error: true, message: error.message });
    }
  }
    for (var i = 0; i < response.length; i++) {
      console.log("Response for " + response[i]);
    }

  return responses; // 모든 요청 응답을 반환
};
