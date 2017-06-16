#------ Clean console, close ports if open and reset variables
cls
$console_msg = ""
$file_name = "file.txt"
if($port.IsOpen) {$port.Close()}

#------ Create and open port
$port=new-Object System.IO.Ports.SerialPort COM3,115200,None,8,one
$port.Open()
Start-Sleep -Milliseconds 500
$port.WriteLine("")
Start-Sleep -Milliseconds 500

#------ Check if user is logged
$console_msg = $port.ReadExisting().ToString();
if(!$console_msg.Contains("login")){
    $port.WriteLine("exit")
    Start-Sleep -Seconds 3
}

#------ Login as pi and ask password to user
do{
    $port.WriteLine("pi")
    Start-Sleep -Milliseconds 500
    $console_msg = $port.ReadExisting().ToString();
    $pass = Read-Host 'What is your password?' -AsSecureString
    $port.WriteLine([Runtime.InteropServices.Marshal]::
        PtrToStringAuto([Runtime.InteropServices.Marshal]::
            SecureStringToBSTR($pass)))
    Start-Sleep -Seconds 4
    $console_msg = $port.ReadExisting().ToString()
    if($console_msg.Contains("incorrect")){
        Write-Host -ForegroundColor Red "Wrong Password!"
    }
}
while($console_msg.Contains("incorrect"))


$file_bytes = [System.IO.File]::ReadAllBytes(".\Desktop\"+$file_name)
$file_size = (Get-Item '.\Desktop\file.txt').Length
for($i=0; $i -lt $file_bytes.Length; $i++){
    $stream += "\x"+[Convert]::ToString($file_bytes[$i], 16)
}

$modulo = [int][Math]::Ceiling($file_size/$bytes_block)

if($modulo -eq 1){
    $port.WriteLine("echo -n -e '"+$stream+"' >> "+$file_name)
}else{
    for($i=0;$i -lt $modulo-1; $i++){
        $port.WriteLine("echo -n -e '"+$stream.Substring(
            $i*$bytes_block,(($i+1)*$bytes_block)-1)+"' >> "+$file_name)
    }
    $port.WriteLine("echo -n -e '"+$stream.Substring(
            ($modulo-1)*$bytes_block,$file_size-1)+"' >> "+$file_name)
}
$port.ReadExisting()

$port.WriteLine("exit")
$port.Close()