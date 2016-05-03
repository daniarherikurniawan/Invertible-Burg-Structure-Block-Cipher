package algorithm;
import java.util.ArrayList;

import commonOperation.commonOperation;

public class SubKey {
	private ArrayList<Integer> hashOfMsg;
	public ArrayList<RoundKey> arrayRoundKey;
	private int roundNumber;
	
	public SubKey(int roundNumber){
		this.roundNumber = roundNumber;
		hashOfMsg = new ArrayList<Integer>();
		arrayRoundKey = new ArrayList<>();
	}
	
	public void generateSubKey(ArrayList<Integer> mainKey, String msg){
		hashOfMsg = commonOperation.getHash(msg);
		for (int i = 0; i < roundNumber; i++) {
			RoundKey roundKey = new RoundKey();
			arrayRoundKey.add(roundKey);
		}
	}
	
}
