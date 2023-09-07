package code.devicemanage;

public class DeviceTable 
{
	public static final int MAX_DEVICE_NUM = 6;
	public static final int PRINTER_NUM = 3;
	public static final int[] DPROCESS_ADDRESS = {2060, 2061, 2062, 2063, 2064, 2065};
	public static final String[] DEVICE_NAMES = {"Logi_K380", "Dell_led", "Dell_A", "Dell_B", "Dell_C", "Lenovo"};
	// �豸������Ϊ�±�
	public String deviceName[];		// �豸��
	public int pAddress[];			// �豸�����������ڵ�ַ
	// �����豸����Ч����ʱ��
	public int time[];
	
	public DeviceTable() 
	{
		deviceName = new String[MAX_DEVICE_NUM];
		pAddress = new int[MAX_DEVICE_NUM];
		time = new int[MAX_DEVICE_NUM];
	}
	public void Initlize()
	{
		for(int i=0;i<MAX_DEVICE_NUM;++i)
		{
			deviceName[i] = DEVICE_NAMES[i];
			pAddress[i] = DPROCESS_ADDRESS[i];
			time[i] = 0;
		}
	}
	public void incTimeAtIndex(int index)
	{
		time[index]++;
	}
}
