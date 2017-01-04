package com.myself.appdemo;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GenerateDB {
    private static final int DATABASE_VERSION = 1;
    private static final String PACKAGE_NAME_BEAN = "com.myself.appdemo.db.entity";
    private static final String PACKAGE_NAME_DAO = "com.myself.appdemo.db.dao";
    private static final String ROOT = "C:\\Users\\Administrator\\AndroidStudioProjects\\AppDemo\\app\\src\\main\\db-gen";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(DATABASE_VERSION, PACKAGE_NAME_BEAN);
        schema.setDefaultJavaPackageDao(PACKAGE_NAME_DAO);
        initProvince(schema);
        initCity(schema);
        initDistrict(schema);
//        initAddress(schema);
        initCompanionDB(schema);
        initTemplateDB(schema);
        initPaibandDataCacheDB(schema);
        initMessageDB(schema);
        new DaoGenerator().generateAll(schema, ROOT);
    }

    /**
     * 省份
     */
    private static void initProvince(Schema schema) {
        Entity province = schema.addEntity("ProvinceDB");
        province.setTableName("putao_wd_province");
//        province.addIdProperty().autoincrement().index();//id
        province.addStringProperty("province_id").primaryKey();//省id
        province.addStringProperty("name");//省名字
    }

    /**
     * 城市
     */
    private static void initCity(Schema schema) {
        Entity city = schema.addEntity("CityDB");
        city.setTableName("putao_wd_city");
//        city.addIdProperty().autoincrement().index();//id
        city.addStringProperty("province_id");//省id
        city.addStringProperty("city_id").primaryKey();//市id
        city.addStringProperty("name");//市名字
    }

    /**
     * 城区
     */
    private static void initDistrict(Schema schema) {
        Entity district = schema.addEntity("DistrictDB");
        district.setTableName("putao_wd_district");
//        district.addIdProperty().autoincrement().index();//id
        district.addStringProperty("province_id");//省id
        district.addStringProperty("city_id");//市id
        district.addStringProperty("district_id").primaryKey();//区id
        district.addStringProperty("name");//区名字
    }

    /**
     * 地址
     */
//    private static void initAddress(Schema schema) {
//        Entity address = schema.addEntity("AddressDB");
//        address.setTableName("putao_wd_address");
//        address.addIdProperty().autoincrement().index();//id
//        address.addStringProperty("name");//收货人姓名
//        address.addStringProperty("province");//省份
//        address.addStringProperty("province_id");//省份id
//        address.addStringProperty("city");//城市
//        address.addStringProperty("city_id");//城市id
//        address.addStringProperty("district");//城区
//        address.addStringProperty("district_id");//城区id
//        address.addStringProperty("street");//街道
//        address.addStringProperty("mobile");//收货人电话
//        address.addBooleanProperty("isDefault");//是否是默认地址
//    }
    private static void initCompanionDB(Schema schema) {
        Entity companionDB = schema.addEntity("CompanionDB");
        companionDB.setTableName("putao_wd_companion");
        companionDB.addStringProperty("id").primaryKey();
        companionDB.addStringProperty("push_id");
        companionDB.addStringProperty("service_id");
        companionDB.addStringProperty("type");
        companionDB.addStringProperty("release_time");
        companionDB.addStringProperty("content_lists");
        companionDB.addStringProperty("isDownload");
        companionDB.addStringProperty("uid");
        companionDB.addStringProperty("key");
        companionDB.addStringProperty("message");
        companionDB.addStringProperty("image");
        companionDB.addStringProperty("reply");
        companionDB.addStringProperty("is_upload_finish");
        companionDB.addStringProperty("receiver_time");
        companionDB.addStringProperty("notice");
    }

    private static void initTemplateDB(Schema schema) {
        Entity templateDB = schema.addEntity("TemplateDB");
        templateDB.setTableName("putao_wd_store_template");
        templateDB.addStringProperty("template_id").primaryKey();
        templateDB.addStringProperty("title");
        templateDB.addStringProperty("template_content");
    }

    private static void initPaibandDataCacheDB(Schema schema) {
        Entity templateDB = schema.addEntity("PaibandDB");
        templateDB.setTableName("putao_wd_store_paiband");
        templateDB.addLongProperty("_id").primaryKey().autoincrement();
        templateDB.addStringProperty("cid");
        templateDB.addStringProperty("data_content");
        templateDB.addStringProperty("date");
        templateDB.addStringProperty("is_uploaded");
    }

    private static void initMessageDB(Schema schema) {
        Entity templateDB = schema.addEntity("PushMessageDB");
        templateDB.setTableName("putao_wd_message");
        templateDB.addLongProperty("push_id").primaryKey().autoincrement();
        templateDB.addStringProperty("admin_id");
        templateDB.addIntProperty("show_location");
        templateDB.addIntProperty("data_type");
        templateDB.addStringProperty("cid");
        templateDB.addStringProperty("uid");

        templateDB.addIntProperty("goods_appid");
        templateDB.addIntProperty("goods_id");
        templateDB.addStringProperty("goods_name");

        templateDB.addStringProperty("location_type");
        templateDB.addStringProperty("location_action");

        templateDB.addStringProperty("status");
        templateDB.addIntProperty("release_time");
        templateDB.addStringProperty("customParams");
        templateDB.addStringProperty("templateParam");
        templateDB.addStringProperty("templateContent");
    }
}
