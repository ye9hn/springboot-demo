package co.henu.boot_bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class BIOClient {
    public static void main(String[] args) {
        //Socket socket=null;18122885239
        try {
            for (int i = 0; i < 10; i++) {
                //创建一个客户端对象Socket
                Socket socket = new Socket("127.0.0.1", 5078);
                //使用socket对象中的方法获取getOutPutStream()获取网络字节输出流对象OutPutStream
                OutputStream os = socket.getOutputStream();
                //使用网络字节输出流，给服务器发送数据
                os.write("68 01 00 15 00 00 00 00 00 30 00 01 00 00 00 00 00 30 00 DF 16".replaceAll(" ","").getBytes());
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //使用socket对象中的方法getInputStream()获取网络字节输出流InputStream
//            InputStream is=socket.getInputStream();
//            byte bytes[]=new byte[1024];
//            int len=0;
//            while((len=is.read(bytes))!=-1){
//                System.out.println(new String(bytes,0,len));
//            }
        } catch (Exception e) {
            e.getStackTrace();
        }finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
