package GameGraphics;

/**
 * 刷新窗口的线程
 */
public class renderThread implements Runnable {
    /**
     * 需要刷新的窗口
     */
    final GameFrame j;

    logicThread logicThread;

    public boolean finished = true;

    /**
     * 暂停游戏
     */
    boolean pause = false;

    /* 刷新线程的构造方法，传递需要刷新的窗口 */
    renderThread(GameFrame j) {
        this.j = j;
    }

    /* 从Runnable实现的run方法，调用窗口的重绘方法 */
    public void run() {
//        synchronized (IDrawE.DRAW_LIST) {
            while (true) {
                try {
//                    /* 等待逻辑线程处理完 */
//                    if(!finished){
//                        IDrawE.DRAW_LIST.wait();
//                    }

                    j.repaint();
//                    IDrawE.DRAW_LIST.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            }
        }
    }

    public void setLogicThread(logicThread logicThread) {
        this.logicThread = logicThread;
    }
}