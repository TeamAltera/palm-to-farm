<?php
	header("Access-Control-Allow-Origin: *");
        header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
	header("Content-Type: application/json");

	$method = $_SERVER['REQUEST_METHOD'];

	if($method == "POST" ){

	chmod("./var/www/html/user_code.json", 777);

	$request_body = file_get_contents("php://input");
	$info = json_decode(stripcslashes($request_body), true);
	$user_code = $info['user_code'];
	file_put_contents("user_code.json", json_encode(array('user_code' => $user_code), JSON_PRETTY_PRINT) );

	$key = ['result'=>'OK'];
	echo json_encode($key);
	}

//	if($method == "GET"){

//	chmod("./var/www/html/inner_ip.json", 777);

//	$reauest_body = file_getcontents("php://input");
//	$info = json_decode(stripcslashes($request_body), true);

//	$ip = $_GET['ip'];
//	file_put_contents("inner_ip.json", json_encode(array('ip' => $ip), JSON_PREETY_PRINT) );

//	$key2 = ['result' => 'OK'];
//	echo json_encode($key2);
//	}
?>
