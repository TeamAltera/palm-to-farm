<?php
/**
 * Created by PhpStorm.
 * User: big94
 * Date: 2018-07-20
 * Time: 오전 2:17
 */


    session_start();

    if (!isset($_SESSION['userid'])) {
        header('Location : ./login.php');
    }
    echo "홈(로그인) 성공</br>";
    echo "<a href=logout.php>logout</a> </br>";

    $ip_print = $_SERVER['REMOTE_ADDR'];
    echo "현재 접속한 장치의 아이피 : " . $ip_print . "</br>";

?>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width" initial-scale="1">
        <title>공유기 로그인 완료.</title>
        <link rel="stylesheet" href="css/bootstrap.css">
    </head>

    <body>
        <div class="container">
            <div class="jumbotron">
                <h1 class="text-center"> 공유기에 로그인 하였습니다.</h1>
                <p class="text-center"> 하잇! </p>
                <p class="text-center"> <a class="btn btn-primary btn-lg" href="logout.php" role="button"> 로그아웃 </a></p>

            </div>
        </div>
    </body>
</html>
