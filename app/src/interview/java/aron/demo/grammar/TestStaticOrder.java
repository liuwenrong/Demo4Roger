package aron.demo.grammar;

/**
 * des: 测试Static语句的语法顺序
1. static 语句块1
download = 5
book = 0
download = 7
book = 66
1.1 static 语句块2
2. Test2 static语句块
3. 语句块1
3.1 语句块2
4. 构造方法
5. Test2 语句块
6. Test2 构造
download = 7, book = 50
 * Created by liuwenrong on 2019/4/24
 */
public class TestStaticOrder {
    public static int page_count_download = 5;

    static {
//        ELogger.d("static initializer----------liuwenrong--2019/4/24--: static 语句块");
        System.out.println("1. static 语句块1");
        build10();
    }
    static {
        System.out.println("1.1 static 语句块2");
    }
    {
        System.out.println("3. 语句块1");
    }
    {
        System.out.println("3.1 语句块2");
    }

    public TestStaticOrder() {
        System.out.println("4. 构造方法");
    }
    static int page_count_book = 50;
    static void build10() {
        System.out.println("download = " + page_count_download);
        System.out.println("book = " + page_count_book);
//        ELogger.d("build10----------liuwenrong--2019/4/24--: page_count_download = " + page_count_download);
//        ELogger.d("build10----------liuwenrong--2019/4/24--: page_count_book = " + page_count_book);
        page_count_download = 7;
        page_count_book = 66;
        System.out.println("download = " + page_count_download);
        System.out.println("book = " + page_count_book);
//        ELogger.d("build10----------liuwenrong--2019/4/24--: page_count_download = " + page_count_download);
//        ELogger.d("build10----------liuwenrong--2019/4/24--: page_count_book = " + page_count_book);
    }
    public static void main(String[] args) {
        TestStaticOrder t = new Test2();
        System.out.println("download = " + page_count_download + ", book = " + page_count_book);
    }

}

class Test2 extends TestStaticOrder {
    static {
        System.out.println("2. Test2 static语句块");
    }
    {
        System.out.println("5. Test2 语句块");
    }
    public Test2() {
        System.out.println("6. Test2 构造");
    }
}
