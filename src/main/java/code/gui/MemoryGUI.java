package code.gui;

import javax.swing.JFrame;

import code.Computer;
import code.barecomputer.Memory;
import code.barecomputer.MyLock;
import net.miginfocom.swing.MigLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JPanel;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JProgressBar;

public class MemoryGUI extends Thread
{
	public JFrame frame;
	public JButton[] blocks;
	public JButton[] inodes;
	private JTextField freeBlockNumText;
	private JTextField freeInodeNumText;
	private JProgressBar blockBar;
	private JProgressBar inodeBar;

	/**
	 * Create the application.
	 */
	public MemoryGUI() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 780, 640);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		blocks = new JButton[64];
		for(int i=0;i<64;++i)
		{
			blocks[i] = new JButton();
			blocks[i].setText(String.format(" %2d ", i));
			blocks[i].setFont(new Font("黑体", Font.PLAIN, 20));
			blocks[i].setHorizontalAlignment(SwingConstants.CENTER);
			blocks[i].setOpaque(true);
			blocks[i].setBackground(Color.WHITE);
			blocks[i].setEnabled(false);
		}
		inodes = new JButton[16];
		for(int i=0;i<16;++i)
		{
			inodes[i] = new JButton();
			inodes[i].setText(String.format(" %2d ", i));
			inodes[i].setFont(new Font("黑体", Font.PLAIN, 20));
			inodes[i].setHorizontalAlignment(SwingConstants.CENTER);
			inodes[i].setOpaque(true);
			inodes[i].setBackground(Color.WHITE);
			inodes[i].setEnabled(false);
		}
		
		JPanel memoryBlockPanel = new JPanel();
		
		// 关闭按钮
		JButton closeButton = new JButton("关闭");
		closeButton.setFont(new Font("楷体", Font.BOLD, 24));
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				unshowWindow();
			}
		});
		
		JLabel LabelA = new JLabel("内存物理块使用情况");
		LabelA.setFont(new Font("黑体", Font.PLAIN, 32));
		
		freeBlockNumText = new JTextField();
		freeBlockNumText.setEditable(false);
		freeBlockNumText.setFont(new Font("仿宋", Font.PLAIN, 32));
		freeBlockNumText.setColumns(10);
		freeBlockNumText.setText("Free: 64");
		
		JPanel memoryInodePanel = new JPanel();
		
		JLabel LabelB = new JLabel("内存Inode使用情况");
		LabelB.setFont(new Font("黑体", Font.PLAIN, 32));
		
		freeInodeNumText = new JTextField();
		freeInodeNumText.setText("Free: 16");
		freeInodeNumText.setFont(new Font("仿宋", Font.PLAIN, 32));
		freeInodeNumText.setEditable(false);
		freeInodeNumText.setColumns(10);
		
		blockBar = new JProgressBar();
		blockBar.setToolTipText("已使用的内存");
		blockBar.setStringPainted(true);
		blockBar.setOrientation(SwingConstants.VERTICAL);
		
		inodeBar = new JProgressBar();
		inodeBar.setToolTipText("已使用的Inode");
		inodeBar.setStringPainted(true);
		inodeBar.setOrientation(SwingConstants.VERTICAL);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(LabelA, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(freeBlockNumText, 220, 220, 220))
							.addComponent(memoryBlockPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(memoryInodePanel, GroupLayout.PREFERRED_SIZE, 602, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(LabelB, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(freeInodeNumText, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(blockBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(53)
							.addComponent(inodeBar, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addComponent(closeButton, 0, 0, Short.MAX_VALUE))
					.addGap(98))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(freeBlockNumText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(closeButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addComponent(LabelA, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(memoryBlockPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(LabelB, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(freeInodeNumText, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
									.addComponent(memoryInodePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(blockBar, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
							.addGap(21))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(inodeBar, GroupLayout.PREFERRED_SIZE, 519, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		memoryInodePanel.setLayout(new MigLayout("wrap 8", "[70!][70!][70!][70!][70!][70!][70!][70!]", "[40!][40!]"));
		memoryBlockPanel.setLayout(new MigLayout("wrap 8", "[70!][70!][70!][70!][70!][70!][70!][70!]", "[40!][40!][40!][40!][40!][40!][40!][40!]"));
		for(int i=0;i<8;++i)
		{
			for(int j=0;j<8;++j)
			{
				int index = i*8 + j;
				memoryBlockPanel.add(blocks[index], "cell "+j+" "+i);
			}
		}
		for(int i=0;i<2;++i)
		{
			for(int j=0;j<8;++j)
			{
				int index = i*8 + j;
				memoryInodePanel.add(inodes[index], "cell "+j+" "+i);
			}
		}
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
					// 更新内存块使用情况
					flashBlock();
					// 更新内存Inode使用情况
					flashInode();
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 其他函数
	 * */
	public void flashBlock()
	{
		int num = Computer.memory.superBlock.freeBlockInMemory;
		freeBlockNumText.setText(String.format("Free: %d", num));
		for(int i = 0; i< Memory.MAX_BLOCK_NUM; ++i)
		{
			if(Computer.memory.superBlock.freeBlockInMemoryMap[i])
				blocks[i].setBackground(Color.GRAY);
			else
				blocks[i].setBackground(Color.WHITE);
		}
		int use = ((64-num)*100)/64;
		blockBar.setValue(use);
		blockBar.setString(use+"%");
	}
	public void flashInode()
	{
		int num = Computer.memory.superBlock.freeInodeInMemory;
		freeInodeNumText.setText(String.format("Free: %d", num));
		for(int i=0;i<2;++i)
		{
			for(int j=0;j<8;++j)
			{
				int index = i*8 + j;
				if(Computer.memory.superBlock.freeInodeInMemoryMap[i][j])
					inodes[index].setBackground(Color.GRAY);
				else
					inodes[index].setBackground(Color.WHITE);
			}
		}
		int use = ((16-num)*100)/16;
		inodeBar.setValue(use);
		inodeBar.setString(use+"%");
	}
}
