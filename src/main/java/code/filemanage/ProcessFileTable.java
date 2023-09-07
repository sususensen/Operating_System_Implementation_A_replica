package code.filemanage;

public class ProcessFileTable 
{
	public boolean isHave;		// 是否有
	public String descriptor;	// 文件描述符，R/W
	public int inodeNumber;
	
	public String getString()
	{
		String out = "";
		if(isHave)
		{
			out += "\t文件描述符: " + descriptor;
			out += "\tInode号: "+inodeNumber+"\n";
		}
		else
			out += "\t没有打开文件\n";
		return out;
	}
	public ProcessFileTable() 
	{
		isHave = false;
		descriptor = "";
		inodeNumber = -1;
	}
	public ProcessFileTable(ProcessFileTable copy)
	{
		isHave = copy.isHave;
		descriptor = copy.descriptor;
		inodeNumber = copy.inodeNumber;
	}
	public void add(String des, int number)
	{
		isHave = true;
		descriptor = des;
		inodeNumber = number;
	}
	public void remove()
	{
		isHave = false;
	}
}