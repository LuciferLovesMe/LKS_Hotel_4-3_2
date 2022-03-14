package com.example.abim.lks_hotel4_mobile_3_2;

public class MyRequest {
    private static final String baseURL = "http://192.168.22.183:668/";
    private static final String loginURL = "api/employee";
    private static final String fdURL = "api/fd";
    private static final String roomURL = "api/room";
    private static final String checkoutURL = "api/fdcheckout";

    public static String getBaseURL() {
        return baseURL;
    }

    public static String getLoginURL() {
        return getBaseURL() + loginURL;
    }

    public static String getFdURL() {
        return getBaseURL() + fdURL;
    }

    public static String getRoomURL() {
        return getBaseURL() + roomURL;
    }

    public static String getCheckoutURL() {
        return getBaseURL() + checkoutURL;
    }
}
