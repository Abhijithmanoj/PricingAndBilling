package com.zafin.CanddellaBank.controllers;

import com.zafin.CanddellaBank.entities.Account;
import com.zafin.CanddellaBank.entities.Product;
import com.zafin.CanddellaBank.entities.ProductService;
import com.zafin.CanddellaBank.entities.Service;
import com.zafin.CanddellaBank.repository.AccountRepository;
import com.zafin.CanddellaBank.repository.ProductRepository;
import com.zafin.CanddellaBank.repository.ProductServiceRepository;
import com.zafin.CanddellaBank.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceRepository productServiceRepository;



    @RequestMapping("/showProductConfig")
    public String showProductConfig(Model theModel){
        List<Service> serviceList = serviceRepository.findAll();
        theModel.addAttribute("serviceList",serviceList);
        return "product/createproduct";
    }

//    @RequestMapping(value = "/createproductdetails" ,method = RequestMethod.POST)
//    public String showRatePage(@ModelAttribute("product") Product product){
//        productRepository.save(product);
//        return "admin/admindashboard";
//    }

    @RequestMapping(value = "/createproductdetails" ,method = RequestMethod.POST)
    public String showRatePage(@ModelAttribute("product") Product product, @RequestParam Map<String,String> requestParams , Model theModel){
        ProductService productService = new ProductService();
        Set<Service> serviceLists = new HashSet<>();
        List<Service> serviceList = serviceRepository.findAll();
        for(Service service:serviceList){
            if(requestParams.get(service.getServiceCode())!=null){
                serviceLists.add(service);
            }
        }
        //System.out.println(serviceList.size());
        //System.out.println(serviceLists.size());
        product.setServices(serviceLists);
        productRepository.save(product);
        List<ProductService> productServiceList = new ArrayList<>();
        for(Service service:serviceLists)
            {

                productService.setProduct(product);
                productService.setService(service);
               // System.out.println(service.getServiceCode());
                productServiceList.add(productService);

            }
        productServiceRepository.saveAll(productServiceList);


        return "redirect:adminControl";
    }

    @RequestMapping("/viewProducts")
    public String viewProducts(Model theModel){
        List<Product> productList = productRepository.findAll();
        theModel.addAttribute("productList",productList);
        return "product/viewproduct";
    }



}
