package code.filemanage;

public class ProcessFileTable 
{
	public boolean isHave;		// �Ƿ���
	public String descriptor;	// �ļ���������R/W
	public int inodeNumber;
	
	public String getString()
	{
		String out = "";
		if(isHave)
		{
			out += "\t�ļ�������: " + descriptor;
			out += "\tInode��: "+inodeNumber+"\n";
		}
		else
			out += "\tû�д��ļ�\n";
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