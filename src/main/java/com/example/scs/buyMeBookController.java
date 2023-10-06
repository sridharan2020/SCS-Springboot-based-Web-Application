package com.example.scs;

import com.example.scs.model.MyOrder;
import com.example.scs.model.User;
import com.example.scs.services.SecurityService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.razorpay.*;
import java.util.Map;

@Controller
public class buyMeBookController {

    @Autowired
    SecurityService securityService;

    @GetMapping("/buymebook")
    public String Buy(){
        return "buymebook/buymebook";
    }

    @PostMapping("buymebook/create_order")
    @ResponseBody
    public String createOrder(@RequestBody Map<String,Object> data) throws Exception
    {
//        System.out.println("Hey order function exceuted");
        System.out.println(data);
        int amt=Integer.parseInt(data.get("amount").toString());

        User user=securityService.findLoggedInUser();

        var client=new RazorpayClient("rzp_test_tAe2onrJhwUA4T","wdk605i9BSqQUy2SHLZikJQN");

        JSONObject options = new JSONObject();
        options.put("amount", amt*100);
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        Order order = client.orders.create(options);
        System.out.println(order);

        MyOrder myOrder=new MyOrder();

//        myOrder.setAmount(order.get("amount"));
//        myOrder.setOrderId(order.get("order_id"));
//        myOrder.setPayment(null);
//        myOrder.setStatus("created");


        //if we want we can save this database in our data

        return order.toString();
    }
}
