package priv.lst.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.Executors;

/**
 * Created by lishutao on 2019-09-20.
 *
 * @author lishutao
 * @date 2019-09-20
 */
//@Configuration
@Component
//所有的定时任务都放在一个线程池中，定时任务启动时使用不同都线程。
public class ScheduleConfig implements SchedulingConfigurer, Serializable {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//创建一个线程池调度器
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //设置线程池容量
        scheduler.setPoolSize(20);
        //线程名前缀
        scheduler.setThreadNamePrefix("task-");
        //等待时常
        scheduler.setAwaitTerminationSeconds(60);
        //当调度器shutdown被调用时等待当前被调度的任务完成
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        //设置当任务被取消的同时从当前调度器移除的策略
        scheduler.setRemoveOnCancelPolicy(true);
        //设置任务注册器的调度器
        taskRegistrar.setTaskScheduler(scheduler);
//        设定一个长度10的定时任务线程池
//        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }

    public static final String x = "xxx";
}