package code.devicemanage;

import java.util.ArrayList;

import code.Computer;
import code.barecomputer.Memory;

public class ProcessBlock 
{
	public ArrayList<PcbBlock> list = new ArrayList<>(Memory.MAX_PROCESS_NUM+1);
	public void add(PcbBlock pcbBlock)
	{
		list.add(pcbBlock);
	}
	// ����Ƿ�����ɵ��������̣�����ѡ�����ȼ��ߵ�
	public int check()
	{
		int index = -1;
		int p = -1;
		for(int i=0;i<list.size();++i)
		{
			if(list.get(i).check())
			{
				if(list.get(i).getPriority() > p)
				{
					p = list.get(i).getPriority();
					index = i;
				}
			}
		}
		return index;
	}
	// ������ȼ���ߵ�index
	// ͬ���ȼ��������ȵ����
	public int getMaxPriorityIndex()
	{
		int index = -1;
		int p = -1;
		for(int i=0;i<list.size();++i)
		{
			if(list.get(i).getPriority() > p)
			{
				p = list.get(i).getPriority();
				index = i;
			}
			else if(list.get(i).getPriority() == p)
			{
				if(list.get(i).getArrivalTime() < list.get(index).getArrivalTime())
					index = i;
			}
		}
		return index;
	}
	// ����index�ŵ�
	public void work(int index)
	{
		list.get(index).incTime();
	}
	// �����ļ�����
	public void workFile()
	{
		if(list.size() > 2)
		{
			System.err.println("�ļ���д�������������У����ڹ����Ľ�����������2");
			System.exit(-1);
		}
		for(int i=0;i<list.size();++i)
		{
			list.get(i).incTime();
		}
	}
	// ��ӡ������
	public void workPrinter()
	{
		if(list.size() > 3)
		{
			System.err.println("��ӡ���������������У����ڹ����Ľ�����������3");
			System.exit(-1);
		}
		for(int i=0;i<list.size();++i)
		{
			list.get(i).incTime();
			Computer.memory.deviceTable.time[i+2]++;
		}
	}
	// �ж��Ƿ��
	public boolean isEmpty()
	{
		if(list.size()==0)
			return true;
		return false;
	}
	// �Ƴ�
	public int remove(int index)
	{
		int outIndex = list.get(index).getPcbIndex();
		list.remove(index);
		return outIndex;
	}
	
	
	public ProcessBlock() 
	{	}
}