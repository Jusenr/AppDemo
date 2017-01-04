package com.myself.appdemo.db.dbmanager;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.myself.appdemo.db.AccountHelper;
import com.myself.appdemo.db.DataBaseManager;
import com.myself.appdemo.db.bean.ServiceMessageList;
import com.myself.appdemo.db.dao.CompanionDBDao;
import com.myself.appdemo.db.dao.DaoMaster;
import com.myself.appdemo.db.entity.CompanionDB;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.DeleteQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市操作
 * Created by guchenkai on 2015/12/6.
 */
public class CompanionDBManager extends DataBaseManager<CompanionDB, String> {
    private static CompanionDBManager mInstance;

    public static CompanionDBManager getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new CompanionDBManager(helper);
        return mInstance;
    }

    public CompanionDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<CompanionDB, String> getAbstractDao() {
        return daoSession.getCompanionDBDao();
    }

    public void addDBColumn(SQLiteDatabase db, String columnName) {
        String str = "alter table " + CompanionDBDao.TABLENAME + " add " + columnName + " text";
        db.execSQL(str);
    }

    /**
     * 根据推送id获取推送数据
     */
    public CompanionDB getCompanInfoById(String id) {
        return getQueryBuilder().where(CompanionDBDao.Properties.Id.eq(id), CompanionDBDao.Properties.Uid.eq(AccountHelper.getCurrentUid())).unique();
    }

    /**
     * 获取没有还没有获取到数据的推送id
     */
    public List<String> getNotDownloadIds() {
        List<String> notDownloadIds = new ArrayList<>();
        List<CompanionDB> companionDBs = getQueryBuilder().where(CompanionDBDao.Properties.IsDownload.eq("0"), CompanionDBDao.Properties.Uid.eq(AccountHelper.getCurrentUid())).listLazy();
        for (CompanionDB companionDB : companionDBs) {
            notDownloadIds.add(companionDB.getId());
        }
        return notDownloadIds;
    }

    /**
     * 获取没有还没有获取到数据的推送id
     */
    public ArrayList<String> getNotDownloadIds(String serviceId) {
        ArrayList<String> notDownloadIds = new ArrayList<>();
        List<CompanionDB> companionDBs = getQueryBuilder().where(CompanionDBDao.Properties.IsDownload.eq("0"), CompanionDBDao.Properties.Service_id.eq(serviceId), CompanionDBDao.Properties.Uid.eq(AccountHelper.getCurrentUid())).orderDesc(CompanionDBDao.Properties.Receiver_time).list();
        for (CompanionDB companionDB : companionDBs) {
            notDownloadIds.add(companionDB.getId());
        }
        return notDownloadIds;
    }

    /**
     * 获取已经下载的文章列表
     */
    public List<CompanionDB> getDownloadArticles(String service_id) {
        if (TextUtils.isEmpty(service_id)) return null;
        return getQueryBuilder().where(CompanionDBDao.Properties.Service_id.eq(service_id), CompanionDBDao.Properties.IsDownload.eq("1"), CompanionDBDao.Properties.Uid.eq(AccountHelper.getCurrentUid())).orderAsc(CompanionDBDao.Properties.Receiver_time).build().list();
    }

    /**
     * 设置文章的下载状态
     */
 /*   public void updataDownloadFinish(ServiceMessageList serviceMessageList) {
        CompanionDB unique = getQueryBuilder().where(CompanionDBDao.Properties.key.eq(serviceMessageList.getId() + AccountHelper.getCurrentUid())).unique();
        unique.setContent_lists(JSON.toJSONString(serviceMessageList.getContent_lists()));
        unique.setIsDownload("1");
        unique.setType(serviceMessageList.getType());
        unique.setRelease_time(serviceMessageList.getRelease_time() + "");
//        deleteContent(unique.getService_id());
//        insertFinishDownload(unique.getService_id(), unique.getId(), unique.getRelease_time(), unique.getContent_lists());
        insertOrReplace(unique);
    }*/

    /**
     * 插入没有带下载的文章
     */
    public void insertFixDownload(String service_id, String id, long receiver_time) {
        insert(new CompanionDB(id, "", service_id, "article", "", "", 0 + "", AccountHelper.getCurrentUid(), id + AccountHelper.getCurrentUid(), "", "", "", "", receiver_time + "", ""));
    }

    /**
     * 插入已经下载的文章
     */
    public void insertFinishDownload(String service_id, String id, String release_time, String content_lists, long receiver_time) {
        insert(new CompanionDB(id, "", service_id, "article", release_time, content_lists, 1 + "", AccountHelper.getCurrentUid(), id + AccountHelper.getCurrentUid(), "", "", "", "", receiver_time + "", ""));
    }

    /**
     * 插入文字信息
     */
   /* public void insertText(String service_id, String id, String release_time, String message) {
        insert(new CompanionDB(id, service_id, "text", release_time, "", 1 + "", AccountHelper.getCurrentUid(), id + AccountHelper.getCurrentUid(), message, "", "", ""));
    }*/

    /**
     * 插入回复信息
     */
    /*public void insertReply(String service_id, String id, String release_time, String message) {
        insert(new CompanionDB(id, service_id, "text", release_time, "", 1 + "", AccountHelper.getCurrentUid(), id + AccountHelper.getCurrentUid(), message, "", "", ""));
    }*/

    /**
     * 插入上传的文字
     */
    /*public String insertUploadText(String service_id, String message) {
        String currentTime = (int) (System.currentTimeMillis() / 1000) + "";
        insert(new CompanionDB(currentTime, service_id, "upload_text", currentTime, "", 1 + "", AccountHelper.getCurrentUid(), currentTime + AccountHelper.getCurrentUid(), message, "", "", ""));
        return currentTime;
    }*/

    /**
     * 清除公众号数据
     */
    public void removeData(String service_id) {
        DeleteQuery<CompanionDB> companionDBDeleteQuery = getQueryBuilder().where(CompanionDBDao.Properties.Service_id.eq(service_id), CompanionDBDao.Properties.Uid.eq(AccountHelper.getCurrentUid())).buildDelete();
        companionDBDeleteQuery.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 清除公众号数据
     */
    public void removeEmptyData(String service_id) {
        DeleteQuery<CompanionDB> companionDBDeleteQuery = getQueryBuilder().where(CompanionDBDao.Properties.Service_id.eq(service_id), CompanionDBDao.Properties.Uid.eq("0"), CompanionDBDao.Properties.Uid.eq(AccountHelper.getCurrentUid())).buildDelete();
        companionDBDeleteQuery.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 清除公众号数据
     */
 /*   public void removeDataWithId(String id) {
        DeleteQuery<CompanionDB> companionDBDeleteQuery = getQueryBuilder().where(CompanionDBDao.Properties.id.eq(id), CompanionDBDao.Properties.uid.eq(AccountHelper.getCurrentUid())).buildDelete();
        companionDBDeleteQuery.executeDeleteWithoutDetachingEntities();
    }*/

    /**
     * 插入上传的图片
     */
   /* public String insertUploadImage(String service_id, String imageUrl) {
        String currentTime = (int) (System.currentTimeMillis() / 1000) + "";
        insert(new CompanionDB(currentTime, service_id, "upload_image", currentTime, "", 1 + "", AccountHelper.getCurrentUid(), currentTime + AccountHelper.getCurrentUid(), imageUrl, "", "", ""));
        return currentTime;
    }*/

    /**
     * 或许服务号最新文章时间
     */
/*    public String getNearestTime(String service_id) {
        List<CompanionDB> list = getQueryBuilder().where(CompanionDBDao.Properties.service_id.eq(service_id), CompanionDBDao.Properties.uid.eq(AccountHelper.getCurrentUid())).orderDesc(CompanionDBDao.Properties.release_time).limit(0).limit(1).list();
        if (list == null || list.size() == 0) return "0";
        return list.get(0).getRelease_time();
    }*/


    /**
     * 或许服务号最新文章时间
     */
    public CompanionDB getNearestItem(String service_id) {
        List<CompanionDB> list = getQueryBuilder().where(CompanionDBDao.Properties.Service_id.eq(service_id), CompanionDBDao.Properties.Uid.eq(AccountHelper.getCurrentUid())).orderDesc(CompanionDBDao.Properties.Receiver_time).limit(0).limit(1).list();
        if (list == null || list.size() == 0) return null;
        return list.get(0);
    }

    private String toJsonString(Object object) {
        if (null == object) return "";
        return JSON.toJSONString(object);
    }

    public void insertAll(String service_id, ArrayList<ServiceMessageList> lists) {
        for (ServiceMessageList serviceMessageList : lists) {
            insertObject(service_id, serviceMessageList);
        }
    }

    public boolean insertObject(String service_id, ServiceMessageList serviceMessageList) {
        serviceMessageList.setService_id(service_id);
        return insertOrReplace(messageToCompanionDB(serviceMessageList));
    }

    public boolean insertObject(ServiceMessageList serviceMessageList) {
        return insertOrReplace(messageToCompanionDB(serviceMessageList));
    }

    public boolean updateObject(ServiceMessageList serviceMessageList) {
        return update(messageToCompanionDB(serviceMessageList));
    }

    public void deleteByPushId(String pushId, String serviceId, String type) {
        getQueryBuilder().where(CompanionDBDao.Properties.Push_id.eq(pushId),
                CompanionDBDao.Properties.Service_id.eq(serviceId),
                CompanionDBDao.Properties.Type.eq(type)).buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    /**
     * 删除订阅号的内容
     *//*
    public void deleteContent(String service_id) {
        DeleteQuery<CompanionDB> companionDBDeleteQuery = getQueryBuilder().where(CompanionDBDao.Properties.service_id.eq(service_id), CompanionDBDao.Properties.uid.eq(AccountHelper.getCurrentUid())).buildDelete();
        companionDBDeleteQuery.executeDeleteWithoutDetachingEntities();
//        rawQuery("delete from " + CompanionDBDao.TABLENAME + " where " + CompanionDBDao.Properties.is_download.columnName + " = '0'");
    }*/
    private CompanionDB messageToCompanionDB(ServiceMessageList serviceMessageList) {
        if (serviceMessageList == null)
            return null;

        return new CompanionDB(serviceMessageList.getId(), serviceMessageList.getPush_id(),
                serviceMessageList.getService_id(),
                serviceMessageList.getType(), serviceMessageList.getRelease_time() + "",
                toJsonString(serviceMessageList.getContent_lists()),
                1 + "", AccountHelper.getCurrentUid(), serviceMessageList.getId() + AccountHelper.getCurrentUid(),
                serviceMessageList.getMessage() == null ? "" : serviceMessageList.getMessage(),
                toJsonString(serviceMessageList.getImage()),
                toJsonString(serviceMessageList.getReply()), serviceMessageList.getSend_state() + "",
                serviceMessageList.getReceiver_time() + "", toJsonString(serviceMessageList.getNotice()));
    }
}
