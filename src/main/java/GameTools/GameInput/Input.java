package GameTools.GameInput;

import GameTools.Screen;
import GameTools.Vector2;

import java.awt.event.*;
import java.util.HashMap;

/**
 * ��Ϸ������
 * <p>
 * �������̡�����������
 *
 * @author ZYM
 */
public final class Input implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {

    /* ����ģʽ */
    static {
        getInstance();
    }

    /* ����״̬ */
    enum KeyState {
        /* ����״̬ */
        pressed,

        /* �ɿ�״̬ */
        released
    }

    /* ���水��״̬ */
    private static HashMap<Integer, KeyState> KeyMap = new HashMap<>();

    /* ���水������ֵ״̬ */
    private static HashMap<Integer, Float> AxixMap = new HashMap<>();

    /* ���� */
    private static Input input = null;

    public static Vector2 MousePosition = new Vector2(0, 0);

    private Input() {

    }

    /* ��ȡ���� */
    public static Input getInstance() {
        if (input == null) {
            input = new Input();
        }
        return input;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        KeyMap.put(e.getButton(), KeyState.pressed);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        KeyMap.put(e.getButton(), KeyState.released);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Ҫ��ȡ�������߶ȵȵ�
        // Frame.getInsets()
        MousePosition.setX(e.getX());
        MousePosition.setY(Screen.Height - e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Ҫ��ȡ�������߶ȵȵ�
        // Frame.getInsets()
        MousePosition.setX(e.getX());
        MousePosition.setY(Screen.Height - e.getY());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        KeyMap.put(e.getKeyCode(), KeyState.pressed);
        // System.out.println(KeyMap.get(KeyCode.values()[e.getKeyCode()]));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyMap.put(e.getKeyCode(), KeyState.released);
    }

    /* ͨ��KeyEvent��ĳ������Ұ����Ƿ��� */
    public static boolean GetKeyDown(int keycode) {
        if (KeyMap.get(keycode) != null && KeyMap.get(keycode).equals(KeyState.pressed)) {
            return true;
        } else {
            return false;
        }
    }

    /* ͨ��KeyEvent��ĳ������Ұ����Ƿ��ɿ� */
    public static boolean GetKeyUp(int keycode) {
        if (KeyMap.get(keycode) != null && KeyMap.get(keycode).equals(KeyState.released)) {
            return true;
        } else {
            return false;
        }
    }

    /* ͨ��KeyEvent��ĳ������Ұ����Ƿ��� */
    public static boolean GetMouseDown(int mosuecode) {
        if (KeyMap.get(mosuecode) != null && KeyMap.get(mosuecode).equals(KeyState.pressed)) {
            return true;
        } else {
            return false;
        }
    }

    /* ͨ��KeyEvent��ĳ������Ұ����Ƿ��ɿ� */
    public static boolean GetMouseUp(int mousecode) {
        if (KeyMap.get(mousecode) != null && KeyMap.get(mousecode).equals(KeyState.released)) {
            return true;
        } else {
            return false;
        }
    }
}
