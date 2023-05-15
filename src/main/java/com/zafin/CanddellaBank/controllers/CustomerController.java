package com.zafin.CanddellaBank.controllers;

import com.zafin.CanddellaBank.entities.Account;
import com.zafin.CanddellaBank.entities.Customer;
import com.zafin.CanddellaBank.entities.Product;
import com.zafin.CanddellaBank.repository.AccountRepository;
import com.zafin.CanddellaBank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/customerSignup")
    public String CustomerSignup(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("accounts", accountRepository.findAll());
        return "customer/customerSignup";
    }

    @RequestMapping(value = "registerCustomer", method = RequestMethod.POST)
    public String registerCustomer(@ModelAttribute("customer") Customer customer, @RequestParam("accounts") Account account){
        customer.setAccountNumber(account.getAccountNumber());
        customerRepository.save(customer);
        return "redirect:adminControl";
    }

    @RequestMapping("/viewCustomers")
    public String viewCustomers(Model theModel){
        List<Customer> customerList = customerRepository.findAll();
        theModel.addAttribute("customerList",customerList);
        return "customer/viewcustomer";
    }
}
