package code.barecomputer;

import code.filemanage.FreeBlocksGroupsLink;

public class SuperBlock extends Block
{
	// 内存
	public int freeBlockInMemory;				// 内存中空闲的物理块数
	public boolean[] freeBlockInMemoryMap;		// 内存物理块的位示图，false表示空闲，true表示占用
	public int freeInodeInMemory;				// 内存中inode的空闲数
	public boolean[][] freeInodeInMemoryMap;	// 内存中inode的位示图
	
	// 硬盘
	public int freeInodeInDisk;				// 硬盘中inode的空闲数
	public boolean[][] freeInodeInDiskMap;		// 硬盘中空闲inode的位示图
	public FreeBlocksGroupsLink freeBlocksGroupsLink;	// 空闲区块链
	
	public SuperBlock() 
	{
		// TODO Auto-generated constructor stub
		super();
		freeBlockInMemoryMap = new boolean[Memory.MAX_BLOCK_NUM];
		freeInodeInMemoryMap = new boolean[Memory.INODE_BLOCK_NUM][INODE_NUM_IN_BLOCK];
		freeInodeInDiskMap = new boolean[Disk.INODE_BLOCK_NUM][INODE_NUM_IN_BLOCK];
		freeBlocksGroupsLink = new FreeBlocksGroupsLink();
	}
	public SuperBlock(SuperBlock copy)
	{
		freeBlockInMemory = copy.freeBlockInMemory;
		for(int i=0;i<Memory.MAX_BLOCK_NUM;++i)
			freeBlockInMemoryMap[i] = copy.freeBlockInMemoryMap[i];
		freeInodeInMemory = copy.freeInodeInMemory;
		for(int i=0;i<Memory.INODE_BLOCK_NUM;++i)
			for(int j=0;j<INODE_NUM_IN_BLOCK;++j)
				freeInodeInMemoryMap[i][j] = copy.freeInodeInMemoryMap[i][j];
		freeInodeInDisk = copy.freeInodeInDisk;
		for(int i=0;i<Disk.INODE_BLOCK_NUM;++i)
			for(int j=0;j<INODE_NUM_IN_BLOCK;++j)
				freeInodeInDiskMap[i][j] = copy.freeInodeInDiskMap[i][j];
		freeBlocksGroupsLink = copy.freeBlocksGroupsLink;
	}
	
	// 获得第一个空闲内存块的index
	public int getFistFreeBlockIndexInMemory()
	{
		for(int i=0; i<Memory.MAX_BLOCK_NUM; ++i)
		{
			if(!freeBlockInMemoryMap[i])
				return i;
		}
		return -1;
	}
	// 获得第一个内存中空闲Inode位置的location
	public int[] getFirstFreeInodeLocationInMemory()
	{
		int[] location = new int[2];
		location[0] = -1;
		location[1] = -1;
		for(int i=0; i<Memory.INODE_BLOCK_NUM; ++i)
		{
			for(int j=0; j<INODE_NUM_IN_BLOCK; ++j)
			{
				if(!freeInodeInMemoryMap[i][j])
				{
					location[0] = i;
					location[1] = j;
					return location;
				}
			}
		}
		return location;
	}
	// 获得第一个磁盘中空闲Inode位置的location
	public int[] getFirstFreeInodeLocationInDisk()
	{
		int[] location = new int[2];
		location[0] = -1;
		location[1] = -1;
		for(int i=0; i<Disk.INODE_BLOCK_NUM; ++i)
		{
			for(int j=0; j<INODE_NUM_IN_BLOCK; ++j)
			{
				if(!freeInodeInDiskMap[i][j])
				{
					location[0] = i;
					location[1] = j;
					return location;
				}
			}
		}
		return location;
	}
	// 获得空闲数
	public int getFreeBlockInMemory()
	{
		return freeBlockInMemory;
	}
	// 获得位示图
	public boolean[] getFreeBlockInMemoryMap()
	{
		return freeBlockInMemoryMap;
	}
	public boolean[][] getFreeInodeInMemoryMap()
	{
		return freeInodeInMemoryMap;
	}
	public boolean[][] getFreeInodeInDiskMap()
	{
		return freeInodeInDiskMap;
	}
	// 设置空闲数
	public void incFreeBlockInMemory()
	{
		freeBlockInMemory++;
	}
	public void decFreeBlockInMemory()
	{
		freeBlockInMemory--;
	}
	// 设置位示图
	public void setFreeBlockInMemoryMap(boolean[] copy)
	{
		for(int i=0;i<Memory.MAX_BLOCK_NUM;++i)
			freeBlockInMemoryMap[i] = copy[i];
	}
	
	/**
	 * 申请一个内存中的inode
	 * 空闲则成功，返回分配后inode的物理位置
	 * 已满则拒绝，返回-1
	 * */
	public int allocateAnInodeInMemory()
	{
		if(freeInodeInMemory == 0)
			return -1;
		int index = -1;
		for(int i=0; i<Memory.INODE_BLOCK_NUM; ++i)
		{
			for(int j=0; j<INODE_NUM_IN_BLOCK; ++j)
			{
				if(freeInodeInMemoryMap[i][j]==false)
				{
					// 获取分配的Inode位置号
					index = Inode_LocationToIndex(i, j);
					// 修改Inode位示图以及剩余空闲数
					freeInodeInMemoryMap[i][j] = true;
					freeInodeInMemory--;
					return index;
				}
			}
		}
		return -1;
	}
	/**
	 * 回收一个内存中的inode
	 * 该inode在内存Inode区的位置为index
	 * */
	public void reclaimAnInodeInMemory(int index)
	{
		int[] location = Inode_IndexToLocation(index);
		// 修改位示图
		freeInodeInMemoryMap[location[0]][location[1]] = false;
		// 修改剩余空闲数
		freeInodeInMemory++;
	}
	/**
	 * 进程原语涉及的位示图转换
	 * */
	// 分配index预定位置的进程空间（2块）
	public void allocatePCBMemoryAtIndex(int index)
	{
		int codeBlockIndex = Memory.CODE_BLOCK_START_FOR_A_PROCESS[index];
		int fileBlockIndex = Memory.DATA_BLOCK_START_FOR_A_PROCESS[index];
		// 空闲数-3
		freeBlockInMemory -= 3;
		// 修改位示图
		freeBlockInMemoryMap[codeBlockIndex] = true;
		freeBlockInMemoryMap[fileBlockIndex] = true;
		freeBlockInMemoryMap[fileBlockIndex+1] = true;
	}
	// 回收index预定位置的进程空间（2块）
	public void reclaimPCBMemoryAtIndex(int index)
	{
		int codeBlockIndex = Memory.CODE_BLOCK_START_FOR_A_PROCESS[index];
		int fileBlockIndex = Memory.DATA_BLOCK_START_FOR_A_PROCESS[index];
		// 空闲数-3
		freeBlockInMemory += 3;
		// 修改位示图
		freeBlockInMemoryMap[codeBlockIndex] = false;
		freeBlockInMemoryMap[fileBlockIndex] = false;
		freeBlockInMemoryMap[fileBlockIndex+1] = false;
	}
	/**
	 * 将磁盘中的十六进制数据
	 * 转化为超级块类属性
	 * */
 	public void loadFromDisk(Block block)
	{
		// System.out.println("超级块装入内存中...");
		// 内存中的空物理块
		freeBlockInMemory = Block.HEX_TO_INT(block.getDataAtIndex(0));
		// 计算内存物理块位示图
		char[] binMemoryBlockMap = Block.getBinFromDatasInBlock(1, 4, block).toCharArray();
		for(int i=0; i<binMemoryBlockMap.length; ++i)
		{
			if(binMemoryBlockMap[i] == '1')
				freeBlockInMemoryMap[i] = true;
			else if(binMemoryBlockMap[i] == '0')
				freeBlockInMemoryMap[i] = false;
			else
			{
				System.err.print("严重错误，转二进制时出现未知错误");
				System.exit(-1);
			}
		}
		// 内存中inode空闲数
		freeInodeInMemory = Block.HEX_TO_INT(block.getDataAtIndex(5));
		// 内存中Inode位示图
		char[] binMemoryInodeMap = block.getDataAtIndex(6).toCharArray();
		for(int i=0; i<binMemoryInodeMap.length; ++i)
		{
			int[] location = Inode_IndexToLocation(i);
			if(binMemoryInodeMap[i] == '1')
				freeInodeInMemoryMap[location[0]][location[1]] = true;
			else if(binMemoryInodeMap[i] == '0')
				freeInodeInMemoryMap[location[0]][location[1]] = false;
			else
			{
				System.err.print("严重错误，转二进制时出现未知错误");
				System.exit(-1);
			}
		}
		// 硬盘中Inode空闲数
		freeInodeInDisk = Block.HEX_TO_INT(block.getDataAtIndex(7));
		// 硬盘中空闲Inode位示图
		char[] binDiskInodeMap = Block.getBinFromDatasInBlock(8, 59, block).toCharArray();
		for(int i=0; i<binDiskInodeMap.length; ++i)
		{
			int[] location = Inode_IndexToLocation(i);
			if(binDiskInodeMap[i] == '1')
				freeInodeInDiskMap[location[0]][location[1]] = true;
			else if(binDiskInodeMap[i] == '0')
				freeInodeInDiskMap[location[0]][location[1]] = false;
			else
			{
				System.err.print("严重错误，转二进制时出现未知错误");
				System.exit(-1);
			}
		}
		// 第一组空闲区块链
		int freeNum = Block.HEX_TO_INT(block.getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK));
		for(int i=0; i<freeNum; ++i)
		{
			int index = FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK + i + 1;
			int number = Block.HEX_TO_INT(block.getDataAtIndex(index));
			freeBlocksGroupsLink.addBlock(number);
		}
		// System.out.println("超级块已同步到内存");
	}
	/**
	 * 用于将inode的index与location相互转换
	 * 用于位示图
	 * */
	public static int[] Inode_IndexToLocation(int index)
	{
		int[] location = new int[2];
		location[0] = index / Block.INODE_NUM_IN_BLOCK;
		location[1] = index % Block.INODE_NUM_IN_BLOCK;
		return location;
	}
	public static int Inode_LocationToIndex(int x, int y)
	{
		if(y >= 8)
		{
			System.err.print("严重错误，一块中记录Inode数多于8个");
			System.exit(-1);
		}
		int out = x*Block.INODE_NUM_IN_BLOCK + y;
		return out;
	}
	/**
	 * 分配一个空闲块
	 * 成功则返回分配的空闲块号
	 * 空闲块不够则返回-1
	 * 其他错误返回-2
	 * */
	public int allocateAFreeBlock(Disk disk)
	{
		// 空闲块富余？
		if(freeBlocksGroupsLink.getTopNumber() == FreeBlocksGroupsLink.LAST_GROUP_FLAG && freeBlocksGroupsLink.getNum() == 0)
			return -1;
		// 富余则进行分配
		try {
			int number = 0;
			int nowNum = freeBlocksGroupsLink.getNum();
			if(nowNum > 1)
			{
				// 当前空闲数大于1
				number = freeBlocksGroupsLink.removeBlock();
			}
			else if(nowNum == 1)
			{
				// 当前空闲数等于1
				// 分配当前组最后一空闲块（组头）
				number = freeBlocksGroupsLink.removeBlock();
				// 获取当前组头的磁盘块信息
				Block topBlockInNextGroup = disk.getBlockAtIndex(number);
				// 超级块相应位置清零
				freeBlocksGroupsLink.Clear();
				// 获取其数据块内容并拷贝到超级块相应位置
				int newNum = Block.HEX_TO_INT(topBlockInNextGroup.getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK));
				for(int i=0; i<newNum; ++i)
				{
					int tempNumber = Block.HEX_TO_INT(topBlockInNextGroup.getDataAtIndex((i+1) + FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK));
					freeBlocksGroupsLink.addBlock(tempNumber);
				}
			}
			else
				return -2;
			return number;
		} catch (Exception e) {
			// 其他错误
			e.printStackTrace();
			return -2;
		}
	}
	/**
	 * 回收一个number号的空闲块
	 * 成功则返回1
	 * 错误1则返回-1
	 * 错误2则返回-2
	 * */
	public int reclaimAFreeBlock(int number, Disk disk)
	{
		try {
			int nowNum = freeBlocksGroupsLink.getNum();
			// 获取当前组最大可空闲的块数
			int MAX = 0;
			if(freeBlocksGroupsLink.getTopNumber() == FreeBlocksGroupsLink.LAST_GROUP_FLAG)
			{
				// 如果是最后一个组
				MAX = FreeBlocksGroupsLink.MAX_BLOCK_NUM_IN_GROUP - 1;
			}
			else
			{
				// 如果不是最后一个组
				MAX = FreeBlocksGroupsLink.MAX_BLOCK_NUM_IN_GROUP;
			}
			// 进行回收
			if(MAX != nowNum)
			{
				// 回收前超级块中当前组仍未满
				freeBlocksGroupsLink.addBlock(number);
			}
			else if(MAX == nowNum)
			{
				// 回收前超级块中当前组已满
				// 启动磁盘写，将超级块中的内容写到磁盘中
				disk.writeFreeBlocksGroupsLink(number, freeBlocksGroupsLink, false);
				// 重新设置超级块中的空闲成组链表的内容
				freeBlocksGroupsLink.Clear();
				freeBlocksGroupsLink.setNum(1);
				freeBlocksGroupsLink.addBlock(number);
			}
			else
			{
				return -2;
			}
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
	}
	// 打印超级块中的内容
	public void showSuperBlock()
	{
		System.out.println("-----SuperBlock-----");
		// 内存部分
		System.out.println("(1) Memory");
		System.out.println("  -Number of Free Blocks: "+freeBlockInMemory);
		for(int i=0;i<freeBlockInMemoryMap.length;++i)
		{
			if(i%32==0)
				System.out.print("  ");
			if(freeBlockInMemoryMap[i])
				System.out.print("■ ");
			else
				System.out.print("□ ");
			if((i+1)%32==0)
				System.out.println("");
		}
		System.out.println("  -Number of Free Inodes: "+freeInodeInMemory);
		for(int i=0;i<freeInodeInMemoryMap.length;++i)
		{
			System.out.print("  ");
			for(int j=0;j<INODE_NUM_IN_BLOCK;++j)
			{
				if(freeInodeInMemoryMap[i][j])
					System.out.print("■ ");
				else
					System.out.print("□ ");
			}
			System.out.println("");
		}
		// 磁盘部分
		System.out.println("(2) Disk");
		System.out.println("  -Number of Free Inodes: "+freeInodeInDisk);
		for(int i=0;i<freeInodeInDiskMap.length;++i)
		{
			System.out.print("  ");
			for(int j=0;j<INODE_NUM_IN_BLOCK;++j)
			{
				if(freeInodeInDiskMap[i][j])
					System.out.print("■ ");
				else
					System.out.print("□ ");
			}
			System.out.println("");
		}
		System.out.println("  -First Group in FreeBlocksGroupsLink");
		int freeNum = freeBlocksGroupsLink.getNum();
		int blocksNumber[] = freeBlocksGroupsLink.getAllFreeBlocksInGroup();
		System.out.println("  FreeNum: " + freeNum);
		for(int i=0; i<freeNum; ++i)
		{
			System.out.printf("%5d ", blocksNumber[i]);
			if(((i+1)%16) == 0)
				System.out.println("");
		}
		System.out.println("");
	}
	// 打印超级块中的内存信息
	public void showMemory()
	{
		// 内存部分
		System.out.println("-----Memory-----");
		System.out.println("  -Number of Free Blocks: "+freeBlockInMemory);
		for(int i=0;i<freeBlockInMemoryMap.length;++i)
		{
			if(i%32==0)
				System.out.print("  ");
			if(freeBlockInMemoryMap[i])
				System.out.print("■ ");
			else
				System.out.print("□ ");
			if((i+1)%32==0)
				System.out.println("");
		}
		System.out.println("  -Number of Free Inodes: "+freeInodeInMemory);
		for(int i=0;i<freeInodeInMemoryMap.length;++i)
		{
			System.out.print("  ");
			for(int j=0;j<INODE_NUM_IN_BLOCK;++j)
			{
				if(freeInodeInMemoryMap[i][j])
					System.out.print("■ ");
				else
					System.out.print("□ ");
			}
			System.out.println("");
		}
	}
	// 打印空闲区块链
	public void showFreeBlocksGroupsLink(Disk disk)
	{
		System.out.println("-----Free Blocks in GroupsLink-----");
		int index=0;
		FreeBlocksGroupsLink temp = freeBlocksGroupsLink;
		while(true)
		{
			int freeNum = temp.getNum();
			int topNumber = temp.getTopNumber();
			int blocksNumber[] = temp.getAllFreeBlocksInGroup();
			System.out.printf("(%3d)[%2d] ", index, freeNum);
			for(int i=0; i<freeNum; ++i)
			{
				System.out.printf("%d ", blocksNumber[i]);
			}
			System.out.println("");
			index++;
			if(topNumber==FreeBlocksGroupsLink.LAST_GROUP_FLAG)
				break;
			// 寻找下一组
			Block next = disk.getBlockAtIndex(topNumber);
			String[] dataInNext = next.getStringArrayFromData(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK, 65);
			int newFreeNum = Block.HEX_TO_INT(dataInNext[0]);
			temp.Clear();
			for(int i=1; i<newFreeNum+1; ++i)
				temp.addBlock(Block.HEX_TO_INT(dataInNext[i]));
		}
	}
}