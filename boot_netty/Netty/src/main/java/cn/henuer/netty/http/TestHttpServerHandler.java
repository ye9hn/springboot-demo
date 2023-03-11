package cn.henuer.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 说明
 * 1、SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * 2、HttpObject 客户端和服务端相互通讯的数据被封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * channelRead0 读取客户端数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是不是httprequest的子类
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址" + ctx.channel().remoteAddress());

            HttpRequest httpRequest=(HttpRequest) msg;
            //获取URI，通过URI过滤掉指定的资源
            URI uri=new URI(httpRequest.uri());
            //过滤掉网页图标
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico ，服务器不做响应");
            }

            //回复信息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello 我是服务器", CharsetUtil.UTF_8);

            //构造一个http的响应，即httpresponse
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //将构建好的response返回给客户端
            ctx.writeAndFlush(fullHttpResponse);
        }
    }
}
