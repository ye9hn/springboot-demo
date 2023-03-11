package cn.henuer.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
        ServerBootstrap serverBootstrap=new ServerBootstrap();

        serverBootstrap.group(bossGroup,workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));//给bossgroup添加log日志
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline=ch.pipeline();

                //因为基于http协议，使用http的编码解码器
                pipeline.addLast(new HttpServerCodec());
                //是以块方式写，添加ChunkedWriteHandler处理器
                pipeline.addLast(new ChunkedWriteHandler());
                /**
                 * 说明：
                 * 1、http数据在传输过程中是分段，HttpObjectAggregateor，就可以将多个段合并
                 * 2、这就是为什么，但浏览器发送大量数据是，就会发出多次请求
                 */
                pipeline.addLast(new HttpObjectAggregator(8192));

                /**
                 * 说明
                 * 1、对应websocket，他的数据实一帧（frame）形式传递
                 * 2、可以看到WebSocketFrame下面为6个子类
                 * 3、浏览器请求时ws://localhost:7000/hello 表示请求的URL
                 * 4、WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议
                 */
                pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                //自定义的Handler，处理业务逻辑
                pipeline.addLast(new MyTextWebSocketFrameHandler());
            }
        });
        ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
        channelFuture.channel().closeFuture().sync();
    }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
