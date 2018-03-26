package com.guohua.java8.date;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * 
 * JAVA 8 日期 Demo
 * 
 * Instant——它代表的是时间戳
 * 
 * LocalDate——不包含具体时间的日期，比如2014-01-14。它可以用来存储生日，周年纪念日，入职日期等。
 * 
 * LocalTime——它代表的是不含日期的时间
 * 
 * LocalDateTime——它包含了日期及时间，不过还是没有偏移信息或者说时区。
 * 
 * ZonedDateTime——这是一个包含时区的完整的日期时间，偏移量是以UTC/格林威治时间为基准的。
 * 
 * @author zrkj09
 *
 */
public class TimeDemo {

	public static void main(String[] args) {

		// 获取当前日期
		LocalDate today = LocalDate.now();
		System.out.println("Today's Local date : " + today);

		// 获取年月日
		int year = today.getYear();
		int month = today.getMonthValue();
		int day = today.getDayOfMonth();
		System.out.printf("Year : %d Month : %d day : %d \t %n", year, month, day);

		// 获取某个特定的日期
		LocalDate dateOfBirth = LocalDate.of(2010, 01, 14);
		System.out.println("Your Date of birth is : " + dateOfBirth);

		// 检查两个日期是否相等
		LocalDate date1 = LocalDate.of(2018, 2, 12);
		if (date1.equals(today)) {
			System.out.printf("Today %s and date1 %s are same date %n", today, date1);
		}

		// 检查重复事件，比如说生日
		// LocalDate dateOfBirth = LocalDate.of(2010, 01, 14);
		MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
		MonthDay currentMonthDay = MonthDay.from(today);
		if (currentMonthDay.equals(birthday)) {
			System.out.println("Many Many happy returns of the day !!");
		} else {
			System.out.println("Sorry, today is not your birthday");
		}

		// 获取当前时间
		LocalTime time = LocalTime.now();
		System.out.println("local time now : " + time);

		// 时间操作
		LocalTime newTime = time.plusHours(2); // adding two hours
		System.out.println("Time after 2 hours : " + newTime);

		// 一周后
		LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
		System.out.println("Today is : " + today);
		System.out.println("Date after 1 week : " + nextWeek);
		System.out.println("Date after 1 week : " + today.plusWeeks(1));

		// 一年后
		LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
		System.out.println("Date before 1 year : " + previousYear);
		LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
		System.out.println("Date after 1 year : " + nextYear);

		// 使用时钟
		// 获取某个时区下当前的瞬时时间，日期或者时间
		// 可以替代System.currentTimeInMillis()与 TimeZone.getDefault() 方法
		// Returns the current time based on your system clock and set to UTC.
		Clock clock = Clock.systemUTC();
		System.out.println("Clock : " + clock);
		System.out.println(clock.millis());

		// Returns time based on system clock zone Clock defaultClock =
		clock = Clock.systemDefaultZone();
		System.out.println("Clock : " + clock);
		System.out.println(clock.millis());

		// 判断某个日期是在另一个日期的前面还是后面
		LocalDate tomorrow = LocalDate.of(2014, 1, 15);
		if (tomorrow.isAfter(today)) {
			System.out.println("Tomorrow comes after today");
		}
		LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
		if (yesterday.isBefore(today)) {
			System.out.println("Yesterday is day before today");
		}

		// 处理不同的时区
		// Date and time with timezone in Java 8
		ZoneId america = ZoneId.of("America/New_York");
		LocalDateTime localtDateAndTime = LocalDateTime.now();
		ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localtDateAndTime, america);
		System.out.println("Current date and time in a particular timezone : " + dateAndTimeInNewYork);

		// 表示固定的日期，比如信用卡过期时间
		YearMonth currentYearMonth = YearMonth.now();
		System.out.printf("Days in month year %s:%d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
		YearMonth creditCardExpiry = YearMonth.of(2018, Month.FEBRUARY);
		System.out.printf("Your credit card expires on %s %n", creditCardExpiry);

		// 检查闰年
		if (today.isLeapYear()) {
			System.out.println("This year is Leap year");
		} else {
			System.out.println("2018 is not a Leap year");
		}

		// 两个日期之间包含多少天，多少个月
		LocalDate java8Release = LocalDate.of(2014, Month.MARCH, 14);
		System.out.println(java8Release);
		Period period = Period.between(java8Release, today);
		System.out.println("Months left between today and Java 8 release : " + period.getYears());
		System.out.println("Months left between today and Java 8 release : " + period.getMonths());
		System.out.println("Months left between today and Java 8 release : " + period.getDays());

		// 带时区偏移量的日期与时间
		LocalDateTime datetime = LocalDateTime.of(2014, Month.JANUARY, 14, 19, 30);
		ZoneOffset offset = ZoneOffset.of("+05:30");
		OffsetDateTime date = OffsetDateTime.of(datetime, offset);
		System.out.println("Date and Time with timezone offset in Java : " + date);

		// 获取当前时间戳
		Instant timestamp = Instant.now();
		System.out.println("What is value of this instant " + timestamp);

		// 使用预定义的格式器来对日期进行解析/格式化
		String dayAfterTommorrow = "20140116";
		LocalDate formatted = LocalDate.parse(dayAfterTommorrow, DateTimeFormatter.BASIC_ISO_DATE);
		System.out.printf("Date generated from String %s is %s %n", dayAfterTommorrow, formatted);

		// 使用自定义的格式器来解析日期
		//String goodFriday = "04 18 2014";
		 String goodFriday = "四月 18 2014";
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
			LocalDate holiday = LocalDate.parse(goodFriday, formatter);
			System.out.printf("Successfully parsed String %s, date is %s%n", goodFriday, holiday);
		} catch (DateTimeParseException ex) {
			System.out.printf("%s is not parsable!%n", goodFriday);
			ex.printStackTrace();
		}
		
		// 对日期进行格式化，转换成字符串
		LocalDateTime arrivalDate = LocalDateTime.now(); 
		try { 
		    DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a"); 
		    String landing = arrivalDate.format(format); 
		    System.out.printf("Arriving at : %s %n", landing); 
		    } catch (DateTimeException ex) { 
		    System.out.printf("%s can't be formatted!%n", arrivalDate); 
		    ex.printStackTrace(); 
		} 
	}
}
