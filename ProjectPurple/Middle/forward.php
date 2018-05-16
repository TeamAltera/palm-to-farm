
<?php
// 포워딩 페이지 작성.
    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept');
    header('Content-Type: application/json');

    //Get JSON as a string..
    $json_str = file_get_contents("php://input");

    // Get as an object..
    $json_obj = json_decode($json_str);

    $cmd = $json_obj -> {"cmd"};
    $dest = $json_obj -> {"dest"};
    $user_code = $json_obj -> {"userCode"};
    //$cmd = $_POST['cmd'];
    //$dest = $_POST['dest'];

    $key = ['cmd' => $cmd, 'dest' => $dest];
    echo json_encode($key);

    $keys = array_column($json_obj, "cmd");
    $keys1 = array_column($json_obj, "dest");

    echo implode(', ', $keys);
    ?>
