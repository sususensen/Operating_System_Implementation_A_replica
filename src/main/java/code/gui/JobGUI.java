package code.gui;

import javax.swing.JFrame;

import code.Computer;
import code.barecomputer.MyLock;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class JobGUI extends Thread
{

	private JFrame frame;
	private JTextArea jobText;
	private JProgressBar numBar;

	/**
	 * Create the application.
	 */
	public JobGUI() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 762, 640);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		numBar = new JProgressBar();
		numBar.setToolTipText("作业池使用量");
		numBar.setStringPainted(true);
		numBar.setOrientation(SwingConstants.VERTICAL);
		
		JButton closeButton = new JButton("关闭");
		closeButton.setFont(new Font("楷体", Font.BOLD, 18));
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
					.addGap(28)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(numBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(closeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(230, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(33, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 539, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(numBar, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(closeButton)))
					.addGap(31))
		);
		
		JLabel lblNewLabel = new JLabel("作业池内容");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("黑体", Font.PLAIN, 32));
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		jobText = new JTextArea();
		scrollPane.setViewportView(jobText);
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
					flashJob();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * other
	 * */
	public void flashJob()
	{
		int num = Computer.memory.jcbPool.number;
		int use = (num*100)/64;
		
		numBar.setValue(use);
		numBar.setString(use+"%");
		numBar.setToolTipText("已使用"+num);
		
		jobText.setText("");
		for(int i=0;i<num;++i)
		{
			jobText.append(String.format("(%2d)\n", i));
			jobText.append(Computer.memory.jcbPool.pool.get(i).getString());
		}
		jobText.repaint();
	}
}
