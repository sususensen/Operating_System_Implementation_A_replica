package code.filemanage;

import code.barecomputer.Block;
import code.barecomputer.Language;

public class Inode 
{
	// 一般创建inode时，另其inode号等于磁盘的物理index
	public static final String[] FILE_TYPES = {"NULL", "DIR", "TXT", "EXE"};
	public static final int DIRECT_ADDRESS_NUM = 8;
	public static final int MAX_BYTE_SIZE = 64;	// 64B
	public static final int MAX_SIZE = 32;		// 32个存储单元
	public static final int[] START_INDEX_IN_BLOCK = {0, 32, 64, 96, 128, 160, 192, 224};
	private int Number;					// Inode号
	private int fileType;				// 文件类型：DIR / TXT
	private boolean filePermission_R; 	// 用户读权限
	private boolean filePermission_W; 	// 用户写权限
	private boolean filePermission_X; 	// 用户使用权限
	private MyDate createDate;			// 文件创建时间
	private MyDate modifyDate;			// 文件修改时间
	private int linkNum;				// 文件的硬链接数
	private int quoteNum;				// 内存引用数
	private int blockNum;				// 文件占的块数
	private int directAddressList[];	// 直接引用地址
	private int primaryReferenceAddress;	// 一级引用地址
	private int secondaryReferenceAddress;	// 二级引用地址
	private String fileName;			// 文件名	
	
	public String getString(int index)
	{
		String out = "";
		out += "文件名: "+fileName+"\n";
		out += "Inode号: "+Number+"\n";
		out += "Inode位置: "+index+"\n";
		out += "文件类型: "+FILE_TYPES[fileType]+"\n";
		out += "读权限: "+filePermission_R+"\n";
		out += "写权限: "+filePermission_W+"\n";
		out += "用权限: "+filePermission_X+"\n";
		out += "创建时间: "+createDate.getDateString()+"\n";
		out += "修改时间: "+modifyDate.getDateString()+"\n";
		out += "文件大小: "+blockNum*512+"B\n";
		out += "直接地址:\n";
		if(blockNum <= 8)
		{
			for(int i=0; i<blockNum; ++i)
				out += "\t"+directAddressList[i]+"\n";
			out += "一级间接地址:\n\tnull\n";
			out += "二级间接地址:\n\tnull\n";
		}
		else 
		{
			for(int i=0; i<8; ++i)
				out += "\t"+directAddressList[i]+"\n";
			out += "一级间接地址:\n";
			out += "\t"+primaryReferenceAddress+"\n";
			out += "二级间接地址:\n";
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
	 * 利用磁盘中Inode存储的内容构建Inode实例
	 * 传入参数为String[32]
	 * */
	public void doIitialize(String[] dateInBlockString)
	{
		if(dateInBlockString.length != 32)
		{
			System.err.println("严重错误，初始化Inode时传入的块内数据不等于Inode大小(64B)");
			System.exit(-1);
		}
		try {
			int[] dateInBlockInt = new int[Inode.MAX_SIZE];
			// 16进制转换为10进制
			for(int i=0; i<Inode.MAX_SIZE; ++i)
				dateInBlockInt[i] = Block.HEX_TO_INT(dateInBlockString[i]);
			// 获得inode号
			Number = dateInBlockInt[0];
			// 获得文件类型
			fileType = dateInBlockInt[1];
			// 获得文件权限
			filePermission_R = getPermissionFromInt(dateInBlockInt[2]);
			filePermission_W = getPermissionFromInt(dateInBlockInt[3]);
			filePermission_X = getPermissionFromInt(dateInBlockInt[4]);
			// 获取创建，修改日期
			createDate.setDate(dateInBlockInt[5], dateInBlockInt[6], dateInBlockInt[7], dateInBlockInt[8], dateInBlockInt[9]);
			modifyDate.setDate(dateInBlockInt[10], dateInBlockInt[11], dateInBlockInt[12], dateInBlockInt[13], dateInBlockInt[14]);
			// 获取硬链接数
			linkNum = dateInBlockInt[15];
			// 获取内存引用数
			quoteNum = dateInBlockInt[16];
			// 文件占的块数
			blockNum = dateInBlockInt[17];
			// 直接地址
			for(int i=0; i<DIRECT_ADDRESS_NUM; ++i)
				directAddressList[i] = dateInBlockInt[i+18];
			// 一级间接地址
			primaryReferenceAddress = dateInBlockInt[26];
			// 二级间接地址
			secondaryReferenceAddress = dateInBlockInt[27];
			// 文件名
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
	 * 将Inode存储内容转化为磁盘可识别格式
	 * 返回值为String[32]
	 * */
	public String[] getInodeString()
	{
		String[] out = new String[32];
		// Inode号
		out[0] = Block.INT_TO_HEX(Number);
		// 文件类型
		out[1] = Block.INT_TO_HEX(fileType);
		// 文件权限
		out[2] = Block.INT_TO_HEX(getIntFromPermission(filePermission_R));
		out[3] = Block.INT_TO_HEX(getIntFromPermission(filePermission_W));
		out[4] = Block.INT_TO_HEX(getIntFromPermission(filePermission_X));
		// 文件创建日期
		int temp[] = createDate.getDate();
		out[5] = Block.INT_TO_HEX(temp[0]);
		out[6] = Block.INT_TO_HEX(temp[1]);
		out[7] = Block.INT_TO_HEX(temp[2]);
		out[8] = Block.INT_TO_HEX(temp[3]);
		out[9] = Block.INT_TO_HEX(temp[4]);
		// 文件修改日期
		temp = modifyDate.getDate();
		out[10] = Block.INT_TO_HEX(temp[0]);
		out[11] = Block.INT_TO_HEX(temp[1]);
		out[12] = Block.INT_TO_HEX(temp[2]);
		out[13] = Block.INT_TO_HEX(temp[3]);
		out[14] = Block.INT_TO_HEX(temp[4]);
		// 硬链接数
		out[15] = Block.INT_TO_HEX(linkNum);
		// 内存引用数
		out[16] = Block.INT_TO_HEX(quoteNum);
		// 文件占的块数
		out[17] = Block.INT_TO_HEX(blockNum);
		// 直接地址
		for(int i=0; i<DIRECT_ADDRESS_NUM; ++i)
			out[i+18] = Block.INT_TO_HEX(directAddressList[i]);
		// 一级间接地址
		out[26] = Block.INT_TO_HEX(primaryReferenceAddress);
		// 二级间接地址
		out[27] = Block.INT_TO_HEX(secondaryReferenceAddress);
		// 文件名
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
	
	// 打印Inode内容
	public void showInode()
	{
		System.out.println("-----Inode内容-----");
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