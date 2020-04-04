package com.henu.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO Server
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        //创建ServerSocketChannel---》SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个selector对象
        Selector selector = Selector.open();
        //绑定一个端口，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞,这个很有必要，不设置会出现异常
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel注册到selector，关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true) {
            if (selector.select(5000) == 0) {//阻塞在这里1秒钟没有连接就返回继续等待,不再阻塞在这里
                System.out.println("服务器等待几秒，无连接");
                continue;//返回
            }

            //如果返回的>0,就获取到相关的SelectionKey集合
            //1、如果返回值>0，表示已经获取到关注的事件
            //2、selector.selectedKeys()返回关注事件的集合
            //通过selectedKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                //获取到selectedKeys()
                SelectionKey key = keyIterator.next();
                //根据key，对应的通道发生的事件做相应的处理
                if (key.isAcceptable()) {
                    //该客户端生成一个SockChannel
                    //正常情况下 serverSocketChannel.accept();会一直等待连接来，但是在这里由于key.isAcceptable()如果为true说明，
                    // 已经有客户端连接并且套接字准备OK了，所以这里不会阻塞了
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功，生成了一个SocketChannel" + socketChannel.hashCode());

                    //将当前的SocketChannel注册到selector,关注事件OP_READ，同时给SocketChannel绑定一个buffer
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    socketChannel.register(selector, SelectionKey.OP_READ, buffer);

                }
                if (key.isReadable()) {//发生OP_READ
                    //通过key 反向获取到对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    System.out.println(channel.read(buffer));
                    System.out.println("from 客户端" + new String(buffer.array()));
                    buffer.clear();
                    buffer = ByteBuffer.wrap("收到，谢谢".getBytes("UTF-8"));
                    channel.write(buffer);
                }

                //手动从集合中移动当前的selectionKey，防止重复操作
                keyIterator.remove();
            }// while (keyIterator.hasNext())
        }// while (true)
    }
}
