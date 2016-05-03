package algorithm;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Random;

import javax.naming.spi.DirStateFactory.Result;

import commonOperation.commonOperation;


public class BurgAlgorithm {
	
	/*To encrypt*/
	public static ArrayList<Integer> blockE(RoundKey roundKey, ArrayList<Integer> blockPlainText){
		Encryption encryption = new Encryption();
		blockPlainText = encryption.firstSubtitutionEnc(roundKey.key4Bytes, blockPlainText);
		blockPlainText = encryption.firstTransformationEnc(blockPlainText);
		blockPlainText = encryption.secondSubtitutionEnc(roundKey.key5Bytes[0], blockPlainText);
		blockPlainText = encryption.secondTransformationEnc(blockPlainText);
		blockPlainText = encryption.thirdSubtitutionEnc(roundKey.key5Bytes[1], blockPlainText);
		blockPlainText = encryption.thirdTransformationEnc(blockPlainText);
		return blockPlainText;
	}

	/*To decrypt*/
	public static ArrayList<Integer> blockD(RoundKey roundKey, ArrayList<Integer> blockPlainText){
		Decryption decryption = new Decryption();
		blockPlainText = decryption.firstSubtitutionDec(roundKey.key4Bytes, blockPlainText);
		blockPlainText = decryption.firstTransformationDec(blockPlainText);
		blockPlainText = decryption.secondSubtitutionDec(roundKey.key5Bytes[0], blockPlainText);
		blockPlainText = decryption.secondTransformationDec(blockPlainText);
		blockPlainText = decryption.thirdSubtitutionDec(roundKey.key5Bytes[1], blockPlainText);
		blockPlainText = decryption.thirdTransformationDec(blockPlainText);
		return blockPlainText;
	}
	

}


