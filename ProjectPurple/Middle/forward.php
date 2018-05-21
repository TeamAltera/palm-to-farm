<?php
$db_host = "localhost";
$db_user = "root";
$db_passwd = "619412";
$db_name = "water_middle_server";

$conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name) or die("Connected Database Failed!!!!");
?>


<?php
// 포워딩 페이지 작성.
// 웹서버에서 버튼을 누르면 라즈베리가 받아서 유저코드의 값을 통하여 아두이노 장비로 ip주소와 cmd 값을 넘겨줘야 한다.

    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept');
    header('Content-Type: application/json');

    //Get JSON as a string..
    $json_str = file_get_contents("php://input");

    // Get as an object..
    $json_obj = json_decode($json_str);

    $cmd = $json_obj -> {"cmd"};
    $dest = $json_obj -> {"dest"};
    $user_code = $json_obj -> {"userCode"}; //명시적으로 줄거라서 일단 주석 침. cmd, dest도 마찬가지

    //$cmd = 5;
    //$dest = "192.168.4.11";
    //$user_code = 0; //명시적으로 준 값이므로 바로 윗줄 코드랑 바꾸어 주어야 함.

    $cmd_string = "?cmd=";

    $key = ['cmd' => $cmd, 'dest' => $dest]; // 받아온 cmd, userCode 값을 key에 넣음.
    //echo json_encode($key);

    $query = "SELECT USER_CODE FROM Sys_info WHERE USER_CODE = '$user_code'"; //Sys_info 테이블의 USER_CODE와 $user_code를 비교하여 맞는 USER_CODE를 받는 쿼리문.
    $result_query = mysqli_query($conn, $query) or die ("Error Database connect!!");

    while($data = mysqli_fetch_array($result_query)){

        //print_r($data); // 유져코드 출력 완료. ( 배열로 출력됨. )

        $user_code_data = $data['USER_CODE']; //user_code 값을 변수에 저장.

        if( $user_code == $user_code_data) { //데이터베이스의 유저코드와 서버에서 받아온 유저코드가 같으면
            //echo "success"; //출력 완료.

            $ardu_url = $dest."".$cmd_string; // 요청 url 주소.
            //echo $ardu_url;

            $query_string_data = $dest."".$cmd_string.""."".$cmd; // 쿼리스트링을 전송하기 위한 변수를 만듬.
            //echo $query_string_data; // 출력 완료.

            $ch = curl_init($query_string_data);

            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // 요청 실행.

            curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10); // 타임아웃 설정.

            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false); //get 방식을 이용한다.

            $response = curl_exec($ch);

            $json_data = ['result' => $response];
            echo json_encode($json_data);
        }
        else {
            echo "Compare DB value, json_obj fail";
        }

    }
    //cmd 값..
    ?>
