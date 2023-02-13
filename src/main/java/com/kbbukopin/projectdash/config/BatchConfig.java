package com.kbbukopin.projectdash.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.kbbukopin.projectdash.entity.Project;
import com.kbbukopin.projectdash.listener.ProjectListener;
import com.kbbukopin.projectdash.repository.ProjectRepository;
import com.kbbukopin.projectdash.utils.BlankLineRecordSeparatorPolicy;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    //Reader class Object
    @Bean
    public FlatFileItemReader<Project> reader() {

       FlatFileItemReader<Project> reader= new FlatFileItemReader<>();
       reader.setResource(new ClassPathResource("/invoices.csv"));
       // Reader. setResource(new FileSystemResource("D:/mydata/invoices.csv"));
       // reader.setResource(new UrlResource("https://xyz.com/files/invoices.csv"));
       // reader.setLinesToSkip(1);

       reader.setLineMapper(new DefaultLineMapper<>() {{
           setLineTokenizer(new DelimitedLineTokenizer() {{
              setDelimiter(DELIMITER_COMMA);
              setNames("name","number","amount","discount","location");
           }});

           setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
              setTargetType(Project.class);
           }});
       }});

       reader.setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());

       return reader;
    }

    //Autowire InvoiceRepository
    @Autowired
    ProjectRepository repository;

    //Writer class Object
    @Bean
    public ItemWriter<Project> writer(){
       // return new ProjectItemWriter(); // Using lambda expression code instead of a separate implementation
       return invoices -> {
         System.out.println("Saving Invoice Records: " +invoices);
         repository.saveAll(invoices);
       };
    }

    //Listener class Object
    @Bean
    public JobExecutionListener listener() {
       return new ProjectListener();
    }

    //Autowire StepBuilderFactory
    @Autowired
    private StepBuilderFactory sbf;

    //Step Object
    @Bean
    public Step stepA() {
       return sbf.get("stepA")
               .<Project,Project>chunk(2)
               .reader(reader())
               .writer(writer())
               .build() 
       ;
    }

    //Autowire JobBuilderFactory
    @Autowired
    private JobBuilderFactory jbf;

    //Job Object
    @Bean
    public Job jobA(){
       return jbf.get("jobA")
              .incrementer(new RunIdIncrementer())
              .listener(listener())
              .start(stepA())
           // .next(stepB()) 
           // .next(stepC())
              .build()
       ;
    }

}