package GameGraphics;


import CollisionDetection.AABB;
import CollisionDetection.Shape;
import GameException.GameException;
import GameTools.Vector2;

import java.awt.*;
import java.awt.image.ImageObserver;


/* EYEngine 专用绘制 */
 public interface IDrawE {

     void SetGBuffer(Graphics2D gBuffer);

     Graphics2D GetGBuffer();

     void ClearList();

     void DrawList() throws GameException;

     void DrawImage(Image img, int x, int y,
                    int width, int height,
                    ImageObserver observer);

     void DrawRect(int x, int y, int width, int height);


    /* 填充实心shape */
     void FillShape(Shape s) throws GameException;

    /* 画空心shape */
     void DrawShape(Shape s) throws GameException;

     void DrawShape(Shape s, Color c) throws GameException;

     void DrawAABB(AABB aabb) throws GameException;

     void DrawLine(Vector2 start, Vector2 end) throws GameException;

     void DrawLine(Vector2 start, Vector2 end, Color c) throws GameException;

    /**
     * 在世界坐标上 绘制文本
     *
     * @param s 文本内容
     * @param v 世界坐标
     * @throws GameException 游戏错误
     */
     void DrawString(Object s, Vector2 v) throws GameException;

    /**
     * 在屏幕坐标上 绘制文本
     *
     * @param s 文本内容
     * @param x 屏幕坐标x
     * @param y 屏幕坐标y
     */
     void DrawString(Object s, int x, int y);

    void DrawCircle(int r, int x, int y);
}

