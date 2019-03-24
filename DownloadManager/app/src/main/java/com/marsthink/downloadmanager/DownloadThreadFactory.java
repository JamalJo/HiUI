package com.marsthink.downloadmanager;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhoumao on 2019/3/23.
 * Description:
 */
public class DownloadThreadFactory implements ThreadFactory {

    private static final AtomicInteger mPoolNumber = new AtomicInteger(1);//原子类，线程池编号
    private final String mName;//为每个创建的线程添加的前缀

    public DownloadThreadFactory() {
        mName = "pool-" + mPoolNumber.getAndIncrement();
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);//真正创建线程的地方，设置了线程的线程组及线程名
        t.setName(mName);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {  //默认是正常优先级
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
