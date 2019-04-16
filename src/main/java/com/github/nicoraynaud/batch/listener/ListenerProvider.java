/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.nicoraynaud.batch.listener;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecutionListener;

import java.util.Set;

/**
 * spring-boot-starter-batch-web automatically registers JobExecutionListeners and
 * StepExecutionListeners at each Job provided by Spring beans implementing
 * this interface.
 *
 * @author Tobias Flohre
 *         Cette classe à été copié puis modifié de
 * @see {https://github.com/codecentric/spring-boot-starter-batch-web/
 */
public interface ListenerProvider {

    /**
     * Returns a set of JobExecutionListeners that will be added to each Job.
     * May not return null.
     *
     * @return Returns a set of JobExecutionListeners that will be added to each Job. May not return null.
     */
    Set<JobExecutionListener> jobExecutionListeners();

    /**
     * Returns a set of StepExecutionListeners that will be added to each Job.
     * May not return null.
     *
     * @return Returns a set of StepExecutionListeners that will be added to each Job. May not return null.
     */
    Set<StepExecutionListener> stepExecutionListeners();

}
