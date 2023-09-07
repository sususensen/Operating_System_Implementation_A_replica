package code.filemanage;

import java.util.ArrayList;

import code.Computer;

/**
 * ϵͳ���ļ���
 * �ñ��¼ϵͳװ���ڴ��inode
 * */
public class OpenFileTable 
{
	public static final int MAX_NUMBER = 16;
	public int Number;						// ����
	public ArrayList<OpenFile> openFiles;	// �ļ����
	
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
	// ���һ������
	public void addAOpenFile(int number, int pNumber, int ptr)
	{
		OpenFile of = new OpenFile(number,pNumber,ptr);
		openFiles.add(of);
		Number++;
	}
	// ��ȡinode��Ϊnumber��λ��index
	public int getIndexFromNumber(int number)
	{
		for(int i=0;i<Number;++i)
		{
			if(openFiles.get(i).getInodeNumber()==number)
				return i;
		}
		return -1;
	}
	// ɾ��inode��Ϊnumber�ı���
	public void removeAOpenFile(int number)
	{
		int index = getIndexFromNumber(number);
		Computer.memory.superBlock.reclaimAnInodeInMemory(openFiles.get(index).getInodePtr());
		openFiles.remove(index);
		Number--;
	}
	// �ж�ĳ��inode�Ƿ�����ϵͳ��
	public int isIn(int number)
	{
		for(int i=0;i<Number;++i)
		{
			if(number == openFiles.get(i).getInodeNumber())
				return i;
		}
		return -1;
	}
	// �ж�ĳnumber�ŵĴ��ļ������������Ƿ�Ϊ1
	public boolean isOne(int number)
	{
		int index = getIndexFromNumber(number);
		if(openFiles.get(index).getProcessNumber()<=1)
			return true;
		return false;
	}
	// ʹnumber��inode�Ľ���ʹ������һ
	public void incPNumberForInode(int number)
	{
		int index = getIndexFromNumber(number);
		openFiles.get(index).incProcessNumber();
	}
	// ʹnumber��inode�Ľ���ʹ������һ
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