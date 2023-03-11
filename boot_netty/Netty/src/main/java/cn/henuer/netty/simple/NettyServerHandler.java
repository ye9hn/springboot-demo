package cn.henuer.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * 说明
 * 1、我们自定义一个Handler，需要继承netty规定好的某个HandlerAdapter（规范）
 * 2、这时我们自定义一个Handler,才能成为一个Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    //读取数据实际（这里我们可以读取客户端发送了的消息）

    /**
     * 1、ChannelHandlerContext ctx:上下文对象，含有管道pipeline通道channel，地址等
     * 2、Object msg：及时客户端发送的数据， 默认object
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /**
         * 比如这里我们有一个非常耗时长的业务-》异步执行-》提交该channel对应的
         * NioEventLoop的taskQueue中
         *  解决方案1、用户自定义的普通任务并加入到当前eventLoop（就是线程池的一个线程）的taskqueue中
         */
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    //向客户端会写数据
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello 客户端111", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    //向客户端会写数据
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello 客户端222", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * 2、用户自定义定时任务-》该任务提交到scheduletaskQueue中
         */
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    //向客户端会写数据
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello 客户端3333", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },2, TimeUnit.SECONDS);

        //        System.out.println("服务器读取线程："+Thread.currentThread().getName());
//        System.out.println("server ctx=" + ctx);
//
//        System.out.println("channel和pipeline的关系");
//        Channel channel = ctx.channel();
//        //本质是一个双向链表，涉及出栈入栈问题和channel是互相一种包含关系
//        ChannelPipeline pipeline = ctx.pipeline();
//
//        //将msg转换成一个bytebuf
//        //不腰疼不会是netty提供的，不是NIO的ByteBuffer
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址：" + channel.remoteAddress());
   }

        //数据读取完毕
        @Override
        public void channelReadComplete (ChannelHandlerContext ctx) throws Exception {
            //writeAndFlush 是write+flush
            //将数据写入到缓存，并刷新一般讲，我们对整个发送的数据进行编码
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello 客户端", CharsetUtil.UTF_8));
        }

        //处理异常一般是关闭通道
        @Override
        public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
