package com.henu.nio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * NIO Client server在MyFileChannel06
 */
public class NIOClient {
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch=new CountDownLatch(1000);
        for (int i = 0; i < 1; i++) {
            new Thread(()->{
                try{
                    //得到一个网络通道
                    SocketChannel socketChannel = SocketChannel.open();
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //设置服务器的IP和端口
                    InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
                    //连接服务器
                    if (!socketChannel.connect(inetSocketAddress)) {
                        while (!socketChannel.finishConnect()) {
                            System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
                        }
                    }
                    while(true){
                        //如果连接成功，就发送数据
                        String str = "hello";
                        //ByteBuffer.allocate(1024);和下面方法一样效果
                        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());//这个方法的意思是将字节数组按照数组大小分配空间，不用在设置容量，避免容量不足或者是浪费空间
                        socketChannel.write(byteBuffer);
                        Thread.sleep(2000);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            },"thread--"+i).start();
        }
        countDownLatch.await();

        System.in.read();
    }
}
