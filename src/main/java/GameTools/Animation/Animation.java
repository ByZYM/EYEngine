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
 * ������װ��
 *
 * @author ZYM
 */
public class Animation {

    /**
     * �������� ��AnimationConroller�е�ʶ���
     */
    public String AnimationName;

    /**
     * ��ͼ����
     */
    public Map[] maps;

    /**
     * ��ǰ֡
     */
    public double CurrentClip = 0;

    /**
     * ÿ����ͼ��ʱ����
     */
    private double Interval = 0.2;

//	/**
//	 * �ʺ�һ�Ŷ�̬ͼƬ��������֡�Ķ�����ʼ��
//	 * 
//	 * ͼƬ��ӣ�
//	 * 
//	 * ��̬GIF
//	 * 
//	 * @param path
//	 *            GIF������·��
//	 */
//	public Animation(String path) throws GameException, IOException {
//
//	}


    /**
     * �ʺ�һ�ž�̬ͼƬ��������֡�Ķ�����ʼ��
     * <p>
     * ͼƬ��ӣ�
     * <p>
     * ÿһ֡�����ţ��޿�϶
     * <p>
     * �Ⱥ�˳��Ϊ���������� ���϶��� ���зָ�
     *
     * @param Name   ��������
     * @param path   ������·��
     * @param width  ÿһ֡���
     * @param height ÿһ֡�߶�
     * @throws GameException ��Ϸ����
     * @throws IOException   ͼƬ���������������
     */
    public Animation(String Name, String path, int width, int height) throws GameException, IOException {
        AnimationName = Name;

        BufferedImage bi = null;

        try {
		/* ����ͼƬ */
            File file = new File(this.getClass().getClassLoader().getResource(path).getFile());

            bi = ImageIO.read(file);
        }catch (NullPointerException e){
            /* �޷�����ͼƬ�������հ�ͼƬ */
            System.out.println("�޷�����ͼƬ�������հ�ͼƬ");
            bi= new BufferedImage(width, height,
                                                        BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bi.getGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, width, height);
        }

		/* �� */
        int col = bi.getWidth() / width;
		
		/* �� */
        int row = bi.getHeight() / height;
		
		/* ��ʼ��maps��С*/
        maps = new Map[col * row];

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                maps[i * row + j] = new Map(bi.getSubimage(i * width, j * height, width, height));
            }
        }
    }

    /**
     * �ʺ�һ֡һ��ͼƬ�Ķ�����ʼ��
     *
     * @param Name ��������
     * @param path ����������֡·����һ֡һ��·����
     * @throws GameException ��Ϸ����
     * @throws IOException   ͼƬ���������������
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
