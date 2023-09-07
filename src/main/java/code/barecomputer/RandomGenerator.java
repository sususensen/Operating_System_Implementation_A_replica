package code.barecomputer;

import java.util.Random;

import code.memorymanage.PageTable;

// 随机数发生器
public class RandomGenerator 
{
	public static final int MAX_LOGICALSPACE = 8196;
	public RandomGenerator()
	{
		// TODO Auto-generated constructor stub
	}
	/**
	 * 根据type获取相应合法operand的操作数
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
	 * 根据某PCB中的页表获取随机数
	 * 获得的随机数大小范围保证在页表中已有的逻辑地址中
	 * 用于指令Value
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
	 * 产生整个逻辑空间的寻址范围的随机数
	 * 用于指令Calculate
	 * */
	public static int getRandomFromLogicalSpace()
	{
		int out = 0;
		Random r1 = new Random();
		out = r1.nextInt(MAX_LOGICALSPACE);
		return out;
	}
	/**
	 * 产生指定范围的随机数
	 * 用于随即生成指令，作业等事务
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
	 * 产生指定高频率Value/Calculate的随机数
	 * 用于创建代码文件时的指令类型
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
	 * 产生指定高频率文件IO的随机数
	 * 用于创建代码文件时的指令类型
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
	 * 产生指定高频率其他IO的随机数
	 * 用于创建代码文件时的指令类型
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
