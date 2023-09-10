package code.gui;

import code.Computer;
import code.barecomputer.Clock;
import code.barecomputer.MyLock;
import code.devicemanage.PcbBlock;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class DeviceGUI extends Thread
{

	private JFrame frame;
	private JTextArea cameraText;
	private JTextArea printerText;
	private JTextArea outputText;
	private JTextArea inputText;
	private JTextArea fileText;
	private JTextArea waitText;

	private JProgressBar cameraBar;
	private JProgressBar outputBar;
	private JProgressBar inputBar;
	private JProgressBar printerABar;
	private JProgressBar printerBBar;
	private JProgressBar printerCBar;
	
	private JTextPane fileLast;
	private JTextPane inputLast;
	private JTextPane outputLast;
	private JTextPane cameraLast;
	private JTextPane printerLast;
	
	
	/**
	 * Create the application.
	 */
	public DeviceGUI() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JScrollPane scrollPane_3 = new JScrollPane();
		
		JScrollPane scrollPane_4 = new JScrollPane();
		
		JScrollPane scrollPane_5 = new JScrollPane();
		
		JScrollPane scrollPane_6 = new JScrollPane();
		
		JScrollPane scrollPane_7 = new JScrollPane();
		
		JScrollPane scrollPane_8 = new JScrollPane();
		
		JScrollPane scrollPane_9 = new JScrollPane();
		
		JLabel lblNewLabel_5 = new JLabel("A打印机");
		lblNewLabel_5.setFont(new Font("����", Font.PLAIN, 24));
		
		printerABar = new JProgressBar();
		printerABar.setFont(new Font("����", Font.PLAIN, 24));
		printerABar.setToolTipText("打印机A有效工作时间");
		printerABar.setStringPainted(true);
		
		JLabel lblNewLabel_5_1 = new JLabel("B打印机");
		lblNewLabel_5_1.setFont(new Font("����", Font.PLAIN, 24));
		
		printerBBar = new JProgressBar();
		printerBBar.setStringPainted(true);
		printerBBar.setToolTipText("打印机B有效工作时间");
		printerBBar.setFont(new Font("����", Font.PLAIN, 24));
		
		JLabel lblNewLabel_5_2 = new JLabel("C打印机");
		lblNewLabel_5_2.setFont(new Font("����", Font.PLAIN, 24));
		
		printerCBar = new JProgressBar();
		printerCBar.setFont(new Font("����", Font.PLAIN, 24));
		printerCBar.setToolTipText("打印机C有效工作时间");
		printerCBar.setStringPainted(true);
		
		JScrollPane scrollPane_8_1 = new JScrollPane();
		
		// �رհ�ť
		JButton closeButton = new JButton("关闭");
		closeButton.setFont(new Font("����", Font.BOLD, 48));
		closeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				unshowWindow();
			}
		});
		
		JPanel panel = new JPanel();
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_7, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane_8, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane_9, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPane_8_1, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(closeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel_5_2, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(printerCBar, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(printerABar, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel_5_1, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(printerBBar, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))))
					.addGap(27))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
								.addComponent(scrollPane_5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_7, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
										.addComponent(printerABar, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_5_1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
										.addComponent(printerBBar, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_5_2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
										.addComponent(printerCBar, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addComponent(closeButton, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
								.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
								.addComponent(scrollPane_8, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPane_9, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
										.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(scrollPane_8_1, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addGap(44))
		);
		panel.setLayout(new MigLayout("wrap 3", "[100!][100!][100!]", "[50!][50!][50!][50!][50!][50!][50!]"));
		
		JLabel lblNewLabel_7 = new JLabel("设备号");
		lblNewLabel_7.setFont(new Font("����", Font.PLAIN, 32));
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_7, "cell 0 0");
		
		JLabel lblNewLabel_7_1 = new JLabel("设备名");
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7_1.setFont(new Font("����", Font.PLAIN, 32));
		panel.add(lblNewLabel_7_1, "cell 1 0");
		
		JLabel lblNewLabel_7_2 = new JLabel("入  口");
		lblNewLabel_7_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7_2.setFont(new Font("����", Font.PLAIN, 32));
		panel.add(lblNewLabel_7_2, "cell 2 0");
		
		JLabel lblNewLabel_8 = new JLabel("0\r\n");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_8, "cell 0 1");
		
		JLabel lblNewLabel_10 = new JLabel("Logi_K380");
		lblNewLabel_10.setFont(new Font("����", Font.BOLD, 18));
		panel.add(lblNewLabel_10, "cell 1 1");
		
		JLabel lblNewLabel_8_1 = new JLabel("2060H");
		lblNewLabel_8_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8_1.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_8_1, "cell 2 1");
		
		JLabel lblNewLabel_9 = new JLabel("1");
		lblNewLabel_9.setFont(new Font("����", Font.BOLD, 24));
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_9, "cell 0 2");
		
		JLabel lblNewLabel_10_1 = new JLabel("Dell_LED");
		lblNewLabel_10_1.setFont(new Font("����", Font.BOLD, 18));
		panel.add(lblNewLabel_10_1, "cell 1 2");
		
		JLabel lblNewLabel_8_2 = new JLabel("2061H");
		lblNewLabel_8_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8_2.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_8_2, "cell 2 2");
		
		JLabel lblNewLabel_9_1 = new JLabel("2");
		lblNewLabel_9_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_9_1.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_9_1, "cell 0 3");
		
		JLabel lblNewLabel_10_2 = new JLabel("PrinterA");
		lblNewLabel_10_2.setFont(new Font("����", Font.BOLD, 18));
		panel.add(lblNewLabel_10_2, "cell 1 3");
		
		JLabel lblNewLabel_8_3 = new JLabel("2062H");
		lblNewLabel_8_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8_3.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_8_3, "cell 2 3");
		
		JLabel lblNewLabel_9_2 = new JLabel("3");
		lblNewLabel_9_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_9_2.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_9_2, "cell 0 4");
		
		JLabel lblNewLabel_10_3 = new JLabel("PrinterB");
		lblNewLabel_10_3.setFont(new Font("����", Font.BOLD, 18));
		panel.add(lblNewLabel_10_3, "cell 1 4");
		
		JLabel lblNewLabel_8_4 = new JLabel("2063H");
		lblNewLabel_8_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8_4.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_8_4, "cell 2 4");
		
		JLabel lblNewLabel_9_3 = new JLabel("4");
		lblNewLabel_9_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_9_3.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_9_3, "cell 0 5");
		
		JLabel lblNewLabel_10_4 = new JLabel("PrinterC");
		lblNewLabel_10_4.setFont(new Font("����", Font.BOLD, 18));
		panel.add(lblNewLabel_10_4, "cell 1 5");
		
		JLabel lblNewLabel_8_5 = new JLabel("2064H");
		lblNewLabel_8_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8_5.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_8_5, "cell 2 5");
		
		JLabel lblNewLabel_9_4 = new JLabel("5");
		lblNewLabel_9_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_9_4.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_9_4, "cell 0 6");
		
		JLabel lblNewLabel_10_5 = new JLabel("Lenovo");
		lblNewLabel_10_5.setFont(new Font("����", Font.BOLD, 18));
		panel.add(lblNewLabel_10_5, "cell 1 6");
		
		JLabel lblNewLabel_8_6 = new JLabel("2065H");
		lblNewLabel_8_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8_6.setFont(new Font("����", Font.BOLD, 24));
		panel.add(lblNewLabel_8_6, "cell 2 6");
		
		JLabel lblNewLabel_6 = new JLabel("等待打印机队列");
		lblNewLabel_6.setFont(new Font("����", Font.PLAIN, 32));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_8_1.setColumnHeaderView(lblNewLabel_6);
		
		waitText = new JTextArea();
		waitText.setFont(new Font("����", Font.PLAIN, 14));
		waitText.setEditable(false);
		scrollPane_8_1.setViewportView(waitText);
		
		cameraText = new JTextArea();
		cameraText.setEditable(false);
		cameraText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_9.setViewportView(cameraText);
		
		printerText = new JTextArea();
		printerText.setEditable(false);
		printerText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_8.setViewportView(printerText);
		
		outputText = new JTextArea();
		outputText.setEditable(false);
		outputText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_7.setViewportView(outputText);
		
		inputText = new JTextArea();
		inputText.setEditable(false);
		inputText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_6.setViewportView(inputText);
		
		fileText = new JTextArea();
		fileText.setEditable(false);
		fileText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_5.setViewportView(fileText);
		
		JLabel lblNewLabel_4 = new JLabel("摄像机");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_4.setColumnHeaderView(lblNewLabel_4);
		
		cameraBar = new JProgressBar();
		cameraBar.setFont(new Font("����", Font.PLAIN, 24));
		cameraBar.setToolTipText("摄像机有效工作时间");
		cameraBar.setStringPainted(true);
		cameraBar.setOrientation(SwingConstants.VERTICAL);
		scrollPane_4.setRowHeaderView(cameraBar);
		
		cameraLast = new JTextPane();
		cameraLast.setFont(new Font("����", Font.PLAIN, 24));
		cameraLast.setEditable(false);
		scrollPane_4.setViewportView(cameraLast);
		
		JLabel lblNewLabel_3 = new JLabel("打印机");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_3.setColumnHeaderView(lblNewLabel_3);
		
		printerLast = new JTextPane();
		printerLast.setFont(new Font("����", Font.PLAIN, 24));
		printerLast.setEditable(false);
		scrollPane_3.setViewportView(printerLast);
		
		JLabel lblNewLabel_2 = new JLabel("屏幕输出");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_2.setColumnHeaderView(lblNewLabel_2);
		
		outputBar = new JProgressBar();
		outputBar.setFont(new Font("����", Font.PLAIN, 24));
		outputBar.setToolTipText("屏幕有效工作时间");
		outputBar.setStringPainted(true);
		outputBar.setOrientation(SwingConstants.VERTICAL);
		scrollPane_2.setRowHeaderView(outputBar);
		
		outputLast = new JTextPane();
		outputLast.setFont(new Font("����", Font.PLAIN, 24));
		outputLast.setEditable(false);
		scrollPane_2.setViewportView(outputLast);
		
		JLabel lblNewLabel_1 = new JLabel("键盘输入");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_1.setColumnHeaderView(lblNewLabel_1);
		
		inputBar = new JProgressBar();
		inputBar.setFont(new Font("����", Font.PLAIN, 24));
		inputBar.setToolTipText("键盘有效工作时间");
		inputBar.setStringPainted(true);
		inputBar.setOrientation(SwingConstants.VERTICAL);
		scrollPane_1.setRowHeaderView(inputBar);
		
		inputLast = new JTextPane();
		inputLast.setFont(new Font("����", Font.PLAIN, 24));
		inputLast.setEditable(false);
		scrollPane_1.setViewportView(inputLast);
		
		JLabel lblNewLabel = new JLabel("文件调用");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		fileLast = new JTextPane();
		fileLast.setEditable(false);
		fileLast.setFont(new Font("����", Font.PLAIN, 24));
		scrollPane.setViewportView(fileLast);
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
		this.unshowWindow();
		try {
			while(true)
			{
				synchronized (MyLock.GUILock)
				{
					MyLock.GUILock.wait();
					// ˢ������������Ϣ
					flashBlockInfo();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * other
	 * */
	public void flashBlockInfo()
	{
		int time = 0;
		int last = 0;
		int clock = Clock.getClock() + 1;
		int use = 0;
		// �ļ�����
		last = Computer.memory.fileNum;
		fileLast.setText("ʣ����Դ:\n\t"+last+"\n����Դ��:\n\t12");
		fileText.setText("");
		if(last!=4)
		{
			int num = Computer.memory.fileBlock.list.size();
			for(int i=0;i<num;++i)
			{
				PcbBlock temp = Computer.memory.fileBlock.list.get(i);
				fileText.append("("+i+")\n");
				fileText.append(temp.getString());
			}
		}
		// ����
		last = Computer.memory.keyboardNum;
		time = Computer.memory.deviceTable.time[0];
		inputLast.setText("ʣ����Դ:\n\t"+last+"\n����Դ��:\n\t1");
		use = (time*100) / clock;
		inputBar.setValue(use);
		inputBar.setString(use+"%");
		inputBar.setToolTipText("��Ч����ʱ��: "+time);
		inputText.setText("");
		if(last!=1)
		{
			int num = Computer.memory.inputBlock.list.size();
			for(int i=0;i<num;++i)
			{
				PcbBlock temp = Computer.memory.inputBlock.list.get(i);
				inputText.append("("+i+")\n");
				inputText.append(temp.getString());
			}
		}
		// ��Ļ
		last = Computer.memory.screenNum;
		outputLast.setText("ʣ����Դ:\n\t"+last+"\n����Դ��:\n\t1");
		time = Computer.memory.deviceTable.time[1];
		use = (time*100) / clock;
		outputBar.setValue(use);
		outputBar.setString(use+"%");
		outputBar.setToolTipText("��Ч����ʱ��: "+time);
		outputText.setText("");
		if(last!=1)
		{
			int num = Computer.memory.outputBlock.list.size();
			for(int i=0;i<num;++i)
			{
				PcbBlock temp = Computer.memory.outputBlock.list.get(i);
				outputText.append("("+i+")\n");
				outputText.append(temp.getString());
			}
		}
		// ��ӡ��
		String[] printerID = {"A", "B", "C"};
		last = Computer.memory.printerNum;
		printerLast.setText("ʣ����Դ:\n\t"+last+"\n����Դ��:\n\t3");
		
		time = Computer.memory.deviceTable.time[2];
		use = (time*100) / clock;
		printerABar.setValue(use);
		printerABar.setString(use+"%");
		printerABar.setToolTipText("��Ч����ʱ��: "+time);
		
		time = Computer.memory.deviceTable.time[3];
		use = (time*100) / clock;
		printerBBar.setValue(use);
		printerBBar.setString(use+"%");
		printerBBar.setToolTipText("��Ч����ʱ��: "+time);
		
		time = Computer.memory.deviceTable.time[4];
		use = (time*100) / clock;
		printerCBar.setValue(use);
		printerCBar.setString(use+"%");
		printerCBar.setToolTipText("��Ч����ʱ��: "+time);
		
		printerText.setText("");
		if(last!=3)
		{
			int num = Computer.memory.printerBlock.list.size();
			for(int i=0;i<num;++i)
			{
				PcbBlock temp = Computer.memory.printerBlock.list.get(i);
				printerText.append("("+i+")\n");
				printerText.append(temp.getString());
				printerText.append("��Ӧ��ӡ��: "+printerID[i]);
			}
		}
		// �����
		last = Computer.memory.cameraNum;
		cameraLast.setText("ʣ����Դ:\n\t"+last+"\n����Դ��:\n\t1");
		time = Computer.memory.deviceTable.time[5];
		use = (time*100) / clock;
		cameraBar.setValue(use);
		cameraBar.setString(use+"%");
		cameraBar.setToolTipText("��Ч����ʱ��: "+time);
		cameraText.setText("");
		if(last!=1)
		{
			int num = Computer.memory.cameraBlock.list.size();
			for(int i=0;i<num;++i)
			{
				PcbBlock temp = Computer.memory.cameraBlock.list.get(i);
				cameraText.append("("+i+")\n");
				cameraText.append(temp.getString());
			}
		}
		// �ȴ�����
		int num = Computer.memory.printerWaiting.list.size();
		waitText.setText("");
		for(int i=0;i<num;++i)
		{
			PcbBlock temp = Computer.memory.printerWaiting.list.get(i);
			waitText.append("("+i+")\n");
			waitText.append(temp.getWaitStrng());
		}
	}
}
