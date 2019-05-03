package com.varvet.barcodereadersample;

public class printData {

    private static String fileName;
    private static float printTime;

    public static String getFileName() {
        return fileName;
    }

    public static float getPrintTime() {
        return printTime;
    }


    public static void setFileName(String fileName) {
        printData.fileName = fileName;
    }

    public static void setPrintTime(float printTime) {
        printData.printTime = printTime;
    }


}
