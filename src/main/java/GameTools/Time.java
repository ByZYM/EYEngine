package GameTools;

public class Time {
    /* FPS计算 */
    private int mS = 0;
    private int frameCount = 0;
    private long lastTime = 0l;
    private long currentTime = 0l;
    private int nowFrame = 0;
    /* FPS计算 */

    /* 帧间隔时间计算 */
    private long cTime = 0l;
    private long lTime = 0l;

    /**
     * 每帧花费时间 秒制
     */
    public static float DeltaTime = 0f;

    public final long startTime;

    /** 游戏已经开始多少秒 */
    public static float Time = 0f;

    /**
     * FPS
     */
    public static int FPS = 0;

    /**
     * FixedUpdate执行间隔时间 秒制
     */
    public static float FixedTime = 0.002f;

    /**
     * 帧间隔时间计算
     */
    public Time() {
        lastTime = System.currentTimeMillis();
        lTime = System.nanoTime();

        /* 游戏开始时间 */
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
     * 计算FPS
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
     * 计算每帧刷新间隔时间
     *
     * @return 秒时间
     */
    public long deltaTime() {
        cTime = System.nanoTime();
        long result = cTime - lTime;
        lTime = cTime;
        DeltaTime = result / 1_000_000_000.0f;
        return result;
    }
}
