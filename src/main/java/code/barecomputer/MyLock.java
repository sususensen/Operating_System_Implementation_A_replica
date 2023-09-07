package code.barecomputer;

public class MyLock 
{
	// 时钟中断锁
	public static final Object ClockIsChanged = new Object();
	// 内存运行锁
	public static Object MemoryLock = new Object();
	// 阻塞线程运行锁
	public static final Object BlockLock = new Object();
	// 阻塞检查线程锁
	public static final Object BlockCheckLock = new Object();
	// GUI同步锁
	public static final Object GUILock = new Object();
	
	public MyLock()
	{
		// TODO Auto-generated constructor stub
	}
}
