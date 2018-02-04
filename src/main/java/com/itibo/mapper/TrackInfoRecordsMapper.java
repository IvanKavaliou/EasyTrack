package com.itibo.mapper;


import com.itibo.database.TrackInfoRecordsInfo;
import com.itibo.tracking.TrackInfoRecord;

import java.util.List;

public interface TrackInfoRecordsMapper {
    void delTrackInfoRecordsByIdTN(Integer idTrackingNumbers);
    List<TrackInfoRecordsInfo> getTrackInfoRecords(Integer idRelations);
    void addTrackInfoRecords(TrackInfoRecordsInfo trackInfoRecordsInfo);
    Integer getTrackInfoRecordsId(TrackInfoRecordsInfo trackInfoRecordsInfo);
}
