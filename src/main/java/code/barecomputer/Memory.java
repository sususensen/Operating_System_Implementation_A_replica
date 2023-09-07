package code.barecomputer;

import code.Computer;
import code.devicemanage.DeviceTable;
import code.devicemanage.PcbBlock;
import code.devicemanage.ProcessBlock;
import code.devicemanage.WaitingBlock;
import code.filemanage.Inode;
import code.filemanage.OpenFileTable;
import code.filemanage.i_InodeTable;
import code.jobmanage.JCBPool;
import code.processmanage.PCB;
import code.processmanage.PCBPool;

public class Memory 
{
	public final static int MAX_BLOCK_NUM = 64;
	public final static int MAX_BYTE_SIZE = 32768;
	public final static int SYSTEM_BLOCK_NUM = 16;
	public final static int USER_BLOCK_NUM = 48;
	public final static int INODE_BLOCK_NUM = 2;
	public final static int GUIDE_BLOCK_NUM = 1;
	public final static int SUPER_BLOCK_NUM = 1;
	public final static int OS_BLOCK_NUM = 4;
	public final static int OTHER_BLOCK_NUM = 8;
	
	public final static int GUIDE_BLOCK_INDEX = 0;
	public final static int SUPER_BLOCK_INDEX = 1;
	public final static int OS_BLOCK_START_INDEX = 2;
	public final static int[] INODE_BLOCK_INDEX = {6, 7};
	public final static int OTHER_BLOCK_START_INDEX = 8;
	public final static int USER_BLOCK_START_INDEX = 8;
	
	public final static int MAX_PROCESS_NUM = 12;
	public final static int[] CODE_BLOCK_START_FOR_A_PROCESS = {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
	public final static int[] DATA_BLOCK_START_FOR_A_PROCESS = {28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50};
	public final static int[] BUFFER_BLOCK = {52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63};
	
	// 内存中的物理块
	public GuideBlock guideBlock = new GuideBlock();
	public SuperBlock superBlock = new SuperBlock();
	private Block blocks[];	// 每个块
	// 内存中的其他数据结构
	public Inode[] inodes;						// 内存中的Inode指针（打开文件）
	public i_InodeTable i_inodeTable;			// i-inodeTable
	public OpenFileTable openFileTable;		// openFileTable
	public JCBPool jcbPool;					// 作业池
	public PCBPool pcbPool;					// 进程池
	public DeviceTable deviceTable;			// 设备表
	// 阻塞队列
	public ProcessBlock fileBlock = new ProcessBlock();
	public ProcessBlock inputBlock = new ProcessBlock();
	public ProcessBlock outputBlock = new ProcessBlock();
	public ProcessBlock printerBlock = new ProcessBlock();
	public ProcessBlock cameraBlock = new ProcessBlock();
	public WaitingBlock printerWaiting = new WaitingBlock();
	// 内存中的资源
	public int fileNum;
	public int keyboardNum;
	public int screenNum;
	public int printerNum;
	public int earphoneNum;
	public int microphoneNum;
	public int cameraNum;
	
	public Memory() 
	{
		// TODO Auto-generated constructor stub
		blocks = new Block[MAX_BLOCK_NUM];
		for(int i=0;i<MAX_BLOCK_NUM;++i)
			blocks[i] = new Block(true, i);
		inodes = new Inode[Block.INODE_NUM_IN_BLOCK * INODE_BLOCK_NUM];
		for(int i=0;i<Block.INODE_NUM_IN_BLOCK * INODE_BLOCK_NUM;++i)
			inodes[i] = new Inode();
		i_inodeTable = new i_InodeTable();
		openFileTable = new OpenFileTable();
		jcbPool = new JCBPool();
		pcbPool = new PCBPool();
		// 初始化内存资源
		fileNum = 12;
		keyboardNum = 1;
		screenNum = 1;
		printerNum = 3;
		earphoneNum = 1;
		microphoneNum = 1;
		cameraNum = 1;
		deviceTable = new DeviceTable();
	}
	/**
	 * 开机时对内存的处理
	 * */
	public void Initialize(Disk disk)
	{
		// 引导块装入内存
		blocks[Memory.GUIDE_BLOCK_INDEX] = disk.getBlockAtIndex(Disk.GUIDE_BLOCK_INDEX);
		guideBlock.loadFromDisk(blocks[Memory.GUIDE_BLOCK_INDEX]);
		// 根据input文件夹内容创建作业池
		jcbPool.Initialize();
//		jcbPool.Initialize(disk);
//		System.out.println("引导块已装入内存中");
		System.out.println("作业已经加载成功");
		Computer.openInfo += "作业已经加载成功\n";
		// 超级块装入内存
		blocks[Memory.SUPER_BLOCK_INDEX] = disk.getBlockAtIndex(Disk.SUPER_BLOCK_INDEX);
		superBlock.loadFromDisk(blocks[Memory.SUPER_BLOCK_INDEX]);
		System.out.println("超级块已装入内存中");
		Computer.openInfo += "超级块已经装入内存中\n";
		// i_inode表初始化
		i_inodeTable.loadFromDisk(disk, superBlock);
		System.out.println("i_InodeTable创建成功");
		Computer.openInfo += "i_InodeTable创建成功\n";
		// 设备表初始化
		deviceTable.Initlize();
		System.out.println("DeviceTable初始化成功");
		Computer.openInfo += "DeviceTable初始化成功\n";
		// 操作系统装入内存
		for(int i=0; i<Disk.OS_BLOCK_NUM; ++i)
			blocks[Memory.OS_BLOCK_START_INDEX+i] = disk.getBlockAtIndex(Disk.OS_BLOCK_INDEX[i]);
		System.out.println("操作系统已装入内存中");
		Computer.openInfo += "操作系统已装入内存中\n";
		// 创建操作系统进程
		System.out.println("操作系统进程已被创建");
		Computer.openInfo += "操作系统进程已被创建\n";
		// 目录Inode装入内存
		int[] location = superBlock.getFirstFreeInodeLocationInMemory();		// 内存中待装入inode位置
//		int index = superBlock.Inode_LocationToIndex(location[0], location[1]);	// 内存中待装入inode的index
		int index = superBlock.allocateAnInodeInMemory();
		if(index==-1)
		{
			System.err.println("开机时第一次分配内存中的inode时出现分配失败的情况");
			System.exit(-1);
		}
		Block block = disk.getBlockAtIndex(2);	// 磁盘中目录Inode存在的物理块
		String[] inodeDataStrings = block.getStringArrayFromData(Inode.START_INDEX_IN_BLOCK[location[1]], Inode.MAX_SIZE);
		// 生成inode
		Inode inode = new Inode();
		inode.doIitialize(inodeDataStrings);
		inodes[index] = inode;	// inode装入内存中inode队列
		// 磁盘块拷贝到内存块
		blocks[INODE_BLOCK_INDEX[location[0]]].setDataFromSringArray(Inode.START_INDEX_IN_BLOCK[location[1]], Inode.MAX_SIZE, inodeDataStrings);
		int inodeNumber = inode.getInodeNumber();
		
		// 文件打开表增加主目录的文件句柄
		openFileTable.addAOpenFile(inodeNumber, 1, index);
		pcbPool.os.processFileTable.add("X", inodeNumber);
		System.out.println("OpenFileTable创建成功");
		Computer.openInfo += "OpenFileTable创建成功\n";
		// 修改主目录inode在i_InodeTable中的isloadMemory属性值
		i_inodeTable.loadToMemory(inodeNumber);
		System.out.println("主目录被加载到内存中");
		Computer.openInfo += "主目录被加载到内存中\n";
	}
	/**
	 * get方法
	 * */
	public i_InodeTable getI_InodeTable()
	{
		return i_inodeTable;
	}
	public OpenFileTable getOpenFileTable()
	{
		return openFileTable;
	}
	public SuperBlock getSuperBlock()
	{
		return superBlock;
	}
	public JCBPool getJCBPool()
	{
		return jcbPool;
	}
	public PCBPool getPCBPool()
	{
		return pcbPool;
	}
	// 获得内存中第blockNumber块的第index字
	public String getWordFromIndexAtBlock(int blockNumber, int index)
	{
		String word = "";
		Block wordBlock = blocks[blockNumber];
		word = wordBlock.getDataAtIndex(index);
		return word;
	}
	public boolean isInodeNumberInMemory(int number)
	{
		for(int i=0;i<Block.INODE_NUM_IN_BLOCK * INODE_BLOCK_NUM;++i)
		{
			if(number == inodes[i].getInodeNumber())
				return true;
		}
		return false;
	}
	public Inode getInodeWithNumber(int number)
	{
		for(int i=0;i<Block.INODE_NUM_IN_BLOCK * INODE_BLOCK_NUM;++i)
		{
			if(number == inodes[i].getInodeNumber())
				return inodes[i];
		}
		Inode err_inode = new Inode();
		System.err.println("内存中未找到"+number+"号的inode，现在强制输出空inode");
		return err_inode;
	}
	
	/**
	 * 装载一个位置在index（内存Inode块中）的Inode
	 * */
	public void loadAnInode(int index, Inode inode)
	{
		String[] dataInInode = inode.getInodeString();
		int[] location = SuperBlock.Inode_IndexToLocation(index);
		int blcokNumber = location[0];
		int offsetNumber = location[1];
		for(int i=0; i<Inode.MAX_SIZE; ++i)
		{
			int j = i + Inode.START_INDEX_IN_BLOCK[offsetNumber];
			blocks[INODE_BLOCK_INDEX[blcokNumber]].setDataAtIndex(j, dataInInode[i]);
		}
	}
	/**
	 * 把内存中第indexM块内容写会到磁盘第indexD中
	 * */
	public void writeToDisk(int indexM, int indexD, Disk disk)
	{
		String data[] = blocks[indexM].getAllData();
		for(int i=0;i<Block.MAX_SIZE;++i)
			disk.setDataAtRow_In_BlockAtIndex(indexD, i, data[i]);
	}
	/**
	 * 将磁盘中第indexD块内容写到内存indexM中
	 * */
	public void writeFromDisk(int indexM, int indexD, Disk disk)
	{
		String data[] = disk.getBlockAtIndex(indexD).getAllData();
		for(int i=0;i<Block.MAX_SIZE;++i)
			blocks[indexM].setDataAtIndex(i, data[i]);
	}
	/**
	 * 内存中分配一个空闲块
	 * */
	public int allocateAFreeMemoryBlock()
	{
		int number = -1;
		boolean[] map = superBlock.getFreeBlockInMemoryMap();
		int freeNum = superBlock.getFreeBlockInMemory();
		if(freeNum==0)
			return -1;
		for(int i=0;i<MAX_BLOCK_NUM;++i)
		{
			if(map[i]==false)
			{
				number = i;
				map[i] = true;
				superBlock.decFreeBlockInMemory();
				superBlock.setFreeBlockInMemoryMap(map);
				break;
			}
		}
		return number;
	}
	public void allocateIndexBlock(int index)
	{
		superBlock.freeBlockInMemoryMap[index] = true;
		superBlock.freeBlockInMemory--;
	}
	/**
	 * 内存中回收一个空闲块
	 * */
	public void reclaimAFreeMemoryBlock(int number)
	{
		boolean[] map = superBlock.getFreeBlockInMemoryMap();
		map[number] = false;
		superBlock.setFreeBlockInMemoryMap(map);
		superBlock.incFreeBlockInMemory();
	}
	public void reclaimIndexBlock(int index)
	{
		superBlock.freeBlockInMemoryMap[index] = false;
		superBlock.freeBlockInMemory++;
	}
	/**
	 * 显示内存中的分配情况
	 * */
	public void showMemory()
	{
		superBlock.showMemory();
	}
	/**
	 * 检查JCBPool
	 * */
	public int checkJCBPool()
	{
		int out = jcbPool.checkReturnIndex();
		return out;
		
	}
	/**
	 * 检查PCBPool是否有新建态
	 * */
	public int checkPCBPoolIsNew()
	{
		int out = pcbPool.checkIsNewReturnIndex();
		return out;
	}
	/**
	 * 检查PCBPool是否有运行态
	 * */
	public int checkPCBPoolIsRunning()
	{
		int out = pcbPool.checkIsRunningReturnIndex();
		return out;
	}
	/**
	 * 检查PCBPool是否有就绪态
	 * 总是选择优先级最高的
	 * */
	public int checkPCBPoolIsReady()
	{
		int out = pcbPool.checkIsReadyReturnIndex();
		return out;
	}
	/**
	 * 检查PCBPool中是否有时间片满的
	 * */
	public int checkTimeIsFull()
	{
		int runningIndex = checkPCBPoolIsRunning();
		if(runningIndex == -1)
			return -1;
		if(pcbPool.pool[runningIndex].getTimeFlak() == PCB.MAX_TIME_FLAK)
			return runningIndex;
		return -1;
	}
	// 加载CodeInode
	public void loadCodeInode(int pcbNumber, int inodeNumber, Disk disk)
	{
		Inode inode = new Inode();
		int[] location = SuperBlock.Inode_IndexToLocation(inodeNumber);
		int blockNumber = location[0] + Disk.INODE_BLOCK_START_INDEX;
		int offset = Inode.START_INDEX_IN_BLOCK[location[1]];
		String[] inodeString = disk.getBlockAtIndex(blockNumber).getStringArrayFromData(offset, Inode.MAX_SIZE);
		inode.doIitialize(inodeString);
	}
	/**
	 * 进程调度原语
	 * */
	// JCB创建为新建态
	public int JcbToNew(int jcbIndex)
	{
		String clock = Clock.getClock() + ": ";
		String tempS = "";
		
//		System.out.println(clock + "开始运行 " + Computer.memory.jcbPool.pool.get(jcbIndex).getJobNumber() + " 号作业");
		int freeIndexInPbcPool = pcbPool.getFirstFreeIndexInPoolMap();
		if(freeIndexInPbcPool == -1)
		{
			tempS = String.format("%sPCBPool已满，无法创建新的PCB\n", clock);
			System.out.print(tempS);
			Computer.RunInfo += tempS;
			//System.out.println(Clock.getClock() + ": " + "PCBPool已满，无法创建新的JCB");
			return 0;
		}
		
		tempS = String.format("%s开始运行 %d 号作业\n", clock, Computer.memory.jcbPool.pool.get(jcbIndex).getJobNumber());
		System.out.print(tempS);
		Computer.RunInfo += tempS;
		
		// 新建PCB
		// 读代码，分配内存及其它初始化
		PCB temp = new PCB();
		temp.createFromJCB(jcbPool.getJCBAtIndex(jcbIndex), freeIndexInPbcPool);
		pcbPool.add(temp);
		
		tempS = String.format("%s%d 号PCB被创建为新建态\n", clock, temp.getProcessNumber());
		tempS += String.format("\t●进程名(%s), 代码数(%s), 优先级(%d)\n", temp.getProcessName(), temp.getCodeLength(), temp.getPriority());
		System.out.print(tempS);
		Computer.RunInfo += tempS;
		
//		System.out.println(clock + temp.getProcessNumber() + " 号PCB被创建为新建态");
//		System.out.printf("\t●进程名(%s), 代码数(%s), 优先级(%d)\n", temp.getProcessName(), temp.getCodeLength(), temp.getPriority());
		// 修改内存位示图
		allocateIndexBlock(CODE_BLOCK_START_FOR_A_PROCESS[freeIndexInPbcPool]);
		allocateIndexBlock(DATA_BLOCK_START_FOR_A_PROCESS[freeIndexInPbcPool]);
		allocateIndexBlock(DATA_BLOCK_START_FOR_A_PROCESS[freeIndexInPbcPool]+1);
		// 删除JCB
		Computer.memory.jcbPool.remove(jcbIndex);
		return 1;
	}
	// PCB新建态变换为就绪态
	public void NewToReady(int pcbIndex)
	{
		String clock = Clock.getClock() + ": ";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		
		String temp = String.format("%s%d 号PCB新建态 -> 就绪态\n", clock, pcbNumber);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.println(clock + pcbNumber + " 号PCB新建态 -> 就绪态");

		pcbPool.setPcbOnNewToReady(pcbIndex);
	}
	// PCB就绪态到运行态
	public void ReadyToRun(int pcbIndex, CPU cpu)
	{
		String clock = Clock.getClock() + ": ";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		
		String temp = String.format("%s%d 号PCB就绪态 -> 运行态\n", clock, pcbNumber);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.println(clock + pcbNumber + " 号PCB就绪态 -> 运行态");
		
		pcbPool.pool[pcbIndex].setState(PCB.RUNNING);
		pcbPool.pool[pcbIndex].setTimeFlak(0);
		// 恢复现场
		cpu.Recover(pcbPool.pool[pcbIndex]);
	}
	// PCB运行态到就绪态
	public void RunToReady(int pcbIndex, CPU cpu)
	{
		String clock = Clock.getClock() + ": ";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		
		String temp = String.format("%s%d 号PCB运行态 -> 就绪态\n", clock, pcbNumber);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.println(clock + pcbNumber + " 号PCB运行态 -> 就绪态");
		
		pcbPool.pool[pcbIndex].setState(PCB.READY);
		pcbPool.pool[pcbIndex].setTimeFlak(0);
		// 保护现场
		pcbPool.pool[pcbIndex].scene.set(cpu.getPCIndex(), cpu.state, cpu.getIR());
	}
	// PCB运行态到阻塞态
	public void RunToBlock(int pcbIndex, CPU cpu, Instruction instruction)
	{
		String clock = Clock.getClock() + ": ";
		String tempS = "";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		pcbPool.pool[pcbIndex].setState(PCB.BLOCK);
		pcbPool.pool[pcbIndex].setTimeFlak(0);
		// 保护现场
		pcbPool.pool[pcbIndex].scene.set(cpu.getPCIndex(), cpu.state, cpu.getIR());
		// 进入阻塞队列
		switch(instruction.getType())
		{
			case Instruction.READ, Instruction.WRITE:
			{
				tempS = String.format("%s%d 号PCB运行态 -> 阻塞态(文件读写队列)\n", clock, pcbNumber);
				System.out.print(tempS);
				Computer.RunInfo += tempS;
//				System.out.println(clock + pcbNumber + " 号PCB运行态 -> 阻塞态(文件读写队列)");
				PcbBlock temp = new PcbBlock(pcbIndex, pcbPool.pool[pcbIndex].getPriority(), Instruction.BLOCK_TIME[instruction.getType()], Clock.getClock());
				fileBlock.add(temp);
				fileNum--;
				// 系统打开文件表
				int fileInodeNumber = instruction.getOperand();
				if(openFileTable.isIn(fileInodeNumber)!=-1)
				{
					// 在系统表中则进程数加一
					openFileTable.incPNumberForInode(fileInodeNumber);
				}
				else
				{
					// 不在系统表中则加入
					int[] freeLocation = superBlock.getFirstFreeInodeLocationInMemory();
					int freeIndex = SuperBlock.Inode_LocationToIndex(freeLocation[0], freeLocation[1]);
					superBlock.allocateAnInodeInMemory();
					openFileTable.addAOpenFile(fileInodeNumber, 1, freeIndex);
				}
				// 进程打开文件表
				if(instruction.getType() == Instruction.READ)
					pcbPool.pool[pcbIndex].processFileTable.add("R", fileInodeNumber);
				else
				{
					pcbPool.pool[pcbIndex].processFileTable.add("W", fileInodeNumber);
					openFileTable.modify(fileInodeNumber);
				}
				break;
			}
			case Instruction.OUTPUT:
			{
				tempS = String.format("%s%d 号PCB运行态 -> 阻塞态(屏幕输出队列)\n", clock, pcbNumber);
				System.out.print(tempS);
				Computer.RunInfo += tempS;
//				System.out.println(clock + pcbNumber + " 号PCB运行态 -> 阻塞态(屏幕输出队列)");
				PcbBlock temp = new PcbBlock(pcbIndex, pcbPool.pool[pcbIndex].getPriority(), instruction.getOperand(), Clock.getClock());
				outputBlock.add(temp);
				screenNum--;
				break;
			}
			case Instruction.INPUT:
			{
				tempS = String.format("%s%d 号PCB运行态 -> 阻塞态(键盘输入队列)\n", clock, pcbNumber);
				System.out.print(tempS);
				Computer.RunInfo += tempS;
//				System.out.println(clock + pcbNumber + " 号PCB运行态 -> 阻塞态(键盘输入队列)");
				PcbBlock temp = new PcbBlock(pcbIndex, pcbPool.pool[pcbIndex].getPriority(), instruction.getOperand(), Clock.getClock());
				inputBlock.add(temp);
				keyboardNum--;
				break;
			}
			case Instruction.PRINTER:
			{
				PcbBlock temp = new PcbBlock(pcbIndex, pcbPool.pool[pcbIndex].getPriority(), Instruction.BLOCK_TIME[instruction.getType()], Clock.getClock());
				if(printerNum <= 0)
				{
					// 打印机资源不足
					tempS = String.format("%s%d 号PCB运行态 -> 阻塞态(打印机等待队列)\n", clock, pcbNumber);
					System.out.print(tempS);
					Computer.RunInfo += tempS;
//					System.out.println(clock + pcbNumber + " 号PCB运行态 -> 阻塞态(打印机等待队列)");
					printerWaiting.add(temp);
				}
				else
				{
					// 打印机资源充足
					tempS = String.format("%s%d 号PCB运行态 -> 阻塞态(打印机队列)\n", clock, pcbNumber);
					System.out.print(tempS);
					Computer.RunInfo += tempS;
//					System.out.println(clock + pcbNumber + " 号PCB运行态 -> 阻塞态(打印机队列)");
					printerBlock.add(temp);
					printerNum--;
				}
				break;
			}
			case Instruction.CAMERA:
			{
				tempS = String.format("%s%d 号PCB运行态 -> 阻塞态(摄像机队列)\n", clock, pcbNumber);
				System.out.print(tempS);
				Computer.RunInfo += tempS;
//				System.out.println(clock + pcbNumber + " 号PCB运行态 -> 阻塞态(摄像机队列)");
				PcbBlock temp = new PcbBlock(pcbIndex, pcbPool.pool[pcbIndex].getPriority(), Instruction.BLOCK_TIME[instruction.getType()], Clock.getClock());
				cameraBlock.add(temp);
				cameraNum--;
				break;
			}
		}
	}
	// PCB阻塞态到就绪态
	public void BlockToReady(int pcbIndex)
	{
		pcbPool.pool[pcbIndex].setState(PCB.READY);
		pcbPool.pool[pcbIndex].setTimeFlak(0);
	}
	// PCB消亡
	public void ToDead(int pcbIndex)
	{
		String clock = Clock.getClock() + ": ";
		String temp = "";
		pcbPool.pool[pcbIndex].setFinishTime(Clock.getClock());
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		
		temp = String.format("%s%d 号进程运行态 -> 终止态\n", clock, pcbNumber);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.println(clock + pcbNumber + " 号进程运行态 -> 终止态");
		
		int start = pcbPool.pool[pcbIndex].getStartTime();
		int finish = pcbPool.pool[pcbIndex].getFinishTime();
		int running = pcbPool.pool[pcbIndex].getRunTime();
		double miu = (double)running / (double)(finish-start);
		
		temp = String.format("\t●持续(%d-%d), 运行(%d), 在CPU运行比率(%.4f)\n", start, finish, running, miu);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.printf("\t●持续(%d-%d), 运行(%d), 有效比(%4f)\n", start, finish, running, miu);
		
		// PCB消亡
		pcbPool.number--;
		pcbPool.pool[pcbIndex].setFinishTime(Clock.getClock());
		pcbPool.pool[pcbIndex].setState(PCB.DEAD);
		pcbPool.poolMap[pcbIndex] = false;
		Computer.FINISH_NUM++;
		// 更新GUI
		Computer.processGUI.deadText.append("("+Computer.FINISH_NUM+")\n");
		Computer.processGUI.deadText.append(pcbPool.pool[pcbIndex].getDeadString());
		Computer.processGUI.deadText.append(String.format("在CPU运行比率: %.4f\n", miu));
		// 内存释放
		reclaimIndexBlock(CODE_BLOCK_START_FOR_A_PROCESS[pcbIndex]);
		reclaimIndexBlock(DATA_BLOCK_START_FOR_A_PROCESS[pcbIndex]);
		reclaimIndexBlock(DATA_BLOCK_START_FOR_A_PROCESS[pcbIndex]+1);
	}
}
