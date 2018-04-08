package GameTools;

public class Time {
    /* FPS���� */
    private int mS = 0;
    private int frameCount = 0;
    private long lastTime = 0l;
    private long currentTime = 0l;
    private int nowFrame = 0;
    /* FPS���� */

    /* ֡���ʱ����� */
    private long cTime = 0l;
    private long lTime = 0l;

    /**
     * ÿ֡����ʱ�� ����
     */
    public static float DeltaTime = 0f;

    public final long startTime;

    /** ��Ϸ�Ѿ���ʼ������ */
    public static float Time = 0f;

    /**
     * FPS
     */
    public static int FPS = 0;

    /**
     * FixedUpdateִ�м��ʱ�� ����
     */
    public static float FixedTime = 0.002f;

    /**
     * ֡���ʱ�����
     */
    public Time() {
        lastTime = System.currentTimeMillis();
        lTime = System.nanoTime();

        /* ��Ϸ��ʼʱ�� */
        startTime= System.currentTimeMillis();
        Time=0;
    }


    public void refreshTime() {
        calTime();
        calRate();
        deltaTime();
    }

    public void calTime() {
        Time = (System.currentTimeMillis()-startTime)/1000.0f;
    }

    /**
     * ����FPS
     *
     * @return int FPS
     */
    public int calRate() {
        currentTime = System.currentTimeMillis();
        mS += currentTime - lastTime;
        lastTime = currentTime;
        frameCount++;
        if (mS > 1000) {
            mS -= 1000;
            nowFrame = frameCount;
            frameCount = 0;
        }
        FPS = nowFrame;
        return nowFrame;
    }

    /**
     * ����ÿ֡ˢ�¼��ʱ��
     *
     * @return ��ʱ��
     */
    public long deltaTime() {
        cTime = System.nanoTime();
        long result = cTime - lTime;
        lTime = cTime;
        DeltaTime = result / 1_000_000_000.0f;
        return result;
    }
}
