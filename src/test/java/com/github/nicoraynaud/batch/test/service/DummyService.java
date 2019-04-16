package com.github.nicoraynaud.batch.test.service;

import com.github.nicoraynaud.batch.test.item.LogItemProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

    private static final Log log = LogFactory.getLog(LogItemProcessor.class);

    public void logService(String from){
        log.info("logService " + from);
    }
}
