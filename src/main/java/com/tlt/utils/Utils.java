package com.tlt.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;
import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;
import org.primefaces.model.file.UploadedFile;

public class Utils {

    public static Pbkdf2PasswordHashImpl passHash;
    public static String FileName;
    public static String FilePath;

    public static final String IMAGE = "image";
    public static final String PDF = "pdf";
    public static final String DOC = "docs";
    public static final String EXCEL = "excel";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
    }

    public static boolean uploadFile_PF(UploadedFile file, String fileType, String destination) {
        if (file == null) {
            return false;
        }
        switch (fileType) {
            case IMAGE:
                if (!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/jpg")) {
                    return false;
                }
                break;
        }
        try ( InputStream input = file.getInputStream()) {

            String directory = destination;
            FileName = System.currentTimeMillis() + "_" + file.getFileName();
            FilePath = directory + FileName;

            try ( OutputStream output = new FileOutputStream(FilePath)) {
                byte[] buffer = new byte[2048];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void resetFilesCache() {
        FileName = null;
        FilePath = null;
    }

    public static String generateHash(String password) {
        passHash = new Pbkdf2PasswordHashImpl();
        return passHash.generate(password.toCharArray());
    }

    public static String generateOtp(Integer length) {
        String numbers = "0123456789";
        char[] otp = new char[length];
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(rand.nextInt(numbers.length()));
        }
        return new String(otp);
    }
}
