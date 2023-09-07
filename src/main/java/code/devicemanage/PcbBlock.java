package code.devicemanage;

import code.Computer;
import code.barecomputer.Clock;

public class PcbBlock 
{
	private int pcbIndex;	// PCBָ��
	private int priority;	// ���ȼ�
	private int arrivalTime;// ����ʱ��
	private int time;		// �Ѿ�������ʱ��
	private int blockTime;	// Ӧ������ʱ��
	
	public String getString()
	{
		String out = "";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		out += String.format("PCB��: %d\n", pcbNumber);
		out += String.format("���ȼ�: %d\n", priority);
		out += String.format("��������ʱ��: %d\n", arrivalTime);
		out += String.format("��������ʱ��: %d\n", time);
		out += String.format("Ӧ������ʱ��: %d\n", blockTime);
		return out;
	}
	public String getWaitStrng()
	{
		String out = "";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		out += String.format("PCB��: %d\n", pcbNumber);
		out += String.format("���ȼ�: %d\n", priority);
		out += String.format("�ȴ�ʱ��: %d\n", Clock.getClock()-arrivalTime+1);
		return out;
	}
	public PcbBlock() 
	{
		pcbIndex = -1;
		arrivalTime = -1;
		time = 0;
		priority = 0;
		blockTime = 0;
	}
	public PcbBlock(int pcb, int p, int b, int time)
	{
		pcbIndex = pcb;
		priority = p;
		time = 0;
		blockTime = b;
		arrivalTime = time;
	}
	public PcbBlock(PcbBlock copy)
	{
		pcbIndex = copy.pcbIndex;
		priority = copy.priority;
		arrivalTime = copy.arrivalTime;
		time = copy.time;
		blockTime = copy.blockTime;
	}
	public void incTime()
	{
		time++;
	}
	public boolean check()
	{
		if(time >= blockTime)
			return true;
		return false;
	}
	public void show()
	{
		System.out.println("PcbIndex: "+pcbIndex);
		System.out.println("Priority: "+priority);
		System.out.println("Time: "+time);
		System.out.println("BlockTime: "+blockTime);
		System.out.println("");
	}
	public int getPriority() 
	{
		return priority;
	}
	public void setPriority(int priority) 
	{
		this.priority = priority;
	}
	public int getBlockTime() 
	{
		return blockTime;
	}
	public void setBlockTime(int blockTime) 
	{
		this.blockTime = blockTime;
	}
	public int getTime() 
	{
		return time;
	}
	public void setTime(int time) 
	{
		this.time = time;
	}
	public int getPcbIndex()
	{
		return pcbIndex;
	}
	public void setPcbIndex(int pcbIndex) 
	{
		this.pcbIndex = pcbIndex;
	}
	public int getArrivalTime()
	{
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}
}
