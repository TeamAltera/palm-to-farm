<?php
    require_once __DIR__ . '/path_ip_class.php';

    $db_host = "localhost";
    $db_user = "root";
    $db_passwd = "619412";
    $db_name = "water_middle_server";
    $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name) or die("Not connect DB");

    $a = $_GET['a'];
    $b = $_GET['b'];

    $data_a = json_encode($a);
    $data_b = json_encode($b);

    $ip_url_settings = Settings::getInstance('php.ini');
    $ip_setting = $ip_url_settings->ip;

    $url = $ip_setting.'/change/device';

    $ip_a = '192.168.4.'.$a;
    $ip_b = '192.168.4.'.$b;

    echo $ip_a;
    echo $ip_b;

    $query_update_dclass = "UPDATE PRODUCT_INFO SET INNER_IP = '$ip_b' WHERE INNER_IP = '$ip_a'";
    $result_update_dclass = mysqli_query($conn, $query_update_dclass) or die('Error database Update.');

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
