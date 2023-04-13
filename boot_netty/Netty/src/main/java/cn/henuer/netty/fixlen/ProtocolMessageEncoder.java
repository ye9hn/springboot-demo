package cn.henuer.netty.fixlen;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocolMessageEncoder extends MessageToByteEncoder<ProtocolMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolMessage msg, ByteBuf out) throws Exception {
        System.out.println("ProtocolMessageEncoder.encode() 被调用");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
        out.writeBytes(new byte[]{'\r','\n'});
    }
}
