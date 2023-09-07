package code.jobmanage;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JTextArea;

import code.barecomputer.Clock;
import code.barecomputer.RandomGenerator;
import code.Computer;

public class JCB 
{
	// 共占10字
	public static final int MAX_SIZE = 16;
	public static final String[] RANDOM_TYPE_STRING = {"寻址计算高频", "文件读写高频", "长时阻塞高频"};
	// 时间属性
	private int arrivalTime;	// 作业到达时间
	private int startTime;		// 作业开始时间（被创建为进程）
	private int finishTime;		// 作业完成时间（进程被销毁）
	// 基本属性
	private int jobNumber;		// 作业号（唯一，系统指定）
	private String jobName;		// 作业名
	// 与创建进程相关的属性
	private int codeLength;		// 代码指令长度
	private int priority;		// 进程优先级
	
	public String getString()
	{
		String out = "";
		out += String.format("作业到达时间: %d\n", arrivalTime);
		out += String.format("作业号: %d\n", jobNumber);
		out += String.format("作业名: %s\n", jobName);
		out += String.format("代码指令长度: %d\n", codeLength);
		out += String.format("作业优先级: %d\n", priority);
		return out;
	}
	public JCB() 
	{
		arrivalTime = -1;
		startTime = -1;
		finishTime = -1;
		jobNumber = -1;
		jobName = "noName";
		//codeBlock = 0;
		codeLength = 0;
		priority = 0;
	}
	public JCB(JCB copy)
	{
		arrivalTime = copy.arrivalTime;
		startTime = copy.startTime;
		finishTime = copy.finishTime;
		jobNumber = copy.jobNumber;
		jobName = copy.jobName;
		//codeBlock = copy.codeBlock;
		codeLength = copy.codeLength;
		priority = copy.priority;
	}
	// make
	// 读入作业文件中的一行字符串来创建JCB
	public void make(String data)
	{
		String[] subData = data.split(",");
		arrivalTime = Integer.valueOf(subData[0]);
		jobNumber = Computer.JCB_NUM;
		jobName = subData[1];
		codeLength = Integer.valueOf(subData[2]);
		priority = Integer.valueOf(subData[3]);
		Computer.JCB_NUM++;
	}
	// 随机创建一个JCB
	public void randMake(int type, int p, int length, JTextArea text)
	{
		int time = Clock.getClock();
		int min = time-10;
		if(min<=0)
			min = 1;
		arrivalTime = RandomGenerator.getRandom(min, time+20);
		jobNumber = Computer.JCB_NUM;
		jobName = "job_"+jobNumber;
		priority = p;
		codeLength = length;
		// 加到JTextArea
		text.append(String.format("创建的随机作业的种类: %s\n", RANDOM_TYPE_STRING[type]));
		text.append(String.format("作业号: %d\n作业名: %s\n到达时间: %d\n优先级: %d\n代码长度: %d\n", jobNumber, jobName, arrivalTime, priority, codeLength));
		// 创建代码文件
		String Root = System.getProperty("user.dir");
		String Path = Root + File.separator + "input" + File.separator + jobNumber + ".txt";
		File file = new File(Path);
		text.append("生成的代码:\n");
		try {
			FileWriter fileWriterA = new FileWriter(file);
			fileWriterA.write("");
			fileWriterA.close();
			FileWriter fileWriterB = new FileWriter(file, true);
			fileWriterB.write("type,operand");
			for(int i=0; i<codeLength; ++i)
			{
				String code = "";
				int ctype = 0;
				if(type==0)
					ctype = RandomGenerator.getRandomFromVC();
				else if(type==1)
					ctype = RandomGenerator.getRandomFromFile();
				else 
					ctype = RandomGenerator.getRandomFromOther();
				int operand = RandomGenerator.getRandomOperand(ctype);
				text.append(String.format("\t%d: %d,%d\n", i, ctype, operand));
				code = "\n"+ctype+","+operand;
				fileWriterB.write(code);
			}
			text.repaint();
			fileWriterB.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Computer.JCB_NUM++;
	}
	// show
	public void showJCB()
	{
		System.out.println("  -arrivalTime: "+arrivalTime);
		System.out.println("  -startTime: "+startTime);
		System.out.println("  -finishTime: "+finishTime);
		System.out.println("  -Job Number: "+jobNumber);
		System.out.println("  -Job Name: "+jobName);
		System.out.println("  -Code Length: "+codeLength);
		System.out.println("  -Priority: "+priority);
	}
	// set
	public void set(int arrTime, int staTime, int finTime, int number, String Name, int block, int length, int p)
	{
		arrivalTime = arrTime;
		startTime = staTime;
		finishTime = finTime;
		jobNumber = number;
		jobName = Name;
		//codeBlock = block;
		codeLength = length;
		priority = p;
	}
	public void setArrivalTime(int arrTime)
	{
		arrivalTime = arrTime;
	}
	public void setStartTime(int time)
	{
		startTime = time;
	}
	public void setFinishTime(int time)
	{
		finishTime = time;
	}
	public void setJobNumber(int number)
	{
		jobNumber = number;
	}
	public void setJobName(String name)
	{
		jobName = name;
	}
//	public void setCodeBlock(int block)
//	{
//		codeBlock = block;
//	}
	public void setCodeLength(int codeLength) 
	{
		this.codeLength = codeLength;
	}
	public void setPriority(int p)
	{
		priority = p;
	}
	// get
	public String getJobNmae() 
	{
		return jobName;
	}
	public int getCodeLength() 
	{
		return codeLength;
	}
	public int getArrivalTime() 
	{
		return arrivalTime;
	}
	public int getStartTime() 
	{
		return startTime;
	}
	public int getFinishTime() 
	{
		return finishTime;
	}
	public int getJobNumber() 
	{
		return jobNumber;
	}
//	public int getCodeBlock() 
//	{
//		return codeBlock;
//	}
	public int getPriority() 
	{
		return priority;
	}
//	public int getInodeNumber()
//	{
//		return inodeNumber;
//	}
}
