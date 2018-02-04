package com.itibo.tracking;



import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public abstract class Tracker extends Thread {

    public static String COMMON_DATE_FORMAT = "yyyy-MM-dd kk:mm";
    public Integer idTrackers;
    public String name = "Track";

    protected String url;
    protected TrackingNumber trackNumber;



    public final String getTrackerName() {
        return name;
    }

    public void call(){
        run();
    }

    @Override
    public void run(){
            System.out.println(getTrackerName() + " is started");
            List<TrackInfoRecord> res = this.process();
            trackNumber.saveTrackingInfo(this.getTrackerName(), res);
            System.out.println(getTrackerName() + " is finished");
    }

    protected final List<TrackInfoRecord> process() {
        String body = prepareBody();
        if (body == null) {
            return prepareEmpryInfo();
        }
        return parseTrackInfoRecords(body);
    }

    private List<TrackInfoRecord> prepareEmpryInfo() {
        List<TrackInfoRecord> res = new LinkedList<TrackInfoRecord>();
        res.add(new TrackInfoRecord(getTrackerName(), new Date(), "Сервер временно не доступен!"));
        return res;
    }

    protected abstract List<TrackInfoRecord> parseTrackInfoRecords(String body);

    protected String prepareBody() {
        try {
            return Filters.filterComments(Downloader.getHTML(url));
        } catch (UnknownHostException e){
            System.out.println(getTrackerName()+" сервер не доступен!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
