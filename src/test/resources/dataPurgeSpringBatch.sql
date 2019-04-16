


INSERT INTO public.batch_job_instance
(job_instance_id, version, job_name, job_key)
VALUES(500, 0, 'nono', 'jobReference');


INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(500, 0, 500, '1999-05-25 13:44:05.690', '1999-05-25 13:44:05.690', '1999-05-25 13:44:05.690', 'COMPLETED', 'COMPLETED', '', '1999-05-25 13:44:05.690', NULL);
INSERT INTO public.batch_job_execution
(job_execution_id, version, job_instance_id, create_time, start_time, end_time, status, exit_code, exit_message, last_updated, job_configuration_location)
VALUES(501, 0, 500, '1999-06-25 13:44:05.690', '1999-06-25 13:44:05.690', '1999-06-25 13:44:05.690', 'COMPLETED', 'COMPLETED', '', '1999-06-25 13:44:05.690', NULL);