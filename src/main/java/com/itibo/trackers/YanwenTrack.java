package com.itibo.trackers;

import com.itibo.comparators.DateComparator;
import com.itibo.tracking.TrackInfoRecord;
import com.itibo.tracking.Tracker;
import com.itibo.tracking.TrackingNumber;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class YanwenTrack extends Tracker {

    public YanwenTrack(TrackingNumber trackNumber){
        this.name = "YanwenTrack";
        this.trackNumber = trackNumber;
         url = "http://www.17track.net/r/handlertrack.ashx?callback=jQuery111009931895746849477_1426589630161&num="+trackNumber.getTrackingNumber()+"&et=190012&_=1426589630162";
    }

/*    public String getDataString() {
        return filterSeventeenTrack(Downloader.getHTML(url));
    }*/

    protected LinkedList<TrackInfoRecord> parseTrackInfoRecords(String text) {
        TrackInfoRecord tir = null;
        LinkedList <TrackInfoRecord> all = new LinkedList<TrackInfoRecord>();

        Pattern pattern1 = Pattern.compile("\"z1\":\\[(.*?)\\]", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(text);
        try {
            matcher1.find();

            String re = "\"a\":(\".*?\"),\"b\":null,\"c\":(\".*?\"),\"d\":\"\",\"z\":(\".*?\")";
            Pattern p = Pattern.compile(re, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = p.matcher(matcher1.group(1));
            while (m.find()) {
                tir = new TrackInfoRecord(this);
                tir.fillDate(m.group(1).replace('"', ' ').trim(), Tracker.COMMON_DATE_FORMAT);
                tir.setDateString(m.group(1).replace('"', ' ').trim());
                tir.setMessage(m.group(3).replace('"', ' ').trim());
                tir.messageAdd(m.group(2).replace('"', ' ').trim());
                all.add(tir);
            }
            Collections.sort(all, new DateComparator());
        } catch (Exception e){}
        return all;
    }

    private LinkedList<TrackInfoRecord> parseAddressee(String text) {
        TrackInfoRecord tir = null;
        LinkedList <TrackInfoRecord> all = new LinkedList<TrackInfoRecord>();

        Pattern pattern1 = Pattern.compile("\"z2\":\\[(.*?)\\]", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(text);
        matcher1.find();

        String re = "\"a\":(\".*?\"),\"b\":null,\"c\":(\".*?\"),\"d\":\"\",\"z\":(\".*?\")";
        Pattern p = Pattern.compile(re,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(matcher1.group(1));
        while(m.find()) {
            tir = new TrackInfoRecord(this);
            tir.fillDate(m.group(1).replace('"', ' ').trim(), Tracker.COMMON_DATE_FORMAT);
            tir.setDateString(m.group(1).replace('"', ' ').trim());
            tir.setMessage(m.group(3).replace('"', ' ').trim());
            tir.messageAdd(m.group(2).replace('"', ' ').trim());
            all.add(tir);
        }
        Collections.sort(all, new DateComparator());
        return all;
    }


    //RS203649271NL
    public static void main(String[] args) {
        YanwenTrack yanwenTrack = new YanwenTrack(new TrackingNumber("PK14110806311"));
        List<TrackInfoRecord> info = yanwenTrack.process();
        for (TrackInfoRecord record : info) {
            System.out.println(record);
        }

        System.out.println("=========================");

        Collections.sort(info, new DateComparator());

        for (TrackInfoRecord record : info) {
            System.out.println(record);
        }
    }
}
