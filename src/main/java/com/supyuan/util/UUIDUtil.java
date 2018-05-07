package com.supyuan.util;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class UUIDUtil {
	/**
	 * 22位字符串
	 * 
	 * @return String
	 */
	public static String UUID() {
		// UUID uuid = Generators.randomBasedGenerator().generate();
		// UUID uuid = Generators.timeBasedGenerator().generate();
		TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
		UUID uuid = gen.generate();
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return Base64.encodeBase64URLSafeString(bb.array());
	}

	/**
	 * 9 位字符串
	 * 
	 * @return String
	 */
	public static String GUID() {
		char[] chars = "abcdefghijklmnopqrstuvwxyzABSDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		Random r = new Random(System.currentTimeMillis());
		char[] id = new char[9];
		for (int i = 0; i < 9; i++) {
			id[i] = chars[r.nextInt(chars.length)];
		}
		return new String(id);
	}
}