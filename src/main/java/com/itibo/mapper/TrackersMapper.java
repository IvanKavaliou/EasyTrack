package com.itibo.mapper;

import com.itibo.database.TrackerInfo;

import java.util.List;

public interface TrackersMapper {
    List<TrackerInfo> getTrackers();
    TrackerInfo getTrackerById(Integer idTrackers);
    Integer getIdTrackersByName(String name);
}
