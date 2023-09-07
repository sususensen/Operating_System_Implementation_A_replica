package code.jobmanage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import code.barecomputer.Clock;

public class JCBPool 
{
	public static final int MAX_JCB_NUM = 64;
	
	public int number;
	public ArrayList<JCB> pool = new ArrayList<JCB>();
	
	public boolean isFull()
	{
		if(MAX_JCB_NUM == number)
			return true;
		return false;
	}
	public JCBPool() 
	{
		number = 0;
	}
	public JCBPool(JCBPool copy)
	{
		number = copy.number;
		pool = new ArrayList<JCB>();
	}
	// ��ȡinput�ļ����е����ݴ���JCBPool
	public void Initialize()
	{
		number = 0;
		String Root = System.getProperty("user.dir");
		String readPath = Root + File.separator + "input" + File.separator + "19218101-jobs-input.txt";
		File readFile = new File(readPath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(readFile));
			String lineText = br.readLine();	// ��ȥ��һ��
			while((lineText = br.readLine()) != null)
			{
				JCB temp = new JCB();
				temp.make(lineText);
				pool.add(temp);
				number++;
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void remove(int index)
	{
		number--;
		pool.remove(index);
	}
	// ���λ�ú�Ϊindex��JCB
	public JCB getJCBAtIndex(int index)
	{
		return pool.get(index);
	}
	// ��ȡn��JCB�ڳ��е�λ��
	public int getIndexFromNumber(int n)
	{
		int index = -1;
		for(int i=0;i<number;++i)
		{
			if(pool.get(i).getJobNumber() == n)
			{
				index = i;
				break;
			}
		}
		return index;
	}
	// ɾ��n�ŵ�JCB
	public void delete(int n)
	{
		int index = getIndexFromNumber(n);
		if(index == -1)
		{
			System.err.println("ɾ��JCB��ʱ������˴����JCB��");
			System.exit(-1);
		}
		pool.remove(index);
		number--;
	}
	// ��ӡJCB��
	public void showJCBPool()
	{
		System.out.println("-----JCBPool-----");
		for(int i=0;i<number;++i)
		{
			System.out.printf("(%02d) JCB\n", i);
			pool.get(i).showJCB();
		}
	}
	/**
	 * ���JCBPool���Ƿ��е���ʱ�����ҵ��ÿһ��ʱ������ֻ�ܴ���һ��PCB
	 * �е�����򷵻���λ��
	 * û�е�����򷵻�-1
	 * */
	public int checkReturnIndex()
	{
		int index = -1;
		int time = Clock.getClock();
		int tempP = 0;
		for(int i=0;i<number;++i)
		{
			JCB temp = pool.get(i);
			if(time >= temp.getArrivalTime() && temp.getStartTime() == -1)
				if(temp.getPriority() > tempP)
				{
					index = i;
					tempP = temp.getPriority();
				}
		}
		return index;
	}
}
