package com.blitzstriker.blogapi.utils;

import java.util.Date;

public class DemoUtil {
    public static void main(String[] args) {
        Date currentDate = new Date();
        Date expDate = new Date(currentDate.getTime() + 604800000);
        System.out.println(expDate);
    }
}
