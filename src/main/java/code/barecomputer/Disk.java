package code.barecomputer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import code.Computer;
import code.filemanage.FreeBlocksGroupsLink;
import code.filemanage.Inode;

public class Disk 
{
	public static final int DISK_NUM = 1;
	public static final int CYLINDER_NUM = 10;
	public static final int TRACK_NUM = 32;
	public static final int SECTOR_NUM = 64;
	public static String DiskStr = "disk";
	public static String Cylinder = "cylinder_";
	public static String Track = "track_";
	public static String Sector = "sector_";
	public final static int GUIDE_BLOCK_NUM = 1;
	public final static int SUPER_BLOCK_NUM = 1;
	public static final int INODE_BLOCK_NUM = 118;
	public static final int PAGETABLE_BLOCK_NUM = 8;
	public static final int EXCHANGE_BLOCK_NUM = 384;
	public static final int JOB_BLOCK_NUM = 256;
	public static final int CODE_BLOCK_NUM = 256;
	public static final int OTHER_BLOCK_NUM = 1024;
	public static final int FILE_BLOCK_NUM = 18432;
	public static final int OS_BLOCK_NUM = 4;
	public static final int DATA_BLOCK_FOR_A_PROCESS_NUM = 32;
	public static int FREE_NUM = 0;		// 文件区中的空闲块数
	
	public void calculateFreeNum()
	{
		String temp = Blocks[0][0][1].getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK);
		int num = Block.HEX_TO_INT(temp);
		temp = Blocks[0][0][1].getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK + 1);
		int start = Block.HEX_TO_INT(temp);
		while(start != FreeBlocksGroupsLink.LAST_GROUP_FLAG)
		{
			int[] location = Disk.BlockTransform_Index_To_Location(start);
			temp = Blocks[location[0]][location[1]][location[2]].getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK);
			num += Block.HEX_TO_INT(temp);
			temp = Blocks[location[0]][location[1]][location[2]].getDataAtIndex(FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK + 1);
			start = Block.HEX_TO_INT(temp);
		}
		FREE_NUM = num;
	}
	
	public static final int GUIDE_BLOCK_INDEX = 0;
	public static final int SUPER_BLOCK_INDEX = 1;
	public static final int INODE_BLOCK_START_INDEX = 2;
	public static final int[] PAGETABLE_BLOCK_INDEX = {120, 121, 122, 123, 124, 125, 126, 127};
	public static final int[] DATA_BLOCK_FOR_A_PROCESS_START_INDEX = {128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448, 480};
	public static final int JOB_BLOCK_START_INDEX = 512;
	public static final int CODE_BLOCK_START_INDEX = 768;
	public static final int FILE_BLOCK_START_INDEX = 2048;
	public static final int[] OS_BLOCK_INDEX = {2048, 2049, 2050, 2051};
	
	public Block Blocks[][][] = new Block[CYLINDER_NUM][TRACK_NUM][SECTOR_NUM];
	public ArrayList<Inode> inodes = new ArrayList<>();
	
	public Disk() 
	{	
		for(int i=0;i<CYLINDER_NUM;++i)
			for(int j=0;j<TRACK_NUM;++j)
				for(int k=0;k<SECTOR_NUM;++k)
					Blocks[i][j][k] = new Block(false, i, j, k);
	}
	// 加载本地txt文件
	public void doLoad()
	{
		try {
			String Root = System.getProperty("user.dir");
			String diskPath = Root + File.separator + DiskStr;
			System.out.println("本地txt开始装载到模拟磁盘");
			Computer.openInfo += "本地txt开始装载到模拟磁盘\n";
			for(int i=0;i<CYLINDER_NUM;++i)	
			// 每一个柱面
			{
				String cylinderPath = diskPath + File.separator + Cylinder + i;
				for(int j=0;j<TRACK_NUM;++j)
				// 每一个磁道
				{
					String trackPath = cylinderPath + File.separator + Track + String.format("%2d", j);
					for(int k=0;k<SECTOR_NUM;++k)
					// 每一个扇区
					{
						String sectorPath = trackPath + File.separator + Sector + String.format("%2d", k) + ".txt";
						File openSectorFile = new File(sectorPath);
						BufferedReader br = new BufferedReader(new FileReader(openSectorFile));
						String lineText = "";
						int index = 0;
						while((lineText = br.readLine()) != null)
						{
							Blocks[i][j][k].setDataAtIndex(index, lineText);
							index++;
							if(index == 256)
								break;
						}
						br.close();
					}
				}
				System.out.println("装载" + i + "号柱面完成");
				Computer.openInfo += String.format("装载%d号柱面完成\n", i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		calculateFreeNum();
		System.out.println("本地txt装载到模拟磁盘完毕");
		Computer.openInfo += "本地txt装载到模拟磁盘完毕\n";
	}
	
	
	
	/**
	 * 磁盘写函数
	 * 函数名有set的不设置TXT本地文件写回
	 * 函数名有write的，可以通过设置最后一个参数来确定是否写回TXT本地文件
	 * */
	// 设置Index号虚拟磁盘上第Row行的内容
	public void setDataAtRow_In_BlockAtIndex(int index, int row, String input)
	{
		int location[] = Disk.BlockTransform_Index_To_Location(index);
		Blocks[location[0]][location[1]][location[2]].setDataAtIndex(row, input);
	}
	// 设置Location位置虚拟磁盘上第Row行的内容
	public void setDataAtRow_In_BlockAtLocation(int cyliner, int track, int sector, int row, String input)
	{
		Blocks[cyliner][track][sector].setDataAtIndex(row, input);
	}
	// 写入成组链接的空闲块链（用于成组链接中的分配与回收）
	// flag=true，表示修改虚拟磁盘的同时，也写回本地TXT文件
	// flag-false，表示只修改虚拟磁盘中的内容
	public void writeFreeBlocksGroupsLink(int number, FreeBlocksGroupsLink link, boolean flag)
	{
		// 修改虚拟磁盘的内容
		String freeBlockNumString = Block.INT_TO_HEX(link.getNum());
		int index = number;
		int row = FreeBlocksGroupsLink.SAVE_START_AT_DISK_BLOCK;
		this.setDataAtRow_In_BlockAtIndex(index, row, freeBlockNumString);
		int[] temp = link.getAllFreeBlocksInGroup();
		for(int i=0; i<link.getNum(); ++i)
		{
			String s = Block.INT_TO_HEX(temp[i]);
			this.setDataAtRow_In_BlockAtIndex(index, row + i + 1, s);
		}
		if(flag)
		{
			// 写回TXT文件
			int location[] = BlockTransform_Index_To_Location(number);
			String Root = System.getProperty("user.dir");
			String Path = Root + File.separator + DiskStr + 
					File.separator + Cylinder + String.format("%2d", location[0]) + 
					File.separator + Track + String.format("%2d", location[1]) +
					File.separator + Sector + String.format("%2d", location[2]);
			try {
				File outfile = new File(Path);
				FileWriter fWriterA = new FileWriter(outfile);
				fWriterA.write("");
				fWriterA.flush();
				fWriterA.close();
				FileWriter fWriterB = new FileWriter(outfile, true);
				Block block = this.getBlockAtIndex(number);
				for(int i=0; i<Block.MAX_SIZE; ++i)
				{
					 String writeData = block.getDataAtIndex(i) + "\n";
					 fWriterB.write(writeData);
				}
				fWriterB.flush();
				fWriterB.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	
	
	// 把柱面，磁道，扇区转换为磁盘块号
	public static int BlockTransform_Location_To_Index(int cyliner, int track, int sector)
	{
		int temp;
		temp = cyliner*(TRACK_NUM*SECTOR_NUM) + track*SECTOR_NUM + sector;
		if(cyliner>=CYLINDER_NUM || track>=TRACK_NUM || sector>=SECTOR_NUM)
		{
			System.err.println("柱面，磁道，扇区转换为磁盘块号中柱面，磁道，扇区号不合法");
			System.exit(-1);
		}
		if(temp >= CYLINDER_NUM*TRACK_NUM*SECTOR_NUM)
		{
			System.err.println("柱面，磁道，扇区转换为磁盘块号中超出块号的最大表示范围");
			System.exit(-1);
		}
		return temp;
	}
	// 把磁盘块号转换为柱面，磁道，扇区
	public static int[] BlockTransform_Index_To_Location(int index)
	{
		if(index >= CYLINDER_NUM*TRACK_NUM*SECTOR_NUM)
		{
			System.err.println("磁盘块号转换为柱面，磁道，扇区中超出块号的最大表示范围");
			System.exit(-1);
		}
		int cyliner = 0;
		int track = 0;
		int sector = 0;
		cyliner = index / (TRACK_NUM * SECTOR_NUM);
		track = (index % (TRACK_NUM * SECTOR_NUM)) / SECTOR_NUM;
		sector = (index % (TRACK_NUM * SECTOR_NUM)) % SECTOR_NUM;
		int[] temp = {cyliner, track, sector};
		return temp;
	}
	// 获取第index块号的块
	public Block getBlockAtIndex(int index)
	{
		Block block = new Block();
		int location[] = Disk.BlockTransform_Index_To_Location(index);
		block = Blocks[location[0]][location[1]][location[2]];
		return block;
	}
	// 获取在磁盘某一位置上的块
	public Block getBlockAtLocation(int cyliner, int track, int sector)
	{
		Block block = Blocks[cyliner][track][sector];
		return block;
	}
}
