package com.henu.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OldIOServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket=new ServerSocket(7001);

        while (true){
            Socket socket=serverSocket.accept();
            DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());

            byte[] byteArray=new byte[4096];

            while (true){
                int readCount=dataInputStream.read(byteArray,0,byteArray.length);
                if (readCount==-1){
                    break;
                }
            }
        }
    }
}
