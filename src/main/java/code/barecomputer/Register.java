package code.barecomputer;

// Í¨ÓÃ¼Ä´æÆ÷
public class Register 
{
	public final static int LENGTH = 16;
	private int data;
	public Register() 
	{
		data = 0;
	}
	public Register(int temp)
	{
		data = temp;
	}
	public Register(Register temp)
	{
		data = temp.data;
	}
	public void setData(int Data)
	{
		data = Data;
	}
	public void setZero()
	{
		data = 0;
	}
	public int getData()
	{
		return data;
	}
}
