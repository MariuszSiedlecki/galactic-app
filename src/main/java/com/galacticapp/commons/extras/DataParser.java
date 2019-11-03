package com.galacticapp.commons.extras;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataParser {
    public static String millsToData(long mills) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date resultDate = new Date(mills);
        return sdf.format(resultDate);
    }
}
