package com.rubbish;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.rubbish.utils.CrashHandler;

import org.xutils.x;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by iRichard on 2017-12-26.
 */
public class MyApp extends MultiDexApplication {
    private static final String TAG = "MyApp";
    public static String PROCESS_NAME_XXXX = "process_name_xxxx";
    private static MyApp mInstance;
    public static Context mContext;
    public SharedPreferences trackConf = null;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;
    /**
     * 轨迹服务ID
     */
    public long serviceId = 218243;

    /**
     * Entity标识
     */
    public String entityName = "myTrace";
    /**
     * 轨迹客户端
     */
    public boolean isRegisterReceiver = false;

    /**
     * 服务是否开启标识
     */
    public boolean isTraceStarted = false;

    /**
     * 采集是否开启标识
     */
    public boolean isGatherStarted = false;
    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    private Notification notification = null;
    /**
     * 轨迹服务
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //其它初始化日志方法：
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        mContext = getApplicationContext();
        mInstance = this;
        initScreenSize();


//        new ImportDB(this).copyDatabase();
    }

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
