package code.barecomputer;

import java.util.Random;

import code.memorymanage.PageTable;

// �����������
public class RandomGenerator 
{
	public static final int MAX_LOGICALSPACE = 8196;
	public RandomGenerator()
	{
		// TODO Auto-generated constructor stub
	}
	/**
	 * ����type��ȡ��Ӧ�Ϸ�operand�Ĳ�����
	 * */
	public static int getRandomOperand(int type)
	{
		int out = 0;
		Random r1 = new Random();
		switch (type) {
			case Instruction.VALUE -> {
				out = r1.nextInt(6);
			}
			case Instruction.CALCULATE -> {
				out = r1.nextInt(4);
			}
			case Instruction.READ, Instruction.WRITE -> {
				out = r1.nextInt(10);
			}
			case Instruction.INPUT, Instruction.OUTPUT -> {
				out = r1.nextInt(15 - 5) + 5;
			}
			case Instruction.PRINTER, Instruction.CAMERA -> {
				out = 0;
			}
			default -> {
				out = 0;
			}
		}
		return out;
	}
	/**
	 * ����ĳPCB�е�ҳ���ȡ�����
	 * ��õ��������С��Χ��֤��ҳ�������е��߼���ַ��
	 * ����ָ��Value
	 * */
	public static int getRandomFromPageTable(PageTable pageTable)
	{
		int pageNumber_A = pageTable.getPageNumber(0);
		int pageNumber_B = pageTable.getPageNumber(1);
		Random r1 = new Random();
		Random r2 = new Random();
		int flag = r1.nextInt(2);
		int out = 0;
		if(flag == 0)
		{
			int max = (pageNumber_A+1) * Block.MAX_SIZE - 1;
			int min = (pageNumber_A) * Block.MAX_SIZE;
			out = r2.nextInt(max - min + 1) + min;
		}
		else
		{
			int max = (pageNumber_B+1) * Block.MAX_SIZE - 1;
			int min = (pageNumber_B) * Block.MAX_SIZE;
			out = r2.nextInt(max - min + 1) + min;
		}
		return out;
	}
	/**
	 * ���������߼��ռ��Ѱַ��Χ�������
	 * ����ָ��Calculate
	 * */
	public static int getRandomFromLogicalSpace()
	{
		int out = 0;
		Random r1 = new Random();
		out = r1.nextInt(MAX_LOGICALSPACE);
		return out;
	}
	/**
	 * ����ָ����Χ�������
	 * �����漴����ָ���ҵ������
	 * [start, end]
	 * */
	public static int getRandom(int start, int end)
	{
		if(start >= end)
			return 0;
		int out = 0;
		Random r1 = new Random();
		out = r1.nextInt(end - start + 1) + start;
		return out;
	}
	/**
	 * ����ָ����Ƶ��Value/Calculate�������
	 * ���ڴ��������ļ�ʱ��ָ������
	 * */
	public static int getRandomFromVC()
	{
		int out = 0;
		Random r1 = new Random();
		int flag = r1.nextInt(100);
		if(flag<40)
			out = 0;
		else if(flag>=40 && flag<80)
			out = 1;
		else if(flag>=80 && flag<85)
			out = 2;
		else if(flag>=85 && flag<90)
			out = 3;
		else if(flag>=90 && flag<93)
			out = 4;
		else if(flag>=93 && flag<96)
			out = 5;
		else if(flag>=96 && flag<99)
			out = 6;
		else 
			out = 7;
		return out;
	}
	/**
	 * ����ָ����Ƶ���ļ�IO�������
	 * ���ڴ��������ļ�ʱ��ָ������
	 * */
	public static int getRandomFromFile()
	{
		int out = 0;
		Random r1 = new Random();
		int flag = r1.nextInt(100);
		if(flag<10)
			out = 0;
		else if(flag>=10 && flag<20)
			out = 1;
		else if(flag>=20 && flag<50)
			out = 2;
		else if(flag>=50 && flag<80)
			out = 3;
		else if(flag>=80 && flag<85)
			out = 4;
		else if(flag>=85 && flag<90)
			out = 5;
		else if(flag>=90 && flag<95)
			out = 6;
		else 
			out = 7;
		return out;
	}
	/**
	 * ����ָ����Ƶ������IO�������
	 * ���ڴ��������ļ�ʱ��ָ������
	 * */
	public static int getRandomFromOther()
	{
		int out = 0;
		Random r1 = new Random();
		int flag = r1.nextInt(100);
		if(flag<10)
			out = 0;
		else if(flag>=10 && flag<20)
			out = 1;
		else if(flag>=20 && flag<45)
			out = 4;
		else if(flag>=45 && flag<70)
			out = 5;
		else if(flag>=70 && flag<90)
			out = 6;
		else 
			out = 7;
		return out;
	}
}
