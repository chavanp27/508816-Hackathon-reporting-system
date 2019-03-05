package com.cts.ora.report.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.ora.report.constants.ORAConstants;

public class ORAUtil {

	static Logger logger = LoggerFactory.getLogger(ORAUtil.class);

	public static Integer getPeriod(Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String resultStr = dateFormatter.format(new Date(cal.getTimeInMillis()));
		Integer result = Integer.parseInt(resultStr);
		return result;
	}

	public static Date getPeriod(Integer period) {
		Date date = null;

		return date;
	}

	public static Integer incrementPeriod(Integer period) {
		return nextPeriod(period, 1);
	}

	private static Integer nextPeriod(Integer period, int increment) {
		try {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMM");
			Date date = dateFormatter.parse(period.toString());
			logger.info("CommonUtils", "nextPeriod : fromDate - " + date);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, increment);
			String resultStr = dateFormatter.format(new Date(cal.getTimeInMillis()));
			Integer result = Integer.parseInt(resultStr);
			logger.info("CommonUtils", "nextPeriod : result - " + result);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return period;
	}

	public static String getDisplayPeriod(String period) {
		return getDisplayPeriod(period, ORAConstants.PERIOD_TYPE);
	}

	public static String getDisplayPeriod(String period, String periodType) {
		String displayPeriod = null;
		if (null != period) {
			try {
				DateFormat df = new SimpleDateFormat("yyyyMM");
				DateFormat yearFormat = new SimpleDateFormat("yy");
				DateFormat monthFormat = new SimpleDateFormat("MM");
				if (periodType == null) {
					periodType = ORAConstants.PERIOD_TYPE;
				}
				Calendar fromCal = Calendar.getInstance();
				fromCal.setTime(df.parse(period));

				if (ORAConstants.PERIOD_TYPE.equalsIgnoreCase(periodType)) {
					displayPeriod = "FY" + yearFormat.format(fromCal.getTime()) + ORAConstants.PERIOD_TYPE
							+ monthFormat.format(fromCal.getTime());
				} else {
					int month = fromCal.get(Calendar.MONTH) + 1;
					int quarter = month % 3 == 0 ? (month / 3) : (month / 3) + 1;
					displayPeriod = ("FY" + yearFormat.format(fromCal.getTime()) + ORAConstants.QUARTER_TYPE + quarter);
				}
			}

			catch (Exception e) {
				logger.info("CommonUtils", "Error occurred which preparing the display period for period - " + period);
				displayPeriod = period;
			}
		}
		return displayPeriod;
	}

	public static List<String> getPeriodList(String periodFrom, String periodTo) throws ParseException {
		List<String> periods = new ArrayList<String>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMM");
		Date fromDate = dateFormatter.parse(periodFrom);
		Date toDate = dateFormatter.parse(periodTo);
		Calendar c = Calendar.getInstance();
		c.setTime(fromDate);
		periods.add(dateFormatter.format(new Date(c.getTimeInMillis())));
		while (c.getTimeInMillis() < toDate.getTime()) {
			c.add(Calendar.MONTH, 1);
			periods.add(dateFormatter.format(new Date(c.getTimeInMillis())));
		}
		return periods;
	}

	public static List<String> getPeriodListForQuarter(String quarterFrom, String quarterTo) {
		List<String> periods = new ArrayList<String>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMM");
		try {
			Date fromDate = dateFormatter.parse(quarterFrom);
			Calendar c = Calendar.getInstance();
			c.setTime(fromDate);
			c.add(Calendar.MONTH, -1);
			periods.add(dateFormatter.format(new Date(c.getTimeInMillis())));
			c.add(Calendar.MONTH, -1);
			periods.add(dateFormatter.format(new Date(c.getTimeInMillis())));
			periods.addAll(getPeriodList(quarterFrom, quarterTo));
		} catch (ParseException e) {
			logger.error("CommonUtils",
					"Error occured while converting quarter to list of periods for  : QuarterFrom - " + quarterFrom
							+ " and QuarterTo :" + quarterTo);
			periods.add(quarterFrom);
			periods.add(quarterTo);
		}
		Collections.sort(periods);
		logger.info("ORAUtils", "Exiting getPeriodListForQuarter()");
		return periods;
	}
}
