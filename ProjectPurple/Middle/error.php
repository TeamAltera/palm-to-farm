<?php
/**
 * Created by PhpStorm.
 * User: big94
 * Date: 2018-08-22
 * Time: 오전 4:53
 */
?>

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
                <a class="nav-link js-scroll-trigger" href="#about">error</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container-fluid p-0">
    <section class="resume-section p-3 p-lg-5 d-flex d-column" id="about">
        <div class="my-auto">
            <div class="lead mb-5">
                <h1 class="mb-0" > Not connect NAT. </h1>
                <h2 class="mb-5" > Please Connect WIFI OR server!!</h2>
            </div>
        </div>
    </section>

    <hr class="m-0">
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

