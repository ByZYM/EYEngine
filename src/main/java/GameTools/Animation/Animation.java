package GameTools.Animation;

import GameException.GameException;
import GameModel.Map;
import GameTools.Time;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 动画封装类
 *
 * @author ZYM
 */
public class Animation {

    /**
     * 动画名字 在AnimationConroller中的识别键
     */
    public String AnimationName;

    /**
     * 贴图序列
     */
    public Map[] maps;

    /**
     * 当前帧
     */
    public double CurrentClip = 0;

    /**
     * 每张贴图的时间间隔
     */
    private double Interval = 0.2;

//	/**
//	 * 适合一张动态图片包含所有帧的动画初始化
//	 * 
//	 * 图片遵从：
//	 * 
//	 * 动态GIF
//	 * 
//	 * @param path
//	 *            GIF动画的路径
//	 */
//	public Animation(String path) throws GameException, IOException {
//
//	}


    /**
     * 适合一张静态图片包含所有帧的动画初始化
     * <p>
     * 图片遵从：
     * <p>
     * 每一帧紧挨着，无空隙
     * <p>
     * 先后顺序为：从左往右 自上而下 进行分割
     *
     * @param Name   动画名称
     * @param path   动画的路径
     * @param width  每一帧宽度
     * @param height 每一帧高度
     * @throws GameException 游戏错误
     * @throws IOException   图片载入输入输出错误
     */
    public Animation(String Name, String path, int width, int height) throws GameException, IOException {
        AnimationName = Name;

        BufferedImage bi = null;

        try {
		/* 读入图片 */
            File file = new File(this.getClass().getClassLoader().getResource(path).getFile());

            bi = ImageIO.read(file);
        }catch (NullPointerException e){
            /* 无法读入图片，创建空白图片 */
            System.out.println("无法读入图片，创建空白图片");
            bi= new BufferedImage(width, height,
                                                        BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bi.getGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, width, height);
        }

		/* 行 */
        int col = bi.getWidth() / width;
		
		/* 列 */
        int row = bi.getHeight() / height;
		
		/* 初始化maps大小*/
        maps = new Map[col * row];

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                maps[i * row + j] = new Map(bi.getSubimage(i * width, j * height, width, height));
            }
        }
    }

    /**
     * 适合一帧一张图片的动画初始化
     *
     * @param Name 动画名称
     * @param path 动画的所有帧路径（一帧一个路径）
     * @throws GameException 游戏错误
     * @throws IOException   图片载入输入输出错误
     */
    public Animation(String Name, String... path) throws GameException, IOException {
        AnimationName = Name;
        maps = new Map[path.length];

        for (int i = 0; i < maps.length; i++) {
            maps[i] = new Map(path[i]);
        }
    }

    public Map run() {

        CurrentClip += 1 / Interval * Time.DeltaTime;
        CurrentClip = CurrentClip % maps.length;

        return maps[(int) (CurrentClip)];
    }


    public Map[] getMaps() {
        return maps;
    }

    public void setMaps(Map[] maps) {
        this.maps = maps;
    }

    public double getInterval() {
        return Interval;
    }

    public void setInterval(double interval) {
        Interval = interval;
    }

    public String getAnimationName() {
        return AnimationName;
    }

    public void setAnimationName(String animationName) {
        AnimationName = animationName;
    }
}
