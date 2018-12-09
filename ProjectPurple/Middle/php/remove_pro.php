
<?php
// AP에 연결된 수경재배기 중 삭제가 요청된 수경재배기의 정보를 삭제
// product_info 의 테이블의 내용만 삭제.

    require_once __DIR__ .'/path_ip_class.php';

    $ip_url_settings = Settings::getInstance('php.ini');
    $ip_setting = $ip_url_settings->ip;

    #접속 권한
    //if($ip_setting == $_SERVER['REMOTE_ADDR']) {

        header('Access-Control-Allow-Origin: * ');
        header('Access-Control-Allow-Headers: Origin, X-Requested-With, Contentent-Type, Accept');
        header('Content-Type: application/json');

        $db_host = "localhost";
        $db_user = "root";
        $db_passwd = "619412";
        $db_name = "water_middle_server";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name) or die("Connected Failed!!!!");

        # Get JSON as a string
        $json_str = file_get_contents('php://input');

        # Get as an object
        $json_obj = json_decode($json_str, false); // Object로 받아서 변수에 저장.


        $sfCode = $json_obj->{"sf_code"};
        //echo $json_obj;

        $query = "DELETE FROM PRODUCT_INFO WHERE SF_CODE = $sfCode";
        $result = mysqli_query($conn, $query) or die ('Error Querying database.');

        $key = ['result' => 'OK'];
        echo json_encode($key);
        echo "Delete product_info";
    //}
    //else {
    //   echo "<script>location.replace('/error.php');</script>";
    //    exit();
    //}
    //$str = file_get_contents('/var/www/html/user_code.json');

    //$json = json_decode($str, true);

    //if( array_key_exists("user_code", $json)) {
    //	unlink('/var/www/html/user_code.json');
    //}

?>
