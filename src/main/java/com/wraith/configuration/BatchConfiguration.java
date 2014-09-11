package com.wraith.configuration;

import com.wraith.processor.MoneyTransaction;
import com.wraith.processor.TransactionProcessor;
import com.wraith.repository.TransactionRepository;
import com.wraith.repository.entity.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Inject;

/**
 * User: rowan.massey
 * Date: 11/09/2014
 * Time: 16:19
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Inject
    TransactionRepository transactionRepository;

    @Bean
    public ItemReader<MoneyTransaction> reader() {
        FlatFileItemReader<MoneyTransaction> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("upload/test-money-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<MoneyTransaction>() {
                                 {
                                     setLineTokenizer(new DelimitedLineTokenizer() {
                                                          {
                                                              setNames(new String[]{"Number", "Date", "Account", "Payee", "Cleared", "Amount", "Category", "Subcategory", "Memo"});

                                                          }
                                                      }
                                     );
                                     setFieldSetMapper(new BeanWrapperFieldSetMapper<MoneyTransaction>() {
                                         {
                                             setTargetType(MoneyTransaction.class);

                                         }

                                     });
                                 }
                             }
        );
        return reader;
    }

    @Bean
    public ItemProcessor<MoneyTransaction, Transaction> processor() {
        return new TransactionProcessor();
    }

    @Bean
    public Job moneyTransactionImport(JobBuilderFactory jobs, Step s1) {
        return jobs.get("moneyTransactionImport")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
//                .listener(notificationExecutionsListener)
                .build();
    }

    @Bean
    public RepositoryItemWriter writer() {
        RepositoryItemWriter writer = new RepositoryItemWriter();
        writer.setRepository(transactionRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<MoneyTransaction> reader,
                      ItemWriter<Transaction> writer, ItemProcessor<MoneyTransaction, Transaction> processor) {
        return stepBuilderFactory.get("step1")
                .<MoneyTransaction, Transaction>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
