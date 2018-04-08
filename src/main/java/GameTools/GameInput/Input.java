package GameTools.GameInput;

import GameTools.Screen;
import GameTools.Vector2;

import java.awt.event.*;
import java.util.HashMap;

/**
 * 游戏输入类
 * <p>
 * 包含键盘、鼠标输入操作
 *
 * @author ZYM
 */
public final class Input implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {

    /* 单例模式 */
    static {
        getInstance();
    }

    /* 按键状态 */
    enum KeyState {
        /* 按下状态 */
        pressed,

        /* 松开状态 */
        released
    }

    /* 储存按键状态 */
    private static HashMap<Integer, KeyState> KeyMap = new HashMap<>();

    /* 储存按键轴数值状态 */
    private static HashMap<Integer, Float> AxixMap = new HashMap<>();

    /* 单例 */
    private static Input input = null;

    public static Vector2 MousePosition = new Vector2(0, 0);

    private Input() {

    }

    /* 获取单例 */
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
        // 要获取标题栏高度等等
        // Frame.getInsets()
        MousePosition.setX(e.getX());
        MousePosition.setY(Screen.Height - e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // 要获取标题栏高度等等
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

    /* 通过KeyEvent里的常量查找按键是否按下 */
    public static boolean GetKeyDown(int keycode) {
        if (KeyMap.get(keycode) != null && KeyMap.get(keycode).equals(KeyState.pressed)) {
            return true;
        } else {
            return false;
        }
    }

    /* 通过KeyEvent里的常量查找按键是否松开 */
    public static boolean GetKeyUp(int keycode) {
        if (KeyMap.get(keycode) != null && KeyMap.get(keycode).equals(KeyState.released)) {
            return true;
        } else {
            return false;
        }
    }

    /* 通过KeyEvent里的常量查找按键是否按下 */
    public static boolean GetMouseDown(int mosuecode) {
        if (KeyMap.get(mosuecode) != null && KeyMap.get(mosuecode).equals(KeyState.pressed)) {
            return true;
        } else {
            return false;
        }
    }

    /* 通过KeyEvent里的常量查找按键是否松开 */
    public static boolean GetMouseUp(int mousecode) {
        if (KeyMap.get(mousecode) != null && KeyMap.get(mousecode).equals(KeyState.released)) {
            return true;
        } else {
            return false;
        }
    }
}
