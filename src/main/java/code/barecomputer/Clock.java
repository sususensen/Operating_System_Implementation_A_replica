package code.barecomputer;

public class Clock extends Thread 
{
	public static final int COUNTING_CYCLE = 1000;
	private static boolean Flag = false;
	private static int Count;
	public Clock() 
	{
		Count = 0;
	}
	public static synchronized void setClock(int x)
	{
		Count = x;
	}
	public static synchronized int getClock()
	{
		return Count;
	}
	public static synchronized void doInc()
	{
		Count++;
	}
	public static void setFlag(boolean flag)
	{
		Flag = flag;
	}
	public static boolean getFlag()
	{
		return Flag;
	}
	public void run()
	{
		try {
			while(true)
			{
				if(Flag)
				{
					// System.out.println(Count);
					synchronized(MyLock.ClockIsChanged)
					{
						MyLock.ClockIsChanged.notifyAll();
					}
					sleep(400);
					synchronized(MyLock.BlockLock)
					{
						MyLock.BlockLock.notifyAll();
					}
					sleep(200);
					synchronized(MyLock.BlockCheckLock)
					{
						MyLock.BlockCheckLock.notifyAll();
					}
					sleep(200);
					synchronized(MyLock.GUILock)
					{
						MyLock.GUILock.notifyAll();
					}
					sleep(200);
					Count++;
				}
				else
				{
					sleep(COUNTING_CYCLE);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
