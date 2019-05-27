package com.github.nicoraynaud.batch.dto.mapping;

import com.github.nicoraynaud.batch.config.BatchApiAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests unitaires de la classe {@link DateMapper}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchApiAutoConfiguration.class)
public class DateMapperTest {

    @Autowired
    private DateMapper mapper;

    @Test
    public void testDateToOffsetDate() {
        assertNull(mapper.toOffsetDateTime((Date) null));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, 3);
        c.set(Calendar.DAY_OF_MONTH, 21);
        c.set(Calendar.HOUR, 1);
        c.set(Calendar.MINUTE, 39);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        assertEquals(LocalDateTime.parse("2018-04-21T01:39:00").atZone(ZoneId.systemDefault()).toOffsetDateTime(),
                mapper.toOffsetDateTime(c.getTime()));
    }

    @Test
    public void testLocalDateToOffsetDate() {
        assertNull(mapper.toOffsetDateTime((LocalDateTime) null));
        LocalDateTime localDateTime = LocalDateTime.parse("2019-05-27T12:30:57");
        assertEquals(LocalDateTime.parse("2019-05-27T12:30:57").atZone(ZoneId.systemDefault()).toOffsetDateTime(),
                mapper.toOffsetDateTime(localDateTime));
    }
}
