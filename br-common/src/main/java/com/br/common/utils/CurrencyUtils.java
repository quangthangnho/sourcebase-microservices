package com.br.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * @author an.cantuong
 */
public final class CurrencyUtils {

    public static final String PATTERN_MONEY_FORMAT = "#,###";

    private CurrencyUtils() {
    }

    /**
     * Format money using Long parser for integer fraction
     * 1000.0 => ["1000","0"]
     *
     * @param inputMoney input.
     * @return formatted money.
     */
    public static String formatCurrency(String inputMoney) {
        if (StringUtils.isEmpty(inputMoney)) return "";
        var fractions = inputMoney.split("\\.");
        if (fractions.length < 2) {
            return formatMoney(inputMoney, ",");
        }
        var decimalFraction = StringUtils.stripEnd(fractions[1], "0");
        if (decimalFraction.isEmpty()) { // 1.0 -> 1
            return formatMoney(fractions[0], ",");
        }
        return String.format("%s.%s", formatMoney(fractions[0], ","), decimalFraction);
    }

    /**
     * Format money using Regex.
     *
     * @param money input.
     * @param delim delimiter.
     * @return formatted money.
     */
    public static String formatMoney(String money, String delim) {
        if (StringUtils.isEmpty(money)) return "";
        var pattern = Pattern.compile("\\B(?<!\\.\\d.)(?=(\\d{3})+(?!\\d))");
        return pattern.matcher(money).replaceAll(delim);
    }

    /**
     * Format money.
     *
     * @param inputMoney input.
     * @return formatted money.
     */
    public static String formatMoney(Long inputMoney) {
        var formatter = new DecimalFormat(PATTERN_MONEY_FORMAT);
        return formatter.format(inputMoney);
    }
}
