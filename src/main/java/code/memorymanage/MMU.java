package code.memorymanage;

import code.Computer;
import code.barecomputer.Block;
import code.processmanage.PCBPool;

public class MMU 
{
	public static Address getAddress(int block, int offset)
	{
		Address out = new Address(block, offset);
		return out;
	}
	// 判断是否会引发缺页中断
	public static boolean isMissPage(PCBPool pcbPool, int pcbNumber, int logicalAddress)
	{
		PageTable tempPageTable = pcbPool.getPCBAtNumber(pcbNumber).getPageTable();
		int logicalBlockNumber = logicalAddress / Block.MAX_SIZE;
		if(tempPageTable.isPageNumber(logicalBlockNumber)==-1)
			return true;
		return false;
	}
	/**
	 * 将逻辑地址转换为物理地址
	 * 默认逻辑页号在页表内
	 * */
	public static Address getAddressFromPageTable(PCBPool pcbPool, int pcbNumber, int logicalAddress)
	{
		int pageNumber = logicalAddress / Block.MAX_SIZE;
		PageTable tempPageTable = pcbPool.getPCBAtNumber(pcbNumber).getPageTable();
		int blockNumber = tempPageTable.getMemoryBlockNumberFromPageNumber(pageNumber);
		int offset = logicalAddress - pageNumber * Block.MAX_SIZE;
		// 设置访问位
		pcbPool.setLRUOnPageNumberAtPCBNumber(pcbNumber, pageNumber);
		String temp = "偏移地址: "+offset+"\n";
		System.out.print(temp);
		Computer.RunInfo += temp;
		Address out = new Address(blockNumber, offset);
		return out;
	}
	/**
	 * 缺页中断发生时的处理
	 * 仅做相应替换，不做访问
	 * */
	public static void MissPage(PCBPool pcbPool, int pcbNumber, int logicalAddress)
	{
		pcbPool.MissPage(pcbNumber, logicalAddress);
	}
}
