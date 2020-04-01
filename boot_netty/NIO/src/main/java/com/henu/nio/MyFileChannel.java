package com.henu.nio;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
//使用两个buffer实现文件读写
public class MyFileChannel {
    public static void main(String[] args) throws Exception {
        String str = "hello,henuer";
        //创建一个输出流->channel
        FileOutputStream fos = new FileOutputStream("d:" + File.separator + "file01.txt",true);
        //通过输出流获取一个FileChannel,包装java输出流,这里借助fos获取一个通道，而不自己创建通道
        FileChannel fileOutChannel = fos.getChannel();
        //创建一个缓存区 byteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str放入byteBuffer
        byteBuffer.put(str.getBytes());
        //对byteBUffer进行反转，让position指向第一个位置
        byteBuffer.flip();

        //将bytebuffer数据写入到filechannel
        fileOutChannel.write(byteBuffer);
        fos.close();

        File file = new File("d:" + File.separator + "file01.txt");
        //将文件通过输入流打印出来
        FileInputStream fis=new FileInputStream(file);
        //通过fis一个对应的channel
        FileChannel fileInChannel=fis.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer1=ByteBuffer.allocate((int)file.length());
        //将通道数据读到缓冲区
        fileInChannel.read(byteBuffer1);
        //将buffer数据输出到控制台
        System.out.println(new String(byteBuffer1.array()));
        fis.close();
    }
}
