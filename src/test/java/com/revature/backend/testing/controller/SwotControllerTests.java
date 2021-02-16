package com.revature.backend.testing.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.backend.config.SecurityConfig;
import com.revature.backend.controller.SwotController;
import com.revature.backend.model.AnalysisItem;
import com.revature.backend.model.AnalysisType;
import com.revature.backend.model.Associate;
import com.revature.backend.model.Manager;
import com.revature.backend.model.Swot;
import com.revature.backend.security.JwtAuthenticationFilter;
import com.revature.backend.service.SwotService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * This is a set of test for the SwotController class. It contaons 2 tests.<p>
 * These tests make use of Junit 5.<p>
 * test 1:Tests if the Application Loads.<p>
 * test 2:Tests that you can hit the /swot/view/all endpoint and get the expected results.<p>
 *
 * @author ? :wrote initial tests and individual test documentation.
 * @author Matthew Sheldon: Updated tests to work with Junit 5 and updated documentation.
 */
@SpringBootTest
public class SwotControllerTests {
	ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mockMvc;

	@Mock
	private SwotService service;

  @BeforeEach
  public void before(){
    mockMvc=MockMvcBuilders.standaloneSetup(swotController).build();
  }

	@Mock
	private JwtAuthenticationFilter jwtAuth;
	@InjectMocks
	SwotController swotController;

	/*
	 * Create a Swot object with an ID. This is obviously a lot of data and is more
	 * going to be a template, fit to change in the future. 2/3/2021 - changed
	 * method to include new analysis item comment field
	 */
	private Swot mockSwot(int id) {

		List<AnalysisItem> mockAnalysisItems = new ArrayList<>();

		AnalysisItem mockAI1 = new AnalysisItem(1, "Strength", null, AnalysisType.STRENGTH, "Strength comment");
		AnalysisItem mockAI2 = new AnalysisItem(2, "Weakness", null, AnalysisType.WEAKNESS, "Weakness comment");
		AnalysisItem mockAI3 = new AnalysisItem(3, "Opportunity", null, AnalysisType.OPPORTUNITY,
				"Opportunity comment");
		AnalysisItem mockAI4 = new AnalysisItem(4, "Threat", null, AnalysisType.THREAT, "Threat comment");

		mockAnalysisItems.add(mockAI1);
		mockAnalysisItems.add(mockAI2);
		mockAnalysisItems.add(mockAI3);
		mockAnalysisItems.add(mockAI4);

		Swot mockSwot = new Swot(id, new Associate(1, null, null, null, null, null, null, null),
				new Manager(1, null, null, null), new Timestamp(System.currentTimeMillis()),
				new Timestamp(System.currentTimeMillis()), "");
		mockSwot.setAnalysisItems(mockAnalysisItems);

		return mockSwot;
	}

	/*
	 * Sanity Check - Ensure the application context is loaded before moving on to
	 * the other tests.
	 */
	@Test
	public void contextLoads() throws Exception {
		assertThat(swotController).isNotNull();
	}

	/*
	 * Verify that the HTTP status is 200 - OK for a basic GET request with no
	 * parameters.
	 */
	@Test
	public void controllerSuccess() throws Exception {

		// Create the expected list.
		List<Swot> swots = new ArrayList<>();
		swots.add(mockSwot(1));
		swots.add(mockSwot(2));

		when(service.retrieveAllSwot()).thenReturn(swots);

		this.mockMvc.perform(get("/swot/view/all")).andDo(print()).andExpect(status().isOk());
	}
}
