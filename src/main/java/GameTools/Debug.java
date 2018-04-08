package GameTools;

import CollisionDetection.AABB;
import CollisionDetection.Shape;
import EngineFactory.GraphicsFactory;
import GameException.GameException;

import java.awt.*;

/**
 * ��ϷDebugϵͳ
 *
 * @author ZYM
 */
public class Debug {

    /**
     * �Ƿ�Debugģʽ
     */
    public static boolean DEBUG = true;


    /* ���ʵ��shape */
    public static void FillShape(Shape s) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().FillShape(s);
        }
    }

    /* ������shape */
    public static void DrawShape(Shape s) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawShape(s);
        }
    }

    public static void DrawShape(Shape s, Color c) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawShape(s, c);
        }
    }

    public static void DrawAABB(AABB aabb) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawAABB(aabb);
        }
    }

    public static void DrawLine(Vector2 start, Vector2 end) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawLine(start, end);
        }
    }

    public static void DrawLine(Vector2 start, Vector2 end, Color c) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawLine(start, end, c);
        }
    }


    /**
     * ����Ļ������ ����Բ
     *
     * @param r Բ�뾶
     * @param x ��Ļ����x
     * @param y ��Ļ����y
     */
    public static void DrawCircle(int r, int x, int y) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawCircle(r,x,y);
        }
    }

    /**
     * ������������ �����ı�
     *
     * @param s �ı�����
     * @param v ��������
     * @throws GameException ��Ϸ����
     */
    public static void DrawString(Object s, Vector2 v) throws GameException {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawString(s, v);
        }
    }

    /**
     * ����Ļ������ �����ı�
     *
     * @param string �ı�����
     * @param x      ��Ļ����x
     * @param y      ��Ļ����y
     */
    public static void DrawString(Object string, int x, int y) {
        if (DEBUG) {
            GraphicsFactory.GetDrawE().DrawString(string, x, y);
        }
    }

}
