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
	// 时间
	private int startTime;		// 开始时间
	private int runTime;		// 运行时间
	private int finishTime;		// 结束时间
	// 基本属性
	private int processNumber;	// 进程号
	private String processName;	// 进程名
	private int priority;		// 优先级
	private int state;			// 进程状态
	private int codeLength;		// 代码长度（行）
	public Instruction[] codes;	// 代码集
//	private int codeFileBeginBlockInDisk;	// 代码文件在磁盘中的物理块号
//	private int codeFileBlockInMemory;		// 代码文件在内存中的物理块号（-1表示未装填）
//	private int codeInodeNumber;			// 代码文件的inode号
	// 其他属性
	private int timeFlak;		// 已使用时间片数
	public PageTable pageTable;				// 进程的页表
	public ProcessFileTable processFileTable;	// 进程文件打开表
	public Scene scene;		// 现场
	
	public String getString()
	{
		String out = "";
		out += String.format("开始时间: %d\n", startTime);
		out += String.format("运行时间: %d\n", runTime);
		out += String.format("进程号: %d\n", processNumber);
		out += String.format("进程名: %s\n", processName);
		out += String.format("优先级: %d\n", priority);
		out += String.format("代码长度: %d\n", codeLength);
		out += "页表:\n";
		out += pageTable.getString();
		return out;
	}
	public String getDeadString()
	{
		String out = "";
		out += String.format("开始时间: %d\n", startTime);
		out += String.format("运行时间: %d\n", runTime);
		out += String.format("结束时间: %d\n", finishTime);
		out += String.format("进程号: %d\n", processNumber);
		out += String.format("进程名: %s\n", processName);
		return out;
	}
	public String getBlockString()
	{
		String out = "";
		out += String.format("开始时间: %d\n", startTime);
		out += String.format("运行时间: %d\n", runTime);
		out += String.format("进程号: %d\n", processNumber);
		out += String.format("进程名: %s\n", processName);
		out += String.format("优先级: %d\n", priority);
		out += String.format("代码长度: %d\n", codeLength);
		out += "页表:\n";
		out += pageTable.getString();
		out += "现场:\n";
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
	// 从JCB创建一个PCB（仅填写基本信息，并不分配资源，创建后转为新建态）
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
		// 读取代码
		String Root = System.getProperty("user.dir");
		String readPath = Root + File.separator + "input" + File.separator + jcb.getJobNumber() + ".txt";
		File readFile = new File(readPath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(readFile));
			String lineText = br.readLine();	// 略去第一行
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
		// 初始化页表
		pageTable.Initlize(pcbIndex);
		// 初始化现场信息
		scene.Initlize();
	}
	
	// 时间片加一
	public void incTimeFlak()
	{
		timeFlak++;
	}
	// 运行时间加一
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

