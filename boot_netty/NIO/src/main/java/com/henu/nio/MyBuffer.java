package com.henu.nio;

import java.nio.IntBuffer;

public class MyBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 3);
        }
        //Buffer四大重要属性，缓存区Buffer就是一个字节数组
        // Invariants: mark <= position <= limit <= capacity
//        private int mark = -1;
//        private int position = 0;
//        private int limit;
//        private int capacity;
        intBuffer.flip();
        intBuffer.position(2);//将position位置置为3
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
