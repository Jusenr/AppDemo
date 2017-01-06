package com.myself.appdemo.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myself.appdemo.TotalApplication;
import com.myself.appdemo.db.dbmanager.CityDBManager;
import com.myself.appdemo.db.dbmanager.DistrictDBManager;
import com.myself.appdemo.db.dbmanager.ProvinceDBManager;
import com.myself.appdemo.db.entity.CityDB;
import com.myself.appdemo.db.entity.DistrictDB;
import com.myself.appdemo.db.entity.ProvinceDB;
import com.myself.mylibrary.util.FileUtils;
import com.myself.mylibrary.util.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 地区解析工具
 * Created by guchenkai on 2015/12/15.
 */
public class DistrictUtils {
    private static JSONObject list;//城市列表
    private static List<ProvinceDB> mProvinceDBs = new ArrayList<>();//省份列表
    private static List<CityDB> mCityDBs = new ArrayList<>();//城区列表
    private static List<DistrictDB> mDistrictDBs = new ArrayList<>();//城区列表

    private static TotalApplication application = (TotalApplication) TotalApplication.getInstance();

    /**
     * 插入地区列表
     */
    public static void insertRegion() {
//        String result = ResourcesUtils.getAssetsTextFile(TotalApplication.getInstance(), "city.json");
        String result = FileUtils.getFileContent(TotalApplication.resourcePath + File.separator + "region" + File.separator + "region.json");
        if (!TextUtils.isEmpty(result)) {
            list = JSON.parseObject(result.substring(result.indexOf("{"), result.length()));//城市列表
            Logger.w("城市列表获取完成");
            insertProvince();
            Logger.w("省份插入数据库成功");
            insertCity();
            Logger.w("城市插入数据库成功");
            insertDistrict();
            Logger.w("城区插入数据库成功");
        }
    }

    /**
     * 将省份插入数据库
     */
    private static void insertProvince() {
        JSONArray provinceList = list.getJSONArray("100000");
        for (Object object : provinceList) {
            JSONObject province = (JSONObject) object;
            ProvinceDB provinceDB = new ProvinceDB();
            provinceDB.setProvince_id(province.getString("id"));
            provinceDB.setName(province.getString("name"));
            mProvinceDBs.add(provinceDB);
        }
//        Logger.w(mProvinceDBs.toString());
        application.getDataBaseManager(ProvinceDBManager.class).insertList(mProvinceDBs);
    }

    /**
     * 将城市插入数据库
     */
    private static void insertCity() {
        for (ProvinceDB provinceDB : mProvinceDBs) {
            JSONArray cityList = list.getJSONArray(provinceDB.getProvince_id());
            if (cityList != null)
                for (Object object : cityList) {
                    JSONObject city = (JSONObject) object;
                    CityDB cityDB = new CityDB();
                    cityDB.setProvince_id(city.getString("parent_id"));
                    cityDB.setCity_id(city.getString("id"));
                    cityDB.setName(city.getString("name"));
                    mCityDBs.add(cityDB);
                }
        }
//        Logger.w(mCityDBs.toString());
        application.getDataBaseManager(CityDBManager.class).insertList(mCityDBs);
    }

    /**
     * 将城区插入数据库
     */
    private static void insertDistrict() {
        for (CityDB cityDB : mCityDBs) {
            JSONArray districtList = list.getJSONArray(cityDB.getCity_id());
            if (districtList != null)
                for (Object object : districtList) {
                    JSONObject district = (JSONObject) object;
                    DistrictDB districtDB = new DistrictDB();
                    districtDB.setProvince_id(cityDB.getProvince_id());
                    districtDB.setCity_id(district.getString("parent_id"));
                    districtDB.setDistrict_id(district.getString("id"));
                    districtDB.setName(district.getString("name"));
                    mDistrictDBs.add(districtDB);
                }
        }
//        Logger.w(mCityDBs.toString());
        application.getDataBaseManager(DistrictDBManager.class).insertList(mDistrictDBs);
    }
}
