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
?>


<?php
/**
 * Created by PhpStorm.
 * User: big94
 * Date: 2018-07-20
 * Time: 오전 2:20
 */

session_start();
$res = session_destroy();
if($res){
    echo "<script> document.location.replace('/login.php'); </script>";
    //echo "<script> document.location.href='/login.php'; </script>";
}
?>