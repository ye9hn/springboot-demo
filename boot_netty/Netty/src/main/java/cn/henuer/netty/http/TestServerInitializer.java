package cn.henuer.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline=ch.pipeline();
        //加入以一个netty 提供的httpserverCodec codec->[coder-decoder]
        // httpserverCodec 说明
        //1、httpservercodec是netty提供的处理http的编解码器
        pipeline.addLast("myHttpServerCodec",new HttpServerCodec());
        //2、增加一个自定义的Handler
        pipeline.addLast("myTestHttpServerHandler",new TestHttpServerHandler());
    }
}
