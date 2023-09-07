package code.barecomputer;

public class Instruction 
{
	public final static int SIZE = 1;	// һ��ָ��ռ������
	public final static int VALUE = 0;
	public final static int CALCULATE = 1;
	public final static int READ = 2;
	public final static int WRITE = 3;
	public final static int INPUT = 4;
	public final static int OUTPUT = 5;
	public final static int PRINTER = 6;
	public final static int CAMERA = 7;
	public final static int[] BLOCK_TIME = {0, 0, 2, 2, 4, 4, 6, 20};
	public final static String[] TYPE = {"value", "calculate", "read", "write", "input", "output", "printer", "camera"};
	public final static String[] TYPE_FOR_VALUE = {"int", "short", "long", "char", "bool", "string"};
	public final static String[] TYPE_FOR_CALCULATE = {"add", "sub", "mul", "div"};
	private int type;					// ָ������
	// �������Ը���type��ȡֵ�в�ͬ������
	private int operand;
	// ���캯��
	public Instruction() 
	{
		type = -1;
		operand = -1;
	}
	// ���캯��
	public Instruction(int Type, int op)
	{ 
		type = Type;
		operand = op;
	}
	// �������캯��
	public Instruction(Instruction temp)
	{
		this.type = temp.type;
		this.operand = temp.operand;
	}
	// �Ӷ�ȡ��input��һ�������й���
	public void make(String data)
	{
		String[] subData = data.split(",");
		type = Integer.valueOf(subData[0]);
		operand = Integer.valueOf(subData[1]);
	}
	// ��������
	// show
	public void show(int pc)
	{
		System.out.print("\t��PC: " + pc + " Type: " + TYPE[type] + " Operand: ");
		if(type == 0)
			System.out.println(TYPE_FOR_VALUE[operand]);
		else if(type == 1)
			System.out.println(TYPE_FOR_CALCULATE[operand]);
		else
			System.out.println(operand);
	}
	public String getInfo()
	{
		if(type == -1)
			return "null";
		String out = String.format("%s, ", TYPE[type]);
		if(type == 0)
			out += TYPE_FOR_VALUE[operand];
		else if(type == 1)
			out += TYPE_FOR_CALCULATE[operand];
		else
			out += operand;
		return out;
	}
	public String get(int pc)
	{
		String out = String.format("\t��PC: %d Type: %s Operand: ", pc, TYPE[type]);
		if(type == 0)
			out += TYPE_FOR_VALUE[operand];
		else if(type == 1)
			out += TYPE_FOR_CALCULATE[operand];
		else
			out += operand;
		out += "\n";
		return out;
	}
	// set
	public void setInstruction(Instruction temp)
	{
		this.type = temp.type;
		this.operand = temp.operand;
	}
	public void setType(int Type)
	{
		type = Type;
	}
	public int getType()
	{
		return type;
	}
	public int getOperand() 
	{
		return operand;
	}
	public void setOperand(int operand) 
	{
		this.operand = operand;
	}
}
