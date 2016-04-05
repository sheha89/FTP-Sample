package com.learn.agent;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import static com.learn.util.ConfigUtil.getApplicationConfig;


public class FTPAgent {

    public static void main(String[] args) {

        // Create Text Files
        File textFile1 = new File(getApplicationConfig("text.file.1").get());
        File textFile2 = new File(getApplicationConfig("text.file.2").get());

        List<File> textFileList = new ArrayList<File>();
        textFileList.add(textFile1);
        textFileList.add(textFile2);

        // Create Binary Files
        File binaryFile1 = new File(getApplicationConfig("binary.file.1").get());
        File binaryFile2 = new File(getApplicationConfig("binary.file.2").get());

        List<File> binaryFileList = new ArrayList<File>();
        binaryFileList.add(binaryFile1);
        binaryFileList.add(binaryFile2);

        // FTP Text Files.
        System.out.println("FTP Text Files");
        boolean isFTPTextFilesSuccess = ftpFiles(false, textFileList);
        System.out.println("FTP Text Files status >> " + isFTPTextFilesSuccess);

        // FTP Binary Files.
        System.out.println("FTP Binary Files");
        boolean isFTPBinaryFilesSuccess = ftpFiles(true, binaryFileList);
        System.out.println("FTP Binary Files Status >> " + isFTPBinaryFilesSuccess);

    }

    public static boolean ftpFiles(boolean isBinary, List<File> fileList) {
        System.out.println("Process upload files " + fileList);

        FTPClient ftpClient = new FTPClient();
        FileInputStream fileInputStream = null;

        try {
            if (fileList.size() > 0) {
                System.out.println("Connecting FTP  Hostname [" + getApplicationConfig("host").get() + "]  " +
                        "Username [" + getApplicationConfig("username").get() + "]  Password [" + getApplicationConfig("password").get() + "]");
                ftpClient.connect(getApplicationConfig("host").get(), Integer.parseInt(getApplicationConfig("port").get()));
                boolean isLoggedIn = ftpClient.login(getApplicationConfig("username").get(), getApplicationConfig("password").get());

                if (isBinary) {
                    System.out.println("Setting FTP File Transfer mode - BINARY_FILE_TYPE");
                    try {
                        ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    } catch (IOException e) {
                        System.out.println("Error occurred while setting file transfer mode to : BINARY_FILE_TYPE [{}] " + e);
                    }
                }
                System.out.println("FTP Logged in status : " + isLoggedIn);

                if (isLoggedIn) {
                    for (File fileEntry : fileList) {
                        System.out.println("Uploading file [" + fileEntry.getName() + "]");
                        fileInputStream = new FileInputStream(fileEntry);

                        boolean isSuccessfullyUploaded = ftpClient.storeFile(getApplicationConfig("upload.path").get() + fileEntry.getName(), fileInputStream);
                        if (isSuccessfullyUploaded) {
                            System.out.println("File uploaded successfully to [" + getApplicationConfig("upload.path").get() + "]");
                        } else {
                            System.out.println("File uploaded failed for FTP location [" + getApplicationConfig("upload.path").get() + "]");
                            return false;
                        }
                    }
                } else {
                    System.out.println("Login Failed for FTP Location.");
                    return false;
                }
                ftpClient.logout();
            } else {
                System.out.println("Received empty filtered file list. Skip file uploading.");
            }
        } catch (ConnectException ex) {
            System.out.println("Error Occurred Due to " + ex);
            return false;

        } catch (Exception ex) {
            System.out.println("Error Occurred Due to " + ex);
            return false;

        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                ftpClient.disconnect();
            } catch (IOException ex) {
                System.out.println("Error Occurred Due to" + ex);
            }
        }
        return true;
    }

}
