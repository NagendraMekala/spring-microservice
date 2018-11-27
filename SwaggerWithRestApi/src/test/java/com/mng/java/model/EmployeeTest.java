package com.mng.java.model;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import com.mng.java.model.Employee;

public class EmployeeTest {
	
	@Before
	public void setUp() {
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}
	
	@Test
	public void testSetmethod() {
		
		Employee employee = mock(Employee.class);
		
		
		doAnswer((i) -> {
			System.out.println("Employee set name argumemts :"+ i.getArgument(0));
			assertTrue("nag".equalsIgnoreCase(i.getArgument(0)));
			return null;
			
		}).when(employee).setName("nag");
		
		doNothing().when(employee).setName("nag");
		
		
	}
	

}
