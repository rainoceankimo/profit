package com.example.profitmarket;

public class ZBarDecoder {

	static {
		System.loadLibrary("ZBarDecoder");
	}

	/**
	 * �???�方�?
	 * 
	 * @param data
	 *            ?��??�数?��
	 * @param width
	 *            ??��?�宽�?
	 * @param height
	 *            ??��?��?�度
	 * @return
	 */
	public native String decodeRaw(byte[] data, int width, int height);

	/**
	 * �???�方�?(??要�?�剪?��???)
	 * 
	 * @param data
	 *            ?��??�数?��
	 * @param width
	 *            ??��?�宽�?
	 * @param height
	 *            ??��?��?�度
	 * @param x
	 *            ?��??��?�x??��??
	 * @param y
	 *            ?��??��?�y??��??
	 * @param cwidth
	 *            ?��??��?�区??�宽�?
	 * @param cheight
	 *            ?��??��?�区??��?�度
	 * @return
	 */
	public native String decodeCrop(byte[] data, int width, int height, int x, int y, int cwidth, int cheight);
}
