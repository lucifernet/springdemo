package com.example.demo;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.models.Coin;
import com.example.demo.repository.CoinRepository;
import com.example.demo.services.CoinService;
import com.example.demo.utils.HttpUtility;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {

	@Autowired
	private WebApplicationContext applicationContext;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private CoinRepository coinRepository;

	@Autowired
	private CoinService coinService;

	private MockMvc mockMvc;
	private Logger logger;

	@Before
	public void setUp() throws Exception {
		this.logger = LoggerFactory.getLogger("test");
		DefaultMockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(applicationContext);
		this.mockMvc = mockMvcBuilder.build();
	}

	@Test
	public void testGetAll() {
		var uri = "/api/coin/all";
		try {

			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
			var response = result.getResponse();
			var status = response.getStatus();
			Assert.assertEquals("錯誤", 200, status);

			var body = response.getContentAsString();
			Assert.assertNotNull("回傳為空值", body);
			logger.info("取回所有內容為：" + body);

			var results = mapper.readValue(body, List.class);
			Assert.assertTrue("陣列不為空", results.size() > 0);			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testInsertCoin() {
		var uri = "/api/coin/";
		try {
			Coin coin = new Coin();
			coin.setCode("TWD");
			coin.setRate("1.0");
			coin.setZh_tw("新台幣");
			
			var content = mapper.writeValueAsString(coin);
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(content)).andReturn();
			var response = result.getResponse();
			var status = response.getStatus();
			Assert.assertEquals("錯誤", 200, status);

			var body = response.getContentAsString();
			Assert.assertNotNull("回傳為空值", body);
			logger.info("取回新增內容為：" + body);

			var newCoin = mapper.readValue(body, Coin.class);
			Assert.assertTrue("新幣別新增完成", newCoin.getId() > 0);		
			
			Assert.assertEquals(newCoin.getZh_tw(), "新台幣");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testUpdateCoin() {
		var uri = "/api/coin/1";
		try {
			Coin coin = new Coin();
			coin.setCode("USD");			
			coin.setZh_tw("美刀");
			coin.setId(200); //whatever it was set. It won't change the original value.
			coin.setRate("30");
			coin.setDescription("United States Dollar");
			coin.setRate_float("30");
			coin.setSymbol("&#36;");
			
			var content = mapper.writeValueAsString(coin);
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON).content(content)).andReturn();
			var response = result.getResponse();
			var status = response.getStatus();
			Assert.assertEquals("錯誤", 200, status);

			var body = response.getContentAsString();
			Assert.assertNotNull("回傳為空值", body);
			logger.info("取回更新內容為：" + body);

			var newCoin = mapper.readValue(body, Coin.class);
			Assert.assertTrue("修改完成", newCoin.getId() == 1);			
			Assert.assertEquals(newCoin.getZh_tw(), "美刀");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testDeleteCoin() {
		var uri = "/api/coin/1";

		try {

			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
			var response = result.getResponse();
			var status = response.getStatus();
			Assert.assertEquals("錯誤", 200, status);

			var coin = coinRepository.findById(1);
			Assert.assertFalse("幣別應該已經刪除", coin.isPresent());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testFetchCoinDesk() throws IOException {		
		var result = coinService.fetchCoinDesk();

		Assert.assertNotNull("取回資料有值", result);
		this.logger.info("取回 coin desk 值為：" + result);
	}

	@Test
	public void testSyncCoinDesk() throws IOException {
		var result = coinService.syncCoinDesk();
		this.logger.info("轉換 coin desk 內容為：" + result);
		Assert.assertTrue("含有內容", result.size() > 0);
	}
}
