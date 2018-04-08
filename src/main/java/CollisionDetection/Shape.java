package CollisionDetection;

import GameTools.Vector2;

/**
 * ͹�������״��
 *
 * @author ZYM
 */
public class Shape {
    /**
     * ����ζ��㼯��
     */
    public Vector2[] vertices;

    public Segment[] edges;

    /**
     * ��ʼ��ͼ�ζ��㡢�� ������˳ʱ�뷽���ʼ����
     *
     * @param vertices ����
     */
    public Shape(Vector2... vertices) {
        this.vertices = vertices;

		/* ��ʼ���� */
        edges = new Segment[vertices.length];

        for (int i = 0; i < vertices.length - 1; i++) {
            edges[i] = new Segment(vertices[i], vertices[i + 1]);
        }
        edges[vertices.length - 1] = new Segment(vertices[vertices.length - 1], vertices[0]);
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("Shape Vertices[ ");
        for (Vector2 v : vertices) {
            s.append(v + " ");
        }

        s.append("]");

        return s.toString();
    }

    /**
     * �ƶ���״
     *
     * @param v �ƶ�����
     * @return �ƶ�֮���Shape
     */
    public Shape translate(Vector2 v) {
        Vector2[] newVertices = new Vector2[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = new Vector2(vertices[i].getX() + v.getX(), vertices[i].getY() + v.getY());
        }
        return new Shape(newVertices);
    }

}
