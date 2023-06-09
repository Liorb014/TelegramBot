package org.example;

public class Lead {
    private long chatId;
    private String name;
    private  int requestStatus;
    private String day;

    public static final int REQUEST_STATUS_UNKNOWN =0;
    public static final int REQUEST_STATUS_SUNDAY =1;
    public static final int REQUEST_STATUS_MONDAY=2;
    public static final int REQUEST_STATUS_TUESDAY=3;
    public static final int REQUEST_STATUS_WEDNESDAY =4;
    public static final int REQUEST_STATUS_THURSDAY =5;
    public static final int REQUEST_STATUS_FRIDAY=6;

    public Lead(long chatId) {
        this.chatId = chatId;
        this.requestStatus=REQUEST_STATUS_UNKNOWN;
    }
}
