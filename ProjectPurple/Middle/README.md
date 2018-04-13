 # RaspberryPi3 B model AP mode ( update 2018.4.14 )
 
 AP : Wireless Access Point
 
 ref http://www.raspberrypi.org/documentation/configuration/wireless/access-point.md
 
 What is dnsmasq & hostapd??
 
 - dnsmasq
  Under 1000 client local network use simply DHCP/DNS server.
  
 - hostapd
  wireless network interface exchange AP mode.
  
    sudo apt-get update 
    sudo apt-get upgrade
    
    sudo apt-get install dnsmasq hostapd
    
   #Configuring a static IP
    
    using the standard 192.168.x.x IP address for our wireless network.
    server IP address 192.168.4.1. wireless device being used is wlan0.
    
    sudo nano /etc/dhcpcd.conf
    
    
    
