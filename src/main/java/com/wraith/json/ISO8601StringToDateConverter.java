package com.wraith.json;

import org.springframework.core.convert.converter.Converter;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 21:37
 */
public class ISO8601StringToDateConverter implements Converter<String[], Date> {

    @Override
    public Date convert(String[] source) {
        if (source.length == 1) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(URLDecoder.decode(source[0]));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            return null;
        }
    }
}
