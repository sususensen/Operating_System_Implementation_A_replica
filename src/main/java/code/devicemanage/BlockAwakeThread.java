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
					// 阻塞检查
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
							temp += String.format("%d: %d 号进程由(文件读写队列)阻塞态 -> 就绪态\n", clock, pcbNumber);
							// 系统打开表
							int inodeNumber = Computer.memory.pcbPool.pool[pcbIndex].processFileTable.inodeNumber;
							if(Computer.memory.openFileTable.isOne(inodeNumber))
							{
								// 没有进程引用了
								Computer.memory.openFileTable.removeAOpenFile(inodeNumber);
							}
							else
							{
								// 有进程引用
								Computer.memory.openFileTable.decPNumberForInode(inodeNumber);
							}
							// 进程表
							Computer.memory.pcbPool.pool[pcbIndex].processFileTable.remove();
							//System.out.println(clock + ": " + pcbNumber + " 号进程由(文件读写队列)阻塞态 -> 就绪态");
						}
					}
					// input
					if(!Computer.memory.inputBlock.isEmpty())
					{
						while(Computer.memory.inputBlock.check() != -1)
						{
							int outIndex = Computer.memory.inputBlock.check();
							// 有满足条件的进程
							int pcbIndex = Computer.memory.inputBlock.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							Computer.memory.keyboardNum++;
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d 号进程由(键盘输入队列)阻塞态 -> 就绪态\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " 号进程由(键盘输入队列)阻塞态 -> 就绪态");
						}
					}
					// output
					if(!Computer.memory.outputBlock.isEmpty())
					{
						while(Computer.memory.outputBlock.check() != -1)
						{
							int outIndex = Computer.memory.outputBlock.check();
							// 有满足条件的进程
							int pcbIndex = Computer.memory.outputBlock.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							Computer.memory.screenNum++;
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d 号进程由(屏幕输出队列)阻塞态 -> 就绪态\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " 号进程由(屏幕输出队列)阻塞态 -> 就绪态");
						}
					}
					// printer
					if(!Computer.memory.printerBlock.isEmpty())
					{
						while(Computer.memory.printerBlock.check() != -1)
						{
							int outIndex = Computer.memory.printerBlock.check();
							// 有满足条件的进程
							int pcbIndex = Computer.memory.printerBlock.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							Computer.memory.printerNum++;
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d 号进程由(打印机队列)阻塞态 -> 就绪态\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " 号进程由(打印机队列)阻塞态 -> 就绪态");
						}
					}
					// camera
					if(!Computer.memory.cameraBlock.isEmpty())
					{
						while(Computer.memory.cameraBlock.check() != -1)
						{
							int outIndex = Computer.memory.cameraBlock.check();
							// 有满足条件的进程
							int pcbIndex = Computer.memory.cameraBlock.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							Computer.memory.cameraNum++;
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d 号进程由(摄像机队列)阻塞态 -> 就绪态\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " 号进程由(摄像机队列)阻塞态 -> 就绪态");
						}
					}
					// waiting
					if(Computer.memory.printerNum > 0)
					{
						// 有闲置资源
						if(!Computer.memory.printerWaiting.isEmpty())
						{
							int outIndex = Computer.memory.printerWaiting.check();
							int pcbIndex = Computer.memory.printerWaiting.remove(outIndex);
							Computer.memory.BlockToReady(pcbIndex);
							int pcbNumber = Computer.memory.pcbPool.pool[pcbIndex].getProcessNumber();
							temp += String.format("%d: %d 号进程由(打印机等待队列)阻塞态 -> 就绪态\n", clock, pcbNumber);
							//System.out.println(clock + ": " + pcbNumber + " 号进程由(打印机等待队列)阻塞态 -> 就绪态");
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
