package code.gui;

import code.barecomputer.*;
import code.barecomputer.Clock;
import code.barecomputer.MyLock;
import code.Computer;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.Color;

public class OSGUI extends Thread 
{
	public JFrame frame;
	public JTextField clockText;
	public JTextArea runInfor;
	public JTextArea deviceInfo;
	public JProgressBar useBar;
	public JTextField pcText;
	public JTextField irText;
	public JTextField stateText;
	public JTextField useText;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) 
	{
		OSGUI window = new OSGUI();
		window.start();
	}
	*/

	/**
	 * Create the application.
	 */
	public OSGUI() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("����", Font.PLAIN, 16));
		frame.setBounds(30, 30, 1440, 760);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);
		
		JScrollPane scrollPane_run = new JScrollPane();
		
		JLabel clockLabel = new JLabel("\u65F6\u949F");
		clockLabel.setHorizontalAlignment(SwingConstants.LEFT);
		clockLabel.setFont(new Font("����", Font.BOLD, 32));
		
		clockText = new JTextField();
		clockText.setEditable(false);
		clockText.setHorizontalAlignment(SwingConstants.CENTER);
		clockText.setText("0");
		clockText.setFont(new Font("����", Font.BOLD, 32));
		clockText.setColumns(1);
		
		// ��ͣ��ť
		JButton pauseButton = new JButton("\u6682\u505C");
		pauseButton.setFont(new Font("����", Font.PLAIN, 32));
		pauseButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				boolean flag = Clock.getFlag();
				if(flag)
				{
					pauseButton.setText("�ָ�");
					Clock.setFlag(false);
				}
				else
				{
					pauseButton.setText("��ͣ");
					Clock.setFlag(true);
				}
			}
		});
		
		JScrollPane scrollPane_device = new JScrollPane();
		
		// �ػ���ť
		JButton closeButton = new JButton("\u5173    \u673A");
		closeButton.setFont(new Font("����", Font.PLAIN, 32));
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				System.exit(1);
			}
		});
		
		// �½���ҵ��ť
		JButton newjobButton = new JButton("\u65B0\u5EFA\u4F5C\u4E1A");
		newjobButton.setFont(new Font("����", Font.PLAIN, 32));
		newjobButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				createGUI creategui = new createGUI();
				creategui.initialize();
			}
		});
		
		// �鿴�ڴ水ť
		JButton memoryButton = new JButton("\u5185\u5B58\u7BA1\u7406");
		memoryButton.setFont(new Font("����", Font.PLAIN, 32));
		memoryButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				Computer.memoryGUI.showWindow();
			}
		});
		
		// �鿴���̰�ť
		JButton diskButton = new JButton("\u78C1\u76D8\u7BA1\u7406");
		diskButton.setFont(new Font("����", Font.PLAIN, 32));
		diskButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				Computer.diskGUI.showWindow();
			}
		});
		
		// �鿴�豸��ť
		JButton deviceButton = new JButton("\u8BBE\u5907\u7BA1\u7406");
		deviceButton.setFont(new Font("����", Font.PLAIN, 32));
		deviceButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				Computer.deviceGUI.showWindow();
			}
		});
		
		// �鿴�ļ���ť
		JButton fileButton = new JButton("\u6587\u4EF6\u7BA1\u7406");
		fileButton.setFont(new Font("����", Font.PLAIN, 32));
		fileButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				Computer.fileGUI.showWindow();
			}
		});
		
		// �鿴��ҵ��ť
		JButton jobButton = new JButton("\u4F5C\u4E1A\u7BA1\u7406");
		jobButton.setFont(new Font("����", Font.PLAIN, 32));
		jobButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				Computer.jobGUI.showWindow();
			}
		});
		
		// �鿴���̰�ť
		JButton processButton = new JButton("\u8FDB\u7A0B\u7BA1\u7406");
		processButton.setFont(new Font("����", Font.PLAIN, 32));
		processButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				Computer.processGUI.showWindow();
			}
		});
		
		JLabel lblCpu = new JLabel("CPU");
		lblCpu.setFont(new Font("����", Font.BOLD, 32));
		lblCpu.setHorizontalAlignment(SwingConstants.LEFT);
		
		pcText = new JTextField();
		pcText.setText("PC: 0");
		pcText.setHorizontalAlignment(SwingConstants.LEFT);
		pcText.setEditable(false);
		pcText.setFont(new Font("����", Font.PLAIN, 16));
		pcText.setColumns(10);
		
		irText = new JTextField();
		irText.setText("IR: null");
		irText.setHorizontalAlignment(SwingConstants.LEFT);
		irText.setFont(new Font("����", Font.PLAIN, 16));
		irText.setEditable(false);
		irText.setColumns(10);
		
		stateText = new JTextField();
		stateText.setText("\u72B6\u6001: \u6838\u5FC3\u6001");
		stateText.setHorizontalAlignment(SwingConstants.LEFT);
		stateText.setFont(new Font("����", Font.PLAIN, 16));
		stateText.setEditable(false);
		stateText.setColumns(10);
		
		useText = new JTextField();
		useText.setText("\u4F7F\u7528\u7387: 0.00%");
		useText.setHorizontalAlignment(SwingConstants.LEFT);
		useText.setFont(new Font("����", Font.PLAIN, 16));
		useText.setEditable(false);
		useText.setColumns(10);
		
		useBar = new JProgressBar();
		useBar.setFont(new Font("����", Font.PLAIN, 16));
		useBar.setToolTipText("CPU\u5229\u7528\u7387");
		useBar.setForeground(new Color(0, 120, 215));
		useBar.setStringPainted(true);
		
		JLabel lblMsf = new JLabel("M S F");
		lblMsf.setFont(new Font("����", Font.PLAIN, 48));
		
		JLabel lblComputeros = new JLabel("computer_os");
		lblComputeros.setFont(new Font("���� Light", Font.PLAIN, 18));
		
		JButton osButton = new JButton("\u64CD\u4F5C\u7CFB\u7EDF\u8FDB\u7A0B");
		osButton.setFont(new Font("����", Font.PLAIN, 32));
		osButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				OsProcessGUI tempGUI = new OsProcessGUI();
				tempGUI.initialize();
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(21)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane_run, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(clockLabel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(clockText, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(pauseButton, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(newjobButton, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)))
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(memoryButton, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(diskButton, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
							.addComponent(deviceButton, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(jobButton, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblCpu, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
									.addGap(486))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(stateText, Alignment.LEADING)
										.addComponent(pcText, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(19)
											.addComponent(irText, 266, 266, 266))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(18)
											.addComponent(useText))))
								.addComponent(useBar, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(fileButton, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
											.addGap(19))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblMsf)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblComputeros, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
											.addGap(47)))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(osButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(processButton, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)))))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(closeButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
								.addComponent(scrollPane_device, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(closeButton, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
						.addComponent(clockLabel, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
						.addComponent(pauseButton, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
						.addComponent(newjobButton, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
						.addComponent(clockText, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane_run, GroupLayout.PREFERRED_SIZE, 559, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_device, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(lblCpu)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(pcText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(irText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(stateText, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(useText, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(useBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(fileButton, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
								.addComponent(processButton, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(memoryButton, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
							.addComponent(jobButton, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
							.addComponent(diskButton, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
							.addComponent(deviceButton, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblMsf)
							.addComponent(lblComputeros))
						.addComponent(osButton, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		JLabel Label_B = new JLabel("\u5916\u8BBE\u5DE5\u4F5C\u60C5\u51B5");
		scrollPane_device.setColumnHeaderView(Label_B);
		Label_B.setFont(new Font("����", Font.BOLD, 32));
		Label_B.setHorizontalAlignment(SwingConstants.CENTER);
		
		deviceInfo = new JTextArea();
		deviceInfo.setFont(new Font("����", Font.PLAIN, 16));
		deviceInfo.setEditable(false);
		scrollPane_device.setViewportView(deviceInfo);
		
		runInfor = new JTextArea();
		runInfor.setFont(new Font("����", Font.PLAIN, 16));
		runInfor.setEditable(false);
		scrollPane_run.setViewportView(runInfor);
		
		JLabel Label_A = new JLabel("\u8BA1\u7B97\u673A\u5185\u90E8\u5173\u952E\u4FE1\u606F");
		Label_A.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_run.setColumnHeaderView(Label_A);
		Label_A.setFont(new Font("����", Font.BOLD, 32));
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
					// ��ʱ������
					int oldClock = Integer.parseInt(clockText.getText());
					oldClock++;
					clockText.setText(String.valueOf(oldClock));
					// RunInfo����
					showRunInfo();
					// DeviceInfo����
					showDevInfo();
					// CPUInofo����
					showCPU();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ť����������
	 * */
	// ����ʱϵͳʵʱ����
	public void showRunInfo()
	{
		runInfor.append(Computer.RunInfo);
		Computer.RunInfo = "";
		runInfor.repaint();
	}
	// ����ʱ����ʵʱ����
	public void showDevInfo()
	{
		deviceInfo.append(Computer.DeviceInfo);
		Computer.DeviceInfo = "";
		deviceInfo.repaint();
	}
	// ����CPU����Ϣ��ʾ
	public void showCPU()
	{
		pcText.setText(String.format("PC: %d", Computer.cpu.getPCIndex()));
		irText.setText(String.format("IR: %s", Computer.cpu.getIR().getInfo()));
		//stateText.setText(String.format("״̬: %s", Computer.cpu.getStateString()));
		double use = (double)Computer.cpu.Time / (double)(Clock.getClock()+1);
		use = use * 100;
		useText.setText(String.format("������: %.2f", use)+"%");
		useBar.setValue((int)use);
		useBar.setString(String.format("%d", (int)use));
	}
}
