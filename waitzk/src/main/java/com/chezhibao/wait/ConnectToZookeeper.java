/*
 * FileName: ConnectToZookeeper.java
 * Author:   Arshle
 * Date:     2018年02月27日
 * Description: Zookeeper连接检测
 */
package com.chezhibao.wait;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 〈Zookeeper连接检测〉<br>
 * 〈测试zk连接〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class ConnectToZookeeper{
    /**
     * 主线程控制器
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    /**
     * 创建连接
     * @param connectString 连接地址
     */
    private void connect(String connectString) throws Exception {
        new ZooKeeper(connectString, 10000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("success connected to zookeeper.");
                countDownLatch.countDown();
            }
        });
    }

    public static void main(String[] args) {
        if(args == null || args.length == 0){
            System.exit(1);
        }
        ConnectToZookeeper tool = new ConnectToZookeeper();
        try {
            tool.connect(args[0]);
            System.out.println("start connect to zookeeper...");
            countDownLatch.await(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
