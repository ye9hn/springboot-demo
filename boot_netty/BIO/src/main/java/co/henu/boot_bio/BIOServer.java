package co.henu.boot_bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BIOServer {
    public static void main(String[] args) throws Exception {
        //创建一个线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10, //核心线程池大小
                15, //最大线程池大小
                10L, //线程池中超过corePoolSize数目的空闲线程最大存活时间；可以allowCoreThreadTimeOut()使得核心线程邮箱时间
                TimeUnit.SECONDS,//keepAliveTime时间单位
                new LinkedBlockingDeque<Runnable>(1000),//阻塞任务队列
                new ThreadPoolExecutor.AbortPolicy());//当提交任务数超过maxMumPoolSize+workQueue之和时，任务会交给RejectExecutionHandler来处理
//
        ServerSocket serverSocket = new ServerSocket(5078);

        System.out.println("服务器启动了");

        while (true) {
            //监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            //创建一个线程，与之通信
            executor.execute(() -> {
                //可以和客户端通讯了
                handler(socket);
            });
        }
    }

    //客户端通讯方法
    public static void handler(Socket socket) {
        try {

            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
        //循环读取客户端发送的数据
            byte[] bytes = new byte[1024];
            int len;
            while ((len=inputStream.read(bytes))!=-1){
                System.out.println(Thread.currentThread().getName()+":  "+new String(bytes,0,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭客户端");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
