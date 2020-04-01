package com.henu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//文件copy
public class MyFileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("d:" + File.separator + "file01.txt");
        FileChannel fileChannel01 = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("d:" + File.separator + "file03.txt");
        FileChannel fileChannel02 = fos.getChannel();
        //这里fis和fos一个buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

       //使用transferFrom完成copy
        fileChannel02.transferFrom(fileChannel01,0,fileChannel01.size());
        fis.close();
        fos.close();
    }
}
