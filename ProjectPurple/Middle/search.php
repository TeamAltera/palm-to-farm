
<?php

	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept');
	header('Content-Type: application/json');

	$db_host = "localhost";
	$db_user = "root";
	$db_passwd = "619412";
	$db_name = "water_middle_server";

	$conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name) or die("Connected Failed!!!!");

	$return_arr = array();

	$query = "SELECT INNER_IP FROM product_info";
	$result = mysqli_query($conn, $query) or die ('Error Querying database.');
    //echo "$result";

    while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
		$row_array['INNER_IP'] = $row['INNER_IP'];

		array_push($return_arr, $row_array);
	}
	mysqli_close($conn);

    ?>



<?php
// Sys_info의 내용을 조회에서 공유기의 추가중복을 방지하게 하기 위해 만듦.-->

    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept');
    header('Content-Type: application/json');

    $db_host1 = "localhost";
    $db_user1 = "root";
    $db_passwd1 = "619412";
    $db_name1 = "water_middle_server";
	//$str1 = file_get_contents('/var/www/html/user_code.json');

	//$json = json_decode($str1, true);
    $conn_user = mysqli_connect($db_host1, $db_user1, $db_passwd1, $db_name1) or die("Connected Failed!!!!");

    $query_user = "SELECT * FROM Sys_info";
	$result_user = mysqli_query($conn_user, $query_user);
	//true 참 0 이외의 값 , false 거짓 0 //
	$num = mysqli_num_rows($result_user);
	// 데이터베이스의개수.
	$res = 'OK';
	if(  $num >= 1 ){
		$res = 'FAIL';
		$data = ['state' => $res];
	}
	else if( $num == 0) {
        $data = ['state' => $res, 'ssid' => 'pi3-ap', 'inner_ip' => $return_arr];
    }
	echo json_encode($data);

	mysqli_close($conn);
?>