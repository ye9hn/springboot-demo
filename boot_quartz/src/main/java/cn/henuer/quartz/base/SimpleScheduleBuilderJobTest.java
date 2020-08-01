package cn.henuer.quartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class SimpleScheduleBuilderJobTest extends BaseQuartz {
    public static void main(String[] args) {
        //创建scheduler ,调度器 核心组件
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            //定义一个trigger ，触发条件类
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
            // 创建触发器 ，执行次固定
//            Trigger trigger = triggerBuilder.withIdentity("trigger1", "group1")
//                    .startNow()//加入生效
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                            .withIntervalInSeconds(2).withRepeatCount(1))
//                    .build();
            // 创建触发器 永久执行
            Trigger trigger = triggerBuilder.withIdentity("trigger1", "group1")
                    .startNow()//加入开始生效
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(2).repeatForever())
                    //.endAt() //结束时间
                    .build();
            //创建JobDetail ，JobBuilder
            JobDetail jobDetail = JobBuilder.newJob(BaseQuartz.class)
                    .withIdentity("job04", "group")
                    .usingJobData("data", "hello world")
                    .build();
            //注册JobDetail和trigger
            scheduler.scheduleJob(jobDetail, trigger);
            //启动调度器，内部注册的所有触发器开始计时
            scheduler.start();
//            Thread.sleep(3000);
//            scheduler.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
