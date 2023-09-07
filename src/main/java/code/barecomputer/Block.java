package code.barecomputer;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

// �����
public class Block 
{
	// ÿ��inode��64b��һ���з�8��inode
	public final static int INODE_NUM_IN_BLOCK = 8;
	// ���512B
	public final static int MAX_BYTE_SIZE = 512;
	// ���ݴ洢��ʽΪ���ֽ�16λ
	public final static int MAX_SIZE = 256;
	// false��ʾ��Ӳ�̣�true��ʾ���ڴ�
	private boolean MemoryOrDisk = false;
	// ����Ӳ�̿�ʱ������
	private int cylinderNumber = 0;	// �����
	private int trackNumber = 0;	// �ŵ���
	private int sectorNumber = 0;	// ������
	// �����ڴ��ʱ������
	private int memoryNumber = 0;
	private ArrayList<String> data = new ArrayList<String>(MAX_SIZE);
	
	public Block() 
	{
		// һ���������256�����ݣ�256 * 2�ֽ�
		for(int i=0; i<MAX_SIZE; ++i)
		{
			data.add("0000");
		}
	}
	public Block(boolean flag, int cNum, int tNum, int sNum)
	{
		if(flag)
		{
			System.out.print("�쳣�ĳ�ʼ��Ӳ��/�ڴ�");
			System.exit(-1);
		}
		MemoryOrDisk = flag;
		cylinderNumber = cNum;
		trackNumber = tNum;
		sectorNumber = sNum;
		// һ���������256�����ݣ�256 * 2�ֽ�
		for(int i=0; i<MAX_SIZE; ++i)
		{
			data.add("0000");
		}
	}
	public Block(boolean flag, int mNum)
	{
		if(!flag)
		{
			// ����Ǵ���
			int[] location = Disk.BlockTransform_Index_To_Location(mNum);
			MemoryOrDisk = flag;
			cylinderNumber = location[0];
			trackNumber = location[1];
			sectorNumber = location[2];
			// һ���������256�����ݣ�256 * 2�ֽ�
			for(int i=0; i<MAX_SIZE; ++i)
			{
				data.add("0000");
			}
		}
		else
		{
			// ������ڴ�
			MemoryOrDisk = flag;
			memoryNumber = mNum;
			// һ���������256�����ݣ�256 * 2�ֽ�
			for(int i=0; i<MAX_SIZE; ++i)
			{
				data.add("0000");
			}
		}
	}
	public Block(Block copBlock)
	{
		
		this.cylinderNumber = copBlock.cylinderNumber;
		this.trackNumber = copBlock.trackNumber;
		this.sectorNumber = copBlock.sectorNumber;
		this.MemoryOrDisk = copBlock.MemoryOrDisk;
		this.memoryNumber = copBlock.memoryNumber;
		for(int i=0; i<MAX_SIZE; ++i)
			data.set(i, copBlock.data.get(i));
	}
	// ��ÿ��ڵ���������
	public String[] getAllData()
	{
		String[] out = new String[MAX_SIZE];
		for(int i=0; i<MAX_SIZE; ++i)
		{
			String temp = data.get(i);
			out[i] = temp;
		}
		return out;
	}
	public void setDataAtIndex(int Index, String Data)
	{
		data.set(Index, Data);
	}
	public String getDataAtIndex(int Index)
	{
		return data.get(Index);
	}
	public void doFormat()
	{
		for(int i=0; i<MAX_SIZE; ++i)
		{
			data.set(i, "");
		}
	}
	// ��ȡdata��start��ʼ����length�ֵ�����
	public String getStringFromData(int start, int length)
	{
		String out = "";
		for(int i=start; i<start+length; ++i)
		{
			out = out + data.get(i);
		}
		return out;
	}
	public String[] getStringArrayFromData(int start, int length)
	{
		String[] out = new String[length];
		int index = 0;
		for(int i=start; i<start+length; ++i)
		{
			out[index] =  data.get(i);
			index++;
		}
		return out;
	}
	// �޸�data��start��ʼ����length�ֵ�����
	public void setDataFromSringArray(int start, int length, String[] stringArray)
	{
		if(length != stringArray.length)
		{
			System.err.println("д��������ƶ������봫�����鳤�Ȳ���");
			System.exit(-1);
		}
		int j=0;
		for(int i=start;i<start+length;++i)
		{
			data.set(i, stringArray[j]);
			j++;
		}
	}
	// �����е�data��Ϊ�˵ȷ֣�����inode��ʼ��
	public String[][] doDivide()
	{
		String[][] dataList = new String[8][32];
		for(int i=0; i<MAX_SIZE; ++i)
		{
			int I = i/32;
			dataList[I][i-I*32] = data.get(i);
		}
		return dataList;
	}
	// �����е�index���ַָ�Ϊ�ֽ�
	public String[] getByte(int index)
	{
		String temp = data.get(index);
		String[] out = new String[2];
		out[0] = String.valueOf(temp.charAt(0)) + String.valueOf(temp.charAt(1));
		out[1] = String.valueOf(temp.charAt(2)) + String.valueOf(temp.charAt(3));
		return out;
	}
	public static String[] getByte(String temp)
	{
		String[] out = new String[2];
		out[0] = String.valueOf(temp.charAt(0)) + String.valueOf(temp.charAt(1));
		out[1] = String.valueOf(temp.charAt(2)) + String.valueOf(temp.charAt(3));
		return out;
	}
	
	/**
	 * д�ؿ��е����ݵ����ش���TXT��
	 * */
	public void writeBack()
	{
		if(MemoryOrDisk)
		{
			System.err.printf("�ÿ�[%d,%d,%d]���ڴ��У��޷�д�ص����ش����ļ�\n", cylinderNumber, trackNumber, sectorNumber);
			System.exit(-1);
		}
		String Root = System.getProperty("user.dir");
		String Path = Root + File.separator + Disk.DiskStr + 
				File.separator + Disk.Cylinder + String.format("%d", cylinderNumber) +
				File.separator + Disk.Track + String.format("%2d", trackNumber) +
				File.separator + Disk.Sector + String.format("%2d.txt", sectorNumber);
		// System.out.println(diskPath);
		try {
			File file = new File(Path);
			// clear old
			FileWriter fWriterA = new FileWriter(file);
			fWriterA.write("");
			fWriterA.flush();
			fWriterA.close();
			// write new
			FileWriter fWriterB = new FileWriter(file, true);
			for(int i=0;i<MAX_SIZE;++i)
			{
				String temp = data.get(i) + "\n";
				fWriterB.write(temp);
			}
			fWriterB.flush();
			fWriterB.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// ��ӡ����data������
	public void showData()
	{
		for(int i=0; i<MAX_SIZE; ++i)
		{
			System.out.print(data.get(i) + " ");
			if((i+1)%16 == 0)
				System.out.println("");
		}
	}
	// ��ӡ���е�ȫ������
	public void showBlock()
	{
		System.out.println("MAX_BYTE_SIZE: " + MAX_BYTE_SIZE);
		System.out.println("MAX_SIZE: " + MAX_SIZE);
		System.out.println("INODE_NUM_IN_BLOCK: " + INODE_NUM_IN_BLOCK);
		System.out.print("Memory Or Disk: " + MemoryOrDisk);
		if(MemoryOrDisk)
		{
			System.out.println("(Memory)");
			System.out.println("Memory Number: " + memoryNumber);
		}
		else
		{
			System.out.println("(Disk)");
			System.out.println("Cyliner: " + cylinderNumber);
			System.out.println("Track: " + trackNumber);
			System.out.println("Sector: " + sectorNumber);
		}
		System.out.println("data: ");
		this.showData();
	}
	
	/**
	 * �������ݽ���ת��������
	 * */
	public static int TransformA(char in)
	{
		int out = 0;
		switch(in)
		{
		case '0':
			out = 0;
			break;
		case '1':
			out = 1;
			break;
		case '2':
			out = 2;
			break;
		case '3':
			out = 3;
			break;
		case '4':
			out = 4;
			break;
		case '5':
			out = 5;
			break;
		case '6':
			out = 6;
			break;
		case '7':
			out = 7;
			break;
		case '8':
			out = 8;
			break;
		case '9':
			out = 9;
			break;
			case 'A': case 'a':
			out = 10;
			break;
			case 'B': case 'b':
			out = 11;
			break;
			case 'C':case  'c':
			out = 12;
			break;
			case 'D': case  'd':
			out = 13;
			break;
			case 'E':case  'e':
			out = 14;
			break;
			case 'F':case  'f':
			out = 15;
			break;
		default:
			System.out.println("���ش��󣬷�ʮ�����Ƴ���");
			System.exit(-1);
		}
		return out;
	}
	public static char TransformB(int in)
	{
		char out = '0';
		switch(in)
		{
		case 0:
			out = '0';
			break;
		case 1:
			out = '1';
			break;
		case 2:
			out = '2';
			break;
		case 3:
			out = '3';
			break;
		case 4:
			out = '4';
			break;
		case 5:
			out = '5';
			break;
		case 6:
			out = '6';
			break;
		case 7:
			out = '7';
			break;
		case 8:
			out = '8';
			break;
		case 9:
			out = '9';
			break;
		case 10:
			out = 'A';
			break;
		case 11:
			out = 'B';
			break;
		case 12:
			out = 'C';
			break;
		case 13:
			out = 'D';
			break;
		case 14:
			out = 'E';
			break;
		case 15:
			out = 'F';
			break;
		default:
			System.out.println("���ش��󣬷�ʮ�����Ƴ���");
			System.exit(-1);
		}
		return out;
	}
	public static String TransformC(char hex)
	{
		String out = "0000";
		switch(hex)
		{
		case '0':
			out = "0000";
			break;
		case '1':
			out = "0001";
			break;
		case '2':
			out = "0010";
			break;
		case '3':
			out = "0011";
			break;
		case '4':
			out = "0100";
			break;
		case '5':
			out = "0101";
			break;
		case '6':
			out = "0110";
			break;
		case '7':
			out = "0111";
			break;
		case '8':
			out = "1000";
			break;
		case '9':
			out = "1001";
			break;
			case 'A':case  'a':
			out = "1010";
			break;
			case 'B':case  'b':
			out = "1011";
			break;
			case 'C':case  'c':
			out = "1100";
			break;
			case 'D':case  'd':
			out = "1101";
			break;
			case 'E':case  'e':
			out = "1110";
			break;
			case 'F':case  'f':
			out = "1111";
			break;
		default:
			System.out.println("���ش��󣬷�ʮ�����Ƴ���");
			System.exit(-1);
		}
		return out;
	}
	public static int HEX_TO_INT(String hex)
	{
		// ��λ����char����ĵ�λ��
//		char[] hexChar = hex.toCharArray();
//		if(hexChar.length != 4)
//		{
//			System.out.println("���ش������ݴ���ʱ����2�ֽ�");
//			System.exit(-1);
//		}
//		int out = 0;
//		for(int i=0; i<4; ++i)
//		{
//			int temp;
//			temp = TransformA(hexChar[i]);
//			// System.out.println(temp);
//			out = out + temp * (int)Math.pow(16.0, (double)(3-i));
//		}
//		return out;
		if(hex.length() != 4)
		{
			System.err.println("���ش������ݴ���ʱ����2�ֽ�");
			System.exit(-1);
		}
		int out = 0;
		try {
			out = Integer.parseInt(hex, 16);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return out;
	}
	public static String INT_TO_HEX(int dec)
	{
		int temp = dec;
		char[] outChar = new char[4];
		for(int i=0;i<4;++i)
		{
			int remainder = temp%16;
			outChar[3-i] = Block.TransformB(remainder);
			temp = temp/16;
		}
		String out = String.copyValueOf(outChar);
		return out;
	}
	public static String HEX_TO_BIN(String hex)
	{
		String bin = "";
		char[] hexChar = hex.toCharArray();
		for(int i=0; i<hex.length(); ++i)
		{
			String temp = TransformC(hexChar[i]);
			bin = bin + temp;
		}
		return bin;
	}
	// ��blcok����start��ʼ��length�����ݵ�Ԫ��ת��Ϊ������������͵�ַΪ��λ
	public static String getBinFromDatasInBlock(int start, int length, Block block)
	{
		String out = "";
		for(int i=start; i<start+length; ++i)
		{
			String data = block.getDataAtIndex(i);
			out = out + HEX_TO_BIN(data);
		}
		return out;
	}
}
