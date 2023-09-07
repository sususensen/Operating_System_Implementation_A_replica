package code.filemanage;

import java.util.ArrayList;

import code.Computer;
import code.barecomputer.Block;

public class i_Inode 
{
	private String fileName;		// 文件名
	private int inodeIndex;			// inode在磁盘中的物理位置
	private int inodeNumber;		// inode号
	private boolean	isLoadMemory;
	// 当inode为目录时，该数组存储目录下的inode号
	public boolean isDir = false;
	public ArrayList<Integer> next = new ArrayList<>();
	
	public i_Inode() 
	{
		inodeIndex = 0;
		inodeNumber = 0;
		isLoadMemory = false;
	}
	public i_Inode(i_Inode copy)
	{
		fileName = copy.fileName;
		inodeIndex = copy.inodeIndex;
		inodeNumber = copy.inodeNumber;
		isLoadMemory = copy.isLoadMemory;
		next = new ArrayList<>();
		for(int i=0;i<copy.next.size();++i)
		{
			int temp = copy.next.get(i);
			next.add(temp);
		}
		isDir = copy.isDir;
	}
	public i_Inode(String name, int index, int number, boolean isload)
	{
		fileName = name;
		inodeIndex = index;
		inodeNumber = number;
		isLoadMemory = isload;
	}
	public void addNext(int blockNumber)
	{
		Block block = Computer.disk.getBlockAtIndex(blockNumber);
		int num = Block.HEX_TO_INT(block.getDataAtIndex(0));
		for(int i=0;i<num;++i)
		{
			int temp = Block.HEX_TO_INT(block.getDataAtIndex(1+i));
			next.add(temp);
		}
		isDir = true;
	}
	public void setI_Inode(String name, int index, int number, boolean isload)
	{
		fileName = name;
		inodeIndex = index;
		inodeNumber = number;
		isLoadMemory = isload;
	}
	public String getFileName()
	{
		return fileName;
	}
	public int getIndex()
	{
		return inodeIndex;
	}
	public int getNumber()
	{
		return inodeNumber;
	}
	public void loadMemory()
	{
		isLoadMemory = true;
	}
	public void unloadMemory()
	{
		isLoadMemory = false;
	}
	public boolean getIsLoad()
	{
		return isLoadMemory;
	}
	public void show()
	{
		System.out.println("  -" + fileName + ", " + inodeNumber + ", " + inodeIndex + ", " + isLoadMemory);
	}
}
