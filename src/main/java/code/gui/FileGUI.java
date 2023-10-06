package code.gui;

import javax.swing.JFrame;

import code.barecomputer.MyLock;
import code.Computer;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.SwingConstants;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.tree.TreePath;

public class FileGUI extends Thread
{

	private JFrame frame;
	private JTextArea i_InodeText;
	private JList<Integer> inodeList;
	private DefaultListModel<Integer> listModel = new DefaultListModel<>();
	private JTextArea openFileText;
	private JTextArea processOpenText;

	/**
	 * Create the application.
	 */
	public FileGUI()
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
		
		// ѡ��ť
		JButton choose = new JButton("ѡ\n��");
		choose.setFont(new Font("����", Font.BOLD, 24));
		choose.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(inodeList.isSelectionEmpty())
				{
					JOptionPane.showMessageDialog(null, "Error", "δѡ���κ���", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int select = inodeList.getSelectedValue();
				i_InodeText.setText(Computer.disk.inodes.get(select).getString(select));
			}
		});
		
		JScrollPane scrollPane_3 = new JScrollPane();
		
		JScrollPane scrollPane_4 = new JScrollPane();
		
		JButton close = new JButton("�رս���");
		close.setFont(new Font("����", Font.BOLD, 24));
		close.addActionListener(new ActionListener() 
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
					.addGap(50)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(choose, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(close, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_4))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 841, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(47, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(choose, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addComponent(close, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
							.addGap(22))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		
		JLabel lblNewLabel_3 = new JLabel("�������̵��ļ��򿪱�");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_4.setColumnHeaderView(lblNewLabel_3);
		
		processOpenText = new JTextArea();
		processOpenText.setEditable(false);
		processOpenText.setTabSize(4);
		scrollPane_4.setViewportView(processOpenText);
		
		JLabel lblNewLabel_2 = new JLabel("ϵͳ�ļ��򿪱�");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane_3.setColumnHeaderView(lblNewLabel_2);
		
		openFileText = new JTextArea();
		openFileText.setEditable(false);
		openFileText.setTabSize(4);
		openFileText.setFont(new Font("����", Font.PLAIN, 18));
		scrollPane_3.setViewportView(openFileText);
		
		i_InodeText = new JTextArea();
		i_InodeText.setFont(new Font("����", Font.PLAIN, 16));
		i_InodeText.setEditable(false);
		scrollPane_2.setViewportView(i_InodeText);
		
		JLabel lblNewLabel_1 = new JLabel("Inodes");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 24));
		scrollPane_1.setColumnHeaderView(lblNewLabel_1);
		
		inodeList = new JList<>();
		inodeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inodeList.setModel(listModel);
		inodeList.setFont(new Font("����", Font.PLAIN, 16));
		scrollPane_1.setViewportView(inodeList);
		
		JLabel lblNewLabel = new JLabel("ϵͳ�ļ�Ԥ��");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 32));
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("����ϵͳ�γ����") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("����ϵͳ���豨��");
						node_1.add(new DefaultMutableTreeNode("19218101.TXT"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("�ⲿ�豸��������");
						node_1.add(new DefaultMutableTreeNode("Device_0.EXE"));
						node_1.add(new DefaultMutableTreeNode("Device_1.EXE"));
						node_1.add(new DefaultMutableTreeNode("Device_2.EXE"));
						node_1.add(new DefaultMutableTreeNode("Device_3.EXE"));
						node_1.add(new DefaultMutableTreeNode("Device_4.EXE"));
						node_1.add(new DefaultMutableTreeNode("Device_5.EXE"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("����ϵͳ�����ļ�");
						node_1.add(new DefaultMutableTreeNode("OStest_0.TXT"));
						node_1.add(new DefaultMutableTreeNode("OStest_1.TXT"));
					add(node_1);
				}
			}
		));
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					TreePath selectedPath = tree.getSelectionPath();
					assert selectedPath != null;
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
					if (!selectedNode.isLeaf()) {
						return; // ֻ����Ҷ�ӽڵ��˫���¼�
					}
					String fileName = selectedNode.toString();
					String fileExtension = getFileExtension(fileName);
					if (fileExtension.equalsIgnoreCase("txt") || fileExtension.equalsIgnoreCase("exe")) {
						// ִ�д��ļ����߼�
						File selectedFile = new File(fileName);
						try {
							openFile(selectedFile);
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					}
				}
			}
		});

		scrollPane.setViewportView(tree);
		frame.getContentPane().setLayout(groupLayout);
	}
	private static void openFile(File file) throws IOException {
		StringBuilder fileContent = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent.append(line).append(System.lineSeparator());
			}
		}

		// ����һ����ʱ�ļ������޸�����
		File tempFile = File.createTempFile("temp", null);
		tempFile.deleteOnExit();

		// ��ԭ�ļ�����д����ʱ�ļ�
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
			writer.write(fileContent.toString());
		}

		// ����ʱ�ļ����༭
		Desktop.getDesktop().edit(tempFile);

		// ����ʱ�ļ������ݱ����ԭ�ļ�
		Files.copy(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

	}
	private static String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
			return fileName.substring(dotIndex + 1);
		}
		return "";
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
		flashi_Inode();
		try {
			while(true)
			{
				synchronized (MyLock.GUILock)
				{
					MyLock.GUILock.wait();
					flashOpenFileTable();
					flashProcessOpenTable();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * other
	 * */
	public void flashOpenFileTable()
	{
		int num = Computer.memory.openFileTable.Number;
		openFileText.setText("");
		openFileText.append("ϵͳ���ļ�����: "+num+"\n");
		if(num==0)
		{
			openFileText.repaint();
			return;
		}
		openFileText.append(Computer.memory.openFileTable.getString());
		openFileText.repaint();
	}
	public void flashProcessOpenTable()
	{
		int num = Computer.memory.pcbPool.number;
		processOpenText.setText("");
		processOpenText.append("������: "+num+"\n");
		int x=0;
		for(int i=0;i<num;++i)
		{
			if(Computer.memory.pcbPool.poolMap[i])
			{
				int pID = Computer.memory.pcbPool.pool[i].getProcessNumber();
				String Name = Computer.memory.pcbPool.pool[i].getProcessName();
				processOpenText.append("("+x+")\n");
				processOpenText.append(String.format("\t���̺�: %d\t������: %s\n", pID, Name));
				processOpenText.append(Computer.memory.pcbPool.pool[i].processFileTable.getString());
				x++;
			}
		}
		processOpenText.repaint();
	}
	// ����i_InodeTable
	public void flashi_Inode()
	{
		int num = Computer.memory.i_inodeTable.Number;
		for(int i=0;i<num;++i)
		{
			int temp = Computer.memory.i_inodeTable.i_Inodes.get(i).getNumber();
			listModel.addElement(temp);
		}
		inodeList.repaint();
	}
}
