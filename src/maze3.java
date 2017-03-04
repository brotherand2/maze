import java.awt.Color;
import javax.swing.*;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.applet.AudioClip;
import java.net.URL;
import java.applet.Applet;
import javax.swing.JPanel;
import java.awt.event.*;
import java.util.Date;
import java.text.SimpleDateFormat;
/*
  规定0表示右方向，1表示下，2表示左，3表示上
*/
class stopwatch implements Runnable //秒表
{
	int second=-1;//记录经过的秒数，初始化为-1
	migong mg;//谜宫类
	public stopwatch(migong mg)
	{
		this.mg=mg;//从谜宫类传来的地址来控制谜宫类
	}
	public void run()
	{
		while((!mg.find&&mg.type==2)||(mg.type==3&&!mg.finish)||(mg.type==1&&!mg.find1))//在三种模式下并且还未走完谜宫或未创建完谜宫时显示经过的时间
		{
			if(!mg.isPause)//按下开始，计时
			{
				try
				{
			    	second++;//秒数加1
					mg.cost="用时:"+Integer.toString(second)+"秒";//时间经过的秒数设置为字符串格式
					Thread.sleep(1000);//每1秒记录1次
				}
				catch (Exception e)
				{
				}
			}
		}
	}
}
class MyTime extends Thread//我的时间线程
{
	String  time=null;//时间
	migong mg;//谜宫类
	public MyTime(migong mg)
	{
		this.mg=mg;
	}
	public void run()//重写run方法
	{
		while(true)
		{
			try
			{
				Thread.sleep(1000);//每隔1000毫秒，即1秒显示
			    SimpleDateFormat format=new SimpleDateFormat("时间:HH:mm:ss");//设定时间格式
	            Date date=new Date();//日期类
                time=format.format(date);//将日期时间按自己要求的格式转换成字符串
				mg.time=time;//设置新时间
				mg.isRepaint3=false;
				mg.repaint();//刷新画面，显示新时间
			}
			catch (Exception e)
			{
			}
		}
	}
}
class puzzle  extends JFrame implements ActionListener
{//谜宫窗体类
	JMenuBar bar=new JMenuBar();//菜单条
	JMenuItem item[]=new JMenuItem[11];//下拉菜单
	JMenu menu[]=new JMenu[3];//菜单项
	JColorChooser colorSelect=new JColorChooser();//JColorChooser 提供一个用于允许用户操作和选择颜色的控制器窗格
	int second;//移动的速度，多少毫秒移一次
	int pixel;//房间像素，
	migong mg=new migong();
	public puzzle()
	{
		super("用栈构造的谜宫");
		menu[0]=new JMenu("文件");//实例化并初始化菜单
		menu[1]=new JMenu("帮助");
		menu[2]=new JMenu("设置");
		item[0]=new JMenuItem("退出");
		item[1]=new JMenuItem("帮助主题");
		item[2]=new JMenuItem("关于版权...");
		item[3]=new JMenuItem("设置速度像素模式");
		item[4]=new JMenuItem("设置谜宫背景颜色");
		item[5]=new JMenuItem("设置墙壁颜色");
		item[6]=new JMenuItem("设置字体颜色");
		item[7]=new JMenuItem("设置字体背景颜色");
		item[8]=new JMenuItem("设置路线颜色");
		item[9]=new JMenuItem("设置开始房间颜色");
		item[10]=new JMenuItem("设置结束房间颜色");	
		menu[0].add(menu[2]);
		menu[0].add(item[0]);//实例化并初始化下拉菜单项目
		menu[1].add(item[1]);
		menu[1].add(item[2]);
		menu[2].add(item[3]);
		menu[2].add(item[4]);
		menu[2].add(item[5]);
		menu[2].addSeparator();//将新分隔符追加到菜单的末尾。	
		menu[2].add(item[6]);
		menu[2].add(item[7]);
		menu[2].add(item[8]);
		menu[2].addSeparator();//将新分隔符追加到菜单的末尾。
		menu[2].add(item[9]);
		menu[2].add(item[10]);
		item[0].addActionListener(this);//加入监听器
		item[1].addActionListener(this);//加入监听器
		item[2].addActionListener(this);//加入监听器
		item[3].addActionListener(this);//加入监听器
		item[4].addActionListener(this);//加入监听器
		item[5].addActionListener(this);//加入监听器
		item[6].addActionListener(this);//加入监听器
		item[7].addActionListener(this);//加入监听器
		item[8].addActionListener(this);//加入监听器
		item[9].addActionListener(this);//加入监听器
		item[10].addActionListener(this);//加入监听器
		bar.add(menu[0]);//将菜单放到菜单条上
		bar.add(menu[1]);
        addKeyListener(new KeyAdapter(){//加入键盘监听器
		public void keyTyped(KeyEvent e)//当键盘按键松开时
			{
               if(e.getKeyChar()==' ')
				{
                    if(mg.isStart)
					{					
						if(mg.isPause)
						{
							mg.str="按空格暂停";
							mg.isPause=false;
						}
						else
						{
							mg.str="按空格开始";
							mg.isPause=true;
						}
					}
					else
					{
						mg.isStart=true;
					}
					repaint();
				}
			}
			public void keyPressed(KeyEvent e)
			{
				if(mg.isStart&&mg.type==1)
				{
					if(e.getKeyCode()==KeyEvent.VK_RIGHT)//按向右
					{
					   mg.flag=0;
					   mg.peopleFind();//向右移动
					}
					if(e.getKeyCode()==KeyEvent.VK_DOWN)//按向下
					{
						mg.flag=1;
					    mg.peopleFind();//向下移动
					}
					if(e.getKeyCode()==KeyEvent.VK_LEFT)//按向左
					{
						mg.flag=2;
					    mg.peopleFind();//向左移动
					}
					if(e.getKeyCode()==KeyEvent.VK_UP)//按向上
					{
						mg.flag=3;
					   mg.peopleFind();//向上移动
					}
				}
			}
		});
		setJMenuBar(bar);//设置菜单条
		setSize(1300,730);//设置大小
        setResizable(false);//设置不可见
		add(mg);//加入面板，面板上画着谜宫
		setVisible(true);//显示窗口
		new SET(mg);//实例化设置类,在程序一开始弹出窗口让用户设置
		new MyTime(mg).start();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置退出应用程序后的默认窗口关闭操作
	}
	public boolean ifColorChange(Color a,Color b)//颜色是否改变,a,b分别是改变前和改变后的颜色，判断是否不同
	{
		mg.isColorChange=(a==b)?false:true;//颜色相同，返回颜色不变，否则，返回颜色改变
        return mg.isColorChange; 
	}
	public void actionPerformed(ActionEvent e)
	{
        if(e.getSource()==item[0])
		{
			System.exit(0);//退出
		}
        if(e.getSource()==item[1])//帮助
		{
		}
        if(e.getSource()==item[2])//关于版权
		{
		}
        if(e.getSource()==item[3])//设置速度，时间，模式
		{
			mg.setPause(true);
			new SET(mg);
		}
        if(e.getSource()==item[4])//设置谜宫背景颜色
		{
  		  Color color=JColorChooser.showDialog(this,"选择谜宫背景颜色",Color.black);//示例颜色为默认背景颜色黑色
		  if(color!=null)//如果选择了其中一种颜色
		  {
			  mg.isColorChange=ifColorChange(mg.mazeBackGroundColor,color);
			  mg.mazeBackGroundColor=color;//设置谜宫背景颜色为所选择的颜色
			  if(mg.isColorChange)//颜色改变
				  mg.setNewColorBufferImage();//在颜色改变后，设置新颜色的缓冲图，谜宫不改变
			  mg.repaint();//刷新画面以显示新颜色
		  }
		}
        if(e.getSource()==item[5])//设置墙壁颜色
		{
        	  Color color=JColorChooser.showDialog(this,"选择谜宫墙壁颜色",Color.green);//示例颜色为默认墙壁颜色绿色
    		  if(color!=null)//如果选择了其中一种颜色
    		  {
    			  mg.isColorChange=ifColorChange(mg.wallColor,color);    			  
    			  mg.wallColor=color;//设置谜宫墙壁颜色为所选择的颜色
    			  if(mg.isColorChange)//颜色改变
    				  mg.setNewColorBufferImage();//在颜色改变后，设置新颜色的缓冲图，谜宫不改变    			  
    			  mg.repaint();//刷新画面以显示新颜色
    		  }
		}
        if(e.getSource()==item[6])//设置字体颜色，不用再setNewColorBufferImage()因为字体颜色随时间线程每1秒重画1次，而墙壁、路线只有在走的时候才更新
		{
        	  Color color=JColorChooser.showDialog(this,"选择字体颜色",Color.black);//示例颜色为默认字体颜色黑色
    		  if(color!=null)//如果选择了其中一种颜色
    		  {
    			  mg.isColorChange=ifColorChange(mg.fontColor,color);   			  
    			  mg.fontColor=color;//设置字体颜色为所选择的颜色
    			  mg.repaint();//刷新画面以显示新颜色
    		  }
		}
        if(e.getSource()==item[7])//设置字体背景颜色，不用再setNewColorBufferImage()因为字体背景颜色随时间线程每1秒重画1次，而墙壁、路线只有在走的时候才更新
		{
        	  Color color=JColorChooser.showDialog(this,"选择字体背景颜色",Color.white);//示例颜色为默认字体背景颜色白色
    		  if(color!=null)//如果选择了其中一种颜色
    		  {
    			  mg.isColorChange=ifColorChange(mg.fontBackGroundColor,color);    			  
    			  mg.fontBackGroundColor=color;//设置字体背景颜色为所选择的颜色
    			  mg.repaint();//刷新画面以显示新颜色
    		  }
		}
        if(e.getSource()==item[8])//设置路线颜色
		{
        	  Color color=JColorChooser.showDialog(this,"选择路线颜色",Color.red);//示例颜色为默认路线颜色红色
    		  if(color!=null)//如果选择了其中一种颜色
    		  {
    			  mg.isColorChange=ifColorChange(mg.routeColor,color);   			  
    			  mg.routeColor=color;//设置谜宫路线颜色为所选择的颜色
    			  if(mg.isColorChange&&mg.type!=1)//颜色改变并且是第2，3种模式
    				  mg.setNewColorBufferImage();//在颜色改变后，设置新颜色的缓冲图，谜宫不改变
    			  mg.repaint();//刷新画面以显示新颜色
    		  }
		}
        if(e.getSource()==item[9])//设置开始房间颜色
		{
        	  Color color=JColorChooser.showDialog(this,"选择开始房间颜色",Color.white);//示例颜色为默认开始房间颜色白色
    		  if(color!=null)//如果选择了其中一种颜色
    		  {
    			  mg.isColorChange=ifColorChange(mg.startColor,color);    			  
    			  mg.startColor=color;//设置谜宫开始房间颜色为所选择的颜色
    			  if(mg.isColorChange&&mg.pixel<60)//颜色改变,且像素小于60
    				  mg.setNewColorBufferImage();//在颜色改变后，设置新颜色的缓冲图，谜宫不改变
    			  mg.repaint();//刷新画面以显示新颜色
    		  }
		}
        if(e.getSource()==item[10])//设置结束房间颜色
		{
        	  Color color=JColorChooser.showDialog(this,"选择谜宫结束房间颜色",Color.red);//示例颜色为默认结束房间颜色红色
    		  if(color!=null)//如果选择了其中一种颜色
    		  {
    			  mg.isColorChange=ifColorChange(mg.endColor,color);    			  
    			  mg.endColor=color;//设置结束房间颜色为所选择的颜色
    			  if(mg.isColorChange&&mg.pixel<60&&mg.type!=3)//颜色改变,且像素小于60且不是第3种模式，因为第3种模式无终点
    				  mg.setNewColorBufferImage();//在颜色改变后，设置新颜色的缓冲图，谜宫不改变   			 
    			  mg.repaint();//刷新画面以显示新颜色
    		  }
		}
	}
}
class SET extends JDialog implements ActionListener ,ItemListener//设置类，设置时间，速度,网格等属性
{
	JLabel lab1=new JLabel("网 格 像素");
	JLabel lab2=new JLabel("速度 (毫秒)");
	JButton but1=new JButton("确定");
	JButton but2=new JButton("退出");
	JTextField field1=new JTextField();//填写网 格 像素
	JTextField field2=new JTextField();//填写速度
	int second;//移动的速度，多少毫秒移一次
	int pixel;//房间像素
	int type;//游戏类型
	JPanel pan=new JPanel();//放选择人工探索还是电脑探索的面板
    JRadioButton rbut1=new JRadioButton("计时比赛");
    JRadioButton rbut2=new JRadioButton("电脑演示");//默认选择电脑自动寻路径
    JRadioButton rbut3=new JRadioButton("创建谜宫演示");
    migong mg;
    ButtonGroup group=new ButtonGroup();//单选按钮组,只有加到组里才能实现单选，或则是多选
    public SET(migong mg)
	{
		this.mg=mg;//将谜宫图地址(mg)将谜宫传过来
        this.type=mg.type;//设置各种属性类型、像素、睡眠时间
		this.pixel=mg.pixel;
		this.second=mg.second;
		switch(mg.type)//在单选按钮上在选择谜宫当前模式
		{
            case 1:
				rbut1.setSelected(true);
			break;
            case 2:
				rbut2.setSelected(true);
			break;
            case 3:
				rbut3.setSelected(true);
			break;
			default:
				rbut2.setSelected(true);
            break;
		}
		setLayout(null);//取消默认的布局管理器
        setTitle("设置");//设置标题
        lab1.setBounds(20,20,80,20);
		lab2.setBounds(20,50,80,20);
        field1.setBounds(100,20,60,20);
		field2.setBounds(100,50,60,20);
        pan.setBounds(20,80,290,60);
		pan.setLayout(new FlowLayout());
		pan.setBorder(BorderFactory.createTitledBorder("游戏模式"));//再创建一个有标题的边框
		but1.setBounds(40,150,60,20);
		but2.setBounds(120,150,60,20);
        but1.addActionListener(this);
        but2.addActionListener(this);
        field1.setText(""+pixel);//显示房间像素
		field2.setText(""+second);//显示速度
		add(lab1);
		add(lab2);
		add(but1);
		add(but2);
		add(field1);
		add(field2);
		group.add(rbut1);
		group.add(rbut2);
		group.add(rbut3);
		rbut1.addItemListener(this);
		rbut2.addItemListener(this);
		rbut3.addItemListener(this);
		pan.add(rbut1);
		pan.add(rbut2);
		pan.add(rbut3);
		add(pan);
		setResizable(false);
		setLocation(300,200);
		setSize(330,220);
		setVisible(true);
	}
	public void itemStateChanged(ItemEvent e) 
         // 在用户已选定或取消选定某项时调用。
	{
		if(e.getSource()==rbut1)
		{
			type=1;//设置类型为人工探索
		}
		if(e.getSource()==rbut2)
		{
			type=2;//设置类型为电脑探索
		}
		if(e.getSource()==rbut3)
		{
			type=3;//设置类型为观看谜宫生成过程
		}

	}
	public void actionPerformed(ActionEvent e)//发生操作时调用
	{
		if(e.getSource()==but1)//确定
		{
			String str=field1.getText();//得到每个房间的像素数
			String str1=field2.getText();//得到速度，毫秒
			try
			{
  			     pixel=Integer.parseInt(str);//转换成字符串
				 second=Integer.parseInt(str1);//转换成字符串
				if(pixel<2||pixel>540)//行数超过范围
				{
                   JOptionPane.showMessageDialog(null,"整个谜宫最大像素为540，最小像素不得低于3像素超出范围。","提示...",JOptionPane.WARNING_MESSAGE);
					return ;
				}
				if(second<10)//秒数太低，不够时间修改
				{
                   JOptionPane.showMessageDialog(null,"最快时间不得低于10毫秒刷新一次，因为低于10毫秒不够时间修改变化，建议设置大于10毫秒的速度\n让电脑有足够修改变化，若出现出发点出现多个，即是因为不够时间修改变化，还没消除掉上一次痕迹又进行下一轮修改","提示...",JOptionPane.WARNING_MESSAGE);
				   return ;
				}			
			}
			catch (NumberFormatException e1)
			{
                JOptionPane.showMessageDialog(null,"只能输入数字，不能输入数字以外的字母，符号等。","提示...",JOptionPane.WARNING_MESSAGE);
			    return;
			}			
			mg.isTypeChange=false;//初始化为类型未改变
			if(mg.type!=type)//谜宫当前类型与选择的类型不同
				mg.isTypeChange=true;//设置类型改变为真			
			mg.column=(int)(1080/pixel);//根据新像素设置新的列数、行数
			mg.row=(int)((mg.column*10)/18);//因为初始化为18列10行，所以设置新的列数，和行数，也保持18：10
			System.out.printf("row=%d\n", mg.row);
			mg.isPixelChange=false;//初始化为像素未改变
			if(pixel!=mg.pixel)//像素改变
			{
				mg.setPixel(pixel);
				mg.isPixelChange=true;//将像素设为已改变
				mg.finish=true;//设置建成以退出建谜宫(模式3)
				mg.find=true;//设置找到以退出寻找(模式2）
				mg.find1=true;//设置找到以退出寻找（模式1)
			}
			mg.setPause(true);//设置游戏探索暂停
			mg.str="按空格开始";//设置游戏暂停后提示按空格开始
			if(type==3)//创建谜宫模式
			{
				mg.model="模式【创建谜宫演示】";
			}
			if(type==2)
			{
				mg.model="模式【电脑探索谜宫】";
				if(mg.finish&&(!mg.isPixelChange)&&mg.isTypeChange)//如果谜宫谜宫完成有2种情况，1种是真的创建完成，另1种是因为像素改变，为了退出该种模式的死循环，设为完成以退出循环
				{//所以像素未变，且finish=true是真的创建完成谜宫,并且如果类型改变了，即从其它模式转变而来，则地图不变，将起点回到原点，消除路线
					mg.createBufferImage();//因为第3种模式下的有路线，为了消除路线，根据谜宫数组信息(mg[][])重新创建缓冲图
					mg.pullDownWall();//拆墙
					mg.drawStartAndEnd();//画开始点和结束点图
					mg.find1=false;//防止再次在像素不变情况下回到模式1的时候发生重建谜宫
				}			
			}
			if(type==1)
			{
				mg.model="模式【人工控索谜宫】";	
				if(mg.finish&&(!mg.isPixelChange)&&mg.isTypeChange)//如果谜宫谜宫完成有2种情况，1种是真的创建完成，另1种是因为像素改变，为了退出该种模式(第3种模式)的死循环，设为完成以退出循环
				{//所以像素未变，且finish=true是真的创建完成谜宫,并且如果类型改变了，即从其它模式转变而来，则地图不变，将起点回到原点，消除路线
					mg.createBufferImage();//因为第3种模式下的有路线，为了消除路线，根据谜宫数组信息(mg[][])重新创建缓冲图
					mg.pullDownWall();//拆墙
					mg.drawStartAndEnd();//画开始点和结束点图
					mg.find=false;//防止再次在像素不变情况下回到模式2的时候发生重建谜宫					
				}	
			}
		    mg.setType(type);//设置类型  ，要放在setPixel后面，因为maze中有个线程1直循环，若先设置类型后设置像素，设置类型后它立即在循环中跳到相应类型，而这时像素还没来得及改变，用老像素创建、访问就会出现数组下标超出异常
		    mg.setSecond(second);//设置时间及像素
			mg.isStart=true;//设置游戏开始，必须放在后面，若放在mg.find,find1,finish=true前，那么在run函数检测到这些为true，又会将isStart设置成false，所以要在设置这些状态之后设置isStart			
			setVisible(false);//确定后对话框消失
		}
		if(e.getSource()==but2)//退出
		{
			setVisible(false);//转到对话框消失
		}
	}
}
class Box//栈类，这个类用作栈元素
{
	int i;//第几行
	int j;//第几列
	int next;//往下一个房间的方向
	int count;//走过的方向数目
	int canGo;//走通的方向数目
	int visit[]=new int[4];//四个方向访问标志，0表示该方向未走过，1表示走过
}
class room//房间类
{
	int property;//房间属性，1表示整个房间是实心的，不能通过、拆墙，0表示空心的房间，里面的房间可通过拆墙打通
	//-1表示该房间已拆墙打通，走过但是死路，2表示房间已拆墙打通不是死路
    int left;//房间左墙壁是否打通，0表示未打通，1表示打通可通过
	int right;//房间右墙壁是否打通，0表示未打通，1表示打通可通过
	int up;//房间前墙壁是否打通，0表示未打通，1表示打通可通过
	int down;//房间后墙壁是否打通，0表示未打通，1表示打通可通过
	//property为1的房间是实心的，不能进行打通，四面围墙就是由众多实心房子围成的
}
class migong  extends JPanel implements Runnable 
{
	int second=1000;//移动的速度，多少毫秒移一次，初始化为1秒
	int pixel=60;//房间像素，初始化为60像素
	int i,j;//下一个房间的坐标
	int k;
	int row=10;//行数,初始化为10行18列
	int column=18;//列数
	int count;//用于计走过的步数
	int entryX,entryY;//入口的横、纵坐标
	int exitX,exitY;//出口的横、纵坐标
	int currentX,currentY;//当前房间的横、纵坐标
    int frontNext;//退栈前的房间通往下一个房间的方向
    int type=2;//模式，1为计时比赛，人工探索，2为电脑探索，3为创建新谜宫
	int top=-1;//栈顶
	int pathTop;//所有路径（包括走过的死路）栈顶
	int top1=-1;//栈顶
	int direct;//当前的房间往下一房间的方向标志
	int entrydirect;//入口方向标志
	int flag=-1;//人工探索移动的方向,初始化未向任何方向移动
	String str="按空格开始";//开始、暂停提示字符串
	String time="时间:";//时间
	String model="模式【控索谜宫演示】";//模式
	String cost;//用时
	String step="步数";//步数
	stopwatch watch;//秒表类
	Thread t1;//线程用来计时
	room mg[][];//房间数组
    Box stack[];//栈
	Box path[];//用于保存所以走过的路径下一个房间的方向，包括死路
	Box temp;//当前的房间
	Box multiDirect[];//保存多个方向的房间
	BufferedImage image1=new BufferedImage(1300,730,BufferedImage.TYPE_3BYTE_BGR); //3张缓冲图，分别存放三种模式下的图，image1是人工探索缓冲图
	BufferedImage image2=new BufferedImage(1300,730,BufferedImage.TYPE_3BYTE_BGR); //电脑探索路径缓冲图
	BufferedImage image3=new BufferedImage(1300,730,BufferedImage.TYPE_3BYTE_BGR); //创建谜宫缓冲图
   	BufferedImage imageStart=null;//开始的图片
	BufferedImage imageEnd=null;//结束的图片
	Graphics2D g1[]=new Graphics2D[3];//3张缓冲图各用一支笔
	Color mazeBackGroundColor=Color.black;//谜宫背景颜色，初始化为黑色
	Color wallColor=Color.green;//谜宫墙壁颜色，初始化为绿色
	Color fontColor=Color.black;//谜宫字体颜色，初始化为黑色
	Color fontBackGroundColor=Color.white;//谜宫字体背景颜色，初始化为白色
	Color routeColor=Color.red;//谜宫路线颜色，初始化为红色
	Color startColor=Color.white;//谜宫开始点颜色，初始化为白色，仅在像素小于60情况下才有效
	Color endColor=Color.red;//谜宫结束点颜色，初始化为红色，仅在像素小于60情况下才有效
	/*
    BufferedImage继承Image,是图像数据缓冲区,将各种线图、图形、图片等加到这个
	缓冲区构成一个组合图，再把这个组和图加到面板上JFrame或JPanel...中
	BufferedImage.TYPE_3BYTE_BGR
	表示一个具有 8 位 RGB 颜色分量的图像，对应于 Windows 风格的
	BGR 颜色模型，具有用 3 字节存储的 Blue、Green 和 Red 三种颜色。
	*/
	Thread t=new Thread(this);//用线程睡眠时间来刷新画面
	URL url=getClass().getResource("jump.wav");//getClss()
	//返回一个对象的运行时类,即Class这个类，
	//getResource在mgpath这个类的工作区间下是在查找返回带有给定名称的资源url，即路径
	AudioClip audio;	//AudioClip 是用于播放音频接口，不能new出来,Applet类有一个方法可以从给定 URL 处获取音频剪辑接口的实例化
    boolean isStart=true;//是否开始移动，初始为开始
	boolean isPause=true;//是暂停
	boolean isBegin;//是否计时开始
	boolean isPixelChange;//像素是否改变
	boolean isTypeChange;//类型是否改变
	boolean isColorChange;//颜色是否改变
	boolean finish;//谜宫创建完成否
    boolean find;//电脑是否找到终点标记
    boolean find1;//玩家是否找到终点标记
    boolean isPull=false;//第3种模式下是否可拆,初始化为不可拆
	boolean isPush=true;//当前房间是否进栈到下一个房间
	boolean isPictureExit=true;//图片是否读取成功,初始化为读取成功
	boolean isRepaint3=false;//进行刷新画面的重画，是第3种模式则为true，第1、2种模式或时间线程调用repaint则为false
	Date now =new Date();//时间
	public void run()
	{
		while(true)
		{
			switch(type)
			{
				case 3:
					if(isStart)
				    {
	            		 isBegin=false;//初始化未计时，按下空格后才计时
						 str="按空格开始";			 
					     createMazePath();//创建新谜宫并演示创建过程
					     if(finish)
						 {
						     setStart(false);//完成要求	
							 setPause(true);
						 }
					}
				break;
				case 2:
				{
					if(isStart)
					{
            		    isBegin=false;
            		   	str="按空格开始"; 
						if(find||(!finish))//找到终点后或从第3种模式向第2种模式转变过程谜宫未创建完成
						{
							find=false;//找终点后要继续下一轮，所以设为初值没找到
							finish=true;//设置建成以退出建谜宫(模式3)
							quickCreateMazePath();//重新快速创建1个新谜宫
						    repaint();//刷新一下显示新创建的谜宫
						}
					    computerFindPath(entryX,entryY,exitX,exitY);//电脑寻找谜宫路径
						if(find)//找到终点后
						{ 
							setStart(false);
							setPause(true);
						}
					}
				}
				break;
				case 1:
				if(isStart)
				{
            		    isBegin=false;//找终点后要继续下一轮，所以设为初值没找到
						str="按空格开始";		                
            		    watch=new stopwatch(this);//这个新秒表为什么放在这，不像1，2模式放在函数内，这是因为模式3每移动1次调用一次函数，而1，2模式只调用1次
						if(find1||(!finish))//找到终点后或从第3种模式向第1种模式转变过程谜宫未创建完成
						{
							find1=false;//找终点后要继续下一轮，所以设为初值没找到
							finish=true;//设置建成以退出建谜宫(模式3)
							quickCreateMazePath();//重新快速创建1个新谜宫
							repaint();
						}
					    currentX=entryX;//当前坐标为初始化为起始点坐标
						currentY=entryY;
						count=0;
                        while(!find1&&type==1)//没找到并且是类型1，一直循环，让它停在这里不让它在run()中循环
					    {
					    }
						if(find1)//找到终点后
						{
							setStart(false);
							setPause(true);
						}
				}
			}
		}
	}
	public void setStart(boolean isStart)//更改状态
	{
		this.isStart=isStart;
	}
	public void setPause(boolean isPause)//设置暂停开始
	{
		this.isPause=isPause;
	}
	public void setSecond(int second)//更改速度
	{
		this.second=second;
	}
	public void setPixel(int pixel)//更改像素
	{
		this.pixel=pixel;
	}
	public void setType(int type)//更改模式
	{
		this.type=type;
	}
	public migong()
	{
		try
		{ //ImageIO类包含一些用来查找 ImageReader 和 ImageWriter 以及执行简单编码和解码的静态便捷方法
		  //ImageIO类没有构造方法，没法new出来，可根据一个输入流来查找某个图像，并返回图像类（实例化）
		  imageStart=ImageIO.read(getClass().getResource("ad.jpg"));
		  imageEnd=ImageIO.read(getClass().getResource("final.jpg"));		
		  audio=Applet.newAudioClip(url);//AudioClip 是用于播放音频接口，不能new出来,Applet类有一个方法可以从给定 URL 处获取音频剪辑接口的实例化
		}
		catch (Exception e)
		{
            if(imageStart==null||imageEnd==null)//只要有一幅图片读取不成功
            	isPictureExit=false;//设置图片读取不成功
		}
		quickCreateMazePath();//创造随机谜宫
		t.start();
	}
	public void initMaze()//每一次建立谜宫前要进行初始化
	{
		mg=new room[row+2][column+2];//用数组保存每个类的地址
		stack=new Box[row*column];
		path=new Box[row*column];
		multiDirect=new Box[row*column];
		for(i=0;i<row*column;i++)
		{
			stack[i]=new Box();//实例化栈中每个元素
			multiDirect[i]=new Box();//实例化栈中每个元素
			multiDirect[i].count=stack[i].count=0;//走过的方向数目初始化为0
			multiDirect[i].next=stack[i].next=-1;//往下一个房间的方向初始化为无，用-1表示
			stack[i].canGo=0;//初始化走通的方向数为0
			for(j=0;j<4;j++)
				stack[i].visit[j]=0;//四个方向初始化为未走过
			path[i]=new Box();////实例化栈中每个元素
			path[i].next=-1;//往下一个房间的方向初始化为无
		}
		for(i=0;i<row+2;i++)
		{
			for(j=0;j<column+2;j++)
			{
				mg[i][j]=new room();//实例化每个房间
				if(i==0||i==row+1||j==0||j==column+1)//初始化由房间组成的四面围墙
					mg[i][j].property=1;
				else
					mg[i][j].property=0;//初始空心房间
				mg[i][j].up=0;//初始化前墙未打通
				mg[i][j].down=0;//初始化后墙未打通
				mg[i][j].left=0;//初始化左墙未打通
				mg[i][j].right=0;//初始化右墙未打通
			}
		}
		count=0;//步数初始化为0
		top=-1;//栈顶初始化为-1
		cost="用时:0秒";
		//入口坐标初始化
		find=false;//初始化为未找到
		find1=false;//初始化为未找到
		finish=false;//初始化为完成
		entrydirect=(int)(4*Math.random());//，入口在靠近围墙哪面
		switch(entrydirect)
		{
			case 0://紧贴围墙右面
				entryX=row;
			    entryY=(int)(column*Math.random())+1;//入口横坐标为row，纵坐标为1-column的随机数
			break;
			case 1://紧贴围墙下面
				entryX=(int)(row*Math.random())+1;//入口纵坐标column，横坐标为为1-row的随机数
			    entryY=column;
			break;
			case 2://紧贴围墙左面
				entryX=1;
			    entryY=(int)(column*Math.random())+1;//入口横坐标为1，纵坐标为1-column的随机数
			break;
			case 3://紧贴围墙上面
				entryX=(int)(row*Math.random())+1;//入口横坐标为1-row的随机数，纵坐标为1
			    entryY=1;
             break;
		}
		//出口坐标初始化
		exitX=row-entryX+1;//出口在入口哪面围墙的另一面，如入口在左围墙，则出口在右围墙
		exitY=column-entryY+1;
		finish=false;//初始化为未创建完成
	    //到这时为止是对谜宫房间的初始化
		//对缓冲图进行初始化
		createBufferImage();//创建谜宫的三张缓冲图
	}
	public void createMazePath()//显示创建谜宫，x,y是谜宫的出发点，xi,却是谜宫的结束坐标
	{//注意x,y为二维数组的横坐标、纵坐标，但x却是第x列y是第y行
	    initMaze();//初始化谜宫数据
		watch=new stopwatch(this);//创建1个秒表
		t1=new Thread(watch);
		top=-1;//栈顶
		top1=-1;
		pathTop=-1;//栈顶
		mg[entryX][entryY].property=2;//表示已走过
		top++;//将开始坐标进栈
		pathTop++;//将开始坐标进栈
		stack[top].i=entryX;
		stack[top].j=entryY;
		path[pathTop].i=entryX;
		path[pathTop].j=entryY;
        count++;
		t1.start();//时间线动，但还未计时
		while(top>-1&&type==3&&!finish)//还有路没走完，一直到谜宫所有的格都走完
		{
			if(!isPause)
			{
				if(count==1)
				{
					isBegin=true;//按开始后，计时
				}
				temp=stack[top];//当前房间为栈顶存放的房间，从这出发
				if(temp.count<4)//四个方向还没走完
				{
					direct=(int)(4*Math.random());//随机产生0-3
					while(temp.visit[direct]==1)//该方向已走过，继续产生没走过的方向
					direct=(int)(4*Math.random());//随机产生0-3
					temp.count++;//走过的方向个数加1，包括走不通的
					temp.visit[direct]=1;//该方向没走过，现在走了，设为已超过
					switch(direct)
					{
						case 0://规定向右方向
							i=temp.i;
							j=temp.j+1;//列数加1
							break;
						case 1://规定向下方向
							i=temp.i+1;
							j=temp.j;//行数加1
							break;
						case 2://规定向左方向
							i=temp.i;
							j=temp.j-1;//列数减1
							break;
						case 3://规定向上方向
							i=temp.i-1;
							j=temp.j;//行数减1
							break;
					}
					if(mg[i][j].property==0)//该房间可进
					{
						switch(direct)
						{
						case 0:
							mg[temp.i][temp.j].right=1;//向右可走，打通右墙
						    mg[i][j].left=1;//即当前房间的左墙打通
						break;
						case 1:
							mg[temp.i][temp.j].down=1;//向下可走，打通后墙
						    mg[i][j].up=1;//即当前房间的前墙打通						
						break;
						case 2:
							mg[temp.i][temp.j].left=1;//向左可走，打通左墙
						    mg[i][j].right=1;//即当前房间的右墙打通										
						break;
						case 3:
							mg[temp.i][temp.j].up=1;//向上可走，打通前墙
						    mg[i][j].down=1;//即当前房间的后墙打通						
						break;
						}
						mg[i][j].property=2;//表示已走过
						count++;//走的步数加1
						isPush=true;//再进栈
						step="步数:"+count;
					    path[pathTop].next=direct;//保存这个房间通往下一个房间的方向
						pathTop++;//走过的房间数加1						
						if(stack[top].canGo>0)//已走通过1次
						{
							top1++;//进栈，保留这个房间第1次走通的信息
							multiDirect[top1].next=temp.next;
							multiDirect[top1].i=temp.i;
							multiDirect[top1].j=temp.j;
						}
						stack[top].next=direct;//保存这个房间通往下一个房间的方向
	                    stack[top].canGo++;//走通的方向加1	        
						top++;//打通墙后，进栈，保存路径
						stack[top].i=i;
						stack[top].j=j;
						path[pathTop].i=i;
						path[pathTop].j=j;
						try
						{
						 repaint();	//重画图像，即对更改后的图像刷新		
						 if(audio!=null)//如果读取到了音频文件
						 audio.play();//播放拆墙的声音
						 Thread.sleep(second);
						}
						catch (Exception e)
						{
						}											
					}
				}
				else//四个方向都不可走
				{
					mg[temp.i][temp.j].property=-1;//该房设为已走过，但不通
					top--;//该方向不可走退栈，返加上一个房间	
					//回退后把当前房间的坐标加入path路径
					isPush=false;//再退栈				
					frontNext=temp.next;//保留退栈前的方向
					temp.count=0;//下一房间还保留被原先的数据，要进行初始化，所以方向个数置为0
					for(i=0;i<4;i++)
					temp.visit[i]=0;//四个方向置为未走过
					temp.next=-1;
					count++;
					step="步数:"+count;
					if(top==0)//退栈到了起点的时候，所有路径走完
					{
					  finish=true;
					}
					else//退回到终点前
					{//回退后把当前房间的坐标加入path路径
						path[pathTop].i=stack[top].i;
						path[pathTop].j=stack[top].j;
					}
					try
					{
					 isRepaint3=true;
					 repaint();	//退栈后立刻重画图像，即对更改后的图像刷新,如果睡眠后再刷新，这时睡眠后已进行了下一步探索，消除的就不是原本那次，原本那次还未消除	
					 if(audio!=null)//如果读取到了音频文件
					 audio.play();//播放加退的声音
					 Thread.sleep(second);
					}
					catch (Exception e)
					{
					}					
				}
			}
		}
	}
	public void quickCreateMazePath()//快速创建谜宫，x,y是谜宫的出发点，xi,却是谜宫的结束坐标
	{//注意x,y为二维数组的横坐标、纵坐标，但x却是第x列y是第y行
	    initMaze();//初始化谜宫数据
		top=-1;//栈顶
		mg[entryX][entryY].property=2;//表示已走过
		top++;//将开始坐标进栈
		stack[top].i=entryX;
		stack[top].j=entryY;
		while(top>-1)//还有路没走完，一直到谜宫所有的格都走完
		{
			temp=stack[top];//当前房间为栈顶存放的房间，从这出发
			if(temp.count<4)//四个方向还没走完
			{
				direct=(int)(4*Math.random());//随机产生0-3
				while(temp.visit[direct]==1)//该方向已走过，继续产生没走过的方向
				direct=(int)(4*Math.random());//随机产生0-3
                temp.count++;//走过的方向个数加1
				temp.visit[direct]=1;//该方向没走过，现在走了，设为已超过
				switch(direct)
				{
					case 0://规定向右方向
						i=temp.i;
						j=temp.j+1;//列数加1
						break;
					case 1://规定向下方向
						i=temp.i+1;
						j=temp.j;//行数加1
						break;
					case 2://规定向左方向
						i=temp.i;
						j=temp.j-1;//列数减1
						break;
					case 3://规定向上方向
						i=temp.i-1;
						j=temp.j;//行数减1
						break;
				}
				if(mg[i][j].property==0)//该房间可进
				{
					switch(direct)
					{
						case 0:
							mg[temp.i][temp.j].right=1;//向右可走，打通右墙
						    mg[i][j].left=1;//即当前房间的左墙打通
						break;
						case 1:
							mg[temp.i][temp.j].down=1;//向下可走，打通后墙
						    mg[i][j].up=1;//即当前房间的前墙打通						
						break;
						case 2:
							mg[temp.i][temp.j].left=1;//向左可走，打通左墙
						    mg[i][j].right=1;//即当前房间的右墙打通										
						break;
						case 3:
							mg[temp.i][temp.j].up=1;//向上可走，打通前墙
						    mg[i][j].down=1;//即当前房间的后墙打通						
						break;
					}
					mg[i][j].property=2;//表示已走过
					top++;//打通墙后，进栈，保存路径
					stack[top].i=i;
					stack[top].j=j;
				}
			}
			else//四个方向都不可走
			{
				mg[temp.i][temp.j].property=-1;//该房设为已走过，但不通
				top--;//该方向不可走退栈，返加上一个房间				
				temp.count=0;//下一房间还保留被原先的数据，要进行初始化，所以方向个数置为0
				for(i=0;i<4;i++)
					temp.visit[i]=0;//四个方向置为未走过
			}
		}
		finish=true;
		pullDownWall();//创建完成后拆墙
		drawStartAndEnd();//画开始点和结束点图
	}
	public void computerFindPath(int x,int y,int xi,int yi)//电脑自动寻找路径，x,y是谜宫的出发点，xi,却是谜宫的结束坐标
	{//注意x,y为二维数组的横坐标、纵坐标，但x却是第x列y是第y行
		 int next;//下一个方向
		 count=0;//步数初始化
		 boolean canIn;//可不可以进入房间的标记
		 cost="用时:0秒";
		 top=-1;//栈顶初始化
		 isBegin=false;
		 watch=new stopwatch(this);
		 t1=new Thread(watch);
		for(i=0;i<row*column;i++)//消除创建谜宫时栈的数据，进行初始化，再次用新的空栈
		{
			stack[i].count=0;//走过的方向数目初始化为0
			stack[i].next=-1;//往下一个房间的方向初始化为无，用-1表示
			for(j=0;j<4;j++)
				stack[i].visit[j]=0;//四个方向初始化为未走过
		}
		for(i=1;i<=row;i++)//消除创建谜宫时的路径
		{//保留每个房间四面墙是否被拆的信息
			for(j=1;j<=column;j++)
			{
				mg[i][j].property=0;//初始空心房间
			}
		}
		mg[x][y].property=2;//表示已走过
		top++;//将开始坐标进栈
		count++;
		stack[top].i=x;
		stack[top].j=y;
		t1.start();
		while(top>-1&&!find&&type==2)//还有路没走完，一直到谜宫所有的格都走完或走到终点
		{
			if(!isPause)//开始探索
			{
					if(count==1)
					{
						isBegin=true;
					}
					canIn=false;//初始化为不可进
					temp=stack[top];//当前房间为栈顶存放的房间，从这出发
					if(temp.i==xi&&temp.j==yi)//到达了终点位置,输出谜宫
					{
						find=true;
						repaint();//刷新一下显示“按开始进行新游戏”
						break;//找到终点，退出循环
					}
					if(temp.count<4)//四个方向还没走完
					{
						next=temp.next;//取出该房间已走过的方向
						next++;//往下一个房间的方向走
						temp.next=next;//保存当前房间刚刚走的下一个方向
						temp.count++;//走过的方向个数加1
						switch(next)
						{

							case 0://规定向右方向
								i=temp.i;
								j=temp.j+1;//列数加1
								if(mg[temp.i][temp.j].right==1&&mg[i][j].property==0)//右墙壁被拆了，且下一个房间可走
								canIn=true;
								break;
							case 1://规定向下方向
								i=temp.i+1;
								j=temp.j;//行数加1
								if(mg[temp.i][temp.j].down==1&&mg[i][j].property==0)//下墙壁被拆了，且下一个房间可走
								canIn=true;
								break;
							case 2://规定向左方向
								i=temp.i;
								j=temp.j-1;//列数减1
								if(mg[temp.i][temp.j].left==1&&mg[i][j].property==0)//左墙壁被拆了，且下一个房间可走
								canIn=true;
								break;
							case 3://规定向上方向
								i=temp.i-1;
								j=temp.j;//行数减1
								if(mg[temp.i][temp.j].up==1&&mg[i][j].property==0)//前墙壁被拆了，且下一个房间可走
								canIn=true;
								break;

						}
						if(canIn)//该房间可进
						{
							mg[i][j].property=2;//下一个房间通过，表示已走过
							top++;//打通墙后，进栈，保存路径
							count++;
							isPush=true;//进栈
						    step="步数:"+count;
							stack[top].i=i;
							stack[top].j=j;
							try
							{
							 repaint();	//重画图像，即对更改后的图像刷新		
							 if(audio!=null)//如果读取到了音频文件
							 audio.play();//播放移动的声音
							 Thread.sleep(second);
							}
							catch (Exception e)
							{
							}					
						}
					}
					else//四个方向都不可走
					{
						mg[temp.i][temp.j].property=-1;//该房设为已走过，但不通
						top--;//该方向不可走退栈，返加上一个房间				
						temp.count=0;//下一房间还保留被原先的数据，要进行初始化，所以方向个数置为0
						temp.next=-1;
						isPush=false;//退栈
						for(i=0;i<4;i++)
							temp.visit[i]=0;//四个方向置为未走过
						count++;
						step="步数:"+count;
						try
						{
						 repaint();	//重画图像，即对更改后的图像刷新		
						 if(audio!=null)//如果读取到了音频文件
						 audio.play();//播放移动的声音
						 Thread.sleep(second);
						}
						catch (Exception e)
						{
						}					
					}
				}
			}
	}
    public void peopleFind()//传过来移动的方向
	{
       if(!isPause)
		{
			if(count==0)//count为1时
			{
				isBegin=true;//标记已开始走了，可以显示走的步数，时间
			    new Thread(watch).start();//计时线程开启
			}
		   switch(flag)
			{
			   case 0:
				if(mg[currentX][currentY].right==1)//右墙被拆
				{
				   returnRoom(0,currentX,currentY);//将当前房间擦掉
				   currentY++;//向右移
				   count++;//步数加1
                   step="步数:"+count;
				   repaint();//刷新移动后画面
				   if(audio!=null)//如果读取到了音频文件
				   audio.play();//播放移动的声音
				}
				   break;
			   case 1:
				if(mg[currentX][currentY].down==1)//下墙被拆
				{
				   returnRoom(0,currentX,currentY);//将当前房间擦掉
				   currentX++;//向下移
				   count++;//步数加1
                   step="步数:"+count;
				   repaint();//刷新移动后画面
				   if(audio!=null)//如果读取到了音频文件
				   audio.play();//播放移动的声音
				}
				   break;
			   case 2:
				if(mg[currentX][currentY].left==1)//左墙被拆
				{
				   returnRoom(0,currentX,currentY);//将当前房间擦掉
				   currentY--;//向左移
				   count++;//步数加1
				   step="步数:"+count;
				   repaint();//刷新移动后画面
				   if(audio!=null)//如果读取到了音频文件
				   audio.play();//播放移动的声音
				}
				   break;
			   case 3:
				if(mg[currentX][currentY].up==1)//前墙被拆
				{
				   returnRoom(0,currentX,currentY);//将当前房间擦掉
				   currentX--;//向前移
				   count++;//步数加1
				   step="步数:"+count;
				   repaint();//刷新移动后画面
				   if(audio!=null)//如果读取到了音频文件
				   audio.play();//播放移动的声音
				}
				   break;
			}
			if(currentX==exitX&&currentY==exitY)
			{
				find1=true;
                JOptionPane.showMessageDialog(null,"恭喜，你到达了终点","过关了",JOptionPane.OK_CANCEL_OPTION);
			}
		}
	}
    public void createBufferImage()//创建谜宫的三张缓冲图
    {
    	//对缓冲图进行初始化
 	    g1[0]=image1.createGraphics();//从这个缓冲图中获得一支画笔
        g1[1]=image2.createGraphics();//从这个缓冲图中获得一支画笔
        g1[2]=image3.createGraphics();//从这个缓冲图中获得一支画笔
        for(k=0;k<3;k++)//初始化三张缓冲图
 		{
         	g1[k].setColor(mazeBackGroundColor);//设置画笔谜宫背景色
 			g1[k].fillRect(0,0,1300,730);//将整个缓冲图设置为背景黑色
 			g1[k].setColor(wallColor);//设置画笔墙壁色，用于画墙壁
 			//画边框，即外墙不可拆，左上顶点20，20，左下顶点20，pixel*row+20，右上顶点
 			//pixel*column+20，20，右下顶点pixel*column+20，20+pixel*row
 			g1[k].drawLine(20,20,pixel*column+20,20);//上边框
 			g1[k].drawLine(20,pixel*row+20,pixel*column+20,20+pixel*row);//下边框
 			g1[k].drawLine(20,20,20,pixel*row+20);//左边框
 			g1[k].drawLine(pixel*column+20,20,pixel*column+20,pixel*row+20);//右边框
 			//画网格线,内墙，可拆
 			//横网格
 			for(i=1;i<row;i++)//纵坐标
 			{
 					g1[k].drawLine(20,i*pixel+20,pixel*column+20,i*pixel+20);
 			}
 			//纵网格
 			for(i=1;i<column;i++)//横坐标
 			{
 					g1[k].drawLine(i*pixel+20,20,i*pixel+20,pixel*row+20);
 			}
 		} 
    }
    public void setNewColorBufferImage()//在颜色改变后，设置新颜色的缓冲图，谜宫不改变
    {
    	createBufferImage();//创建谜宫的三张缓冲图
    	if(type==3)//如果是第3种模式，设置为可拆
    		isPull=true;
    	pullDownWall();//拆墙
    	isPull=false;//拆完后，为下一轮进行创建进行初始化，设置为不可拆
    	if(type==1)//模式1,无路线
    	{
			if(pixel>=60&&isPictureExit)//如果房间像素不小于60并且图片都存在，画图片
			{
				g1[0].drawImage(imageStart,20+(int)((pixel-60)/2)+(currentY-1)*pixel,20+(int)((pixel-60)/2)+(currentX-1)*pixel,this);//画人物图标，放在当前坐标
				//画终点图片
				g1[0].drawImage(imageEnd,20+(int)((pixel-60)/2)+(exitY-1)*pixel,20+(int)((pixel-60)/2)+(exitX-1)*pixel,this);//画结束图标，放在结束坐标
			}
			else//否则，结束点画开始点颜色正方形代表当前房间位置，结束色正方形代表结束位置
			{
				g1[0].setColor(startColor);//设置画笔为开始点颜色
				g1[0].fillRect(20+(currentY-1)*pixel,20+(currentX-1)*pixel,pixel,pixel);
				g1[0].setColor(endColor);//设置画笔为结束点颜色
				//画结束点颜色正方形开作为结束点
				g1[0].fillRect(20+(exitY-1)*pixel,20+(exitX-1)*pixel,pixel,pixel);						
			}
    	}
    	if(type==2)//模式2,先画路线，后画当前点和结束点
    	{
    		g1[1].setColor(routeColor);//设置画笔路线颜色
			for( k=0;k<top;k++)//走过的路线
			{
				i=stack[k].i;//路径中取出当前下标对应房间的坐标
				j=stack[k].j;
				switch(stack[k].next)//方向   （20，20）是谜宫左上角的坐标，所以每个横坐标加20，纵坐标加20，i-1,j-1,是前面i-1个房间和左边j-1个房间的宽度，再加上这个房间中心的宽度(int)(pixel/2)
				{/*注意：i行j列的方块为mg[j][i],但横坐标为i,纵坐标为j
				画路线，线路为从当前房间的中心到下一个房间的中心，当前房间中心为((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);
				左边房间中心点坐标((j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20)
				右边房间中心点坐标(j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20)
				前边房间中心点坐标((j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20)
				后边房间中心点坐标((j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20)
				*/						
					case 0:g1[1].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//当前房间往右方向右房间
					case 3:g1[1].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20);break;//当前房间往上方向前面房间
					case 1:g1[1].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20);break;//当前房间往下方向后面房间
					case 2:g1[1].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//当前房间往左方向左边房间
					default:break;
				}
			}
			if(pixel>=60&&isPictureExit)//如果房间像素不小于60并且图片都存在，画图片在当前位置画人物图
			{
				g1[1].drawImage(imageStart,(stack[top].j-1)*pixel+20+(int)((pixel-60)/2),(stack[top].i-1)*pixel+20+(int)((pixel-60)/2),this);
				g1[1].drawImage(imageEnd,20+(int)((pixel-60)/2)+(exitY-1)*pixel,20+(int)((pixel-60)/2)+(exitX-1)*pixel,this);//画结束图标，放在结束坐标			
			}
			else//否则，结束点画开始点颜色正方形代表当前房间位置，结束色正方形代表结束位置
			{
				g1[1].setColor(startColor);
				g1[1].fillRect(20+(stack[top].j-1)*pixel,20+(stack[top].i-1)*pixel,pixel,pixel);
				g1[1].setColor(endColor);//设置画笔为结束点颜色
				//画结束点颜色正方形开作为结束a点
				g1[1].fillRect(20+(exitY-1)*pixel,20+(exitX-1)*pixel,pixel,pixel);						
			}	
    	}
       	if(type==3)//模式3,先画路线，后画当前点和结束点
    	{
       		g1[2].setColor(routeColor);//设置画笔路线颜色
       		System.out.printf("pathtop=%d\n",pathTop);
			for( k=0;k<pathTop;k++)//走过的路线
			{
				i=path[k].i;//路径中取出当前下标对应房间的坐标
				j=path[k].j;
				System.out.printf("i=%d,j=%d,next=%d\n",i,j,path[k].next);
				switch(path[k].next)//方向  
				{
					case 0:g1[2].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//当前房间往右方向右房间
					case 3:g1[2].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20);break;//当前房间往上方向前面房间
					case 1:g1[2].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20);break;//当前房间往下方向后面房间
					case 2:g1[2].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//当前房间往左方向左边房间
					default:break;
				}
			}  	
			if(pixel>=60&&isPictureExit)//如果房间像素不小于60并且图片都存在，画图片在当前位置画人物图
			{
				g1[2].drawImage(imageStart,(stack[top].j-1)*pixel+20+(int)((pixel-60)/2),(stack[top].i-1)*pixel+20+(int)((pixel-60)/2),this);
			}
			else//否则，结束点画开始点颜色正方形代表当前房间位置
			{
				g1[2].setColor(Color.white);
				g1[2].fillRect(20+(stack[top].j-1)*pixel,20+(stack[top].i-1)*pixel,pixel,pixel);
			}	
    	}
    }
	public void pullDownWall()//拆墙
	{
		for(int k=0;k<3;k++)
		{
			if(k<2||isPull)//谜宫创建完成后前2幅缓冲图进行拆墙，第3幅缓冲图还不能拆，因为要按了开始后才进行显示拆的过程,或颜色改变重建缓冲图的时候才拆
			{
				for(i=row;i>=1;i--)//为何要从最后一行的房间拆起而不从第1行拆起，这是因为
				{
				/*
				  经过在放大境下观察发现，线段也有宽，不是很大，假设画一个60像正方形和画一条60像素长线线段，发现线段的长度
		         比正方形的长度长，多出的长度恰好是线段的微小宽度，大约0.5左右，所以用线段拼的正方形(绿色)比直接画的正方形(黑色)要大1，
		         因此因黑色正方形进行覆盖绿色正方形时不能完全覆盖，还剩下2条宽度为0.5长度为60为长方形未覆盖，这宽度跟线段宽度一样
		         可以这么说假设画1个黑色正方形60像素，画同像素的线段却有60.5像素，因为很小年不出差别，只能在放大境（微软的放大境软件）下
		         看出差别（不协调），如果从第1行开始用黑色正方形进行覆盖每个房间，哪么正在进行覆盖房间的哪一行会把上一行已经补好的墙（用60像素实则有60.5像素)
		         覆盖掉0.5像素这2面增这间减少了1个0.5像素的正方形，形成1个小间隔，而从下到上则不会，因为上一行它覆盖不了南墙和西墙，因此影响不了下一行已补好的墙
		         而从上到下，它会覆盖东墙和北墙因此会把上一行已补好的2相邻墙之间覆盖掉一个0.5像素正方形，变得这2面墙不是直接相连不和协
				*/			
					for(j=1;j<=column;j++)
					{
						returnRoom(k,i,j);//拆墙先将房间用背景色覆盖，这样四面墙都倒了，再将不用拆的墙补回去
					}
				}			
			}
	
		}
	}
	public void drawStartAndEnd()//画开始点和结束点
	{		//画起始和结束位置，必须在拆完墙后，否则起始和结束的图会被背景色覆盖
		for(k=0;k<2;k++)//第3种模式下无结束图
		{
			if(pixel>=60&&isPictureExit)//如果房间像素不小于60并且图片都存在              
			{//画图片在当前位置画人物图
			  g1[k].drawImage(imageStart,20+(int)((pixel-60)/2)+(entryY-1)*pixel,20+(int)((pixel-60)/2)+(entryX-1)*pixel,this);//画人物图标，放在开始坐标
				//画终点图片
			  g1[k].drawImage(imageEnd,20+(int)((pixel-60)/2)+(exitY-1)*pixel,20+(int)((pixel-60)/2)+(exitX-1)*pixel,this);//画结束图标，放在结束坐标
			}			 
			else//否则结束点
			{
				//画开始点颜色的正方形作为起点
				g1[k].setColor(startColor);
				g1[k].fillRect(20+(entryY-1)*pixel,20+(entryX-1)*pixel,pixel,pixel);			
				g1[k].setColor(endColor);//设置画笔为结束点颜色
				//画结束点颜色正方形开作为结束点
				g1[k].fillRect(20+(exitY-1)*pixel,20+(exitX-1)*pixel,pixel,pixel);				
			}							
		}
	}
	public void returnRoom(int k,int i,int j)//将某个房间还原,k是缓冲图下标，i,j是房间的下标
	{
	    /*经过在放大境下观察发现，线段也有宽，不是很大，假设画一个60像正方形和画一条60像素长线线段，发现线段的长度
	     比正方形的长度长，多出的长度恰好是线段的微小宽度，大约0.5左右，所以用线段拼的正方形(绿色)比直接画的正方形(黑色)要大1，
	     因此因黑色正方形进行覆盖绿色正方形时不能完全覆盖，还剩下2条宽度为0.5长度为60为长方形未覆盖，这宽度跟线段宽度一样，因此有
	     2条线段未覆盖，分别是后面墙和右面墙未覆盖，所以这两2面墙没拆，不用再补上，哪这2面墙若本来应该拆掉却还存在，但拆墙是每个房间都进行
	     当所有房间要黑色背景覆盖完的时候，整个大谜宫只有围墙的南面和西南没拆，围墙是不能拆的，要拆的是房间的墙，而这是不靠近房围墙的所有房间南面墙
	     和西面墙都拆了（在拆临近房间的时候把它拆了），只有靠近围墙的房间南墙和西墙没拆，这2面墙是围墙组成部分所以不能拆
	    */
		g1[k].setColor(mazeBackGroundColor);//设置画笔为谜宫背景色
		g1[k].fillRect(20+(j-1)*pixel,20+(i-1)*pixel,pixel,pixel);//将未退栈前的房间画上黑色背景
		g1[k].setColor(wallColor);//设置画笔为墙壁颜色
		if(mg[i][j].left==0)//左边有墙
		g1[k].drawLine(20+(j-1)*pixel,20+(i-1)*pixel,20+(j-1)*pixel,20+i*pixel);
		//if(mg[i][j].right==0)//右边有墙    注释是因为这两2面墙没拆，不用再补上
		//g1[k].drawLine(20+j*pixel,20+(i-1)*pixel,20+j*pixel,20+i*pixel);
		if(mg[i][j].up==0)//前边有墙
		g1[k].drawLine(20+(j-1)*pixel,20+(i-1)*pixel,20+j*pixel,20+(i-1)*pixel);
		//if(mg[i][j].down==0)//后边有墙    注释是因为这两2面墙没拆，不用再补上
		//g1[k].drawLine(20+(j-1)*pixel,20+i*pixel,20+j*pixel,20+i*pixel);
	}
	public void returnRoute(int k,int top)//房间擦掉还原后，还原成原来的路线，k是缓冲图下标,top是栈顶下标
	{//每个房间有2条路线，1条是其它房间通往这个房间路线，另1条是这个房间通往下一个房间路线
		int m,n;//m是当前房间下标，n是当前房间方向
		//画出进栈过程走的方向，因为进栈过程中把进栈前的房间给擦掉了，这个房间还包括上上个房间延伸出来的一部分路线，要画出来					
		//总之每个房间是有两条路线的，分别是前一房间到本房间的路线及本房间到下一个房间的路线，所以擦掉某个房间要重画这2条路线
		g1[k].setColor(routeColor);//设置笔为线践颜色，用来画走过的路线	    	
		if(isPush)
			m=top-2;//是进栈的话，补上进栈前2个房间的路线
		else
			m=top;//是退栈的话，补上当前房间和退栈前房间的路线
		for(int a=1;a<=2;m++,a++)//分别画上未进栈前2个房间的路线
		{
			if(m>=0)//为了防止下标为-1，即刚走了2步，top为1，这时只要补前一个房间路线就可以，因为没有前2个房间
			{
				i=stack[m].i;//取出当前房间下标
				j=stack[m].j;
				n=stack[m].next;
				if(m==top+1)//m=top+1意昧着是退栈，要补回退栈前路线，但每退栈1次就把痕迹清除，stack[top+1]没保留top+1房间方向，所以用frontNext专门保留退栈前的方向
				   n=frontNext;
				switch(n)//方向   （20，20）是谜宫左上角的坐标，所以每个横坐标加20，纵坐标加20，i-1,j-1,是前面i-1个房间和左边j-1个房间的宽度，再加上这个房间中心的宽度(int)(pixel/2)
				{				
					case 0:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//当前房间往右方向右房间
					case 3:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20);break;//当前房间往上方向前面房间
					case 1:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20);break;//当前房间往下方向后面房间
					case 2:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//当前房间往左方向左边房间
					default:break;
				}
			}
		}
		if(k==2)//在第2 种模式下进行过进栈和退栈，这个房间走过2个方向，前面只还原了走的第2个方向，还没还原第1个方向
		{
			if((isPush==true&&stack[top-1].canGo>1)||(isPush==false&&stack[top+1].canGo>1))
			{//如果是进栈，进栈前的房间有超过2个方向或是退栈，退栈前的房间有超过2个方向
				i=multiDirect[top1].i;
				j=multiDirect[top1].j;//取出进退栈前的坐标
	            //multiDirect[top1].next保留最近的多方向房间的方向
				switch(multiDirect[top1].next)//方向   （20，20）是谜宫左上角的坐标，所以每个横坐标加20，纵坐标加20，i-1,j-1,是前面i-1个房间和左边j-1个房间的宽度，再加上这个房间中心的宽度(int)(pixel/2)
				{				
					case 0:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//当前房间往右方向右房间
					case 3:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20);break;//当前房间往上方向前面房间
					case 1:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20);break;//当前房间往下方向后面房间
					case 2:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//当前房间往左方向左边房间
					default:break;
				}
				if(isPush==false&&isRepaint3)//在退栈情况下并且在第3种模式下刷新，不是时间线程的刷新
				{
				   top1--;//进栈时不减，因为退栈时再次经过这个房间要重梳妆打扮画2个方向路线，而退栈后不再走这个房间路线，所以减1，并且消除之前top1的方向
				}			
			}
		}
		if(!isPush&&isRepaint3)
		{
		  stack[top+1].canGo=0;//退栈前将栈顶可走的步数初始化为0，即消除退栈前房间的信息，为何在此处消除不在刚退的时候消除，因为前面还要用到canGo>1这个
		  //判断条件，若消除则无法实现将某个房间的2个方向画出，所以2个方向画出后，再消除方向个数
		}
	}
	public void drawMessage(int k)//画出谜宫的信息，k为哪种模式
	{
		if(isBegin)
		{
			g1[k].setColor(fontBackGroundColor);//设置画笔为字体背景色
			g1[k].fillRect(1130,80,120,20);
			g1[k].fillRect(1130,110,120,20);
			g1[k].setColor(fontColor);//设置画笔为字体颜色
			g1[k].drawString(cost,1133,92);//画时间信息
			g1[k].drawString(step,1133,122);//画走的步数
		}
		g1[k].setColor(fontBackGroundColor);//设置画笔为字体背景色
		g1[k].fillRect(550,640,120,20);
		g1[k].fillRect(1130,20,120,20);
		g1[k].fillRect(1130,50,120,20);
		g1[k].setColor(fontColor);//设置画笔为字体颜色
        g1[k].drawString(str,553,652);//画开始暂停的状态信息
        g1[k].drawString(time,1133,32);//画时间 信息
        g1[k].drawString(model,1133,62);//画模式类型信息
	}
	public void paint(Graphics g)
	{
		if(type==2)//谜宫电脑探索模式
		{//图片的像素为60，将图片放房间中间，则(int)((pixel-60)/2)为图片距离房间左墙和右墙的距离，所以图片坐标为
			if(top>0)//谜宫探索已开始,且有路径
			{
				//走过的路线
				if(isPush)//如果是进栈
				{
					i=stack[top-1].i;//取出当前对象进栈前下标对应房间的坐标
					j=stack[top-1].j;
					returnRoom(1,i,j);//将进栈前下标对应房间还原
					returnRoute(1,top);//房间擦掉还原后，还原成原来的路线
				}
				else//退栈的话要将走过的痕迹擦掉，还原原来的图象
				{
					for(k=top;k<=top+1;k++)//取出当前对象未退栈前和退栈后的下标对应房间的坐标
					{
						i=stack[k].i;
						j=stack[k].j;
						returnRoom(1,i,j);//将这2个房间还原
					}
				}				
				if(pixel>=60&&isPictureExit)//如果房间像素不小于60并且图片都存在
				{//画图片在当前位置画人物图
					g1[1].drawImage(imageStart,(stack[top].j-1)*pixel+20+(int)((pixel-60)/2),(stack[top].i-1)*pixel+20+(int)((pixel-60)/2),this);
			    }	
				else//否则，结束点画开始点颜色正方形
				{
					g1[1].setColor(startColor);
					g1[1].fillRect(20+(stack[top].j-1)*pixel,20+(stack[top].i-1)*pixel,pixel,pixel);
				}
			}
			if(find)
			{
			    str="按空格开始新游戏";
			}
			drawMessage(1);//画谜宫信息
            g.drawImage(image2,0,0,this);//将整个缓冲组合图贴在JFrame上
		}
		if(type==3)//谜宫电脑创建模式
		{
			if(isPush)//进栈
			{
				if(top>0)//top>0后才进行更改图像，<0时top-1数组下标会超出
				{
					i=stack[top-1].i;//取出当前对象进栈前下标对应房间的坐标
					j=stack[top-1].j;
					returnRoom(2,i,j);//将进栈前下标对应房间还原
				    returnRoute(2,top);//房间擦掉还原后，还原成原来的路线					
				}
			}
			else
			{
				if(count>1&&isRepaint3==true)//谜宫创建完成后的最后一步是退栈，默认3模式下继续创建，这时count=1,只不过
					//因为暂停只走了一步，而时间线程每1秒刷新1次画面，这时会把top[1]（top[1]在退栈时数据已经清零）房间用背景覆盖
				{//所以要求count>1,走了2步后才算谜宫正式开始创建
					i=stack[top+1].i;//取出当前对象退栈下标对应房间的坐标
					j=stack[top+1].j;
					returnRoom(2,i,j);//将退栈前下标对应房间还原
					returnRoute(2,top);//房间擦掉还原后，还原成原来的路线					
				}
			}
			if(top>-1)//谜宫探索已开始,且有路径
			{
				if(pixel>=60&&isPictureExit)//如果房间像素不小于60并且图片都存在，画图片在当前位置画人物图              
					g1[2].drawImage(imageStart,(stack[top].j-1)*pixel+20+(int)((pixel-60)/2),(stack[top].i-1)*pixel+20+(int)((pixel-60)/2),this);
				else//否则，结束点画开始点颜色的正方形
				{
					g1[2].setColor(startColor);
					g1[2].fillRect(20+(stack[top].j-1)*pixel,20+(stack[top].i-1)*pixel,pixel,pixel);
				}
			}		
			if(finish)
			{
				str="按空格开始创建新谜宫";
			}
			drawMessage(2);//画谜宫信息
            g.drawImage(image3,0,0,this);//将整个缓冲组合图贴在JFrame上
		}
		if(type==1)//谜宫人工探索模式
		{
			if(pixel>=60&&isPictureExit)//如果房间像素不小于60并且图片都存在，画图片
			{
				g1[0].drawImage(imageStart,20+(int)((pixel-60)/2)+(currentY-1)*pixel,20+(int)((pixel-60)/2)+(currentX-1)*pixel,this);//画人物图标，放在开始坐标
			}
			else//否则，结束点画开始点颜色正方形代表当前房间位置，红色正方形代表结束位置
			{
				g1[0].setColor(startColor);
				g1[0].fillRect(20+(currentY-1)*pixel,20+(currentX-1)*pixel,pixel,pixel);
			}
			if(find1)
			{
				str="按空格开始新游戏";
			}
			drawMessage(0);//画谜宫信息
			g.drawImage(image1,0,0,this);//将整个缓冲组合图贴在JFrame上
		}
	}
}
class maze3
{
	public static void main(String args[])throws Exception
	{
		new puzzle();
	}
}