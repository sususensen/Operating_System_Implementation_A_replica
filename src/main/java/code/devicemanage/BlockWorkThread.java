package code.devicemanage;

import code.Computer;
import code.barecomputer.Clock;
import code.barecomputer.MyLock;

public class BlockWorkThread extends Thread
{
	public void run()
	{
		try {
			while(true)
			{
				synchronized (MyLock.BlockLock)
				{
					MyLock.BlockLock.wait();
					
					String temp = "";
					int clock = Clock.getClock();
					// ���������Ĺ���
					
					// Read/Write
					if(!Computer.memory.fileBlock.isEmpty())
					{
						Computer.memory.fileBlock.workFile();
						temp += String.format("%d: Read/Write���̹��� 1��\n", clock);
						//System.out.println(clock + ": " + "Read/Write���̹��� 1��");
					}
					else
						temp += String.format("%d: Read/Write���̹�����ת\n", clock);
						//System.out.println(clock + ": " + "Read/Write���̹�����ת");
					
					// input
					if(!Computer.memory.inputBlock.isEmpty())
					{
						int inputIndex = Computer.memory.inputBlock.getMaxPriorityIndex();
						Computer.memory.inputBlock.work(inputIndex);
						temp += String.format("%d: ���̹��� 1��\n", clock);
						Computer.memory.deviceTable.time[0]++;
						//System.out.println(clock + ": " + "���̹��� 1��");
					}
					else
						temp += String.format("%d: ���̹�����ת\n", clock);
						//System.out.println(clock + ": " + "���̹�����ת");
					
					// output
					if(!Computer.memory.outputBlock.isEmpty())
					{
						int outputIndex = Computer.memory.outputBlock.getMaxPriorityIndex();
						Computer.memory.outputBlock.work(outputIndex);
						temp += String.format("%d: ��Ļ���� 1��\n", clock);
						Computer.memory.deviceTable.time[1]++;
						//System.out.println(clock + ": " + "��Ļ���� 1��");
					}
					else
						temp += String.format("%d: ��Ļ������ת\n", clock);
						//System.out.println(clock + ": " + "��Ļ������ת");
					
					// printer
					if(!Computer.memory.printerBlock.isEmpty())
					{
						Computer.memory.printerBlock.workPrinter();
						temp += String.format("%d: ��ӡ������ 1��\n", clock);
						//System.out.println(clock + ": " + "��ӡ������ 1��");
					}
					else
						temp += String.format("%d: ��ӡ��������ת\n", clock);
						//System.out.println(clock + ": " + "��ӡ��������ת");
					
					// camera
					if(!Computer.memory.cameraBlock.isEmpty())
					{
						int cameraIndex = Computer.memory.cameraBlock.getMaxPriorityIndex();
						Computer.memory.cameraBlock.work(cameraIndex);
						temp += String.format("%d: ����ͷ���� 1��\n", clock);
						Computer.memory.deviceTable.time[5]++;
						//System.out.println(clock + ": " + "����ͷ���� 1��");
					}
					else
						temp += String.format("%d: ����ͷ������ת\n", clock);
						//System.out.println(clock + ": " + "����ͷ������ת");
					
					System.out.print(temp);
					Computer.DeviceInfo += temp;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
