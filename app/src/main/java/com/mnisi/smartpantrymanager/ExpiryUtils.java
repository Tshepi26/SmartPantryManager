package com.mnisi.smartpantrymanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//expiry statuses
public class ExpiryUtils {
    public static boolean isExpired(String expiryDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date expiry = sdf.parse(expiryDate);
            return new Date().after(expiry);
        } catch (Exception e) {
            return false;
        }
    }

    public static long daysUntilExpiry(String expiryDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date expiry = sdf.parse(expiryDate);
            long diff = expiry.getTime() - new Date().getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return -1;
        }
    }
}