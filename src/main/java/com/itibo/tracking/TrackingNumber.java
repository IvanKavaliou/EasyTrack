package com.itibo.tracking;


import com.itibo.trackers.BelPostTrack;
import com.itibo.trackers.SeventeenTrack;

import java.util.*;

public class TrackingNumber {
    private String trackingNumber;
    private List<Tracker> trackers;
    private Map<String, List<TrackInfoRecord>> info;
    private List<TrackInfoRecord> listInfo;

    public List<TrackInfoRecord> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<TrackInfoRecord> listInfo) {
        this.listInfo = listInfo;
    }

    public Map<String, List<TrackInfoRecord>> getInfo() {
        return info;
    }

    public TrackingNumber(String tn) {
        setTrackingNumber(tn);
        trackers = new LinkedList<Tracker>();
        info = new HashMap<String, List<TrackInfoRecord>>();
        listInfo = new LinkedList<TrackInfoRecord>();
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void add(Tracker tracker){
        trackers.add(tracker);
    }

    public LinkedList<TrackInfoRecord> update2() throws InterruptedException{
        LinkedList<TrackInfoRecord> all = new LinkedList<TrackInfoRecord>();
        ThreadGroup group = new ThreadGroup("group");
        for(Tracker record: trackers){
            new Thread(group, record).start();
        }
        MainThread mt = new MainThread(group);
        mt.st();
        return all;
    }

/*    public LinkedList<TrackInfoRecord> update(){
        LinkedList<TrackInfoRecord> all = new LinkedList<TrackInfoRecord>();
*//*        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Future<String>> f1 = null;
        Future<String> f2 = null;*//*
     //   try{
        for(Tracker record: trackers){
            record.start();
*//*            f2 = (Future) service.submit(record);
            System.out.println(f2);
            f1.add(f2);*//*
        }
*//*            for (Future<String> rec: f1) {
               rec.get(1, TimeUnit.SECONDS);
            }*//*
      //  }catch (Exception e){}
*//*            for (Future<String> rec: f1){
            if(!rec.isDone()) {
                System.out.println("don't complite "+rec);
            }
        }*//*
           // service.shutdown();
        return all;
    }*/

    public void saveTrackingInfo(String trackerName, List<TrackInfoRecord> res) {
        info.put(trackerName, res);
        listInfo.addAll(res);
    }
                                                //PK14110806311
                                                //VV090074097BY      RD503242720CN
    public static void main(String[] args) throws InterruptedException{
        TrackingNumber tn = new TrackingNumber("RD503242720CN");
        Tracker bp = new BelPostTrack(tn);
        Tracker st = new SeventeenTrack(tn);

        tn.add(bp);
        tn.add(st);

        tn.update2();

        System.out.println("Results:");
        for(String key: tn.info.keySet()){
            for(TrackInfoRecord record: tn.info.get(key)){
                System.out.println(record.toString());
            }
        }
    }
}