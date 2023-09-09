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
		//��д����͸�������÷���
		JLabel backgroundLabel = new JLabel() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); // ����͸���ȣ������ֵΪ 0.5f
				super.paintComponent(g2d);
				g2d.dispose();
			}
		};
		backgroundLabel.setIcon(backgroundImage);
		backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
		frame.setContentPane(backgroundLabel);

		JScrollPane scrollPane = new JScrollPane();
		

		// ȷ��
		yes = new JButton("ȷ  ��");
		yes.setEnabled(false);
		yes.setFont(new Font("����", Font.BOLD, 30));
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
				JOptionPane.showMessageDialog(null, "�������\n������ 123456", "Error", JOptionPane.ERROR_MESSAGE);

		});
		
		// ȡ��
		JButton no = new JButton("ȡ  ��");
		no.setFont(new Font("����", Font.BOLD, 32));
		no.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				System.exit(1);
			}
		});

		JLabel labelTitle = new JLabel("����ϵͳ�γ����");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("����", Font.PLAIN, 60));

		JButton buttonEnter=new JButton("����");
		buttonEnter.setHorizontalAlignment(SwingConstants.CENTER);
		buttonEnter.setFont(new Font("��������",Font.PLAIN,70));
		ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/osicon.png")));
		Image image = backgroundIcon.getImage();

// ����ͼ��ߴ�
		int iconWidth = 60;  // ����ͼ����
		int iconHeight = 60; // ����ͼ��߶�
		Image scaledImage = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);

// �����µ� ImageIcon��������Ϊ��ťͼ��
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
		JLabel labelDate = new JLabel("��2023/7-2023/10");
		labelDate.setHorizontalAlignment(SwingConstants.CENTER);
		labelDate.setFont(new Font("����", Font.ITALIC, 34));

		JLabel labelTeacher = new JLabel("ָ����ʦ�������");
		labelTeacher.setHorizontalAlignment(SwingConstants.CENTER);
		labelTeacher.setFont(new Font("����", Font.PLAIN, 35));

		JLabel labelTeam = new JLabel("�Ŷӳ�Ա��21��8");
		labelTeam.setHorizontalAlignment(SwingConstants.CENTER);
		labelTeam.setFont(new Font("����", Font.PLAIN, 34));

		JLabel labelXIE = new JLabel("202125220823 л껾�");
		labelXIE.setHorizontalAlignment(SwingConstants.CENTER);
		labelXIE.setFont(new Font("����", Font.PLAIN, 30));

		JLabel labelZHOU = new JLabel("202125220851 ���Ӵ�");
		labelZHOU.setHorizontalAlignment(SwingConstants.CENTER);
		labelZHOU.setFont(new Font("����", Font.PLAIN, 30));

		JLabel labelWANG = new JLabel("202125220821 ������");
		labelWANG.setHorizontalAlignment(SwingConstants.CENTER);
		labelWANG.setFont(new Font("����", Font.PLAIN, 30));

		JLabel labelSU = new JLabel("202125220820 ��  ɭ");
		labelSU.setHorizontalAlignment(SwingConstants.CENTER);
		labelSU.setFont(new Font("����", Font.PLAIN, 30));

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		frame.getContentPane().setLayout(groupLayout);

		JLabel labelOpenInformation = new JLabel("������Ϣ");

		labelOpenInformation.setHorizontalAlignment(SwingConstants.CENTER);
		labelOpenInformation.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane.setColumnHeaderView(labelOpenInformation);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("����", Font.PLAIN, 18));

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
