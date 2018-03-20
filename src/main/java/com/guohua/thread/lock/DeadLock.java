package com.guohua.thread.lock;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 死锁
 * <p>
 * https://mp.weixin.qq.com/s/BVGtDDCa7yjtfJJPNKOC_g
 */
public class DeadLock {

    public static void main(String[] args) {
        deadLockNetworkConnection();
    }

    /**
     * 线程死锁
     */
    private static void deadLockThread() {
        final Object a = new Object();
        final Object b = new Object();
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                synchronized (a) {
                    try {
                        System.out.println("now i in threadA-locka");
                        Thread.sleep(1000l);
                        synchronized (b) {
                            System.out.println("now i in threadA-lockb");
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            public void run() {
                synchronized (b) {
                    try {
                        System.out.println("now i in threadB-lockb");
                        Thread.sleep(1000l);
                        synchronized (a) {
                            System.out.println("now i in threadB-locka");
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        });

        threadA.start();
        threadB.start();
    }

    /**
     * 线程池死锁
     */
    private static void deadLockPool() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Long> f1 = executorService.submit(new Callable<Long>() {
            public Long call() throws Exception {
                System.out.println("start f1");
                Thread.sleep(1000);//延时
                Future<Long> f2 = executorService.submit(new Callable<Long>() {
                    public Long call() throws Exception {
                        System.out.println("start f2");
                        return -1L;
                    }
                });
                System.out.println("result" + f2.get());
                System.out.println("end f1");
                return -1L;
            }
        });
    }

    /**
     * 网络连接死锁
     */
    private static void deadLockNetworkConnection(){
        // 连接1
        final MultiThreadedHttpConnectionManager connectionManager1 = new MultiThreadedHttpConnectionManager();
        final HttpClient httpClient1 = new HttpClient(connectionManager1);
        httpClient1.getHttpConnectionManager().getParams().setMaxTotalConnections(1);  //设置整个连接池最大连接数
        // 连接2
        final MultiThreadedHttpConnectionManager connectionManager2 = new MultiThreadedHttpConnectionManager();
        final HttpClient httpClient2 = new HttpClient(connectionManager2);
        httpClient2.getHttpConnectionManager().getParams().setMaxTotalConnections(1);  //设置整个连接池最大连接数
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    PostMethod httpost = new PostMethod("http://www.baidu.com");
                    System.out.println(">>>> Thread A execute 1 >>>>");
                    httpClient1.executeMethod(httpost);
                    Thread.sleep(5000l);
                    System.out.println(">>>> Thread A execute 2 >>>>");
                    httpClient2.executeMethod(httpost);
                    System.out.println(">>>> End Thread A>>>>");
                } catch (Exception e) {
                    // ignore
                }
            }
        });
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    PostMethod httpost = new PostMethod("http://www.baidu.com");
                    System.out.println(">>>> Thread B execute 2 >>>>");
                    httpClient2.executeMethod(httpost);
                    Thread.sleep(5000l);
                    System.out.println(">>>> Thread B execute 1 >>>>");
                    httpClient1.executeMethod(httpost);
                    System.out.println(">>>> End Thread B>>>>");
                } catch (Exception e) {
                    // ignore
                }
            }
        });
    }
}