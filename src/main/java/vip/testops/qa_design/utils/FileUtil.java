package vip.testops.qa_design.utils;

import com.intellij.psi.PsiDirectory;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class FileUtil {

    public static String getNonRepetitiveFilename(String filename, @NotNull File basePath, @NotNull String suffix) {
        String filenameWithoutSuffix = getFilenameWithoutSuffix(filename, suffix);
        String filenameWithSuffix = filename.endsWith(suffix) ? filename : filename + (suffix.equals("") ? "" : ".") + suffix;
        if(filenameWithoutSuffix.endsWith(".")) {
            filenameWithoutSuffix = filenameWithoutSuffix.substring(0, filenameWithoutSuffix.length()-1);
        }
        if(!basePath.isDirectory()) {
            return filenameWithSuffix;
        }
        File[] files = basePath.listFiles(File::isFile);
        if(files == null) {
            return filenameWithSuffix;
        }
        if(!isExist(files, filenameWithSuffix)) {
            return filenameWithSuffix;
        }
        int index = 1;
        filenameWithoutSuffix = filenameWithoutSuffix + "_" + index;
        filenameWithSuffix = filenameWithoutSuffix +  (suffix.equals("") ? "" : ".") + suffix;
        while(isExist(files, filenameWithSuffix)) {
            index++;
            filenameWithoutSuffix = filenameWithoutSuffix.substring(0, filenameWithoutSuffix.lastIndexOf("_")) + "_" + index;
            filenameWithSuffix = filenameWithoutSuffix +  (suffix.equals("") ? "" : ".") + suffix;

        }
        return filenameWithSuffix;
    }

    public static String getNonRepetitiveFilename(String filename, @NotNull PsiDirectory directory, @NotNull String suffix) {
        String filenameWithoutSuffix = getFilenameWithoutSuffix(filename, suffix);
        String filenameWithSuffix = filename.endsWith(suffix) ? filename : filename + (suffix.equals("") ? "" : ".") + suffix;
        if(filenameWithoutSuffix.endsWith(".")) {
            filenameWithoutSuffix = filenameWithoutSuffix.substring(0, filenameWithoutSuffix.length()-1);
        }
        if(directory.findFile(filenameWithSuffix) == null) {
            return filenameWithSuffix;
        }
        int index = 1;
        filenameWithoutSuffix = filenameWithoutSuffix + "_" + index;
        filenameWithSuffix = filenameWithoutSuffix +  (suffix.equals("") ? "" : ".") + suffix;
        while(directory.findFile(filenameWithSuffix) != null) {
            index++;
            filenameWithoutSuffix = filenameWithoutSuffix.substring(0, filenameWithoutSuffix.lastIndexOf("_")) + "_" + index;
            filenameWithSuffix = filenameWithoutSuffix +  (suffix.equals("") ? "" : ".") + suffix;

        }
        return filenameWithSuffix;
    }

    private static String getFilenameWithoutSuffix(String filename, @NotNull String suffix) {
        return filename.endsWith(suffix) ? filename.substring(0, filename.length() - suffix.length()) : filename;
    }

    private static boolean isExist(File[] files, String filename) {
        for(File file : files) {
            if(file.getName().equals(filename)) {
                return true;
            }
        }
        return false;
    }
}
