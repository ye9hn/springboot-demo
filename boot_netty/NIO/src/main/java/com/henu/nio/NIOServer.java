package com.henu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NIO Server
 *
 *     FileChannel
 *          Java NIO FileChannel是一类文件相连的channel，通过它可以从文件读取数据，或向文件写数据。
 *          FileChannel类是Java NIO类库提供的用于代替标准Java IO API来读写文件。FileChannel不能设置为非阻塞模式，只能工作在阻塞模式下
 *
 * 　　SocketChannel
 *          Java NIO SocketChannel用于和TCP网络socket相连，等同于Java网络包中的Socket。可以通过两种方式来创建一个SocketChannel：
 *              打开一个SocketChannel并且将其和网络上的某台服务器相连；
 *              当有一个连接到达一个ServerSocketChannel时会自动创建一个SocketChannel
 * 　　ServerSocketChannel
 *          Java NIO ServerSocketChannel可以监听TCP连接，就像标准Java网络库中的ServerSocket，工作在非阻塞模式下
 * 　　DatagramChannel
 *          DatagramChannel用于发送和接收UDP包。因为UDP是一个无连接协议，不是像其他channel一样进行读写操作，而是通过数据包来交换数据。
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        ConcurrentHashMap<String, SocketChannel> socketChannelMap = new ConcurrentHashMap<>();
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
        //循环等待客户端连接，JavaNIO会出现空循环
        while (true) {
            if (selector.select(5000) == 0) {//阻塞在这里5秒钟没有连接就返回继续等待,不再阻塞在这里
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
                    socketChannelMap.put(socketChannel.hashCode() + "", socketChannel);
                    //将当前的SocketChannel注册到selector,关注事件OP_READ，同时给SocketChannel绑定一个buffer
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    socketChannel.register(selector, SelectionKey.OP_READ, buffer);

                }
                if (key.isReadable()) {//发生OP_READ
                    //通过key 反向获取到对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    /**
                     * nio的客户端如果关闭了，服务端还是会收到该channel的读事件
                     * 但是数目为0，而且会读到-1，其实-1在网络io中就是socket关闭的含义，
                     * 在文件时末尾的含义，所以为了避免客户端关闭服务端一直收到读事件，
                     * 必须检测上一次的读是不是-1，如果是-1，就关闭这个channel。
                     */
                    try {
                        channel.read(buffer);
                        System.out.println("from 客户端" + new String(buffer.array()).trim());
                        buffer.clear();
                        buffer = ByteBuffer.wrap("收到，谢谢".getBytes("UTF-8"));
                        channel.write(buffer);
                    } catch (IOException exception) {
                        try {
                            System.out.println("客户端channel" + channel.getRemoteAddress() + "已关闭");
                            socketChannelMap.remove(channel);
                            key.cancel();//取消注册
                            channel.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                }
                //手动从集合中移动当前的selectionKey，防止重复操作
                keyIterator.remove();
            }// while (keyIterator.hasNext())
        }// while (true)
    }
}
