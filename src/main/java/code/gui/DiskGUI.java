package code.gui;

import javax.swing.JFrame;

import code.Computer;
import code.barecomputer.Disk;
import code.filemanage.FreeBlocksGroupsLink;
import code.barecomputer.Block;
import code.barecomputer.MyLock;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JSplitPane;

public class DiskGUI extends Thread
{

	private JFrame frame;
	private JTextField diskSum;
	private JTextField diskUse;
	private JTextField fileSum;
	private JTextField fileUse;
	private JProgressBar inodeBar;
	private JProgressBar diskBar;
	private JProgressBar fileBar;
	private JButton closeButton;
	private JLabel inodeNum;
	private JTextArea inodeText;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JTextArea diskInfo;
	private JScrollPane scrollPane_3;
	private JLabel lblNewLabel_3;
	private JButton First;
	private JTextArea linkText;
	private int start = -1;

	/**
	 * Create the application.
	 */
	public DiskGUI() 
	{
		initialize();
	}
	public void setStart()
	{
		start = -1;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 640);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblNewLabel_2 = new JLabel("磁盘总大小");
		lblNewLabel_2.setFont(new Font("黑体", Font.PLAIN, 32));
		
		JLabel lblNewLabel_2_1 = new JLabel("空闲数");
		lblNewLabel_2_1.setFont(new Font("黑体", Font.PLAIN, 32));
		
		JLabel lblNewLabel_2_2 = new JLabel("文件区总大小");
		lblNewLabel_2_2.setFont(new Font("黑体", Font.PLAIN, 32));
		
		JLabel lblNewLabel_2_1_1 = new JLabel("空闲数");
		lblNewLabel_2_1_1.setFont(new Font("黑体", Font.PLAIN, 32));
		
		diskSum = new JTextField();
		diskSum.setHorizontalAlignment(SwingConstants.RIGHT);
		diskSum.setEditable(false);
		diskSum.setText("20480");
		diskSum.setFont(new Font("仿宋", Font.PLAIN, 32));
		diskSum.setColumns(10);
		
		diskUse = new JTextField();
		diskUse.setText("0");
		diskUse.setHorizontalAlignment(SwingConstants.RIGHT);
		diskUse.setFont(new Font("仿宋", Font.PLAIN, 32));
		diskUse.setEditable(false);
		diskUse.setColumns(10);
		
		fileSum = new JTextField();
		fileSum.setText("18432");
		fileSum.setHorizontalAlignment(SwingConstants.RIGHT);
		fileSum.setFont(new Font("仿宋", Font.PLAIN, 32));
		fileSum.setEditable(false);
		fileSum.setColumns(10);
		
		fileUse = new JTextField();
		fileUse.setText("0");
		fileUse.setHorizontalAlignment(SwingConstants.RIGHT);
		fileUse.setFont(new Font("仿宋", Font.PLAIN, 32));
		fileUse.setEditable(false);
		fileUse.setColumns(10);
		
		inodeBar = new JProgressBar();
		inodeBar.setToolTipText("已使用的Inode");
		inodeBar.setStringPainted(true);
		
		diskBar = new JProgressBar();
		diskBar.setToolTipText("已使用的磁盘空间");
		diskBar.setStringPainted(true);
		
		fileBar = new JProgressBar();
		fileBar.setToolTipText("已使用的磁盘空间");
		fileBar.setStringPainted(true);
		
		scrollPane_1 = new JScrollPane();
		
		scrollPane_2 = new JScrollPane();
		
		// 关闭按钮
		closeButton = new JButton("关\r\n闭");
		closeButton.setFont(new Font("楷体", Font.BOLD, 32));
		closeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				unshowWindow();
			}
		});
		
		scrollPane_3 = new JScrollPane();
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(diskBar, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(diskUse, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(diskSum, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(5)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 505, GroupLayout.PREFERRED_SIZE))
								.addComponent(inodeBar, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(16)
											.addComponent(closeButton))))
								.addComponent(scrollPane_2, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 508, GroupLayout.PREFERRED_SIZE))
							.addGap(88))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(fileBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel_2_1_1, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(fileUse))
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel_2_2, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(fileSum, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(inodeBar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(49)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_2)
									.addGap(18)
									.addComponent(lblNewLabel_2_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(diskSum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(diskUse, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(diskBar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2_2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(fileSum, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2_1_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(fileUse, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(fileBar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(closeButton, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
									.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(69))
								.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))))
					.addGap(20))
		);
		
		lblNewLabel_3 = new JLabel("成组区块链");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("黑体", Font.PLAIN, 32));
		scrollPane_3.setColumnHeaderView(lblNewLabel_3);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		scrollPane_3.setRowHeaderView(splitPane);
		
		// next按钮
		JButton Next = new JButton("下一组");
		Next.setFont(new Font("SimSun", Font.PLAIN, 12));
		Next.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				if(start==-1)
					return;
				if(start== FreeBlocksGroupsLink.LAST_GROUP_FLAG)
					return;
				linkText.setText("");
				String temp = Computer.disk.getBlockAtIndex(start).getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK);
				int num = Block.HEX_TO_INT(temp);
				linkText.append("Num:\n");
				linkText.append("\t"+num+"\n");
				linkText.append("Free:\n");
				for(int i=0;i<num;++i)
				{
					temp = Computer.disk.getBlockAtIndex(start).getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK+1+i);
					int number = Block.HEX_TO_INT(temp);
					linkText.append("\t"+number+"\n");
				}
				linkText.repaint();
				temp = Computer.disk.getBlockAtIndex(start).getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK+1);
				start = Block.HEX_TO_INT(temp);
			}
		});
		splitPane.setLeftComponent(Next);
		
		// first 按钮
		First = new JButton("第一组");
		First.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				linkText.setText("");
				// TODO Auto-generated method stub
				start = Computer.memory.superBlock.freeBlocksGroupsLink.getTopNumber();
				linkText.append("Num:\n");
				int num = Computer.memory.superBlock.freeBlocksGroupsLink.getNum();
				linkText.append("\t"+num+"\n");
				linkText.append("Free:\n");
				int[] temp = Computer.memory.superBlock.freeBlocksGroupsLink.getAllFreeBlocksInGroup();
				for(int i=0;i<num;++i)
					linkText.append("\t"+temp[i]+"\n");
				linkText.repaint();
			}
		});
		splitPane.setRightComponent(First);
		
		linkText = new JTextArea();
		linkText.setFont(new Font("仿宋", Font.PLAIN, 18));
		scrollPane_3.setViewportView(linkText);
		
		diskInfo = new JTextArea();
		diskInfo.setText("引导块：0\r\n超级块：1\r\nInode块：2~119\r\n系统表：120~127\r\n交换区：128~511\r\n作业区：512~575\r\n代码区：576~1023\r\n预留区：1024~2047\r\n文件区：2048~20478\r\n柱面数：10\r\n磁道数：32\r\n扇区数：64");
		diskInfo.setFont(new Font("仿宋", Font.PLAIN, 18));
		scrollPane_2.setViewportView(diskInfo);
		
		JLabel lblNewLabel_1 = new JLabel("磁盘分区基本信息");
		scrollPane_2.setColumnHeaderView(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("黑体", Font.PLAIN, 32));
		
		JLabel lblNewLabel = new JLabel("磁盘中的Inode使用情况");
		lblNewLabel.setFont(new Font("黑体", Font.PLAIN, 32));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		inodeText = new JTextArea();
		inodeText.setFont(new Font("新宋体", Font.PLAIN, 24));
		scrollPane.setViewportView(inodeText);
		
		inodeNum = new JLabel("944");
		inodeNum.setFont(new Font("宋体", Font.PLAIN, 24));
		scrollPane.setRowHeaderView(inodeNum);
		frame.getContentPane().setLayout(groupLayout);
		
	}
	public void showWindow()
	{
		frame.setVisible(true);
	}
	public void unshowWindow()
	{
		frame.setVisible(false);
		setStart();
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
					// 更新Inode
					flashInode();
					// 更新磁盘和文件区总使用量
					flashDisk();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 其他
	 * */
	public void flashInode()
	{
		int num = Computer.memory.superBlock.freeInodeInDisk;
		inodeNum.setText(num + "");
		int max = 118*8;
		int use = ((max-num)*100)/max;
		inodeBar.setValue(use);
		inodeBar.setString(use+"%");
		inodeText.setText("");
		for(int i=0;i<118;++i)
		{
			String temp = "";
			temp += String.format("[%3d] ", i);
			for(int j=0;j<8;++j)
			{
				if(Computer.memory.superBlock.freeInodeInDiskMap[i][j])
					temp += "■ ";
				else 
					temp += "□ ";
			}
			temp += "\n";
			inodeText.append(temp);
			inodeText.repaint();
		}
	}
	public void flashDisk()
	{
		int numDisk = 1024 + Disk.FREE_NUM;
		int numFile = Disk.FREE_NUM;
		int all = 20480;
		int fileAll = 18432;
		diskUse.setText(numDisk + "");
		fileUse.setText(numFile + "");
		double use_disk = (double)((all-numDisk)*100) / (double)all;
		double use_file = (double)((fileAll-numFile)*100) / (double)fileAll;
		diskBar.setValue((int)use_disk);
		diskBar.setString(String.format("%.2f", use_disk) + "%");
		fileBar.setValue((int)use_file);
		fileBar.setString(String.format("%.2f", use_file) + "%");
	}
}
