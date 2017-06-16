# ################################################################################
# NAME: listener2.ps1
#
# AUTHOR:  Sandro Tiago Carlao, I2b
# DATE:  05/04/2017
#
# COMMENT:  Script that runs on RPi as a demon, performs linux login as user pi
#           and writes the received bytes to a file. Simulates an encryption command
#
# ################################################################################
import serial,sys,binascii,pam

#------ Functions definition
def encrypt():
    print "file encrypted!\n"

actions = {
    0 : encrypt,
    1: decrypt,
    2: sign,
    3: hash,
}

#------ create port and open
ser = serial.Serial("/dev/ttyS0", 115200)

#------ Cycle to allow to perform more actions without need to restart the script
while True:
    # ------ Authentication until good password
    while True:
        ser.flushInput()
        secureLen = ""
        #------ Read password lenght
        while True:
            x = ser.read()
            if x == '/':
                break
            else:
                if x != '':
                    secureLen += x

        #------ Get password
        secure = ser.read(int(secureLen))
        p = pam.pam()
        #------ Try to authenticate
        authenticate = p.authenticate('pi', secure, service='login')
        print authenticate
        #------ Give authentication response to client
        if authenticate:
            ser.write("0")
            break
        else:
            ser.write("1")

    # ------ Get filename and open a file with the same name
    fileNameSize = ser.read()
    fileName = ser.read(int(fileNameSize))
    f = open(fileName,"wb+")
    str = ""

    #------ Read all bytes of the file
    while True:
        x = ser.read()
        if x == '!':
            break
        else:
            if x != '':
                str += x

    #------ Write the data to the new file on RPi
    f.write(binascii.unhexlify(str))

    #------ Get action to perform (simulates an encryption with 0 as parameter)
    action = ser.read()
    actions[int(action)]()
    print len(str)