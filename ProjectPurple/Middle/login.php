<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.css">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>NAT PAGE</title>

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

        <style type="text/css">
            .form-control.text-left{
                padding: 10px;
                font-size: 16px;
                height: auto;
                width: auto;
            }
        </style>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top" id="sideNav">
            <a class="navbar-brand js-scroll-trigger" href="#page-top">
                <span class="d-block d-lg-none" href="/login.html">NAT PAGE</span>
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
                        <a class="nav-link js-scroll-trigger" href="#login">Login</a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container-fluid p-0">
            <section class="resume-section p-3 p-lg-5 d-flex d-column" id="about">
                <div class="my-auto">
                    <h1 class="mb-0">NAT
                        <span class="text-primary">PAGE</span>
                    </h1>
                    <div class="subheading mb-5"> Enginnering college 520 Altera LAB · Catholic University of Daegu  · 010-3352-5812 ·
                        <a href="mailto:name@email.com">big9401@gmail.com</a>
                    </div>
                    <p class="lead mb-5">This is NAT PAGE.</p>
                </div>
            </section>

            <hr class="m-0">

            <section class="resume-section p-3 p-lg-5 d-flex flex-column" id="login">
                <div class="my-auto">
                    <h1 class="mb-5"> LOGIN </h1>
                    <h3 class="mb-5"> Please Login..</h3>

                    <form class="form-horizontal" method = "POST" action = "/login_check.php">
                        <input class="form-control text-left" type = "text" name = "id" placeholder="ID">
                        <input class="form-control text-left" type = "password" id="input_pwd" name = "pwd" placeholder="PASSWORD">
                        <br>
                        <button class="btn btn-primary" href="login_check.html" type = "submit" >LOGIN</button>
                    </form> <p class="text-left"> <a class="btn btn-primary btn-lg" href="sign_up.html" role="button"> Sign UP </a></p>
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