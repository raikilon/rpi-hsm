# ################################################################################
# NAME: rpihsm4.ps1
# 
# AUTHOR:  Sandro Tiago Carlao, I2b
# DATE:  05/04/2017
# 
# COMMENT:  Script to Login and send a file to the Raspberry Pi. There also is
#           the possibility of send values to perform actions with the sent file.
#
# ################################################################################

#------ Set file name and close the serial port if open
cls
$file_name = "img.jpg"
if($port.IsOpen) {$port.Close()}

#------ Create and open port
$port= new-Object System.IO.Ports.SerialPort COM3,115200,None,8,One
$port.Open()

#------ This passage is done at least once: the login password is asked, its 
#------ lenght and value are set in the $secure variable (so that in the other
#------ side the RPi knows how long is the sent password. Then this value
#------ is sent and when the response is "48" (0 in ASCII code) the user is
#------ authenticated. Else, a message is displayed and the mechanism is repeated
do{
    $input = Read-Host 'Authentication is needed! Password:' -AsSecureString
    $secure = ""+$input.Length+"/"
    $secure += [Runtime.InteropServices.Marshal]::
                    PtrToStringAuto([Runtime.InteropServices.Marshal]::
                        SecureStringToBSTR($input))
    $port.Write($secure)
    $authenticated = $port.ReadByte()
    if($authenticated -ne "48"){Write-Host -ForegroundColor Red "Wrong Password!"}
    else{Write-Host -ForegroundColor Green "Welcome!"}
}while($authenticated -ne "48")

#------ Add file name lenght and the file name to the $strem variable so that
#------ the RPi knows how to name the file (more because of its extention)
$stream = ""+$file_name.Length+$file_name

#------ Open file, read all bytes, compute HEX values
$file_bytes = [System.IO.File]::ReadAllBytes(".\Desktop\"+$file_name)
for($i=0; $i -lt $file_bytes.Length; $i++){
    $stream += "{0:x2}" -f $file_bytes[$i]
}

$stream += "!" #------ Final character of the file
$stream += "0" #------ Action to perform (actually 0 means enctyption)

#------ Write and then close connection
$port.Write($stream)
$port.Close()