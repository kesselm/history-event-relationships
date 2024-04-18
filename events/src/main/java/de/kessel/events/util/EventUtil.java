package de.kessel.events.util;

import de.kessel.events.exception.ErrorDetail;
import lombok.experimental.UtilityClass;
import org.jruby.ir.instructions.BIntInstr;

import java.text.SimpleDateFormat;
import java.util.*;

@UtilityClass
public class EventUtil {

    public static String createDate(int year, int month, int day, Locale locale) {
        GregorianCalendar calendar = new GregorianCalendar(locale);
        var result = new StringBuilder();

        if (day > 0) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            result.append("d.");
        }

        if (month > 0) {
            calendar.set(Calendar.MONTH, month);
            result.append(" MMMM ");
        }

        int size = String.valueOf(Math.abs(year)).length();
        result.append("y".repeat(size));

        if (year < 0) {
            calendar.set(Calendar.ERA, GregorianCalendar.BC);
            result.append(" G");
        }
        calendar.set(Calendar.YEAR, Math.abs(year));

        SimpleDateFormat dateFormat = new SimpleDateFormat(result.toString(), locale);
        Date date = calendar.getTime(); // current date
        return dateFormat.format(date);
    }

    public static Optional<ErrorDetail> getErrorDetailFromErrorMessage(String errorMessage) {
        for (ErrorDetail detail : ErrorDetail.values()) {
            if (detail.getErrorMessage().contains(errorMessage)) {
                return Optional.of(detail);
            }
        }
        return Optional.empty();
    }
}
