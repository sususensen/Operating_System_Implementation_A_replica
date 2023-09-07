package code.gui;

import javax.swing.JFrame;

import code.Computer;
import code.barecomputer.MyLock;
import code.processmanage.PCB;
import net.miginfocom.swing.MigLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JPanel;

import javax.swing.JButton;

public class ProcessGUI extends Thread
{

	private JFrame frame;
	public JTextArea deadText;
	private JTextArea blockText;
	private JTextArea runText;
	private JTextArea readyText;
	private JTextArea newText;
	private JTextArea codeText ;
	private JTextArea pageText;
	
	private JProgressBar numBar;

	/**
	 * Create the application.
	 */
	public ProcessGUI()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 1300, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JScrollPane scrollPane_3 = new JScrollPane();
		
		JScrollPane scrollPane_4 = new JScrollPane();
		
		numBar = new JProgressBar();
		numBar.setStringPainted(true);
		numBar.setOrientation(SwingConstants.VERTICAL);
		
		JPanel panel = new JPanel();
		
		JScrollPane scrollPane_5 = new JScrollPane();
		
		JScrollPane scrollPane_6 = new JScrollPane();
		
		JButton closeButton = new JButton("\u5173\u95ED");
		closeButton.setFont(new Font("����", Font.PLAIN, 24));
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				unshowWindow();
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(39)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
					.addGap(31)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(numBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(closeButton, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
							.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE)))
					.addGap(27))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 333, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(closeButton, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
								.addComponent(scrollPane_6, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
						.addComponent(numBar, GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE))
							.addGap(34)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPane_4, GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE))
					.addGap(39))
		);
		
		JLabel lblNewLabel_5_1 = new JLabel("\u9875\u8868");
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5_1.setFont(new Font("����", Font.PLAIN, 24));
		scrollPane_6.setColumnHeaderView(lblNewLabel_5_1);
		
		pageText = new JTextArea();
		pageText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_6.setViewportView(pageText);
		
		JLabel lblNewLabel_5 = new JLabel("\u4EE3\u7801\u6BB5");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setFont(new Font("����", Font.PLAIN, 24));
		scrollPane_5.setColumnHeaderView(lblNewLabel_5);
		
		codeText = new JTextArea();
		codeText.setFont(new Font("����", Font.PLAIN, 16));
		scrollPane_5.setViewportView(codeText);
		panel.setLayout(new MigLayout("wrap 6", "[60!][60!][60!][60!][60!][60!]", "[30!][30!]"));
		
		JButton buttun1 = new JButton(" 0 ");
		buttun1.setFont(new Font("����", Font.PLAIN, 18));
		buttun1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(0);
				showPageTable(0);
			}
		});
		panel.add(buttun1, "cell 0 0");
		
		JButton buttun2 = new JButton(" 1 ");
		buttun2.setFont(new Font("����", Font.PLAIN, 18));
		buttun2.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(1);
				showPageTable(1);
			}
		});
		panel.add(buttun2, "cell 1 0");
		
		JButton buttun3 = new JButton(" 2 ");
		buttun3.setFont(new Font("����", Font.PLAIN, 18));
		buttun3.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(2);
				showPageTable(2);
			}
		});
		panel.add(buttun3, "cell 2 0");
		
		JButton buttun4 = new JButton(" 3 ");
		buttun4.setFont(new Font("����", Font.PLAIN, 18));
		buttun4.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(3);
				showPageTable(3);
			}
		});
		panel.add(buttun4, "cell 3 0");
		
		JButton buttun5 = new JButton(" 4 ");
		buttun5.setFont(new Font("����", Font.PLAIN, 18));
		buttun5.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(4);
				showPageTable(4);
			}
		});
		panel.add(buttun5, "cell 4 0");
		
		JButton buttun6 = new JButton(" 5 ");
		buttun6.setFont(new Font("����", Font.PLAIN, 18));
		buttun6.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(5);
				showPageTable(5);
			}
		});
		panel.add(buttun6, "cell 5 0");
		
		JButton buttun7 = new JButton(" 6 ");
		buttun7.setFont(new Font("����", Font.PLAIN, 18));
		buttun7.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(6);
				showPageTable(6);
			}
		});
		panel.add(buttun7, "cell 0 1");
		
		JButton buttun8 = new JButton(" 7 ");
		buttun8.setFont(new Font("����", Font.PLAIN, 18));
		buttun8.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(7);
				showPageTable(7);
			}
		});
		panel.add(buttun8, "cell 1 1");
		
		JButton buttun9 = new JButton(" 8 ");
		buttun9.setFont(new Font("����", Font.PLAIN, 18));
		buttun9.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(8);
				showPageTable(8);
			}
		});
		panel.add(buttun9, "cell 2 1");
		
		JButton buttun10 = new JButton(" 9 ");
		buttun10.setFont(new Font("����", Font.PLAIN, 18));
		buttun10.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(9);
				showPageTable(9);
			}
		});
		panel.add(buttun10, "cell 3 1");
		
		JButton buttun11 = new JButton(" 10");
		buttun11.setFont(new Font("����", Font.PLAIN, 18));
		buttun11.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(10);
				showPageTable(10);
			}
		});
		panel.add(buttun11, "cell 4 1");
		
		JButton buttun12 = new JButton(" 11");
		buttun12.setFont(new Font("����", Font.PLAIN, 18));
		buttun12.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				showCode(11);
				showPageTable(11);
			}
		});
		panel.add(buttun12, "cell 5 1");
		
		JLabel lblNewLabel_4 = new JLabel("\u5DF2\u5B8C\u6210");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_4.setColumnHeaderView(lblNewLabel_4);
		
		deadText = new JTextArea();
		deadText.setForeground(Color.BLACK);
		deadText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_4.setViewportView(deadText);
		
		JLabel lblNewLabel_3 = new JLabel("\u963B\u585E\u6001");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_3.setColumnHeaderView(lblNewLabel_3);
		
		blockText = new JTextArea();
		blockText.setForeground(Color.BLACK);
		blockText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_3.setViewportView(blockText);
		
		JLabel lblNewLabel_2 = new JLabel("\u8FD0\u884C\u6001");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_2.setColumnHeaderView(lblNewLabel_2);
		
		runText = new JTextArea();
		runText.setForeground(Color.BLACK);
		runText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_2.setViewportView(runText);
		
		JLabel lblNewLabel_1 = new JLabel("\u5C31\u7EEA\u6001");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_1.setColumnHeaderView(lblNewLabel_1);
		
		readyText = new JTextArea();
		readyText.setForeground(Color.BLACK);
		readyText.setFont(new Font("����", Font.PLAIN, 14));
		scrollPane_1.setViewportView(readyText);
		
		JLabel lblNewLabel = new JLabel("\u65B0\u5EFA\u6001");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		newText = new JTextArea();
		newText.setFont(new Font("����", Font.PLAIN, 14));
		newText.setForeground(new Color(0, 0, 0));
		scrollPane.setViewportView(newText);
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
					flash();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * other
	 * */
	public void flash()
	{
		// Bar
		int num = Computer.memory.pcbPool.number;
		int use = (num*100)/12;
		numBar.setValue(use);
		numBar.setString(use+"%");
		numBar.setToolTipText("��ʹ��"+num);
		
		// ��̬��ʾ
		newText.setText("");
		readyText.setText("");
		runText.setText("");
		blockText.setText("");
		int newNum = 0;
		int readyNum = 0;
		int runNum = 0;
		int blockNum = 0;
		for(int i=0;i<12;++i)
		{
			if(Computer.memory.pcbPool.poolMap[i])
			{
				switch(Computer.memory.pcbPool.pool[i].getState())
				{
					case PCB.NEW:
					{
						newText.append(newNum+":\n");
						newNum++;
						newText.append(Computer.memory.pcbPool.pool[i].getString());
						break;
					}
					case PCB.READY:
					{
						readyText.append(readyNum+":\n");
						readyNum++;
						readyText.append(Computer.memory.pcbPool.pool[i].getString());
						break;
					}
					case PCB.RUNNING:
					{
						runText.append(runNum+":\n");
						runNum++;
						runText.append(Computer.memory.pcbPool.pool[i].getString());
						runText.append("\n");
						break;
					}
					case PCB.BLOCK:
					{
						blockText.append(blockNum+":\n");
						blockNum++;
						blockText.append(Computer.memory.pcbPool.pool[i].getBlockString());
						blockText.append("\n");
						break;
					}
					default:
						break;
				}
			}
		}
		newText.repaint();
		readyText.repaint();
		runText.repaint();
		blockText.repaint();
		deadText.repaint();
	}
	public void showCode(int index)
	{
		codeText.setText("");
		if(Computer.memory.pcbPool.poolMap[index])
		{
			codeText.append("���̺�: "+Computer.memory.pcbPool.pool[index].getProcessNumber()+"\n");
			codeText.append("������: "+Computer.memory.pcbPool.pool[index].getProcessName()+"\n");
			codeText.append("������: "+Computer.memory.pcbPool.pool[index].getCodeLength()+"\n");
			codeText.append("�����:\n");
			for(int i=0;i<Computer.memory.pcbPool.pool[index].codes.length;++i)
			{
				codeText.append(String.format("%4d: ", i));
				codeText.append(Computer.memory.pcbPool.pool[index].codes[i].getInfo()+"\n");
			}
			codeText.repaint();
		}
		else
		{
			codeText.setText("�ù̶�����λ����û�е������");
		}
	}
	public void showPageTable(int index)
	{
		pageText.setText("");
		if(Computer.memory.pcbPool.poolMap[index])
		{
			pageText.append("���̺�: "+Computer.memory.pcbPool.pool[index].getProcessNumber()+"\n");
			pageText.append("������: "+Computer.memory.pcbPool.pool[index].getProcessName()+"\n");
			pageText.append("ҳ��:\n");
			pageText.append(Computer.memory.pcbPool.pool[index].pageTable.getString());
			pageText.repaint();
		}
		else
		{
			pageText.setText("�ù̶�����λ����û�е������");
		}
	}
}
