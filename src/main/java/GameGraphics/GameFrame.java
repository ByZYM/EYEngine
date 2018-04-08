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
 * ����Ϊ��������Ⱦ�࣬������Ϸͼ�εĻ����Լ������̲����ȡ�
 */
public class GameFrame extends JFrame implements gameConfig {

    private static final long serialVersionUID = 1L;

    // ÿ20�������һ��FixedUpdate
    private float mS = 0L;

    /**
     * ˫���廭��
     */
    private Image iBuffer = null;

    /**
     * ˫�����ͼ����
     */
    public Graphics2D gBuffer = null;

    /**
     * �Ƿ��״γ�ʼ��
     */
    private boolean firstInit = true;

    /**
     * FPS
     */
    private Time fr = new Time();

    /**
     * ���ڹ��췽��������init�������д��ڻ�����ʼ��
     *
     * @throws IOException   �����������
     * @throws GameException ��Ϸ����
     */
    public GameFrame() throws GameException, IOException {
        this.addMouseMotionListener(Input.getInstance());
        this.addMouseListener(Input.getInstance());
        this.addKeyListener(Input.getInstance());

        // ��ʼ������
        init(gameConfig.title, Screen.Width, Screen.Height);

        Screen.WindowLeft = getInsets().left;
        Screen.WindowTop = getInsets().top;
        Screen.WindowBottom = getInsets().bottom;
    }

    /**
     * ���ڴ��ڳ�ʼ���ĺ���
     *
     * @param title       = ���ô��ڱ���-
     * @param panelWidth  = ���ڿ��
     * @param panelHeight = ���ڸ߶�
     * @throws IOException   �����������
     * @throws GameException ��Ϸ����
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
        /* ˢ�»�����Ⱦ�߳� */
        renderThread rt = new renderThread(this);
        Thread t_render = new Thread(rt);

//        /* ִ���߼�������߳� */
//        logicThread lt = new logicThread(this);
//        Thread t_logic = new Thread(lt);

//        rt.setLogicThread(lt);

//        lt.setRenderThread(rt);

//        t_logic.start();

        t_render.start();

//        this.addKeyListener(lt);

        /*
        * ������꣨�������ͼƬΪ�գ�
        * */
        Image image=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(0,0,new int[0],0,0));
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image,new Point(0,0),null));
    }

    /**
     * ֻ���ڵ���paint����
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * ���ڻ���ͼ�ν���ķ���
     *
     * @param g ��Ļ���ƶ���
     */
    @Override
    public void paint(Graphics g) {

        /* ����FPS ������ ���㵱ǰʱ�� */
        fr.refreshTime();

		/* ��ʼ��˫���� */
        if (iBuffer == null) {
            /* �������� */
            iBuffer = createImage(this.getWidth(), this.getHeight());
            /* ��û����Ļ�ͼ���� */
            gBuffer = (Graphics2D) iBuffer.getGraphics();

            gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            gBuffer.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            /*
              ��ʼ��DrawE��ͼ����
             */
            GraphicsFactory.GetDrawE().SetGBuffer(gBuffer);

        }

		/* ��ʼ����ͼ���� */
        if (firstInit) {
            /* ���û������� */
            gBuffer.setFont(new Font("΢���ź�", Font.PLAIN, 13));

            firstInit = false;
        }

		/* ���ñ�ˢ��ɫ */
        gBuffer.setColor(Color.white);

		/* ����������ɫ */
        gBuffer.fillRect(0, 0, Screen.Width, Screen.Height);
        // gBuffer.DrawImage(img, 0, 0, Screen.Width, Screen.Height, null);

        try {
			/* --------------------------��Ҫ����-------------------------- */
			/* ���ñ�ˢ��ɫ */
            gBuffer.setColor(Color.black);
			/*
			 * for (int i = 0; i < 100; i++) { gBuffer.DrawRect(new
			 * Random().nextInt()%600, new Random().nextInt()%600, 10,10); }
			 */

            // gBuffer.clearRect(50, 30, 80,60);

            gBuffer.drawString("FPS " + Time.FPS, 50, 50);

            gBuffer.drawString("������ " + Time.DeltaTime + " s", 50, 70);

            gBuffer.drawString("����� " + Camera.GetCurrentCamera().getModel().transform_world.getPosition(), 50, 90);

            gBuffer.drawString("��Ļʵ�������Ŀ: " + GameObject.GetAllObjects().size(), 50, 110);

            gBuffer.drawString("���λ��: " + Input.MousePosition, 50, 130);

            gBuffer.drawString("�������λ��: " + Camera.GetCurrentCamera().ScreenToWorldPoint(Input.MousePosition), 50, 150);

			/*
			 * --------------------------���ƶ���ִ��Start��Update�ű�-------------------
			 * -------
			 */

//            Collection<GameObject> clct = GameObject.GetAllObjectsCollection();
            ArrayList<GameObject> allObjects = new ArrayList<>(GameObject.GetAllObjectsCollection());

            for (GameObject o : allObjects) {
                // ���ƶ���
                o.getModel().draw(gBuffer);
            }
//            Collider.bp.DrawTree();

            Calculate();

            GraphicsFactory.GetDrawE().DrawList();

			/* �������������ͼ������ */
            g.drawImage(iBuffer, 0, 0, this);

        } catch (

                GameException e) {
            e.printStackTrace();
        }
    }


    public void Calculate() throws GameException {
        GraphicsFactory.GetDrawE().ClearList();



        /*
             * --------------------------���ƶ���ִ��Start��Update�ű�-------------------
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
             * --------------------------���ƶ���ִ��Start��Update�ű�-------------------
			 * -------
			 */
            /* --------------------------��Ҫ����-------------------------- */

			/* --------------------------��ײ���-------------------------- */

        // ����AABB��
        Collider.bp.Update();


        // ��ȡ������ײ
        List<Pair<Collider, Collider>> ColliderPairList = Collider.bp.ComputePairs();

        for (Pair<Collider, Collider> pair : ColliderPairList) {
            // ��������
            Vector2 MTD = new Vector2();


            Collider c1 = pair.getFirst();
            Collider c2 = pair.getSecond();
            GameObject ob1 = c1.getGameobject();
            GameObject ob2 = c2.getGameobject();

            // ����������ײ
            if (SAT.Intersect(c1.WorldShape, c2.WorldShape, MTD)) {

                // �ǽű�������ײ
                if (!c1.IsTrigger && !c2.IsTrigger) {

                    // ���������ײ
                    if (ob1.GetComponent(RigidBody.class) != null) {
                        // ������ײ
                        for (GameComponent.Component e : ob1.getComponents()) {
                            if (e instanceof EngineBehavior) {
                                ((EngineBehavior) e).OnRigidBodyCollisionEnter(ob2);
                            }
                        }
                    } else if (ob2.GetComponent(RigidBody.class) != null) {
                        // ������ײ
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
                    // ������ײ
                    for (GameComponent.Component e : ob1.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnCollisionEnter(ob2);
                        }
                    }

                    // ������ײ
                    for (GameComponent.Component e : ob2.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnCollisionEnter(ob1);
                        }
                    }
                }

					/* �ű�������ײ */
                else {
                    // ������ײ
                    for (GameComponent.Component e : ob1.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnTriggerEnter(ob2);
                        }
                    }

                    // ������ײ
                    for (GameComponent.Component e : ob2.getComponents()) {
                        if (e instanceof EngineBehavior) {
                            ((EngineBehavior) e).OnTriggerEnter(ob1);
                        }
                    }
                }

            }

        }

//			// �����ظ����������ļ���
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
//					// ��������
//					Vector2 MTD = new Vector2();
//
//					if (c2 != c1 && c1 != null && c2 != null) {
//
//						// ����������ײ
//						if (SAT.Intersect(c1.WorldShape, c2.WorldShape, MTD)) {
//
//							// �ǽű�������ײ
//							if (!c1.IsTrigger && !c2.IsTrigger) {
//
//								// ���������ײ
//								if (ob1.GetComponent(RigidBody.class) != null) {
//									// ������ײ
//									for (Component e : ob1.getComponents()) {
//										if (e instanceof EngineBehavior) {
//											((EngineBehavior) e).OnRigidBodyCollisionEnter(ob2);
//										}
//									}
//								} else if (ob2.GetComponent(RigidBody.class) != null) {
//									// ������ײ
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
//								// ������ײ
//								for (Component e : ob1.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnCollisionEnter(ob2);
//									}
//								}
//
//								// ������ײ
//								for (Component e : ob2.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnCollisionEnter(ob1);
//									}
//								}
//							}
//
//							/* �ű�������ײ */
//							else {
//								// ������ײ
//								for (Component e : ob1.getComponents()) {
//									if (e instanceof EngineBehavior) {
//										((EngineBehavior) e).OnTriggerEnter(ob2);
//									}
//								}
//
//								// ������ײ
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


			/* --------------------------��ײ���-------------------------- */

			/*
             * --------------------------ִ��FixedUpdate�ű�------------------------
			 * --
			 */
        mS += Time.DeltaTime;
        // ÿ2����ִ��һ��
        if (mS > Time.FixedTime) {
            mS -= Time.FixedTime;

            for (GameObject o : allObjects) {
                // GameObject o = allObjects.get(i);
                for (GameComponent.Component e : o.getComponents()) {
                    // ����ǽű�
                    if (e instanceof EngineBehavior) {
                        ((EngineBehavior) e).FixedUpdate();
                    }
                }
            }
        }
            /*
             * --------------------------ִ��FixedUpdate�ű�------------------------
			 * --
			 */

			/*
             * --------------------------ִ��LateUpdate�ű�-------------------------
			 * -
			 */
        for (GameObject o : allObjects) {
            // GameObject o = allObjects.get(i);
            // ִ�нű�
            for (Component e : o.getComponents()) {

                // ����ǽű�
                if (e instanceof EngineBehavior) {
                    ((EngineBehavior) e).LateUpdate();
                }
            }
        }
			/*
			 * --------------------------ִ��LateUpdate�ű�-------------------------
			 * -
			 */
    }

}