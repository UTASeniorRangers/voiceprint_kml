package com.varvet.barcodereadersample;

import java.util.ArrayList;

public class GetIndex {

    static ArrayList<String> items = new ArrayList<String>();


    public static void addItem(String item) {
        items.add(item);
    }

    public static void clearItems() {
        items.clear();
    }

    public static ArrayList<String> getItems() {
        return items;
    }

    public static int getIndex(String target) {
        for (int i = 0; i < items.size(); i++)
            if (items.get(i).toLowerCase().contains(target.toLowerCase()) || target.toLowerCase().contains(items.get(i).toLowerCase()))
                return i;
        return -1;
    }

    public static int getIndex(String target, ArrayList<String> source) {
        for (int i = 0; i < source.size(); i++)
            if (source.get(i).toLowerCase().contains(target.toLowerCase()) || target.toLowerCase().contains(source.get(i).toLowerCase()))
                return i;
        return -1;
    }

}