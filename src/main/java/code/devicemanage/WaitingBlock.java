package code.devicemanage;

import java.util.ArrayList;

public class WaitingBlock 
{
	public ArrayList<PcbBlock> list = new ArrayList<>();
	
	public WaitingBlock() 
	{
		// TODO Auto-generated constructor stub
	}
	public void add(PcbBlock pcbBlock)
	{
		list.add(pcbBlock);
	}
	/**
	 * 获得当前应该出队的index
	// 根据优先级
	 * 优先级相同时根据入队时间先来后到
	 * */
	public int check()
	{
		int index = -1;
		int p = -1;
		for(int i=0;i<list.size();++i)
		{
			if(list.get(i).getPriority() > p)
			{
				index = i;
				p = list.get(i).getPriority();
			}
			else if(list.get(i).getPriority() == p)
			{
				if(list.get(i).getArrivalTime() < list.get(index).getArrivalTime())
					index = i;
			}
		}
		return index;
	}
	// 判断是否空
	public boolean isEmpty()
	{
		if(list.size()==0)
			return true;
		return false;
	}
	public int remove(int index)
	{
		int out = list.get(index).getPcbIndex();
		list.remove(index);
		return out;
	}
}
