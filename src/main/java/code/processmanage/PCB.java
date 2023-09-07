package code.processmanage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import code.barecomputer.Clock;
import code.barecomputer.Instruction;
import code.barecomputer.Scene;
import code.filemanage.ProcessFileTable;
import code.jobmanage.JCB;
import code.memorymanage.PageTable;

public class PCB
{
	public static final int NEW = 0;
	public static final int READY = 1;
	public static final int RUNNING = 2;
	public static final int BLOCK = 3;
	public static final int DEAD = 4;
	public static final String[] STATE_NAMES = {"new", "ready", "running", "block", "dead"};
	public static final int MAX_TIME_FLAK = 4;
	// ʱ��
	private int startTime;		// ��ʼʱ��
	private int runTime;		// ����ʱ��
	private int finishTime;		// ����ʱ��
	// ��������
	private int processNumber;	// ���̺�
	private String processName;	// ������
	private int priority;		// ���ȼ�
	private int state;			// ����״̬
	private int codeLength;		// ���볤�ȣ��У�
	public Instruction[] codes;	// ���뼯
//	private int codeFileBeginBlockInDisk;	// �����ļ��ڴ����е�������
//	private int codeFileBlockInMemory;		// �����ļ����ڴ��е������ţ�-1��ʾδװ�
//	private int codeInodeNumber;			// �����ļ���inode��
	// ��������
	private int timeFlak;		// ��ʹ��ʱ��Ƭ��
	public PageTable pageTable;				// ���̵�ҳ��
	public ProcessFileTable processFileTable;	// �����ļ��򿪱�
	public Scene scene;		// �ֳ�
	
	public String getString()
	{
		String out = "";
		out += String.format("��ʼʱ��: %d\n", startTime);
		out += String.format("����ʱ��: %d\n", runTime);
		out += String.format("���̺�: %d\n", processNumber);
		out += String.format("������: %s\n", processName);
		out += String.format("���ȼ�: %d\n", priority);
		out += String.format("���볤��: %d\n", codeLength);
		out += "ҳ��:\n";
		out += pageTable.getString();
		return out;
	}
	public String getDeadString()
	{
		String out = "";
		out += String.format("��ʼʱ��: %d\n", startTime);
		out += String.format("����ʱ��: %d\n", runTime);
		out += String.format("����ʱ��: %d\n", finishTime);
		out += String.format("���̺�: %d\n", processNumber);
		out += String.format("������: %s\n", processName);
		return out;
	}
	public String getBlockString()
	{
		String out = "";
		out += String.format("��ʼʱ��: %d\n", startTime);
		out += String.format("����ʱ��: %d\n", runTime);
		out += String.format("���̺�: %d\n", processNumber);
		out += String.format("������: %s\n", processName);
		out += String.format("���ȼ�: %d\n", priority);
		out += String.format("���볤��: %d\n", codeLength);
		out += "ҳ��:\n";
		out += pageTable.getString();
		out += "�ֳ�:\n";
		out += scene.getString();
		return out;
	}
	
	public PCB() 
	{
		startTime = -1;
		runTime = -1;
		finishTime = -1;
		processNumber = -1;
		processName = "noName";
		priority = 0;
		state = -1;
		codeLength = -1;
		timeFlak = -1;
		codes = null;
		
		pageTable = new PageTable();
		processFileTable = new ProcessFileTable();
		scene = new Scene();
	}
	public PCB(PCB copy)
	{
		startTime = copy.startTime;
		runTime = copy.runTime;
		finishTime = copy.finishTime;
		processNumber = copy.processNumber;
		processName = copy.processName;
		priority = copy.priority;
		state = copy.state;
		codeLength = copy.codeLength;
		timeFlak = copy.timeFlak;
		codes = new Instruction[codeLength];
		for(int i=0;i<codeLength;++i)
		{
			codes[i] = copy.codes[i];
		}
		
		pageTable = copy.pageTable;
		processFileTable = copy.processFileTable;
		scene = copy.scene;
	}
	// ��JCB����һ��PCB������д������Ϣ������������Դ��������תΪ�½�̬��
	public void createFromJCB(JCB jcb, int pcbIndex)
	{
		int time = Clock.getClock();
		startTime = time;
		jcb.setStartTime(time);
		state = 0;
		processNumber = jcb.getJobNumber();
		processName = jcb.getJobNmae();
		codeLength = jcb.getCodeLength();
		timeFlak = 0;
		priority = jcb.getPriority();
		codes = new Instruction[codeLength];
		// ��ȡ����
		String Root = System.getProperty("user.dir");
		String readPath = Root + File.separator + "input" + File.separator + jcb.getJobNumber() + ".txt";
		File readFile = new File(readPath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(readFile));
			String lineText = br.readLine();	// ��ȥ��һ��
			int index = 0;
			while((lineText = br.readLine()) != null)
			{
				Instruction temp = new Instruction();
				temp.make(lineText);
				codes[index] = temp;
				index++;
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��ʼ��ҳ��
		pageTable.Initlize(pcbIndex);
		// ��ʼ���ֳ���Ϣ
		scene.Initlize();
	}
	
	// ʱ��Ƭ��һ
	public void incTimeFlak()
	{
		timeFlak++;
	}
	// ����ʱ���һ
	public void incRunTime()
	{
		runTime++;
	}
	// get && set
	public int getStartTime() 
	{
		return startTime;
	}
	public void setStartTime(int startTime) 
	{
		this.startTime = startTime;
	}
	public int getRunTime() 
	{
		return runTime;
	}
	public void setRunTime(int runTime) 
	{
		this.runTime = runTime;
	}
	public int getFinishTime() 
	{
		return finishTime;
	}
	public void setFinishTime(int finishTime) 
	{
		this.finishTime = finishTime;
	}
	public int getProcessNumber() 
	{
		return processNumber;
	}
	public void setProcessNumber(int processNumber)
	{
		this.processNumber = processNumber;
	}
	public String getProcessName() 
	{
		return processName;
	}
	public void setProcessName(String processName)
	{
		this.processName = processName;
	}
	public int getPriority() 
	{
		return priority;
	}
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	public int getState() 
	{
		return state;
	}
	public void setState(int state)
	{
		this.state = state;
	}
	public int getCodeLength()
	{
		return codeLength;
	}
	public void setCodeLength(int codeLength)
	{
		this.codeLength = codeLength;
	}
//	public int getCodeFileBeginBlockInDisk() 
//	{
//		return codeFileBeginBlockInDisk;
//	}
//	public void setCodeFileBeginBlockInDisk(int codeFileBeginBlockInDisk)
//	{
//		this.codeFileBeginBlockInDisk = codeFileBeginBlockInDisk;
//	}
//	public int getCodeFileBlockInMemory() 
//	{
//		return codeFileBlockInMemory;
//	}
//	public void setCodeFileBlockInMemory(int codeFileBlockInMemory) 
//	{
//		this.codeFileBlockInMemory = codeFileBlockInMemory;
//	}
	public int getTimeFlak()
	{
		return timeFlak;
	}
	public void setTimeFlak(int timeFlak)
	{
		this.timeFlak = timeFlak;
	}
	public ProcessFileTable getProcessFileTable()
	{
		return processFileTable;
	}
	public void setProcessFileTable(ProcessFileTable processFileTable)
	{
		this.processFileTable = processFileTable;
	}
	public Scene getScene()
	{
		return scene;
	}
	public void setScene(Scene scene) 
	{
		this.scene = scene;
	}
	public PageTable getPageTable()
	{
		return pageTable;
	}
//	public int getCodeInodeNumber()
//	{
//		return codeInodeNumber;
//	}
}

