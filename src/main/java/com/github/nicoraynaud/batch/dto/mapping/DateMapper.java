package com.github.nicoraynaud.batch.dto.mapping;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Mapper pour les dates.
 *
 * @author Alexis Toulotte
 */
@Mapper(componentModel = "spring")
public interface DateMapper {

    default OffsetDateTime toOffsetDateTime(Date date) {
        return (date == null) ? null : date.toInstant().atZone(ZoneOffset.systemDefault()).toOffsetDateTime();
    }

    default OffsetDateTime toOffsetDateTime(LocalDateTime date) {
        return (date == null) ? null : date.atZone(ZoneOffset.systemDefault()).toOffsetDateTime();
    }

}
