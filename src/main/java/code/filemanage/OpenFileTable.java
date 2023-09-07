package code.filemanage;

import java.util.ArrayList;

import code.Computer;

/**
 * 系统打开文件表
 * 该表记录系统装入内存的inode
 * */
public class OpenFileTable 
{
	public static final int MAX_NUMBER = 16;
	public int Number;						// 打开数
	public ArrayList<OpenFile> openFiles;	// 文件句柄
	
	public String getString()
	{
		String out = "";
		for(int i=0;i<Number;++i)
		{
			out += String.format("(%d)\n", i);
			out += openFiles.get(i).getString();
		}
		return out;
	}
	public OpenFileTable() 
	{
		Number = 0;
		openFiles = new ArrayList<OpenFile>(MAX_NUMBER);
	}
	public OpenFileTable(OpenFileTable copy)
	{
		Number = copy.Number;
		openFiles = new ArrayList<OpenFile>(MAX_NUMBER);
		for(int i=0;i<MAX_NUMBER;++i)
		{
			OpenFile temp = copy.getOpenFile(i);
			openFiles.add(i, temp);
		}
	}
	public OpenFile getOpenFile(int index)
	{
		return openFiles.get(index);
	}
	// 添加一个表项
	public void addAOpenFile(int number, int pNumber, int ptr)
	{
		OpenFile of = new OpenFile(number,pNumber,ptr);
		openFiles.add(of);
		Number++;
	}
	// 获取inode号为number的位置index
	public int getIndexFromNumber(int number)
	{
		for(int i=0;i<Number;++i)
		{
			if(openFiles.get(i).getInodeNumber()==number)
				return i;
		}
		return -1;
	}
	// 删除inode号为number的表项
	public void removeAOpenFile(int number)
	{
		int index = getIndexFromNumber(number);
		Computer.memory.superBlock.reclaimAnInodeInMemory(openFiles.get(index).getInodePtr());
		openFiles.remove(index);
		Number--;
	}
	// 判断某号inode是否已在系统中
	public int isIn(int number)
	{
		for(int i=0;i<Number;++i)
		{
			if(number == openFiles.get(i).getInodeNumber())
				return i;
		}
		return -1;
	}
	// 判断某number号的打开文件进程引用数是否为1
	public boolean isOne(int number)
	{
		int index = getIndexFromNumber(number);
		if(openFiles.get(index).getProcessNumber()<=1)
			return true;
		return false;
	}
	// 使number号inode的进程使用数加一
	public void incPNumberForInode(int number)
	{
		int index = getIndexFromNumber(number);
		openFiles.get(index).incProcessNumber();
	}
	// 使number号inode的进程使用数减一
	public void decPNumberForInode(int number)
	{
		int index = getIndexFromNumber(number);
		openFiles.get(index).decProcessNumber();;
	}
	public void modify(int number)
	{
		int index = getIndexFromNumber(number);
		openFiles.get(index).doModify();
	}
	public int getNumber()
	{
		return Number;
	}
}