package code.jobmanage;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JTextArea;

import code.barecomputer.Clock;
import code.barecomputer.RandomGenerator;
import code.Computer;

public class JCB 
{
	// ��ռ10��
	public static final int MAX_SIZE = 16;
	public static final String[] RANDOM_TYPE_STRING = {"Ѱַ�����Ƶ", "�ļ���д��Ƶ", "��ʱ������Ƶ"};
	// ʱ������
	private int arrivalTime;	// ��ҵ����ʱ��
	private int startTime;		// ��ҵ��ʼʱ�䣨������Ϊ���̣�
	private int finishTime;		// ��ҵ���ʱ�䣨���̱����٣�
	// ��������
	private int jobNumber;		// ��ҵ�ţ�Ψһ��ϵͳָ����
	private String jobName;		// ��ҵ��
	// �봴��������ص�����
	private int codeLength;		// ����ָ���
	private int priority;		// �������ȼ�
	
	public String getString()
	{
		String out = "";
		out += String.format("��ҵ����ʱ��: %d\n", arrivalTime);
		out += String.format("��ҵ��: %d\n", jobNumber);
		out += String.format("��ҵ��: %s\n", jobName);
		out += String.format("����ָ���: %d\n", codeLength);
		out += String.format("��ҵ���ȼ�: %d\n", priority);
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
	// ������ҵ�ļ��е�һ���ַ���������JCB
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
	// �������һ��JCB
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
		// �ӵ�JTextArea
		text.append(String.format("�����������ҵ������: %s\n", RANDOM_TYPE_STRING[type]));
		text.append(String.format("��ҵ��: %d\n��ҵ��: %s\n����ʱ��: %d\n���ȼ�: %d\n���볤��: %d\n", jobNumber, jobName, arrivalTime, priority, codeLength));
		// ���������ļ�
		String Root = System.getProperty("user.dir");
		String Path = Root + File.separator + "input" + File.separator + jobNumber + ".txt";
		File file = new File(Path);
		text.append("���ɵĴ���:\n");
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
