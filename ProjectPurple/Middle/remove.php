<?php
        header('Access-Control-Allow-Origin: * ');
        header('Access-Control-Allow-Headers: Origin, X-Requested-With, Contentent-Type, Accept');
        header('Content-Type: application/json');

        $str = file_get_contents('/var/www/html/user_code.json');

	$json = json_decode($str, true);

	if( array_key_exists("user_code", $json)) {
		unlink('/var/www/html/user_code.json');
	}

?>

