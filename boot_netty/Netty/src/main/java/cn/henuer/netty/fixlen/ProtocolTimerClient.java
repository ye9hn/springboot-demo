package cn.henuer.netty.fixlen;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class ProtocolTimerClient {
    public void connect(){
        EventLoopGroup group=new NioEventLoopGroup();

        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //使用\n作为分隔符
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                            //这里使用自定义分隔符
                            ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, delimiter));
                            ch.pipeline().addLast(new ProtocolMessageEncoder());
                            ch.pipeline().addLast(new ProtocolMessageDecoder());

                            ch.pipeline().addLast(new ProtocolTimerClientHandler());
                        }
                    });

            ChannelFuture f=bootstrap.connect("127.0.0.1",9001).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ProtocolTimerClient().connect();
    }

}

class ProtocolTimerClientHandler extends SimpleChannelInboundHandler<ProtocolMessage> {

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolMessage msg) throws Exception {
        //读取服务器响应的报文
        int length = msg.getLength();
        byte[] body = msg.getContent();
        System.out.println("--------------------");
        System.out.println("报文长度：" + length);
        System.out.println("报文体：" + new String(body, "UTF-8"));
        System.out.println("累计收到的消息数量：" + count.addAndGet(1));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送10条数据到服务器
        for (int i = 1; i <= 10; i++) {
            byte[] body = ("你好服务器，我是客户端张三" + i).getBytes("UTF-8");
            // 创建协议包对象
            ProtocolMessage message = new ProtocolMessage();
            message.setContent(body);
            message.setLength(body.length);
            // 发送
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
