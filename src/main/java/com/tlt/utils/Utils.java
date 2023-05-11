package com.tlt.utils;

import static com.tlt.constants.PathConstants.PROFILE_IMG_UPLOAD;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import org.primefaces.model.file.UploadedFile;

public class Utils {

    public static String FileName;
    public static String FilePath;

    public static final String IMAGE = "image";
    public static final String PDF = "pdf";
    public static final String DOC = "docs";
    public static final String EXCEL = "excel";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
    }

    public static boolean uploadFile_PF(UploadedFile file, String fileType) {
        if (file == null) {
            return false;
        }
        switch (fileType) {
            case IMAGE:
                if (!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/jpg")) {
                    return false;
                } break;
        }
        try ( InputStream input = file.getInputStream()) {

            String directory = PROFILE_IMG_UPLOAD;
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
}
