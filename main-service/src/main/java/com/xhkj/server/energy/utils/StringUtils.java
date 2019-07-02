package com.xhkj.server.energy.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	final static DecimalFormat dfmt = new DecimalFormat("0");

	private static Gson gson, gson2;

	/**
	 * 获取32位uuid
	 * @author lgz
	 * @date 2015
	 * @return 返回32位uuid
	 * @version 1.0
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * map转json
	 * @author lgz
	 * @date 2016年12月10日
	 * @version 1.0
	 */
	public static String getMapToJson(Map<String,Object> map){
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json=gson.toJson(map);
		return json;
	}
	
	/**
	 * 数字前几位补�?
	 * @param num 要补零的数字 (25)
	 * @param digits �?要的位数 (6)
	 * @return 返回结果�?000025
	 * @author lgz
	 * 2015-09-14
	 */
	public static String fillDigital(int num,int digits){
		 //得到�?个NumberFormat的实�?
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置�?大整数位�?
        nf.setMaximumIntegerDigits(digits);
        //设置�?小整数位�?    
        nf.setMinimumIntegerDigits(digits);
        return nf.format(num);
	}

	/**
	 * 获取�?个gson工具示例
	 * 
	 * @param disableHtmlEscaping
	 * @return
	 */
	public static Gson getGson(boolean... disableHtmlEscaping) {
		if (gson == null && gson2 == null) {
			gson = new Gson();
			gson2 = new GsonBuilder().disableHtmlEscaping().create();
		}
		return disableHtmlEscaping.length > 0 && disableHtmlEscaping[0] ? gson2 : gson;
	}

	public static Byte parseByte(Object str) {
		return str == null ? 0 : Byte.valueOf((isNumeric(str.toString())) ? Byte.parseByte(str.toString()) : 0);
	}

	public static Short parseShort(Object str) {
		return str == null ? 0 : Short.valueOf((isNumeric(str.toString())) ? Short.parseShort(str.toString()) : 0);
	}

	public static Integer parseInt(Object str) {
		return str == null ? 0 : Integer.valueOf((isNumeric(str.toString())) ? Integer.parseInt(str.toString()) : 0);
	}

	public static Long parseLong(Object str) {
		return str == null ? 0 : Long.valueOf((isNumeric(str.toString())) ? Long.parseLong(str.toString()) : 0);
	}

	public static Float parseFloat(Object str) {
		return str == null ? 0 : Float.valueOf((isNumeric(str.toString())) ? Float.parseFloat(str.toString()) : 0);
	}

	public static Double parseDouble(Object str) {
		return str == null ? 0 : Double.valueOf((isNumeric(str.toString())) ? Double.parseDouble(str.toString()) : 0);
	}
	public static BigDecimal parseDecimal(Object str){
		return new BigDecimal(StringUtils.defaultIfEmpty(StringUtils.null2Blank(str),"0.00"));
	}
	public static Boolean parseBoolean(Object str) {
		if (str == null) {
			return false;
		}
		String s = str.toString().toLowerCase();
		if (("y".equalsIgnoreCase(s)) || ("yes".equalsIgnoreCase(s)) || ("true".equalsIgnoreCase(s)) || ("t".equalsIgnoreCase(s)) || ("1".equalsIgnoreCase(s))) {
			return true;
		}
		return false;
	}

	/**
	 * null 或�?? 'null' 转换�? ""
	 */
	public static final String null2Blank(Object str) {
		return ((null == str || "null".equals(str)) ? "" : str.toString());
	}

	/**
	 * 判断是否为数�?
	 */
	public static boolean isNumeric(String str) {
		Matcher isNum = Pattern.compile("(-|\\+)?[0-9]+(.[0-9]+)?").matcher(str);
		return isNum.matches();
	}

	/**
	 * 数字格式�?<br>
	 * 
	 * @param obj
	 * @param scale
	 * @return
	 * @throws Exception
	 */
	public static String fmtNumeric(Object obj, int scale) throws Exception {
		String objStr = defaultIfEmpty(null2Blank(obj), "0");
		objStr = "NaN".equals(objStr) ? "0" : objStr;
		double doubleValue = new BigDecimal(Double.valueOf(objStr).doubleValue()).setScale(scale, BigDecimal.ROUND_HALF_EVEN).doubleValue();

		StringBuffer endWith = new StringBuffer();
		for (int i = 0; i < scale; i++) {
			endWith.append("0");
		}
		String pattern = "0";
		if (endWith.length() > 0) {
			pattern = pattern.concat(".").concat(endWith.toString());
		}
		dfmt.applyPattern(pattern);

		return dfmt.format(doubleValue);
	}


	/**
	 * 去除html编码
	 */
	public static String unHtmlEncode(String txt) {
		if (null != txt) {
			txt = txt.replace("&", "&amp;").replace("&amp;amp;", "&amp;").replace("&amp;quot;", "&quot;").replace("\"", "&quot;").replace("&amp;lt;", "&lt;").replace("<", "&lt;").replace("&amp;gt;", "&gt;").replace(">", "&gt;").replace("&amp;nbsp;", "&nbsp;");
		}
		return txt;
	}

	/**
	 * 去出html&js
	 */
	public static String replaceHtmlJs(String txt) {
		if (null != txt) {
			txt = txt.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "").replaceAll("eval\\((.*)\\)", "");
		}
		return txt;
	}

	/**
	 * 针对数据库查询参数中的特殊字�?(" ' % )进行转义
	 * 
	 * @param txt
	 * @return
	 */
	public static String replaceSpecial(String txt) {
		if (null != txt) {
			txt = txt.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"").replaceAll("%", "\\\\%");
		}
		return txt;
	}

	public static String base64Encode(String source) throws UnsupportedEncodingException {
		return null;
	}

	/**
	 * 全角转半�?
	 * 
	 * @param input String.
	 * @return 半角字符�?
	 */
	public static String toDBC(String input) {

		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);

			}
		}

		return new String(c);
	}

	/**
	 * 半角转全�?
	 * @param input String.
	 * @return 全角字符�?.
	 */
	public static String toSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000'; // 采用十六进制,相当于十进制�?12288

			} else if (c[i] < '\177') { // 采用八进�?,相当于十进制�?127
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
	private final static String[] lc_FirstLetter = { "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "w", "x", "y", "z" };

	/**
	 * 取得给定汉字的首字母,即声�?
	 * 
	 * @param chinese 给定的汉�?
	 * @return 给定汉字的声�?
	 */
	public static String getFirstLetter(String chinese) throws Exception {
		if (chinese == null || chinese.trim().length() == 0) {
			return "";
		}
		chinese = new String(chinese.getBytes("GB2312"), "ISO8859-1");

		if (chinese.length() > 1) {// 判断是不是汉�?

			int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
			int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
			li_SectorCode = li_SectorCode - 160;
			li_PositionCode = li_PositionCode - 160;
			int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位�?
			if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
				for (int i = 0; i < 23; i++) {
					if (li_SecPosCode >= li_SecPosValue[i] && li_SecPosCode < li_SecPosValue[i + 1]) {
						chinese = lc_FirstLetter[i];
						break;
					}
				}
			} else {// 非汉字字�?,如图形符号或ASCII�?

				chinese = new String(chinese.getBytes("ISO8859-1"), "GB2312");
				chinese = chinese.substring(0, 1);
			}
		}

		return chinese;
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID());
	}
	
	public static String getProperties(String key) {
	    Properties properties = new Properties();
	    try {
			properties.load( ClassLoader.getSystemResourceAsStream("config.properties"));
		} catch (IOException e) {
			throw new RuntimeException("获取properties文件失败");
		}
		return properties.getProperty(key);
	}

	/**
	 * 改成html编码
	 */
	public static String htmlEncode(String txt) {
		if (null != txt) {
			txt = txt.replace("&amp;", "&").replace("&quot;", "\"").replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " ");
		}
		return txt;
	}
 

}