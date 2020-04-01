package com.henu.nio;


import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

//文件MappedByteBuffer

/**
 * 直接让文件在内存（堆外内存）中修改数据
 */
public class MyFileChannel04 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile accessFile =
                new RandomAccessFile("d:" + File.separator + "file01.txt", "rw");

//获取到对应的FileChannel
        FileChannel channel=accessFile.getChannel();
        /**
         * 参数1：FileChannel.MapMode.READ_WRITE
         * 参数2：0：可以直接修改的起始位置
         * 参数3：5是映射到内存的大小，既将文件的多少个字节映射到内存，就是直接修改的内存范围0---5
         */
        MappedByteBuffer mappedByteBuffer=channel.map(FileChannel.MapMode.READ_WRITE,0,5);
        mappedByteBuffer.put(0,(byte)'h');
        mappedByteBuffer.put(3,(byte) '9');
        accessFile.close();
    }
}
