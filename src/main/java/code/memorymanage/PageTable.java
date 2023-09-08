package code.memorymanage;

import code.barecomputer.Block;
import code.barecomputer.Disk;
import code.barecomputer.Memory;
import code.Computer;

public class PageTable 
{
	// 页框数目为2
	private static final int MAX_NUMBER = 2; 
	private int itemNumber;			// 页表项数
	/**
	 * 由于页框数为2
	 * 则若使用LRU算法，数据结构会方便很多，这里采用一维二元数组
	 * 0表示上次之前有访问或从未访问
	 * 1表示上次就被访问
	 * 所以每次替换时，只需要找到记录为0的index即可
	 * */
	private final PageNumber[] pageNumber;// 逻辑页号以及对应的LRU标志位
	private final int[] memoryBlockNumber;// 内存物理块号
	private final int[] diskBlockNumber;	// 外存物理块号
	private boolean isModify[];		// 表示是否修改，true表示修改
	private boolean Flag[];			// 表示该表项是否有效，true为有效
	
	
	public String getString()
	{
		String out = "";
		out += "(逻辑页号, LRU, 内存, 外存)\n";
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
	 * 设置是否修改
	 * */
	public void setIsModify(int number, boolean flag)
	{
		int index = getIndexFromNumber(number);
		isModify[index] = flag;
	}
	/**
	 * 回收所有页
	 * */
	public void reclaimAllPage(Memory memory, Disk disk)
	{
		itemNumber = 0;
		for(int i=0;i<MAX_NUMBER;++i)
		{
			pageNumber[i].set(-1, -1);
			// 写回
			writeBack(i, memory, disk);
			// 修改位示图
			memory.reclaimAFreeMemoryBlock(memoryBlockNumber[i]);
			// 设置初值
			memoryBlockNumber[i] = -1;
			diskBlockNumber[i] = -1;
			isModify[i] = false;
			Flag[i] = false;
		}
	}
	/**
	 * 根据页号获得内存物理块号
	 * */
	public int getMemoryBlockNumberFromPageNumber(int page)
	{
		int index = getIndexFromNumber(page);
		if(index == -1)
		{
			System.err.println("检查缺页中断失败");
			System.exit(-1);
		}
		return memoryBlockNumber[index];
	}
	/**
	 * 根据页号获得磁盘物理块号
	 * */
	public int getDiskBlockNumberFromPageNumber(int page)
	{
		int index = getIndexFromNumber(page);
		return diskBlockNumber[index];
	}
	/**
	 * 获得页表中某一页号在表中的位置
	 * 成功返回index
	 * 失败返回-1
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
	 * 获得页表中第一个空闲页表项的位置
	 * 成功返回index
	 * 失败（满）返回-1
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
	 * 某个页号是否在页表里
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
	 * 判断页表是否满
	 * */
	public boolean isFull()
	{
		if(itemNumber==MAX_NUMBER)
			return true;
		return false;
	}
	/**
	 * 访问某个页
	 * 假设该页已经存在页表
	 * */
	public void accessAPage(int page)
	{
		int index = getIndexFromNumber(page);
		pageNumber[index].setLRU(1);
		pageNumber[1-index].setLRU(0);
		
		String temp = String.format("\t●逻辑页号: %d, 内存块号: %d, 外存块号: %d, ", pageNumber[index].getPage(), memoryBlockNumber[index], diskBlockNumber[index]);
		System.out.print(temp);
		Computer.RunInfo += temp;
	}
	/**
	 * 缺页中断
	 * */
	public void MissPage(int logicalAddress, int pcbIndex)
	{
		int newPage = logicalAddress / Block.MAX_SIZE;
		int replacedNumber = getReplaceedPageNumberWithLRU();
		int replacedIndex = getIndexFromNumber(replacedNumber);
		
		String temp = "";
		temp += String.format("\t●(%d, %d, %d) -> ", pageNumber[replacedIndex].getPage(), memoryBlockNumber[replacedIndex], diskBlockNumber[replacedIndex]);
		
		// 若原页修改则写回
		if(isModify[replacedIndex])
			writeBackWhenReplace(replacedIndex);
		// 设置新页信息
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
	 * 写回
	 * */
	public void writeBackWhenReplace(int index)
	{	}
	/**
	 * 初始化页表
	 * 装入数据段前两页到内存
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
	 * 添加一个页表项
	 * */
	public void addAPageItem(int page, int mblock, int dblock, Memory memory, Disk disk)
	{
		int index = getFirstFreePageItemIndex();
		if(index == -1)
		{
			System.err.println("页面替换中未检索到应替换的页号");
			System.exit(-1);
		}
		// 添加
		PageNumber newPageNumber = new PageNumber(page, 1);
		pageNumber[index] = newPageNumber;
		memoryBlockNumber[index] = mblock;
		diskBlockNumber[index] = dblock;
		isModify[index] = false;
		Flag[index] = true;
		itemNumber++;
		// 修改LRU标志
		pageNumber[1-index].setLRU(0);
	}
	/**
	 * 删除一个页表项
	 * 该页号为number
	 * 成功返回true
	 * 失败返回false
	 * */
	public boolean deleteAPageItem(int page, Memory memory, Disk disk)
	{
//		for(int i=0;i<MAX_NUMBER;++i)
//		{
//			if(Flag[i] && pageNumber[i]==page)
//			{
//				// 如果修改位为true，则将块写回磁盘中
//				writeBack(i, memory, disk);
//				// 删除该表项
//				Flag[i] = false;
//				itemNumber--;
//				// 修改LRUList
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
	 * 确定哪个页号应被替换
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
	 * 将一个页表项指向的内存块写回磁盘块
	 * */
	public void writeBack(int index, Memory memory, Disk disk)
	{
		if(Flag[index])
			return;
		if(isModify[index])
			memory.writeToDisk(memoryBlockNumber[index], diskBlockNumber[index], disk);
	}
	/**
	 * 替换
	 * */
	public void replace(int newPageNumber, int newDBlock, Memory memory, Disk disk)
	{
		int oldPageNumber = getReplaceedPageNumberWithLRU();
		int index = getIndexFromNumber(oldPageNumber);
		if(index==-1)
		{
			System.err.print("页表替换时，旧页号无法在页表中被找到");
			System.exit(-1);
		}
		// 如果旧的修改位为true，则将块写回磁盘中
		writeBack(index, memory, disk);
		// 替换
		PageNumber newPNumber = new PageNumber(newPageNumber, 1);
		pageNumber[index] = newPNumber;
		// 内存块还是原来的位置
		// memoryBlockNumber[index] = memoryBlockNumber[index];
		diskBlockNumber[index] = newDBlock;
		isModify[index] = false;
		Flag[index] = true;
		// 修改LRU标志
		pageNumber[1-index].setLRU(0);
		// 磁盘块写入内存块中
		memory.writeFromDisk(memoryBlockNumber[index], diskBlockNumber[index], disk);
	}
	/**
	 * 打印页表信息
	 * */
	public void show()
	{
		System.out.println("-----PageTable-----");
		System.out.println("Number of Page Items: " + itemNumber);
		for(int i=0;i<MAX_NUMBER;++i)
		{
			if(Flag[i])
			{
				System.out.println("  ●PageNumber: "+pageNumber[i].getPage());
				System.out.println("  -PageLRU: "+pageNumber[i].getLRU());
				System.out.println("  -Block Number in Memory: "+memoryBlockNumber[i]);
				System.out.println("  -Block Number in Disk: "+diskBlockNumber[i]);
				System.out.println("  -isModify: "+isModify[i]);
			}
		}
	}
	/**
	 * 判断某页是否在页表内
	 * 判断逻辑页号
	 * 返回其index
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
