package com.rg.rahul.networklib.lib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Make single tone
public abstract  class NetworkHandler {
    private ExecutorService singleThreadExecuter;//= Executors.newSingleThreadExecutor();
    // private static ExecutorService threadPoolExecutor;//= Executors.newSingleThreadExecutor();
    // private static NetworkHandler networkHandler;
    private ExecutorService multihreadExecuter;

  /*  public static NetworkHandler getInstance() {
        if (networkHandler == null) {
            networkHandler = new NetworkHandler();
        }
        return networkHandler;
    }*/

    public void singleTaskExecute(NetworkRunnable simpleRequest) {
        if (singleThreadExecuter == null) {
            singleThreadExecuter = Executors.newSingleThreadExecutor();
        }
        singleThreadExecuter.execute(simpleRequest);
    }

    //
    public void multiTaskExcite(NetworkRunnable simpleRequest) {
        if (multihreadExecuter == null) {
            multihreadExecuter = Executors.newFixedThreadPool(5);
        }
        multihreadExecuter.execute(simpleRequest);
    }

    public void newThread(NetworkRunnable simpleRequest) {
        Thread thread = new Thread(simpleRequest);
        thread.start();
    }
}
