package com.wraith.service;

import com.wraith.configuration.MoneyRunIdIncrementer;
import com.wraith.exception.MoneyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;

/**
 * This service class deals with all data upload requirements. This is the entry point for all the relevant processing.
 * <p>
 * User: rowan.massey
 * Date: 26/08/2014
 * Time: 21:11
 */
@Service
public class DataUploadService {

    private static final String TEMP_DIRECTORY = "java.io.tmpdir";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private JobLauncher jobLauncher;

    //    @Inject
    private Job job;

//    @Inject
//    private JobRegistry jobRegistry;

    @Inject
    private JobOperator operator;
//
//    @Inject
private JobBuilderFactory jobs;
    //
//    @Inject
    private Step step;


    @Inject
    public DataUploadService(JobBuilderFactory jobBuilderFactory, Step step) {
        this.jobs = jobBuilderFactory;
        this.step = step;
    }
    /**
     * @param name
     * @param file
     */
    public JobExecution processFile(String name, MultipartFile file) {
        logger.debug(String.format("Starting process of '%s' for upload, and import.", name));
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String path = System.getProperty(TEMP_DIRECTORY);
                String fileName = path.concat(name);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(fileName)));
                stream.write(bytes);
                stream.close();
                return startImportJob(fileName, "moneyTransactionImport");
            } catch (Exception e) {
                logger.error(String.format("Error processing file '%s'.", name), e);
                throw new MoneyException(e);
            }
        } else {
            throw new MoneyException("There was no file to process.");
        }
    }

    /**
     * @param file
     */
    private JobExecution startImportJob(String file, String jobName) {
        logger.debug(String.format("Starting job to import file '%s'.", file));
        try {
            job = jobs.get(jobName).incrementer(new MoneyRunIdIncrementer()).flow(step).end().build();
            return jobLauncher.run(job, new JobParametersBuilder().addString("targetFile", file).toJobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            logger.error(String.format("Job for processing file '%s' is already running.", file), e);
            throw new MoneyException(e);
        } catch (JobParametersInvalidException e) {
            logger.error(String.format("Invalid parameters for processing of file '%s'.", file), e);
            throw new MoneyException(e);
        } catch (JobRestartException e) {
            logger.error(String.format("Error restarting job, for processing file '%s'.", file), e);
            throw new MoneyException(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.error(String.format("Job to process file '%s' has already completed.", file), e);
            throw new MoneyException(e);
        }
    }

    /**
     *
     */
    public void stopJob() {

    }

    /**
     * @param job
     */
    public void stopAllExecutions(String job) {
        Set<Long> runningJobs;
        try {
            runningJobs = operator.getRunningExecutions(job);
            if (!runningJobs.isEmpty()) {
                for (Long executionId : runningJobs) {
                    operator.abandon(executionId);
                }
            }
        } catch (NoSuchJobException | NoSuchJobExecutionException e) {
            logger.error(String.format("Job '%s' does not exist.", job), e);
            throw new MoneyException(e);
        } catch (JobExecutionAlreadyRunningException e) {
            logger.error(String.format("Job '%s' is already running.", job), e);
            throw new MoneyException(e);
        }
    }
}
