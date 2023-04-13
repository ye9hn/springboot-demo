package cn.henuer.netty.line.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.concurrent.atomic.AtomicInteger;

public class LineBasedFramerDecoderTimerServer {public void bind() {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new LineBasedFramerDecoderTimerServerHandler());
                    }
                });
        ChannelFuture f = serverBootstrap.bind(9001).sync();

        f.channel().closeFuture().sync();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}

    public static void main(String[] args) {
        new LineBasedFramerDecoderTimerServer().bind();
    }

    public class LineBasedFramerDecoderTimerServerHandler extends ChannelInboundHandlerAdapter {
        private AtomicInteger counter = new AtomicInteger(0);

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String body = (String) msg;
            System.out.println("The time server receive order : " + body + ",counter is:" + counter.addAndGet(1));

            String currentTime = "Query time order".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
            //currentTime=currentTime+System.getProperty("line.separator");
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());

            ctx.writeAndFlush(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
