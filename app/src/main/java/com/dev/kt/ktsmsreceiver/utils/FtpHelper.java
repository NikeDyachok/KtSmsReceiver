package com.dev.kt.ktsmsreceiver.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;


/**
 * Created by Андрей on 13.03.2017.
 */

public class FtpHelper {

    private static final String HOST = "ftp.kt.ua";
    private static final String LOGIN = "obmen";
    private static final String PASSWORD = "Ob20091941";
    private static final String REMOTE_PATH = "SMS/";

    public static void storeFile(final String file_name, final String file_local_path) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FTPClient con = new FTPClient();
                    con.connect(InetAddress.getByName(HOST));

                    if (con.login(LOGIN, PASSWORD)) {
                        con.enterLocalPassiveMode(); // important!
                        con.setFileType(FTP.BINARY_FILE_TYPE);

                        String data = file_local_path + file_name;

                        FileInputStream in = new FileInputStream(new File(data));

                        con.storeFile(REMOTE_PATH + file_name, in);
                        in.close();
                        con.logout();
                        con.disconnect();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
