package code.barecomputer;

public class Scene 
{
	private int state;
	private int pc;
	private Instruction ir;
	private Register[] registers;

	public String getString()
	{
		String out = "";
		if(state==0)
			out += "CPU: ÄÚºËÌ¬\n";
		else
			out += "CPU: ÓÃ»§Ì¬\n";
		out += "PC: " + pc + "\n";
		return out;
	}
	public Scene() 
	{
		state = 0;
		pc = 0;
		ir = new Instruction();
		registers = new Register[CPU.REGISTER_NUM];
		for(int i=0;i<CPU.REGISTER_NUM;++i)
			registers[i] = new Register(0);
	}
	public void Initlize()
	{
		pc = 0;
		state = 0;
	}
	public void set(int pc, int state, Instruction ir)
	{
		this.pc = pc;
		this.state = state;
		this.ir = ir;
	}
	public int getPC()
	{
		return pc;
	}
	public int getState()
	{
		return state;
	}
	public Instruction getIR()
	{
		return ir;
	}
}
