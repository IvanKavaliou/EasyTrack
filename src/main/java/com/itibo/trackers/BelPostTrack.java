package com.itibo.trackers;

import com.itibo.comparators.DateComparator;
import com.itibo.tracking.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BelPostTrack extends Tracker {

    private static final String EXT_DATE_FORMAT = "yyyy-MM-dd kk:mm:ss";
    private static final String INT_DATE_FORMAT = "dd.MM.yyyy kk:mm";

    public BelPostTrack(TrackingNumber trackNumber){
        this.name = "BelPostTrack";
        this.trackNumber = trackNumber;
        url = "http://search.belpost.by/ajax/search!?item="+trackNumber.getTrackingNumber()+"&internal=2";
    }

    private LinkedList<TrackInfoRecord> parseInternalDate(String text) {
        LinkedList<TrackInfoRecord> all = new LinkedList<TrackInfoRecord>();
        TrackInfoRecord tir = null;
        text.trim();
        String re = "<td>(.*?)</td>\n<td>(.*?)</td>";
        Pattern p = Pattern.compile(re,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(text);

        while (m.find()){
            tir = new TrackInfoRecord(this);
            tir.fillDate(m.group(1), INT_DATE_FORMAT);
            tir.setMessage(Filters.filterHTMLtags(m.group(2)));
            all.add(tir);
        }
        return all;
    }

    private LinkedList<TrackInfoRecord> parseExternalDate(String text) {
        LinkedList<TrackInfoRecord> all = new LinkedList<TrackInfoRecord>();
        TrackInfoRecord tir = null;
//        tir.setMessage(""); //++
//        all.add(tir);//++
        String re = "<td>(.*?)</td>\n<td>(.*?)</td>\n<td>(.*?)</td>";
        Pattern p = Pattern.compile(re,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(text);

        while (m.find()){
            tir = new TrackInfoRecord(this);
            tir.fillDate(m.group(1), EXT_DATE_FORMAT);
            tir.setMessage(Filters.filterHTMLtags(m.group(2)));
            tir.messageAdd(Filters.filterHTMLtags(m.group(3)));
            all.add(tir);
        }

        return all;
    }

    private LinkedList<TrackInfoRecord> parseExternal(String text){
        LinkedList<TrackInfoRecord> res = new LinkedList<TrackInfoRecord>();
        String reg = "<table(.*?)/table>";
        Pattern pa = Pattern.compile(reg, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher ma = pa.matcher(text);
        if(ma.find()){
            res.addAll(parseExternalDate(ma.group(1)));
        }
        return res;
    }

    protected LinkedList<TrackInfoRecord> parseTrackInfoRecords(String text){
        LinkedList<TrackInfoRecord> all = new LinkedList<TrackInfoRecord>();
        String externalText = "";
        String internalText = "";

        String re = "(.*)(Прохождение внутри страны)(.*)";
        Pattern p = Pattern.compile(re, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(text);
        if(m.find()){
            String reg = "<table(.*?)/table>";
            Pattern pa = Pattern.compile(reg, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

                all.addAll(parseExternal(m.group(1)));

        Matcher ma = pa.matcher(m.group(3));
            if(ma.find()){
                internalText = ma.group(1);
                all.addAll(parseInternalDate(internalText));
            }
        } else {
            all.addAll(parseExternal(text));
        }

        return all;
    }

    protected LinkedList<TrackInfoRecord> parseTrackInfoRecords2(String text){
        LinkedList<TrackInfoRecord> all = new LinkedList<TrackInfoRecord>();
            String externalText = "";
            String internalText = "";


            String re = "<table(.*?)/table>";
            Pattern p = Pattern.compile(re, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = p.matcher(text);
          //  try{
                if(m.find()) {
                    externalText = m.group(1);
                    all.addAll(parseExternalDate(externalText));
                    if(m.find()) {
                        internalText = m.group(1);
                        all.addAll(parseInternalDate(internalText));
                    }
                }
                Collections.sort(all, new DateComparator());
           // } catch (Exception e) {}
        return all;
    }

    public static void main(String[] args) {
        BelPostTrack belPostTrack = new BelPostTrack(new TrackingNumber("RD503242720CN"));
        List<TrackInfoRecord> info = belPostTrack.process();
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
