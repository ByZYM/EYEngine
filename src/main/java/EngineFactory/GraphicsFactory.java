package EngineFactory;

import GameGraphics.IDrawE;
import GameGraphics.IDrawImpl;

public class GraphicsFactory {

    private final static IDrawE Draw=new IDrawImpl();

    public static IDrawE GetDrawE(){
        return Draw;
    }

}
