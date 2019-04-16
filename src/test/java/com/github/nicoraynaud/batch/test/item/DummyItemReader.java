package com.github.nicoraynaud.batch.test.item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;

import java.util.List;

/**
 * {@link ItemReader} with hard-coded input data.
 */
public class DummyItemReader implements ItemReader<String> {
	
	private static final Log log = LogFactory.getLog(DummyItemReader.class);

	private int index = 0;
	private final List<String> input;

	public DummyItemReader(List<String> input) {
		this.input = input;
	}


	/**
	 * Reads next record from input
	 */
	public String read() {
		String item;
		if (index < input.size()) {
			item = input.get(index++);
			log.info(item);
			return item;
		}
		else {
			return null;
		}
		
	}

}
