package com.example.mutithreading.beans.staticTypes;

import java.util.List;
public class Constants {

    public static final List<String> urls = List.of(
            "https://skillsoft.com/home",
            "https://skillsoft.com/partners",
            "https://skillsoft.com/about",
            "https://skillsoft.com/industries",
            "https://skillsoft.com/about/awards",
            "https://skillsoft.com/leadersip-teams"
    );

    public static final List<String> itemsForPC = List.of(
            "huseyin", "kalcik", "mehmet", "kaan", "mert", "taha", "serdar",
            "ahmet", "baykal", "selim", "cem", "ferhat", "baris", "duygu", "samet"
    );

    public static int myNum;

    public static final String WRITE = "write";
    public static final String READ = "read";
}