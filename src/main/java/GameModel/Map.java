package GameModel;

import GameException.GameException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 贴图精灵
 *
 * @author ZYM
 */
public class Map {

    /**
     * 贴图文件
     */
    private Image im;

    public Map(Image im) throws GameException {
        if (im == null) {
            throw new GameException("贴图不存在");
        }
        this.im = im;
    }

    /**
     * 从文件路径读入贴图
     *
     * @param FilePath 文件路径
     * @throws GameException 不存在贴图错误
     * @throws IOException   输入输出错误
     */
    public Map(String FilePath) throws GameException, IOException {

        try {
            im = ImageIO.read(new File(this.getClass().getClassLoader().getResource(FilePath).getFile()));
        }catch (NullPointerException e){
            System.out.println("贴图不存在，创建空白贴图");
            BufferedImage bi= new BufferedImage(1, 1,
                                  BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bi.getGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, 1, 1);
            im=bi;
        }

        if (im == null) {
            throw new GameException("贴图不存在");
        }
    }

    public Image getIm() {
        return im;
    }

    public void setIm(Image im) {
        this.im = im;
    }
}
