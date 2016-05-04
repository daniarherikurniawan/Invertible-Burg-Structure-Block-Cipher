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
		blockPlainText = encryption.firstTransformationEnc(blockPlainText);
		blockPlainText = encryption.firstSubtitutionEnc(roundKey.key4Bytes, blockPlainText);
		for (int i = 0; i < 5; i++) {
			blockPlainText = encryption.chainingOperation( roundKey.key5Bytes2.get(i),blockPlainText);
			blockPlainText = encryption.secondTransformationEnc(blockPlainText);
		}
		blockPlainText = encryption.secondSubtitutionEnc(roundKey.key5Bytes1, blockPlainText);
		blockPlainText = encryption.sBoxEnc("daniar", blockPlainText);
		return blockPlainText;
	}

	/*To decrypt*/
	public static ArrayList<Integer> blockD(RoundKey roundKey, ArrayList<Integer> blockPlainText){
		Decryption decryption = new Decryption();
		blockPlainText = decryption.sBoxDec("daniar", blockPlainText);
		blockPlainText = decryption.secondSubtitutionDec(roundKey.key5Bytes1, blockPlainText);
		for (int i = 0; i < 5; i++) {
			blockPlainText = decryption.thirdTransformationDec(blockPlainText);
			blockPlainText = decryption.chainingOperation(roundKey.key5Bytes2.get(4-i), blockPlainText);
		}
		blockPlainText = decryption.thirdSubtitutionDec(roundKey.key4Bytes, blockPlainText);
		blockPlainText = decryption.thirdTransformationDec(blockPlainText);

		return blockPlainText;
	}
}


//public static ArrayList<Integer> blockE(RoundKey roundKey, ArrayList<Integer> blockPlainText){
//	Encryption encryption = new Encryption();
//	blockPlainText = encryption.firstTransformationEnc(blockPlainText);
//	blockPlainText = encryption.firstSubtitutionEnc(roundKey.key4Bytes, blockPlainText);
//	for (int i = 0; i < 5; i++) {
//		blockPlainText = encryption.firstSubtitutionEnc(roundKey.key4Bytes, blockPlainText);
//		blockPlainText = encryption.firstTransformationEnc(blockPlainText);
//		blockPlainText = encryption.chainingOperation( roundKey.key5Bytes2.get(i),blockPlainText);
//		blockPlainText = encryption.firstTransformationEnc(blockPlainText);
//		blockPlainText = encryption.chainingOperation( roundKey.key5Bytes2.get(i),blockPlainText);
//	}
//	blockPlainText = encryption.secondSubtitutionEnc(roundKey.key5Bytes1, blockPlainText);
//	blockPlainText = encryption.firstTransformationEnc(blockPlainText);
//	return blockPlainText;
//}

