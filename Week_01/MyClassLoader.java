package com.test.classload.demo;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName: MyClassLoader
 * @Description: TODO
 * @Author zhaiwei
 * @Date 2020/10/21 19:22
 * @Version 1.0
 */
public class MyClassLoader extends ClassLoader{

    private static final String HELLO_CLASS_FILE = "./Hello.xlass";
    private static final String HELLO_CLASS_NAME = "Hello";
    private static final String HELLO_CLASS_METHOD = "hello";


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        final Class<?> aClass = new MyClassLoader().findClass(HELLO_CLASS_NAME);
        final Object obj = aClass.newInstance();

        Method method = null;
        try {
            method = aClass.getMethod(HELLO_CLASS_METHOD);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(obj);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {


        try {
            byte[] fileContent = getFileContent(HELLO_CLASS_FILE);
            return defineClass(name,fileContent,0,fileContent.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);

    }




    /**
     * 读取文件到Byte数组
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] getFileContent(String filePath) throws IOException {



        int buf_size = 1024;
        byte[] bytes = new byte[buf_size];
        final File file = new File(filePath);

        final ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
        BufferedInputStream in = null;

        try {
            in = new BufferedInputStream(new FileInputStream(file));

            int len = 0;
            while (-1 != (len = in.read())) {
                len = 255 -len;
                bos.write(len);
            }
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }
}
