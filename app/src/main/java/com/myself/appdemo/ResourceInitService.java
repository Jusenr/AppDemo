package com.myself.appdemo;

import android.app.IntentService;
import android.content.Intent;

import com.myself.appdemo.utils.DistrictUtils;
import com.myself.mylibrary.BasicApplication;
import com.myself.mylibrary.util.FileUtils;
import com.myself.mylibrary.util.Logger;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化地区和表情资源
 * Created by riven_chris on 16/6/3.
 */
public class ResourceInitService extends IntentService {

    public static final String MAP_EMOJI = "map_emoji";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ResourceInitService(String name) {
        super(name);
    }

    public ResourceInitService() {
        super("ResourceInitService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            DistrictUtils.insertRegion();
            File setFile = new File(BasicApplication.sdCardPath + File.separator + "patch/biaoqing/set.txt");
            if (!setFile.exists() || !new File(BasicApplication.sdCardPath + File.separator + "patch/biaoqing/001.png").exists())
                FileUtils.unZipInAsset(getApplicationContext(), "patch_10002_10003.zip", "patch", true);
            DistrictUtils.insertRegion();

            ConcurrentHashMap<String, String> mMap = parseEmoji();
            Logger.d("表情包设置完成");
            TotalApplication.getDiskFileCacheHelper().put(MAP_EMOJI, mMap);
            TotalApplication.setEmojis(mMap);
        } catch (Exception e) {
            Logger.e("初始数据异常----" + e);
            e.printStackTrace();
        }
    }

    /**
     * 解析表情
     *
     * @return
     */
    private ConcurrentHashMap<String, String> parseEmoji() {
        ConcurrentHashMap<String, String> result = new ConcurrentHashMap<>();
        File emoji = new File(TotalApplication.resourcePath + File.separator + "biaoqing", "set.txt");
        String source = FileUtils.readFile(emoji).replace("\uFEFF", "");
//        Logger.d(source);
        String[] sources = source.split("\\n");
        for (String s : sources) {
            String[] s1 = s.split(",");
            result.put(s1[0], TotalApplication.resourcePath + File.separator + "biaoqing" + File.separator + s1[1]);
        }
        return result;
    }
}
