

<?php
// AP의 등록정보를 삭제하고, 연결된 수경재배기들 정보 또한 삭제
// product_info 와 Sys_info 두 개의 테이블의 내용을 삭제.

    require_once __DIR__ .'/path_ip_class.php';

    $ip_url_settings = Settings::getInstance('php.ini');
    $ip_setting = $ip_url_settings->ip;

    //if($ip_setting == $_SERVER['REMOTE_ADDR']) {

        header('Access-Control-Allow-Origin: * ');
        header('Access-Control-Allow-Headers: Origin, X-Requested-With, Contentent-Type, Accept');
        header('Content-Type: application/json');

        $db_host = "localhost";
        $db_user = "root";
        $db_passwd = "619412";
        $db_name = "water_middle_server";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name) or die("Connected Failed!!!!");

        $query_delete_pro = "DELETE FROM PRODUCT_INFO";
        $result = mysqli_query($conn, $query_delete_pro) or die ('Error Querying database.');

        $query_delete_sys = "DELETE FROM SYS_INFO";
        $result1 = mysqli_query($conn, $query_delete_sys) or die ('Error Querying database.');

        $key = ['result' => 'OK'];

        echo json_encode($key);

        echo "Delete Sys_info and product_info";
    //}

    //else {
    //    echo "<script>location.replace('/error.php');</script>";
    //    exit();
    //}
    //$str = file_get_contents('/var/www/html/user_code.json');

	//$json = json_decode($str, true);

	//if( array_key_exists("user_code", $json)) {
	//	unlink('/var/www/html/user_code.json');
	//}

?>

