package GameGraphics;

import CollisionDetection.Pair;
import CollisionDetection.SAT;
import EngineFactory.GraphicsFactory;
import GameComponent.Collider;
import GameComponent.Component;
import GameComponent.RigidBody;
import GameConfig.gameConfig;
import GameException.GameException;
import GameTools.GameInput.Input;
import GameObject.*;
import GameScript.EngineBehavior;
import GameTools.Screen;
import GameTools.Time;
import GameTools.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 本类为主窗口渲染类，包含游戏图形的绘制以及鼠标键盘操作等。
 */
public class GameFrame extends JFrame implements gameConfig {

    private static final long serialVersionUID = 1L;

    // 每20毫秒调用一次FixedUpdate
    private float mS = 0L;

    /**
     * 双缓冲画布
     */
    private Image iBuffer = null;

    /**
     * 双缓冲绘图对象
     */
    public Graphics2D gBuffer = null;

    /**
     * 是否首次初始化
     */
    private boolean firstInit = true;

    /**
     * FPS
     */
    private Time fr = new Time();

    /**
     * 窗口构造方法，调用init方法进行窗口基本初始化
     *
     * @throws IOException   输入输出错误
     * @throws GameException 游戏错误
     */
    public GameFrame() throws GameException, IOException {
        this.addMouseMotionListener(Input.getInstance());
        this.addMouseListener(Input.getInstance());
        this.addKeyListener(Input.getInstance());

        // 初始化窗体
        init(gameConfig.title, Screen.Width, Screen.Height);

        Screen.WindowLeft = getInsets().left;
        Screen.WindowTop = getInsets().top;
        Screen.WindowBottom = getInsets().bottom;
    }

    /**
     * 用于窗口初始化的函数
     *
     * @param title       = 设置窗口标题-
     * @param panelWidth  = 窗口宽度
     * @param panelHeight = 窗口高度
     * @throws IOException   输入输出错误
     * @throws GameException 游戏错误
     */
    private void init(String title, int panelWidth, int panelHeight) throws GameException, IOException {


        this.setTitle(title);
        this.setSize(panelWidth, panelHeight);
        this.setBounds(300, 0, panelWidth, panelHeight);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        Container c = this.getContentPane();
        c.setBackground(Color.white);
        c.setLayout(null);
        setResizable(false);
        setVisible(true);

        new Camera(null, 0, 0);
        new Wall(null, 0, 0);
        new Player(null, 5, 2);

        new Scene(null, 0, 0);

//        new Light(null,0,0);

        for (int i = 0; i < 3; i++) {
            new Stone(null, 5, 5 + i * 5);
        }
        /* 刷新画面渲染线程 */
        renderThread rt = new renderThread(this);
        Thread t_render = new Thread(rt);

//        /* 执行逻辑计算的线程 */
//        logicThread lt = new logicThread(this);
//        Thread t_logic = new Thread(lt);

//        rt.setLogicThread(lt);

//        lt.setRenderThread(rt);

//        t_logic.start();

        t_render.start();

//        this.addKeyListener(lt);

        /*
        * 隐藏鼠标（设置鼠标图片为空）
        * */
        Image image=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(0,0,new int[0],0,0));
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image,new Point(0,0),null));
    }

    /**
     * 只用于调用paint方法
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * 用于绘制图形界面的方法
     *
     * @param g 屏幕绘制对象
     */
    @Override
    public void paint(Graphics g) {

        /* 计算FPS 计算间隔 计算当前时间 */
        fr.refreshTime();

		/* 初始化双缓冲 */
        if (iBuffer == null) {
            /* 创建画布 */
            iBuffer = createImage(this.getWidth(), this.getHeight());
            /* 获得画布的绘图对象 */
            gBuffer = (Graphics2D) iBuffer.getGraphics();

            gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            gBuffer.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            /*
              初始化DrawE绘图对象
             */
            GraphicsFactory.GetDrawE().SetGBuffer(gBuffer);

        }

		/* 初始化绘图对象 */
        if (firstInit) {
            /* 设置绘制字体 */
            gBuffer.setFont(new Font("微软雅黑", Font.PLAIN, 13));

            firstInit = false;
        }

		/* 设置笔刷颜色 */
        gBuffer.setColor(Color.white);

		/* 画布背景颜色 */
        gBuffer.fillRect(0, 0, Screen.Width, Screen.Height);
        // gBuffer.DrawImage(img, 0, 0, Screen.Width, Screen.Height, null);

        try {
			/* --------------------------主要绘制-------------------------- */
			/* 设置笔刷颜色 */
            gBuffer.setColor(Color.black);
			/*
			 * for (int i = 0; i < 100; i++) { gBuffer.DrawRect(new
			 * Random().nextInt()%600, new Random().nextInt()%600, 10,10); }
			 */

            // gBuffer.clearRect(50, 30, 80,60);

            gBuffer.drawString("FPS " + Time.FPS, 50, 50);

            gBuffer.drawString("计算间隔 " + Time.DeltaTime + " s", 50, 70);

            gBuffer.drawString("摄像机 " + Camera.GetCurrentCamera().getModel().transform_world.getPosition(), 50, 90);

            gBuffer.drawString("屏幕实体对象数目: " + GameObject.GetAllObjects().size(), 50, 110);

            gBuffer.drawString("鼠标位置: " + Input.MousePosition, 50, 130);

            gBuffer.drawString("鼠标世界位置: " + Camera.GetCurrentCamera().ScreenToWorldPoint(Input.MousePosition), 50, 150);

			/*
			 * --------------------------绘制对象执行Start、Update脚本-------------------
			 * -------
			 */

//            Collection<GameObject> clct = GameObject.GetAllObjectsCollection();
            ArrayList<GameObject> allObjects = new ArrayList<>(GameObject.GetAllObjectsCollection());

            for (GameObject o : allObjects) {
                // 绘制对象
                o.getModel().draw(gBuffer);
            }
//            Collider.bp.DrawTree();

            Calculate();

            GraphicsFactory.GetDrawE().DrawList();

			/* 将画布输出到绘图对象上 */
            g.drawImage(iBuffer, 0, 0, this);

        } catch (

                GameException e) {
            e.printStackTrace();
        }
    }


    public void Calculate() throws GameException {
        GraphicsFactory.GetDrawE().ClearList();



        /*
             * --------------------------绘制对象执行Start、Update脚本-------------------
			 * -------
			 */
//            Collection<GameObject> clct = GameObject.GetAllObjectsCollection();
        ArrayList<GameObject> allObjects = new ArrayList<>(GameObject.GetAllObjectsCollection());

        for (GameObject o : allObjects) {

            for (GameComponent.Component e : o.getComponents()) {
                if (e instanceof EngineBehavior) {
                    if (!((EngineBehavior) e).IsStarted) {
                        ((EngineBehavior) e).Start();
                        ((EngineBehavior) e).IsStarted = true;
                    }
                    ((EngineBehavior) e).Update();
                }
            }
        }
            /*
             * --------------------------绘制对象执行Start、Update脚本-------------------
			 * -------
			 */
            /* --------------------------主要绘制-------------------------- */

			/* --------------------------碰撞检测-------------------------- */

        // 更新AABB树
        Collider.bp.Update();


        // 获取可能碰撞
        List<Pair<Collider, Collider>> ColliderPairList = Collider.bp.ComputePairs();

        for (Pair<Collider, Collider> pair : ColliderPairList) {
            // 分离向量
            Vector2 MTD = new Vector2();


            Collider c1 = pair.getFirst();
            Collider c2 = pair.getSecond();
            GameObject ob1 = c1.getGameobject();
            GameObject ob2 = c2.getGameobject();

            // 分离轴检测碰撞
            if (SAT.Intersect(c1.WorldShape, c2.WorldShape, MTD)) {

                // 非脚本处理碰撞
                if (!c1.IsTrigger && !c2.IsTrigger) {

                    // 处理刚体碰撞
                    if (ob1.GetComponent(RigidBody.class) != null) {
                        // 发生碰撞
                        for (GameComponent.Component e : ob1.getComponents()) {
                            if (e instanceof EngineBehavior) {
                                ((EngineBehavior) e).OnRigidBodyCollisionEnter(ob2);
                            }
                        }
                    } else if (ob2.GetComponent(RigidBody.class) != null) {
                        // 发生碰撞
                        for (GameComponent.Component e : ob2.getComponents()) {
                            if (e instanceof EngineBehavior) {
                                ((EngineBehavior) e).OnRigidBodyCollisionEnter(ob1);
                            }
                        }
                    }

                    Vector2 D = c1.transform_world.getPosition().sub(c2.transform_world.getPosition());

//						MTD.multiLocal(0.001f);

                    if (D.dot(MTD) < 0)
                        MTD.multiLocal(-1);
                    if (c1.IsStatic && !c2.IsStatic) {
                        c2.translate(MTD.multi(-1));
                    } else if (!c1.IsStatic && c2.IsStatic) {
                        c1.translate(MTD.multi(1));
                    } else if (!c1.IsStatic) {
                        c1.translate(MTD.multi(0.5f));
                        c2.translate(MTD.multi(-0.5f));
                    }
                    // 发生碰撞
                    for (GameComponent.Component e : ob1.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnCollisionEnter(ob2);
                        }
                    }

                    // 发生碰撞
                    for (GameComponent.Component e : ob2.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnCollisionEnter(ob1);
                        }
                    }
                }

					/* 脚本处理碰撞 */
                else {
                    // 发生碰撞
                    for (GameComponent.Component e : ob1.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnTriggerEnter(ob2);
                        }
                    }

                    // 发生碰撞
                    for (GameComponent.Component e : ob2.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnTriggerEnter(ob1);
                        }
                    }
                }

            }

        }

//			// 避免重复检测两物体的集合
//			for (int i = 0; i < allObjects.size(); i++) {
//
//				GameObject ob1 = allObjects.get(i);
//
//				for (int j = i + 1; j < allObjects.size(); j++) {
//
//					GameObject ob2 = allObjects.get(j);
//
//					Collider c1 = (Collider) ob1.GetComponent(Collider.class);
//					Collider c2 = (Collider) ob2.GetComponent(Collider.class);
//
//					// 分离向量
//					Vector2 MTD = new Vector2();
//
//					if (c2 != c1 && c1 != null && c2 != null) {
//
//						// 分离轴检测碰撞
//						if (SAT.Intersect(c1.WorldShape, c2.WorldShape, MTD)) {
//
//							// 非脚本处理碰撞
//							if (!c1.IsTrigger && !c2.IsTrigger) {
//
//								// 处理刚体碰撞
//								if (ob1.GetComponent(RigidBody.class) != null) {
//									// 发生碰撞
//									for (Component e : ob1.getComponents()) {
//										if (e instanceof EngineBehavior) {
//											((EngineBehavior) e).OnRigidBodyCollisionEnter(ob2);
//										}
//									}
//								} else if (ob2.GetComponent(RigidBody.class) != null) {
//									// 发生碰撞
//									for (Component e : ob2.getComponents()) {
//										if (e instanceof EngineBehavior) {
//											((EngineBehavior) e).OnRigidBodyCollisionEnter(ob1);
//										}
//									}
//								}
//
//								Vector2 D = c1.transform_world.getPosition().sub(c2.transform_world.getPosition());
//								if (D.dot(MTD) < 0)
//									MTD.multiLocal(-1);
//								if (c1.IsStatic && !c2.IsStatic) {
//									c2.translate(MTD.multi(-1));
//								} else if (!c1.IsStatic && c2.IsStatic) {
//									c1.translate(MTD.multi(1));
//								} else if (!c1.IsStatic && !c2.IsStatic) {
//									c1.translate(MTD.multi(0.5f));
//									c2.translate(MTD.multi(-0.5f));
//								}
//								// 发生碰撞
//								for (Component e : ob1.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnCollisionEnter(ob2);
//									}
//								}
//
//								// 发生碰撞
//								for (Component e : ob2.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnCollisionEnter(ob1);
//									}
//								}
//							}
//
//							/* 脚本处理碰撞 */
//							else {
//								// 发生碰撞
//								for (Component e : ob1.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnTriggerEnter(ob2);
//									}
//								}
//
//								// 发生碰撞
//								for (Component e : ob2.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnTriggerEnter(ob1);
//									}
//								}
//							}
//
//						}
//
//					}
//				}
//			}


			/* --------------------------碰撞检测-------------------------- */

			/*
             * --------------------------执行FixedUpdate脚本------------------------
			 * --
			 */
        mS += Time.DeltaTime;
        // 每2毫秒执行一次
        if (mS > Time.FixedTime) {
            mS -= Time.FixedTime;

            for (GameObject o : allObjects) {
                // GameObject o = allObjects.get(i);
                for (GameComponent.Component e : o.getComponents()) {
                    // 如果是脚本
                    if (e instanceof EngineBehavior) {
                        ((EngineBehavior) e).FixedUpdate();
                    }
                }
            }
        }
            /*
             * --------------------------执行FixedUpdate脚本------------------------
			 * --
			 */

			/*
             * --------------------------执行LateUpdate脚本-------------------------
			 * -
			 */
        for (GameObject o : allObjects) {
            // GameObject o = allObjects.get(i);
            // 执行脚本
            for (Component e : o.getComponents()) {

                // 如果是脚本
                if (e instanceof EngineBehavior) {
                    ((EngineBehavior) e).LateUpdate();
                }
            }
        }
			/*
			 * --------------------------执行LateUpdate脚本-------------------------
			 * -
			 */
    }

}