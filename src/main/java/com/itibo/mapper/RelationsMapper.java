package com.itibo.mapper;


import com.itibo.database.RelationsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;

public interface RelationsMapper {
    void addRelations(List<RelationsInfo> relationsInfoList);
    void delRelations(Integer idTrackingNumbers);
    List<Integer> getIdTrackersByIdTN(Integer idTrackingNumbers);
    List<RelationsInfo> getRelationsByIdTN(Integer idTrackingNumbers);
    String getTrackerNameByIdRelation(Integer idRelations);
    Integer getIdRelations(@Param("idTrackingNumbers") Integer idTrackingNumbers,
                           @Param("idTrackers") Integer idTrackers);

    Integer delRelationsTn(@Param("idTrackingNumbers") Integer idTrackingNumbers,
                           @Param("idTrackers") Integer idTrackers);
    void addRelationsEd(@Param("idTrackingNumbers") Integer idTrackingNumbers,
                        @Param("idTrackers") Integer idTrackers);
}
