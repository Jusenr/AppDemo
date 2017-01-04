package com.myself.appdemo.db.dbmanager;

import com.myself.appdemo.db.DataBaseManager;
import com.myself.appdemo.db.dao.CityDBDao;
import com.myself.appdemo.db.dao.DaoMaster;
import com.myself.appdemo.db.entity.CityDB;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市操作
 * Created by guchenkai on 2015/12/6.
 */
public class CityDBManager extends DataBaseManager<CityDB, String> {
    private static CityDBManager mInstance;

    public static CityDBManager getInstance(DaoMaster.OpenHelper helper) {
        if (mInstance == null)
            mInstance = new CityDBManager(helper);
        return mInstance;
    }

    public CityDBManager(DaoMaster.OpenHelper helper) {
        super(helper);
    }

    @Override
    public AbstractDao<CityDB, String> getAbstractDao() {
        return daoSession.getCityDBDao();
    }

    /**
     * 根据省份id获取城市名
     *
     * @param provinceId 省份id
     * @return 城市名列表
     */
    public List<String> getCityNamesByProvinceId(String provinceId) {
        List<String> cityNames = new ArrayList<>();
        List<CityDB> cityDBs = getQueryBuilder().where(CityDBDao.Properties.Province_id.eq(provinceId)).list();
        for (CityDB cityDB : cityDBs) {
            cityNames.add(cityDB.getName());
        }
        return cityNames;
    }

    /**
     * 根据省份名称获取城市名
     *
     * @param provinceName 省份名称
     * @return 城市名列表
     */
    public List<String> getCityNamesByProvinceName(String provinceName) {
        List<String> cityNames = new ArrayList<>();
        List<CityDB> cityDBs = getQueryBuilder().where(
                new WhereCondition.StringCondition("PROVINCE_ID=" + "(SELECT PROVINCE_ID FROM putao_wd_province WHERE NAME=\"" + provinceName + "\")"))
                .list();
        for (CityDB cityDB : cityDBs) {
            cityNames.add(cityDB.getName());
        }
        return cityNames;
    }

    /**
     * 根据城市名称获得城市id
     *
     * @param province_id 省区id
     * @param cityName    城市名称
     * @return 城市id
     */
    public String getCityId(String province_id, String cityName) {
        CityDB cityDB = getQueryBuilder().where(CityDBDao.Properties.Province_id.eq(province_id), CityDBDao.Properties.Name.eq(cityName)).unique();
        if (cityDB == null)
            return "";
        else
            return cityDB.getCity_id();
    }

    /**
     * 根据城市名称获得城市id
     *
     * @param cityId 城市名称
     * @return 城市id
     */
    public String getCityNameByCityId(String cityId) {
        CityDB cityDB = getQueryBuilder().where(CityDBDao.Properties.City_id.eq(cityId)).unique();
        if (cityDB == null)
            return "";
        else
            return cityDB.getName();
    }
}
