package com.guohua.attack;

import java.util.LinkedList;

/**
 * 十进制转换为二进制
 * 
 * 未考虑负数的情况
 * 
 * @author zrkj09
 *
 */
public class BinaryInt {

	public static void main(String[] args) {
		int number = -13;
		System.out.println(Integer.toOctalString(number));
		System.out.println(Integer.toString(number, 2));
		System.out.println(Integer.toBinaryString(number));
	}

	public static String toBinaryString(int i) {
		return toUnsignedString0(i, 1);
	}
	
	public static String toBinaryString(int i,int shift) {
		return toUnsignedString0(i, shift);
	}

	/**
	 * 
	 * @param val 待转换的数
	 * @param shift 二进制位中多少个二进制位表示该进制下的一个数
	 * @return
	 */
	private static String toUnsignedString0(int val, int shift) {
		// assert shift > 0 && shift <=5 : "Illegal shift value";
		// 忽略从高位开始，连续是0的位。即从高位开始第一位不是0的总计是多少位
		// mag
		int mag = Integer.SIZE - numberOfLeadingZeros(val);
		// 该进制下显示多少位
		// 比如要转换位8进制，那个在二进制中没3个二进制位表示一个8进制位
		// (mag + (shift - 1) 的作用是主要防止出现下面的情况
		// 32/3 = 10 实际需要11位
		// n/3=0 至 (n+3)/3 = 0之间的数少算一位 
		int chars = Math.max(((mag + (shift - 1)) / shift), 1);
		// 二进制结果使用 char 数组存储
		char[] buf = new char[chars];
		// 二进制
		formatUnsignedInt(val, shift, buf, 0, chars);

		// Use special constructor which takes over "buf".
		return new String(buf);
	}

	// 二进制
	static int formatUnsignedInt(int val, int shift, char[] buf, int offset, int len) {
		int charPos = len;
		// 需要转换为几进制数
		int radix = 1 << shift;
		// 进制数可使用数的表示范围
		// 例如：2进制 -> 使用 0，1
		// 8进制 -> 0,1,2,3,4,5,6,7
		int mask = radix - 1;
		do {
			// 取余
			buf[offset + --charPos] = digits[val & mask];
			// 取商
			val >>>= shift;
		} while (val != 0 && charPos > 0);

		return charPos;
	}

	// 从高位开始连续出现0的次数
	// 可以延伸出 计算一个无符号数的二进制中1出现的个数
	// 第一种：连续移位，与数的大小有关
	// 第二种：连续清除地位是1的位，并计数。即 当前数n与n-1进行与运算(&)
	public static int numberOfLeadingZeros(int i) {
		// HD, Figure 5-6
		if (i == 0)
			return 32;
		int n = 1;
		if (i >>> 16 == 0) {
			n += 16;
			i <<= 16;
		}
		if (i >>> 24 == 0) {
			n += 8;
			i <<= 8;
		}
		if (i >>> 28 == 0) {
			n += 4;
			i <<= 4;
		}
		if (i >>> 30 == 0) {
			n += 2;
			i <<= 2;
		}
		n -= i >>> 31;
		return n;
	}

	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * 输入是一个十进制整数，输出是它的二进制表示的字符串
	 * 
	 * 考虑负数
	 * 
	 * 符号位设为1，其它与负数绝对值的二进制一致，即原码 除符号位，原码其它位取反，反码 反码+1，负数的二进制
	 * 
	 * @param n
	 * @return
	 */
	private String oct2Bin(int n) {
		LinkedList<Integer> bytes = new LinkedList<>();
		shortDivision(bytes, n);
		StringBuffer sb = new StringBuffer();
		bytes.forEach(b -> sb.append(b));
		return sb.toString();
	}

	// // 二进制中有多少个连续的0
	// // >>> 无符号右移，补0
	// // >> 右移，正数补0，负数补1
	// // << 左移，低位补0
	// public static int numberOfLeadingZeros(int i) {
	// // HD, Figure 5-6
	// // 例如：i = 13 0000000000000000 0000000000001101
	// if (i == 0)
	// return 32;
	// // 记录0的个数
	// int n = 1;
	// // 前16位(高位)，全是0
	// if (i >>> 16 == 0) {
	// n += 16;
	// // 丢弃高位，低位补0
	// i <<= 16;
	// }
	// System.out.println(Integer.toBinaryString(i));
	// // 0000000000001101 0000000000000000
	// // 低位前8位，全是0
	// if (i >>> 24 == 0) {
	// n += 8;
	// i <<= 8;
	// }
	// System.out.println(Integer.toBinaryString(i));
	// // 00001101 000000000000000000000000
	// // 低位前12位，全是0
	// if (i >>> 28 == 0) {
	// n += 4;
	// i <<= 4;
	// }
	// System.out.println(Integer.toBinaryString(i));
	// // 1101 0000000000000000000000000000
	// // 低位前14位，全是0
	// if (i >>> 30 == 0) {
	// n += 2;
	// i <<= 2;
	// }
	// System.out.println(Integer.toBinaryString(i));
	// // 正数减1，负数不变
	// n -= i >>> 31;
	// return n;
	// }

	private void shortDivision(LinkedList<Integer> bytes, int n) {
		if (n / 2 == 0) {
			bytes.addFirst(n);
			return;
		}
		bytes.addFirst((n % 2));
		shortDivision(bytes, n / 2);
	}
}
