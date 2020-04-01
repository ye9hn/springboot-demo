package com.henu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
//fis和fos公用一个buffer
public class MyFileChannel02 {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("d:" + File.separator + "file01.txt");
        FileChannel fileChannel01 = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("d:" + File.separator + "file02.txt");
        FileChannel fileChannel02 = fos.getChannel();
        //这里fis和fos一个buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {//循环读取
            //这里要清空buffer数据，就是重置position等属性
            byteBuffer.clear();
            int read = fileChannel01.read(byteBuffer);
            if (read == -1) {
                break;
            }
            //将buffer中的数据写入到fileChannel02中，也就是利用fos写入到文件中
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
        //关闭流
        fis.close();
        fos.close();
    }
}
