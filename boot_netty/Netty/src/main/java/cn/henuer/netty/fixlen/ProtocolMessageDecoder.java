package cn.henuer.netty.fixlen;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ProtocolMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("ProtocolMessageDecoder.decode() 被调用");
        int length=in.readInt();
        byte[] body=new byte[length];
        in.readBytes(body);
        ProtocolMessage protocolMessage=new ProtocolMessage();
        protocolMessage.setLength(length);
        protocolMessage.setContent(body);

        out.add(protocolMessage);
    }
}
