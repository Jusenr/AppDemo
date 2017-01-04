package com.myself.appdemo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.myself.appdemo.db.dao.DaoMaster;
import com.myself.appdemo.db.dao.DaoSession;
import com.myself.mylibrary.db.DBEntity;
import com.myself.mylibrary.db.DatabaseInterface;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 数据库管理器
 * Created by guchenkai on 2015/11/26.
 */
public abstract class DataBaseManager<Entity extends DBEntity, Key extends Serializable> implements DatabaseInterface<Entity, Key> {
    protected static DaoMaster.OpenHelper mHelper;
    protected static DaoSession daoSession;

    public DataBaseManager(DaoMaster.OpenHelper helper) {
        mHelper = helper;
    }

    /**
     * Query for readable DB
     *
     * @throws SQLiteException
     */
    protected void openReadableDB() throws SQLiteException {
        getDaoMaster(mHelper.getReadableDatabase());
        getDaoSession();
    }

    /**
     * Query for writable DB
     *
     * @throws SQLiteException
     */
    protected void openWritableDB() throws SQLiteException {
        getDaoMaster(mHelper.getWritableDatabase());
        getDaoSession();
    }


    /**
     * 初始化DaoMaster
     *
     * @param database SQLiteDatabase
     * @return DaoMaster
     */
    private DaoMaster getDaoMaster(SQLiteDatabase database) {
        return new DaoMaster(database);
    }

    /**
     * 初始化DaoSession
     *
     * @return DaoSession
     */
    private DaoSession getDaoSession() {
        if (daoSession == null)
            daoSession = getDaoMaster(mHelper.getWritableDatabase()).newSession();
        return daoSession;
    }

    @Override
    public void closeDBConnections() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        clearDaoSession();
    }

    @Override
    public void clearDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    @Override
    public boolean dropDatabase(Class<? extends Serializable>... classes) {
        try {
            openWritableDB();
            for (Class<? extends Serializable> clazz : classes) {
                daoSession.deleteAll(clazz);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean insert(@NonNull Entity entity) {
        try {
            if (entity == null) return false;
            openWritableDB();
            getAbstractDao().insert(entity);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(@NonNull Entity entity) {
        try {
            if (entity == null) return false;
            openWritableDB();
            getAbstractDao().insertOrReplace(entity);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean insertList(List<Entity> entities) {
        try {
            openWritableDB();
            getAbstractDao().insertInTx(entities);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplaceList(List<Entity> entities) {
        try {
            openWritableDB();
            getAbstractDao().insertOrReplaceInTx(entities);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(@NonNull Entity entity) {
        try {
            if (entity == null) return false;
            openWritableDB();
            getAbstractDao().delete(entity);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(Key key) {
        try {
            if (TextUtils.isEmpty(key.toString())) return false;
            openWritableDB();
            getAbstractDao().deleteByKey(key);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKeyInTx(Key... keys) {
        try {
            openWritableDB();
            getAbstractDao().deleteByKeyInTx(keys);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteList(List<Entity> entities) {
        try {
            openWritableDB();
            getAbstractDao().deleteInTx(entities);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try {
            openWritableDB();
            getAbstractDao().deleteAll();
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(@NonNull Entity entity) {
        try {
            if (entity == null) return false;
            openWritableDB();
            getAbstractDao().update(entity);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 传入sql语句直接query
     *
     * @param sql
     * @return
     */
    public Cursor rawQuery(String sql) {
        Cursor cursor = null;
        try {
            openWritableDB();
            cursor = daoSession.getDatabase().rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    @Override
    public boolean updateInTx(Entity... entities) {
        try {
            if (entities == null) return false;
            openWritableDB();
            getAbstractDao().updateInTx(entities);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateList(List<Entity> entities) {
        try {
            if (entities == null) return false;
            openWritableDB();
            getAbstractDao().updateInTx(entities);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Entity selectByPrimaryKey(Key key) {
        try {
            openReadableDB();
            return getAbstractDao().load(key);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Entity> loadAll() {
        List<Entity> list = null;
        try {
            openReadableDB();
            list = getAbstractDao().loadAll();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean refresh(Entity entity) {
        try {
            if (entity == null) return false;
            openWritableDB();
            getAbstractDao().refresh(entity);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void runInTx(Runnable runnable) {
        try {
            openReadableDB();
            getDaoSession().runInTx(runnable);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public QueryBuilder<Entity> getQueryBuilder() {
        openReadableDB();
        return getAbstractDao().queryBuilder();
    }

    @Override
    public List<Entity> queryRaw(String where, String... selectionArg) {
        openReadableDB();
        return getAbstractDao().queryRaw(where, selectionArg);
    }

    @Override
    public Query<Entity> queryRawCreate(String where, Object... selectionArg) {
        openReadableDB();
        return getAbstractDao().queryRawCreate(where, selectionArg);
    }

    @Override
    public Query<Entity> queryRawCreateListArg(String where, Collection<Object> selectionArg) {
        openReadableDB();
        return getAbstractDao().queryRawCreateListArgs(where, selectionArg);
    }
}
