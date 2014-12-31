package com.wraith.money.web.configuration;

import java.net.UnknownHostException;

import javax.inject.Inject;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.wraith.money.data.Transaction;
import com.wraith.money.repository.TransactionRepository;
import com.wraith.money.web.processor.MoneyTransaction;
import com.wraith.money.web.processor.TransactionProcessor;

/**
 * User: rowan.massey Date: 11/09/2014 Time: 16:19
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Inject
	private TransactionRepository transactionRepository;

	@Inject
	private JobRepository jobRepository;

	@Bean
	@StepScope
	public FlatFileItemReader<MoneyTransaction> reader(@Value("#{jobParameters[targetFile]}") String file) {
		FlatFileItemReader<MoneyTransaction> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(file));
		reader.setLineMapper(new DefaultLineMapper<MoneyTransaction>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "Number", "Date", "Account", "Payee", "Cleared", "Amount", "Category",
								"Subcategory", "Memo" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<MoneyTransaction>() {
					{
						setTargetType(MoneyTransaction.class);

					}
				});
			}
		});
		reader.setLinesToSkip(1);
		return reader;
	}

	@Bean
	public ItemProcessor<MoneyTransaction, Transaction> processor() {
		return new TransactionProcessor();
	}

	@Bean
	public MongoItemWriter writer() {
		MongoItemWriter writer = new MongoItemWriter();
		writer.setCollection("transaction");
		writer.setTemplate(mongoOperations());
		return writer;
	}

	@Bean
	public Step step(StepBuilderFactory stepBuilderFactory, ItemReader<MoneyTransaction> reader, ItemWriter<Transaction> writer,
			ItemProcessor<MoneyTransaction, Transaction> processor) {
		return stepBuilderFactory.get("step").<MoneyTransaction, Transaction> chunk(100).reader(reader).processor(processor)
				.writer(writer).build();
	}

	@Bean
	public SimpleAsyncTaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
		executor.setConcurrencyLimit(1);
		return executor;
	}

	@Bean
	public SimpleJobLauncher jobLauncher() {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(taskExecutor());
		return jobLauncher;
	}

	@Bean
	//TODO: Might be able to get this from the autoconfiguration setup for mongo.
	public MongoOperations mongoOperations() {
		MongoOperations operations = null;
		try {
			Mongo mongo = new MongoClient("127.0.0.1", 27017);
			MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, "test");
			operations = new MongoTemplate(mongoDbFactory);
		} catch (UnknownHostException e) {

		}
		return operations;
	}


//	@Bean
//	public JobOperator operator() {
//		SimpleJobOperator operator = new SimpleJobOperator();
//		operator.setJobRepository(jobRepository);
//		operator.setJobLauncher(jobLauncher());
//		operator.setJobRegistry(jobRegistry());
//		operator.setJobExplorer(jobExplorer());
//		return operator;
//	}
//
//	@Bean
//	public MapJobRegistry jobRegistry() {
//		return new MapJobRegistry();
//	}
//
//	@Bean
//	public JobExplorer jobExplorer() {
//		JobExplorerFactoryBean factoryBean = new JobExplorerFactoryBean();
//		factoryBean.set
//	}
}
