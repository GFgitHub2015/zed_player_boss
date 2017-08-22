package com.zed.util;

/**
 * some helper routines for data conversion, all data is treated in network byte
 * order
 * 
 * @author Markus Hahn <hahnqflix.de>
 * @version 11 Nov 98
 */
public class ByteArrayConvert {
	// our table for binhex conversion
	final static char[] HEXTAB = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
								   '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * converts a byte array to a binhex string
	 * 
	 * @param data
	 *            the byte array
	 * @return the binhex string
	 */
	public static String bytesToHexStr(byte[] data) {

		// just map the call
		return bytesToHexStr(data, 0, data.length);
	}

	/**
	 * converts a byte array to a binhex string
	 * 
	 * @param data
	 *            the byte array
	 * @param nStartPos
	 *            start index where to get the bytes
	 * @param nNumOfBytes
	 *            number of bytes to convert
	 * @return the binhex string
	 */
	public static String bytesToHexStr(byte[] data, int nStartPos,
			int nNumOfBytes) {

		StringBuffer sbuf = new StringBuffer();
		sbuf.setLength(nNumOfBytes << 1);

		int nPos = 0;
		for (int nI = 0; nI < nNumOfBytes; nI++) {
			sbuf.setCharAt(nPos++, HEXTAB[(data[nI + nStartPos] >> 4) & 0x0f]);
			sbuf.setCharAt(nPos++, HEXTAB[data[nI + nStartPos] & 0x0f]);
		}
		;
		return sbuf.toString();
	};

	public static byte[] hexStrToBytes(String as_hexStr) {
		int li_len = as_hexStr.length() / 2;
		byte[] lbA_result = new byte[li_len];
		hexStrFillBytes(as_hexStr, lbA_result, 0, 0, li_len);
		return lbA_result;
	}

	/**
	 * converts a binhex string back into a byte array (invalid codes will be
	 * skipped)
	 * 
	 * @param sBinHex
	 *            binhex string
	 * @param data
	 *            the target array
	 * @param nSrcPos
	 *            from which character in the string the conversion should
	 *            begin, remember that (nSrcPos modulo 2) should equals 0
	 *            normally
	 * @param nDstPos
	 *            to store the bytes from which position in the array
	 * @param nNumOfBytes
	 *            number of bytes to extract
	 * @return number of extracted bytes
	 */
	public static int hexStrFillBytes(String sBinHex, byte[] data, int nSrcPos,
			int nDstPos, int nNumOfBytes) {

		// check for correct ranges
		int nStrLen = sBinHex.length();
		int nAvailBytes = (nStrLen - nSrcPos) >> 1;
		if (nAvailBytes < nNumOfBytes)
			nNumOfBytes = nAvailBytes;
		int nOutputCapacity = data.length - nDstPos;
		if (nNumOfBytes > nOutputCapacity)
			nNumOfBytes = nOutputCapacity;

		// convert now
		int nResult = 0;
		for (int nI = 0; nI < nNumOfBytes; nI++) {
			byte bActByte = 0;
			boolean blConvertOK = true;
			for (int nJ = 0; nJ < 2; nJ++) {
				bActByte <<= 4;
				char cActChar = sBinHex.charAt(nSrcPos++);
				if ((cActChar >= 'A') && (cActChar <= 'F'))
					bActByte |= (byte) (cActChar - 'A') + 10;
				else if ((cActChar >= 'a') && (cActChar <= 'f'))
					bActByte |= (byte) (cActChar - 'a') + 10;
				else if ((cActChar >= '0') && (cActChar <= '9'))
					bActByte |= (byte) (cActChar - '0');
				else
					blConvertOK = false;
			}
			if (blConvertOK) {
				data[nDstPos++] = bActByte;
				nResult++;
			}
		}
		return nResult;
	}
}
