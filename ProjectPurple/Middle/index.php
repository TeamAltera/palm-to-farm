
<?php
// 아두이노에서 오는 IP를 받아서 자동으로 product_info 테이블에 장비를 추가해주는 코드. -->
// userCode를 이용하여 데이터의 내용을 URL(203.250.32.180:9001/device/sf/auto)로 전송해야함 -->


    $db_host = "localhost";
    $db_user = "root";
    $db_passwd = "619412";
    $db_name = "water_middle_server";

    $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name) or die("Connected Database Failed!!!!");
?>

<?php
    require_once __DIR__ .'/vendor/autoload.php';

    use Middle;

    $ip = $_GET['ip']; //Query_string
    //$site = $_SERVER['DOCUMENT_ROOT']; //index.php road
    //$self_ip = $_SERVER['SERVER_ADDR']; //my ip
    //$whois_user = $_SERVER['REMOTE_ADDR']; //웹서버의 요청을 보내는 사용자ip
    //$using_port = $_SERVER['SERVER_PORT']; //클라이언트 포트

    //printf("Root : %s<br/>", $site);
    //printf("SERVER_IP : %s<br/>",$self_ip);
    //printf("USER_IP : %s<br/>", $whois_user);
    //printf("PORT : %s<br/>", $using_port);
    echo "QUERY_STRING_IP : ".$ip; echo "</br>";

    if( $ip ){
        //echo "get ip<br/>";
        $led = 'N';
        $state = 'N';
        $register = 'N';

        $query = "INSERT INTO product_info  VALUES ('$ip', '$led', '$state', '$register')";
        $result_ip = mysqli_query($conn, $query) or die ('Error database.. not connect product table.');
        echo '  Customer added.'; echo "</br>";

        // 아랫줄부터 user_code의 존재여부를 확인 후 POST 방식으로 전송함.
        $query_user = "SELECT * FROM Sys_info"; //echo $query; echo "</br>";
        $result_user = mysqli_query($conn, $query_user) or die ("Error database.. not connect Sys_info table.");
        // true 참 0 이외의 값, false 거짓 0

        $num = mysqli_num_rows($result_user); //echo "$num";
        //Sys_info의 table 행 개수 저장.

        if( $num >= 1) {
            //user_code가 존재한다면.
            $exist_query = "SELECT * FROM Sys_info";
            $exist_result = mysqli_query($conn, $exist_query) or die ("Error database.. not connect Sys_info 2 table.");

            $row = mysqli_fetch_array($exist_result, MYSQLI_BOTH);

            $user_code = $row['USER_CODE']; //user_code를 변수에 넣음.
            $sys_info_ip = $row['OUTER_IP']; //user_code의 ip를 변수에 넣음.

            $fields = array(
                    'apInfo' => urlencode($sys_info_ip),
                    'ipInfo' => urlencode($ip),
                    'userCode' => urlencode($user_code)
            );

            foreach($fields as $key=>$value) { $fields_string .= $key.'='.$value.'&'; }
            $fields_string = rtrim($fields_string,'&');


            //open connection
            $ch = curl_init();

            //set the url, number of POST vars, POST data
            curl_setopt($ch,CURLOPT_URL,$url);
            curl_setopt($ch,CURLOPT_POST,count($fields));
            curl_setopt($ch,CURLOPT_POSTFIELDS,$fields_string);

            //execute post
            $result = curl_exec($ch);
            print $result;
        }

        else {
            echo "Please user_code input..";
        }

        mysqli_close($conn);
    }

    else {
        echo "Please get ip...";
    }

    //chmod("./var/www/html/inner_ip.json", 777);

    //$is_file_exist = file_exists('/var/www/html/inner_ip.json');

    //	$iptables_version = shell_exec("sudo iptables --version");
    //	echo "<pre> $iptables_version </pre>";

    ///	$port_forward = exec("sudo iptables -t nat -A PREROUTING -p tcp -i eth0 --dport 9000 -j DNAT --to 192.168.4.19:80");
    //	echo "<pre> $port_forward </pre>";

    //	$port_save = shell_exec('sudo sh -c "iptables-save > /etc/tables/rules.v4"');
    //	echo "<pre> $port_save</pre>";

    //	$iptables_show = shell_exec("/etc/iptables sudo iptables -L");
    //	echo "<pre>$iptables_show</pre>";

    //	$data = shell_exec("ls");
    //	echo "<pre>$data</pre>";

    //	echo shell_exec("<pre>whoami</pre>");

    //	echo shell_exec("<pre>ls -al</pre>");

?>
