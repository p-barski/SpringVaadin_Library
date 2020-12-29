package application.algorithm;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.LibraryApp;
import application.services.BookService;
import application.services.OrderService;
import application.services.UserService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = LibraryApp.class)
@ActiveProfiles("test")
class ControllerTests {
	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mvc;

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@BeforeEach
	public void init() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void ControllerTest1() throws Exception {
		String userName = "Name0";
		String url = "/rest/" + userName;
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		Integer status = mvcResult.getResponse().getStatus();
		Integer expectedResponse = 200;
		assertEquals(expectedResponse, status);

		String content = mvcResult.getResponse().getContentAsString();
		Penalty penalty = mapFromJson(content, Penalty.class);
		Penalty expectedPenalty = new Penalty(userName, (long) 6);
		assertEquals(expectedPenalty, penalty);
	}
}
