package code.memorymanage;

import code.barecomputer.Block;
import code.barecomputer.Disk;
import code.barecomputer.Memory;
import code.Computer;

public class PageTable 
{
	// ҳ����ĿΪ2
	private static final int MAX_NUMBER = 2; 
	private int itemNumber;			// ҳ������
	/**
	 * ����ҳ����Ϊ2
	 * ����ʹ��LRU�㷨�����ݽṹ�᷽��ܶ࣬�������һά��Ԫ����
	 * 0��ʾ�ϴ�֮ǰ�з��ʻ��δ����
	 * 1��ʾ�ϴξͱ�����
	 * ����ÿ���滻ʱ��ֻ��Ҫ�ҵ���¼Ϊ0��index����
	 * */
	private PageNumber pageNumber[];// �߼�ҳ���Լ���Ӧ��LRU��־λ
	private int memoryBlockNumber[];// �ڴ�������
	private int diskBlockNumber[];	// ���������
	private boolean isModify[];		// ��ʾ�Ƿ��޸ģ�true��ʾ�޸�
	private boolean Flag[];			// ��ʾ�ñ����Ƿ���Ч��trueΪ��Ч
	
	
	public String getString()
	{
		String out = "";
		out += "(�߼�ҳ��, LRU, �ڴ�, ���)\n";
		out += pageNumber[0].getPage()+","+pageNumber[0].getLRU()+","+memoryBlockNumber[0]+","+diskBlockNumber[0]+"\n";
		out += pageNumber[1].getPage()+","+pageNumber[1].getLRU()+","+memoryBlockNumber[1]+","+diskBlockNumber[1]+"\n";
		return out;
	}
	public PageTable() 
	{
		itemNumber = 0;
		pageNumber = new PageNumber[MAX_NUMBER];
		memoryBlockNumber = new int[MAX_NUMBER];
		diskBlockNumber = new int[MAX_NUMBER];
		isModify = new boolean[MAX_NUMBER];
		Flag = new boolean[MAX_NUMBER];
		for(int i=0;i<MAX_NUMBER;++i)
		{
			pageNumber[i] = new PageNumber();
			memoryBlockNumber[i] = -1;
			diskBlockNumber[i] = -1;
			isModify[i] = false;
			Flag[i] = false;
		}
	}
	public int getPageNumber(int index)
	{
		int out = -1;
		out = pageNumber[index].getPage();
		return out;
	}
	public PageTable(PageTable copy)
	{
		itemNumber = copy.itemNumber;
		pageNumber = new PageNumber[MAX_NUMBER];
		memoryBlockNumber = new int[MAX_NUMBER];
		diskBlockNumber = new int[MAX_NUMBER];
		isModify = new boolean[MAX_NUMBER];
		Flag = new boolean[MAX_NUMBER];
		for(int i=0;i<MAX_NUMBER;++i)
		{
			pageNumber[i] = copy.pageNumber[i];
			memoryBlockNumber[i] = copy.memoryBlockNumber[i];
			diskBlockNumber[i] = copy.diskBlockNumber[i];
			isModify[i] = copy.isModify[i];
			Flag[i] = copy.Flag[i];
		}
	}
	/**
	 * �����Ƿ��޸�
	 * */
	public void setIsModify(int number, boolean flag)
	{
		int index = getIndexFromNumber(number);
		isModify[index] = flag;
	}
	/**
	 * ��������ҳ
	 * */
	public void reclaimAllPage(Memory memory, Disk disk)
	{
		itemNumber = 0;
		for(int i=0;i<MAX_NUMBER;++i)
		{
			pageNumber[i].set(-1, -1);
			// д��
			writeBack(i, memory, disk);
			// �޸�λʾͼ
			memory.reclaimAFreeMemoryBlock(memoryBlockNumber[i]);
			// ���ó�ֵ
			memoryBlockNumber[i] = -1;
			diskBlockNumber[i] = -1;
			isModify[i] = false;
			Flag[i] = false;
		}
	}
	/**
	 * ����ҳ�Ż���ڴ�������
	 * */
	public int getMemoryBlockNumberFromPageNumber(int page)
	{
		int index = getIndexFromNumber(page);
		if(index == -1)
		{
			System.err.println("���ȱҳ�ж�ʧ��");
			System.exit(-1);
		}
		return memoryBlockNumber[index];
	}
	/**
	 * ����ҳ�Ż�ô���������
	 * */
	public int getDiskBlockNumberFromPageNumber(int page)
	{
		int index = getIndexFromNumber(page);
		return diskBlockNumber[index];
	}
	/**
	 * ���ҳ����ĳһҳ���ڱ��е�λ��
	 * �ɹ�����index
	 * ʧ�ܷ���-1
	 * */
	public int getIndexFromNumber(int number)
	{
		for(int i=0;i<MAX_NUMBER;++i)
		{
			if(Flag[i] && pageNumber[i].getPage()==number)
				return i;
		}
		return -1;
	}
	/**
	 * ���ҳ���е�һ������ҳ�����λ��
	 * �ɹ�����index
	 * ʧ�ܣ���������-1
	 * */
	public int getFirstFreePageItemIndex()
	{
		for(int i=0;i<MAX_NUMBER;++i)
		{
			if(Flag[i] == false)
				return i;
		}
		return -1;
	}
	/**
	 * ĳ��ҳ���Ƿ���ҳ����
	 * */
	public boolean exist(int page)
	{
		for(int i=0;i<MAX_NUMBER;++i)
		{
			if(Flag[i] && page==pageNumber[i].getPage())
				return true;
		}
		return false;
	}
	/**
	 * �ж�ҳ���Ƿ���
	 * */
	public boolean isFull()
	{
		if(itemNumber==MAX_NUMBER)
			return true;
		return false;
	}
	/**
	 * ����ĳ��ҳ
	 * �����ҳ�Ѿ�����ҳ��
	 * */
	public void accessAPage(int page)
	{
		int index = getIndexFromNumber(page);
		pageNumber[index].setLRU(1);
		pageNumber[1-index].setLRU(0);
		
		String temp = String.format("\t���߼�ҳ��: %d, �ڴ���: %d, �����: %d, ", pageNumber[index].getPage(), memoryBlockNumber[index], diskBlockNumber[index]);
		System.out.print(temp);
		Computer.RunInfo += temp;
	}
	/**
	 * ȱҳ�ж�
	 * */
	public void MissPage(int logicalAddress, int pcbIndex)
	{
		int newPage = logicalAddress / Block.MAX_SIZE;
		int replacedNumber = getReplaceedPageNumberWithLRU();
		int replacedIndex = getIndexFromNumber(replacedNumber);
		
		String temp = "";
		temp += String.format("\t��(%d, %d, %d) -> ", pageNumber[replacedIndex].getPage(), memoryBlockNumber[replacedIndex], diskBlockNumber[replacedIndex]);
		
		// ��ԭҳ�޸���д��
		if(isModify[replacedIndex])
			writeBackWhenReplace(replacedIndex);
		// ������ҳ��Ϣ
		pageNumber[replacedIndex].set(newPage, 1);
		pageNumber[1-replacedIndex].setLRU(0);
		// memoryBlockNumber[replacedIndex] = Memory.DATA_BLOCK_START_FOR_A_PROCESS[pcbIndex + replacedIndex];
		diskBlockNumber[replacedIndex] = Disk.DATA_BLOCK_FOR_A_PROCESS_START_INDEX[pcbIndex]+newPage;
		isModify[replacedIndex] = false;
		
		temp += String.format("(%d, %d, %d)\n", pageNumber[replacedIndex].getPage(), memoryBlockNumber[replacedIndex], diskBlockNumber[replacedIndex]);
		System.out.print(temp);
		Computer.RunInfo += temp;
	}
	/**
	 * д��
	 * */
	public void writeBackWhenReplace(int index)
	{	}
	/**
	 * ��ʼ��ҳ��
	 * װ�����ݶ�ǰ��ҳ���ڴ�
	 * */
	public void Initlize(int pcbIndex)
	{
		pageNumber[0].set(0, 1);
		pageNumber[1].set(1, 0);
		isModify[0] = false;
		isModify[1] = false;
		memoryBlockNumber[0] = Memory.DATA_BLOCK_START_FOR_A_PROCESS[pcbIndex];
		memoryBlockNumber[1] = Memory.DATA_BLOCK_START_FOR_A_PROCESS[pcbIndex]+1;
		diskBlockNumber[0] = Disk.DATA_BLOCK_FOR_A_PROCESS_START_INDEX[pcbIndex];
		diskBlockNumber[1] = Disk.DATA_BLOCK_FOR_A_PROCESS_START_INDEX[pcbIndex]+1;
		Flag[0] = true;
		Flag[1] = true;
	}
	/**
	 * ���һ��ҳ����
	 * */
	public void addAPageItem(int page, int mblock, int dblock, Memory memory, Disk disk)
	{
		int index = getFirstFreePageItemIndex();
		if(index == -1)
		{
			System.err.println("ҳ���滻��δ������Ӧ�滻��ҳ��");
			System.exit(-1);
		}
		// ���
		PageNumber newPageNumber = new PageNumber(page, 1);
		pageNumber[index] = newPageNumber;
		memoryBlockNumber[index] = mblock;
		diskBlockNumber[index] = dblock;
		isModify[index] = false;
		Flag[index] = true;
		itemNumber++;
		// �޸�LRU��־
		pageNumber[1-index].setLRU(0);
	}
	/**
	 * ɾ��һ��ҳ����
	 * ��ҳ��Ϊnumber
	 * �ɹ�����true
	 * ʧ�ܷ���false
	 * */
	public boolean deleteAPageItem(int page, Memory memory, Disk disk)
	{
//		for(int i=0;i<MAX_NUMBER;++i)
//		{
//			if(Flag[i] && pageNumber[i]==page)
//			{
//				// ����޸�λΪtrue���򽫿�д�ش�����
//				writeBack(i, memory, disk);
//				// ɾ���ñ���
//				Flag[i] = false;
//				itemNumber--;
//				// �޸�LRUList
//				if(LRUList[0]==page)
//				{
//					LRUList[0] = LRUList[1];
//					LRUList[1] = -1;
//				}
//				else if(LRUList[1]==page)
//					LRUList[1] = -1;
//				return true;
//			}
//		}
		return false;
	}
	/**
	 * ȷ���ĸ�ҳ��Ӧ���滻
	 * */
	public int getReplaceedPageNumberWithLRU()
	{
		for(int i=0;i<MAX_NUMBER;++i)
		{
			if(pageNumber[i].getLRU() == 0)
				return pageNumber[i].getPage();
		}
		return -1;
	}
	/**
	 * ��һ��ҳ����ָ����ڴ��д�ش��̿�
	 * */
	public void writeBack(int index, Memory memory, Disk disk)
	{
		if(Flag[index])
			return;
		if(isModify[index])
			memory.writeToDisk(memoryBlockNumber[index], diskBlockNumber[index], disk);
	}
	/**
	 * �滻
	 * */
	public void replace(int newPageNumber, int newDBlock, Memory memory, Disk disk)
	{
		int oldPageNumber = getReplaceedPageNumberWithLRU();
		int index = getIndexFromNumber(oldPageNumber);
		if(index==-1)
		{
			System.err.print("ҳ���滻ʱ����ҳ���޷���ҳ���б��ҵ�");
			System.exit(-1);
		}
		// ����ɵ��޸�λΪtrue���򽫿�д�ش�����
		writeBack(index, memory, disk);
		// �滻
		PageNumber newPNumber = new PageNumber(newPageNumber, 1);
		pageNumber[index] = newPNumber;
		// �ڴ�黹��ԭ����λ��
		// memoryBlockNumber[index] = memoryBlockNumber[index];
		diskBlockNumber[index] = newDBlock;
		isModify[index] = false;
		Flag[index] = true;
		// �޸�LRU��־
		pageNumber[1-index].setLRU(0);
		// ���̿�д���ڴ����
		memory.writeFromDisk(memoryBlockNumber[index], diskBlockNumber[index], disk);
	}
	/**
	 * ��ӡҳ����Ϣ
	 * */
	public void show()
	{
		System.out.println("-----PageTable-----");
		System.out.println("Number of Page Items: " + itemNumber);
		for(int i=0;i<MAX_NUMBER;++i)
		{
			if(Flag[i])
			{
				System.out.println("  ��PageNumber: "+pageNumber[i].getPage());
				System.out.println("  -PageLRU: "+pageNumber[i].getLRU());
				System.out.println("  -Block Number in Memory: "+memoryBlockNumber[i]);
				System.out.println("  -Block Number in Disk: "+diskBlockNumber[i]);
				System.out.println("  -isModify: "+isModify[i]);
			}
		}
	}
	/**
	 * �ж�ĳҳ�Ƿ���ҳ����
	 * �ж��߼�ҳ��
	 * ������index
	 * */
	public int isPageNumber(int Number)
	{
		for(int i=0;i<MAX_NUMBER;++i)
		{
			if(Flag[i] && pageNumber[i].getPage()==Number)
				return i;
		}
		return -1;
	}
}

class PageNumber
{
	private int Page;
	private int LRU;
	
	public PageNumber()
	{
		Page = -1;
		LRU = -1;
	}
	public PageNumber(int page, int lru)
	{
		Page = page;
		LRU = lru;
	}
	public PageNumber(PageNumber copy)
	{
		Page = copy.Page;
		LRU = copy.LRU;
	}
	public void set(int page, int lru)
	{
		Page = page;
		LRU = lru;
	}
	public void setPage(int page)
	{
		Page = page;
	}
	public void setLRU(int lru)
	{
		LRU = lru;
	}
	public int getPage()
	{
		return Page;
	}
	public int getLRU()
	{
		return LRU;
	}
}
