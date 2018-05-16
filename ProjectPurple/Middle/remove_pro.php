
<?php

// product_info 의 테이블의 내용만 삭제.
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
    $json_obj = json_decode($json_str);

    $query = "DELETE FROM product_info";
    $result = mysqli_query($conn, $query) or die ('Error Querying database.');

    $key = ['result'=>'OK'];
    echo json_encode($key);
    echo "Delete product_info";

    //$str = file_get_contents('/var/www/html/user_code.json');

    //$json = json_decode($str, true);

    //if( array_key_exists("user_code", $json)) {
    //	unlink('/var/www/html/user_code.json');
    //}

?>
