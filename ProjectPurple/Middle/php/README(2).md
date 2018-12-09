#기본 공유기 접속 루트
```
203.250.35.169/login.php
```

# RaspberryPi 3  B Model AP Mode

reference :
https://www.raspberrypi.org/documentation/configuration/wireless/access-point.md

dnsmasq와 hostapd

```dnsmasq``` : 1000클라이언트 이하의 로컬 네트워크에서 활용할 수 있는 간단한 DHCP/DNS 서버.


```DHCP```는 컴퓨터 사고 랜선 꼽으면 그냥 아무 설정 안했는데 자동으로 그냥 인터넷 연결도 되고 편하게 사용할 수 있다.
그렇게 해주는 설정이 바로 DHCP설정이라고 한다.



DHCP를 통한 IP주소 할당은 흔히 '임대'라는 개념을 가지고 있어서 IP주소를 임대기간을 명시하여 그 기간 동안에만 단말이
IP주소를 사용할수 있다고 하고 더 사용하고자 하면 기간연장을 서버에 요청하고 필요없으면 반납절차를 수행하게 된다고 한다.

```DNS```는 흔히 naver.com이나 google.co.kr 같은 도메인 네임을 뜻한다.



```
hostapd is a user space daemon for wireless access point and authentication servers. There are three implementations: Jouni Malinen's hostapd, OpenBSD's hostapd and Devicescape's hostapd.


Contents  [hide] 
```

AP와 인증 서버를 위한 사용자 공간 데몬이라고 한다. 데몬은 흔히 백그라운도 프로세스라고 불린다.

그냥 무선 네트워크 인터페이스는 AP모드로 전환하여 서비스를 하게 해주는 패키지라고 생각하면 될 것 같다.

라즈베리파이는 독립형 와이파이 ap로 사용할 수 있다. 

라즈베리파이 3, 제로W 에 무선요소가 내장되었거나 AP를 지원하는 USB 동글이 탑제된 라즈베리도 가능하다.

라즈베리3 장비에서 테스트를 한 문서이고 USB동글을 이용하게 된다면 조금 설정을 바꿔야 하는 부분이 필요하다.

만약 USB 무선동글을 이용하여 문제가 발생이되면, 동글의 상태를 체크하길 바란다.

추가적으로 라즈베리파이 기본 AP의 네트워크 설정에 관한 정보는 이 섹션을 참고한다.

```
sudo apt-get update  
sudo apt-get upgrade

sudo apt-get install dnsmasq hostapd
```

```
sudo systemctl stop dnsmasq  
sudo systemctl stop hostapd
```

# Configuring a static IP - 고정 IP 설정.

라즈베리파이는 무선포트 연결을 위해 고정 아이피 필요하다.

192.168.x.x의 주소를 무선 네트워크 주소를 사용한다.

라즈베리파이 주소는 192.168.4.1이고 wlan0이라는 이름을 사용한다.

고정 IP주소를 사용하고 dhcp 설정 파일을 편집을 한다.

```
sudo nano /etc/dhcpcd.conf
```

다음으로 파일의 마지막 줄에 아래의 글을 추가로 적고 저장 후 종료한다.

```
interface wlan0
    static ip_address=192.168.4.1/24
```

그리고 dhcp 데몬을 재시작하고 새로운 wlan0설정이 되었는지 확인한다.

```
sudo systemctl restart dhcpcd
```
#Configuring the DHCP server (dnsmasq)
 
DHCP 서비스는 dnsmasq로부터 제공된다.

결과적으로 설정파일은 많은 정보가 필요하지는 않고 쉬운 편이다.

설정 파일을 이름을 재설정 하고, 새로운 것을 하나 만든다.

```
sudo mv /etc/dnsmasq.conf /etc/dnsmasq.conf.orig 
```
```
sudo nano /etc/dnsmasq.conf
```

Type or copy the following information into the dnsmasq configuration file and save it:
dnsmasq 설정파일에서 다음과 같은 타입과 정보들을 저장한다.

```
interface=wlan0      # Use the require wireless interface - usually wlan0
  
dhcp-range=192.168.4.2,192.168.4.20,255.255.255.0,24h
```

wlan0을 따라, 192.168.4.2에서 192.168.4.20 사이의 IP주소들을 제공할 것이고 24시간동안 제공할 것이다.
만약 DHCP 서비스를 다른 네트워크 장비들에게 제공하고 싶다면, 섹션을 추가하고 주소의 범위와 함께 인터페이스 설정을 만들어주면 된다.
dnsmasq의 많은 옵션이 있고, 더 자세한 사항은 dnsmasq 문서를 참고하면 된다.

hostapd의 설정을 한다.
hostapd 설정 파일을 편집하는 것이 필요한데, 해당 파일의 위치는 /etc/hostad/hostapd.conf에 존재하며 무선 네트워크 파라미터들을 추가한다.
인스톨 후 새로운 빈 파일을 생성한다.

```
sudo nano /etc/hostapd/hostapd.conf
```

설정 파일의 정보를 추가한다.
이 설정은 7 채널을 사용하였으며 네트워크 이름과 패스워드는 임의로 설정해주면 된다.
이름과 패스워드를 노트해둔다.

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

우리는 설정파일을 찾아 설정해주고 시스템에 알려줄 필요가 있다.

```
sudo nano /etc/default/hostapd
```

Find the line with #DAEMON_CONF, and replace it with this:

```
DAEMON_CONF="/etc/hostapd/hostapd.conf"
```

서비스를 재시작 해준다.

```
sudo systemctl start hostapd

sudo systemctl start dnsmasq
```

추가적으로 라우팅과 마스크레이드 설정을 한다.

Edit ```/etc/sysctl.conf``` and uncomment this line:

```
net.ipv4.ip_forward=1
```

Add a masquerade for outbound traffic on eth0:

```
sudo iptables -t nat -A  POSTROUTING -o eth0 -j MASQUERADE
```

Save the iptables rule.

```
sudo sh -c "iptables-save > /etc/iptables.ipv4.nat"
```

Edit ```/etc/rc.local ```and add this just above "exit 0" to install these rules on boot.

```
iptables-restore < /etc/iptables.ipv4.nat
```

```
Reboot
```

이 부분에서 Reboot 후 무선장치에 SSID가 검색이 된다고 나와 있지만 전혀 나타나지 않았다.

```
sudo hostapd /etc/hostapd/hostapd.conf
```

```
sudo hostapd /etc/hostapd/hostapd.conf
```
하면 AP모드가 작동하지 않고 ERROR가 발생하게 되었다.

에러는 ```Line2 : invalid/unknown driver 'nl80211'```

라이브러리가 필요하다고 하니까 이것도 설치를 한다.


```
sudo apt-get install libssl-dev
```

nl80211 이라는 것을 아예 못찾는 것 같으니까 다운로드부터 받도록 한다.

```
git clone git://w1.fi/srv/git/hostap.git

cd hostap/hostapd
```

Or you can get a stable release (0.6.8 or later recommended)  
  by downloading the tarball from http://w1.fi/hostapd/ as follows:

http://w1.fi/hostapd/ 이 링크에 들어가면 2018.02.21. 기준으로  
가장 최근버젼인 2.6 버젼을 다운로드 받도록 한다.

```
wget http://w1.fi/releases/hostapd-x.y.z.tar.gz
tar xzvf hostapd-x.y.z.tar.gz
cd hostapd-x.y.z/hostapd
```

여기서 x.y.z.는 버젼이다. 나는 ~ hostapd-2.6.tar.gz 라고 해주었다. 상황에 따라 맞게 타이핑 쳐주자.

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

/#부분을 없앤다. 최근버젼인 2.6버젼에는 애초에 #이 없었다.  
/는 깃허브에 올릴려고 추가적으로 붙인 것이니 원래 없는 부분... 신경쓰지 말자!!

Next, compile hostapd:

```
make
```

컴파일을 해준다. 여기서 분명 안되는 것 처럼?? 나오는 걸로 알고있다. 

```../src/drivers/driver_nl080211.c:17:31: fatal error~~ ```에러가 뜨는데 딱히 신경 쓰지말자..

여기서 이제

```
sudo apt-get hostapd hostapd/hostapd.conf
```

```
sudo /usr/sbin/hostapd /etc/hostapd/hostapd.conf
```
완료되었다.

무선장치를 통하여 해당 SSID가 잘 검색이 되면서 작동하는지 확인한다.