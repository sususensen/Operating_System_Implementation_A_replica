package code.filemanage;

import code.barecomputer.SuperBlock;

public class OpenFile 
{
	private int inodeNumber;	// inode��
	private int processNumber;	// ʹ�ø��ļ��Ľ�����
	private int inodePtr;		// inodeָ��
	private boolean isModify;	// ��ʾ���ļ��Ƿ��޸�
	
	public String getString()
	{
		String out = "";
		int[] location = SuperBlock.Inode_IndexToLocation(inodePtr);
		out += String.format("\tInode��: %d\tInode���ڴ�Inode������λ��: %d(%d, %d)\t�ڴ�������: %d\t�Ƿ��޸�: ", inodeNumber, inodePtr, location[0], location[1], processNumber);
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
