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
	 * ��õ�ǰӦ�ó��ӵ�index
	// �������ȼ�
	 * ���ȼ���ͬʱ�������ʱ��������
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
	// �ж��Ƿ��
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
