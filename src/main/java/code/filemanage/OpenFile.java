package code.filemanage;

import code.barecomputer.SuperBlock;

public class OpenFile 
{
	private int inodeNumber;	// inode号
	private int processNumber;	// 使用该文件的进程数
	private int inodePtr;		// inode指针
	private boolean isModify;	// 表示该文件是否修改
	
	public String getString()
	{
		String out = "";
		int[] location = SuperBlock.Inode_IndexToLocation(inodePtr);
		out += String.format("\tInode号: %d\tInode在内存Inode分区的位置: %d(%d, %d)\t内存引用数: %d\t是否修改: ", inodeNumber, inodePtr, location[0], location[1], processNumber);
		out += isModify + "\n";
		return out;
	}
	public OpenFile() 
	{
		inodeNumber = 0;
		processNumber = 0;
		inodePtr = 0;
		isModify = false;
	}
	public OpenFile(OpenFile copy)
	{
		inodeNumber = copy.inodeNumber;
		processNumber = copy.processNumber;
		inodePtr = copy.inodePtr;
		isModify = copy.isModify;
	}
	public OpenFile(int number, int pNumber, int ptr)
	{
		inodeNumber = number;
		processNumber = pNumber;
		inodePtr = ptr;
		isModify = false;
	}
	public void setOpenFile(int number, int pNumber, int ptr)
	{
		inodeNumber = number;
		processNumber = pNumber;
		inodePtr = ptr;
		isModify = false;
	}
	public void incProcessNumber()
	{
		processNumber++;
	}
	public void decProcessNumber()
	{
		processNumber--;
	}
	public boolean isOne()
	{
		if(processNumber<=1)
			return true;
		else
			return false;
	}
	public int getInodeNumber()
	{
		return inodeNumber;
	}
	public int getInodePtr()
	{
		return inodePtr;
	}
	public int getProcessNumber()
	{
		return processNumber;
	}
	public boolean getIsModify()
	{
		return isModify;
	}
	public void doModify()
	{
		isModify = true;
	}
}
