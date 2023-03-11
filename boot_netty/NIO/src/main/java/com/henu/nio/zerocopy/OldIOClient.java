package com.henu.nio.zerocopy;

import java.io.*;
import java.net.Socket;

public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("localhost",7001);

        String fileName="D:"+ File.separator+"helloworld.txt";
        InputStream inputStream=new FileInputStream(fileName);
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());

        byte[] buffer=new byte[4096];
        long readCount;
        long total=0;
        long startTime=System.currentTimeMillis();
        while ((readCount=inputStream.read(buffer))!=-1){
            total+=readCount;
            dataOutputStream.write(buffer,0,buffer.length);
        }
        System.out.println("发送的总字节数："+total+"总耗时："+(System.currentTimeMillis()-startTime));
        inputStream.close();
        dataOutputStream.close();
        socket.close();
    }
}
