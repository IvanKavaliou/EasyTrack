package com.itibo.comparators;

import com.itibo.tracking.TrackInfoRecord;

import java.util.Comparator;


public class DateComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        TrackInfoRecord i1 = (TrackInfoRecord)o1;
        TrackInfoRecord i2 = (TrackInfoRecord)o2;
        return i1.getDate().compareTo(i2.getDate());
    }
}
