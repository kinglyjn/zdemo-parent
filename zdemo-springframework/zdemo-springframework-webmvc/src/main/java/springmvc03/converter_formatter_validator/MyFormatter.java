package springmvc03.converter_formatter_validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;


public class MyFormatter implements Formatter<Date>{

	@Override
	public String print(Date date, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm.S", locale);
		return sdf.format(date);
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm.S", locale);
		return sdf.parse(text);
	}
}
