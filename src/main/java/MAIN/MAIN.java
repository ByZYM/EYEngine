package MAIN;

import GameConfig.gameConfig;
import GameException.GameException;
import GameGraphics.GameFrame;

import java.io.IOException;

/**
 * <h1>2d游戏引擎</h1>
 *
 * @author 张一鸣
 * @version v0.01
 */
public class MAIN implements gameConfig {
    public static GameFrame mf;

    public static void main(String[] args) throws GameException, IOException {
        mf = new GameFrame();
    }
}
