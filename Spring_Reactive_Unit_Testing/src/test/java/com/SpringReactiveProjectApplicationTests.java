package com;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.vaccine.Vaccine;
import com.vaccine.Vaccine_Controller;
import com.vaccine.Vaccine_Provider;
import com.vaccine.Vaccine_Service;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class SpringReactiveProjectApplicationTests {
	
	@MockBean
	Vaccine_Service service;  //  service is a mocked out guy..
	
//===============================================================================================================
	
	//  Unit Testing in Vaccine_Provider class:
	
	@Autowired
	Vaccine_Provider provider;
	
	@Test
	void test_VaccineProvider_reactive() {
		when(service.get_Vaccines())
				.thenReturn(Flux.just(new Vaccine("Pfizer"), new Vaccine("J&J"), new Vaccine("Covaxin")));
		StepVerifier.create(provider.providerVaccines())
				.expectSubscription()
				.expectNext(new Vaccine("Pfizer"))
				.expectNext(new Vaccine("J&J"))
				.expectNext(new Vaccine("Covaxin"))   
				.expectComplete()
				.verify();
		
		// Mockito Verify | Optional
		verify(service).get_Vaccines();
	}
	
//================================================================================================================
	
	//  Unit Testing in Vaccine_Controller class:
	
	@Autowired
	Vaccine_Controller controller;
	
	@Test
	void test_Vaccine_Controller_getVaccine() {
		when(service.get_Vaccines())
				.thenReturn(Flux.just(new Vaccine("Pfizer"), new Vaccine("J&J"), new Vaccine("Covaxin")));
		StepVerifier.create(controller.get_Vaccines())
				.expectNextCount(3)
				.expectComplete()
				.verify();
		
		// Mockito Verify | Optional
		verify(service).get_Vaccines();
	}

	
//================================================================================================================	
//================================================================================================================


}
