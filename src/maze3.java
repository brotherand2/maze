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
  �涨0��ʾ�ҷ���1��ʾ�£�2��ʾ��3��ʾ��
*/
class stopwatch implements Runnable //���
{
	int second=-1;//��¼��������������ʼ��Ϊ-1
	migong mg;//�չ���
	public stopwatch(migong mg)
	{
		this.mg=mg;//���չ��ഫ���ĵ�ַ�������չ���
	}
	public void run()
	{
		while((!mg.find&&mg.type==2)||(mg.type==3&&!mg.finish)||(mg.type==1&&!mg.find1))//������ģʽ�²��һ�δ�����չ���δ�������չ�ʱ��ʾ������ʱ��
		{
			if(!mg.isPause)//���¿�ʼ����ʱ
			{
				try
				{
			    	second++;//������1
					mg.cost="��ʱ:"+Integer.toString(second)+"��";//ʱ�侭������������Ϊ�ַ�����ʽ
					Thread.sleep(1000);//ÿ1���¼1��
				}
				catch (Exception e)
				{
				}
			}
		}
	}
}
class MyTime extends Thread//�ҵ�ʱ���߳�
{
	String  time=null;//ʱ��
	migong mg;//�չ���
	public MyTime(migong mg)
	{
		this.mg=mg;
	}
	public void run()//��дrun����
	{
		while(true)
		{
			try
			{
				Thread.sleep(1000);//ÿ��1000���룬��1����ʾ
			    SimpleDateFormat format=new SimpleDateFormat("ʱ��:HH:mm:ss");//�趨ʱ���ʽ
	            Date date=new Date();//������
                time=format.format(date);//������ʱ�䰴�Լ�Ҫ��ĸ�ʽת�����ַ���
				mg.time=time;//������ʱ��
				mg.isRepaint3=false;
				mg.repaint();//ˢ�»��棬��ʾ��ʱ��
			}
			catch (Exception e)
			{
			}
		}
	}
}
class puzzle  extends JFrame implements ActionListener
{//�չ�������
	JMenuBar bar=new JMenuBar();//�˵���
	JMenuItem item[]=new JMenuItem[11];//�����˵�
	JMenu menu[]=new JMenu[3];//�˵���
	JColorChooser colorSelect=new JColorChooser();//JColorChooser �ṩһ�����������û�������ѡ����ɫ�Ŀ���������
	int second;//�ƶ����ٶȣ����ٺ�����һ��
	int pixel;//�������أ�
	migong mg=new migong();
	public puzzle()
	{
		super("��ջ������չ�");
		menu[0]=new JMenu("�ļ�");//ʵ��������ʼ���˵�
		menu[1]=new JMenu("����");
		menu[2]=new JMenu("����");
		item[0]=new JMenuItem("�˳�");
		item[1]=new JMenuItem("��������");
		item[2]=new JMenuItem("���ڰ�Ȩ...");
		item[3]=new JMenuItem("�����ٶ�����ģʽ");
		item[4]=new JMenuItem("�����չ�������ɫ");
		item[5]=new JMenuItem("����ǽ����ɫ");
		item[6]=new JMenuItem("����������ɫ");
		item[7]=new JMenuItem("�������屳����ɫ");
		item[8]=new JMenuItem("����·����ɫ");
		item[9]=new JMenuItem("���ÿ�ʼ������ɫ");
		item[10]=new JMenuItem("���ý���������ɫ");	
		menu[0].add(menu[2]);
		menu[0].add(item[0]);//ʵ��������ʼ�������˵���Ŀ
		menu[1].add(item[1]);
		menu[1].add(item[2]);
		menu[2].add(item[3]);
		menu[2].add(item[4]);
		menu[2].add(item[5]);
		menu[2].addSeparator();//���·ָ���׷�ӵ��˵���ĩβ��	
		menu[2].add(item[6]);
		menu[2].add(item[7]);
		menu[2].add(item[8]);
		menu[2].addSeparator();//���·ָ���׷�ӵ��˵���ĩβ��
		menu[2].add(item[9]);
		menu[2].add(item[10]);
		item[0].addActionListener(this);//���������
		item[1].addActionListener(this);//���������
		item[2].addActionListener(this);//���������
		item[3].addActionListener(this);//���������
		item[4].addActionListener(this);//���������
		item[5].addActionListener(this);//���������
		item[6].addActionListener(this);//���������
		item[7].addActionListener(this);//���������
		item[8].addActionListener(this);//���������
		item[9].addActionListener(this);//���������
		item[10].addActionListener(this);//���������
		bar.add(menu[0]);//���˵��ŵ��˵�����
		bar.add(menu[1]);
        addKeyListener(new KeyAdapter(){//������̼�����
		public void keyTyped(KeyEvent e)//�����̰����ɿ�ʱ
			{
               if(e.getKeyChar()==' ')
				{
                    if(mg.isStart)
					{					
						if(mg.isPause)
						{
							mg.str="���ո���ͣ";
							mg.isPause=false;
						}
						else
						{
							mg.str="���ո�ʼ";
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
					if(e.getKeyCode()==KeyEvent.VK_RIGHT)//������
					{
					   mg.flag=0;
					   mg.peopleFind();//�����ƶ�
					}
					if(e.getKeyCode()==KeyEvent.VK_DOWN)//������
					{
						mg.flag=1;
					    mg.peopleFind();//�����ƶ�
					}
					if(e.getKeyCode()==KeyEvent.VK_LEFT)//������
					{
						mg.flag=2;
					    mg.peopleFind();//�����ƶ�
					}
					if(e.getKeyCode()==KeyEvent.VK_UP)//������
					{
						mg.flag=3;
					   mg.peopleFind();//�����ƶ�
					}
				}
			}
		});
		setJMenuBar(bar);//���ò˵���
		setSize(1300,730);//���ô�С
        setResizable(false);//���ò��ɼ�
		add(mg);//������壬����ϻ����չ�
		setVisible(true);//��ʾ����
		new SET(mg);//ʵ����������,�ڳ���һ��ʼ�����������û�����
		new MyTime(mg).start();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�����˳�Ӧ�ó�����Ĭ�ϴ��ڹرղ���
	}
	public boolean ifColorChange(Color a,Color b)//��ɫ�Ƿ�ı�,a,b�ֱ��Ǹı�ǰ�͸ı�����ɫ���ж��Ƿ�ͬ
	{
		mg.isColorChange=(a==b)?false:true;//��ɫ��ͬ��������ɫ���䣬���򣬷�����ɫ�ı�
        return mg.isColorChange; 
	}
	public void actionPerformed(ActionEvent e)
	{
        if(e.getSource()==item[0])
		{
			System.exit(0);//�˳�
		}
        if(e.getSource()==item[1])//����
		{
		}
        if(e.getSource()==item[2])//���ڰ�Ȩ
		{
		}
        if(e.getSource()==item[3])//�����ٶȣ�ʱ�䣬ģʽ
		{
			mg.setPause(true);
			new SET(mg);
		}
        if(e.getSource()==item[4])//�����չ�������ɫ
		{
  		  Color color=JColorChooser.showDialog(this,"ѡ���չ�������ɫ",Color.black);//ʾ����ɫΪĬ�ϱ�����ɫ��ɫ
		  if(color!=null)//���ѡ��������һ����ɫ
		  {
			  mg.isColorChange=ifColorChange(mg.mazeBackGroundColor,color);
			  mg.mazeBackGroundColor=color;//�����չ�������ɫΪ��ѡ�����ɫ
			  if(mg.isColorChange)//��ɫ�ı�
				  mg.setNewColorBufferImage();//����ɫ�ı����������ɫ�Ļ���ͼ���չ����ı�
			  mg.repaint();//ˢ�»�������ʾ����ɫ
		  }
		}
        if(e.getSource()==item[5])//����ǽ����ɫ
		{
        	  Color color=JColorChooser.showDialog(this,"ѡ���չ�ǽ����ɫ",Color.green);//ʾ����ɫΪĬ��ǽ����ɫ��ɫ
    		  if(color!=null)//���ѡ��������һ����ɫ
    		  {
    			  mg.isColorChange=ifColorChange(mg.wallColor,color);    			  
    			  mg.wallColor=color;//�����չ�ǽ����ɫΪ��ѡ�����ɫ
    			  if(mg.isColorChange)//��ɫ�ı�
    				  mg.setNewColorBufferImage();//����ɫ�ı����������ɫ�Ļ���ͼ���չ����ı�    			  
    			  mg.repaint();//ˢ�»�������ʾ����ɫ
    		  }
		}
        if(e.getSource()==item[6])//����������ɫ��������setNewColorBufferImage()��Ϊ������ɫ��ʱ���߳�ÿ1���ػ�1�Σ���ǽ�ڡ�·��ֻ�����ߵ�ʱ��Ÿ���
		{
        	  Color color=JColorChooser.showDialog(this,"ѡ��������ɫ",Color.black);//ʾ����ɫΪĬ��������ɫ��ɫ
    		  if(color!=null)//���ѡ��������һ����ɫ
    		  {
    			  mg.isColorChange=ifColorChange(mg.fontColor,color);   			  
    			  mg.fontColor=color;//����������ɫΪ��ѡ�����ɫ
    			  mg.repaint();//ˢ�»�������ʾ����ɫ
    		  }
		}
        if(e.getSource()==item[7])//�������屳����ɫ��������setNewColorBufferImage()��Ϊ���屳����ɫ��ʱ���߳�ÿ1���ػ�1�Σ���ǽ�ڡ�·��ֻ�����ߵ�ʱ��Ÿ���
		{
        	  Color color=JColorChooser.showDialog(this,"ѡ�����屳����ɫ",Color.white);//ʾ����ɫΪĬ�����屳����ɫ��ɫ
    		  if(color!=null)//���ѡ��������һ����ɫ
    		  {
    			  mg.isColorChange=ifColorChange(mg.fontBackGroundColor,color);    			  
    			  mg.fontBackGroundColor=color;//�������屳����ɫΪ��ѡ�����ɫ
    			  mg.repaint();//ˢ�»�������ʾ����ɫ
    		  }
		}
        if(e.getSource()==item[8])//����·����ɫ
		{
        	  Color color=JColorChooser.showDialog(this,"ѡ��·����ɫ",Color.red);//ʾ����ɫΪĬ��·����ɫ��ɫ
    		  if(color!=null)//���ѡ��������һ����ɫ
    		  {
    			  mg.isColorChange=ifColorChange(mg.routeColor,color);   			  
    			  mg.routeColor=color;//�����չ�·����ɫΪ��ѡ�����ɫ
    			  if(mg.isColorChange&&mg.type!=1)//��ɫ�ı䲢���ǵ�2��3��ģʽ
    				  mg.setNewColorBufferImage();//����ɫ�ı����������ɫ�Ļ���ͼ���չ����ı�
    			  mg.repaint();//ˢ�»�������ʾ����ɫ
    		  }
		}
        if(e.getSource()==item[9])//���ÿ�ʼ������ɫ
		{
        	  Color color=JColorChooser.showDialog(this,"ѡ��ʼ������ɫ",Color.white);//ʾ����ɫΪĬ�Ͽ�ʼ������ɫ��ɫ
    		  if(color!=null)//���ѡ��������һ����ɫ
    		  {
    			  mg.isColorChange=ifColorChange(mg.startColor,color);    			  
    			  mg.startColor=color;//�����չ���ʼ������ɫΪ��ѡ�����ɫ
    			  if(mg.isColorChange&&mg.pixel<60)//��ɫ�ı�,������С��60
    				  mg.setNewColorBufferImage();//����ɫ�ı����������ɫ�Ļ���ͼ���չ����ı�
    			  mg.repaint();//ˢ�»�������ʾ����ɫ
    		  }
		}
        if(e.getSource()==item[10])//���ý���������ɫ
		{
        	  Color color=JColorChooser.showDialog(this,"ѡ���չ�����������ɫ",Color.red);//ʾ����ɫΪĬ�Ͻ���������ɫ��ɫ
    		  if(color!=null)//���ѡ��������һ����ɫ
    		  {
    			  mg.isColorChange=ifColorChange(mg.endColor,color);    			  
    			  mg.endColor=color;//���ý���������ɫΪ��ѡ�����ɫ
    			  if(mg.isColorChange&&mg.pixel<60&&mg.type!=3)//��ɫ�ı�,������С��60�Ҳ��ǵ�3��ģʽ����Ϊ��3��ģʽ���յ�
    				  mg.setNewColorBufferImage();//����ɫ�ı����������ɫ�Ļ���ͼ���չ����ı�   			 
    			  mg.repaint();//ˢ�»�������ʾ����ɫ
    		  }
		}
	}
}
class SET extends JDialog implements ActionListener ,ItemListener//�����࣬����ʱ�䣬�ٶ�,���������
{
	JLabel lab1=new JLabel("�� �� ����");
	JLabel lab2=new JLabel("�ٶ� (����)");
	JButton but1=new JButton("ȷ��");
	JButton but2=new JButton("�˳�");
	JTextField field1=new JTextField();//��д�� �� ����
	JTextField field2=new JTextField();//��д�ٶ�
	int second;//�ƶ����ٶȣ����ٺ�����һ��
	int pixel;//��������
	int type;//��Ϸ����
	JPanel pan=new JPanel();//��ѡ���˹�̽�����ǵ���̽�������
    JRadioButton rbut1=new JRadioButton("��ʱ����");
    JRadioButton rbut2=new JRadioButton("������ʾ");//Ĭ��ѡ������Զ�Ѱ·��
    JRadioButton rbut3=new JRadioButton("�����չ���ʾ");
    migong mg;
    ButtonGroup group=new ButtonGroup();//��ѡ��ť��,ֻ�мӵ��������ʵ�ֵ�ѡ�������Ƕ�ѡ
    public SET(migong mg)
	{
		this.mg=mg;//���չ�ͼ��ַ(mg)���չ�������
        this.type=mg.type;//���ø����������͡����ء�˯��ʱ��
		this.pixel=mg.pixel;
		this.second=mg.second;
		switch(mg.type)//�ڵ�ѡ��ť����ѡ���չ���ǰģʽ
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
		setLayout(null);//ȡ��Ĭ�ϵĲ��ֹ�����
        setTitle("����");//���ñ���
        lab1.setBounds(20,20,80,20);
		lab2.setBounds(20,50,80,20);
        field1.setBounds(100,20,60,20);
		field2.setBounds(100,50,60,20);
        pan.setBounds(20,80,290,60);
		pan.setLayout(new FlowLayout());
		pan.setBorder(BorderFactory.createTitledBorder("��Ϸģʽ"));//�ٴ���һ���б���ı߿�
		but1.setBounds(40,150,60,20);
		but2.setBounds(120,150,60,20);
        but1.addActionListener(this);
        but2.addActionListener(this);
        field1.setText(""+pixel);//��ʾ��������
		field2.setText(""+second);//��ʾ�ٶ�
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
         // ���û���ѡ����ȡ��ѡ��ĳ��ʱ���á�
	{
		if(e.getSource()==rbut1)
		{
			type=1;//��������Ϊ�˹�̽��
		}
		if(e.getSource()==rbut2)
		{
			type=2;//��������Ϊ����̽��
		}
		if(e.getSource()==rbut3)
		{
			type=3;//��������Ϊ�ۿ��չ����ɹ���
		}

	}
	public void actionPerformed(ActionEvent e)//��������ʱ����
	{
		if(e.getSource()==but1)//ȷ��
		{
			String str=field1.getText();//�õ�ÿ�������������
			String str1=field2.getText();//�õ��ٶȣ�����
			try
			{
  			     pixel=Integer.parseInt(str);//ת�����ַ���
				 second=Integer.parseInt(str1);//ת�����ַ���
				if(pixel<2||pixel>540)//����������Χ
				{
                   JOptionPane.showMessageDialog(null,"�����չ��������Ϊ540����С���ز��õ���3���س�����Χ��","��ʾ...",JOptionPane.WARNING_MESSAGE);
					return ;
				}
				if(second<10)//����̫�ͣ�����ʱ���޸�
				{
                   JOptionPane.showMessageDialog(null,"���ʱ�䲻�õ���10����ˢ��һ�Σ���Ϊ����10���벻��ʱ���޸ı仯���������ô���10������ٶ�\n�õ������㹻�޸ı仯�������ֳ�������ֶ����������Ϊ����ʱ���޸ı仯����û��������һ�κۼ��ֽ�����һ���޸�","��ʾ...",JOptionPane.WARNING_MESSAGE);
				   return ;
				}			
			}
			catch (NumberFormatException e1)
			{
                JOptionPane.showMessageDialog(null,"ֻ���������֣��������������������ĸ�����ŵȡ�","��ʾ...",JOptionPane.WARNING_MESSAGE);
			    return;
			}			
			mg.isTypeChange=false;//��ʼ��Ϊ����δ�ı�
			if(mg.type!=type)//�չ���ǰ������ѡ������Ͳ�ͬ
				mg.isTypeChange=true;//�������͸ı�Ϊ��			
			mg.column=(int)(1080/pixel);//���������������µ�����������
			mg.row=(int)((mg.column*10)/18);//��Ϊ��ʼ��Ϊ18��10�У����������µ���������������Ҳ����18��10
			System.out.printf("row=%d\n", mg.row);
			mg.isPixelChange=false;//��ʼ��Ϊ����δ�ı�
			if(pixel!=mg.pixel)//���ظı�
			{
				mg.setPixel(pixel);
				mg.isPixelChange=true;//��������Ϊ�Ѹı�
				mg.finish=true;//���ý������˳����չ�(ģʽ3)
				mg.find=true;//�����ҵ����˳�Ѱ��(ģʽ2��
				mg.find1=true;//�����ҵ����˳�Ѱ�ң�ģʽ1)
			}
			mg.setPause(true);//������Ϸ̽����ͣ
			mg.str="���ո�ʼ";//������Ϸ��ͣ����ʾ���ո�ʼ
			if(type==3)//�����չ�ģʽ
			{
				mg.model="ģʽ�������չ���ʾ��";
			}
			if(type==2)
			{
				mg.model="ģʽ������̽���չ���";
				if(mg.finish&&(!mg.isPixelChange)&&mg.isTypeChange)//����չ��չ������2�������1������Ĵ�����ɣ���1������Ϊ���ظı䣬Ϊ���˳�����ģʽ����ѭ������Ϊ������˳�ѭ��
				{//��������δ�䣬��finish=true����Ĵ�������չ�,����������͸ı��ˣ���������ģʽת����������ͼ���䣬�����ص�ԭ�㣬����·��
					mg.createBufferImage();//��Ϊ��3��ģʽ�µ���·�ߣ�Ϊ������·�ߣ������չ�������Ϣ(mg[][])���´�������ͼ
					mg.pullDownWall();//��ǽ
					mg.drawStartAndEnd();//����ʼ��ͽ�����ͼ
					mg.find1=false;//��ֹ�ٴ������ز�������»ص�ģʽ1��ʱ�����ؽ��չ�
				}			
			}
			if(type==1)
			{
				mg.model="ģʽ���˹������չ���";	
				if(mg.finish&&(!mg.isPixelChange)&&mg.isTypeChange)//����չ��չ������2�������1������Ĵ�����ɣ���1������Ϊ���ظı䣬Ϊ���˳�����ģʽ(��3��ģʽ)����ѭ������Ϊ������˳�ѭ��
				{//��������δ�䣬��finish=true����Ĵ�������չ�,����������͸ı��ˣ���������ģʽת����������ͼ���䣬�����ص�ԭ�㣬����·��
					mg.createBufferImage();//��Ϊ��3��ģʽ�µ���·�ߣ�Ϊ������·�ߣ������չ�������Ϣ(mg[][])���´�������ͼ
					mg.pullDownWall();//��ǽ
					mg.drawStartAndEnd();//����ʼ��ͽ�����ͼ
					mg.find=false;//��ֹ�ٴ������ز�������»ص�ģʽ2��ʱ�����ؽ��չ�					
				}	
			}
		    mg.setType(type);//��������  ��Ҫ����setPixel���棬��Ϊmaze���и��߳�1ֱѭ���������������ͺ��������أ��������ͺ���������ѭ����������Ӧ���ͣ�����ʱ���ػ�û���ü��ı䣬�������ش��������ʾͻ���������±곬���쳣
		    mg.setSecond(second);//����ʱ�估����
			mg.isStart=true;//������Ϸ��ʼ��������ں��棬������mg.find,find1,finish=trueǰ����ô��run������⵽��ЩΪtrue���ֻὫisStart���ó�false������Ҫ��������Щ״̬֮������isStart			
			setVisible(false);//ȷ����Ի�����ʧ
		}
		if(e.getSource()==but2)//�˳�
		{
			setVisible(false);//ת���Ի�����ʧ
		}
	}
}
class Box//ջ�࣬���������ջԪ��
{
	int i;//�ڼ���
	int j;//�ڼ���
	int next;//����һ������ķ���
	int count;//�߹��ķ�����Ŀ
	int canGo;//��ͨ�ķ�����Ŀ
	int visit[]=new int[4];//�ĸ�������ʱ�־��0��ʾ�÷���δ�߹���1��ʾ�߹�
}
class room//������
{
	int property;//�������ԣ�1��ʾ����������ʵ�ĵģ�����ͨ������ǽ��0��ʾ���ĵķ��䣬����ķ����ͨ����ǽ��ͨ
	//-1��ʾ�÷����Ѳ�ǽ��ͨ���߹�������·��2��ʾ�����Ѳ�ǽ��ͨ������·
    int left;//������ǽ���Ƿ��ͨ��0��ʾδ��ͨ��1��ʾ��ͨ��ͨ��
	int right;//������ǽ���Ƿ��ͨ��0��ʾδ��ͨ��1��ʾ��ͨ��ͨ��
	int up;//����ǰǽ���Ƿ��ͨ��0��ʾδ��ͨ��1��ʾ��ͨ��ͨ��
	int down;//�����ǽ���Ƿ��ͨ��0��ʾδ��ͨ��1��ʾ��ͨ��ͨ��
	//propertyΪ1�ķ�����ʵ�ĵģ����ܽ��д�ͨ������Χǽ�������ڶ�ʵ�ķ���Χ�ɵ�
}
class migong  extends JPanel implements Runnable 
{
	int second=1000;//�ƶ����ٶȣ����ٺ�����һ�Σ���ʼ��Ϊ1��
	int pixel=60;//�������أ���ʼ��Ϊ60����
	int i,j;//��һ�����������
	int k;
	int row=10;//����,��ʼ��Ϊ10��18��
	int column=18;//����
	int count;//���ڼ��߹��Ĳ���
	int entryX,entryY;//��ڵĺᡢ������
	int exitX,exitY;//���ڵĺᡢ������
	int currentX,currentY;//��ǰ����ĺᡢ������
    int frontNext;//��ջǰ�ķ���ͨ����һ������ķ���
    int type=2;//ģʽ��1Ϊ��ʱ�������˹�̽����2Ϊ����̽����3Ϊ�������չ�
	int top=-1;//ջ��
	int pathTop;//����·���������߹�����·��ջ��
	int top1=-1;//ջ��
	int direct;//��ǰ�ķ�������һ����ķ����־
	int entrydirect;//��ڷ����־
	int flag=-1;//�˹�̽���ƶ��ķ���,��ʼ��δ���κη����ƶ�
	String str="���ո�ʼ";//��ʼ����ͣ��ʾ�ַ���
	String time="ʱ��:";//ʱ��
	String model="ģʽ�������չ���ʾ��";//ģʽ
	String cost;//��ʱ
	String step="����";//����
	stopwatch watch;//�����
	Thread t1;//�߳�������ʱ
	room mg[][];//��������
    Box stack[];//ջ
	Box path[];//���ڱ��������߹���·����һ������ķ��򣬰�����·
	Box temp;//��ǰ�ķ���
	Box multiDirect[];//����������ķ���
	BufferedImage image1=new BufferedImage(1300,730,BufferedImage.TYPE_3BYTE_BGR); //3�Ż���ͼ���ֱ�������ģʽ�µ�ͼ��image1���˹�̽������ͼ
	BufferedImage image2=new BufferedImage(1300,730,BufferedImage.TYPE_3BYTE_BGR); //����̽��·������ͼ
	BufferedImage image3=new BufferedImage(1300,730,BufferedImage.TYPE_3BYTE_BGR); //�����չ�����ͼ
   	BufferedImage imageStart=null;//��ʼ��ͼƬ
	BufferedImage imageEnd=null;//������ͼƬ
	Graphics2D g1[]=new Graphics2D[3];//3�Ż���ͼ����һ֧��
	Color mazeBackGroundColor=Color.black;//�չ�������ɫ����ʼ��Ϊ��ɫ
	Color wallColor=Color.green;//�չ�ǽ����ɫ����ʼ��Ϊ��ɫ
	Color fontColor=Color.black;//�չ�������ɫ����ʼ��Ϊ��ɫ
	Color fontBackGroundColor=Color.white;//�չ����屳����ɫ����ʼ��Ϊ��ɫ
	Color routeColor=Color.red;//�չ�·����ɫ����ʼ��Ϊ��ɫ
	Color startColor=Color.white;//�չ���ʼ����ɫ����ʼ��Ϊ��ɫ����������С��60����²���Ч
	Color endColor=Color.red;//�չ���������ɫ����ʼ��Ϊ��ɫ����������С��60����²���Ч
	/*
    BufferedImage�̳�Image,��ͼ�����ݻ�����,��������ͼ��ͼ�Ρ�ͼƬ�ȼӵ����
	����������һ�����ͼ���ٰ�������ͼ�ӵ������JFrame��JPanel...��
	BufferedImage.TYPE_3BYTE_BGR
	��ʾһ������ 8 λ RGB ��ɫ������ͼ�񣬶�Ӧ�� Windows ����
	BGR ��ɫģ�ͣ������� 3 �ֽڴ洢�� Blue��Green �� Red ������ɫ��
	*/
	Thread t=new Thread(this);//���߳�˯��ʱ����ˢ�»���
	URL url=getClass().getResource("jump.wav");//getClss()
	//����һ�����������ʱ��,��Class����࣬
	//getResource��mgpath�����Ĺ������������ڲ��ҷ��ش��и������Ƶ���Դurl����·��
	AudioClip audio;	//AudioClip �����ڲ�����Ƶ�ӿڣ�����new����,Applet����һ���������ԴӸ��� URL ����ȡ��Ƶ�����ӿڵ�ʵ����
    boolean isStart=true;//�Ƿ�ʼ�ƶ�����ʼΪ��ʼ
	boolean isPause=true;//����ͣ
	boolean isBegin;//�Ƿ��ʱ��ʼ
	boolean isPixelChange;//�����Ƿ�ı�
	boolean isTypeChange;//�����Ƿ�ı�
	boolean isColorChange;//��ɫ�Ƿ�ı�
	boolean finish;//�չ�������ɷ�
    boolean find;//�����Ƿ��ҵ��յ���
    boolean find1;//����Ƿ��ҵ��յ���
    boolean isPull=false;//��3��ģʽ���Ƿ�ɲ�,��ʼ��Ϊ���ɲ�
	boolean isPush=true;//��ǰ�����Ƿ��ջ����һ������
	boolean isPictureExit=true;//ͼƬ�Ƿ��ȡ�ɹ�,��ʼ��Ϊ��ȡ�ɹ�
	boolean isRepaint3=false;//����ˢ�»�����ػ����ǵ�3��ģʽ��Ϊtrue����1��2��ģʽ��ʱ���̵߳���repaint��Ϊfalse
	Date now =new Date();//ʱ��
	public void run()
	{
		while(true)
		{
			switch(type)
			{
				case 3:
					if(isStart)
				    {
	            		 isBegin=false;//��ʼ��δ��ʱ�����¿ո��ż�ʱ
						 str="���ո�ʼ";			 
					     createMazePath();//�������չ�����ʾ��������
					     if(finish)
						 {
						     setStart(false);//���Ҫ��	
							 setPause(true);
						 }
					}
				break;
				case 2:
				{
					if(isStart)
					{
            		    isBegin=false;
            		   	str="���ո�ʼ"; 
						if(find||(!finish))//�ҵ��յ���ӵ�3��ģʽ���2��ģʽת������չ�δ�������
						{
							find=false;//���յ��Ҫ������һ�֣�������Ϊ��ֵû�ҵ�
							finish=true;//���ý������˳����չ�(ģʽ3)
							quickCreateMazePath();//���¿��ٴ���1�����չ�
						    repaint();//ˢ��һ����ʾ�´������չ�
						}
					    computerFindPath(entryX,entryY,exitX,exitY);//����Ѱ���չ�·��
						if(find)//�ҵ��յ��
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
            		    isBegin=false;//���յ��Ҫ������һ�֣�������Ϊ��ֵû�ҵ�
						str="���ո�ʼ";		                
            		    watch=new stopwatch(this);//��������Ϊʲô�����⣬����1��2ģʽ���ں����ڣ�������Ϊģʽ3ÿ�ƶ�1�ε���һ�κ�������1��2ģʽֻ����1��
						if(find1||(!finish))//�ҵ��յ���ӵ�3��ģʽ���1��ģʽת������չ�δ�������
						{
							find1=false;//���յ��Ҫ������һ�֣�������Ϊ��ֵû�ҵ�
							finish=true;//���ý������˳����չ�(ģʽ3)
							quickCreateMazePath();//���¿��ٴ���1�����չ�
							repaint();
						}
					    currentX=entryX;//��ǰ����Ϊ��ʼ��Ϊ��ʼ������
						currentY=entryY;
						count=0;
                        while(!find1&&type==1)//û�ҵ�����������1��һֱѭ��������ͣ�����ﲻ������run()��ѭ��
					    {
					    }
						if(find1)//�ҵ��յ��
						{
							setStart(false);
							setPause(true);
						}
				}
			}
		}
	}
	public void setStart(boolean isStart)//����״̬
	{
		this.isStart=isStart;
	}
	public void setPause(boolean isPause)//������ͣ��ʼ
	{
		this.isPause=isPause;
	}
	public void setSecond(int second)//�����ٶ�
	{
		this.second=second;
	}
	public void setPixel(int pixel)//��������
	{
		this.pixel=pixel;
	}
	public void setType(int type)//����ģʽ
	{
		this.type=type;
	}
	public migong()
	{
		try
		{ //ImageIO�����һЩ�������� ImageReader �� ImageWriter �Լ�ִ�м򵥱���ͽ���ľ�̬��ݷ���
		  //ImageIO��û�й��췽����û��new�������ɸ���һ��������������ĳ��ͼ�񣬲�����ͼ���ࣨʵ������
		  imageStart=ImageIO.read(getClass().getResource("ad.jpg"));
		  imageEnd=ImageIO.read(getClass().getResource("final.jpg"));		
		  audio=Applet.newAudioClip(url);//AudioClip �����ڲ�����Ƶ�ӿڣ�����new����,Applet����һ���������ԴӸ��� URL ����ȡ��Ƶ�����ӿڵ�ʵ����
		}
		catch (Exception e)
		{
            if(imageStart==null||imageEnd==null)//ֻҪ��һ��ͼƬ��ȡ���ɹ�
            	isPictureExit=false;//����ͼƬ��ȡ���ɹ�
		}
		quickCreateMazePath();//��������չ�
		t.start();
	}
	public void initMaze()//ÿһ�ν����չ�ǰҪ���г�ʼ��
	{
		mg=new room[row+2][column+2];//�����鱣��ÿ����ĵ�ַ
		stack=new Box[row*column];
		path=new Box[row*column];
		multiDirect=new Box[row*column];
		for(i=0;i<row*column;i++)
		{
			stack[i]=new Box();//ʵ����ջ��ÿ��Ԫ��
			multiDirect[i]=new Box();//ʵ����ջ��ÿ��Ԫ��
			multiDirect[i].count=stack[i].count=0;//�߹��ķ�����Ŀ��ʼ��Ϊ0
			multiDirect[i].next=stack[i].next=-1;//����һ������ķ����ʼ��Ϊ�ޣ���-1��ʾ
			stack[i].canGo=0;//��ʼ����ͨ�ķ�����Ϊ0
			for(j=0;j<4;j++)
				stack[i].visit[j]=0;//�ĸ������ʼ��Ϊδ�߹�
			path[i]=new Box();////ʵ����ջ��ÿ��Ԫ��
			path[i].next=-1;//����һ������ķ����ʼ��Ϊ��
		}
		for(i=0;i<row+2;i++)
		{
			for(j=0;j<column+2;j++)
			{
				mg[i][j]=new room();//ʵ����ÿ������
				if(i==0||i==row+1||j==0||j==column+1)//��ʼ���ɷ�����ɵ�����Χǽ
					mg[i][j].property=1;
				else
					mg[i][j].property=0;//��ʼ���ķ���
				mg[i][j].up=0;//��ʼ��ǰǽδ��ͨ
				mg[i][j].down=0;//��ʼ����ǽδ��ͨ
				mg[i][j].left=0;//��ʼ����ǽδ��ͨ
				mg[i][j].right=0;//��ʼ����ǽδ��ͨ
			}
		}
		count=0;//������ʼ��Ϊ0
		top=-1;//ջ����ʼ��Ϊ-1
		cost="��ʱ:0��";
		//��������ʼ��
		find=false;//��ʼ��Ϊδ�ҵ�
		find1=false;//��ʼ��Ϊδ�ҵ�
		finish=false;//��ʼ��Ϊ���
		entrydirect=(int)(4*Math.random());//������ڿ���Χǽ����
		switch(entrydirect)
		{
			case 0://����Χǽ����
				entryX=row;
			    entryY=(int)(column*Math.random())+1;//��ں�����Ϊrow��������Ϊ1-column�������
			break;
			case 1://����Χǽ����
				entryX=(int)(row*Math.random())+1;//���������column��������ΪΪ1-row�������
			    entryY=column;
			break;
			case 2://����Χǽ����
				entryX=1;
			    entryY=(int)(column*Math.random())+1;//��ں�����Ϊ1��������Ϊ1-column�������
			break;
			case 3://����Χǽ����
				entryX=(int)(row*Math.random())+1;//��ں�����Ϊ1-row���������������Ϊ1
			    entryY=1;
             break;
		}
		//���������ʼ��
		exitX=row-entryX+1;//�������������Χǽ����һ�棬���������Χǽ�����������Χǽ
		exitY=column-entryY+1;
		finish=false;//��ʼ��Ϊδ�������
	    //����ʱΪֹ�Ƕ��չ�����ĳ�ʼ��
		//�Ի���ͼ���г�ʼ��
		createBufferImage();//�����չ������Ż���ͼ
	}
	public void createMazePath()//��ʾ�����չ���x,y���չ��ĳ����㣬xi,ȴ���չ��Ľ�������
	{//ע��x,yΪ��ά����ĺ����ꡢ�����꣬��xȴ�ǵ�x��y�ǵ�y��
	    initMaze();//��ʼ���չ�����
		watch=new stopwatch(this);//����1�����
		t1=new Thread(watch);
		top=-1;//ջ��
		top1=-1;
		pathTop=-1;//ջ��
		mg[entryX][entryY].property=2;//��ʾ���߹�
		top++;//����ʼ�����ջ
		pathTop++;//����ʼ�����ջ
		stack[top].i=entryX;
		stack[top].j=entryY;
		path[pathTop].i=entryX;
		path[pathTop].j=entryY;
        count++;
		t1.start();//ʱ���߶�������δ��ʱ
		while(top>-1&&type==3&&!finish)//����·û���꣬һֱ���չ����еĸ�����
		{
			if(!isPause)
			{
				if(count==1)
				{
					isBegin=true;//����ʼ�󣬼�ʱ
				}
				temp=stack[top];//��ǰ����Ϊջ����ŵķ��䣬�������
				if(temp.count<4)//�ĸ�����û����
				{
					direct=(int)(4*Math.random());//�������0-3
					while(temp.visit[direct]==1)//�÷������߹�����������û�߹��ķ���
					direct=(int)(4*Math.random());//�������0-3
					temp.count++;//�߹��ķ��������1�������߲�ͨ��
					temp.visit[direct]=1;//�÷���û�߹����������ˣ���Ϊ�ѳ���
					switch(direct)
					{
						case 0://�涨���ҷ���
							i=temp.i;
							j=temp.j+1;//������1
							break;
						case 1://�涨���·���
							i=temp.i+1;
							j=temp.j;//������1
							break;
						case 2://�涨������
							i=temp.i;
							j=temp.j-1;//������1
							break;
						case 3://�涨���Ϸ���
							i=temp.i-1;
							j=temp.j;//������1
							break;
					}
					if(mg[i][j].property==0)//�÷���ɽ�
					{
						switch(direct)
						{
						case 0:
							mg[temp.i][temp.j].right=1;//���ҿ��ߣ���ͨ��ǽ
						    mg[i][j].left=1;//����ǰ�������ǽ��ͨ
						break;
						case 1:
							mg[temp.i][temp.j].down=1;//���¿��ߣ���ͨ��ǽ
						    mg[i][j].up=1;//����ǰ�����ǰǽ��ͨ						
						break;
						case 2:
							mg[temp.i][temp.j].left=1;//������ߣ���ͨ��ǽ
						    mg[i][j].right=1;//����ǰ�������ǽ��ͨ										
						break;
						case 3:
							mg[temp.i][temp.j].up=1;//���Ͽ��ߣ���ͨǰǽ
						    mg[i][j].down=1;//����ǰ����ĺ�ǽ��ͨ						
						break;
						}
						mg[i][j].property=2;//��ʾ���߹�
						count++;//�ߵĲ�����1
						isPush=true;//�ٽ�ջ
						step="����:"+count;
					    path[pathTop].next=direct;//�����������ͨ����һ������ķ���
						pathTop++;//�߹��ķ�������1						
						if(stack[top].canGo>0)//����ͨ��1��
						{
							top1++;//��ջ��������������1����ͨ����Ϣ
							multiDirect[top1].next=temp.next;
							multiDirect[top1].i=temp.i;
							multiDirect[top1].j=temp.j;
						}
						stack[top].next=direct;//�����������ͨ����һ������ķ���
	                    stack[top].canGo++;//��ͨ�ķ����1	        
						top++;//��ͨǽ�󣬽�ջ������·��
						stack[top].i=i;
						stack[top].j=j;
						path[pathTop].i=i;
						path[pathTop].j=j;
						try
						{
						 repaint();	//�ػ�ͼ�񣬼��Ը��ĺ��ͼ��ˢ��		
						 if(audio!=null)//�����ȡ������Ƶ�ļ�
						 audio.play();//���Ų�ǽ������
						 Thread.sleep(second);
						}
						catch (Exception e)
						{
						}											
					}
				}
				else//�ĸ����򶼲�����
				{
					mg[temp.i][temp.j].property=-1;//�÷���Ϊ���߹�������ͨ
					top--;//�÷��򲻿�����ջ��������һ������	
					//���˺�ѵ�ǰ������������path·��
					isPush=false;//����ջ				
					frontNext=temp.next;//������ջǰ�ķ���
					temp.count=0;//��һ���仹������ԭ�ȵ����ݣ�Ҫ���г�ʼ�������Է��������Ϊ0
					for(i=0;i<4;i++)
					temp.visit[i]=0;//�ĸ�������Ϊδ�߹�
					temp.next=-1;
					count++;
					step="����:"+count;
					if(top==0)//��ջ��������ʱ������·������
					{
					  finish=true;
					}
					else//�˻ص��յ�ǰ
					{//���˺�ѵ�ǰ������������path·��
						path[pathTop].i=stack[top].i;
						path[pathTop].j=stack[top].j;
					}
					try
					{
					 isRepaint3=true;
					 repaint();	//��ջ�������ػ�ͼ�񣬼��Ը��ĺ��ͼ��ˢ��,���˯�ߺ���ˢ�£���ʱ˯�ߺ��ѽ�������һ��̽���������ľͲ���ԭ���ǴΣ�ԭ���Ǵλ�δ����	
					 if(audio!=null)//�����ȡ������Ƶ�ļ�
					 audio.play();//���ż��˵�����
					 Thread.sleep(second);
					}
					catch (Exception e)
					{
					}					
				}
			}
		}
	}
	public void quickCreateMazePath()//���ٴ����չ���x,y���չ��ĳ����㣬xi,ȴ���չ��Ľ�������
	{//ע��x,yΪ��ά����ĺ����ꡢ�����꣬��xȴ�ǵ�x��y�ǵ�y��
	    initMaze();//��ʼ���չ�����
		top=-1;//ջ��
		mg[entryX][entryY].property=2;//��ʾ���߹�
		top++;//����ʼ�����ջ
		stack[top].i=entryX;
		stack[top].j=entryY;
		while(top>-1)//����·û���꣬һֱ���չ����еĸ�����
		{
			temp=stack[top];//��ǰ����Ϊջ����ŵķ��䣬�������
			if(temp.count<4)//�ĸ�����û����
			{
				direct=(int)(4*Math.random());//�������0-3
				while(temp.visit[direct]==1)//�÷������߹�����������û�߹��ķ���
				direct=(int)(4*Math.random());//�������0-3
                temp.count++;//�߹��ķ��������1
				temp.visit[direct]=1;//�÷���û�߹����������ˣ���Ϊ�ѳ���
				switch(direct)
				{
					case 0://�涨���ҷ���
						i=temp.i;
						j=temp.j+1;//������1
						break;
					case 1://�涨���·���
						i=temp.i+1;
						j=temp.j;//������1
						break;
					case 2://�涨������
						i=temp.i;
						j=temp.j-1;//������1
						break;
					case 3://�涨���Ϸ���
						i=temp.i-1;
						j=temp.j;//������1
						break;
				}
				if(mg[i][j].property==0)//�÷���ɽ�
				{
					switch(direct)
					{
						case 0:
							mg[temp.i][temp.j].right=1;//���ҿ��ߣ���ͨ��ǽ
						    mg[i][j].left=1;//����ǰ�������ǽ��ͨ
						break;
						case 1:
							mg[temp.i][temp.j].down=1;//���¿��ߣ���ͨ��ǽ
						    mg[i][j].up=1;//����ǰ�����ǰǽ��ͨ						
						break;
						case 2:
							mg[temp.i][temp.j].left=1;//������ߣ���ͨ��ǽ
						    mg[i][j].right=1;//����ǰ�������ǽ��ͨ										
						break;
						case 3:
							mg[temp.i][temp.j].up=1;//���Ͽ��ߣ���ͨǰǽ
						    mg[i][j].down=1;//����ǰ����ĺ�ǽ��ͨ						
						break;
					}
					mg[i][j].property=2;//��ʾ���߹�
					top++;//��ͨǽ�󣬽�ջ������·��
					stack[top].i=i;
					stack[top].j=j;
				}
			}
			else//�ĸ����򶼲�����
			{
				mg[temp.i][temp.j].property=-1;//�÷���Ϊ���߹�������ͨ
				top--;//�÷��򲻿�����ջ��������һ������				
				temp.count=0;//��һ���仹������ԭ�ȵ����ݣ�Ҫ���г�ʼ�������Է��������Ϊ0
				for(i=0;i<4;i++)
					temp.visit[i]=0;//�ĸ�������Ϊδ�߹�
			}
		}
		finish=true;
		pullDownWall();//������ɺ��ǽ
		drawStartAndEnd();//����ʼ��ͽ�����ͼ
	}
	public void computerFindPath(int x,int y,int xi,int yi)//�����Զ�Ѱ��·����x,y���չ��ĳ����㣬xi,ȴ���չ��Ľ�������
	{//ע��x,yΪ��ά����ĺ����ꡢ�����꣬��xȴ�ǵ�x��y�ǵ�y��
		 int next;//��һ������
		 count=0;//������ʼ��
		 boolean canIn;//�ɲ����Խ��뷿��ı��
		 cost="��ʱ:0��";
		 top=-1;//ջ����ʼ��
		 isBegin=false;
		 watch=new stopwatch(this);
		 t1=new Thread(watch);
		for(i=0;i<row*column;i++)//���������չ�ʱջ�����ݣ����г�ʼ�����ٴ����µĿ�ջ
		{
			stack[i].count=0;//�߹��ķ�����Ŀ��ʼ��Ϊ0
			stack[i].next=-1;//����һ������ķ����ʼ��Ϊ�ޣ���-1��ʾ
			for(j=0;j<4;j++)
				stack[i].visit[j]=0;//�ĸ������ʼ��Ϊδ�߹�
		}
		for(i=1;i<=row;i++)//���������չ�ʱ��·��
		{//����ÿ����������ǽ�Ƿ񱻲����Ϣ
			for(j=1;j<=column;j++)
			{
				mg[i][j].property=0;//��ʼ���ķ���
			}
		}
		mg[x][y].property=2;//��ʾ���߹�
		top++;//����ʼ�����ջ
		count++;
		stack[top].i=x;
		stack[top].j=y;
		t1.start();
		while(top>-1&&!find&&type==2)//����·û���꣬һֱ���չ����еĸ�������ߵ��յ�
		{
			if(!isPause)//��ʼ̽��
			{
					if(count==1)
					{
						isBegin=true;
					}
					canIn=false;//��ʼ��Ϊ���ɽ�
					temp=stack[top];//��ǰ����Ϊջ����ŵķ��䣬�������
					if(temp.i==xi&&temp.j==yi)//�������յ�λ��,����չ�
					{
						find=true;
						repaint();//ˢ��һ����ʾ������ʼ��������Ϸ��
						break;//�ҵ��յ㣬�˳�ѭ��
					}
					if(temp.count<4)//�ĸ�����û����
					{
						next=temp.next;//ȡ���÷������߹��ķ���
						next++;//����һ������ķ�����
						temp.next=next;//���浱ǰ����ո��ߵ���һ������
						temp.count++;//�߹��ķ��������1
						switch(next)
						{

							case 0://�涨���ҷ���
								i=temp.i;
								j=temp.j+1;//������1
								if(mg[temp.i][temp.j].right==1&&mg[i][j].property==0)//��ǽ�ڱ����ˣ�����һ���������
								canIn=true;
								break;
							case 1://�涨���·���
								i=temp.i+1;
								j=temp.j;//������1
								if(mg[temp.i][temp.j].down==1&&mg[i][j].property==0)//��ǽ�ڱ����ˣ�����һ���������
								canIn=true;
								break;
							case 2://�涨������
								i=temp.i;
								j=temp.j-1;//������1
								if(mg[temp.i][temp.j].left==1&&mg[i][j].property==0)//��ǽ�ڱ����ˣ�����һ���������
								canIn=true;
								break;
							case 3://�涨���Ϸ���
								i=temp.i-1;
								j=temp.j;//������1
								if(mg[temp.i][temp.j].up==1&&mg[i][j].property==0)//ǰǽ�ڱ����ˣ�����һ���������
								canIn=true;
								break;

						}
						if(canIn)//�÷���ɽ�
						{
							mg[i][j].property=2;//��һ������ͨ������ʾ���߹�
							top++;//��ͨǽ�󣬽�ջ������·��
							count++;
							isPush=true;//��ջ
						    step="����:"+count;
							stack[top].i=i;
							stack[top].j=j;
							try
							{
							 repaint();	//�ػ�ͼ�񣬼��Ը��ĺ��ͼ��ˢ��		
							 if(audio!=null)//�����ȡ������Ƶ�ļ�
							 audio.play();//�����ƶ�������
							 Thread.sleep(second);
							}
							catch (Exception e)
							{
							}					
						}
					}
					else//�ĸ����򶼲�����
					{
						mg[temp.i][temp.j].property=-1;//�÷���Ϊ���߹�������ͨ
						top--;//�÷��򲻿�����ջ��������һ������				
						temp.count=0;//��һ���仹������ԭ�ȵ����ݣ�Ҫ���г�ʼ�������Է��������Ϊ0
						temp.next=-1;
						isPush=false;//��ջ
						for(i=0;i<4;i++)
							temp.visit[i]=0;//�ĸ�������Ϊδ�߹�
						count++;
						step="����:"+count;
						try
						{
						 repaint();	//�ػ�ͼ�񣬼��Ը��ĺ��ͼ��ˢ��		
						 if(audio!=null)//�����ȡ������Ƶ�ļ�
						 audio.play();//�����ƶ�������
						 Thread.sleep(second);
						}
						catch (Exception e)
						{
						}					
					}
				}
			}
	}
    public void peopleFind()//�������ƶ��ķ���
	{
       if(!isPause)
		{
			if(count==0)//countΪ1ʱ
			{
				isBegin=true;//����ѿ�ʼ���ˣ�������ʾ�ߵĲ�����ʱ��
			    new Thread(watch).start();//��ʱ�߳̿���
			}
		   switch(flag)
			{
			   case 0:
				if(mg[currentX][currentY].right==1)//��ǽ����
				{
				   returnRoom(0,currentX,currentY);//����ǰ�������
				   currentY++;//������
				   count++;//������1
                   step="����:"+count;
				   repaint();//ˢ���ƶ�����
				   if(audio!=null)//�����ȡ������Ƶ�ļ�
				   audio.play();//�����ƶ�������
				}
				   break;
			   case 1:
				if(mg[currentX][currentY].down==1)//��ǽ����
				{
				   returnRoom(0,currentX,currentY);//����ǰ�������
				   currentX++;//������
				   count++;//������1
                   step="����:"+count;
				   repaint();//ˢ���ƶ�����
				   if(audio!=null)//�����ȡ������Ƶ�ļ�
				   audio.play();//�����ƶ�������
				}
				   break;
			   case 2:
				if(mg[currentX][currentY].left==1)//��ǽ����
				{
				   returnRoom(0,currentX,currentY);//����ǰ�������
				   currentY--;//������
				   count++;//������1
				   step="����:"+count;
				   repaint();//ˢ���ƶ�����
				   if(audio!=null)//�����ȡ������Ƶ�ļ�
				   audio.play();//�����ƶ�������
				}
				   break;
			   case 3:
				if(mg[currentX][currentY].up==1)//ǰǽ����
				{
				   returnRoom(0,currentX,currentY);//����ǰ�������
				   currentX--;//��ǰ��
				   count++;//������1
				   step="����:"+count;
				   repaint();//ˢ���ƶ�����
				   if(audio!=null)//�����ȡ������Ƶ�ļ�
				   audio.play();//�����ƶ�������
				}
				   break;
			}
			if(currentX==exitX&&currentY==exitY)
			{
				find1=true;
                JOptionPane.showMessageDialog(null,"��ϲ���㵽�����յ�","������",JOptionPane.OK_CANCEL_OPTION);
			}
		}
	}
    public void createBufferImage()//�����չ������Ż���ͼ
    {
    	//�Ի���ͼ���г�ʼ��
 	    g1[0]=image1.createGraphics();//���������ͼ�л��һ֧����
        g1[1]=image2.createGraphics();//���������ͼ�л��һ֧����
        g1[2]=image3.createGraphics();//���������ͼ�л��һ֧����
        for(k=0;k<3;k++)//��ʼ�����Ż���ͼ
 		{
         	g1[k].setColor(mazeBackGroundColor);//���û����չ�����ɫ
 			g1[k].fillRect(0,0,1300,730);//����������ͼ����Ϊ������ɫ
 			g1[k].setColor(wallColor);//���û���ǽ��ɫ�����ڻ�ǽ��
 			//���߿򣬼���ǽ���ɲ����϶���20��20�����¶���20��pixel*row+20�����϶���
 			//pixel*column+20��20�����¶���pixel*column+20��20+pixel*row
 			g1[k].drawLine(20,20,pixel*column+20,20);//�ϱ߿�
 			g1[k].drawLine(20,pixel*row+20,pixel*column+20,20+pixel*row);//�±߿�
 			g1[k].drawLine(20,20,20,pixel*row+20);//��߿�
 			g1[k].drawLine(pixel*column+20,20,pixel*column+20,pixel*row+20);//�ұ߿�
 			//��������,��ǽ���ɲ�
 			//������
 			for(i=1;i<row;i++)//������
 			{
 					g1[k].drawLine(20,i*pixel+20,pixel*column+20,i*pixel+20);
 			}
 			//������
 			for(i=1;i<column;i++)//������
 			{
 					g1[k].drawLine(i*pixel+20,20,i*pixel+20,pixel*row+20);
 			}
 		} 
    }
    public void setNewColorBufferImage()//����ɫ�ı����������ɫ�Ļ���ͼ���չ����ı�
    {
    	createBufferImage();//�����չ������Ż���ͼ
    	if(type==3)//����ǵ�3��ģʽ������Ϊ�ɲ�
    		isPull=true;
    	pullDownWall();//��ǽ
    	isPull=false;//�����Ϊ��һ�ֽ��д������г�ʼ��������Ϊ���ɲ�
    	if(type==1)//ģʽ1,��·��
    	{
			if(pixel>=60&&isPictureExit)//����������ز�С��60����ͼƬ�����ڣ���ͼƬ
			{
				g1[0].drawImage(imageStart,20+(int)((pixel-60)/2)+(currentY-1)*pixel,20+(int)((pixel-60)/2)+(currentX-1)*pixel,this);//������ͼ�꣬���ڵ�ǰ����
				//���յ�ͼƬ
				g1[0].drawImage(imageEnd,20+(int)((pixel-60)/2)+(exitY-1)*pixel,20+(int)((pixel-60)/2)+(exitX-1)*pixel,this);//������ͼ�꣬���ڽ�������
			}
			else//���򣬽����㻭��ʼ����ɫ�����δ���ǰ����λ�ã�����ɫ�����δ������λ��
			{
				g1[0].setColor(startColor);//���û���Ϊ��ʼ����ɫ
				g1[0].fillRect(20+(currentY-1)*pixel,20+(currentX-1)*pixel,pixel,pixel);
				g1[0].setColor(endColor);//���û���Ϊ��������ɫ
				//����������ɫ�����ο���Ϊ������
				g1[0].fillRect(20+(exitY-1)*pixel,20+(exitX-1)*pixel,pixel,pixel);						
			}
    	}
    	if(type==2)//ģʽ2,�Ȼ�·�ߣ��󻭵�ǰ��ͽ�����
    	{
    		g1[1].setColor(routeColor);//���û���·����ɫ
			for( k=0;k<top;k++)//�߹���·��
			{
				i=stack[k].i;//·����ȡ����ǰ�±��Ӧ���������
				j=stack[k].j;
				switch(stack[k].next)//����   ��20��20�����չ����Ͻǵ����꣬����ÿ���������20���������20��i-1,j-1,��ǰ��i-1����������j-1������Ŀ�ȣ��ټ�������������ĵĿ��(int)(pixel/2)
				{/*ע�⣺i��j�еķ���Ϊmg[j][i],��������Ϊi,������Ϊj
				��·�ߣ���·Ϊ�ӵ�ǰ��������ĵ���һ����������ģ���ǰ��������Ϊ((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);
				��߷������ĵ�����((j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20)
				�ұ߷������ĵ�����(j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20)
				ǰ�߷������ĵ�����((j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20)
				��߷������ĵ�����((j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20)
				*/						
					case 0:g1[1].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//��ǰ�������ҷ����ҷ���
					case 3:g1[1].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20);break;//��ǰ�������Ϸ���ǰ�淿��
					case 1:g1[1].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20);break;//��ǰ�������·�����淿��
					case 2:g1[1].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//��ǰ������������߷���
					default:break;
				}
			}
			if(pixel>=60&&isPictureExit)//����������ز�С��60����ͼƬ�����ڣ���ͼƬ�ڵ�ǰλ�û�����ͼ
			{
				g1[1].drawImage(imageStart,(stack[top].j-1)*pixel+20+(int)((pixel-60)/2),(stack[top].i-1)*pixel+20+(int)((pixel-60)/2),this);
				g1[1].drawImage(imageEnd,20+(int)((pixel-60)/2)+(exitY-1)*pixel,20+(int)((pixel-60)/2)+(exitX-1)*pixel,this);//������ͼ�꣬���ڽ�������			
			}
			else//���򣬽����㻭��ʼ����ɫ�����δ���ǰ����λ�ã�����ɫ�����δ������λ��
			{
				g1[1].setColor(startColor);
				g1[1].fillRect(20+(stack[top].j-1)*pixel,20+(stack[top].i-1)*pixel,pixel,pixel);
				g1[1].setColor(endColor);//���û���Ϊ��������ɫ
				//����������ɫ�����ο���Ϊ����a��
				g1[1].fillRect(20+(exitY-1)*pixel,20+(exitX-1)*pixel,pixel,pixel);						
			}	
    	}
       	if(type==3)//ģʽ3,�Ȼ�·�ߣ��󻭵�ǰ��ͽ�����
    	{
       		g1[2].setColor(routeColor);//���û���·����ɫ
       		System.out.printf("pathtop=%d\n",pathTop);
			for( k=0;k<pathTop;k++)//�߹���·��
			{
				i=path[k].i;//·����ȡ����ǰ�±��Ӧ���������
				j=path[k].j;
				System.out.printf("i=%d,j=%d,next=%d\n",i,j,path[k].next);
				switch(path[k].next)//����  
				{
					case 0:g1[2].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//��ǰ�������ҷ����ҷ���
					case 3:g1[2].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20);break;//��ǰ�������Ϸ���ǰ�淿��
					case 1:g1[2].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20);break;//��ǰ�������·�����淿��
					case 2:g1[2].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//��ǰ������������߷���
					default:break;
				}
			}  	
			if(pixel>=60&&isPictureExit)//����������ز�С��60����ͼƬ�����ڣ���ͼƬ�ڵ�ǰλ�û�����ͼ
			{
				g1[2].drawImage(imageStart,(stack[top].j-1)*pixel+20+(int)((pixel-60)/2),(stack[top].i-1)*pixel+20+(int)((pixel-60)/2),this);
			}
			else//���򣬽����㻭��ʼ����ɫ�����δ���ǰ����λ��
			{
				g1[2].setColor(Color.white);
				g1[2].fillRect(20+(stack[top].j-1)*pixel,20+(stack[top].i-1)*pixel,pixel,pixel);
			}	
    	}
    }
	public void pullDownWall()//��ǽ
	{
		for(int k=0;k<3;k++)
		{
			if(k<2||isPull)//�չ�������ɺ�ǰ2������ͼ���в�ǽ����3������ͼ�����ܲ���ΪҪ���˿�ʼ��Ž�����ʾ��Ĺ���,����ɫ�ı��ؽ�����ͼ��ʱ��Ų�
			{
				for(i=row;i>=1;i--)//Ϊ��Ҫ�����һ�еķ����������ӵ�1�в���������Ϊ
				{
				/*
				  �����ڷŴ��¹۲췢�֣��߶�Ҳ�п����Ǻܴ󣬼��軭һ��60�������κͻ�һ��60���س����߶Σ������߶εĳ���
		         �������εĳ��ȳ�������ĳ���ǡ�����߶ε�΢С��ȣ���Լ0.5���ң��������߶�ƴ��������(��ɫ)��ֱ�ӻ���������(��ɫ)Ҫ��1��
		         ������ɫ�����ν��и�����ɫ������ʱ������ȫ���ǣ���ʣ��2�����Ϊ0.5����Ϊ60Ϊ������δ���ǣ����ȸ��߶ο��һ��
		         ������ô˵���軭1����ɫ������60���أ���ͬ���ص��߶�ȴ��60.5���أ���Ϊ��С�겻�����ֻ���ڷŴ󾳣�΢��ķŴ��������
		         ������𣨲�Э����������ӵ�1�п�ʼ�ú�ɫ�����ν��и���ÿ�����䣬��ô���ڽ��и��Ƿ������һ�л����һ���Ѿ����õ�ǽ����60����ʵ����60.5����)
		         ���ǵ�0.5������2������������1��0.5���ص������Σ��γ�1��С����������µ����򲻻ᣬ��Ϊ��һ�������ǲ�����ǽ����ǽ�����Ӱ�첻����һ���Ѳ��õ�ǽ
		         �����ϵ��£����Ḳ�Ƕ�ǽ�ͱ�ǽ��˻����һ���Ѳ��õ�2����ǽ֮�串�ǵ�һ��0.5���������Σ������2��ǽ����ֱ����������Э
				*/			
					for(j=1;j<=column;j++)
					{
						returnRoom(k,i,j);//��ǽ�Ƚ������ñ���ɫ���ǣ���������ǽ�����ˣ��ٽ����ò��ǽ����ȥ
					}
				}			
			}
	
		}
	}
	public void drawStartAndEnd()//����ʼ��ͽ�����
	{		//����ʼ�ͽ���λ�ã������ڲ���ǽ�󣬷�����ʼ�ͽ�����ͼ�ᱻ����ɫ����
		for(k=0;k<2;k++)//��3��ģʽ���޽���ͼ
		{
			if(pixel>=60&&isPictureExit)//����������ز�С��60����ͼƬ������              
			{//��ͼƬ�ڵ�ǰλ�û�����ͼ
			  g1[k].drawImage(imageStart,20+(int)((pixel-60)/2)+(entryY-1)*pixel,20+(int)((pixel-60)/2)+(entryX-1)*pixel,this);//������ͼ�꣬���ڿ�ʼ����
				//���յ�ͼƬ
			  g1[k].drawImage(imageEnd,20+(int)((pixel-60)/2)+(exitY-1)*pixel,20+(int)((pixel-60)/2)+(exitX-1)*pixel,this);//������ͼ�꣬���ڽ�������
			}			 
			else//���������
			{
				//����ʼ����ɫ����������Ϊ���
				g1[k].setColor(startColor);
				g1[k].fillRect(20+(entryY-1)*pixel,20+(entryX-1)*pixel,pixel,pixel);			
				g1[k].setColor(endColor);//���û���Ϊ��������ɫ
				//����������ɫ�����ο���Ϊ������
				g1[k].fillRect(20+(exitY-1)*pixel,20+(exitX-1)*pixel,pixel,pixel);				
			}							
		}
	}
	public void returnRoom(int k,int i,int j)//��ĳ�����仹ԭ,k�ǻ���ͼ�±꣬i,j�Ƿ�����±�
	{
	    /*�����ڷŴ��¹۲췢�֣��߶�Ҳ�п����Ǻܴ󣬼��軭һ��60�������κͻ�һ��60���س����߶Σ������߶εĳ���
	     �������εĳ��ȳ�������ĳ���ǡ�����߶ε�΢С��ȣ���Լ0.5���ң��������߶�ƴ��������(��ɫ)��ֱ�ӻ���������(��ɫ)Ҫ��1��
	     ������ɫ�����ν��и�����ɫ������ʱ������ȫ���ǣ���ʣ��2�����Ϊ0.5����Ϊ60Ϊ������δ���ǣ����ȸ��߶ο��һ���������
	     2���߶�δ���ǣ��ֱ��Ǻ���ǽ������ǽδ���ǣ���������2��ǽû�𣬲����ٲ��ϣ�����2��ǽ������Ӧ�ò��ȴ�����ڣ�����ǽ��ÿ�����䶼����
	     �����з���Ҫ��ɫ�����������ʱ���������չ�ֻ��Χǽ�����������û��Χǽ�ǲ��ܲ�ģ�Ҫ����Ƿ����ǽ�������ǲ�������Χǽ�����з�������ǽ
	     ������ǽ�����ˣ��ڲ��ٽ������ʱ��������ˣ���ֻ�п���Χǽ�ķ�����ǽ����ǽû����2��ǽ��Χǽ��ɲ������Բ��ܲ�
	    */
		g1[k].setColor(mazeBackGroundColor);//���û���Ϊ�չ�����ɫ
		g1[k].fillRect(20+(j-1)*pixel,20+(i-1)*pixel,pixel,pixel);//��δ��ջǰ�ķ��仭�Ϻ�ɫ����
		g1[k].setColor(wallColor);//���û���Ϊǽ����ɫ
		if(mg[i][j].left==0)//�����ǽ
		g1[k].drawLine(20+(j-1)*pixel,20+(i-1)*pixel,20+(j-1)*pixel,20+i*pixel);
		//if(mg[i][j].right==0)//�ұ���ǽ    ע������Ϊ����2��ǽû�𣬲����ٲ���
		//g1[k].drawLine(20+j*pixel,20+(i-1)*pixel,20+j*pixel,20+i*pixel);
		if(mg[i][j].up==0)//ǰ����ǽ
		g1[k].drawLine(20+(j-1)*pixel,20+(i-1)*pixel,20+j*pixel,20+(i-1)*pixel);
		//if(mg[i][j].down==0)//�����ǽ    ע������Ϊ����2��ǽû�𣬲����ٲ���
		//g1[k].drawLine(20+(j-1)*pixel,20+i*pixel,20+j*pixel,20+i*pixel);
	}
	public void returnRoute(int k,int top)//���������ԭ�󣬻�ԭ��ԭ����·�ߣ�k�ǻ���ͼ�±�,top��ջ���±�
	{//ÿ��������2��·�ߣ�1������������ͨ���������·�ߣ���1�����������ͨ����һ������·��
		int m,n;//m�ǵ�ǰ�����±꣬n�ǵ�ǰ���䷽��
		//������ջ�����ߵķ�����Ϊ��ջ�����аѽ�ջǰ�ķ���������ˣ�������仹�������ϸ��������������һ����·�ߣ�Ҫ������					
		//��֮ÿ��������������·�ߵģ��ֱ���ǰһ���䵽�������·�߼������䵽��һ�������·�ߣ����Բ���ĳ������Ҫ�ػ���2��·��
		g1[k].setColor(routeColor);//���ñ�Ϊ�߼���ɫ���������߹���·��	    	
		if(isPush)
			m=top-2;//�ǽ�ջ�Ļ������Ͻ�ջǰ2�������·��
		else
			m=top;//����ջ�Ļ������ϵ�ǰ�������ջǰ�����·��
		for(int a=1;a<=2;m++,a++)//�ֱ���δ��ջǰ2�������·��
		{
			if(m>=0)//Ϊ�˷�ֹ�±�Ϊ-1����������2����topΪ1����ʱֻҪ��ǰһ������·�߾Ϳ��ԣ���Ϊû��ǰ2������
			{
				i=stack[m].i;//ȡ����ǰ�����±�
				j=stack[m].j;
				n=stack[m].next;
				if(m==top+1)//m=top+1����������ջ��Ҫ������ջǰ·�ߣ���ÿ��ջ1�ξͰѺۼ������stack[top+1]û����top+1���䷽��������frontNextר�ű�����ջǰ�ķ���
				   n=frontNext;
				switch(n)//����   ��20��20�����չ����Ͻǵ����꣬����ÿ���������20���������20��i-1,j-1,��ǰ��i-1����������j-1������Ŀ�ȣ��ټ�������������ĵĿ��(int)(pixel/2)
				{				
					case 0:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//��ǰ�������ҷ����ҷ���
					case 3:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20);break;//��ǰ�������Ϸ���ǰ�淿��
					case 1:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20);break;//��ǰ�������·�����淿��
					case 2:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//��ǰ������������߷���
					default:break;
				}
			}
		}
		if(k==2)//�ڵ�2 ��ģʽ�½��й���ջ����ջ����������߹�2������ǰ��ֻ��ԭ���ߵĵ�2�����򣬻�û��ԭ��1������
		{
			if((isPush==true&&stack[top-1].canGo>1)||(isPush==false&&stack[top+1].canGo>1))
			{//����ǽ�ջ����ջǰ�ķ����г���2�����������ջ����ջǰ�ķ����г���2������
				i=multiDirect[top1].i;
				j=multiDirect[top1].j;//ȡ������ջǰ������
	            //multiDirect[top1].next��������Ķ෽�򷿼�ķ���
				switch(multiDirect[top1].next)//����   ��20��20�����չ����Ͻǵ����꣬����ÿ���������20���������20��i-1,j-1,��ǰ��i-1����������j-1������Ŀ�ȣ��ټ�������������ĵĿ��(int)(pixel/2)
				{				
					case 0:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,j*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//��ǰ�������ҷ����ҷ���
					case 3:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,(i-2)*pixel+(int)(pixel/2)+20);break;//��ǰ�������Ϸ���ǰ�淿��
					case 1:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-1)*pixel+(int)(pixel/2)+20,i*pixel+(int)(pixel/2)+20);break;//��ǰ�������·�����淿��
					case 2:g1[k].drawLine((j-1)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20,(j-2)*pixel+(int)(pixel/2)+20,(i-1)*pixel+(int)(pixel/2)+20);break;//��ǰ������������߷���
					default:break;
				}
				if(isPush==false&&isRepaint3)//����ջ����²����ڵ�3��ģʽ��ˢ�£�����ʱ���̵߳�ˢ��
				{
				   top1--;//��ջʱ��������Ϊ��ջʱ�ٴξ����������Ҫ����ױ��续2������·�ߣ�����ջ�������������·�ߣ����Լ�1����������֮ǰtop1�ķ���
				}			
			}
		}
		if(!isPush&&isRepaint3)
		{
		  stack[top+1].canGo=0;//��ջǰ��ջ�����ߵĲ�����ʼ��Ϊ0����������ջǰ�������Ϣ��Ϊ���ڴ˴��������ڸ��˵�ʱ����������Ϊǰ�滹Ҫ�õ�canGo>1���
		  //�ж����������������޷�ʵ�ֽ�ĳ�������2�����򻭳�������2�����򻭳����������������
		}
	}
	public void drawMessage(int k)//�����չ�����Ϣ��kΪ����ģʽ
	{
		if(isBegin)
		{
			g1[k].setColor(fontBackGroundColor);//���û���Ϊ���屳��ɫ
			g1[k].fillRect(1130,80,120,20);
			g1[k].fillRect(1130,110,120,20);
			g1[k].setColor(fontColor);//���û���Ϊ������ɫ
			g1[k].drawString(cost,1133,92);//��ʱ����Ϣ
			g1[k].drawString(step,1133,122);//���ߵĲ���
		}
		g1[k].setColor(fontBackGroundColor);//���û���Ϊ���屳��ɫ
		g1[k].fillRect(550,640,120,20);
		g1[k].fillRect(1130,20,120,20);
		g1[k].fillRect(1130,50,120,20);
		g1[k].setColor(fontColor);//���û���Ϊ������ɫ
        g1[k].drawString(str,553,652);//����ʼ��ͣ��״̬��Ϣ
        g1[k].drawString(time,1133,32);//��ʱ�� ��Ϣ
        g1[k].drawString(model,1133,62);//��ģʽ������Ϣ
	}
	public void paint(Graphics g)
	{
		if(type==2)//�չ�����̽��ģʽ
		{//ͼƬ������Ϊ60����ͼƬ�ŷ����м䣬��(int)((pixel-60)/2)ΪͼƬ���뷿����ǽ����ǽ�ľ��룬����ͼƬ����Ϊ
			if(top>0)//�չ�̽���ѿ�ʼ,����·��
			{
				//�߹���·��
				if(isPush)//����ǽ�ջ
				{
					i=stack[top-1].i;//ȡ����ǰ�����ջǰ�±��Ӧ���������
					j=stack[top-1].j;
					returnRoom(1,i,j);//����ջǰ�±��Ӧ���仹ԭ
					returnRoute(1,top);//���������ԭ�󣬻�ԭ��ԭ����·��
				}
				else//��ջ�Ļ�Ҫ���߹��ĺۼ���������ԭԭ����ͼ��
				{
					for(k=top;k<=top+1;k++)//ȡ����ǰ����δ��ջǰ����ջ����±��Ӧ���������
					{
						i=stack[k].i;
						j=stack[k].j;
						returnRoom(1,i,j);//����2�����仹ԭ
					}
				}				
				if(pixel>=60&&isPictureExit)//����������ز�С��60����ͼƬ������
				{//��ͼƬ�ڵ�ǰλ�û�����ͼ
					g1[1].drawImage(imageStart,(stack[top].j-1)*pixel+20+(int)((pixel-60)/2),(stack[top].i-1)*pixel+20+(int)((pixel-60)/2),this);
			    }	
				else//���򣬽����㻭��ʼ����ɫ������
				{
					g1[1].setColor(startColor);
					g1[1].fillRect(20+(stack[top].j-1)*pixel,20+(stack[top].i-1)*pixel,pixel,pixel);
				}
			}
			if(find)
			{
			    str="���ո�ʼ����Ϸ";
			}
			drawMessage(1);//���չ���Ϣ
            g.drawImage(image2,0,0,this);//�������������ͼ����JFrame��
		}
		if(type==3)//�չ����Դ���ģʽ
		{
			if(isPush)//��ջ
			{
				if(top>0)//top>0��Ž��и���ͼ��<0ʱtop-1�����±�ᳬ��
				{
					i=stack[top-1].i;//ȡ����ǰ�����ջǰ�±��Ӧ���������
					j=stack[top-1].j;
					returnRoom(2,i,j);//����ջǰ�±��Ӧ���仹ԭ
				    returnRoute(2,top);//���������ԭ�󣬻�ԭ��ԭ����·��					
				}
			}
			else
			{
				if(count>1&&isRepaint3==true)//�չ�������ɺ�����һ������ջ��Ĭ��3ģʽ�¼�����������ʱcount=1,ֻ����
					//��Ϊ��ֻͣ����һ������ʱ���߳�ÿ1��ˢ��1�λ��棬��ʱ���top[1]��top[1]����ջʱ�����Ѿ����㣩�����ñ�������
				{//����Ҫ��count>1,����2��������չ���ʽ��ʼ����
					i=stack[top+1].i;//ȡ����ǰ������ջ�±��Ӧ���������
					j=stack[top+1].j;
					returnRoom(2,i,j);//����ջǰ�±��Ӧ���仹ԭ
					returnRoute(2,top);//���������ԭ�󣬻�ԭ��ԭ����·��					
				}
			}
			if(top>-1)//�չ�̽���ѿ�ʼ,����·��
			{
				if(pixel>=60&&isPictureExit)//����������ز�С��60����ͼƬ�����ڣ���ͼƬ�ڵ�ǰλ�û�����ͼ              
					g1[2].drawImage(imageStart,(stack[top].j-1)*pixel+20+(int)((pixel-60)/2),(stack[top].i-1)*pixel+20+(int)((pixel-60)/2),this);
				else//���򣬽����㻭��ʼ����ɫ��������
				{
					g1[2].setColor(startColor);
					g1[2].fillRect(20+(stack[top].j-1)*pixel,20+(stack[top].i-1)*pixel,pixel,pixel);
				}
			}		
			if(finish)
			{
				str="���ո�ʼ�������չ�";
			}
			drawMessage(2);//���չ���Ϣ
            g.drawImage(image3,0,0,this);//�������������ͼ����JFrame��
		}
		if(type==1)//�չ��˹�̽��ģʽ
		{
			if(pixel>=60&&isPictureExit)//����������ز�С��60����ͼƬ�����ڣ���ͼƬ
			{
				g1[0].drawImage(imageStart,20+(int)((pixel-60)/2)+(currentY-1)*pixel,20+(int)((pixel-60)/2)+(currentX-1)*pixel,this);//������ͼ�꣬���ڿ�ʼ����
			}
			else//���򣬽����㻭��ʼ����ɫ�����δ���ǰ����λ�ã���ɫ�����δ������λ��
			{
				g1[0].setColor(startColor);
				g1[0].fillRect(20+(currentY-1)*pixel,20+(currentX-1)*pixel,pixel,pixel);
			}
			if(find1)
			{
				str="���ո�ʼ����Ϸ";
			}
			drawMessage(0);//���չ���Ϣ
			g.drawImage(image1,0,0,this);//�������������ͼ����JFrame��
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