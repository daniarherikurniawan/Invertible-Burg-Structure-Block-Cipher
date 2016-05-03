package algorithm;

import java.util.ArrayList;

public class RoundKey {
	public ArrayList<Integer>[] key5Bytes;
	public ArrayList<Integer> key4Bytes;
	
	public RoundKey(){
		key5Bytes[2] = new ArrayList<Integer>();
		key4Bytes = new ArrayList<Integer>();
	}
	
}
