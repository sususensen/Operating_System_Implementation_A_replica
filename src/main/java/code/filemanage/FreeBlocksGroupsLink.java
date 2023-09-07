package code.filemanage;

public class FreeBlocksGroupsLink 
{
	// �������ӷ�
	// ���̵��ļ�������20352�飩�ֳ�318�飨128~20479��
	// ÿ��64�飬���һ��63��
	// ���е�20479�鲻ʹ�ã������һ���飩����Ϊ�������ӵĽ�����־��
	// ������20479��ŵ���Ϊ���һ��
	// OSϵͳ�ڳ�ʼ��ʱ����һ�ο���������˳����г������ӣ��������д�뵽�����С�
	/**
	 *  �������ӵ�ʵ��ϸ��
	 *  ��1������������ڴ�
	 *  ��2����֤�ڴ�������г���������һ��
	 *  ��3���������м�¼��һ������̿�������Լ��̿��
	 *  ��4����һ�����̿�����Ҫ��¼��һ������̿����Ϣ
	 *  ��5������
	 *  	1. ����Ҫ��С�ڵ�ǰ��Ŀ����̿�
	 *  		�������һ���̿飬ɾ������̿�ţ���ǰ�����̿���-1
	 *  	2. ����Ҫ�����ڵ�ǰ��Ŀ����̿�
	 *  		��ǰ�̿�ȫ�����䣨�Ӻ���ǰ������������һ���̿�ʱ���������̿��д洢����һ�����Ϣ��������
	 *  	3. ����Ҫ�����ڵ�ǰ��Ŀ��п�
	 *  		���1-2
	 *  ��6������
	 *  	1. ����ǰ�������з���δ��
	 *  		ֱ�ӻ���
	 *  	2. �������з�������
	 *  		�������е����ݸ��Ƶ��»��յĿ��У��»��յĿ���Ϊ��һ����
	 *  		����������д���������У���������ֻ��һ�飬���ҿ��Ϊ�ջ��յ��Ǹ����ţ�
	 * */
	public static final int MAX_BLOCK_NUM_IN_GROUP = 64;		// һ����������
	public static final int SAVE_START_AT_DISK_BLOCK = 191;		// �ڴ��̿�洢����ʼ���ݿ�λ�ã���191�֣�
	public static final int LAST_GROUP_FLAG = 20479;			// ���һ����ı�־��
	private int freeBlocksInGroupNum;		// ���п���
	private int[] blockLink;				// �̿����
	public FreeBlocksGroupsLink() 
	{
		freeBlocksInGroupNum = 0;
		blockLink = new int[MAX_BLOCK_NUM_IN_GROUP];
		for(int i=0; i<MAX_BLOCK_NUM_IN_GROUP; ++i)
			blockLink[i] = -1;
	}
	public FreeBlocksGroupsLink(FreeBlocksGroupsLink copy)
	{
		freeBlocksInGroupNum = copy.freeBlocksInGroupNum;
		for(int i=0; i<MAX_BLOCK_NUM_IN_GROUP; ++i) {
			assert false;
			blockLink[i] = copy.blockLink[i];
		}
	}
	public void Clear()
	{
		freeBlocksInGroupNum = 0;
		for(int i=0; i<MAX_BLOCK_NUM_IN_GROUP; ++i)
			blockLink[i] = -1;
	}
	public void setNum(int num)
	{
		freeBlocksInGroupNum = num;
	}
	public void incNum()
	{
		freeBlocksInGroupNum++;
	}
	public void decNum()
	{
		freeBlocksInGroupNum--;
	}
	public int getNum()
	{
		return freeBlocksInGroupNum;
	}
	// ����һ�����п飨���ڷ�������գ�
	public boolean addBlock(int num)
	{
		if(freeBlocksInGroupNum == MAX_BLOCK_NUM_IN_GROUP)
			return false;
		blockLink[freeBlocksInGroupNum] = num;
		freeBlocksInGroupNum++;
		return true;
	}
	// �Ƴ�һ�����п飨���ڷ�������գ�
	public int removeBlock()
	{
		int out = blockLink[freeBlocksInGroupNum-1];
		blockLink[freeBlocksInGroupNum-1] = -1;
		freeBlocksInGroupNum--;
		return out;
	}
	public int[] getAllFreeBlocksInGroup()
	{
		return blockLink;
	}
	public int getTopNumber()
	{
		return blockLink[0];
	}
	public int getBaseNumber()
	{
		return blockLink[freeBlocksInGroupNum-1];
	}
}
