package com.github.nicoraynaud.batch.dto.mapping;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Mapper pour les dates.
 *
 * @author Alexis Toulotte
 */
@Mapper(componentModel = "spring")
public interface DateMapper {

    default LocalDateTime toLocalDateTime(Date date) {
        return (date == null) ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
