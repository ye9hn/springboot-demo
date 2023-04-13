package cn.henuer.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HeartBeatServer {

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入一个netty 提供 IdleStateHandler
                            /*
                             *当 IdleStateEvent 触发后 , 就会传递给管道 的下一个handler去处理
                             *通过调用(触发)下一个handler 的 userEventTiggered , 在该方法中去处理 IdleStateEvent(读空闲，写空闲，读写空闲)
                             */
                            pipeline.addLast(new IdleStateHandler(7000, 7000, 10, TimeUnit.SECONDS));

                            pipeline.addLast(new HeartBeatServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(9001).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
    AtomicInteger readIdleTimes = new AtomicInteger(0);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        String eventType = null;

        switch (event.state()) {
            case ALL_IDLE:
                eventType = "读写空闲";
                readIdleTimes.incrementAndGet();
                break;
            case READER_IDLE:
                eventType = "读空闲";
                readIdleTimes.incrementAndGet();
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                break;
        }
        System.out.println(ctx.channel().remoteAddress() + "超时事件" + eventType);
        if (readIdleTimes.get() > 3) {
            System.out.println("[server]读空闲超过3次，关闭连接，释放更多资源");
            ctx.channel().writeAndFlush("idle close");
            ctx.channel().close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("=== " + ctx.channel().remoteAddress() + " is active ===");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("=====>[server] message received:" +msg);

    }
}