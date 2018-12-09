<?php
header(' 
    Cache-Control: no-store, 
    no-cache, 
    private, 
    max-age=0, 
    must-revalidate, 
    post-check=0, 
    pre-check=0; 
    Pragma: no-cache; 
    Expires: Sat, 26 jul 1997 05:00:00 GMT; 
 ');

    $conn = mysqli_connect("localhost", "root", "619412", "water_middle_server");
    require_once __DIR__ . '/connect_inner_ip.php';
?>

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

    <body id="page-top">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top" id="sideNav">
            <a class="navbar-brand js-scroll-trigger" href="#page-top">
                <span class="d-block d-lg-none">NAT Setting</span>
                <span class="d-none d-lg-block">
              <img class="img-fluid img-profile rounded-circle mx-auto mb-2" src="img/profile1.jpg" alt="">
            </span>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="#about">About</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="#setting_nat">NAT Setting</a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container-fluid p-0">
            <section class="resume-section p-3 p-lg-5 d-flex d-column" id="about">
                <div class="my-auto">
                    <div class="lead mb-5">
                        <h1 class="mb-0"> NAT LOGIN SUCCESS. </h1>
                        <h2 class="mb-1"> Hello NAT!</h2>
                        <p class="mb-2"> Experience NAT.</p>
                    </div>

                    <p> <a class="mb-1" href="logout.php" role="button"> Logout </a></p>
                </div>
            </section>

            <hr class="m-0">

            <section class="resume-section p-3 p-lg-5 d-flex d-column" id="setting_nat">
                <div class="my-auto">
                    <h3 class="mb-5"> NAT SETTING</h3>

                    <div class="lead mb-5">
                        <?php
                        session_start();

                        if (!isset($_SESSION['userid'])) {
                            //header('Location : ./login.php');
                            echo "<script> location.replace('/login.php');</script>";
                        }

                        echo "<a class='mb-1' href=logout.php>Logout</a> <br>";
                        ?>

                        <?php
                        // IP 조회
                        $ip_print = $_SERVER['REMOTE_ADDR'];
                        echo "<p>"."Currently connected device IP : " . $ip_print ."</p>"."</br>";

			$temp = shell_exec("/sbin/ifconfig eth0 | grep inet | cut -d: -f2");
			//echo $temp;
			$ifcon_part1 = explode(" ", trim($temp));
			//print_r($ifcon_part1);
			echo "<p> PUBLIC IP : ".$ifcon_part1[1]."</p><br>";

			//맥주소 조회
                        exec("arp -H ether -n -a ".$_SERVER["REMOTE_ADDR"]."",$values);
                        $parts = explode(' ',$values[0]);
                        echo "<p> Device MAC : ".$parts[3]."</p><br>";
                        ?>

                        <form class="form-horizontal" method = "POST" action = "/delete_device.php">
                            <button class="btn btn-primary" href="delete_device.php" type = "submit" >Delete Device</button><br>
                        </form>


                        <?php
                        $query_SYS = "SELECT * FROM SYS_INFO"; $select_query1 = mysqli_query($conn, $query_SYS) or die ("Error database.. Not select SYS_INFO table.");
                        $query_PRO = "SELECT * FROM PRODUCT_INFO"; $select_query2 = mysqli_query($conn, $query_PRO) or die ("Error database.. Not select PRODUCT_INFO table.");
                        ?>

                        <div class="subheading mb-3">NAT SYS_INFOMAION</div>
                        <ul class="fa-ul mb-0">
                            <li>
                                <i class="fa-li fa fa-check"></i>
                                SYS_INFO TABLE</li><br>
                            <?php echo "AP_CODE &nbsp &nbsp IP<br>" ?>
                            <?php while($row1 = mysqli_fetch_array($select_query1)) { ?>
                            <li>
                                <?php echo $row1['AP_CODE']."&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp ".$row1['PUBLIC_IP'] ?> </li><br>
                            <?php } ?>
                        </ul>

                        <div class="subheading mb-3">NAT PRODUCT_INFOMAION</div>
                        <ul class="fa-ul mb-0">
                            <li>
                                <i class="fa-li fa fa-check"></i>
                                PRODUCT_INFO TABLE</li><br>
                            <?php echo "SF_CODE &nbsp &nbsp IP<br>" ?>
                            <?php while($row2 = mysqli_fetch_array($select_query2)) { ?>
                                <li>
                                    <?php echo $row2['SF_CODE']."&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp ".$row2['INNER_IP'] ?> </li>
                            <?php }  mysqli_close($conn); ?> <br>
                        </ul>
                    </div>

                </div>

            </section>
        </div>

        <!-- Bootstrap core JavaScript -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Plugin JavaScript -->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for this template -->
        <script src="js/resume.min.js"></script>

    </body>
</html>
