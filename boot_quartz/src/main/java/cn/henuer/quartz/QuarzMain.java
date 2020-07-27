package cn.henuer.quartz;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class QuarzMain {
    public static void main(String[] args) throws Exception{
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_quartz.xml");
        Thread.sleep(10000);
    }
}
