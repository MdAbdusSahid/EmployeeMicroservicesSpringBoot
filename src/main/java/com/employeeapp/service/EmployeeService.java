package com.employeeapp.service;

import com.employeeapp.entity.Employee;
import com.employeeapp.repository.EmployeeRepo;
import com.employeeapp.response.AddressResponse;
import com.employeeapp.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Service
public class EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

//    @Value("${address-service.base.url}")
//   private String addressBaseURL;

//    public EmployeeService(@Value("${address-service.base.url}") String addressBaseURL,
//                           RestTemplateBuilder restTemplateBuilder){
//        System.out.println(addressBaseURL);
//        this.restTemplate = restTemplateBuilder.rootUri(addressBaseURL).build();
//    }


    public EmployeeResponse getEmployeeById(int id) {
        logger.info("Find By Id method invoked");
        try {
            Employee employee= employeeRepo.findById(id).get();
            EmployeeResponse employeeResponse =modelMapper.map(employee, EmployeeResponse.class);
            RestTemplate restTemplate = new RestTemplate();
            //restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:1998/address-app/api"));
            AddressResponse addressResponse= webClient
                    .get()
                    .uri("/address/"+id)
                    .retrieve()
                    .bodyToMono(AddressResponse.class)
                    .block();
            employeeResponse.setAddressResponse(addressResponse);
            return employeeResponse;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

//    private static AddressResponse getForObject(int id, RestTemplate restTemplate) {
//        return restTemplate.getForObject("/address/{id}", AddressResponse.class, id);
//    }
}
