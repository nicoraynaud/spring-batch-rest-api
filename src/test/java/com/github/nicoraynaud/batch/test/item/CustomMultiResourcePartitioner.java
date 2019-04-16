package com.github.nicoraynaud.batch.test.item;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class CustomMultiResourcePartitioner implements Partitioner {

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> map = new HashMap<>(gridSize);
        for (int i = 0; i < 3; i++) {
            ExecutionContext context = new ExecutionContext();
            context.putString("index", Integer.toString(i));
            map.put("partition_key" + i, context);
        }
        return map;
    }
}