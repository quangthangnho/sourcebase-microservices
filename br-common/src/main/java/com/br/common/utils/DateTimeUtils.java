package com.br.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.Supplier;

public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    /**
     * Get current date as format.
     * Default locale & zone.
     *
     * @param format format.
     * @return date time formatted.
     */
    public static String getCurrentDateTime(String format) {
        return DateTimeFormatter.ofPattern(format)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(LocalDateTime.now());
    }

    /**
     * Get current date as format.
     * Default locale & zone.
     *
     * @return date time formatted.
     */
    public static String getCurrentDateTimeISO() {
        return DateTimeFormatter.ofPattern(Pattern.ISO_DATETIME)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(LocalDateTime.now());
    }

    /**
     * Get current date as format.
     * Default locale & zone.
     *
     * @return date formatted.
     */
    public static String getCurrentDateISO() {
        return DateTimeFormatter.ofPattern(Pattern.ISO_DATE)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(LocalDateTime.now());
    }

    /**
     * Get current date at 0:00AM as format.
     * Default locale & zone.
     *
     * @param format format.
     * @return date time formatted.
     */
    public static String getCurrentDate(String format) {
        var curDateCalendar = Calendar.getInstance();
        curDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        curDateCalendar.set(Calendar.MINUTE, 0);
        curDateCalendar.set(Calendar.SECOND, 0);
        return DateTimeFormatter.ofPattern(format)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(curDateCalendar.toInstant());
    }

    /**
     * Get back date at 0:00AM as format.
     * Default locale & zone.
     *
     * @param backDay backDay
     * @param format  format.
     * @return date time formatted.
     */
    public static String getBackDate(int backDay, String format) {
        return getBackDate(Calendar.getInstance().getTime(), backDay, format);
    }

    /**
     * Get back date from a Date at 0:00AM as format.
     * Default locale & zone.
     *
     * @param backDay backDay
     * @param format  format.
     * @return date time formatted.
     */
    public static String getBackDate(String fromDate, int backDay, String format) throws ParseException {
        return getBackDate(convertStrToDate(fromDate, format), backDay, format);
    }

    /**
     * Get back date at 0:00AM as format.
     * Default locale & zone.
     *
     * @param backDay backDay
     * @param format  format.
     * @return date time formatted.
     */
    public static String getBackDate(Date fromDate, int backDay, String format) {
        var calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(Calendar.DAY_OF_YEAR, -backDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return DateTimeFormatter.ofPattern(format)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(calendar.toInstant());
    }

    public static Time convertStrToTime(String strTime, String format) throws ParseException {
        var simpleDateFormat = new SimpleDateFormat(format);
        var time = simpleDateFormat.parse(strTime).getTime();
        return new Time(time);
    }

    public static Timestamp convertStrToTimeStamp(String strTimeStamp, String format) throws ParseException {
        var simpleDateFormat = new SimpleDateFormat(format);
        var time = simpleDateFormat.parse(strTimeStamp).getTime();
        return new Timestamp(time);
    }

    /**
     * Get date string from date.
     * Default locale & zone.
     *
     * @param format format.
     * @param date   date.
     * @return date time formatted.
     */
    public static String getDateAsString(Date date, String format) {
        return DateTimeFormatter.ofPattern(format)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(date.toInstant());
    }

    /**
     * Get date string from date.
     * Default locale & zone.
     *
     * @param date date.
     * @return date time formatted.
     */
    public static String getISODateTime(Date date) {
        return DateTimeFormatter.ofPattern(Pattern.ISO_DATETIME)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(date.toInstant());
    }

    /**
     * Get date string from date.
     * Default locale & zone.
     *
     * @param date date.
     * @return date time formatted.
     */
    public static String getISODate(Date date) {
        return getDateAsString(date, Pattern.ISO_DATE);
    }

    /**
     * Get ISO date time from UTC_ISO date time.
     *
     * @param dateTimeUTCStr dateTimeUTCStr.
     * @return iso date time.
     */
    public static String getISODatetimeFromUTC(String dateTimeUTCStr) {
        return formatDateTime(ZonedDateTime.parse(dateTimeUTCStr)
                        .withZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDateTime(),
                Pattern.ISO_DATETIME);
    }

    /**
     * Format date string.
     *
     * @param strDateTime  strDate.
     * @param inputFormat  inputFormat.
     * @param outputFormat outputFormat.
     * @return String.
     */
    public static String formatDateTime(String strDateTime,
                                        String inputFormat,
                                        String outputFormat) {
        var formatter = DateTimeFormatter.ofPattern(inputFormat);
        var outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
        return LocalDateTime.parse(strDateTime, formatter).format(outputFormatter);
    }

    /**
     * Format local date time string.
     *
     * @param localDateTime localDateTime.
     * @param outputFormat  outputFormat
     * @return formatted date.
     */
    public static String formatDateTime(LocalDateTime localDateTime,
                                        String outputFormat) {
        return Objects.nonNull(localDateTime) ? localDateTime.format(DateTimeFormatter.ofPattern(outputFormat)) : null;
    }

    /**
     * Format local date time string.
     *
     * @param localDateTime localDateTime.
     * @return formatted date.
     */
    public static String formatAsISODateTime(LocalDateTime localDateTime) {
        return Objects.nonNull(localDateTime) ? localDateTime.format(DateTimeFormatter.ofPattern(Pattern.ISO_DATETIME)) : null;
    }

    public static String formatDate(String strDate,
                                    String inputFormat,
                                    String outputFormat) throws ParseException {
        var inputDate = new SimpleDateFormat(inputFormat).parse(strDate);
        return new SimpleDateFormat(outputFormat).format(inputDate);
    }

    public static <X extends Throwable> String formatDate(String strDate,
                                                          String inputFormat,
                                                          String outputFormat,
                                                          Supplier<X> exceptionSupplier) throws X {
        try {
            var inputDate = new SimpleDateFormat(inputFormat).parse(strDate);
            return new SimpleDateFormat(outputFormat).format(inputDate);
        } catch (Exception e) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Format date string.
     *
     * @param isoDate isoDate.
     * @return String.
     */
    public static String isoDateToVietnameseDate(String isoDate) {
        if (StringUtils.isEmpty(isoDate)) return "";
        var isoDateTime = isoDate.trim().concat(" 00:00:00");
        var formatter = DateTimeFormatter.ofPattern(Pattern.ISO_DATETIME);
        var outputFormatter = DateTimeFormatter.ofPattern(Pattern.DD_MM_YYYY_SPLASH);
        return LocalDateTime.parse(isoDateTime, formatter).format(outputFormatter);
    }

    /**
     * Get now with time zone.
     *
     * @param timeZoneId timeZoneId.
     * @return Date.
     */
    public static Date now(String timeZoneId) {
        return Calendar.getInstance(TimeZone.getTimeZone(timeZoneId)).getTime();
    }

    /**
     * Get now with time zone.
     *
     * @return Date.
     */
    public static Date now() {
        return now(ZoneId.systemDefault().getId());
    }

    /**
     * Get date before from now.
     *
     * @param timeZoneId timeZoneId.
     * @param dateNum    number date to add.
     * @return Date.
     */
    public static Date addDate(String timeZoneId, int dateNum) {
        return addDate(Calendar.DAY_OF_YEAR, timeZoneId, dateNum);
    }

    /**
     * Get date before from now.
     *
     * @param dateNum number date to add.
     * @return Date.
     */
    public static Date addDate(int dateNum) {
        return addDate(ZoneId.systemDefault().getId(), dateNum);
    }

    /**
     * Get date before from now.
     *
     * @param monthNum number month to add.
     * @return Date.
     */
    public static Date addMonth(int monthNum) {
        return addDate(Calendar.MONTH, ZoneId.systemDefault().getId(), monthNum);
    }

    /**
     * Get date before from now.
     *
     * @param field      calendar calculation
     * @param timeZoneId timeZoneId.
     * @param amount     number time to add.
     * @return Date.
     */
    public static Date addDate(int field, String timeZoneId, int amount) {
        Date now = now(timeZoneId);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZoneId));
        calendar.setTime(now);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * Check is before
     *
     * @param strExpectedDate expected.
     * @param strCompareDate  date.
     * @param format          format.
     * @return true if expected > compareDate.
     */
    public static boolean isAfter(String strExpectedDate,
                                  String strCompareDate,
                                  String format) {
        var formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime expectedDate = LocalDateTime.parse(strExpectedDate, formatter);
        LocalDateTime compareDate = LocalDateTime.parse(strCompareDate, formatter);
        return expectedDate.isAfter(compareDate);
    }

    /**
     * Check is before
     *
     * @param strExpectedDate expected.
     * @param strCompareDate  date.
     * @param expectedFormat  format of expected date
     * @param compareFormat   format of compare date
     * @return true if expected > compareDate.
     */
    public static boolean isAfter(String strExpectedDate,
                                  String strCompareDate,
                                  String expectedFormat,
                                  String compareFormat) throws ParseException {
        var expectedDate = new SimpleDateFormat(expectedFormat).parse(strExpectedDate);
        var compareDate = new SimpleDateFormat(compareFormat).parse(strCompareDate);
        return expectedDate.after(compareDate);
    }

    /**
     * Check is before
     *
     * @param strExpectedDate expected.
     * @param strCompareDate  date.
     * @return true if expected > compareDate.
     */
    public static boolean isAfterIsoDate(String strExpectedDate,
                                         String strCompareDate) {
        var expectedDate = LocalDate.parse(strExpectedDate);
        var compareDate = LocalDate.parse(strCompareDate);
        return expectedDate.isAfter(compareDate);
    }

    /**
     * Get utc date.
     *
     * @param date input date.
     * @return Date in UTC.
     */
    public static String getUTCDateISO(Date date) {
        var sdf = new SimpleDateFormat(Pattern.ISO_DATETIME);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        return sdf.format(date);
    }

    /**
     * Get utc date.
     *
     * @return Datetime string in UTC.
     */
    public static String currentDateTimeUTC() {
        return LocalDateTime.now().toString();
    }

    /**
     * To Hours format.
     * ex: 01 Hours 01 Minutes 01 Seconds
     *
     * @param timeInMillis time in millis.
     */
    public static String toHours(long timeInMillis) {
        var duration = Duration.ofMillis(timeInMillis);
        return String.format("%d Hour(s) %d Minute(s) %d Second(s)%n",
                duration.toHours(), duration.toMinutes() % 60, duration.getSeconds() % 60);
    }

    /**
     * Convert String date to Date.
     *
     * @param strDate str date.
     * @return Date date.
     * @throws ParseException ex.
     */
    public static Date convertStrToDate(String strDate) throws ParseException {
        return new SimpleDateFormat(Pattern.ISO_DATE).parse(strDate);
    }

    /**
     * Convert String date to Date with format
     *
     * @param strDate str date.
     * @param format  Date format
     * @return Date date.
     * @throws ParseException ex.
     */
    public static Date convertStrToDate(String strDate, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(strDate);
    }

    public static final class Pattern {
        public static final String DD_MM_YYYY = "dd-MM-yyyy";

        public static final String DD_MM_YYYY_SPLASH = "dd/MM/yyyy";

        public static final String DD_MM_YYYY_DATETIME = "HH:mm:ss dd-MM-yyyy";

        public static final String ISO_DATETIME = "yyyy-MM-dd HH:mm:ss";

        public static final String ISO_DATETIME_MSE = "yyyy-MM-dd HH:mm:ss.SSS";

        public static final String UTC_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        public static final String ISO_DATE = "yyyy-MM-dd";

        public static final String ISO_DATE_UNDER = "yyyy_MM_dd";

        private Pattern() {
        }
    }
}
