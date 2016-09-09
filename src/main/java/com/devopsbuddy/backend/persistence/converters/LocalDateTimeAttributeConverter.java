package com.devopsbuddy.backend.persistence.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by larris on 08/09/16.
 */
@Converter
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime,Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return (localDateTime == null ? null : Timestamp.valueOf(localDateTime));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimeStamp) {
        return (sqlTimeStamp == null ? null : sqlTimeStamp.toLocalDateTime());
    }
}
