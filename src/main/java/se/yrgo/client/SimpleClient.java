package se.yrgo.client;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.calls.CallHandlingServiceImpl;
import se.yrgo.services.customers.CustomerManagementMockImpl;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

public class SimpleClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new
                ClassPathXmlApplicationContext("application.xml");

        CustomerManagementService customerService = container.getBean("customerManagementService", CustomerManagementService.class);


        // part one
        System.out.println("Part 1");
        System.out.println("Searching for customers by keyword 'ltd':");
        List<Customer> customers = customerService.findCustomersByName("ltd");
        for (Customer customer : customers) {
            System.out.println(customer);
        }


        // part two
        System.out.println("\nPart 2");
        CallHandlingServiceImpl callHandlingService = container.getBean("callHandlingService", CallHandlingServiceImpl.class);
        DiaryManagementService diaryService = container.getBean("diaryManagementService", DiaryManagementService.class);

        customerService.newCustomer(new Customer("BV48192", "Bosses VVS", "Best VVS in town"));

        Call newCall = new Call("Bosse vill veta saker");
        Action action1 = new Action("Kollade med Bossse vad han ville", new GregorianCalendar(2025, 5, 21), "Mattias D");
        Action action2 = new Action("Kollade hur det var med Bosses katt", new GregorianCalendar(2025, 6, 22), "Mattias D");


        List<Action> actions = new ArrayList<Action>();
        actions.add(action1);
        actions.add(action2);

        try{
            callHandlingService.recordCall("BV48192", newCall, actions);
        }catch (CustomerNotFoundException e){
            System.out.println("That customer doesn't exist");
        }

        System.out.println("Here are the outstanding actions:");
        Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("Mattias D");
        for (Action next: incompleteActions){
            System.out.println(next);
        }

        container.close();
    }
}