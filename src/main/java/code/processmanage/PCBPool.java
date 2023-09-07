package code.processmanage;

public class PCBPool 
{
	public static final int MAX_PROCESS_NUM_IN_MEMORY = 12;
	public int number;		// PCBPool中的PCB数目
	public PCB os;			// 操作系统进程
	public PCB[] pool;		// pool
	public boolean[] poolMap;	// 池的位示图
	
	public PCBPool() 
	{
		setNumber(0);
		pool = new PCB[MAX_PROCESS_NUM_IN_MEMORY];
		poolMap = new boolean[MAX_PROCESS_NUM_IN_MEMORY];
		os = new PCB();
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			pool[i] = new PCB();
			poolMap[i] = false;
		}
	}
	public PCBPool(PCBPool copy)
	{
		number = copy.number;
		os = copy.os;
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			pool[i] = copy.pool[i];
			poolMap[i] = copy.poolMap[i];
		}
	}
	// 获得PCBPool中第一个空闲位置index，顺序索引
	public int getFirstFreeIndexInPoolMap()
	{
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
			if(poolMap[i]==false)
				return i;
		return -1;
	}
	// 获得当前处于运行态的PCB在PCB池中的位置
	public int getRunningProcrssIndex()
	{
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			if(pool[i].getState() == 2 && poolMap[i] == true)
				return i;
		}
		return -1;
	}
	// 添加一个PCB
	public void add(PCB pcb)
	{
		int index = getFirstFreeIndexInPoolMap();
		pool[index] = pcb;
		number++;
		poolMap[index] = true;
	}
	// 移除index号位置的PCB
	public PCB remove(int index)
	{
		poolMap[index] = false;
		number--;
		return pool[index];
	}
	// 获得index位置的PCB
	public PCB getPCBAtIndex(int index)
	{
		return pool[index];
	}
	// 获得number号的PCB
	public PCB getPCBAtNumber(int number)
	{
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			if(pool[i].getProcessNumber()==number && pool[i].getStartTime()!=-1)
				return pool[i];
		}
		System.err.println("系统错误：未能在PCB池中搜索到"+number+"号的PCB");
		System.exit(-1);
		return (new PCB());
	}
	public int getIndexAtNumber(int number)
	{
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			if(pool[i].getProcessNumber()==number && pool[i].getStartTime()!=-1)
				return i;
		}
		System.err.println("系统错误：未能在PCB池中搜索到"+number+"号的PCB");
		System.exit(-1);
		return -1;
	}
	/**
	 * 修改某number的PCB的页表
	 * 设置其number页表号为刚访问
	 * */
	public void setLRUOnPageNumberAtPCBNumber(int pcbNumber, int page)
	{
		int index = getIndexAtNumber(pcbNumber);
		pool[index].pageTable.accessAPage(page);
	}
	/**
	 * number号PCB的缺页中断
	 * */
	public void MissPage(int pcbNumber, int logicalAddress)
	{
		int index = getIndexAtNumber(pcbNumber);
		pool[index].pageTable.MissPage(logicalAddress, index);
	}
	/**
	 * 检查PCB中是否有新建态
	 * 总是选取优先级最高的
	 * 存在则返回其在PCBPool中的位置
	 * 不存在则返回-1
	 * */
	public int checkIsNewReturnIndex()
	{
		int out = -1;
		int p = -1;
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			if(pool[i].getState() == PCB.NEW && poolMap[i])
			{	
				if(pool[i].getPriority() > p)
				{
					p = pool[i].getPriority();
					out = i;
				}
			}
		}
		return out;
	}
	/**
	 * 检查PCB中是否有运行态
	 * 存在则返回其在PCBPool中的位置
	 * 不存在则返回-1
	 * */
	public int checkIsRunningReturnIndex()
	{
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			if(pool[i].getState() == PCB.RUNNING && poolMap[i])
				return i;
		}
		return -1;
	}
	/**
	 * 检查PCB中是否有就绪态
	 * 存在则返回其在PCBPool中的位置
	 * 总是选取优先级最高的
	 * 不存在则返回-1
	 * */
	public int checkIsReadyReturnIndex()
	{
		int out = -1;
		int p = -1;
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			if(pool[i].getState() == PCB.READY && poolMap[i])
			{	
				if(pool[i].getPriority() > p)
				{
					p = pool[i].getPriority();
					out = i;
				}
			}
		}
		return out;
	}
	/**
	 * 新建态转化为就绪态的操作集
	 * */
	// 设置index号pcb内容，用于新建态到就绪态转换
	public void setPcbOnNewToReady(int pcbIndex)
	{
		pool[pcbIndex].setState(1);
		pool[pcbIndex].setTimeFlak(0);
	}
	
	
	
	// get && set
	public int getNumber() 
	{
		return number;
	}
	public void setNumber(int number) 
	{
		this.number = number;
	}
}
