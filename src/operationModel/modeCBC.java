package operationModel;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import algorithm.RoundKey;
import algorithm.SubKey;
import algorithm.BurgAlgorithm;
import commonOperation.commonOperation;

public class modeCBC {
	final int  blockSize = 16; /*Bytes*/
	/*SHA256 of the text*/
	ArrayList<Integer> hashOfMainKey;
	ArrayList<Integer> hashOfMsg;

	final int  roundNumber = 4;
	
	/*Key with minimum 8 Byte length or 8 characters*
	 * 1 character = 8 bit = 1 Byte*/	
	ArrayList<Integer> mainKey;
	
	/*Plain text that will be encrypted */
	public ArrayList<Integer> plainText;
	/*Chiper text that will be decrypted */
	public ArrayList<Integer> cipherText;
	/*Result text is the result of decrypted cipher text */
	public ArrayList<Integer> resultText;
	
	/*Constructor of modeCBC*/
	public modeCBC(String key){
		this.mainKey = commonOperation.convertStringToArrayInt(key);
		this.plainText = new ArrayList<Integer>();
		this.cipherText = new ArrayList<Integer>();
		this.resultText = new ArrayList<Integer>();
		this.hashOfMainKey = commonOperation.getHash(key);
	}
	

	
	public ArrayList<Integer> encrypt(RoundKey roundKey, ArrayList<Integer>  plainText){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> constant = hashOfMainKey;
		/*Proceed per block*/
		for (int i = 0 ; i < plainText.size()-blockSize ; i += blockSize){
			/*one single block = 8 Bytes*/
			ArrayList<Integer> arrayInput= new ArrayList<Integer>();
			for (int j = 0; j < blockSize; j++) {
				arrayInput.add(plainText.get(i+j));
			}
			/*XOR before doing encription*/
			ArrayList<Integer> singleBlock = commonOperation.XOR( arrayInput, constant);
	
			/*Encription per block*/
			singleBlock = BurgAlgorithm.blockE(roundKey, singleBlock);	
			
			/*Collect the result (cipher)*/
			result.addAll(singleBlock);	
			
			/*Update the constant as CBC rules*/
			constant = singleBlock;
		}
		return result;
	}

	public ArrayList<Integer> decrypt(RoundKey roundKey, ArrayList<Integer>  plainText){
		ArrayList<Integer> result = new ArrayList<Integer>();		
		ArrayList<Integer> constant = hashOfMainKey;
		for (int i = 0 ; i < plainText.size()-blockSize ; i += blockSize){
			ArrayList<Integer> singleBlock = new ArrayList<Integer>();			
			ArrayList<Integer> arrayInput= new ArrayList<Integer>();
			/*one single block = 4 Bytes*/
			for (int j = 0; j < blockSize; j++) {
				arrayInput.add(plainText.get(i+j));
			}			
			/*Decription per block*/
			singleBlock = BurgAlgorithm.blockD(roundKey, arrayInput);	

			/*XOR after doing encription*/
			singleBlock = commonOperation.XOR( singleBlock, constant);
			
			/*Update the constant as CBC rules*/
			constant = arrayInput;
			
			/*Save the result (cipher)*/
			result.addAll(singleBlock);	
		}
		return result;
	}
	
		
	/*Start the encryption mode CBC*/
	public ArrayList<Integer> startEncryptionModeCBC(ArrayList<Integer> plainText){
		plainText = commonOperation.adjustSizeOfPlaintext(plainText, blockSize);
		ArrayList<Integer> result = (ArrayList<Integer>) plainText.clone();
		
		SubKey subKey = new SubKey(roundNumber);
		subKey.generateSubKey(mainKey, plainText.toString());
		subKey.print();
		
		for (int i = 0; i < roundNumber; i++) {
//			result = encrypt(subKey.arrayRoundKey.get(i), result);
		}
		
//		Map<Integer, Integer> frequency = commonOperation.countFrequency(result);
		return result;
	}
	

	/*Start the decryption mode CBC*/
	public ArrayList<Integer> startDecryptionModeCBC(ArrayList<Integer> plainText){
		ArrayList<Integer> result = (ArrayList<Integer>) plainText.clone();
		
		SubKey subKey = new SubKey(roundNumber);
		subKey.generateSubKey(mainKey, plainText.toString());
		
		for (int i = 0; i < roundNumber; i++) {
			result = decrypt(subKey.arrayRoundKey.get(i), result);
		}
		result = commonOperation.removePadding(result, blockSize);
		return result;
	}
	
}
