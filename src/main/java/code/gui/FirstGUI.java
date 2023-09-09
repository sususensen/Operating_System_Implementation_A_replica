package code.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import code.Computer;
import code.barecomputer.Clock;

import javax.swing.LayoutStyle.ComponentPlacement;

public class FirstGUI extends Thread
{

	private JFrame frame;
	private JTextField name;
	private JPasswordField password;
	private JTextArea textArea;
	public JButton yes;

	/**
	 * Create the application.
	 */
	public FirstGUI() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/osicon.png"))).getImage());

		frame.setBounds(50, 50, 1024, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/background.jpg")));
		//重写背景透明度设置方法
		JLabel backgroundLabel = new JLabel() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); // 设置透明度，这里的值为 0.5f
				super.paintComponent(g2d);
				g2d.dispose();
			}
		};
		backgroundLabel.setIcon(backgroundImage);
		backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
		frame.setContentPane(backgroundLabel);

		JScrollPane scrollPane = new JScrollPane();
		

		// 确定
		yes = new JButton("确  定");
		yes.setEnabled(false);
		yes.setFont(new Font("楷体", Font.BOLD, 30));
		yes.addActionListener(e -> {
			// TODO Auto-generated method stub
			String pword = String.valueOf(password.getPassword());
			if(pword.equals("123456"))
			{
				Clock.setFlag(true);
				Computer.mainGUI.showWindow();
				frame.dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "密码错误\n请输入 123456", "Error", JOptionPane.ERROR_MESSAGE);

		});
		
		// 取消
		JButton no = new JButton("取  消");
		no.setFont(new Font("楷体", Font.BOLD, 32));
		no.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				System.exit(1);
			}
		});

		JLabel labelTitle = new JLabel("操作系统课程设计");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("楷体", Font.PLAIN, 60));

		JButton buttonEnter=new JButton("启动");
		buttonEnter.setHorizontalAlignment(SwingConstants.CENTER);
		buttonEnter.setFont(new Font("华文琥珀",Font.PLAIN,70));
		ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/osicon.png")));
		Image image = backgroundIcon.getImage();

// 调整图像尺寸
		int iconWidth = 60;  // 设置图像宽度
		int iconHeight = 60; // 设置图像高度
		Image scaledImage = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);

// 创建新的 ImageIcon，并设置为按钮图标
		Icon scaledIcon = new ImageIcon(scaledImage);
		buttonEnter.setIcon(scaledIcon);

		buttonEnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Clock.setFlag(true);
				Computer.mainGUI.showWindow();
				frame.dispose();
			}
		});
		JLabel labelDate = new JLabel("—2023/7-2023/10");
		labelDate.setHorizontalAlignment(SwingConstants.CENTER);
		labelDate.setFont(new Font("宋体", Font.ITALIC, 34));

		JLabel labelTeacher = new JLabel("指导老师：王金凤");
		labelTeacher.setHorizontalAlignment(SwingConstants.CENTER);
		labelTeacher.setFont(new Font("楷体", Font.PLAIN, 35));

		JLabel labelTeam = new JLabel("团队成员：21软工8");
		labelTeam.setHorizontalAlignment(SwingConstants.CENTER);
		labelTeam.setFont(new Font("楷体", Font.PLAIN, 34));

		JLabel labelXIE = new JLabel("202125220823 谢昊峻");
		labelXIE.setHorizontalAlignment(SwingConstants.CENTER);
		labelXIE.setFont(new Font("楷体", Font.PLAIN, 30));

		JLabel labelZHOU = new JLabel("202125220851 周子川");
		labelZHOU.setHorizontalAlignment(SwingConstants.CENTER);
		labelZHOU.setFont(new Font("楷体", Font.PLAIN, 30));

		JLabel labelWANG = new JLabel("202125220821 王淳殷");
		labelWANG.setHorizontalAlignment(SwingConstants.CENTER);
		labelWANG.setFont(new Font("楷体", Font.PLAIN, 30));

		JLabel labelSU = new JLabel("202125220820 苏  森");
		labelSU.setHorizontalAlignment(SwingConstants.CENTER);
		labelSU.setFont(new Font("楷体", Font.PLAIN, 30));

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		frame.getContentPane().setLayout(groupLayout);

		JLabel labelOpenInformation = new JLabel("开机信息");

		labelOpenInformation.setHorizontalAlignment(SwingConstants.CENTER);
		labelOpenInformation.setFont(new Font("楷体", Font.PLAIN, 32));
		scrollPane.setColumnHeaderView(labelOpenInformation);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("宋体", Font.PLAIN, 18));

		scrollPane.setViewportView(textArea);
        scrollPane.setSize(100,200);
		groupLayout.setHorizontalGroup(
				groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup()
								.addComponent(labelTitle,1024,1024,1024)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(40)
									  .addComponent(scrollPane,250,250,250)
										.addGap(100)
										.addComponent(buttonEnter)
										.addGap(40)
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
														.addComponent(labelDate)
														.addComponent(labelTeacher)
														.addComponent(labelTeam)
														.addComponent(labelSU)
														.addComponent(labelWANG)
														.addComponent(labelZHOU)
														.addComponent(labelXIE)

												)

										)
						)
		);
//
		groupLayout.setVerticalGroup(
				groupLayout.createSequentialGroup()
						.addGap(50)
						.addComponent(labelTitle,60,60,60)
						.addGap(30)
						.addGroup(groupLayout.createParallelGroup()

								.addComponent(scrollPane,510,510,510)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(155)
										.addComponent(buttonEnter))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(labelDate)
										.addGap(40)
										.addComponent(labelTeacher)
										.addGap(40)
										.addComponent(labelTeam)
										.addGap(40)
										.addComponent(labelSU)
										.addGap(40)
										.addComponent(labelWANG)
										.addGap(40)
										.addComponent(labelZHOU)
										.addGap(40)
										.addComponent(labelXIE)
								)
						)
		);
//		groupLayout.setVerticalGroup(
//				groupLayout.createParallelGroup(Alignment.LEADING)
//						.addComponent(labelTitle,250,250,250)
//						.addGroup(groupLayout.createSequentialGroup()
//								.addGroup(groupLayout.createParallelGroup()
//										.addComponent(scrollPane,500,500,500)
//								)
//								.addGroup(groupLayout.createParallelGroup()
//										.addGroup(groupLayout.createSequentialGroup().addComponent(labelSU,200,200,200))
//										.addGroup(groupLayout.createSequentialGroup().addComponent(labelWANG,200,200,200))
//										.addGroup(groupLayout.createSequentialGroup().addComponent(labelZHOU,200,200,200))
//										.addGroup(groupLayout.createSequentialGroup().addComponent(labelXIE,200,200,200))
//								)
//						)
//		);
		//设置的是组长
        //上下组
//		GroupLayout.ParallelGroup hSeqTitle = groupLayout.createParallelGroup()
//				.addComponent(labelTitle, 1000, 1000, 1000);
//        //左右组
//
//		   GroupLayout.ParallelGroup hSeqXIE = groupLayout.createParallelGroup()
//				.addComponent(labelXIE, 250, 250, 250);
//		   GroupLayout.ParallelGroup hSeqZHOU = groupLayout.createParallelGroup()
//				.addComponent(labelZHOU, 250, 250, 250);
//		   GroupLayout.ParallelGroup hSeqWANG = groupLayout.createParallelGroup()
//				.addComponent(labelWANG, 250, 250, 250);
//		   GroupLayout.ParallelGroup hSeqSU = groupLayout.createParallelGroup()
//				.addComponent(labelSU, 250, 250, 250);
//
//		GroupLayout.ParallelGroup hSeqTotal = groupLayout.createParallelGroup()
//				.addGroup(hSeqXIE).addGroup(hSeqZHOU).addGroup(hSeqWANG).addGroup(hSeqSU);
//		GroupLayout.SequentialGroup hSeqInfromation = groupLayout.createSequentialGroup()
//						.addGap(50).addComponent(scrollPane,250,250,250).addGroup(hSeqTotal);
//
//		GroupLayout.ParallelGroup hSegGroup = groupLayout.createParallelGroup().addGroup(hSeqTitle).addGroup(hSeqInfromation);
//
//
//		GroupLayout.ParallelGroup vParaTotal = groupLayout.createParallelGroup()
//				.addComponent(labelXIE,50, 50, 50)
//				.addComponent(labelZHOU,50, 50, 50)
//				.addComponent(labelWANG,50, 50, 50)
//				.addComponent(labelSU,50, 50, 50);
//        //设置的数字是组高
//		GroupLayout.ParallelGroup vParaTitle = groupLayout.createParallelGroup()
//				.addComponent(labelTitle,120, 120, 120);
//		GroupLayout.ParallelGroup vParaOpenInformation = groupLayout.createParallelGroup()
//				.addComponent(scrollPane,509, 509, 509);
//		GroupLayout.SequentialGroup vParaInformation  = groupLayout.createSequentialGroup().addGroup(vParaOpenInformation).addGroup(vParaTotal);
//		GroupLayout.SequentialGroup vSeqGroup = groupLayout.createSequentialGroup().addGroup(vParaTitle).addGroup(vParaInformation);
//
//		groupLayout.setHorizontalGroup(hSegGroup);
//        groupLayout.setVerticalGroup(vSeqGroup);
//

		frame.getContentPane().setLayout(groupLayout);
	}
	public void showWindow()
	{
		frame.setVisible(true);
	}
	public void unshowWindow()
	{
		frame.setVisible(false);
	}
	
	/**
	 * run
	 * */
	public void run()
	{
		this.showWindow();
		try {
			while(true)
			{
				textArea.setText(Computer.openInfo);;
				sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
