package com.kodcu;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: usta
 * Date: 08.09.2013
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class Gorev2 implements Callable<Void> {

    private int taskID;


    public Gorev2(int taskID) {
        this.taskID = taskID;
    }


    @Override
    public Void call() throws Exception {
        Thread.sleep(3000);
        System.out.println("Görev çalışıyor: Task ID="+taskID);
        return null;
    }
}
