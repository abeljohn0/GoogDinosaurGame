import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import sun.audio.*;

public class DinoGame
{
	public static void main(String...args)
	{
		JFrame j = new JFrame();  //JFrame is the window; window is a depricated class
		MyPanelb m = new MyPanelb();
		j.setSize(m.getSize());
		j.add(m); //adds the panel to the frame so that the picture will be drawn
			      //use setContentPane() sometimes works better then just add b/c of greater efficiency.

		j.setVisible(true); //allows the frame to be shown.

		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes the dialog box exit when you click the "x" button.
	}

}

class MyPanelb extends JPanel implements ActionListener, KeyListener, MouseListener
{
	
	private Timer time;
	private int x,y;
	private int add;
	private CollisionDetection dino = new CollisionDetection();
	private boolean a = false;
	private double counter = 80;
	private int gameover = 0;
	private int animation = 2;
	private int dinored = 204;
	private int dinogreen = 121;
	private int dinoblue = 51;
	private int carred = 0;
	private int cargreen = 255;
	private int carblue = 0;
	private double sum = 0;
	private int carrandomizer = 0;
	private Graphics gr;
	private int carmover = 0;
	private int delta = 0;
	private int gradchangeX;
	private int cityscapemover;
	private Graphics2D g2d;
	
	private int [] bodyx = {2000,2001,2002,2002,2002,2004,2007,2011,2015,2017,2019,2023,2029,2034,2041,2046,2050,2054,2059,2065,2071,2076,2082,2086,2092,2097,2102,2107,2111,2116,2120,2125,2129,2134,2140,2144,2149,2154,2159,2165,2172,2179,2184,2191,2198,2210,2224,2238,2233,2229,2247,2243,2254,2262,2268,2274,2280,2217,2287,2293,2300,2309,2315,2322,2327,2332,2338,2334,2340,2340,2338,2335,2332,2329,2322,2316,2311,2307,2304,2301,2295,2289,2281,2272,2266,2259,2254,2250,2247,2244,2238,2233,2188,2129,2092,2089,2087,2082,2077,2084,2073,2068,2062,2057,2050,2046,2042,2037,2032,2029,2026,2023,2013,2005};
	private int [] bodyy = {841,835,829,823,818,812,809,806,802,799,794,790,787,785,784,781,780,780,779,777,775,775,775,774,774,773,772,769,765,760,757,754,751,747,745,742,741,739,739,739,739,736,736,737,738,739,741,742,742,740,746,742,748,751,754,757,760,739,764,768,772,776,782,787,792,797,804,802,810,817,824,830,837,840,842,842,842,845,852,858,862,865,866,866,866,863,858,854,847,842,842,842,843,842,842,845,850,857,861,853,862,863,864,864,864,864,861,857,853,847,843,843,844,844};	
	private int [] window1x = {2204,2202,2198,2195,2189,2175,2152,2126,2111,2117,2130,2124,2137,2145,2155,2165,2174,2183,2192,2198};
	private int [] window1y = {746,755,763,773,774,775,776,778,778,767,755,760,751,749,745,744,743,742,743,743};
	private int [] window2x = {2211,2232,2251,2269,2275,2253,2233,2210,2203,2205,2208};
	private int [] window2y = {745,751,758,767,772,774,776,776,776,762,752};
	private int [] frontlightx = {2026,2030,2037,2055,2046,2063,2062,2057,2050,2042,2035};
	private int [] frontlighty = {813,805,800,793,796,791,798,803,807,809,811};
	private int [] backlightx = {2340,2335,2330,2324,2317,2309,2305,2309,2318,2326,2333};
	private int [] backlighty = {808,802,796,792,789,789,793,800,805,809,810};
	private int [] grillex = {2000,1999,2000,2002,2006,2008,2008,2008,2009,2005};
	private int [] grilley = {841,834,828,825,826,830,833,840,836,844};
	private int [] handlex = {2175,2180,2186,2193,2191,2187,2181,2176,2174};
	private int [] handley = {793,793,793,794,797,797,798,797,795};
	private int wheel11x = 2030;
	private int wheel12x = 2042;
	private int wheel21x = 2247;
	private int wheel22x = 2258;
		
					

	MyPanelb()
	{
	//	Audio.music("DinoGameAudio.wav");
		time = new Timer(20, this); //sets delay to 20 millis and calls the actionPerformed of this class.
		setSize(1920, 1080);
		setVisible(true); //it's like calling the repaint method.
		time.start();
		add=1;
		x=y=600;
		addMouseListener(this);
		setFocusable(true);
		addKeyListener(this);
	
	}
	
	public void paintComponent(Graphics g)
	{
		if (!dino.collisioncheck()) cityscapemover+=2;
		gradchangeX+=2;
		g2d = (Graphics2D) g;
		drawgrad(g2d);
		drawBackground(g);
		if (!a && animation!=0 && (animation/2)%2==0) DinoFrame1(g);
		else if (!a && (animation/2)%2==1) DinoFrame2(g);
		gr = g;
		
		if(score(g) < 100)
		{
			g.setFont(new Font("Courier",Font.PLAIN,36));
			g.drawString("Press any key to jump and", 700, 340);
			g.drawString("click the mouse to change the dinosaur's color", 450, 440);
		}
		
		if(a) jumpDino(g);
		
		if(!dino.collisioncheck())
		animation+=6;
		
		drawCar(g);
		if (dino.collisioncheck() && gameover <= 1907)
		{	
			drawBackground(g);
			DinoFrame0(g, (int) sum);
			drawCar(g);
			g.setColor(new Color(255, 213, 33));
			g.fillOval(5, 5, gameover, gameover);
			gameover+=30;
		}

		if (gameover > 1907)
		{
			g.setColor(new Color (255, 213, 33));
			g.fillOval(5, 5, 1907, 1920);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Courier",Font.PLAIN,72));
			g.drawString("GAME OVER", 745, 540);
		}
		
		if (score(g)%100==2 && score(g)!=2)
		{
			Audio.music("milestone.wav");
		}
		
		if (bodyx[0]<=275 && bodyx[0]>=-65 && sum < 344 && !a) dino.collided();
		if (!dino.collisioncheck()){
		wheel11x-=25;
		wheel12x-=25;
		wheel21x-=25;
		wheel22x-=25;
		for (int index = 0; index<bodyx.length; index++)
		{	
			carrandomizer = (int) (Math.random()*2000);
			System.out.println(carrandomizer);
			bodyx[index]-=25;
		}
		
		for (int index = 0; index<window1x.length;index++)
		{
			window1x[index]-=25;
			
		}

		for (int index = 0; index<window2x.length;index++)
		{
			window2x[index]-=25;
			
		}

		for (int index = 0; index<frontlightx.length;index++)
		{
			frontlightx[index]-=25;
			
		}

		for (int index = 0; index<backlightx.length;index++)
		{
			backlightx[index]-=25;
			
		}

		for (int index = 0; index<grillex.length;index++)
		{
			grillex[index]-=25;
			
		}

		for (int index = 0; index<handlex.length;index++)
		{
			handlex[index]-=25;
			
		}
		}
		if (bodyx[0] <=-350){
			for (int index = 0; index < bodyx.length; index++) bodyx[index]+=2350+carrandomizer;
			for (int index = 0; index < window1x.length; index++) window1x[index]+=2350+carrandomizer;
			for (int index = 0; index < window2x.length; index++) window2x[index]+=2350+carrandomizer;
			for (int index = 0; index < frontlightx.length; index++) frontlightx[index]+=2350+carrandomizer;
			for (int index = 0; index < backlightx.length; index++) backlightx[index]+=2350+carrandomizer;
			for (int index = 0; index < grillex.length; index++) grillex[index]+=2350+carrandomizer;
			for (int index = 0; index < handlex.length; index++) handlex[index]+=2350+carrandomizer;
			wheel11x+=2350+carrandomizer;
			wheel12x+=2350+carrandomizer;
			wheel21x+=2350+carrandomizer;
			wheel22x+=2350+carrandomizer;

			carred = (int) (Math.random()*256);
			cargreen = (int) (Math.random()*256);
			carblue = (int) (Math.random()*256);
		}
		
		if (cityscapemover>3300) cityscapemover=-100;
	}

		public void drawgrad(Graphics g)
		{
			//orange is default color
			delta+=2;
			//800
			if(delta<=800)
			{
				gradchangeX+=8;
				GradientPaint gradient = new GradientPaint(0,-3000+gradchangeX, Color.black,0, 1080, Color.blue);
				g2d.setPaint(gradient);
				g2d.fillRect(0,0,1920,1080);

			}
			if(delta > 800&&delta<1600)
			{
				gradchangeX+=8;
				GradientPaint gradient = new GradientPaint(0,-3000+gradchangeX, new Color(253,224,128),0, 1080, Color.black);			
				g2d.setPaint(gradient);
				g2d.fillRect(0,0,1920,1080);
			}
			//insert first gradient
			//turn orange to yellow
			if(delta>=1600&&delta<2400)
			{
				gradchangeX+=8;
				GradientPaint gradient=new GradientPaint(0,-3000+gradchangeX,new Color(62,187,198) ,0, 1080,new Color(253,224,128) );			
				g2d.setPaint(gradient);
				g2d.fillRect(0,0,1920,1080);
			}
			//insert second gradient
			//turn yellow to blue
			if(delta>=2400&&delta<=3200)
			{
				gradchangeX+=8;
				GradientPaint gradient=new GradientPaint(0,-3000+gradchangeX,Color.blue,0, 1080,new Color(62,187,198));			
				g2d.setPaint(gradient);
				g2d.fillRect(0,0,1920,1080);
			}
			//insert third
			//turn blue to black
			if(delta/800==4)
			{
				delta=0;
			}
			if(delta%800==0)
			{
				gradchangeX=0;
			}
			//turn black to orange
			//set delta to
			g.fillRect(0,0,1920,1080);
	}	
	
	public void drawBackground(Graphics g)
	{
				//desert background
		g.setColor(new Color(255,255,153));
		g.fillRect(0,910,1920,170);
		g.fillRect(0,735,1920,15);
				//moon
		g.setColor(new Color(255, 213, 33));
		if (!dino.collisioncheck()) g.fillOval(5, 5, 100, 100);
		g.setColor(Color.BLACK);
		for(int x=0; x<20; x++)
		{
		g.fillOval((int)(Math.random()*1921)+0,(int)(Math.random()*50)+910,5,5);		
		}
		for(int x=0; x<15; x++)
		{
		g.fillOval((int)(Math.random()*1921)+0,(int)(Math.random()*15)+735,3,3);		
		}
				//road
		g.setColor(new Color(153,153,153));
		g.fillRect(0,750,1920,150);
		g.setColor(Color.YELLOW);
		for(int x=0; x< 1920;x+=30)
		{
		g.fillRect(0+x,800,20,10);
		}
		g.setColor(Color.WHITE);
		g.fillRect(0,750,1920,5);
		g.fillRect(0,900,1920,10);
			//cityscape
		int[] cx={3005-cityscapemover,3005-cityscapemover,3000-cityscapemover,2985-cityscapemover,2985-cityscapemover,2980-cityscapemover,
		2965-cityscapemover,2950-cityscapemover,2945-cityscapemover,2945-cityscapemover,2915-cityscapemover,2915-cityscapemover,
		2895-cityscapemover,2900-cityscapemover,2850-cityscapemover,2845-cityscapemover,2805-cityscapemover,2770-cityscapemover,
		2765-cityscapemover,2750-cityscapemover,2750-cityscapemover,2735-cityscapemover,2735-cityscapemover,2725-cityscapemover,
		2730-cityscapemover,2715-cityscapemover,2710-cityscapemover,2705-cityscapemover,2685-cityscapemover,2670-cityscapemover,
		2665-cityscapemover,2665-cityscapemover,2615-cityscapemover,2615-cityscapemover,2595-cityscapemover,2595-cityscapemover,
		2575-cityscapemover,2575-cityscapemover,2555-cityscapemover,2555-cityscapemover,2550-cityscapemover,2545-cityscapemover,
		2530-cityscapemover,2530-cityscapemover,2510-cityscapemover,2505-cityscapemover,2490-cityscapemover,2495-cityscapemover,
		2485-cityscapemover,2470-cityscapemover,2425-cityscapemover,2420-cityscapemover,2390-cityscapemover,2385-cityscapemover,
		2365-cityscapemover,2365-cityscapemover,2320-cityscapemover,2300-cityscapemover,2275-cityscapemover,2275-cityscapemover,
		2265-cityscapemover,2265-cityscapemover,2220-cityscapemover,2215-cityscapemover,2210-cityscapemover,2210-cityscapemover,
		2220-cityscapemover,2205-cityscapemover,2210-cityscapemover,2180-cityscapemover,2160-cityscapemover,2130-cityscapemover,
		2125-cityscapemover,2115-cityscapemover,2120-cityscapemover,2125-cityscapemover,2130-cityscapemover,2130-cityscapemover,
		2130-cityscapemover,2115-cityscapemover,2115-cityscapemover,2115-cityscapemover,2105-cityscapemover,2075-cityscapemover,
		2060-cityscapemover,2045-cityscapemover,2045-cityscapemover,2045-cityscapemover,2025-cityscapemover,2020-cityscapemover,
		2020-cityscapemover,2000-cityscapemover,1995-cityscapemover,1990-cityscapemover,1990-cityscapemover,1990-cityscapemover,
		1985-cityscapemover,1980-cityscapemover,1975-cityscapemover,1975-cityscapemover,1965-cityscapemover,1945-cityscapemover,
		1925-cityscapemover,1900-cityscapemover,1875-cityscapemover,1855-cityscapemover,1840-cityscapemover,1830-cityscapemover,
		1825-cityscapemover,2110-cityscapemover,2385-cityscapemover,2665-cityscapemover,2875-cityscapemover};
		
		int[] cy={730,715,705,710,690,680,680,680,645,615,620,645,645,605,605,345,390,420,625,630,545,545,535,535,495,
		495,425,395,380,395,420,455,460,430,425,375,370,275,260,210,215,355,360,420,425,560,565,480,345,330,325,335,
		335,500,505,380,375,385,395,590,590,480,475,580,585,440,365,365,310,280,280,305,360,360,385,420,485,590,655,
		660,530,465,435,435,455,485,530,580,595,615,575,575,600,630,660,680,680,625,595,595,650,635,630,630,640,660,
		680,710,730,735,735,735,735};
		g.setColor(new Color(255,255,255,50));
		g.fillPolygon(cx,cy,113);
	}
	
	public void drawCar(Graphics g)
	{
		g.setColor(new Color(carred, cargreen, carblue));
		g.fillPolygon(bodyx,bodyy,114);
		//window
		g.setColor(new Color(66,55,115));
		g.fillPolygon(window1x,window1y,20);
		//secondwindow
		g.setColor(new Color(66,55,115));
		g.fillPolygon(window2x,window2y,11);
		//frontlight
		g.setColor(Color.YELLOW);
		g.fillPolygon(frontlightx,frontlighty,11);
		//blacklight
		g.setColor(new Color(243,50,128));
		g.fillPolygon(backlightx,backlighty,11);
		//grille
		g.setColor(Color.BLACK);
		g.fillPolygon(grillex,grilley,10);
		//handle
		g.fillPolygon(handlex,handley,9);
		
		//wheel1
		g.setColor(new Color(54,50,67));
		g.fillOval(wheel11x,820,59,48);
		g.setColor(new Color(139,131,163)); //grey
		g.fillOval(wheel12x,829,33,29);
		
		//wheel2
		g.setColor(new Color(54,50,67));
		g.fillOval(wheel21x,823,59,44);
		g.setColor(new Color(139,131,163));
		g.fillOval(wheel22x,831,34,27);
		
	}
	
	
	
	public void DinoFrame0(Graphics g, int y)
	{
		g.setColor(new Color(dinored, dinogreen, dinoblue));
		
		g.fillRect(208, 687-y, 9, 49);
		g.fillRect(217, 703-y, 7, 33);
		g.fillRect(223, 711-y, 10, 32);
		g.fillRect(233, 718-y, 9, 33);
		g.fillRect(242, 718-y, 7, 41);
		g.fillRect(249, 710-y, 8, 93);
		g.fillRect(257, 702-y, 3, 101);
																							g.fillRect(259, 793-y, 8, 10);
		g.fillRect(260, 702-y, 8, 84);
		g.fillRect(268, 702-y, 3, 72);
		g.fillRect(271, 693-y, 5, 81);
		g.fillRect(276, 693-y, 7, 72);
		g.fillRect(283, 685-y, 1, 80);
		g.fillRect(284, 685-y, 8, 94);
		g.fillRect(291, 631-y, 9, 173);
		g.fillRect(300, 622-y, 1, 182);
		g.fillRect(301, 622-y, 8, 140);
		g.fillRect(301, 793-y, 7, 11);
		g.fillRect(309, 622-y, 7, 15);
		g.fillRect(309, 644-y, 7, 110);
		g.fillRect(316, 622-y, 2, 132);
		g.fillRect(318, 622-y, 8, 120);
		g.fillRect(326, 622-y, 8, 64);
																							g.fillRect(326, 702-y, 6, 10);
																							g.fillRect(332, 702-y, 11, 18);
		g.fillRect(334, 622-y, 34, 48);
		g.fillRect(334, 677-y, 25, 9); 
		g.fillRect(368, 631-y, 8, 39);
	}
	
	public void DinoFrame1(Graphics g)
	{
		g.setColor(new Color(dinored, dinogreen, dinoblue));
		g.fillRect(208, 687, 9, 49);
		g.fillRect(217, 703, 7, 33);
		g.fillRect(223, 711, 10, 32);
		g.fillRect(233, 718, 9, 33);
		g.fillRect(242, 718, 7, 41);
		g.fillRect(249, 710, 8, 93);
		g.fillRect(257, 702, 3, 101);
																							g.fillRect(259, 793, 8, 10);
		g.fillRect(260, 702, 8, 84);
		g.fillRect(268, 702, 3, 72);
		g.fillRect(271, 693, 5, 81);
		g.fillRect(276, 693, 7, 72);
		g.fillRect(283, 685, 1, 80);
		g.fillRect(284, 685, 8, 94);
		g.fillRect(291, 631, 9, 159);
		g.fillRect(300, 622, 1, 168);
		g.fillRect(301, 622, 8, 140);
		g.fillRect(301, 779, 7, 11);
		g.fillRect(309, 622, 7, 15);
		g.fillRect(309, 644, 7, 110);
		g.fillRect(316, 622, 2, 132);
		g.fillRect(318, 622, 8, 120);
		g.fillRect(326, 622, 8, 64);
																							g.fillRect(326, 702, 6, 10);
																							g.fillRect(332, 702, 11, 18);
		g.fillRect(334, 622, 34, 48);
		g.fillRect(334, 677, 25, 9); 
		g.fillRect(368, 631, 8, 39);
		if (!dino.collisioncheck())animation+=1;
	}
	
	public void DinoFrame2(Graphics g)
	{
		g.setColor(new Color(dinored, dinogreen, dinoblue));
		g.fillRect(208, 687, 9, 49);
		g.fillRect(217, 703, 7, 33);
		g.fillRect(223, 711, 10, 32);
		g.fillRect(233, 718, 9, 33);
		g.fillRect(242, 718, 7, 41);
		g.fillRect(249, 710, 8, 69);
		g.fillRect(257, 702, 3, 77);
																							 g.fillRect(260, 786, 8, 10);
		g.fillRect(260, 702, 8, 84);
		g.fillRect(268, 702, 3, 72);
		g.fillRect(271, 693, 5, 81);
		g.fillRect(276, 693, 7, 72);
		g.fillRect(283, 685, 1, 80);
		g.fillRect(284, 685, 8, 94);
		g.fillRect(291, 631, 9, 173);
		g.fillRect(300, 622, 1, 182);
		g.fillRect(301, 622, 8, 140);
		g.fillRect(301, 793, 7, 11);
		g.fillRect(309, 622, 7, 15);
		g.fillRect(309, 644, 7, 110);
		g.fillRect(316, 622, 2, 132);
		g.fillRect(318, 622, 8, 120);
		g.fillRect(326, 622, 8, 64);
																							g.fillRect(326, 702, 6, 10);
																							g.fillRect(332, 702, 11, 18);
		g.fillRect(334, 622, 34, 48);
		g.fillRect(334, 677, 25, 9); 
		g.fillRect(368, 631, 8, 39);
	}
	
	public void jumpDino(Graphics g)
	{
		sum+=counter;
		
		if (sum<=0)
		{
			DinoFrame0(g, 0);
			a = false;
			counter = 80;
			sum = 0;
		}
		else DinoFrame0(g, (int) sum);
		System.out.println(sum);
		counter-=7;
	}
	
	public int score(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier", Font.BOLD, 30));
		g.drawString((animation+2)/20 + "", 1800, 50);
		return (animation+2)/20;
	}
		
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (!dino.collisioncheck() && !a)
		{
			Audio.music("jump beep.wav");
			a = true;
		}
		repaint();
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

	public void mouseClicked(MouseEvent e)
	{
		dinored = (int) (Math.random()*256);
		dinogreen = (int) (Math.random()*256);
		dinoblue = (int) (Math.random()*256);
		repaint();
	}

	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}

class Audio
{
	public static void music(String name) 
    {       
       // AudioPlayer MGP = AudioPlayer.player;
       // AudioStream BGM;
       // AudioData MD;

       // ContinuousAudioDataStream loop = null;

        /*try
        {
            InputStream test = new FileInputStream(name);
        //    BGM = new AudioStream(test);
       //     AudioPlayer.player.start(BGM);
            //MD = BGM.getData();
            //loop = new ContinuousAudioDataStream(MD);

        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
       // MGP.start(loop);*/
    }
}