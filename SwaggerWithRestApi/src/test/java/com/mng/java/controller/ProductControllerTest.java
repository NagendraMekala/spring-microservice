package com.mng.java.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mng.java.model.Product;
import com.mng.java.service.ProductService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class ProductControllerTest {

	@Mock
	private ProductService productService;

	private MockMvc mockMvc;

	@InjectMocks
	private ProductController productController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}
	
	@Test
	public void list() throws Exception {

		Map<Integer, Product> products = new HashMap<Integer, Product>();

		Product p1 = new Product();
			p1.setId(001);
			p1.setProductId("mock laptop");
			p1.setDescription("mock product configuration info");
			p1.setImageUrl("mock://localhost:8080/delImage");
			p1.setPrice(50000);
			p1.setVersion(5);
			
		products.put(001, p1);
		when(productService.listAllProducts()).thenReturn(products);

		MvcResult mvcResults = mockMvc
				.perform(get("http://localhost:8080/product/list").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		String results = mvcResults.getResponse().getContentAsString();
		assertEquals(HttpStatus.OK.value(), mvcResults.getResponse().getStatus());
		assertNotNull(results);

		System.out.println("results:  " + results);

		mockMvc.perform(get("http://localhost:8080/product/list")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.*", hasSize(1))).andExpect(jsonPath("1", is(notNullValue())));
	}
	
	@Test
	public void showProduct() throws Exception {

		Product p1 = new Product();
			p1.setId(111);
			p1.setProductId("mock laptop obj");
			p1.setDescription("mock product configuration info msg");
			p1.setImageUrl("mock://localhost:8080/delImage");
			p1.setPrice(50000);
			p1.setVersion(5);
		
		when(productService.getProductById(111)).thenReturn(p1);
		mockMvc.perform(get("http://localhost:8080/product/show/{id}",111).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.productId", is("mock laptop obj")))
		.andExpect(jsonPath("$.description", is("mock product configuration info msg")))
		.andExpect(jsonPath("$.imageUrl", is("mock://localhost:8080/delImage")))
		.andExpect(jsonPath("$.price", is(50000.0)))
		.andExpect(jsonPath("$.version", is(5)))
		.andReturn();
		
		// verify that service method was called once
		verify(productService).getProductById(111);
		
		// second approach 
		when(productService.getProductById(111)).thenReturn(p1);
		MvcResult mvcResults = mockMvc.perform(get("http://localhost:8080/product/show/{id}",111).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResults.getResponse().getStatus());
		assertNotNull(mvcResults.getResponse());
		System.out.println("****JSON REsponse*****"+ mvcResults.getResponse());
				  
		System.out.println("response status:" + mvcResults.getResponse().getStatus());
		System.out.println("results:  " + mvcResults.getResponse().getContentAsString());
	}
	
   @Test
   public void saveProduct() throws Exception {
	   Product p1 = new Product();
		p1.setId(111);
		p1.setProductId("mock laptop obj");
		p1.setDescription("mock product configuration info msg");
		p1.setImageUrl("mock://localhost:8080/delImage");
		p1.setPrice(50000);
		p1.setVersion(5);
		
		String exJson ="{\"id\":111,\"version\":5,\"productId\":\"mock laptop obj\",\"description\":\"mock product configuration info msg\",\"imageUrl\":\"mock://localhost:8080/delImage\",\"price\":50000.0}";
		
		doAnswer((i) ->{
			System.out.println("****do answer method*******: "+i);
			return null;
		}).when(productService).saveProduct(101, p1);;
		
		when(productService.exists(101)).thenReturn(true);
		
		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders
						.post("http://localhost:8080/product/add")
						.accept(MediaType.APPLICATION_JSON).content(exJson)
						.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals("Product saved successfully", mvcResult.getResponse().getContentAsString());
		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
   }

	@Test
	public void updateProduct() throws Exception {
     
		 Product p1 = new Product();
			p1.setId(111);
			p1.setProductId("mock laptop obj");
			p1.setDescription("mock product configuration info msg");
			p1.setImageUrl("mock://localhost:8080/delImage");
			p1.setPrice(50000);
			p1.setVersion(5);
			
			String exJson ="{\"id\":111,\"version\":5,\"productId\":\"mock laptop obj\",\"description\":\"mock product configuration info msg\",\"imageUrl\":\"mock://localhost:8080/delImage\",\"price\":50000.0}";
			
			doAnswer((i) ->{
				System.out.println("****do answer method*******: "+i);
				return null;
			}).when(productService).saveProduct(101, p1);;
			
			when(productService.getProductById(101)).thenReturn(p1);
			
			
			// Send course as body to /students/Student1/courses
			RequestBuilder requestBuilder = MockMvcRequestBuilders
							.put("http://localhost:8080/product/update/{id}",101)
							.accept(MediaType.APPLICATION_JSON).content(exJson)
							.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			
			assertEquals("Product updated successfully", mvcResult.getResponse().getContentAsString());
			assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void deleteProduct() throws Exception {
     
		 Product p1 = new Product();
			p1.setId(111);
			p1.setProductId("mock laptop obj");
			p1.setDescription("mock product configuration info msg");
			p1.setImageUrl("mock://localhost:8080/delImage");
			p1.setPrice(50000);
			p1.setVersion(5);
			
			doAnswer((i) ->{
				System.out.println("****do answer method*******: "+i);
				return null;
			}).when(productService).deleteProduct(101);;
			
			when(productService.getProductById(101)).thenReturn(p1);
			
			MvcResult mvcResults = mockMvc
					.perform(delete("http://localhost:8080/product/delete/{id}",101).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andReturn();
			
			assertEquals("Product deleted successfully", mvcResults.getResponse().getContentAsString());
			assertEquals(HttpStatus.OK.value(), mvcResults.getResponse().getStatus());
	}

	@Test
	public void getProductList() throws Exception {

		List<Product> al = new ArrayList<>();

		Product p1 = new Product();
		p1.setId(001);
		p1.setProductId("mock laptop");
		p1.setDescription("mock product configuration info");
		p1.setImageUrl("mock://localhost:8080/delImage");
		p1.setPrice(50000.0);
		p1.setVersion(5);
		al.add(p1);

		when(productService.getProductList()).thenReturn(al);

		mockMvc.perform(get("http://localhost:8080/product/getAll")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(001)))
				.andExpect(jsonPath("$[0].productId", is("mock laptop")))
				.andExpect(jsonPath("$[0].description", is("mock product configuration info")))
				.andExpect(jsonPath("$[0].imageUrl", is("mock://localhost:8080/delImage")))
				.andExpect(jsonPath("$[0].price", is(50000.0))).andExpect(jsonPath("$[0].version", is(5)));
	}

}
