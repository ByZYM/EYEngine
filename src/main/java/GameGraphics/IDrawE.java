package GameGraphics;


import CollisionDetection.AABB;
import CollisionDetection.Shape;
import GameException.GameException;
import GameTools.Vector2;

import java.awt.*;
import java.awt.image.ImageObserver;


/* EYEngine ר�û��� */
 public interface IDrawE {

     void SetGBuffer(Graphics2D gBuffer);

     Graphics2D GetGBuffer();

     void ClearList();

     void DrawList() throws GameException;

     void DrawImage(Image img, int x, int y,
                    int width, int height,
                    ImageObserver observer);

     void DrawRect(int x, int y, int width, int height);


    /* ���ʵ��shape */
     void FillShape(Shape s) throws GameException;

    /* ������shape */
     void DrawShape(Shape s) throws GameException;

     void DrawShape(Shape s, Color c) throws GameException;

     void DrawAABB(AABB aabb) throws GameException;

     void DrawLine(Vector2 start, Vector2 end) throws GameException;

     void DrawLine(Vector2 start, Vector2 end, Color c) throws GameException;

    /**
     * ������������ �����ı�
     *
     * @param s �ı�����
     * @param v ��������
     * @throws GameException ��Ϸ����
     */
     void DrawString(Object s, Vector2 v) throws GameException;

    /**
     * ����Ļ������ �����ı�
     *
     * @param s �ı�����
     * @param x ��Ļ����x
     * @param y ��Ļ����y
     */
     void DrawString(Object s, int x, int y);

    void DrawCircle(int r, int x, int y);
}

