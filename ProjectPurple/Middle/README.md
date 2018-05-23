# RaspberryPi 3  B Model AP Mode

Hello. How to access point RapsberryPi.
```
AP란?

* Access Point : 와이파이를 이용한 관련 표준을 이용하여 무선 장치들을 유선 장치에 연결할 수 있게 하는 장치.

즉, 공유기와 비슷한 역할을 하게 된다고 합니다.
```
<<<<<<< HEAD


=======
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
ref
https://www.raspberrypi.org/documentation/configuration/wireless/access-point.md

dnsmasq와 hostapd가 무엇인지부터 알아봐야겠네요??



dnsmasq : 1000클라이언트 이하의 로컬 네트워크에서 활용할 수 있는 간단한 DHCP/DNS 서버라고 하네요.

핵심 특징은 쉬운 설정과 소규모에서 활용하기 좋은 시스템이라고 하네요.!

즉, 와이파이를 사용하고자 하는 사용자가 오면 그 사용자에게 IP와 DNS를 할당해주는 패키지라고 생각하면 편할 것 같아요.



DHCP는 또 뭐고 DNS는 또 무엇일까??



일단 DHCP는 우리가 전화를 사용할 때 유일한 전화번호가 있듯이 인터넷에도 유일한 주소가 있겠죠??

그런 것들을 IP주소라고 흔히 부르고 있죠~?



그런데 보면 컴퓨터 사고 랜선 꼽으면 그냥 아무 설정 안했는데 자동으로 그냥 인터넷 연결도 되고~

편하게 사용할 수 있죠??

그렇게 해주는 설정이 바로 DHCP설정이라고 하네요!



DHCP를 통한 IP주소 할당은 흔히 '임대'라는 개념을 가지고 있어서 IP주소를 임대기간을 명시하여 그 기간 동안에만 단말이

IP주소를 사용할수 있다고 하고 더 사용하고자 하면 기간연장을 서버에 요청하고 필요없으면 반납절차를 수행하게 된다고 하네요.



DNS는 흔히 naver.com이나 google.co.kr 같은 도메인 네임 서버를 뜻하죠??







그럼 hostapd는 또 무엇일까요??

hostapd와 관련된 내용은 영문으로 된 위키백과를 참고하였는데요

설명으로는 



hostapd is a user space daemon for wireless access point and authentication servers. There are three implementations: Jouni Malinen's hostapd, OpenBSD's hostapd and Devicescape's hostapd.


Contents  [hide] 


AP와 인증 서버를 위한 사용자 공간 데몬이라고 하네요?? 데몬은 흔히 백그라운도 프로세스라고 불리고..

자세하겐 잘 안나와있어서 알기가 힘드네요 ㅋㅋ..

그냥 무선 네트워크 인터페이스는 AP모드로 전환하여 서비스를 하게 해주는 패키지라고 생각하면 될 것 같아요.



번역 극혐..

The Raspberry Pi can be used as a wireless access point, running a standalone network. 
This can be done using the inbuilt wireless features of the Raspberry Pi 3 or Raspberry Pi Zero W, or by using a suitable USB wireless dongle that supports access points.

라즈베리파이는 독립형 와이파이 ap로 사용할 수 있다. 

라즈베리파이 3, 제로W 에 무선요소가 내장되었거나 AP를 지원하는 USB 동글이 탑제된 라즈베리도 가능하다.

Note that this documentation was tested on a Raspberry Pi 3, and it is possible that some USB dongles may need slight changes to their settings. If you are having trouble with a USB wireless dongle, please check the forums.

라즈베리3 장비에서 테스트를 한 문서이고 USB동글을 이용하게 된다면 조금 설정을 바꿔야 하는 부분이 필요하다.

만약 USB 무선동글을 이용하여 문제가 발생이되면, 동글의 상태를 체크하길 바란다.


To add a Raspberry Pi-based access point to an existing network, see this section.



추가적으로 라즈베리파이 기본 AP의 네트워크 설정에 관한 정보는 이 섹션을 참고하라???

(바로 내려가다보면 큰 글씨로 USING the raspberrypi ~~ 이 단락부분을 말한다.)

```
sudo apt-get update  
sudo apt-get upgrade
<<<<<<< HEAD

sudo apt-get install dnsmasq hostapd

=======
```
```
sudo apt-get install dnsmasq hostapd
```
```
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
sudo systemctl stop dnsmasq  
sudo systemctl stop hostapd
```

# Configuring a static IP - 고정 IP 설정.
We are configuring a standalone network to act as a server, so the Raspberry Pi needs to have a static IP address assigned to the wireless port. 

This documentation assumes that we are using the standard 192.168.x.x IP addresses for our wireless network, 

so we will assign the server the IP address 192.168.4.1. 

It is also assumed that the wireless device being used is wlan0.

To configure the static IP address, edit the dhcpcd configuration file with:

```
sudo nano /etc/dhcpcd.conf
```

Go to the end of the file and edit it so that it looks like the following:
```
interface wlan0
    static ip_address=192.168.4.1/24
```
Now restart the dhcpcd daemon and set up the new wlan0 configuration:
```
sudo systemctl restart dhcpcd
```
#Configuring the DHCP server (dnsmasq)
The DHCP service is provided by dnsmasq. 

By default, the configuration file contains a lot of information that is not needed, 

and it is easier to start from scratch. Rename this configuration file, and edit a new one:
<<<<<<< HEAD

```
sudo mv /etc/dnsmasq.conf /etc/dnsmasq.conf.orig 
 
sudo nano /etc/dnsmasq.conf
```

Type or copy the following information into the dnsmasq configuration file and save it:

=======
```
sudo mv /etc/dnsmasq.conf /etc/dnsmasq.conf.orig 
```
```
sudo nano /etc/dnsmasq.conf
```
Type or copy the following information into the dnsmasq configuration file and save it:
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
```
interface=wlan0      # Use the require wireless interface - usually wlan0
  
dhcp-range=192.168.4.2,192.168.4.20,255.255.255.0,24h
```

So for wlan0, we are going to provide IP addresses between ```192.168.4.2``` and ```192.168.4.20```, with a lease time of 24 hours. If you are providing DHCP services for other network devices (e.g. eth0), you could add more sections with the appropriate interface header, with the range of addresses you intend to provide to that interface.

There are many more options for dnsmasq; see the dnsmasq documentation for more details.

Configuring the access point host software (hostapd)
You need to edit the hostapd configuration file, located at /etc/hostapd/hostapd.conf, to add the various parameters for your wireless network. After initial install, this will be a new/empty file.

sudo nano /etc/hostapd/hostapd.conf
Add the information below to the configuration file. This configuration assumes we are using channel 7, with a network name of NameOfNetwork, and a password AardvarkBadgerHedgehog. Note that the name and password should not have quotes around them.
<<<<<<< HEAD

=======
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
```
interface=wlan0
driver=nl80211
ssid=NameOfNetwork
hw_mode=g
channel=7
wmm_enabled=0
macaddr_acl=0
auth_algs=1
ignore_broadcast_ssid=0
wpa=2
wpa_passphrase=AardvarkBadgerHedgehog
wpa_key_mgmt=WPA-PSK
wpa_pairwise=TKIP
rsn_pairwise=CCMP
```
<<<<<<< HEAD

We now need to tell the system where to find this configuration file.

=======
We now need to tell the system where to find this configuration file.
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
```
sudo nano /etc/default/hostapd
```

Find the line with #DAEMON_CONF, and replace it with this:

```
DAEMON_CONF="/etc/hostapd/hostapd.conf"
```
Start it up

Now start up the remaining services:
```
sudo systemctl start hostapd

<<<<<<< HEAD
```
sudo systemctl start hostapd

sudo systemctl start dnsmasq
```

ADD ROUTING AND MASQUERADE

Edit ```/etc/sysctl.conf``` and uncomment this line:
=======
sudo systemctl start dnsmasq
```
ADD ROUTING AND MASQUERADE

Edit /etc/sysctl.conf and uncomment this line:
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114

```
net.ipv4.ip_forward=1
```
Add a masquerade for outbound traffic on eth0:

```
sudo iptables -t nat -A  POSTROUTING -o eth0 -j MASQUERADE
```
<<<<<<< HEAD

Save the iptables rule.

```
sudo sh -c "iptables-save > /etc/iptables.ipv4.nat"
```

Edit /etc/rc.local and add this just above "exit 0" to install these rules on boot.

=======
Save the iptables rule.
```
sudo sh -c "iptables-save > /etc/iptables.ipv4.nat"
```
Edit /etc/rc.local and add this just above "exit 0" to install these rules on boot.
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
```
iptables-restore < /etc/iptables.ipv4.nat
```
```
Reboot
```
Using a wireless device, search for networks.

The network SSID you specified in the hostapd configuration should now be present, and it should be accessible with the specified password.

이 부분에서 Reboot 후 무선장치에 SSID가 검색이 된다고 나와 있지만 전혀 나타나지 않음!!!!

아무 반응도 없다..


그래서 StackOverFlow를 통하여 검색하면서 해결하여보았다.

일단 재부팅만 한다고 해서 바로 AP모드로 작동하는 것은 아닌 것 같다.
<<<<<<< HEAD

```
$sudo hostapd /etc/hostapd/hostapd.conf
```

=======
```
sudo hostapd /etc/hostapd/hostapd.conf
```
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
하면

AP모드가 작동하지 않고 ERROR가 발생하게 되었다.

에러는 Line2 : invalid/unknown driver 'nl80211'

이라고 하면서 앞에서 hostapd.conf에서 설정을 해준 'nl80211'이 뭐가 잘 안되는 것을 느꼈다.


https://askubuntu.com/questions/288437/how-fix-hostapd-invalid-unknown-driver-nl80211-error

이 링크에 들어가면 간단하게 해결이 가능했다!!

4개의 답글이 존재하는데 질문자 채택글에 보면 There are simple ~~~ 줄에 찾아가다보면

Hostapd building instruction. 이라고 링크에 들어갈 수 있다.

라이브러리가 필요하다고 하니까 이것도 설치를 해주자.

<<<<<<< HEAD

=======
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
```
sudo apt-get install libssl-dev
```


https://wireless.wiki.kernel.org/en/users/Documentation/hostapd

이 링크가 뜨게 되는데 hostapd에 대해 주구장창 설명을 해놓았다. 일단. nl80211 이라는 것을 아예 못찾는 것 같으니까 다운로드부터 받도록하자.


```
git clone git://w1.fi/srv/git/hostap.git
<<<<<<< HEAD

cd hostap/hostapd
```

Or you can get a stable release (0.6.8 or later recommended) by downloading the tarball from http://w1.fi/hostapd/ as follows:

http://w1.fi/hostapd/ 이 링크에 들어가면 2018.02.21. 기준으로 가장 최근버젼인 2.6 버젼을 다운로드 받도록 하자

```
wget http://w1.fi/releases/hostapd-x.y.z.tar.gz

tar xzvf hostapd-x.y.z.tar.gz

cd hostapd-x.y.z/hostapd
```

여기서 x.y.z.는 버젼이다. 나는 ~ hostapd-2.6.tar.gz 라고 해주었다.

상황에 따라 맞게 타이핑 쳐주자.

```
cp defconfig .config

nano .config
```

```.config ```파일로 들어가서 찾아준다.

```
/#CONFIG_DRIVER_NL80211=y
```

/#부분을 없앤다. 최근버젼인 2.6버젼에는 애초에 #이 없었다. /는 깃허브에 올릴려고 추가적으로 붙인 것이니 원래 없는 부분... 신경쓰지 말자!!

Next, compile hostapd:

```
make
```

=======
```
```
cd hostap/hostapd
```
Or you can get a stable release (0.6.8 or later recommended) by downloading the tarball from http://w1.fi/hostapd/ as follows:

http://w1.fi/hostapd/ 이 링크에 들어가면 2018.02.21. 기준으로 가장 최근버젼인 2.6 버젼을 다운로드 받도록 하자
```
wget http://w1.fi/releases/hostapd-x.y.z.tar.gz
tar xzvf hostapd-x.y.z.tar.gz
cd hostapd-x.y.z/hostapd
```
여기서 x.y.z.는 버젼이다. 나는 ~ hostapd-2.6.tar.gz 라고 해주었다.

상황에 따라 맞게 타이핑 쳐주자.
```
cp defconfig .config
```
```
nano .config
```
.config 파일로 들어가서 찾아준다.
```
/#CONFIG_DRIVER_NL80211=y
```
/#부분을 없앤다. 최근버젼인 2.6버젼에는 애초에 #이 없었다. /는 깃허브에 올릴려고 추가적으로 붙인 것이니 원래 없는 부분... 신경쓰지 말자!!

Next, compile hostapd:
```
make
```
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
컴파일을 해준다. 여기서 분명 안되는 것 처럼?? 나오는 걸로 알고있다. 

../src/drivers/driver_nl080211.c:17:31: fatal error~~ 에러가 뜨는데 딱히 신경 쓰지말자..

여기서 이제
```
<<<<<<< HEAD
$sudo apt-get hostapd hostapd/hostapd.conf 를 해보도록하자.
=======
sudo apt-get hostapd hostapd/hostapd.conf 를 해보도록하자.
>>>>>>> 7e79b9cc3d2438dd8684ac3242e86e253afc8114
```
```
$sudo /usr/sbin/hostapd /etc/hostapd/hostapd.conf 도 같은 거라고 생각하면 될 것 같다.
```

드디어!! 성공!!

그럼 이제 사설ip로 휴대폰이나 놋북에서 잘 받아지는지 확인을 해보자!!
