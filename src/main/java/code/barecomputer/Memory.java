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
	
	// �ڴ��е������
	public GuideBlock guideBlock = new GuideBlock();
	public SuperBlock superBlock = new SuperBlock();
	private Block blocks[];	// ÿ����
	// �ڴ��е��������ݽṹ
	public Inode[] inodes;						// �ڴ��е�Inodeָ�루���ļ���
	public i_InodeTable i_inodeTable;			// i-inodeTable
	public OpenFileTable openFileTable;		// openFileTable
	public JCBPool jcbPool;					// ��ҵ��
	public PCBPool pcbPool;					// ���̳�
	public DeviceTable deviceTable;			// �豸��
	// ��������
	public ProcessBlock fileBlock = new ProcessBlock();
	public ProcessBlock inputBlock = new ProcessBlock();
	public ProcessBlock outputBlock = new ProcessBlock();
	public ProcessBlock printerBlock = new ProcessBlock();
	public ProcessBlock cameraBlock = new ProcessBlock();
	public WaitingBlock printerWaiting = new WaitingBlock();
	// �ڴ��е���Դ
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
		// ��ʼ���ڴ���Դ
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
	 * ����ʱ���ڴ�Ĵ���
	 * */
	public void Initialize(Disk disk)
	{
		// ������װ���ڴ�
		blocks[Memory.GUIDE_BLOCK_INDEX] = disk.getBlockAtIndex(Disk.GUIDE_BLOCK_INDEX);
		guideBlock.loadFromDisk(blocks[Memory.GUIDE_BLOCK_INDEX]);
		// ����input�ļ������ݴ�����ҵ��
		jcbPool.Initialize();
//		jcbPool.Initialize(disk);
//		System.out.println("��������װ���ڴ���");
		System.out.println("��ҵ�Ѿ����سɹ�");
		Computer.openInfo += "��ҵ�Ѿ����سɹ�\n";
		// ������װ���ڴ�
		blocks[Memory.SUPER_BLOCK_INDEX] = disk.getBlockAtIndex(Disk.SUPER_BLOCK_INDEX);
		superBlock.loadFromDisk(blocks[Memory.SUPER_BLOCK_INDEX]);
		System.out.println("��������װ���ڴ���");
		Computer.openInfo += "�������Ѿ�װ���ڴ���\n";
		// i_inode���ʼ��
		i_inodeTable.loadFromDisk(disk, superBlock);
		System.out.println("i_InodeTable�����ɹ�");
		Computer.openInfo += "i_InodeTable�����ɹ�\n";
		// �豸���ʼ��
		deviceTable.Initlize();
		System.out.println("DeviceTable��ʼ���ɹ�");
		Computer.openInfo += "DeviceTable��ʼ���ɹ�\n";
		// ����ϵͳװ���ڴ�
		for(int i=0; i<Disk.OS_BLOCK_NUM; ++i)
			blocks[Memory.OS_BLOCK_START_INDEX+i] = disk.getBlockAtIndex(Disk.OS_BLOCK_INDEX[i]);
		System.out.println("����ϵͳ��װ���ڴ���");
		Computer.openInfo += "����ϵͳ��װ���ڴ���\n";
		// ��������ϵͳ����
		System.out.println("����ϵͳ�����ѱ�����");
		Computer.openInfo += "����ϵͳ�����ѱ�����\n";
		// Ŀ¼Inodeװ���ڴ�
		int[] location = superBlock.getFirstFreeInodeLocationInMemory();		// �ڴ��д�װ��inodeλ��
//		int index = superBlock.Inode_LocationToIndex(location[0], location[1]);	// �ڴ��д�װ��inode��index
		int index = superBlock.allocateAnInodeInMemory();
		if(index==-1)
		{
			System.err.println("����ʱ��һ�η����ڴ��е�inodeʱ���ַ���ʧ�ܵ����");
			System.exit(-1);
		}
		Block block = disk.getBlockAtIndex(2);	// ������Ŀ¼Inode���ڵ������
		String[] inodeDataStrings = block.getStringArrayFromData(Inode.START_INDEX_IN_BLOCK[location[1]], Inode.MAX_SIZE);
		// ����inode
		Inode inode = new Inode();
		inode.doIitialize(inodeDataStrings);
		inodes[index] = inode;	// inodeװ���ڴ���inode����
		// ���̿鿽�����ڴ��
		blocks[INODE_BLOCK_INDEX[location[0]]].setDataFromSringArray(Inode.START_INDEX_IN_BLOCK[location[1]], Inode.MAX_SIZE, inodeDataStrings);
		int inodeNumber = inode.getInodeNumber();
		
		// �ļ��򿪱�������Ŀ¼���ļ����
		openFileTable.addAOpenFile(inodeNumber, 1, index);
		pcbPool.os.processFileTable.add("X", inodeNumber);
		System.out.println("OpenFileTable�����ɹ�");
		Computer.openInfo += "OpenFileTable�����ɹ�\n";
		// �޸���Ŀ¼inode��i_InodeTable�е�isloadMemory����ֵ
		i_inodeTable.loadToMemory(inodeNumber);
		System.out.println("��Ŀ¼�����ص��ڴ���");
		Computer.openInfo += "��Ŀ¼�����ص��ڴ���\n";
	}
	/**
	 * get����
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
	// ����ڴ��е�blockNumber��ĵ�index��
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
		System.err.println("�ڴ���δ�ҵ�"+number+"�ŵ�inode������ǿ�������inode");
		return err_inode;
	}
	
	/**
	 * װ��һ��λ����index���ڴ�Inode���У���Inode
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
	 * ���ڴ��е�indexM������д�ᵽ���̵�indexD��
	 * */
	public void writeToDisk(int indexM, int indexD, Disk disk)
	{
		String data[] = blocks[indexM].getAllData();
		for(int i=0;i<Block.MAX_SIZE;++i)
			disk.setDataAtRow_In_BlockAtIndex(indexD, i, data[i]);
	}
	/**
	 * �������е�indexD������д���ڴ�indexM��
	 * */
	public void writeFromDisk(int indexM, int indexD, Disk disk)
	{
		String data[] = disk.getBlockAtIndex(indexD).getAllData();
		for(int i=0;i<Block.MAX_SIZE;++i)
			blocks[indexM].setDataAtIndex(i, data[i]);
	}
	/**
	 * �ڴ��з���һ�����п�
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
	 * �ڴ��л���һ�����п�
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
	 * ��ʾ�ڴ��еķ������
	 * */
	public void showMemory()
	{
		superBlock.showMemory();
	}
	/**
	 * ���JCBPool
	 * */
	public int checkJCBPool()
	{
		int out = jcbPool.checkReturnIndex();
		return out;
		
	}
	/**
	 * ���PCBPool�Ƿ����½�̬
	 * */
	public int checkPCBPoolIsNew()
	{
		int out = pcbPool.checkIsNewReturnIndex();
		return out;
	}
	/**
	 * ���PCBPool�Ƿ�������̬
	 * */
	public int checkPCBPoolIsRunning()
	{
		int out = pcbPool.checkIsRunningReturnIndex();
		return out;
	}
	/**
	 * ���PCBPool�Ƿ��о���̬
	 * ����ѡ�����ȼ���ߵ�
	 * */
	public int checkPCBPoolIsReady()
	{
		int out = pcbPool.checkIsReadyReturnIndex();
		return out;
	}
	/**
	 * ���PCBPool���Ƿ���ʱ��Ƭ����
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
	// ����CodeInode
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
	 * ���̵���ԭ��
	 * */
	// JCB����Ϊ�½�̬
	public int JcbToNew(int jcbIndex)
	{
		String clock = Clock.getClock() + ": ";
		String tempS = "";
		
//		System.out.println(clock + "��ʼ���� " + Computer.memory.jcbPool.pool.get(jcbIndex).getJobNumber() + " ����ҵ");
		int freeIndexInPbcPool = pcbPool.getFirstFreeIndexInPoolMap();
		if(freeIndexInPbcPool == -1)
		{
			tempS = String.format("%sPCBPool�������޷������µ�PCB\n", clock);
			System.out.print(tempS);
			Computer.RunInfo += tempS;
			//System.out.println(Clock.getClock() + ": " + "PCBPool�������޷������µ�JCB");
			return 0;
		}
		
		tempS = String.format("%s��ʼ���� %d ����ҵ\n", clock, Computer.memory.jcbPool.pool.get(jcbIndex).getJobNumber());
		System.out.print(tempS);
		Computer.RunInfo += tempS;
		
		// �½�PCB
		// �����룬�����ڴ漰������ʼ��
		PCB temp = new PCB();
		temp.createFromJCB(jcbPool.getJCBAtIndex(jcbIndex), freeIndexInPbcPool);
		pcbPool.add(temp);
		
		tempS = String.format("%s%d ��PCB������Ϊ�½�̬\n", clock, temp.getProcessNumber());
		tempS += String.format("\t�������(%s), ������(%s), ���ȼ�(%d)\n", temp.getProcessName(), temp.getCodeLength(), temp.getPriority());
		System.out.print(tempS);
		Computer.RunInfo += tempS;
		
//		System.out.println(clock + temp.getProcessNumber() + " ��PCB������Ϊ�½�̬");
//		System.out.printf("\t�������(%s), ������(%s), ���ȼ�(%d)\n", temp.getProcessName(), temp.getCodeLength(), temp.getPriority());
		// �޸��ڴ�λʾͼ
		allocateIndexBlock(CODE_BLOCK_START_FOR_A_PROCESS[freeIndexInPbcPool]);
		allocateIndexBlock(DATA_BLOCK_START_FOR_A_PROCESS[freeIndexInPbcPool]);
		allocateIndexBlock(DATA_BLOCK_START_FOR_A_PROCESS[freeIndexInPbcPool]+1);
		// ɾ��JCB
		Computer.memory.jcbPool.remove(jcbIndex);
		return 1;
	}
	// PCB�½�̬�任Ϊ����̬
	public void NewToReady(int pcbIndex)
	{
		String clock = Clock.getClock() + ": ";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		
		String temp = String.format("%s%d ��PCB�½�̬ -> ����̬\n", clock, pcbNumber);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.println(clock + pcbNumber + " ��PCB�½�̬ -> ����̬");

		pcbPool.setPcbOnNewToReady(pcbIndex);
	}
	// PCB����̬������̬
	public void ReadyToRun(int pcbIndex, CPU cpu)
	{
		String clock = Clock.getClock() + ": ";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		
		String temp = String.format("%s%d ��PCB����̬ -> ����̬\n", clock, pcbNumber);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.println(clock + pcbNumber + " ��PCB����̬ -> ����̬");
		
		pcbPool.pool[pcbIndex].setState(PCB.RUNNING);
		pcbPool.pool[pcbIndex].setTimeFlak(0);
		// �ָ��ֳ�
		cpu.Recover(pcbPool.pool[pcbIndex]);
	}
	// PCB����̬������̬
	public void RunToReady(int pcbIndex, CPU cpu)
	{
		String clock = Clock.getClock() + ": ";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		
		String temp = String.format("%s%d ��PCB����̬ -> ����̬\n", clock, pcbNumber);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.println(clock + pcbNumber + " ��PCB����̬ -> ����̬");
		
		pcbPool.pool[pcbIndex].setState(PCB.READY);
		pcbPool.pool[pcbIndex].setTimeFlak(0);
		// �����ֳ�
		pcbPool.pool[pcbIndex].scene.set(cpu.getPCIndex(), cpu.state, cpu.getIR());
	}
	// PCB����̬������̬
	public void RunToBlock(int pcbIndex, CPU cpu, Instruction instruction)
	{
		String clock = Clock.getClock() + ": ";
		String tempS = "";
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		pcbPool.pool[pcbIndex].setState(PCB.BLOCK);
		pcbPool.pool[pcbIndex].setTimeFlak(0);
		// �����ֳ�
		pcbPool.pool[pcbIndex].scene.set(cpu.getPCIndex(), cpu.state, cpu.getIR());
		// ������������
		switch(instruction.getType())
		{
			case Instruction.READ, Instruction.WRITE:
			{
				tempS = String.format("%s%d ��PCB����̬ -> ����̬(�ļ���д����)\n", clock, pcbNumber);
				System.out.print(tempS);
				Computer.RunInfo += tempS;
//				System.out.println(clock + pcbNumber + " ��PCB����̬ -> ����̬(�ļ���д����)");
				PcbBlock temp = new PcbBlock(pcbIndex, pcbPool.pool[pcbIndex].getPriority(), Instruction.BLOCK_TIME[instruction.getType()], Clock.getClock());
				fileBlock.add(temp);
				fileNum--;
				// ϵͳ���ļ���
				int fileInodeNumber = instruction.getOperand();
				if(openFileTable.isIn(fileInodeNumber)!=-1)
				{
					// ��ϵͳ�������������һ
					openFileTable.incPNumberForInode(fileInodeNumber);
				}
				else
				{
					// ����ϵͳ���������
					int[] freeLocation = superBlock.getFirstFreeInodeLocationInMemory();
					int freeIndex = SuperBlock.Inode_LocationToIndex(freeLocation[0], freeLocation[1]);
					superBlock.allocateAnInodeInMemory();
					openFileTable.addAOpenFile(fileInodeNumber, 1, freeIndex);
				}
				// ���̴��ļ���
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
				tempS = String.format("%s%d ��PCB����̬ -> ����̬(��Ļ�������)\n", clock, pcbNumber);
				System.out.print(tempS);
				Computer.RunInfo += tempS;
//				System.out.println(clock + pcbNumber + " ��PCB����̬ -> ����̬(��Ļ�������)");
				PcbBlock temp = new PcbBlock(pcbIndex, pcbPool.pool[pcbIndex].getPriority(), instruction.getOperand(), Clock.getClock());
				outputBlock.add(temp);
				screenNum--;
				break;
			}
			case Instruction.INPUT:
			{
				tempS = String.format("%s%d ��PCB����̬ -> ����̬(�����������)\n", clock, pcbNumber);
				System.out.print(tempS);
				Computer.RunInfo += tempS;
//				System.out.println(clock + pcbNumber + " ��PCB����̬ -> ����̬(�����������)");
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
					// ��ӡ����Դ����
					tempS = String.format("%s%d ��PCB����̬ -> ����̬(��ӡ���ȴ�����)\n", clock, pcbNumber);
					System.out.print(tempS);
					Computer.RunInfo += tempS;
//					System.out.println(clock + pcbNumber + " ��PCB����̬ -> ����̬(��ӡ���ȴ�����)");
					printerWaiting.add(temp);
				}
				else
				{
					// ��ӡ����Դ����
					tempS = String.format("%s%d ��PCB����̬ -> ����̬(��ӡ������)\n", clock, pcbNumber);
					System.out.print(tempS);
					Computer.RunInfo += tempS;
//					System.out.println(clock + pcbNumber + " ��PCB����̬ -> ����̬(��ӡ������)");
					printerBlock.add(temp);
					printerNum--;
				}
				break;
			}
			case Instruction.CAMERA:
			{
				tempS = String.format("%s%d ��PCB����̬ -> ����̬(���������)\n", clock, pcbNumber);
				System.out.print(tempS);
				Computer.RunInfo += tempS;
//				System.out.println(clock + pcbNumber + " ��PCB����̬ -> ����̬(���������)");
				PcbBlock temp = new PcbBlock(pcbIndex, pcbPool.pool[pcbIndex].getPriority(), Instruction.BLOCK_TIME[instruction.getType()], Clock.getClock());
				cameraBlock.add(temp);
				cameraNum--;
				break;
			}
		}
	}
	// PCB����̬������̬
	public void BlockToReady(int pcbIndex)
	{
		pcbPool.pool[pcbIndex].setState(PCB.READY);
		pcbPool.pool[pcbIndex].setTimeFlak(0);
	}
	// PCB����
	public void ToDead(int pcbIndex)
	{
		String clock = Clock.getClock() + ": ";
		String temp = "";
		pcbPool.pool[pcbIndex].setFinishTime(Clock.getClock());
		int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
		
		temp = String.format("%s%d �Ž�������̬ -> ��ֹ̬\n", clock, pcbNumber);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.println(clock + pcbNumber + " �Ž�������̬ -> ��ֹ̬");
		
		int start = pcbPool.pool[pcbIndex].getStartTime();
		int finish = pcbPool.pool[pcbIndex].getFinishTime();
		int running = pcbPool.pool[pcbIndex].getRunTime();
		double miu = (double)running / (double)(finish-start);
		
		temp = String.format("\t�����(%d-%d), ����(%d), ��CPU���б���(%.4f)\n", start, finish, running, miu);
		System.out.print(temp);
		Computer.RunInfo += temp;
//		System.out.printf("\t�����(%d-%d), ����(%d), ��Ч��(%4f)\n", start, finish, running, miu);
		
		// PCB����
		pcbPool.number--;
		pcbPool.pool[pcbIndex].setFinishTime(Clock.getClock());
		pcbPool.pool[pcbIndex].setState(PCB.DEAD);
		pcbPool.poolMap[pcbIndex] = false;
		Computer.FINISH_NUM++;
		// ����GUI
		Computer.processGUI.deadText.append("("+Computer.FINISH_NUM+")\n");
		Computer.processGUI.deadText.append(pcbPool.pool[pcbIndex].getDeadString());
		Computer.processGUI.deadText.append(String.format("��CPU���б���: %.4f\n", miu));
		// �ڴ��ͷ�
		reclaimIndexBlock(CODE_BLOCK_START_FOR_A_PROCESS[pcbIndex]);
		reclaimIndexBlock(DATA_BLOCK_START_FOR_A_PROCESS[pcbIndex]);
		reclaimIndexBlock(DATA_BLOCK_START_FOR_A_PROCESS[pcbIndex]+1);
	}
}
