package org.zerock.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.mapper.TimeMapper;
import org.zerock.sample.SampleTests;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class JDBCTests {
	@Setter(onMethod_ = @Autowired)
	private TimeMapper timeMapper;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource dataSource;
	
	@Test
	public void testGetTime() {
		log.info("-----------------------");
		log.info(timeMapper.getTime());
	}
	
	
	
	
	@Test
	public void testConnection() {
		try (Connection con = 
				dataSource.getConnection()) {
			log.info("----------------------");
			log.info(con);
			log.info("----------------------");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
