package GameGraphics;

/**
 * ˢ�´��ڵ��߳�
 */
public class renderThread implements Runnable {
    /**
     * ��Ҫˢ�µĴ���
     */
    final GameFrame j;

    logicThread logicThread;

    public boolean finished = true;

    /**
     * ��ͣ��Ϸ
     */
    boolean pause = false;

    /* ˢ���̵߳Ĺ��췽����������Ҫˢ�µĴ��� */
    renderThread(GameFrame j) {
        this.j = j;
    }

    /* ��Runnableʵ�ֵ�run���������ô��ڵ��ػ淽�� */
    public void run() {
//        synchronized (IDrawE.DRAW_LIST) {
            while (true) {
                try {
//                    /* �ȴ��߼��̴߳����� */
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