package com.itibo.database;

import com.itibo.mapper.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;


public class DataBaseUtilites {

    private SqlSessionFactory sqlSessionFactory = null;
    private SqlSession sqlSession = null;
    private UsersMapper usersMapper = null;
    private TrackersMapper trackersMapper = null;
    private TrackingNumbersMapper trackingNumbersMapper = null;
    private RelationsMapper relationsMapper = null;
    private TrackInfoRecordsMapper trackInfoRecordsMapper = null;


    private void setConfig() {
        try {
            if (null == sqlSession) {
                String resource = "mybatis-config.xml";
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
                sqlSession = sqlSessionFactory.openSession();
            }
            if (null == usersMapper) {
                usersMapper = sqlSession.getMapper(UsersMapper.class);
            }
            if (null == trackersMapper){
                trackersMapper = sqlSession.getMapper(TrackersMapper.class);
            }
            if (null == trackingNumbersMapper){
                trackingNumbersMapper = sqlSession.getMapper(TrackingNumbersMapper.class);
            }
            if (null == relationsMapper){
                relationsMapper = sqlSession.getMapper(RelationsMapper.class);
            }
            if (null == trackInfoRecordsMapper){
                trackInfoRecordsMapper = sqlSession.getMapper(TrackInfoRecordsMapper.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static void main(String[] args) throws IOException {
        List<TrackInfoRecordsInfo> tir = new LinkedList();
        DataBaseUtilites db = new DataBaseUtilites();
        tir = db.getTrackInfoRecords(44);
        for (TrackInfoRecordsInfo rec: tir){
            System.out.println(rec.getMessage() + " " + rec.getDate());
        }
    }

    public void sessionCommit(){
        sqlSession.commit();
    }

    public String getUserEmailById(Integer id){
        setConfig();
        return usersMapper.getUserEmailById(id);
    }

    public List<TrackInfoRecordsInfo> getTrackInfoRecords(Integer idRelations){
        setConfig();
        return trackInfoRecordsMapper.getTrackInfoRecords(idRelations);
    }

    public void delTrackInfoRecordsByIdTN(Integer idTrackingNumbers){
        setConfig();
        trackInfoRecordsMapper.delTrackInfoRecordsByIdTN(idTrackingNumbers);
    }

    public void addTrackInfoRecords(TrackInfoRecordsInfo trackInfoRecordsInfo){
        setConfig();
        trackInfoRecordsMapper.addTrackInfoRecords(trackInfoRecordsInfo);
    }

    public Integer getTrackInfoRecordsId(TrackInfoRecordsInfo trackInfoRecordsInfo){
        setConfig();
        return trackInfoRecordsMapper.getTrackInfoRecordsId(trackInfoRecordsInfo);
    }

    public List<RelationsInfo> getRelationsByIdTN(Integer idTrackingNumbers){
        setConfig();
        return relationsMapper.getRelationsByIdTN(idTrackingNumbers);
    }

    public void delRelations(Integer idTrackingNumbers){
        setConfig();
        relationsMapper.delRelations(idTrackingNumbers);
    }

    public void addRelations(List<RelationsInfo> relationsInfoList){
        setConfig();
        relationsMapper.addRelations(relationsInfoList);
    }

    public List<Integer> getIdTrackersByIdTN(Integer idTrackingNumbers){
        setConfig();
        return relationsMapper.getIdTrackersByIdTN(idTrackingNumbers);
    }

    public String getTrackerNameByIdRelation(Integer idRelations){
        setConfig();
        return relationsMapper.getTrackerNameByIdRelation(idRelations);
    }

    public Integer getIdRelations(Integer idTrackingNumbers, Integer idTrackers){
        setConfig();
        return relationsMapper.getIdRelations(idTrackingNumbers, idTrackers);
    }

    public void delRelationsTn(Integer idTrackingNumbers, Integer idTrackers){
        setConfig();
        relationsMapper.delRelationsTn(idTrackingNumbers, idTrackers);
    }

    public void addRelationsEd(Integer idTrackingNumbers, Integer idTrackers){
        setConfig();
        relationsMapper.addRelationsEd(idTrackingNumbers, idTrackers);
    }

    public UserInfo getUserByGoogleId(String googleid){
        setConfig();
        return usersMapper.getUserByGoogleId(googleid);
    }

    public void upEmail(UserInfo userInfo){
        setConfig();
        usersMapper.upEmail(userInfo);
    }

    public Integer getUserIdByEmail(String email) {
        setConfig();
        return usersMapper.getUserIdByEmail(email);
    }

    public Integer getUserIdByGoogleId(String googleid) {
        setConfig();
        return usersMapper.getUserIdByGoogleId(googleid);
    }

    public void addUser(String email, String googleid) {
        setConfig();
        usersMapper.addUser(email, googleid);
    }

    public TrackingNumberInfo getTrackingNumberById(Integer idTrackingNumbers){
        setConfig();
        return trackingNumbersMapper.getTrackingNumberById(idTrackingNumbers);
    }

    public void delTrackingNumbers(Integer idTrackingNumbers){
        setConfig();
        trackingNumbersMapper.delTrackingNumbers(idTrackingNumbers);
    }

    public void addTrackingNumbers(TrackingNumberInfo trackingNumberInfo){
        setConfig();
        trackingNumbersMapper.addTrackingNumber(trackingNumberInfo);
    }

    public Integer getTrackingNumberIdByName (String trackingNumber, Integer idUsers){
        setConfig();
        return trackingNumbersMapper.getTrackingNumberIdByName(trackingNumber, idUsers);
    }

    public void upTrackingNumbers(TrackingNumberInfo trackingNumberInfo){
        setConfig();
        trackingNumbersMapper.upTrackingNumbers(trackingNumberInfo);
    }

    public List<TrackerInfo> getTrackers(){
        setConfig();
        return trackersMapper.getTrackers();
    }

    public TrackerInfo getTrackerById(Integer idTrackers){
        setConfig();
        return trackersMapper.getTrackerById(idTrackers);
    }

    public Integer getIdTrackersByName(String name){
        setConfig();
        return trackersMapper.getIdTrackersByName(name);
    }

    public List<TrackingNumberInfo> getTrackingNumberInfoList(Integer idUsers, String trackingNumber, boolean admin){
        setConfig();
        return trackingNumbersMapper.getTrackingNumberInfoList(idUsers, trackingNumber, admin);
    }

    private String name;
    private Long idTrackers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdTrackers() {
        return idTrackers;
    }

    public void setIdTrackers(Long idTrackers) {
        this.idTrackers = idTrackers;
    }


}
