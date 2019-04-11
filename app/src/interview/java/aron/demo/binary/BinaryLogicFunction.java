package aron.demo.binary;

import aron.Utils.ELogger;

/**
 * des: 进制逻辑函数 与或非操作
 * Created by liuwenrong on 2019/3/26
 */
public class BinaryLogicFunction {
    private static final String TAG = "LogicFunction";
    static int EIGHT = 017; // 8进制 转成10进制则是15
    public static int MASK = 0x13; // 16进制 转成10进制则是19
    public static int MODE = 0x3 << 30; // 左移30位,即添加30个0
    public static void test () {

        ELogger.d("test--   --------liuwenrong--2019/3/26--: eight 八进制 = " + EIGHT);
        ELogger.d(TAG, "test: MASK = " + MASK + " , Mode = " + Integer.toBinaryString(MODE));
    }
}
