package com.rohit.batch.schedular;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.rohit.batch.pojo.Coffee;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Value("${file.input}")
    private String fileInput;
    
    @Autowired
	private DataSource dataSource;
    
    @Bean
    public FlatFileItemReader<Coffee> reader() {
		
    	FlatFileItemReader<Coffee> reader = new FlatFileItemReader<>();
		//reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource(fileInput));
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] { "brand", "origin", "characteristics"});
		
		DefaultLineMapper<Coffee> coffeeLineMapper = new DefaultLineMapper<>();
		coffeeLineMapper.setLineTokenizer(tokenizer);
		coffeeLineMapper.setFieldSetMapper(new
				 BeanWrapperFieldSetMapper<Coffee>() {{ setTargetType(Coffee.class); }});
		coffeeLineMapper.afterPropertiesSet();
		
		reader.setLineMapper(coffeeLineMapper);
		
		return reader;
    }
    
    

   /* @Bean
    public JdbcBatchItemWriter<Coffee> writer() {
       
        JdbcBatchItemWriter<Coffee> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(this.dataSource);
		writer.setSql("INSERT INTO coffee VALUES (:coffeeId,:brand, :origin, :characteristics)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.afterPropertiesSet();
		
		return writer;
    }*/
    
    @Bean
    public CoffeeItemProcessor processor() {
        return new CoffeeItemProcessor();
    }
    
    @Bean
	public JobCompletionNotificationListener listener() {
		return new JobCompletionNotificationListener();
	}
    
    @Bean
    public CoffeeItemWritter writer() {
        return new CoffeeItemWritter();
    }
    
    @Bean
    public Step demoStep() {
        return stepBuilderFactory.get("demoStep")
            .<Coffee, Coffee> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean("DemoJob")
    public Job demoExecutorJob() {
        return jobBuilderFactory.get("demoExecutorJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener())
            .flow(demoStep())
            .end()
            .build();
    }
    
    

}
