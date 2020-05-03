package com.parkinglot.config;

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
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.parkinglot.model.LotDetail;


@Configuration
@EnableBatchProcessing
public class ParkingLotBatchConfiguration {

	@Autowired
	private JobBuilderFactory jbf;
	 
	@Autowired
	private StepBuilderFactory sbf;
	
	@Autowired
	private DataSource datasource;
	
	@Bean
	public LineMapper<LotDetail> lineMapper(){
		DefaultLineMapper<LotDetail> linemapper=new DefaultLineMapper<>();
		DelimitedLineTokenizer linetokenizer=new DelimitedLineTokenizer();
		BeanWrapperFieldSetMapper<LotDetail> bfsMapper=new BeanWrapperFieldSetMapper<>();
		linetokenizer.setNames(new String[] {"lotid","floor","lottype","bno","bname","city","location","amount","status"});
		linetokenizer.setIncludedFields(new int[] {0,1,2,3,4,5,6,7,8});
		bfsMapper.setTargetType(LotDetail.class);
		linemapper.setLineTokenizer(linetokenizer);//set lineTokenizer----
		linemapper.setFieldSetMapper(bfsMapper); //set bfsMapper--- to the linemapper
		return linemapper;
	}
	
	@Bean
	public FlatFileItemReader<LotDetail> reader(){
		FlatFileItemReader<LotDetail> itemReader=new FlatFileItemReader<>();
		itemReader.setResource(new ClassPathResource("parkinglot.csv"));
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}
	
	@Bean
	public JdbcBatchItemWriter<LotDetail> writer(){
		JdbcBatchItemWriter<LotDetail> itemWriter=new JdbcBatchItemWriter<>();
		itemWriter.setDataSource(datasource);
		itemWriter.setSql("insert into lotdetail (lotid,floor,lottype,bno,bname,city,location,amount,status) values(:lotid,:floor,:lottype,:bno,:bname,:city,:location,:amount,:status)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<LotDetail>());
		return itemWriter;
	}
	
	@Bean
	public Step step1() {
		return sbf.get("step1").<LotDetail,LotDetail>chunk(10)
				.reader(reader())
				//.processor(function)--for logs enable then work
				.writer(writer()).build();
	}
	
	@Bean
	public Job readvendorcsvfile() {
		return jbf.get("readlotBulkcsvfile").incrementer(new RunIdIncrementer()).start(step1()).build();
	}
	
	
}
