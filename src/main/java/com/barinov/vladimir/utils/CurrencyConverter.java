package com.barinov.vladimir.utils;

import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import java.math.BigDecimal;

/**
 * CurrencyConverter class
 */
public class CurrencyConverter {
    private static final String USD = "840";
    private static final String EUR = "978";
    private static final String GBP = "826";

    /**
     * Getting exchange rate
     *
     * @param codeFrom payer currency code
     * @param codeTo   payee currency code
     * @return exchange rate
     */
    public static BigDecimal convert(String codeFrom, String codeTo) {
        BigDecimal rate = BigDecimal.valueOf(1.0);
        if (!validateCurrency(codeFrom) && !validateCurrency(codeTo)) {
            throw new WebApplicationException("Currency code is incorrect!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        if (USD.equals(codeFrom)) {
            if (EUR.equals(codeTo)) {
                rate = BigDecimal.valueOf(0.88);
            } else if (GBP.equals(codeTo)) {
                rate = BigDecimal.valueOf(0.70);
            }
        } else if (EUR.equals(codeFrom)) {
            if (USD.equals(codeTo)) {
                rate = BigDecimal.valueOf(1.13);
            } else if (GBP.equals(codeTo)) {
                rate = BigDecimal.valueOf(0.80);
            }
        } else if (GBP.equals(codeFrom)) {
            if (USD.equals(codeTo)) {
                rate = BigDecimal.valueOf(1.42);
            } else if (EUR.equals(codeTo)) {
                rate = BigDecimal.valueOf(1.24);
            }
        }
        return rate;
    }

    /**
     * Validating currency
     *
     * @param currencyCode currency code
     * @return is the currency code correct or not
     */
    public static boolean validateCurrency(String currencyCode) {
        if (USD.equals(currencyCode) || EUR.equals(currencyCode) || GBP.equals(currencyCode)) {
            return true;
        } else {
            return false;
        }
    }
}
