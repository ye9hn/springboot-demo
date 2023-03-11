package com.henu.nio.zerocopy;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address=new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        ServerSocket serverSocket=serverSocketChannel.socket();
        serverSocket.bind(address);
        //创建buffer
        ByteBuffer byteBuffer=ByteBuffer.allocate(4096);
        while (true){
           SocketChannel socketChannel=serverSocketChannel.accept();
           int readCount=0;
           while (readCount!=-1){
               readCount=socketChannel.read(byteBuffer);
               byteBuffer.rewind();//倒带 position=0 mark作废
           }
        }

    }
}
