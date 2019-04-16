package com.github.nicoraynaud.batch.utils;

import org.slf4j.MDC;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

public class MDCUtils {

    private static final String THREADMDC1_JOBNAME_ID = "threadMDC1";
    private static final String THREADMDC2_STEPNAME_ID = "threadMDC2";

    /**
     * MDC utilisé comme marqueur permettant au Step de savoir s'il est sur le même thread que le job
     */
    private static final String THREADMDC_MARKER_IN_JOB = "threadMDCMarkerInJob";

    private MDCUtils() {
    }

    /**
     * Exemple de format attendu par le logger ' job-[JobName|2]'
     */
    private static String getThreadMDC1JobNameId(JobExecution jobExecution) {
        return " job-[" + jobExecution.getJobInstance().getJobName() + "|" + Long.toString(jobExecution.getId()) + "]";
    }

    /**
     * Exemple de format attendu par le logger  ' step-[stepName|256]'
     */
    private static String getThreadMDC2JobNameId(StepExecution stepExecution) {
        return " step-[" + stepExecution.getStepName() + "|" + Long.toString(stepExecution.getId()) + "]";
    }

    public static void insertJobValues(JobExecution jobExecution) {
        MDC.put(THREADMDC1_JOBNAME_ID, getThreadMDC1JobNameId(jobExecution));
    }

    public static void removeJobValues() {
        MDC.remove(THREADMDC1_JOBNAME_ID);
    }


    public static void insertStepValues(StepExecution stepExecution) {
        MDC.put(THREADMDC2_STEPNAME_ID, getThreadMDC2JobNameId(stepExecution));
    }

    public static void removeStepValues() {
        MDC.remove(THREADMDC2_STEPNAME_ID);
    }

    public static void setJobMarkerInJob() {
        MDC.put(THREADMDC_MARKER_IN_JOB, Boolean.toString(true));
    }

    public static boolean hasJobMarkerInJob() {
        return Boolean.valueOf(MDC.get(THREADMDC_MARKER_IN_JOB));
    }

    public static void removeJobMarkerInJob() {
        MDC.remove(THREADMDC_MARKER_IN_JOB);
    }
}
