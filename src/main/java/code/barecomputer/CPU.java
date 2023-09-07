package code.barecomputer;

import code.Computer;
import code.memorymanage.Address;
import code.processmanage.PCB;
import code.memorymanage.MMU;

// 程序计数器
class PC
{
	// 记录指令地址，指令执行时，即改变为下一条指令的地址
	private int pcIndex;
	// 构造函数
	public PC() 
	{
		pcIndex = 0;
	}
	// 构造函数
	public PC(int index)
	{
		pcIndex = index;
	}
	// 拷贝构造函数
	public PC(PC pc)
	{
		pcIndex = pc.pcIndex;
	}
	public void setPCIndex(int index)
	{
		pcIndex = index;
	}
	public int getPCIndex()
	{
		return pcIndex;
	}
	// 计数器自增
	public void inc()
	{
		pcIndex ++;
	}
}
// 指令寄存器
class IR
{
	private Instruction instruction;
	public IR() 
	{
		instruction = new Instruction();
	}
	public IR(IR copy)
	{
		instruction = copy.instruction;
	}
	public IR(Instruction temp)
	{
		instruction = new Instruction(temp);
	}
	public void setIR(Instruction temp)
	{
		instruction.setInstruction(temp);
	}
	public Instruction getInstruction()
	{
		return instruction;
	}
}

// CPU
public class CPU extends Thread
{
	public static final int REGISTER_NUM = 4;
	public static final String[] REGISTER_NAMES = {"R0", "R1", "R2", "R3", "PC", "IR"};
	public static final String[] STATES = {"Kernel state", "User state"};
	public int state;			// CPU状态，内核态0/用户态1
	private PC pc;
	private IR ir;
	public int Time;	// 记录CPU有效工作的时间
	private Register[] registers;
	public CPU()
	{
		state = 0;
		Time = 0;
		pc = new PC();
		ir = new IR();
		registers = new Register[4];
	}
	public CPU(CPU copy)
	{
		state = copy.state;
		pc = copy.pc;
		ir = copy.ir;
		Time = copy.Time;
		for(int i=0;i<REGISTER_NUM;i++)
			registers[i] = copy.registers[i];
	}
	public String getStateString()
	{
		if(state==0)
			return "内核态";
		else
			return "用户态";
	}
	public void showCPU()
	{
		System.out.println("-----CPU-----");
		System.out.println("  -State: " + STATES[state]);
		System.out.println("  -PC: " + pc.getPCIndex());
		for(int i=0;i<REGISTER_NUM;++i)
			System.out.println("  "+REGISTER_NAMES[i]+": "+registers[i].getData());
		
	}
	// 切换为内核态
	public void toKernel()
	{
		state = 0;
		Computer.mainGUI.stateText.setText("状态: 内核态");
	}
	// 切换为用户态
	public void toUser()
	{
		state = 1;
		Computer.mainGUI.stateText.setText("状态: 用户态");
	}
	// 从PC指向的内存地址中获得一条指令
	public Instruction getInstruction_PC(Memory memory)
	{
		int codeAddress = pc.getPCIndex();
		int runningIndex = memory.pcbPool.getRunningProcrssIndex();
		Instruction out = memory.pcbPool.pool[runningIndex].codes[codeAddress];
		return out;
	}
	// 恢复现场
	public void Recover(PCB pcb)
	{
		int pcIndex = pcb.scene.getPC();
		state = pcb.scene.getState();
		pc.setPCIndex(pcIndex);
		ir.setIR(pcb.scene.getIR());
	}
	// get
	public int getPCIndex()
	{
		return pc.getPCIndex();
	}
	public Instruction getIR()
	{
		return ir.getInstruction();
	}
	
	// 线程run
	public void run()
	{
		try {
			while(true)
			{
				toKernel();
				synchronized (MyLock.ClockIsChanged)
				{
					MyLock.ClockIsChanged.wait();
					
					int jcbIndex = Computer.memory.checkJCBPool();
					// JCB池中有需要创建为进程的JCB
					if(jcbIndex >= 0)
					{
						toKernel();
						int back = Computer.memory.JcbToNew(jcbIndex);
						if(back==0)
						{
							// 如果进程池满，则检查有无新建态
							int pcbNewIndex = Computer.memory.checkPCBPoolIsNew();
							// PCB池中有新建态
							if(pcbNewIndex >= 0)
							{
								toKernel();
								Computer.memory.NewToReady(pcbNewIndex);
							}
						}
					}
					int pcbNewIndex = Computer.memory.checkPCBPoolIsNew();
					// PCB池中有新建态
					if(pcbNewIndex >= 0)
					{
						toKernel();
						Computer.memory.NewToReady(pcbNewIndex);
					}
					// JCB池中没有需要创建为进程的JCB
//					else
//					{
//						int pcbNewIndex = Computer.memory.checkPCBPoolIsNew();
//						// PCB池中有新建态
//						if(pcbNewIndex >= 0)
//						{
//							toKernel();
//							Computer.memory.NewToReady(pcbNewIndex);
//						}
//					}
					int pcbRunIndex = Computer.memory.checkPCBPoolIsRunning();
					// PCB 池中没有运行态
					if(pcbRunIndex == -1)
					{
						int pcbReadyIndex = Computer.memory.checkPCBPoolIsReady();
						// PCB中有就绪态
						if(pcbReadyIndex >= 0)
						{
							toKernel();
							Computer.memory.ReadyToRun(pcbReadyIndex, this);
							// 对刚转化为运行态的进程运行
							PCBRunning(Computer.memory, pcbReadyIndex);
						}
						// 没有就绪态，空转
						else
						{
							String temp = String.format("%d: CPU空转(无运行态)\n", Clock.getClock());
							System.out.print(temp);
							Computer.RunInfo += temp;
							//System.out.println(Clock.getClock() + ": " + "CPU空转(无运行态)");
						}
					}
					// 有运行态则执行
					else
					{
						PCBRunning(Computer.memory, pcbRunIndex);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	// 指令执行函数
	public void PCBRunning(Memory memory, int pcbRunIndex)
	{
		String temp;
		// 检查是否执行完，若执行完则进程消亡
		if(pc.getPCIndex() >= memory.pcbPool.pool[pcbRunIndex].getCodeLength())
		{
			// 进程执行完毕
			memory.ToDead(pcbRunIndex);
			return;
		}
		Instruction instruction = getInstruction_PC(memory);
		ir.setIR(instruction);
		pc.inc();
		switch(instruction.getType())
		{
			case Instruction.VALUE:
			{
				toUser();
				
				temp = String.format("%d: %d 号进程执行一条指令\n", Clock.getClock(), memory.pcbPool.pool[pcbRunIndex].getProcessNumber());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				temp = instruction.get(pc.getPCIndex());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				memory.pcbPool.pool[pcbRunIndex].incRunTime();
				// 产生随机地址
				int pcbRunNumber = Computer.memory.pcbPool.pool[pcbRunIndex].getProcessNumber();
				int logic = RandomGenerator.getRandomFromPageTable(Computer.memory.pcbPool.pool[pcbRunIndex].getPageTable());
				// 访问地址
				if(MMU.isMissPage(Computer.memory.pcbPool, pcbRunNumber, logic))
					// 缺页中断
					MMU.MissPage(Computer.memory.pcbPool, pcbRunNumber, logic);
				// 地址变换并访问
				Address address = MMU.getAddressFromPageTable(Computer.memory.pcbPool, pcbRunNumber, logic);
				// CPUTime
				Time++;
				
				temp = String.format("\t●PCBIndex: %d, 访问地址(%d -> %d)\n", pcbRunIndex, logic, address.get());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				break;
			}
			case Instruction.CALCULATE:
			{
				toUser();
				
				temp = String.format("%d: %d 号进程执行一条指令\n", Clock.getClock(), memory.pcbPool.pool[pcbRunIndex].getProcessNumber());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				temp = instruction.get(pc.getPCIndex());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
//				System.out.println(Clock.getClock() + " : 进程" + memory.pcbPool.pool[pcbRunIndex].getProcessNumber() + "执行一条指令");
//				instruction.show(pc.getPCIndex());
				memory.pcbPool.pool[pcbRunIndex].incRunTime();
				// 产生随机地址
				int pcbRunNumber = Computer.memory.pcbPool.pool[pcbRunIndex].getProcessNumber();
				int logic = RandomGenerator.getRandomFromLogicalSpace();
				// 访问地址
				if(MMU.isMissPage(Computer.memory.pcbPool, pcbRunNumber, logic))
					// 缺页中断
					MMU.MissPage(Computer.memory.pcbPool, pcbRunNumber, logic);
				// 地址变换并访问
				Address address = MMU.getAddressFromPageTable(Computer.memory.pcbPool, pcbRunNumber, logic);
				// CPUTime
				Time++;
				
				temp = String.format("\t●PCBIndex: %d, 访问地址(%d -> %d)\n", pcbRunIndex, logic, address.get());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				
				//System.out.printf("\t●访问地址(%d -> %d)\n", logic, address.get());
				break;
			}
			case Instruction.READ, Instruction.WRITE, Instruction.INPUT, Instruction.OUTPUT, Instruction.PRINTER, Instruction.CAMERA:
			{
				toKernel();
				
				temp = String.format("%d: %d 号进程执行一条指令\n", Clock.getClock(), memory.pcbPool.pool[pcbRunIndex].getProcessNumber());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				temp = instruction.get(pc.getPCIndex());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
//				System.out.println(Clock.getClock() + " : 进程 " + memory.pcbPool.pool[pcbRunIndex].getProcessNumber() + " 执行一条指令");
//				instruction.show(pc.getPCIndex());
				memory.pcbPool.pool[pcbRunIndex].incRunTime();
				memory.RunToBlock(pcbRunIndex, this, instruction);
				break;
			}
			default:
			{
				System.err.println("出现了未知的指令类型");
				System.exit(-1);
			}
		}
		// 检查运行态时间片是否完以进行替换
		int timeFullIndex = memory.checkTimeIsFull();
		// 时间片完
		if(timeFullIndex != -1)
		{
			toKernel();
			memory.RunToReady(timeFullIndex, this);
			// 选取新的就绪态装入
			int newRunningIndex = memory.checkPCBPoolIsReady();
			memory.ReadyToRun(newRunningIndex, this);
		}
	}
}