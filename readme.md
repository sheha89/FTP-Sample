# FTP-Sample

Sample FTP application to transfer binary and text file via FTP avoiding file corruption when transfer.

## Instroduction

Basically File transfering over FTP handle Binary and ASCII.

### ASCII [American Standard Code for Information Interchange] File types

.txt .html .shtml .php .pl .cgi .js .css .map .pwd .txt .grp .ctl

### Binary File Types

.jpg .gif .png .tif .exe .zip .sit .rar .class .mid .wav .mp3, .doc, .xls


## Problem

When tranfering over FTP binary file in ASCII mode will corrupt the binary file’s structure and
it will convert and encode some characters.
Most of the FTP clients only support enabling manual specification of the mode rather auto detecting the file transfer
mode based on the type of the files.

## Solution

Sending both ASCII and binary files in binary mode

## How to run

To Simulate FTP file tranferring start the Client and Server following the instruction given in particular modules

### Reference
https://mina.apache.org/ftpserver-project/