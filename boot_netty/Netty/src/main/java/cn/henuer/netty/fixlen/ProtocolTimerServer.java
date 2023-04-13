package cn.henuer.netty.fixlen;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class ProtocolTimerServer {

    public void bind() {
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
                            //使用\n作为分隔符
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                            //这里使用自定义分隔符
                            ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, delimiter));
                            // 添加入站解码器-把字节转为协议报文便于业务逻辑处理
                            ch.pipeline().addLast(new ProtocolMessageDecoder());
                            // 添加出站编码器-把协议报文转为字节便于网络传输
                            ch.pipeline().addLast(new ProtocolMessageEncoder());
                            ch.pipeline().addLast(new ProtocolTimerServerHandler());
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
        new ProtocolTimerServer().bind();
    }
}
class ProtocolTimerServerHandler extends SimpleChannelInboundHandler<ProtocolMessage> {
    private AtomicInteger count=new AtomicInteger(0);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolMessage msg) throws Exception {
        int length=msg.getLength();

        String bodyStr=new String(msg.getContent(),"UTF-8");
        System.out.println("--------------------------------");
        System.out.println("报文长度："+length);
        System.out.println("报文内容："+bodyStr);
        System.out.println("服务器累计接收到的消息包数量="+count.addAndGet(1));
        byte[] body=("我是服务器"+count).getBytes("UTF-8");
        int responseLen=body.length;
        ProtocolMessage responseMsg=new ProtocolMessage();
        responseMsg.setLength(responseLen);
        responseMsg.setContent(body);

        ctx.writeAndFlush(responseMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
