package com.henu.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * scattering:将数据写入到buffer时，可以采用buffer数组，分散依次写入
 * Gathering：从buffer读取数据，可以采用buffer数组依次读入数据
 */
public class MyFileChannel05 {
    public static void main(String[] args) throws Exception {


        //从ServerSocketChannel和SocketChannel,不使用FileChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等客户端连接（telnet）,在这里阻塞等待读取数据
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength=8;
        while (true) {
            int byteRead = 0;
            while (byteRead<messageLength){
                long l=socketChannel.read(byteBuffers);
                byteRead+=l;
                System.out.println("byteRead="+byteRead);
                //使用流打印，看看当前的buffer的position和limit
                Arrays.asList(byteBuffers).stream()
                        .map(buffer->"position= "+buffer.position()+" ,limit= "+buffer.limit())
                        .forEach(System.out::println);
            }
            //将所有的buffer进行flip
            Arrays.asList(byteBuffers).forEach(buffer ->buffer.flip());
            //将数据督促显示到客户端
            long byteWrite=0;
            while (byteWrite<messageLength){
               long l= socketChannel.write(byteBuffers);
               byteWrite+=l;
            }
            //将所有的buffer进行clear
            Arrays.asList(byteBuffers).forEach(buffer->{
                buffer.clear();
            });
            System.out.println("byteRead:= "+byteRead+"byteWrite= "+byteWrite+" ,messageLength"+messageLength);
        }//while


    }
}
