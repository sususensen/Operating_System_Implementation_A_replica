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
		frame.getContentPane().setFont(new Font("宋体", Font.PLAIN, 16));
		frame.setBounds(30, 30, 1440, 760);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);
		
		JScrollPane scrollPane_run = new JScrollPane();
		
		JLabel clockLabel = new JLabel("时钟");
		clockLabel.setHorizontalAlignment(SwingConstants.LEFT);
		clockLabel.setFont(new Font("黑体", Font.BOLD, 32));
		
		clockText = new JTextField();
		clockText.setEditable(false);
		clockText.setHorizontalAlignment(SwingConstants.CENTER);
		clockText.setText("0");
		clockText.setFont(new Font("宋体", Font.BOLD, 32));
		clockText.setColumns(1);
		
		// 暂停按钮
		JButton pauseButton = new JButton("暂停");
		pauseButton.setFont(new Font("楷体", Font.PLAIN, 32));
		pauseButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				boolean flag = Clock.getFlag();
				if(flag)
				{
					pauseButton.setText("恢复");
					Clock.setFlag(false);
				}
				else
				{
					pauseButton.setText("暂停");
					Clock.setFlag(true);
				}
			}
		});
		
		JScrollPane scrollPane_device = new JScrollPane();
		
		// 关机按钮
		JButton closeButton = new JButton("关    机");
		closeButton.setFont(new Font("楷体", Font.PLAIN, 32));
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				System.exit(1);
			}
		});
		
		// 新建作业按钮
		JButton newjobButton = new JButton("新建作业");
		newjobButton.setFont(new Font("楷体", Font.PLAIN, 32));
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
		
		// 查看内存按钮
		JButton memoryButton = new JButton("内存管理");
		memoryButton.setFont(new Font("楷体", Font.PLAIN, 32));
		memoryButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				Computer.memoryGUI.showWindow();
			}
		});
		
		// 查看磁盘按钮
		JButton diskButton = new JButton("磁盘管理");
		diskButton.setFont(new Font("楷体", Font.PLAIN, 32));
		diskButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				Computer.diskGUI.showWindow();
			}
		});
		
		// 查看设备按钮
		JButton deviceButton = new JButton("设备管理");
		deviceButton.setFont(new Font("楷体", Font.PLAIN, 32));
		deviceButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				Computer.deviceGUI.showWindow();
			}
		});
		
		// 查看文件按钮
		JButton fileButton = new JButton("文件管理");
		fileButton.setFont(new Font("楷体", Font.PLAIN, 32));
		fileButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				Computer.fileGUI.showWindow();
			}
		});
		
		// 查看作业按钮
		JButton jobButton = new JButton("作业管理");
		jobButton.setFont(new Font("楷体", Font.PLAIN, 32));
		jobButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				Computer.jobGUI.showWindow();
			}
		});
		
		// 查看进程按钮
		JButton processButton = new JButton("进程管理");
		processButton.setFont(new Font("楷体", Font.PLAIN, 32));
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
		lblCpu.setFont(new Font("黑体", Font.BOLD, 32));
		lblCpu.setHorizontalAlignment(SwingConstants.LEFT);
		
		pcText = new JTextField();
		pcText.setText("PC: 0");
		pcText.setHorizontalAlignment(SwingConstants.LEFT);
		pcText.setEditable(false);
		pcText.setFont(new Font("宋体", Font.PLAIN, 16));
		pcText.setColumns(10);
		
		irText = new JTextField();
		irText.setText("IR: null");
		irText.setHorizontalAlignment(SwingConstants.LEFT);
		irText.setFont(new Font("宋体", Font.PLAIN, 16));
		irText.setEditable(false);
		irText.setColumns(10);
		
		stateText = new JTextField();
		stateText.setText("状态: 核心态");
		stateText.setHorizontalAlignment(SwingConstants.LEFT);
		stateText.setFont(new Font("宋体", Font.PLAIN, 16));
		stateText.setEditable(false);
		stateText.setColumns(10);
		
		useText = new JTextField();
		useText.setText("使用率: 0.00%");
		useText.setHorizontalAlignment(SwingConstants.LEFT);
		useText.setFont(new Font("宋体", Font.PLAIN, 16));
		useText.setEditable(false);
		useText.setColumns(10);
		
		useBar = new JProgressBar();
		useBar.setFont(new Font("宋体", Font.PLAIN, 16));
		useBar.setToolTipText("CPU利用率");
		useBar.setForeground(new Color(0, 120, 215));
		useBar.setStringPainted(true);
		
		JLabel lblMsf = new JLabel("M S F");
		lblMsf.setFont(new Font("黑体", Font.PLAIN, 48));
		
		JLabel lblComputeros = new JLabel("computer_os");
		lblComputeros.setFont(new Font("等线 Light", Font.PLAIN, 18));
		
		JButton osButton = new JButton("操作系统进程");
		osButton.setFont(new Font("楷体", Font.PLAIN, 32));
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
		
		JLabel Label_B = new JLabel("外设工作情况");
		scrollPane_device.setColumnHeaderView(Label_B);
		Label_B.setFont(new Font("宋体", Font.BOLD, 32));
		Label_B.setHorizontalAlignment(SwingConstants.CENTER);
		
		deviceInfo = new JTextArea();
		deviceInfo.setFont(new Font("仿宋", Font.PLAIN, 16));
		deviceInfo.setEditable(false);
		scrollPane_device.setViewportView(deviceInfo);
		
		runInfor = new JTextArea();
		runInfor.setFont(new Font("仿宋", Font.PLAIN, 16));
		runInfor.setEditable(false);
		scrollPane_run.setViewportView(runInfor);
		
		JLabel Label_A = new JLabel("计算机内部关键信息");
		Label_A.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_run.setColumnHeaderView(Label_A);
		Label_A.setFont(new Font("宋体", Font.BOLD, 32));
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
					// 计时器更新
					int oldClock = Integer.parseInt(clockText.getText());
					oldClock++;
					clockText.setText(String.valueOf(oldClock));
					// RunInfo更新
					showRunInfo();
					// DeviceInfo更新
					showDevInfo();
					// CPUInofo更新
					showCPU();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 按钮等其他方法
	 * */
	// 运行时系统实时数据
	public void showRunInfo()
	{
		runInfor.append(Computer.RunInfo);
		Computer.RunInfo = "";
		runInfor.repaint();
	}
	// 运行时外设实时数据
	public void showDevInfo()
	{
		deviceInfo.append(Computer.DeviceInfo);
		Computer.DeviceInfo = "";
		deviceInfo.repaint();
	}
	// 设置CPU等信息显示
	public void showCPU()
	{
		pcText.setText(String.format("PC: %d", Computer.cpu.getPCIndex()));
		irText.setText(String.format("IR: %s", Computer.cpu.getIR().getInfo()));
		//stateText.setText(String.format("状态: %s", Computer.cpu.getStateString()));
		double use = (double)Computer.cpu.Time / (double)(Clock.getClock()+1);
		use = use * 100;
		useText.setText(String.format("利用率: %.2f", use)+"%");
		useBar.setValue((int)use);
		useBar.setString(String.format("%d", (int)use));
	}
}
