package code.barecomputer;

public class MyLock 
{
	// ʱ���ж���
	public static final Object ClockIsChanged = new Object();
	// �ڴ�������
	public static Object MemoryLock = new Object();
	// �����߳�������
	public static final Object BlockLock = new Object();
	// ��������߳���
	public static final Object BlockCheckLock = new Object();
	// GUIͬ����
	public static final Object GUILock = new Object();
	
	public MyLock()
	{
		// TODO Auto-generated constructor stub
	}
}
