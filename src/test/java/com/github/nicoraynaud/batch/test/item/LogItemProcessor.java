package com.github.nicoraynaud.batch.test.item;

import com.github.nicoraynaud.batch.test.service.DummyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;


/**
 * Dummy {@link ItemProcessor} which only logs data it receives.
 */
public class LogItemProcessor implements ItemProcessor<String,String> {

	private static final Log log = LogFactory.getLog(LogItemProcessor.class);

	private final DummyService dummyService;
	private Long threadSleepTime;

	public LogItemProcessor(DummyService dummyService) {
		this.dummyService = dummyService;
	}

	public LogItemProcessor(DummyService dummyService, Long threadSleepTime) {
		this.dummyService = dummyService;
		this.threadSleepTime = threadSleepTime;
	}


	public String process(String item) throws InterruptedException {
		log.info(item);
		if (threadSleepTime != null){
			//Thread.sleep(threadSleepTime);
		}

		dummyService.logService("from batch");
		return item;
	}

}
