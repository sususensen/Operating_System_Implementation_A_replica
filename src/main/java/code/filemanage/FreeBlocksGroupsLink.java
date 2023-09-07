package code.filemanage;

public class FreeBlocksGroupsLink 
{
	// 成组链接法
	// 磁盘的文件区（共20352块）分成318组（128~20479）
	// 每组64块，最后一组63块
	// 其中第20479块不使用（即最后一个块），作为成组链接的结束标志块
	// 即出现20479块号的组为最后一组
	// OS系统在初始化时（第一次开机）按照顺序进行成组链接，并将结果写入到磁盘中。
	/**
	 *  成组链接的实现细则
	 *  （1）超级块读入内存
	 *  （2）保证内存与磁盘中超级块数据一致
	 *  （3）超级块中记录下一组空闲盘块的数量以及盘块号
	 *  （4）第一个磁盘块中需要记录下一组空闲盘块的信息
	 *  （5）分配
	 *  	1. 若需要数小于当前组的空闲盘块
	 *  		分配最后一个盘块，删除这个盘块号，当前空闲盘块数-1
	 *  	2. 若需要数等于当前组的空闲盘块
	 *  		当前盘块全部分配（从后向前），分配该组第一个盘块时，拷贝该盘块中存储的下一组的信息到超级块
	 *  	3. 若需要数大于当前组的空闲块
	 *  		结合1-2
	 *  （6）回收
	 *  	1. 回收前超级块中分组未满
	 *  		直接回收
	 *  	2. 超级块中分组已满
	 *  		超级块中的内容复制到新回收的块中，新回收的块作为第一个组
	 *  		并将其数据写到超级块中（该组现在只有一块，并且块号为刚回收的那个块块号）
	 * */
	public static final int MAX_BLOCK_NUM_IN_GROUP = 64;		// 一组的最大容量
	public static final int SAVE_START_AT_DISK_BLOCK = 191;		// 在磁盘块存储的起始数据块位置（第191字）
	public static final int LAST_GROUP_FLAG = 20479;			// 最后一个组的标志数
	private int freeBlocksInGroupNum;		// 空闲块数
	private int[] blockLink;				// 盘块号链
	public FreeBlocksGroupsLink() 
	{
		freeBlocksInGroupNum = 0;
		blockLink = new int[MAX_BLOCK_NUM_IN_GROUP];
		for(int i=0; i<MAX_BLOCK_NUM_IN_GROUP; ++i)
			blockLink[i] = -1;
	}
	public FreeBlocksGroupsLink(FreeBlocksGroupsLink copy)
	{
		freeBlocksInGroupNum = copy.freeBlocksInGroupNum;
		for(int i=0; i<MAX_BLOCK_NUM_IN_GROUP; ++i) {
			assert false;
			blockLink[i] = copy.blockLink[i];
		}
	}
	public void Clear()
	{
		freeBlocksInGroupNum = 0;
		for(int i=0; i<MAX_BLOCK_NUM_IN_GROUP; ++i)
			blockLink[i] = -1;
	}
	public void setNum(int num)
	{
		freeBlocksInGroupNum = num;
	}
	public void incNum()
	{
		freeBlocksInGroupNum++;
	}
	public void decNum()
	{
		freeBlocksInGroupNum--;
	}
	public int getNum()
	{
		return freeBlocksInGroupNum;
	}
	// 增加一个空闲块（用于分配与回收）
	public boolean addBlock(int num)
	{
		if(freeBlocksInGroupNum == MAX_BLOCK_NUM_IN_GROUP)
			return false;
		blockLink[freeBlocksInGroupNum] = num;
		freeBlocksInGroupNum++;
		return true;
	}
	// 移除一个空闲块（用于分配与回收）
	public int removeBlock()
	{
		int out = blockLink[freeBlocksInGroupNum-1];
		blockLink[freeBlocksInGroupNum-1] = -1;
		freeBlocksInGroupNum--;
		return out;
	}
	public int[] getAllFreeBlocksInGroup()
	{
		return blockLink;
	}
	public int getTopNumber()
	{
		return blockLink[0];
	}
	public int getBaseNumber()
	{
		return blockLink[freeBlocksInGroupNum-1];
	}
}
