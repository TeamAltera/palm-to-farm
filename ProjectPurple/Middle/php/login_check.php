<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>NAT SETTING</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:500,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Muli:400,400i,800,800i" rel="stylesheet">
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/resume.min.css" rel="stylesheet">

    </head>

    <body>
        <?php
        /**
         * Created by PhpStorm.
         * User: big94
         * Date: 2018-07-20
         * Time: 오전 1:43
         */
        include("config_db.php");
        require_once __DIR__ . '/connect_inner_ip.php';

        session_start();

        $id = $_POST['id'];
        $pwd = md5($_POST['pwd']);

        $check = "SELECT * FROM LOGIN_INFO WHERE userid='$id'";
        $result = $conn->query($check);

        if($result -> num_rows == 1) {
            $row = $result->fetch_array(MYSQLI_ASSOC);
            //echo $pwd;
            if($row['userpwd'] == $pwd) { // MYSQLI_ASSOC 필드명으로 첨자 가능
                $_SESSION['userid'] = $id; // 로그인 성공 시 세션 변수 만들기

                if(isset($_SESSION['userid'])) // 세션 변수가 참일 때,
                {
                    //header('Location: ./main.php'); // 로그인 성공시 페이지 이동.
                    echo "<script>location.replace('/main.php');</script>";
                }
                else {
                    echo "세션 저장 실패";
                }
            }
            else
            {
                //echo "틀린 id나 패스워드";
                echo "<script>alert('로그인 실패.');</script>";
                echo "<script>location.replace('/main.php');</script>";
            }
        }
        else {
            //echo "틀린 id나 패스워드1";
            echo "<script>alert('로그인 실패.');</script>";
            echo "<script>location.replace('/main.php');</script>";
        }

        ?>
    </body>
</html>


