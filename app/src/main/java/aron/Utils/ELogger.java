package aron.Utils;

import android.util.Log;

import com.roger.demo4roger.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * des:
 * 日志管理工具类
 * Created by liuwenrong on 2019/3/26
 */
public final class ELogger {

    //public final static String TAG_FRAGMENT	="Frag";
    //
    //private static final String LOG_FORMAT = "%1$s\n%2$s";
    //private static volatile boolean DISABLE2FILE 	= true;

    private static final String TAG = "iReader";
    /**
     * 系统日志的开关
     */
    public static final boolean ENABLE_SYS_DEBUG = BuildConfig.LOG_DEBUG;

    public static final boolean DEBUG = BuildConfig.LOG_DEBUG;

    /**
     * 书城日志开关
     */
    public static final boolean ENABLE_STORE_DEBUG = BuildConfig.LOG_DEBUG;


    public static void i(String msg) {
        if (!ENABLE_SYS_DEBUG) return;
        try {
            String[] info = getStackInfo();
            Log.i(info[0], msg + info[1]);
        } catch (Exception e) {
        }
    }

    public static void d(String msg) {
        if (!ENABLE_SYS_DEBUG) return;
        try {
            String[] info = getStackInfo();
            Log.d(info[0], msg + info[1]);
        } catch (Exception e) {
        }
    }

    public static void e(String msg) {
        if (!ENABLE_SYS_DEBUG) return;
        try {
            String[] info = getStackInfo();
            Log.e(info[0], msg + info[1]);
        } catch (Exception e) {
        }
    }

    public static void v(String msg) {
        if (ENABLE_SYS_DEBUG) return;
        try {
            String[] info = getStackInfo();
            Log.v(info[0], msg + info[1]);
        } catch (Exception e) {
        }
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (!ENABLE_SYS_DEBUG) return;
        try {
            String[] info = getStackInfo();
            Log.i(tag, msg + info[1]);
        } catch (Exception e) {
        }
    }

    public static void d(String tag, String msg) {
        if (!ENABLE_SYS_DEBUG) return;
        try {
            String[] info = getStackInfo();
            Log.d(tag, msg + info[1]);
        } catch (Exception e) {
        }
    }

    public static void e(String tag, String msg) {
        if (!ENABLE_SYS_DEBUG) return;
        try {
            String[] info = getStackInfo();
            Log.e(tag, msg + info[1]);
        } catch (Exception e) {
        }
    }

    public static void v(String tag, String msg) {
        if (!ENABLE_SYS_DEBUG) return;
        try {
            String[] info = getStackInfo();
            Log.v(tag, msg + info[1]);
        } catch (Exception e) {
        }
    }

    public static void json(Object msg) {
        json(null, msg);
    }

    public static void json(String tag, Object msg) {
        try {
            if (!ENABLE_SYS_DEBUG) return;
            String str = null;
            if (msg instanceof JSONObject) {
                str = ((JSONObject) msg).toString(4);
            } else if (msg instanceof JSONArray) {
                str = ((JSONArray) msg).toString(4);
            } else {
                str = msg == null ? "" : msg.toString();
                if (str.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(str);
                    str = jsonObject.toString(4);
                } else if (str.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(str);
                    str = jsonArray.toString(4);
                }
            }

            String[] info = getStackInfo();

            int maxLength = 3000;

            String tagg = tag == null ? info[0] : tag;
            while (str != null && str.length() > 0) {
                if (str.length() <= maxLength) {
                    Log.i(tagg, str + info[1]);
                    str = null;
                } else {
                    Log.i(tagg, str.substring(0, maxLength));
                    str = str.substring(maxLength);
                }
            }
        } catch (Exception e) {
        }
    }

    private static String[] getStackInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null || stackTrace.length < 5) return new String[]{"LLog", ""};
        int index = 4;
        String className = stackTrace[index].getFileName();
//		String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(")
                .append(className)
                .append(":")
                .append(lineNumber)
                .append(")");

        return new String[]{TAG, stringBuilder.toString()};
    }

}
