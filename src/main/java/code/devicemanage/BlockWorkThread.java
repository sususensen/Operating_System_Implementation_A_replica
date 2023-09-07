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
					// 各种阻塞的工作
					
					// Read/Write
					if(!Computer.memory.fileBlock.isEmpty())
					{
						Computer.memory.fileBlock.workFile();
						temp += String.format("%d: Read/Write磁盘工作 1秒\n", clock);
						//System.out.println(clock + ": " + "Read/Write磁盘工作 1秒");
					}
					else
						temp += String.format("%d: Read/Write磁盘工作空转\n", clock);
						//System.out.println(clock + ": " + "Read/Write磁盘工作空转");
					
					// input
					if(!Computer.memory.inputBlock.isEmpty())
					{
						int inputIndex = Computer.memory.inputBlock.getMaxPriorityIndex();
						Computer.memory.inputBlock.work(inputIndex);
						temp += String.format("%d: 键盘工作 1秒\n", clock);
						Computer.memory.deviceTable.time[0]++;
						//System.out.println(clock + ": " + "键盘工作 1秒");
					}
					else
						temp += String.format("%d: 键盘工作空转\n", clock);
						//System.out.println(clock + ": " + "键盘工作空转");
					
					// output
					if(!Computer.memory.outputBlock.isEmpty())
					{
						int outputIndex = Computer.memory.outputBlock.getMaxPriorityIndex();
						Computer.memory.outputBlock.work(outputIndex);
						temp += String.format("%d: 屏幕工作 1秒\n", clock);
						Computer.memory.deviceTable.time[1]++;
						//System.out.println(clock + ": " + "屏幕工作 1秒");
					}
					else
						temp += String.format("%d: 屏幕工作空转\n", clock);
						//System.out.println(clock + ": " + "屏幕工作空转");
					
					// printer
					if(!Computer.memory.printerBlock.isEmpty())
					{
						Computer.memory.printerBlock.workPrinter();
						temp += String.format("%d: 打印机工作 1秒\n", clock);
						//System.out.println(clock + ": " + "打印机工作 1秒");
					}
					else
						temp += String.format("%d: 打印机工作空转\n", clock);
						//System.out.println(clock + ": " + "打印机工作空转");
					
					// camera
					if(!Computer.memory.cameraBlock.isEmpty())
					{
						int cameraIndex = Computer.memory.cameraBlock.getMaxPriorityIndex();
						Computer.memory.cameraBlock.work(cameraIndex);
						temp += String.format("%d: 摄像头工作 1秒\n", clock);
						Computer.memory.deviceTable.time[5]++;
						//System.out.println(clock + ": " + "摄像头工作 1秒");
					}
					else
						temp += String.format("%d: 摄像头工作空转\n", clock);
						//System.out.println(clock + ": " + "摄像头工作空转");
					
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
