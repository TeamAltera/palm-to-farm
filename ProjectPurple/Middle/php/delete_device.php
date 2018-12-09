<?php
/**
 * Created by PhpStorm.
 * User: big94
 * Date: 2018-08-22
 * Time: 오전 7:29
 */
    require_once __DIR__ .'/config_db.php';
    require_once __DIR__ .'/connect_inner_ip.php';

    $ip = $_SERVER['REMOTE_ADDR'];
    $data = explode(".", $ip)[3];

    $query_delete_device = "DELETE FROM PRODUCT_INFO WHERE SF_CODE = $data";
    $result = $conn->query($query_delete_device) or die("ㅠㅠ");

    if($result) {

        echo "<script>location.replace('/main.php');</script>";
    }
    else{
        echo "<script>alert('삭제할 수 없습니다.');</script>";
        echo "<script>location.replace('/main.php');</script>";
}
?>