package com.myself.appdemo.db.dbmanager;


import com.myself.appdemo.db.AccountHelper;
import com.myself.appdemo.db.DataBaseManager;
import com.myself.appdemo.db.dao.DaoMaster;
import com.myself.appdemo.db.dao.PushMessageDBDao;
import com.myself.appdemo.db.entity.PushMessageDB;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */

public class MessageDBMangaer extends DataBaseManager<PushMessageDB, Long> {
    private static MessageDBMangaer mInstance;

    public MessageDBMangaer(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<PushMessageDB, Long> getAbstractDao() {
        return daoSession.getPushMessageDBDao();
    }

    public static MessageDBMangaer getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new MessageDBMangaer(helper);
        return mInstance;
    }

    public List<PushMessageDB> getMessageByPage(int type, int page) {
        return getQueryBuilder().where(PushMessageDBDao.Properties.Admin_id.eq(AccountHelper.getCurrentUid()),
                PushMessageDBDao.Properties.Show_location.eq(type)).offset(page * 20).limit(20)
                .orderDesc(PushMessageDBDao.Properties.Release_time).build().list();
    }

    public int getUnreadMessageCount(int type) {
        return (int) getQueryBuilder().where(PushMessageDBDao.Properties.Admin_id.eq(AccountHelper.getCurrentUid()),
                PushMessageDBDao.Properties.Show_location.eq(type),
                PushMessageDBDao.Properties.Status.eq("unread")).buildCount().count();
    }

    public int getAllUnreadMessageCount() {
        return (int) getQueryBuilder().where(PushMessageDBDao.Properties.Admin_id.eq(AccountHelper.getCurrentUid()),
                PushMessageDBDao.Properties.Show_location.notEq("1"),
                PushMessageDBDao.Properties.Status.eq("unread")).buildCount().count();
    }

    public PushMessageDB getMessageById(long id) {
        return getQueryBuilder().where(PushMessageDBDao.Properties.Admin_id.eq(AccountHelper.getCurrentUid()),
                PushMessageDBDao.Properties.Push_id.eq(id)).build().unique();
    }

    public List<PushMessageDB> getMessageByTypeAndCid(int type, String cid, int page) {
        return getQueryBuilder().where(PushMessageDBDao.Properties.Admin_id.eq(AccountHelper.getCurrentUid()),
                PushMessageDBDao.Properties.Show_location.eq(type), PushMessageDBDao.Properties.Cid.eq(cid)).
                offset(page * 10).limit(10).orderDesc(PushMessageDBDao.Properties.Release_time).build().list();
    }

}
