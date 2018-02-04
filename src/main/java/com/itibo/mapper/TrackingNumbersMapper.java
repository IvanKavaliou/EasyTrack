package com.itibo.mapper;

import com.itibo.database.TrackingNumberInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrackingNumbersMapper {
    List<TrackingNumberInfo> getTrackingNumberInfoList(@Param("idUsers")Integer idUser,
                                                       @Param("trackingNumber") String trackingNumber,
                                                       @Param("admin") boolean admin);
    TrackingNumberInfo getTrackingNumberById(Integer idTrackingNumbers);
    void addTrackingNumber(TrackingNumberInfo trackingNumberInfo);
    void delTrackingNumbers(Integer idTrackingNumbers);
    void upTrackingNumbers(TrackingNumberInfo trackingNumberInfo);
    Integer getTrackingNumberIdByName (@Param("trackingNumber")String trackingNumber, @Param("idUsers") Integer idUsers);
    //Integer idUsers, String trackingNumber, String namePackage, Integer cost
}
