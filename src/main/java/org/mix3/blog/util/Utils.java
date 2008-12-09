package org.mix3.blog.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.mix3.blog.WicketApplication;

public class Utils {
	public static Properties getDBProperties(){
		Properties back = new Properties();
		InputStream is = WicketApplication.class.getResourceAsStream("/db.properties");
		if (is == null) {
			throw new RuntimeException("Unable to locate db.properties");
		}
		try {
			back.load(is);
			is.close();
		} catch (IOException e) {
			throw new RuntimeException("Unable to load db.properties");
		}
		return back;
	}
	
	public static String digest(String strKey) throws NoSuchAlgorithmException{
		//md5のキーとなる文字列
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(strKey.getBytes());
		byte byDig[] = md.digest();
		String strOut = "";
		for (int i = 0; i < byDig.length; i++) {
			int d = byDig[i];
			if (d < 0) { //byte型では128～255が負値になっているので補正
				d += 256;
			}
			if (d < 16) { //0～15は16進数で1けたになるので、2けたになるよう頭に0を追加
				strOut += "0";
			}
			strOut += Integer.toString(d, 16); //ダイジェスト値の1バイトを16進数2けたで表示
		}
		return strOut;
	}
}
