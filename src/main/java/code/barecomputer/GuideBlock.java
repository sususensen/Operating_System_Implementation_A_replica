package code.barecomputer;

public class GuideBlock extends Block
{
	private String Name;
	private int loadClock;
	public GuideBlock() 
	{
		// TODO Auto-generated constructor stub
		super();
		setName("Null");
		setLoadClock(0);
	}
	public void loadFromDisk(Block block)
	{
		setLoadClock(Clock.getClock());
		setName("Guide Block");
	}
	public int getLoadClock() {
		return loadClock;
	}
	public void setLoadClock(int loadClock) {
		this.loadClock = loadClock;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
}
