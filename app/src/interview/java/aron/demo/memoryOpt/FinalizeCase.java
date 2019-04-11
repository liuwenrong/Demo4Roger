package aron.demo.memoryOpt;


import aron.Utils.ELogger;

/**
 * des:
 * Created by liuwenrong on 2019/4/8
 */
public class FinalizeCase {

    private static Block holder = null;

    public static void testGc(String[] args) throws Exception {
        holder = new Block();
        holder = null;
        Runtime.getRuntime().gc();//会调用 GC_EXPLICIT
//        System.gc();
        //System.in.read();
    }

    static class Block {

        public byte[] mBytes;

        public Block() {
            long maxMemory = Runtime.getRuntime().maxMemory();
            ELogger.d("onCreate----------liuwenrong--2019/4/8--: 系统Heap分配的maxMemory = " + maxMemory);
//        byte[] _200M = new byte[200 * 1024 * 1024];
            mBytes = new byte[(int) (maxMemory / 2)];
        }
        /** 实现后 测试还能正常回收 不管是否请求了gc holder=null后就会调用 */
        @Override
        protected void finalize() throws Throwable {
            System.out.println("invoke finalize  Thread.currentThread() = " + Thread.currentThread().getName());

            if (mBytes != null) {
                // log中引用了 导致不能回收
//                ELogger.d("finalize----------liuwenrong--2019/4/8--: mBytes.size = " + mBytes.length);
                mBytes = null;
                /**  I/dalvikvm-heap: Grow heap (frag case) to 34.184MB for 33554448-byte allocation
                     D/dalvikvm: GC_FOR_ALLOC freed <1K, 1% free 34985K/35320K, paused 14ms, total 14ms
                     I/System.out: invoke finalize
                     D/dalvikvm: GC_EXPLICIT freed 32768K, 12% free 2216K/2516K, paused 2ms+3ms, total 27ms */
            }
            Thread.sleep(11000); // 如果拥有finalize的object超过10s（MIUI改成了60s）没有被回收，则发生crash
        }
    }
}
