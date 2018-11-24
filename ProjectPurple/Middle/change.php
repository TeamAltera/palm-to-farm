<?php
// 이 페이지는 아두이노와 라즈베리파이 간에 wi-fi 접속을 하는데
// 필요한 할당 시간이 24h로 되어있다. 그래서 하루가 지나면 종료가 되는 현상을
// 해결하기 위해 만든 페이지이다.

require_once __DIR__ . '/path_ip_class.php'; //ip 접속

    $db_host = "localhost";
    $db_user = "root";
    $db_passwd = "619412";
    $db_name = "water_middle_server";
    $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name) or die("Not connect DB");

    $a = $_GET['a'];
    $b = $_GET['b']; // a, b를 get

    $data_a = json_encode($a);
    $data_b = json_encode($b); //json으로 format

    $ip_url_settings = Settings::getInstance('php.ini');
    $ip_setting = $ip_url_settings->ip;

    $url = $ip_setting.'/change/device'; // jsp 접속 주소.

    $ip_a = '192.168.4.'.$a;
    $ip_b = '192.168.4.'.$b; // ip가 바뀔 때 해당 주소로 변경

    echo $ip_a;
    echo $ip_b;

    $query_update_dclass = "UPDATE PRODUCT_INFO SET INNER_IP = '$ip_b' WHERE INNER_IP = '$ip_a'";
    $result_update_dclass = mysqli_query($conn, $query_update_dclass) or die('Error database Update.');
    // 데이터베이스에 들어가 있는 D클래스의 ip를 변경시킴.

    $qurey_apcode = "SELECT AP_CODE FROM SYS_INFO";
    $result_apcode = mysqli_query($conn, $query_apcode) or die('Error database AP_CODE');

    /*$c = curl_init($url);
    curl_setopt($c, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($c, CURLOPT_POST, true);
    curl_setopt($c, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));

    print curl_exec($c);
    curl_close($c);
*/
    mysqli_close($conn);
?>
