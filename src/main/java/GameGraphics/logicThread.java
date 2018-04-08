package GameGraphics;

import CollisionDetection.Pair;
import CollisionDetection.SAT;
import EngineFactory.GraphicsFactory;
import GameComponent.Collider;
import GameComponent.Component;
import GameComponent.RigidBody;
import GameException.GameException;
import GameObject.GameObject;
import GameScript.EngineBehavior;
import GameTools.Time;
import GameTools.Vector2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行计算的线程
 */
public class logicThread implements Runnable, KeyListener {

    /**
     * 暂停游戏
     */
    boolean pause = false;

    /**
     * 计算间隔
     */
    private Time fr = new Time();

    // 每20毫秒调用一次FixedUpdate
    private float mS = 0L;

    final GameFrame j;

    public logicThread(GameFrame j) {
        this.j = j;
    }

    renderThread renderThread;

    /* 从Runnable实现的run方法，调用窗口的重绘方法 */
    public void run() {
//        synchronized (IDrawE.DRAW_LIST) {
            while (true) {
                if (!pause) {
                    try {
//                        if(!renderThread.finished){
//                            IDrawE.DRAW_LIST.wait();
//                        }
                        renderThread.finished = false;
//                        Calculate();
                        renderThread.finished = true;
//                        IDrawE.DRAW_LIST.notifyAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pause = !(!pause);
            }
//        }
    }

    public void Calculate() throws GameException {

        GraphicsFactory.GetDrawE().ClearList();
        // 计算间隔
        fr.deltaTime();


        /*
             * --------------------------绘制对象执行Start、Update脚本-------------------
			 * -------
			 */
//            Collection<GameObject> clct = GameObject.GetAllObjectsCollection();
        ArrayList<GameObject> allObjects = new ArrayList<>(GameObject.GetAllObjectsCollection());

        for (GameObject o : allObjects) {

            for (Component e : o.getComponents()) {
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
                        for (Component e : ob1.getComponents()) {
                            if (e instanceof EngineBehavior) {
                                ((EngineBehavior) e).OnRigidBodyCollisionEnter(ob2);
                            }
                        }
                    } else if (ob2.GetComponent(RigidBody.class) != null) {
                        // 发生碰撞
                        for (Component e : ob2.getComponents()) {
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
                    for (Component e : ob1.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnCollisionEnter(ob2);
                        }
                    }

                    // 发生碰撞
                    for (Component e : ob2.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnCollisionEnter(ob1);
                        }
                    }
                }

					/* 脚本处理碰撞 */
                else {
                    // 发生碰撞
                    for (Component e : ob1.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnTriggerEnter(ob2);
                        }
                    }

                    // 发生碰撞
                    for (Component e : ob2.getComponents()) {
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
                for (Component e : o.getComponents()) {
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

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isShiftDown()) {
            switch (e.getKeyCode()) {
            /* 暂停游戏 */
                case KeyEvent.VK_P:
                    pause = !pause;
                    break;
            }
        } else {
            switch (e.getKeyCode()) {
			/* 逐帧播放 */
                case KeyEvent.VK_P:
                    if (pause == true) {
                        try {
                            Calculate();
                        } catch (GameException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void setRenderThread(renderThread renderThread) {
        this.renderThread = renderThread;
    }
}