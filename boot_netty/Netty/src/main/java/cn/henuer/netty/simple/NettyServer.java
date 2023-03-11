package cn.henuer.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        /**创建BossGroup和WorkerGroup
         * 说明
         * 1、创建两个线程组bossGroup和workerGroup
         * 2、bossgroup负责处理连接请求，真正的业务端处理会交给workerGroup
         * 3、这两个都是无限循环
         * 4、bossgroup和workerGroup含有的子线程（NioEventLoop）个数
         * 默认实际CPU核数*2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            //使用链式编程来进行设置
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NIOServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象（匿名对象）
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //向管道增加一个处理器Handler
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });//给我们的workerGroup的eventLoop对象的管道设置处理器

            System.out.println("....服务器 is ready....");
            //绑定一个端口并且同步，生成了一个ChannelFuture对象
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //给cf注册监听器，监听绑定端口是否成功，这里是future-listener机制，用于监听异步操作，不会造成阻塞
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()){
                        System.out.println("服务器端口 6668 监听成功");
                    }else {
                        System.out.println("服务器端口 6668 监听失败");
                    }
                }
            });

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
