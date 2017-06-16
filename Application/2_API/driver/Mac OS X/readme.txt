Release Note, PL-2303 Mac OS X driver v1.5.1
For Mac OS X 10.6, 10.7, 10.8
Prolific Edition
==============================================

Supported device ID:
====================  
    VID:067B
    PID:2303

Kernel extension filename:
==========================  
    ProlificUsbSerial.kext

Device base name: 
===================  
    usbserial

Device descriptions: 
======================  
    USB-Serial Controller

Driver version:
====================  
    1.5.1

Revision Change:
====================  
    v1.5.1: Fixed GPS device will cause system freeze. 
    v1.5.0: Fixed Mac OS X 10.8 crash when unplug while open port.


Installer filename:
=====================  
    PL2303_MacOSX_v1.5.1_20160309.pkg

Uninstaller script:
=====================  
    Remove any previous installed driver first.

	1. Open Terminal program (located in /Applications/Utilities/)
	2. sudo rm -rf /System/Library/Extensions/ProlificUsbSerial.kext
	3. sudo rm -rf /var/db/receipts/*ProlificUsbSerial*.*


Installer title:
====================  
    Prolific USB to Serial Cable driver for Mac OS X

 
System Requirement:
====================  
. Mac OS X 10.6 Snow Leopard (32 and 64 bit kernel)
. Mac OS X 10.7 Lion (32 and 64 bit kernel)
. Mac OS X 10.8 Mountain Lion (64 bit kernel)
. USB host controller
. Device using any PL2303 chip versions (H, HX, HXD, EA, RA, SA, TA, TB)


Security Settings:
====================
Apple introduced new levels of security for dictating which software should be allowed to install. 

You may need to set the following:
1. Run "System Preferences" in Mac OS X and click "Security & Privacy" settings.
2. Set Allow apps downloaded from to "Anywhere" (as shown below).
3. Run PL2303 Mac driver installer package.


==================================================
Prolific Technology Inc. 
http://www.prolific.com.tw
 

