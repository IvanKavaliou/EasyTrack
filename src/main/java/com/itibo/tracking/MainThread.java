package com.itibo.tracking;


public class MainThread extends Thread {

    ThreadGroup group = null;

    MainThread(ThreadGroup group){
        this.group = group;
    }

    public void st() throws InterruptedException {
        int t = 0;
        while (group.activeCount()>0){
            if(t!=10){
                t++;
                System.out.println("===== "+t);
                Thread.sleep(1000);
            }else {
                System.out.println("stop");
                group.interrupt();
                break;
            }
        }
    }
    }

