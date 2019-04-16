package com.github.nicoraynaud.batch.dto.mapping;

import com.github.nicoraynaud.batch.config.BatchApiAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Calendar;

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
    public void testToLocalDate() {
        assertNull(mapper.toLocalDateTime(null));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, 3);
        c.set(Calendar.DAY_OF_MONTH, 21);
        assertEquals(LocalDate.of(2018, 4, 21), mapper.toLocalDateTime(c.getTime()).toLocalDate());
    }
}
