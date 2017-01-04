package com.myself.appdemo.db.dbmanager;

import com.alibaba.fastjson.JSONObject;
import com.myself.appdemo.db.DataBaseManager;
import com.myself.appdemo.db.bean.StoreTemplate;
import com.myself.appdemo.db.dao.DaoMaster;
import com.myself.appdemo.db.dao.TemplateDBDao;
import com.myself.appdemo.db.entity.TemplateDB;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by riven_chris on 16/6/20.
 */
public class TemplateDBManager extends DataBaseManager<TemplateDB, String> {
    private static TemplateDBManager mInstance;

    public static TemplateDBManager getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new TemplateDBManager(helper);
        return mInstance;
    }

    public TemplateDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<TemplateDB, String> getAbstractDao() {
        return daoSession.getTemplateDBDao();
    }

    public boolean templateExist(String templateId) {
        return getQueryBuilder().where(TemplateDBDao.Properties.Template_id.eq(templateId)).count() > 0;
    }

    public boolean insertObject(StoreTemplate template) {
        return insert(new TemplateDB(template.getTemplate_id(), template.getTitle(),
                JSONObject.toJSONString(template.getTemplate_content())));
    }

    public TemplateDB queryTemplate(String templateId) {
        return getQueryBuilder().where(TemplateDBDao.Properties.Template_id.eq(templateId)).unique();
    }
}
