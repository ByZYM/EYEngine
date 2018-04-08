package GameModel;

import GameException.GameException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ��ͼ����
 *
 * @author ZYM
 */
public class Map {

    /**
     * ��ͼ�ļ�
     */
    private Image im;

    public Map(Image im) throws GameException {
        if (im == null) {
            throw new GameException("��ͼ������");
        }
        this.im = im;
    }

    /**
     * ���ļ�·��������ͼ
     *
     * @param FilePath �ļ�·��
     * @throws GameException ��������ͼ����
     * @throws IOException   �����������
     */
    public Map(String FilePath) throws GameException, IOException {

        try {
            im = ImageIO.read(new File(this.getClass().getClassLoader().getResource(FilePath).getFile()));
        }catch (NullPointerException e){
            System.out.println("��ͼ�����ڣ������հ���ͼ");
            BufferedImage bi= new BufferedImage(1, 1,
                                  BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bi.getGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, 1, 1);
            im=bi;
        }

        if (im == null) {
            throw new GameException("��ͼ������");
        }
    }

    public Image getIm() {
        return im;
    }

    public void setIm(Image im) {
        this.im = im;
    }
}
