package cn.henuer.netty.fixlen;

public class ProtocolMessage {
    private int length;
    private byte[] content;

    public ProtocolMessage() {
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
