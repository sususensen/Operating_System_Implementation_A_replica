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
	// 检查是否有完成的阻塞进程，总是选择优先级高的
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
	// 获得优先级最高的index
	// 同优先级处理最先到达的
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
	// 处理index号的
	public void work(int index)
	{
		list.get(index).incTime();
	}
	// 用于文件阻塞
	public void workFile()
	{
		if(list.size() > 2)
		{
			System.err.println("文件读写工作阻塞队列中，正在工作的进程数量大于2");
			System.exit(-1);
		}
		for(int i=0;i<list.size();++i)
		{
			list.get(i).incTime();
		}
	}
	// 打印机处理
	public void workPrinter()
	{
		if(list.size() > 3)
		{
			System.err.println("打印机工作阻塞队列中，正在工作的进程数量大于3");
			System.exit(-1);
		}
		for(int i=0;i<list.size();++i)
		{
			list.get(i).incTime();
			Computer.memory.deviceTable.time[i+2]++;
		}
	}
	// 判断是否空
	public boolean isEmpty()
	{
		if(list.size()==0)
			return true;
		return false;
	}
	// 移除
	public int remove(int index)
	{
		int outIndex = list.get(index).getPcbIndex();
		list.remove(index);
		return outIndex;
	}
	
	
	public ProcessBlock() 
	{	}
}