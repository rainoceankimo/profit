package com.example.profitmarket;

public class ZBarDecoder {

	static {
		System.loadLibrary("ZBarDecoder");
	}

	/**
	 * è§???æ–¹æ³?
	 * 
	 * @param data
	 *            ?›¾??‡æ•°?®
	 * @param width
	 *            ??Ÿå?‹å®½åº?
	 * @param height
	 *            ??Ÿå?‹é?˜åº¦
	 * @return
	 */
	public native String decodeRaw(byte[] data, int width, int height);

	/**
	 * è§???æ–¹æ³?(??è¦è?å‰ª?›¾???)
	 * 
	 * @param data
	 *            ?›¾??‡æ•°?®
	 * @param width
	 *            ??Ÿå?‹å®½åº?
	 * @param height
	 *            ??Ÿå?‹é?˜åº¦
	 * @param x
	 *            ?ˆª??–ç?„x??æ??
	 * @param y
	 *            ?ˆª??–ç?„y??æ??
	 * @param cwidth
	 *            ?ˆª??–ç?„åŒº??Ÿå®½åº?
	 * @param cheight
	 *            ?ˆª??–ç?„åŒº??Ÿé?˜åº¦
	 * @return
	 */
	public native String decodeCrop(byte[] data, int width, int height, int x, int y, int cwidth, int cheight);
}
