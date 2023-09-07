package code.devicemanage;

import code.barecomputer.Clock;
import code.barecomputer.MyLock;
import code.Computer;

public class BlockAwakeThread extends Thread
{
	public BlockAwakeThread() {
		// TODO Auto-generated constructor stub
	}
	
	public void run()
	{
		try {
			while(true)
			{
				synchronized (MyLock.BlockCheckLock) 
				{
					MyLock.BlockCheckLock.wait();
					
					String temp = "";
					int clock = Clock.getClock();
					// �������
					// file
					if(!Computer.memory.fileBlock.isEmpty())
					{
						while(Computer.memory.fileBlock.check() != -1)
						{
							int outIndex = Computer.memory.fileBlock.check();
							int pcbIndex = Computer.memory.fileBlock.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							Computer.memory.fileNum++;
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d �Ž�����(�ļ���д����)����̬ -> ����̬\n", clock, pcbNumber);
							// ϵͳ�򿪱�
							int inodeNumber = Computer.memory.pcbPool.pool[pcbIndex].processFileTable.inodeNumber;
							if(Computer.memory.openFileTable.isOne(inodeNumber))
							{
								// û�н���������
								Computer.memory.openFileTable.removeAOpenFile(inodeNumber);
							}
							else
							{
								// �н�������
								Computer.memory.openFileTable.decPNumberForInode(inodeNumber);
							}
							// ���̱�
							Computer.memory.pcbPool.pool[pcbIndex].processFileTable.remove();
							//System.out.println(clock + ": " + pcbNumber + " �Ž�����(�ļ���д����)����̬ -> ����̬");
						}
					}
					// input
					if(!Computer.memory.inputBlock.isEmpty())
					{
						while(Computer.memory.inputBlock.check() != -1)
						{
							int outIndex = Computer.memory.inputBlock.check();
							// �����������Ľ���
							int pcbIndex = Computer.memory.inputBlock.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							Computer.memory.keyboardNum++;
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d �Ž�����(�����������)����̬ -> ����̬\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " �Ž�����(�����������)����̬ -> ����̬");
						}
					}
					// output
					if(!Computer.memory.outputBlock.isEmpty())
					{
						while(Computer.memory.outputBlock.check() != -1)
						{
							int outIndex = Computer.memory.outputBlock.check();
							// �����������Ľ���
							int pcbIndex = Computer.memory.outputBlock.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							Computer.memory.screenNum++;
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d �Ž�����(��Ļ�������)����̬ -> ����̬\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " �Ž�����(��Ļ�������)����̬ -> ����̬");
						}
					}
					// printer
					if(!Computer.memory.printerBlock.isEmpty())
					{
						while(Computer.memory.printerBlock.check() != -1)
						{
							int outIndex = Computer.memory.printerBlock.check();
							// �����������Ľ���
							int pcbIndex = Computer.memory.printerBlock.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							Computer.memory.printerNum++;
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d �Ž�����(��ӡ������)����̬ -> ����̬\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " �Ž�����(��ӡ������)����̬ -> ����̬");
						}
					}
					// camera
					if(!Computer.memory.cameraBlock.isEmpty())
					{
						while(Computer.memory.cameraBlock.check() != -1)
						{
							int outIndex = Computer.memory.cameraBlock.check();
							// �����������Ľ���
							int pcbIndex = Computer.memory.cameraBlock.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							Computer.memory.cameraNum++;
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d �Ž�����(���������)����̬ -> ����̬\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " �Ž�����(���������)����̬ -> ����̬");
						}
					}
					// waiting
					if(Computer.memory.printerNum > 0)
					{
						// ��������Դ
						if(!Computer.memory.printerWaiting.isEmpty())
						{
							int outIndex = Computer.memory.printerWaiting.check();
							int pcbIndex = Computer.memory.printerWaiting.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d �Ž�����(��ӡ���ȴ�����)����̬ -> ����̬\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " �Ž�����(��ӡ���ȴ�����)����̬ -> ����̬");
						}
					}
					System.out.print(temp);
					Computer.RunInfo += temp;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
