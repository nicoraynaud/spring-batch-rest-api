


INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(33, 0, 'jobReference', '256522b22d2a26fa23b520fcff07e97d');
INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(60, 0, 'JobRetry', 'd41d8cd98f00b204e9800998ecf8427e');
INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(71, 0, 'JobRetry', '1b7cf599a8d81d1744af2f1477f77f6b');
INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(73, 0, 'JobRetry', '458a2252ed000d0778e728b0ac59e17d');
INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(149, 0, 'JobRetry', 'ccd913b75ecf39076a14e3442a4d0c12');
INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(345, 0, 'jobReference', '65469b84cfc1b04747193b3ecf624819');
INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(346, 0, 'jobReference', 'ef2213d713be4d1b485f2a76e9a720d2');
INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(347, 0, 'jobReference', 'e968e3f2e0bb01a7f6d18aa9a4b32408');
INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(350, 0, 'jobReference', 'db956231fe3ef3c5eff2a68e85dfe946');


INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(150, 2, 149, '2018-05-25 13:44:05.690', '2018-05-25 13:44:05.690', '2018-05-25 13:44:05.690', 'FAILED', 'FAILED', '', '2018-05-25 13:44:05.690', NULL);

INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(346, 2, 345, '2018-05-25 13:44:05.689', '2018-05-25 13:44:05.693', '2018-05-25 13:44:05.793', 'FAILED', 'FAILED', '', '2018-05-25 13:44:05.793', NULL);
INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(351, 2, 350, '2018-05-25 14:04:14.459', '2018-05-25 14:04:14.464', '2018-05-25 14:04:14.511', 'FAILED', 'FAILED', '', '2018-05-25 14:04:14.511', NULL);
INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(347, 2, 346, '2018-05-25 13:44:38.099', '2018-05-25 13:44:38.105', '2018-05-25 13:46:46.097', 'FAILED', 'FAILED', 'java.nio.file.NoSuchFileException: C:\test\CONTRATS_BSCS_20180516_092636.csv -> C:\test\requisitions-loader\archive\completed\test\CONTRATS_BSCS_20180516_092636.csv.completed
	at sun.nio.fs.WindowsException.translateToIOException(WindowsException.java:79)
	at sun.nio.fs.WindowsException.rethrowAsIOException(WindowsException.java:97)
	at sun.nio.fs.WindowsFileCopy.move(WindowsFileCopy.java:387)
	at sun.nio.fs.WindowsFileSystemProvider.move(WindowsFileSystemProvider.java:287)
	at java.nio.file.Files.move(Files.java:1395)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:333)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157)
	at org.springframework.aop.support.DelegatingIntroductionInterceptor.doProceed(DelegatingIntroductionInterceptor.java:133)
	at org.springframework.aop.support.DelegatingIntroductionInterceptor.invoke(DelegatingIntroductionInterceptor.java:121)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:213)
	at com.sun.proxy.$Proxy173.execute(Unknown Source)
	at org.springframework.batch.core.step.tasklet.TaskletStep$ChunkTransactionCallback.doInTransaction(TaskletStep.java:406)
	at org.springframework.batch.core.step.tasklet.TaskletStep$ChunkTransactionCallback.doInTransaction(TaskletStep.java:330)
	at org.springframework.transaction.support.TransactionTemplate.execute(TransactionTemplate.java:133)
	at org.springframework.batch.core.step.tasklet.TaskletStep$2.doInChunkContext(TaskletStep.java:272)
	at org.springframework.batch.core.scope.context.StepContextRepeatCallback.doInIteration(StepContextRepeatCallback.java:81)
	at org.springframework.batch.repeat.support.RepeatTemplate.getNextResult(RepeatTemplate.java:374)
	at org.springframework.batch.repeat.support.RepeatTemplate.execut', '2018-05-25 13:46:46.101', NULL);
INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(348, 2, 347, '2018-05-25 14:00:33.867', '2018-05-25 14:00:33.872', '2018-05-25 14:00:33.922', 'FAILED', 'FAILED', '', '2018-05-25 14:00:33.922', NULL);
INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(34, 2, 33, '2018-03-16 13:30:58.323', '2018-03-16 13:30:58.371', '2018-03-16 13:31:53.334', 'COMPLETED', 'COMPLETED', '', '2018-03-16 13:31:53.344', NULL);
INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(72, 1, 71, '2018-03-23 15:34:04.506', '2018-03-23 15:34:04.511', NULL, 'STARTED', 'UNKNOWN', '', '2018-03-23 15:34:04.511', NULL);
INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(61, 2, 60, '2018-03-23 14:49:11.158', '2018-03-23 14:49:11.331', NULL, 'STOPPING', 'UNKNOWN', '', '2018-03-23 15:55:25.873', NULL);
INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(74, 2, 73, '2018-04-03 12:57:45.542', '2018-04-03 12:57:45.558', '2018-04-03 12:57:49.904', 'COMPLETED', 'COMPLETED', '', '2018-04-03 12:57:49.904', NULL);




INSERT INTO public.batch_job_execution_params
(job_execution_id, type_cd, key_name, string_val, date_val, long_val, double_val, identifying)
VALUES(150, 'LONG', 'run.id', '', '1970-01-01 11:00:00.000', 76, 0, 'Y');
INSERT INTO public.batch_job_execution_params
(job_execution_id, type_cd, key_name, string_val, date_val, long_val, double_val, identifying)
VALUES(150, 'STRING', 'time', '1521779644490', '1970-01-01 11:00:00.000', 0, 0, 'Y');
