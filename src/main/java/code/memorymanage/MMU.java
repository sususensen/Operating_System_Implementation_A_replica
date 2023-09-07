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
	// �ж��Ƿ������ȱҳ�ж�
	public static boolean isMissPage(PCBPool pcbPool, int pcbNumber, int logicalAddress)
	{
		PageTable tempPageTable = pcbPool.getPCBAtNumber(pcbNumber).getPageTable();
		int logicalBlockNumber = logicalAddress / Block.MAX_SIZE;
		if(tempPageTable.isPageNumber(logicalBlockNumber)==-1)
			return true;
		return false;
	}
	/**
	 * ���߼���ַת��Ϊ�����ַ
	 * Ĭ���߼�ҳ����ҳ����
	 * */
	public static Address getAddressFromPageTable(PCBPool pcbPool, int pcbNumber, int logicalAddress)
	{
		int pageNumber = logicalAddress / Block.MAX_SIZE;
		PageTable tempPageTable = pcbPool.getPCBAtNumber(pcbNumber).getPageTable();
		int blockNumber = tempPageTable.getMemoryBlockNumberFromPageNumber(pageNumber);
		int offset = logicalAddress - pageNumber * Block.MAX_SIZE;
		// ���÷���λ
		pcbPool.setLRUOnPageNumberAtPCBNumber(pcbNumber, pageNumber);
		String temp = "ƫ�Ƶ�ַ: "+offset+"\n";
		System.out.print(temp);
		Computer.RunInfo += temp;
		Address out = new Address(blockNumber, offset);
		return out;
	}
	/**
	 * ȱҳ�жϷ���ʱ�Ĵ���
	 * ������Ӧ�滻����������
	 * */
	public static void MissPage(PCBPool pcbPool, int pcbNumber, int logicalAddress)
	{
		pcbPool.MissPage(pcbNumber, logicalAddress);
	}
}
