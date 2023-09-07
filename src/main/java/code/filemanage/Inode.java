package code.filemanage;

import code.barecomputer.Block;
import code.barecomputer.Language;

public class Inode 
{
	// һ�㴴��inodeʱ������inode�ŵ��ڴ��̵�����index
	public static final String[] FILE_TYPES = {"NULL", "DIR", "TXT", "EXE"};
	public static final int DIRECT_ADDRESS_NUM = 8;
	public static final int MAX_BYTE_SIZE = 64;	// 64B
	public static final int MAX_SIZE = 32;		// 32���洢��Ԫ
	public static final int[] START_INDEX_IN_BLOCK = {0, 32, 64, 96, 128, 160, 192, 224};
	private int Number;					// Inode��
	private int fileType;				// �ļ����ͣ�DIR / TXT
	private boolean filePermission_R; 	// �û���Ȩ��
	private boolean filePermission_W; 	// �û�дȨ��
	private boolean filePermission_X; 	// �û�ʹ��Ȩ��
	private MyDate createDate;			// �ļ�����ʱ��
	private MyDate modifyDate;			// �ļ��޸�ʱ��
	private int linkNum;				// �ļ���Ӳ������
	private int quoteNum;				// �ڴ�������
	private int blockNum;				// �ļ�ռ�Ŀ���
	private int directAddressList[];	// ֱ�����õ�ַ
	private int primaryReferenceAddress;	// һ�����õ�ַ
	private int secondaryReferenceAddress;	// �������õ�ַ
	private String fileName;			// �ļ���	
	
	public String getString(int index)
	{
		String out = "";
		out += "�ļ���: "+fileName+"\n";
		out += "Inode��: "+Number+"\n";
		out += "Inodeλ��: "+index+"\n";
		out += "�ļ�����: "+FILE_TYPES[fileType]+"\n";
		out += "��Ȩ��: "+filePermission_R+"\n";
		out += "дȨ��: "+filePermission_W+"\n";
		out += "��Ȩ��: "+filePermission_X+"\n";
		out += "����ʱ��: "+createDate.getDateString()+"\n";
		out += "�޸�ʱ��: "+modifyDate.getDateString()+"\n";
		out += "�ļ���С: "+blockNum*512+"B\n";
		out += "ֱ�ӵ�ַ:\n";
		if(blockNum <= 8)
		{
			for(int i=0; i<blockNum; ++i)
				out += "\t"+directAddressList[i]+"\n";
			out += "һ����ӵ�ַ:\n\tnull\n";
			out += "������ӵ�ַ:\n\tnull\n";
		}
		else 
		{
			for(int i=0; i<8; ++i)
				out += "\t"+directAddressList[i]+"\n";
			out += "һ����ӵ�ַ:\n";
			out += "\t"+primaryReferenceAddress+"\n";
			out += "������ӵ�ַ:\n";
			out += "\t"+secondaryReferenceAddress+"\n";
		}
		return out;
	}
	public Inode() 
	{
		Number = -1;
		fileType = 0;
		filePermission_R = false;
		filePermission_W = false;
		filePermission_X = false;
		createDate = new MyDate();
		modifyDate = new MyDate();
		linkNum = 0;
		quoteNum = 0;
		blockNum = 0;
		directAddressList = new int[DIRECT_ADDRESS_NUM];
		primaryReferenceAddress = 0;
		secondaryReferenceAddress = 0;
		fileName = "";
	}
	public Inode(Inode copy)
	{
		Number = copy.Number;
		fileType = copy.fileType;
		filePermission_R = copy.filePermission_R;
		filePermission_W = copy.filePermission_W;
		filePermission_X = copy.filePermission_X;
		createDate = copy.createDate;
		modifyDate = copy.modifyDate;
		linkNum = copy.linkNum;
		quoteNum = copy.quoteNum;
		blockNum = copy.blockNum;
		for(int i=0;i<DIRECT_ADDRESS_NUM;++i)
			directAddressList[i] = copy.directAddressList[i];
		primaryReferenceAddress = copy.primaryReferenceAddress;
		secondaryReferenceAddress = copy.secondaryReferenceAddress;
		fileName = copy.fileName;
	}
	public static boolean getPermissionFromInt(int Int)
	{
		if(Int == 0)
			return false;
		else
			return true;
	}
	public static int getIntFromPermission(boolean Permission)
	{
		if(Permission)
			return 0;
		else
			return 1;
	}
	public String getFileName()
	{
		return fileName;
	}
	public int getInodeNumber()
	{
		return Number;
	}
	
	/**
	 * ���ô�����Inode�洢�����ݹ���Inodeʵ��
	 * �������ΪString[32]
	 * */
	public void doIitialize(String[] dateInBlockString)
	{
		if(dateInBlockString.length != 32)
		{
			System.err.println("���ش��󣬳�ʼ��Inodeʱ����Ŀ������ݲ�����Inode��С(64B)");
			System.exit(-1);
		}
		try {
			int[] dateInBlockInt = new int[Inode.MAX_SIZE];
			// 16����ת��Ϊ10����
			for(int i=0; i<Inode.MAX_SIZE; ++i)
				dateInBlockInt[i] = Block.HEX_TO_INT(dateInBlockString[i]);
			// ���inode��
			Number = dateInBlockInt[0];
			// ����ļ�����
			fileType = dateInBlockInt[1];
			// ����ļ�Ȩ��
			filePermission_R = getPermissionFromInt(dateInBlockInt[2]);
			filePermission_W = getPermissionFromInt(dateInBlockInt[3]);
			filePermission_X = getPermissionFromInt(dateInBlockInt[4]);
			// ��ȡ�������޸�����
			createDate.setDate(dateInBlockInt[5], dateInBlockInt[6], dateInBlockInt[7], dateInBlockInt[8], dateInBlockInt[9]);
			modifyDate.setDate(dateInBlockInt[10], dateInBlockInt[11], dateInBlockInt[12], dateInBlockInt[13], dateInBlockInt[14]);
			// ��ȡӲ������
			linkNum = dateInBlockInt[15];
			// ��ȡ�ڴ�������
			quoteNum = dateInBlockInt[16];
			// �ļ�ռ�Ŀ���
			blockNum = dateInBlockInt[17];
			// ֱ�ӵ�ַ
			for(int i=0; i<DIRECT_ADDRESS_NUM; ++i)
				directAddressList[i] = dateInBlockInt[i+18];
			// һ����ӵ�ַ
			primaryReferenceAddress = dateInBlockInt[26];
			// ������ӵ�ַ
			secondaryReferenceAddress = dateInBlockInt[27];
			// �ļ���
			fileName = "";
			for(int i=0;i<4;++i)
			{
				String str = dateInBlockString[i+28];
				String[] out = Block.getByte(str);
				fileName = fileName + Language.getValue(out[0]);
				fileName = fileName + Language.getValue(out[1]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * ��Inode�洢����ת��Ϊ���̿�ʶ���ʽ
	 * ����ֵΪString[32]
	 * */
	public String[] getInodeString()
	{
		String[] out = new String[32];
		// Inode��
		out[0] = Block.INT_TO_HEX(Number);
		// �ļ�����
		out[1] = Block.INT_TO_HEX(fileType);
		// �ļ�Ȩ��
		out[2] = Block.INT_TO_HEX(getIntFromPermission(filePermission_R));
		out[3] = Block.INT_TO_HEX(getIntFromPermission(filePermission_W));
		out[4] = Block.INT_TO_HEX(getIntFromPermission(filePermission_X));
		// �ļ���������
		int temp[] = createDate.getDate();
		out[5] = Block.INT_TO_HEX(temp[0]);
		out[6] = Block.INT_TO_HEX(temp[1]);
		out[7] = Block.INT_TO_HEX(temp[2]);
		out[8] = Block.INT_TO_HEX(temp[3]);
		out[9] = Block.INT_TO_HEX(temp[4]);
		// �ļ��޸�����
		temp = modifyDate.getDate();
		out[10] = Block.INT_TO_HEX(temp[0]);
		out[11] = Block.INT_TO_HEX(temp[1]);
		out[12] = Block.INT_TO_HEX(temp[2]);
		out[13] = Block.INT_TO_HEX(temp[3]);
		out[14] = Block.INT_TO_HEX(temp[4]);
		// Ӳ������
		out[15] = Block.INT_TO_HEX(linkNum);
		// �ڴ�������
		out[16] = Block.INT_TO_HEX(quoteNum);
		// �ļ�ռ�Ŀ���
		out[17] = Block.INT_TO_HEX(blockNum);
		// ֱ�ӵ�ַ
		for(int i=0; i<DIRECT_ADDRESS_NUM; ++i)
			out[i+18] = Block.INT_TO_HEX(directAddressList[i]);
		// һ����ӵ�ַ
		out[26] = Block.INT_TO_HEX(primaryReferenceAddress);
		// ������ӵ�ַ
		out[27] = Block.INT_TO_HEX(secondaryReferenceAddress);
		// �ļ���
		char[] fileNameArray = fileName.toCharArray();
		String str = "5050505050505050";
		char[] strCharArray = str.toCharArray();
		int fileNameLength = fileNameArray.length;
		for(int i=0;i<fileNameLength;++i)
		{
			char[] a = Language.getKey(String.valueOf(fileNameArray[i])).toCharArray();
			strCharArray[i*2] = a[0];
			strCharArray[i*2+1] = a[1];
		}
		return out;
	}
	
	// ��ӡInode����
	public void showInode()
	{
		System.out.println("-----Inode����-----");
		System.out.println("MAX_BYTE_SIZE: " + MAX_BYTE_SIZE);
		System.out.println("MAX_SIZE: " + MAX_SIZE);
		System.out.println("DIRECT_ADDRESS_NUM: " + DIRECT_ADDRESS_NUM);
		System.out.println("File Name: " + fileName);
		System.out.println("Inode Number: " + Number);
		System.out.println("File Type: " + FILE_TYPES[fileType]);
		System.out.print("Permission: ");
		if(filePermission_R)
			System.out.print("R");
		else
			System.out.print("-");
		if(filePermission_W)
			System.out.print("W");
		else
			System.out.print("-");
		if(filePermission_X)
			System.out.println("X");
		else
			System.out.println("-");
		System.out.println("CreateDate: " + createDate.getDateString());
		System.out.println("ModifyDate: " + modifyDate.getDateString());
		System.out.println("LinkNum: " + linkNum);
		System.out.println("QuoteNum: " + quoteNum);
		System.out.println("BlockNum: " + blockNum);
		System.out.println("Direct Address: ");
		if(blockNum <= 8)
		{
			for(int i=0; i<blockNum; ++i)
				System.out.println("\t-" + directAddressList[i]);
			for(int i=0; i<(DIRECT_ADDRESS_NUM-blockNum); ++i)
				System.out.println("\t-null");
			System.out.println("Primary Reference Address: ");
			System.out.println("\t-null");
			System.out.println("Secondary Reference Address: ");
			System.out.println("\t-null");
		}
		else 
		{
			for(int i=0; i<DIRECT_ADDRESS_NUM; ++i)
				System.out.println("\t-" + directAddressList[i]);
			System.out.println("Primary Reference Address: ");
			System.out.println("\t-" + primaryReferenceAddress);
			System.out.println("Secondary Reference Address: ");
			System.out.println("\t-" + secondaryReferenceAddress);
		}
	}

//	public static void main(String[] args) 
//	{
//		ArrayList<Integer> Temp = new ArrayList<>(2);
//		Temp.add(0);
//		Temp.add(3);
//		Temp.add(2);
//		for(int i=0;i<Temp.size();++i)
//		{
//			System.out.print(Temp.get(i)+" ");
//		}
//		System.out.println("");
//		Temp.remove(1);
//		for(int i=0;i<Temp.size();++i)
//		{
//			System.out.print(Temp.get(i)+" ");
//		}
//		System.out.println("");
//		MyDate date = new MyDate(2018, 3, 4, 7, 0);
//		int[] temp = date.getDate();
//		for(int i=0; i<temp.length; ++i)
//		{
//			System.out.print(temp[i]);
//			System.out.print(" ");
//		}
//		System.out.println("");
//		System.out.println(date.getDateString());
//	}
}



class MyDate
{
	private int Year;
	private int Mouth;
	private int Day;
	private int Hour;
	private int Minute;
	
	public MyDate() 
	{
		Year = 0;
		Mouth = 0;
		Day = 0;
		Hour = 0;
		Minute = 0;
	}
	public MyDate(int year, int mouth, int day, int hour, int minute)
	{
		Year = year;
		Mouth = mouth;
		Day = day;
		Hour = hour;
		Minute = minute;
	}
	public MyDate(MyDate copy)
	{
		Year = copy.Year;
		Mouth = copy.Mouth;
		Day = copy.Day;
		Hour = copy.Hour;
		Minute = copy.Minute;
	}
	public void setDate(int year, int mouth, int day, int hour, int minute)
	{
		Year = year;
		Mouth = mouth;
		Day = day;
		Hour = hour;
		Minute = minute;
	}
	public int[] getDate()
	{
		int[] temp = {Year, Mouth, Day, Hour, Minute};
		return temp;
	}
	public String getDateString()
	{
		String temp = String.format("%d/%d/%d-%02d:%02d", Year, Mouth, Day, Hour, Minute);
		return temp;
	}
	public void setYear(int year)
	{
		Year = year;
	}
	public void setMouth(int mouth)
	{
		Mouth = mouth;
	}
	public void setDay(int day)
	{
		Day = day;
	}
	public void setHour(int hour)
	{
		Hour = hour;
	}
	public void setMinute(int minute)
	{
		Minute = minute;
	}
	public int getYear()
	{
		return Year;
	}
	public int getMouth()
	{
		return Mouth;
	}
	public int getDay()
	{
		return Day;
	}
	public int getHour()
	{
		return Hour;
	}
	public int getMinute()
	{
		return Minute;
	}
}