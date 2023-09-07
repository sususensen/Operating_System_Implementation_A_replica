package code.gui;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import code.Computer;
import code.barecomputer.Clock;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JButton;

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
		frame.setBounds(50, 50, 1024, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		name = new JTextField();
		name.setText("Student");
		name.setFont(new Font("宋体", Font.PLAIN, 32));
		name.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("用户名");
		lblNewLabel_1.setFont(new Font("黑体", Font.PLAIN, 32));
		
		JLabel lblNewLabel_1_1 = new JLabel("密  码");
		lblNewLabel_1_1.setFont(new Font("黑体", Font.PLAIN, 32));
		
		password = new JPasswordField();
		password.setFont(new Font("宋体", Font.PLAIN, 32));
		
		// 确定
		yes = new JButton("确  定");
		yes.setEnabled(false);
		yes.setFont(new Font("楷体", Font.BOLD, 32));
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
		
		JLabel lblNewLabel_2 = new JLabel("马树凡");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("楷体", Font.PLAIN, 99));
		
		JLabel lblNewLabel_3 = new JLabel("默认用户名为任意字符");
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 18));
		
		JLabel lblNewLabel_3_1 = new JLabel("默认密码为123456");
		lblNewLabel_3_1.setFont(new Font("宋体", Font.PLAIN, 18));
		
		JLabel lblNewLabel_3_1_1 = new JLabel("操作系统课程设计");
		lblNewLabel_3_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		
		JLabel lblNewLabel_3_1_1_1 = new JLabel("2021.3.31");
		lblNewLabel_3_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3_1_1_1.setFont(new Font("宋体", Font.PLAIN, 18));
		
		JButton rebuild = new JButton("重装系统");
		rebuild.setFont(new Font("楷体", Font.BOLD, 32));
		rebuild.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				Computer.rebuild(false);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
					.addGap(72)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(no, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(password)
									.addComponent(name, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)))
							.addComponent(lblNewLabel_3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_3_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
							.addGroup(Alignment.LEADING, groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3_1_1_1, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
							.addComponent(yes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(rebuild, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(121, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(65)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_3_1_1_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addGap(24)
									.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(yes)
							.addGap(18)
							.addComponent(no, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rebuild, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(76, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel = new JLabel("开机信息");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("黑体", Font.PLAIN, 32));
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("仿宋", Font.PLAIN, 18));
		scrollPane.setViewportView(textArea);
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
