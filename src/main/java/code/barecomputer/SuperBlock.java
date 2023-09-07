package code.barecomputer;

import code.filemanage.FreeBlocksGroupsLink;

public class SuperBlock extends Block
{
	// �ڴ�
	public int freeBlockInMemory;				// �ڴ��п��е��������
	public boolean[] freeBlockInMemoryMap;		// �ڴ�������λʾͼ��false��ʾ���У�true��ʾռ��
	public int freeInodeInMemory;				// �ڴ���inode�Ŀ�����
	public boolean[][] freeInodeInMemoryMap;	// �ڴ���inode��λʾͼ
	
	// Ӳ��
	public int freeInodeInDisk;				// Ӳ����inode�Ŀ�����
	public boolean[][] freeInodeInDiskMap;		// Ӳ���п���inode��λʾͼ
	public FreeBlocksGroupsLink freeBlocksGroupsLink;	// ����������
	
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
	
	// ��õ�һ�������ڴ���index
	public int getFistFreeBlockIndexInMemory()
	{
		for(int i=0; i<Memory.MAX_BLOCK_NUM; ++i)
		{
			if(!freeBlockInMemoryMap[i])
				return i;
		}
		return -1;
	}
	// ��õ�һ���ڴ��п���Inodeλ�õ�location
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
	// ��õ�һ�������п���Inodeλ�õ�location
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
	// ��ÿ�����
	public int getFreeBlockInMemory()
	{
		return freeBlockInMemory;
	}
	// ���λʾͼ
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
	// ���ÿ�����
	public void incFreeBlockInMemory()
	{
		freeBlockInMemory++;
	}
	public void decFreeBlockInMemory()
	{
		freeBlockInMemory--;
	}
	// ����λʾͼ
	public void setFreeBlockInMemoryMap(boolean[] copy)
	{
		for(int i=0;i<Memory.MAX_BLOCK_NUM;++i)
			freeBlockInMemoryMap[i] = copy[i];
	}
	
	/**
	 * ����һ���ڴ��е�inode
	 * ������ɹ������ط����inode������λ��
	 * ������ܾ�������-1
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
					// ��ȡ�����Inodeλ�ú�
					index = Inode_LocationToIndex(i, j);
					// �޸�Inodeλʾͼ�Լ�ʣ�������
					freeInodeInMemoryMap[i][j] = true;
					freeInodeInMemory--;
					return index;
				}
			}
		}
		return -1;
	}
	/**
	 * ����һ���ڴ��е�inode
	 * ��inode���ڴ�Inode����λ��Ϊindex
	 * */
	public void reclaimAnInodeInMemory(int index)
	{
		int[] location = Inode_IndexToLocation(index);
		// �޸�λʾͼ
		freeInodeInMemoryMap[location[0]][location[1]] = false;
		// �޸�ʣ�������
		freeInodeInMemory++;
	}
	/**
	 * ����ԭ���漰��λʾͼת��
	 * */
	// ����indexԤ��λ�õĽ��̿ռ䣨2�飩
	public void allocatePCBMemoryAtIndex(int index)
	{
		int codeBlockIndex = Memory.CODE_BLOCK_START_FOR_A_PROCESS[index];
		int fileBlockIndex = Memory.DATA_BLOCK_START_FOR_A_PROCESS[index];
		// ������-3
		freeBlockInMemory -= 3;
		// �޸�λʾͼ
		freeBlockInMemoryMap[codeBlockIndex] = true;
		freeBlockInMemoryMap[fileBlockIndex] = true;
		freeBlockInMemoryMap[fileBlockIndex+1] = true;
	}
	// ����indexԤ��λ�õĽ��̿ռ䣨2�飩
	public void reclaimPCBMemoryAtIndex(int index)
	{
		int codeBlockIndex = Memory.CODE_BLOCK_START_FOR_A_PROCESS[index];
		int fileBlockIndex = Memory.DATA_BLOCK_START_FOR_A_PROCESS[index];
		// ������-3
		freeBlockInMemory += 3;
		// �޸�λʾͼ
		freeBlockInMemoryMap[codeBlockIndex] = false;
		freeBlockInMemoryMap[fileBlockIndex] = false;
		freeBlockInMemoryMap[fileBlockIndex+1] = false;
	}
	/**
	 * �������е�ʮ����������
	 * ת��Ϊ������������
	 * */
 	public void loadFromDisk(Block block)
	{
		// System.out.println("������װ���ڴ���...");
		// �ڴ��еĿ������
		freeBlockInMemory = Block.HEX_TO_INT(block.getDataAtIndex(0));
		// �����ڴ������λʾͼ
		char[] binMemoryBlockMap = Block.getBinFromDatasInBlock(1, 4, block).toCharArray();
		for(int i=0; i<binMemoryBlockMap.length; ++i)
		{
			if(binMemoryBlockMap[i] == '1')
				freeBlockInMemoryMap[i] = true;
			else if(binMemoryBlockMap[i] == '0')
				freeBlockInMemoryMap[i] = false;
			else
			{
				System.err.print("���ش���ת������ʱ����δ֪����");
				System.exit(-1);
			}
		}
		// �ڴ���inode������
		freeInodeInMemory = Block.HEX_TO_INT(block.getDataAtIndex(5));
		// �ڴ���Inodeλʾͼ
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
				System.err.print("���ش���ת������ʱ����δ֪����");
				System.exit(-1);
			}
		}
		// Ӳ����Inode������
		freeInodeInDisk = Block.HEX_TO_INT(block.getDataAtIndex(7));
		// Ӳ���п���Inodeλʾͼ
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
				System.err.print("���ش���ת������ʱ����δ֪����");
				System.exit(-1);
			}
		}
		// ��һ�����������
		int freeNum = Block.HEX_TO_INT(block.getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK));
		for(int i=0; i<freeNum; ++i)
		{
			int index = FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK + i + 1;
			int number = Block.HEX_TO_INT(block.getDataAtIndex(index));
			freeBlocksGroupsLink.addBlock(number);
		}
		// System.out.println("��������ͬ�����ڴ�");
	}
	/**
	 * ���ڽ�inode��index��location�໥ת��
	 * ����λʾͼ
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
			System.err.print("���ش���һ���м�¼Inode������8��");
			System.exit(-1);
		}
		int out = x*Block.INODE_NUM_IN_BLOCK + y;
		return out;
	}
	/**
	 * ����һ�����п�
	 * �ɹ��򷵻ط���Ŀ��п��
	 * ���п鲻���򷵻�-1
	 * �������󷵻�-2
	 * */
	public int allocateAFreeBlock(Disk disk)
	{
		// ���п鸻�ࣿ
		if(freeBlocksGroupsLink.getTopNumber() == FreeBlocksGroupsLink.LAST_GROUP_FLAG && freeBlocksGroupsLink.getNum() == 0)
			return -1;
		// ��������з���
		try {
			int number = 0;
			int nowNum = freeBlocksGroupsLink.getNum();
			if(nowNum > 1)
			{
				// ��ǰ����������1
				number = freeBlocksGroupsLink.removeBlock();
			}
			else if(nowNum == 1)
			{
				// ��ǰ����������1
				// ���䵱ǰ�����һ���п飨��ͷ��
				number = freeBlocksGroupsLink.removeBlock();
				// ��ȡ��ǰ��ͷ�Ĵ��̿���Ϣ
				Block topBlockInNextGroup = disk.getBlockAtIndex(number);
				// ��������Ӧλ������
				freeBlocksGroupsLink.Clear();
				// ��ȡ�����ݿ����ݲ���������������Ӧλ��
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
			// ��������
			e.printStackTrace();
			return -2;
		}
	}
	/**
	 * ����һ��number�ŵĿ��п�
	 * �ɹ��򷵻�1
	 * ����1�򷵻�-1
	 * ����2�򷵻�-2
	 * */
	public int reclaimAFreeBlock(int number, Disk disk)
	{
		try {
			int nowNum = freeBlocksGroupsLink.getNum();
			// ��ȡ��ǰ�����ɿ��еĿ���
			int MAX = 0;
			if(freeBlocksGroupsLink.getTopNumber() == FreeBlocksGroupsLink.LAST_GROUP_FLAG)
			{
				// ��������һ����
				MAX = FreeBlocksGroupsLink.MAX_BLOCK_NUM_IN_GROUP - 1;
			}
			else
			{
				// ����������һ����
				MAX = FreeBlocksGroupsLink.MAX_BLOCK_NUM_IN_GROUP;
			}
			// ���л���
			if(MAX != nowNum)
			{
				// ����ǰ�������е�ǰ����δ��
				freeBlocksGroupsLink.addBlock(number);
			}
			else if(MAX == nowNum)
			{
				// ����ǰ�������е�ǰ������
				// ��������д�����������е�����д��������
				disk.writeFreeBlocksGroupsLink(number, freeBlocksGroupsLink, false);
				// �������ó������еĿ��г������������
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
	// ��ӡ�������е�����
	public void showSuperBlock()
	{
		System.out.println("-----SuperBlock-----");
		// �ڴ沿��
		System.out.println("(1) Memory");
		System.out.println("  -Number of Free Blocks: "+freeBlockInMemory);
		for(int i=0;i<freeBlockInMemoryMap.length;++i)
		{
			if(i%32==0)
				System.out.print("  ");
			if(freeBlockInMemoryMap[i])
				System.out.print("�� ");
			else
				System.out.print("�� ");
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
					System.out.print("�� ");
				else
					System.out.print("�� ");
			}
			System.out.println("");
		}
		// ���̲���
		System.out.println("(2) Disk");
		System.out.println("  -Number of Free Inodes: "+freeInodeInDisk);
		for(int i=0;i<freeInodeInDiskMap.length;++i)
		{
			System.out.print("  ");
			for(int j=0;j<INODE_NUM_IN_BLOCK;++j)
			{
				if(freeInodeInDiskMap[i][j])
					System.out.print("�� ");
				else
					System.out.print("�� ");
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
	// ��ӡ�������е��ڴ���Ϣ
	public void showMemory()
	{
		// �ڴ沿��
		System.out.println("-----Memory-----");
		System.out.println("  -Number of Free Blocks: "+freeBlockInMemory);
		for(int i=0;i<freeBlockInMemoryMap.length;++i)
		{
			if(i%32==0)
				System.out.print("  ");
			if(freeBlockInMemoryMap[i])
				System.out.print("�� ");
			else
				System.out.print("�� ");
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
					System.out.print("�� ");
				else
					System.out.print("�� ");
			}
			System.out.println("");
		}
	}
	// ��ӡ����������
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
			// Ѱ����һ��
			Block next = disk.getBlockAtIndex(topNumber);
			String[] dataInNext = next.getStringArrayFromData(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK, 65);
			int newFreeNum = Block.HEX_TO_INT(dataInNext[0]);
			temp.Clear();
			for(int i=1; i<newFreeNum+1; ++i)
				temp.addBlock(Block.HEX_TO_INT(dataInNext[i]));
		}
	}
}