package com.myself.appdemo.db.dbmanager;

import com.myself.appdemo.db.DataBaseManager;
import com.myself.appdemo.db.dao.DaoMaster;
import com.myself.appdemo.db.dao.ProvinceDBDao;
import com.myself.appdemo.db.entity.ProvinceDB;

import org.greenrobot.greendao.AbstractDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 省份操作
 * Created by guchenkai on 2015/12/6.
 */
public class ProvinceDBManager extends DataBaseManager<ProvinceDB, String> {
    private static ProvinceDBManager mInstance;

    public static ProvinceDBManager getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new ProvinceDBManager(helper);
        return mInstance;
    }

    public ProvinceDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<ProvinceDB, String> getAbstractDao() {
        return daoSession.getProvinceDBDao();
    }

    /**
     * 获得所有省份名
     *
     * @return 省份名
     */
    public List<String> getProvinceNames() {
        List<String> provinces = new ArrayList<>();
        List<ProvinceDB> provinceDBs = loadAll();
        for (ProvinceDB provinceDB : provinceDBs) {
            provinces.add(provinceDB.getName());
        }
        return provinces;
    }

    /**
     * 根据省份名称获得省份id
     * @param provinceName 省份名称
     * @return 省份id
     */
    public String getProvinceId(String provinceName) {
        ProvinceDB provinceDB = getQueryBuilder().where(ProvinceDBDao.Properties.Name.eq(provinceName)).uniqueOrThrow();
        return provinceDB.getProvince_id();
    }
    /**
     * 根据省份名称获得省份id
     * @param provinceId 省份名称
     * @return 省份id
     */
    public String getProvinceNameByProvinceId(String provinceId) {
        ProvinceDB provinceDB = getQueryBuilder().where(ProvinceDBDao.Properties.Province_id.eq(provinceId)).uniqueOrThrow();
        return provinceDB.getName();
    }
}
