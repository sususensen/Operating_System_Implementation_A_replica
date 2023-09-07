package code.gui;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import code.Computer;
import code.barecomputer.Clock;
import code.barecomputer.RandomGenerator;
import code.jobmanage.JCB;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class createGUI
{

	private JFrame frame;
	private JTextField textField;
	private JButton randomB;
	private JTextArea info;

	/**
	 * Create the application.
	 */
	public createGUI() 
	{
		//initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 587, 498);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("\u4F5C\u4E1A\u7C7B\u578B");
		lblNewLabel.setFont(new Font("黑体", Font.PLAIN, 24));
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.addItem("寻址计算高频");
		comboBox.addItem("文件读写高频");
		comboBox.addItem("长时阻塞高频");
		comboBox.setSelectedIndex(0);
		comboBox.setFont(new Font("仿宋", Font.PLAIN, 24));
		
		JLabel lblNewLabel_1 = new JLabel("\u4F18\u5148\u7EA7");
		lblNewLabel_1.setFont(new Font("黑体", Font.PLAIN, 24));
		
		SpinnerModel model = new SpinnerNumberModel(5, 0, 9, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setFont(new Font("仿宋", Font.PLAIN, 24));
		
		JLabel lblNewLabel_1_1 = new JLabel("\u4EE3\u7801\u6570\u76EE");
		lblNewLabel_1_1.setFont(new Font("黑体", Font.PLAIN, 24));
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setEditable(false);
		textField.setFont(new Font("仿宋", Font.PLAIN, 24));
		textField.setColumns(10);
		
		JSlider slider = new JSlider(5, 125, 30);
		slider.setMajorTickSpacing(30);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				// TODO Auto-generated method stub
				textField.setText(slider.getValue()+"");
			}
		});
		
		// 创建
		JButton creat = new JButton("\u521B  \u5EFA");
		creat.setFont(new Font("楷体", Font.PLAIN, 24));
		creat.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				int type = comboBox.getSelectedIndex();
				if(type == -1)
				{
					JOptionPane.showMessageDialog(null, "Error", "请选择指令类型", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int priority = (int) spinner.getValue();
				if(priority>9 || priority<0)
				{
					JOptionPane.showMessageDialog(null, "Error", "优先级不合法", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int codelength = slider.getValue();
				if(codelength>150 || codelength<5)
				{
					JOptionPane.showMessageDialog(null, "Error", "代码数量不合法", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(Computer.memory.jcbPool.isFull())
				{
					JOptionPane.showMessageDialog(null, "Error", "作业池满，拒绝创建", JOptionPane.ERROR_MESSAGE);
					System.out.println(Clock.getClock() + ": 作业池满，拒绝创建一份作业");
					Computer.RunInfo += Clock.getClock() + ": 作业池满，拒绝创建一份作业\n";
					return;
				}
				JCB temp = new JCB();
				info.setText("");
				temp.randMake(type, priority, codelength, info);
				Computer.memory.jcbPool.pool.add(temp);
				Computer.memory.jcbPool.number++;
			}
		});
		
		// 随机创建
		randomB = new JButton("\u968F\u673A\u521B\u5EFA");
		randomB.setFont(new Font("楷体", Font.PLAIN, 24));
		randomB.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				int type = RandomGenerator.getRandom(0, 2);
				int priority = RandomGenerator.getRandom(0, 9);
				int codelength = RandomGenerator.getRandom(5, 100);
				if(Computer.memory.jcbPool.isFull())
				{
					JOptionPane.showMessageDialog(null, "Error", "作业池满，拒绝创建", JOptionPane.ERROR_MESSAGE);
					System.out.println(Clock.getClock() + ": 作业池满，拒绝创建一份作业");
					Computer.RunInfo += Clock.getClock() + ": 作业池满，拒绝创建一份作业\n";
					return;
				}
				JCB temp = new JCB();
				info.setText("");
				temp.randMake(type, priority, codelength, info);
				Computer.memory.jcbPool.pool.add(temp);
				Computer.memory.jcbPool.number++;
			}
		});
		
		// 关闭
		JButton close = new JButton("\u5173  \u95ED");
		close.setFont(new Font("楷体", Font.PLAIN, 24));
		close.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(scrollPane, Alignment.LEADING)
						.addComponent(slider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(comboBox, 0, 219, Short.MAX_VALUE))))
							.addGap(40)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(creat, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(randomB, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(close, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(creat))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addComponent(randomB, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addComponent(close, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_2 = new JLabel("\u6700\u8FD1\u521B\u5EFA\u7684\u4F5C\u4E1A");
		lblNewLabel_2.setFont(new Font("黑体", Font.PLAIN, 24));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel_2);
		
		info = new JTextArea();
		info.setFont(new Font("仿宋", Font.PLAIN, 18));
		scrollPane.setViewportView(info);
		frame.getContentPane().setLayout(groupLayout);
	}
}
