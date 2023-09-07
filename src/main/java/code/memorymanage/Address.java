package code.memorymanage;

import code.barecomputer.Block;

public class Address 
{
	private int blockNumber;	// ÄÚ´æ¿éºÅ»ò´ÅÅÌ¿éºÅ
	private int Offset;			// ¿éÄÚÆ«ÒÆ

	public Address()
	{
		blockNumber = -1;
		Offset = -1;
	}
	public Address(int number, int offset)
	{
		blockNumber = number;
		Offset = offset;
	}
	public Address(Address copy)
	{
		blockNumber = copy.blockNumber;
		Offset = copy.Offset;
	}
	public void set(int number, int offset)
	{
		blockNumber = number;
		Offset = offset;
	}
	public void setBlockNumber(int number)
	{
		blockNumber = number;
	}
	public void setOffset(int offset)
	{
		Offset = offset;
	}
	public int getBlockNumber()
	{
		return blockNumber;
	}
	public int getOffset()
	{
		return Offset;
	}
	public int get()
	{
		int out = blockNumber*Block.MAX_SIZE + Offset;
		return out;
	}
}
