package cn.henuer.quartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * 简单Job
 */
public class BaseQuartz implements Job {
    public BaseQuartz() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //创建工作详情
        JobDetail jobDetail = context.getJobDetail();
        //获取工作的名称
        String name = jobDetail.getKey().getName();
        String group = jobDetail.getKey().getGroup();
        String job = jobDetail.getJobDataMap().getString("data");
        System.out.println("job执行，job名：" + name + "group: " + group + "data: " + job + "  " + new Date());
    }
}
