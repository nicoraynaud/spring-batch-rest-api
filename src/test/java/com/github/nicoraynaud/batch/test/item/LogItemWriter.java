package com.github.nicoraynaud.batch.test.item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;


/**
 * Dummy {@link ItemWriter} which only logs data it receives.
 */
public class LogItemWriter implements ItemWriter<Object> {

	private static final Log log = LogFactory.getLog(LogItemWriter.class);


	/**
	 * @see ItemWriter#write(List)
	 */
	public void write(List<? extends Object> data) {
		log.info(data);
	}

}
