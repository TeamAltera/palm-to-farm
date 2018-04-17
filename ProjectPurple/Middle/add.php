<?php
	header("Access-Control-Allow-Origin: *");
	header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
	header("Content-Type: application/json");

	$db_host = "localhost";
	$db_user = "root";
	$db_passwd = "619412";
	$db_name = "water_middle_server";

	$conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name) or die("Connected Failed!!!!");

	# Get JSON as a string
	$json_str = file_get_contents('php://input');

	# Get as an object
	$json_obj = json_decode($json_str);

	$user_code = $json_obj->{"user_code"};
	$submit_ip = $json_obj->{"submit_ip"};

	$query = "INSERT INTO Sys_info (USER_CODE, OUTER_IP ) VALUES ( $user_code, '$submit_ip')";
	$result = mysqli_query($conn, $query) or die ('Error database.');
	mysqli_close($conn);

	$key = ['result'=>'OK'];
	echo json_encode($key);

?>