package code.processmanage;

public class PCBPool 
{
	public static final int MAX_PROCESS_NUM_IN_MEMORY = 12;
	public int number;		// PCBPool�е�PCB��Ŀ
	public PCB os;			// ����ϵͳ����
	public PCB[] pool;		// pool
	public boolean[] poolMap;	// �ص�λʾͼ
	
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
	// ���PCBPool�е�һ������λ��index��˳������
	public int getFirstFreeIndexInPoolMap()
	{
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
			if(poolMap[i]==false)
				return i;
		return -1;
	}
	// ��õ�ǰ��������̬��PCB��PCB���е�λ��
	public int getRunningProcrssIndex()
	{
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			if(pool[i].getState() == 2 && poolMap[i] == true)
				return i;
		}
		return -1;
	}
	// ���һ��PCB
	public void add(PCB pcb)
	{
		int index = getFirstFreeIndexInPoolMap();
		pool[index] = pcb;
		number++;
		poolMap[index] = true;
	}
	// �Ƴ�index��λ�õ�PCB
	public PCB remove(int index)
	{
		poolMap[index] = false;
		number--;
		return pool[index];
	}
	// ���indexλ�õ�PCB
	public PCB getPCBAtIndex(int index)
	{
		return pool[index];
	}
	// ���number�ŵ�PCB
	public PCB getPCBAtNumber(int number)
	{
		for(int i=0;i<MAX_PROCESS_NUM_IN_MEMORY;++i)
		{
			if(pool[i].getProcessNumber()==number && pool[i].getStartTime()!=-1)
				return pool[i];
		}
		System.err.println("ϵͳ����δ����PCB����������"+number+"�ŵ�PCB");
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
		System.err.println("ϵͳ����δ����PCB����������"+number+"�ŵ�PCB");
		System.exit(-1);
		return -1;
	}
	/**
	 * �޸�ĳnumber��PCB��ҳ��
	 * ������numberҳ���Ϊ�շ���
	 * */
	public void setLRUOnPageNumberAtPCBNumber(int pcbNumber, int page)
	{
		int index = getIndexAtNumber(pcbNumber);
		pool[index].pageTable.accessAPage(page);
	}
	/**
	 * number��PCB��ȱҳ�ж�
	 * */
	public void MissPage(int pcbNumber, int logicalAddress)
	{
		int index = getIndexAtNumber(pcbNumber);
		pool[index].pageTable.MissPage(logicalAddress, index);
	}
	/**
	 * ���PCB���Ƿ����½�̬
	 * ����ѡȡ���ȼ���ߵ�
	 * �����򷵻�����PCBPool�е�λ��
	 * �������򷵻�-1
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
	 * ���PCB���Ƿ�������̬
	 * �����򷵻�����PCBPool�е�λ��
	 * �������򷵻�-1
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
	 * ���PCB���Ƿ��о���̬
	 * �����򷵻�����PCBPool�е�λ��
	 * ����ѡȡ���ȼ���ߵ�
	 * �������򷵻�-1
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
	 * �½�̬ת��Ϊ����̬�Ĳ�����
	 * */
	// ����index��pcb���ݣ������½�̬������̬ת��
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
