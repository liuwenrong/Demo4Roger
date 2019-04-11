// IDemoAidl.aidl
package aron.demo.IPC;

// Declare any non-default types here with import statements

interface IDemoAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            int sum(int anInt, int anthorInt);
}
