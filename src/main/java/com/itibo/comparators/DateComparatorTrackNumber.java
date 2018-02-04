package com.itibo.comparators;

import java.util.Comparator;
import com.itibo.database.TrackInfoRecordsInfo;
import com.itibo.database.TrackingNumberInfo;

import java.util.Comparator;


public class DateComparatorTrackNumber implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        TrackingNumberInfo i1 = (TrackingNumberInfo)o1;
        TrackingNumberInfo i2 = (TrackingNumberInfo)o2;
        return -i1.getLastDateString().compareTo(i2.getLastDateString());
    }
}
