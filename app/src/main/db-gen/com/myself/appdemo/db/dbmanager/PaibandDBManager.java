package com.myself.appdemo.db.dbmanager;

import com.myself.appdemo.db.DataBaseManager;
import com.myself.appdemo.db.dao.DaoMaster;
import com.myself.appdemo.db.entity.PaibandDB;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by leo on 2016/10/12.
 */

public class PaibandDBManager extends DataBaseManager<PaibandDB, Long> {
    public PaibandDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<PaibandDB, Long> getAbstractDao() {
        return daoSession.getPaibandDBDao() ;
    }

    private static PaibandDBManager mInstance;

    public static PaibandDBManager getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new PaibandDBManager(helper);
        return mInstance;
    }

    public List<PaibandDB> getUnuploadData(String cid){
        return getQueryBuilder().where(new WhereCondition.StringCondition("IS_UPLOADED='0'and CID='"+cid+"'")).list();
    }

}
