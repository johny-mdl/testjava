package com.bet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bet.services.BetService;

@RunWith(SpringRunner.class)
@WebMvcTest(BetController.class)
@AutoConfigureMockMvc(secure = false)
public class TestBetController {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BetService service;

	@Before
	public void createData() {

	}

	@Test
	public void test() throws Exception {

		mvc.perform(get("/bets/user/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}
