package code.barecomputer;

import code.Computer;
import code.memorymanage.Address;
import code.processmanage.PCB;
import code.memorymanage.MMU;

// ���������
class PC
{
	// ��¼ָ���ַ��ָ��ִ��ʱ�����ı�Ϊ��һ��ָ��ĵ�ַ
	private int pcIndex;
	// ���캯��
	public PC() 
	{
		pcIndex = 0;
	}
	// ���캯��
	public PC(int index)
	{
		pcIndex = index;
	}
	// �������캯��
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
	// ����������
	public void inc()
	{
		pcIndex ++;
	}
}
// ָ��Ĵ���
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
	public int state;			// CPU״̬���ں�̬0/�û�̬1
	private PC pc;
	private IR ir;
	public int Time;	// ��¼CPU��Ч������ʱ��
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
			return "�ں�̬";
		else
			return "�û�̬";
	}
	public void showCPU()
	{
		System.out.println("-----CPU-----");
		System.out.println("  -State: " + STATES[state]);
		System.out.println("  -PC: " + pc.getPCIndex());
		for(int i=0;i<REGISTER_NUM;++i)
			System.out.println("  "+REGISTER_NAMES[i]+": "+registers[i].getData());
		
	}
	// �л�Ϊ�ں�̬
	public void toKernel()
	{
		state = 0;
		Computer.mainGUI.stateText.setText("״̬: �ں�̬");
	}
	// �л�Ϊ�û�̬
	public void toUser()
	{
		state = 1;
		Computer.mainGUI.stateText.setText("״̬: �û�̬");
	}
	// ��PCָ����ڴ��ַ�л��һ��ָ��
	public Instruction getInstruction_PC(Memory memory)
	{
		int codeAddress = pc.getPCIndex();
		int runningIndex = memory.pcbPool.getRunningProcrssIndex();
		Instruction out = memory.pcbPool.pool[runningIndex].codes[codeAddress];
		return out;
	}
	// �ָ��ֳ�
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
	
	// �߳�run
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
					// JCB��������Ҫ����Ϊ���̵�JCB
					if(jcbIndex >= 0)
					{
						toKernel();
						int back = Computer.memory.JcbToNew(jcbIndex);
						if(back==0)
						{
							// ������̳��������������½�̬
							int pcbNewIndex = Computer.memory.checkPCBPoolIsNew();
							// PCB�������½�̬
							if(pcbNewIndex >= 0)
							{
								toKernel();
								Computer.memory.NewToReady(pcbNewIndex);
							}
						}
					}
					int pcbNewIndex = Computer.memory.checkPCBPoolIsNew();
					// PCB�������½�̬
					if(pcbNewIndex >= 0)
					{
						toKernel();
						Computer.memory.NewToReady(pcbNewIndex);
					}
					// JCB����û����Ҫ����Ϊ���̵�JCB
//					else
//					{
//						int pcbNewIndex = Computer.memory.checkPCBPoolIsNew();
//						// PCB�������½�̬
//						if(pcbNewIndex >= 0)
//						{
//							toKernel();
//							Computer.memory.NewToReady(pcbNewIndex);
//						}
//					}
					int pcbRunIndex = Computer.memory.checkPCBPoolIsRunning();
					// PCB ����û������̬
					if(pcbRunIndex == -1)
					{
						int pcbReadyIndex = Computer.memory.checkPCBPoolIsReady();
						// PCB���о���̬
						if(pcbReadyIndex >= 0)
						{
							toKernel();
							Computer.memory.ReadyToRun(pcbReadyIndex, this);
							// �Ը�ת��Ϊ����̬�Ľ�������
							PCBRunning(Computer.memory, pcbReadyIndex);
						}
						// û�о���̬����ת
						else
						{
							String temp = String.format("%d: CPU��ת(������̬)\n", Clock.getClock());
							System.out.print(temp);
							Computer.RunInfo += temp;
							//System.out.println(Clock.getClock() + ": " + "CPU��ת(������̬)");
						}
					}
					// ������̬��ִ��
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
	// ָ��ִ�к���
	public void PCBRunning(Memory memory, int pcbRunIndex)
	{
		String temp;
		// ����Ƿ�ִ���꣬��ִ�������������
		if(pc.getPCIndex() >= memory.pcbPool.pool[pcbRunIndex].getCodeLength())
		{
			// ����ִ�����
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
				
				temp = String.format("%d: %d �Ž���ִ��һ��ָ��\n", Clock.getClock(), memory.pcbPool.pool[pcbRunIndex].getProcessNumber());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				temp = instruction.get(pc.getPCIndex());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				memory.pcbPool.pool[pcbRunIndex].incRunTime();
				// ���������ַ
				int pcbRunNumber = Computer.memory.pcbPool.pool[pcbRunIndex].getProcessNumber();
				int logic = RandomGenerator.getRandomFromPageTable(Computer.memory.pcbPool.pool[pcbRunIndex].getPageTable());
				// ���ʵ�ַ
				if(MMU.isMissPage(Computer.memory.pcbPool, pcbRunNumber, logic))
					// ȱҳ�ж�
					MMU.MissPage(Computer.memory.pcbPool, pcbRunNumber, logic);
				// ��ַ�任������
				Address address = MMU.getAddressFromPageTable(Computer.memory.pcbPool, pcbRunNumber, logic);
				// CPUTime
				Time++;
				
				temp = String.format("\t��PCBIndex: %d, ���ʵ�ַ(%d -> %d)\n", pcbRunIndex, logic, address.get());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				break;
			}
			case Instruction.CALCULATE:
			{
				toUser();
				
				temp = String.format("%d: %d �Ž���ִ��һ��ָ��\n", Clock.getClock(), memory.pcbPool.pool[pcbRunIndex].getProcessNumber());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				temp = instruction.get(pc.getPCIndex());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
//				System.out.println(Clock.getClock() + " : ����" + memory.pcbPool.pool[pcbRunIndex].getProcessNumber() + "ִ��һ��ָ��");
//				instruction.show(pc.getPCIndex());
				memory.pcbPool.pool[pcbRunIndex].incRunTime();
				// ���������ַ
				int pcbRunNumber = Computer.memory.pcbPool.pool[pcbRunIndex].getProcessNumber();
				int logic = RandomGenerator.getRandomFromLogicalSpace();
				// ���ʵ�ַ
				if(MMU.isMissPage(Computer.memory.pcbPool, pcbRunNumber, logic))
					// ȱҳ�ж�
					MMU.MissPage(Computer.memory.pcbPool, pcbRunNumber, logic);
				// ��ַ�任������
				Address address = MMU.getAddressFromPageTable(Computer.memory.pcbPool, pcbRunNumber, logic);
				// CPUTime
				Time++;
				
				temp = String.format("\t��PCBIndex: %d, ���ʵ�ַ(%d -> %d)\n", pcbRunIndex, logic, address.get());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				
				//System.out.printf("\t����ʵ�ַ(%d -> %d)\n", logic, address.get());
				break;
			}
			case Instruction.READ, Instruction.WRITE, Instruction.INPUT, Instruction.OUTPUT, Instruction.PRINTER, Instruction.CAMERA:
			{
				toKernel();
				
				temp = String.format("%d: %d �Ž���ִ��һ��ָ��\n", Clock.getClock(), memory.pcbPool.pool[pcbRunIndex].getProcessNumber());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
				temp = instruction.get(pc.getPCIndex());
				System.out.print(temp);
				Computer.RunInfo += temp;
				
//				System.out.println(Clock.getClock() + " : ���� " + memory.pcbPool.pool[pcbRunIndex].getProcessNumber() + " ִ��һ��ָ��");
//				instruction.show(pc.getPCIndex());
				memory.pcbPool.pool[pcbRunIndex].incRunTime();
				memory.RunToBlock(pcbRunIndex, this, instruction);
				break;
			}
			default:
			{
				System.err.println("������δ֪��ָ������");
				System.exit(-1);
			}
		}
		// �������̬ʱ��Ƭ�Ƿ����Խ����滻
		int timeFullIndex = memory.checkTimeIsFull();
		// ʱ��Ƭ��
		if(timeFullIndex != -1)
		{
			toKernel();
			memory.RunToReady(timeFullIndex, this);
			// ѡȡ�µľ���̬װ��
			int newRunningIndex = memory.checkPCBPoolIsReady();
			memory.ReadyToRun(newRunningIndex, this);
		}
	}
}