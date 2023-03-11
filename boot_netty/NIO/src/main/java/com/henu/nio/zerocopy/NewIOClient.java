package com.henu.nio.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String fileName="D:"+ File.separator+"helloworld.txt";
        //得到一个文件channel
        FileChannel fileChannel=new FileInputStream(fileName).getChannel();
        //准备发送
        long startTime=System.currentTimeMillis();
        //transferTo()在Linux环境文件一次就能传输完成，在windows环境下一次只能传输8m大文件需要多次传输
        long transferCount=fileChannel.transferTo(0,fileChannel.size(),socketChannel);
        System.out.println("发送的总字节数："+transferCount+"耗时："+(System.currentTimeMillis()-startTime));

        fileChannel.close();
    }
}
