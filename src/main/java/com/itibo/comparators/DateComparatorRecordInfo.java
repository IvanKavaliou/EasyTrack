package com.itibo.comparators;

import com.itibo.database.TrackInfoRecordsInfo;

import java.util.Comparator;


public class DateComparatorRecordInfo implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        TrackInfoRecordsInfo i1 = (TrackInfoRecordsInfo)o1;
        TrackInfoRecordsInfo i2 = (TrackInfoRecordsInfo)o2;
        return -i1.getDate().compareTo(i2.getDate());
    }
}
