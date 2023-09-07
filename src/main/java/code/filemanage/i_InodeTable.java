package code.filemanage;

import java.util.ArrayList;

import code.barecomputer.Block;
import code.barecomputer.Disk;
import code.barecomputer.SuperBlock;
import code.Computer;

/**
 * 该表用于保存磁盘中所有的inode信息
 * （对应文件名，inode号，物理位置）
 * */
public class i_InodeTable 
{
	public ArrayList<i_Inode> i_Inodes;
	public int Number;		// 当前磁盘中的inode数目
	
	public i_InodeTable() 
	{
		Number = 0;
		i_Inodes = new ArrayList<i_Inode>();
	}
	public i_InodeTable(i_InodeTable copy)
	{
		Number = copy.Number;
		i_Inodes = new ArrayList<i_Inode>();
		for(int i=0;i<Number;++i)
		{
			i_Inode temp = copy.getI_Inode(i);
			i_Inodes.add(temp);
		}
	}
	public i_Inode getI_Inode(int index)
	{
		return i_Inodes.get(index);
	}
	// 在磁盘中加载（开机时）
	public void loadFromDisk(Disk disk, SuperBlock sBlock)
	{
		boolean[][] map = sBlock.getFreeInodeInDiskMap();
		// 遍历inode位示图，找到非空闲inode的位置
		int index = 0;
		for(int x=0; x<Disk.INODE_BLOCK_NUM; ++x)
		{
			for(int y=0; y<Block.INODE_NUM_IN_BLOCK; ++y)
			{
				if(map[x][y])
				{
					// 占用，即有inode存储
					Block block = disk.getBlockAtIndex(Disk.INODE_BLOCK_START_INDEX + x);
					String[] dataInInode = block.getStringArrayFromData(Inode.START_INDEX_IN_BLOCK[y], Inode.MAX_SIZE);
					Inode tempInode = new Inode();
					tempInode.doIitialize(dataInInode);
					Computer.disk.inodes.add(tempInode);
					String name = tempInode.getFileName();
					int number = tempInode.getInodeNumber();
					int blockNumber = Block.HEX_TO_INT(dataInInode[18]);
					this.addAnI_Inode(name, index, number, false, blockNumber);
					index++;
				}
			}
		}
	}
	// 添加一个新的表项
	public void addAnI_Inode(String name, int index, int number, boolean isload, int blockNumber)
	{
		i_Inode temp = new i_Inode(name, index, number, isload);
		temp.addNext(blockNumber);
		i_Inodes.add(temp);
		Number++;
	}
	// 删除一个inode号为number的表项
	public void removeAnI_Inode(int number)
	{
		for(int i=0; i<Number; ++i)
		{
			if(number == i_Inodes.get(i).getNumber())
			{
				i_Inodes.remove(i);
				Number--;
				break;
			}
		}
	}
	// 根据文件名获得inode号
	public int getNumberFromName(String name)
	{
		for(int i=0;i<Number;++i)
		{
			String temp = i_Inodes.get(i).getFileName();
			if(temp.equals(name))
				return i_Inodes.get(i).getNumber();
		}
		return -1;
	}
	// 根据文件名获得inode物理位置
	public int getIndexFromName(String name)
	{
		for(int i=0;i<Number;++i)
		{
			String temp = i_Inodes.get(i).getFileName();
			if(temp.equals(name))
				return i_Inodes.get(i).getIndex();
		}
		return -1;
	}
	public void loadToMemory(int number)
	{
		for(int i=0;i<Number;++i)
		{
			i_Inode temp = i_Inodes.get(i);
			if(number == temp.getNumber())
				temp.loadMemory();
		}
	}
	public void unloadToMemory(int number)
	{
		for(int i=0;i<Number;++i)
		{
			i_Inode temp = i_Inodes.get(i);
			if(number == temp.getNumber())
				temp.unloadMemory();
		}
	}
	// 打印i_InodeTable
	public void show()
	{
		System.out.println("-----i_InodeTable-----");
		System.out.println("Number of Inodes in Disk: "+Number);
		System.out.println("(FileName, InodeNumber, InodeIndexInDisk, isLoadMemory)");
		for(int i=0;i<Number;++i)
		{
			i_Inode temp = i_Inodes.get(i);
			temp.show();
		}
	}
}
