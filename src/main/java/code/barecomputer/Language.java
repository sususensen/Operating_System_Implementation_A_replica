package code.barecomputer;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

// �������ڸü�����д洢�Ļ�����ͷ��ţ����֣���Ӣ�ĵ�׼��
public class Language 
{
	private static final Map<String, String> map = new HashMap<>();
	public Language() 
	{
		map.put("00", "0");
		map.put("01", "1");
		map.put("02", "2");
		map.put("03", "3");
		map.put("04", "4");
		map.put("05", "5");
		map.put("06", "6");
		map.put("07", "7");
		map.put("08", "8");
		map.put("09", "9");
		
		map.put("10", "a");
		map.put("11", "b");
		map.put("12", "c");
		map.put("13", "d");
		map.put("14", "e");
		map.put("15", "f");
		map.put("16", "g");
		map.put("17", "h");
		map.put("18", "i");
		map.put("19", "j");
		map.put("1A", "k");
		map.put("1B", "l");
		map.put("1C", "m");
		map.put("1D", "n");
		map.put("1E", "o");
		map.put("1F", "p");
		map.put("20", "q");
		map.put("21", "r");
		map.put("22", "s");
		map.put("23", "t");
		map.put("24", "u");
		map.put("25", "v");
		map.put("26", "w");
		map.put("27", "x");
		map.put("28", "y");
		map.put("29", "z");
		
		map.put("30", "A");
		map.put("31", "B");
		map.put("32", "C");
		map.put("33", "D");
		map.put("34", "E");
		map.put("35", "F");
		map.put("36", "G");
		map.put("37", "H");
		map.put("38", "I");
		map.put("39", "J");
		map.put("3A", "K");
		map.put("3B", "L");
		map.put("3C", "M");
		map.put("3D", "N");
		map.put("3E", "O");
		map.put("3F", "P");
		map.put("40", "Q");
		map.put("41", "R");
		map.put("42", "S");
		map.put("43", "T");
		map.put("44", "U");
		map.put("45", "V");
		map.put("46", "W");
		map.put("47", "X");
		map.put("48", "Y");
		map.put("49", "Z");
		
		map.put("50", "");
		map.put("51", " ");
		map.put("52", ".");
		map.put("53", ",");
		map.put("54", "!");
		map.put("55", "?");
		map.put("56", "(");
		map.put("57", ")");
		map.put("58", "+");
		map.put("59", "-");
		map.put("5A", "\n");
		map.put("5B", "*");
		map.put("5C", "&");
		map.put("5D", "{");
		map.put("5E", "}");
		map.put("5F", "=");
		map.put("60", "|");
		map.put("61", "@");
		map.put("62", "#");
		map.put("63", ":");
		map.put("64", "<");
		map.put("65", ">");
		map.put("66", "%");
		map.put("67", "\t");
		map.put("68", ";");
		map.put("69", "\"");
		map.put("6A", "^");
		map.put("6B", "_");
		
		map.put("70", "��");
		map.put("71", "��");
		map.put("72", "ϵ");
		map.put("73", "ͳ");
		map.put("74", "��");
		map.put("75", "��");
		map.put("76", "��");
		map.put("77", "��");
		map.put("78", "��");
		map.put("79", "��");
		map.put("7A", "��");
		map.put("7B", "��");
		map.put("7C", "��");
		map.put("7D", "��");
		map.put("7E", "��");
		map.put("7F", "��");
		map.put("80", "ѧ");
		map.put("81", "��");
		map.put("82", "��");
		map.put("83", "��");
		map.put("84", "��");
		map.put("85", "��");
		map.put("86", "��");
		map.put("87", "��");
		map.put("88", "��");
		map.put("89", "��");
		map.put("8A", "��");
		map.put("8B", "��");
		map.put("8C", "��");
		map.put("8D", "��");
		map.put("8E", "��");
		map.put("8F", "��");
		map.put("90", "��");
	}
	
	public static String getValue(String key)
	{
		key = key.toUpperCase();
		return map.get(key);
	}
	
	/**
	 * ���ڽ�һ���ַ�ת��ΪOSʶ���ʮ��������
	 * ������С��256��
	 * flagΪ��ʱ����Ϊһ��block�ļ�����һ�����ݿ�Ϊ���ļ��е��ַ���
	 * */
	public static String[] getKeyStringsFromValueString(String value, boolean flag)
	{
		String[] out = new String[256];
		for(int i=0;i<256;++i)
			out[i]="0000";
		if(value.length()>=256)
		{
			System.err.print("Ҫת�����ַ�������256���ַ�");
			return out;
		}
		int number = value.length()/2 + 1;
		out[0] = Block.INT_TO_HEX(number);
		char[] valueCharArray = value.toCharArray();
		int index = 1;
		for(int i=0;i<valueCharArray.length;)
		{
			String a = String.valueOf(valueCharArray[i]);
			String x = Language.getKey(a);
			i++;
			if(i == valueCharArray.length)
			{
				out[index] = x+"50";
				break;
			}
			String b = String.valueOf(valueCharArray[i]);
			String y = Language.getKey(b);
			i++;
			out[index] = x + y;
			index++;
		}
		if(flag)
		{
			String Root = System.getProperty("user.dir");
			String Path = Root + File.separator + "output" + File.separator + "KeyStrings.txt";
			try {
				File file = new File(Path);
				FileWriter delete = new FileWriter(file);
				delete.write("");
				delete.flush();
				delete.close();
				FileWriter fWriter = new FileWriter(file, true);
				for(int i=0;i<256;i++)
					fWriter.write(out[i]+"\n");
				fWriter.flush();
				fWriter.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return out;
	}
	/**
	 * ���ڽ�����Stirng[]ʮ����������ת��Ϊ�ַ�����
	 * */
	public static String getValueStringFromKeyStrings(String[] keys)
	{
		int number = Block.HEX_TO_INT(keys[0]);
		String out = "";
		for(int i=1;i<number;++i)
		{
			String[] temp = Block.getByte(keys[i]);
			out = out + getValue(temp[0]);
			out = out + getValue(temp[1]);
		}
		return out;
	}
	// ��һ�ֵĻ�����ת��Ϊ��Ӧ���ַ���
	public static String getValueStringFromKeyString(String key)
	{
		String out = "";
		String[] temp = Block.getByte(key);
		out = out + getValue(temp[0]);
		out = out + getValue(temp[1]);
		return out;
	}
	
	public static String getKey(String value)
	{
		String key="";
        //����map
        for (Map.Entry<String, String> entry : map.entrySet()) 
        {
            if(value.equals(entry.getValue()))
            {
                key=entry.getKey();
                break;
            }
        }
        return key;
	}
}
