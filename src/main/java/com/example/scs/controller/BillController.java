package com.example.scs.controller;
//import com.example.scs.repos.BillRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
////import com.example.scs.controller
//@Controller
//public class BillController {
//    @Autowired
//    BillRepository billRepository;
//
//}

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import javax.management.NotificationFilter;
import javax.validation.Valid;

import com.example.scs.model.*;
import com.example.scs.repos.*;
import com.example.scs.services.SecurityService;
import com.example.scs.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.relational.core.sql.Not;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/bills")
public class BillController {

    @Autowired
    BillRepository billRepository;

    @Autowired
    SecurityService securityService;


    @Autowired
    StudentRepository studentRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UnreadRepository unreadRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScsMembersRepository scsMembersRepository;

    @Autowired
    VerticalRepository verticalRepository;

    public BillController(final BillRepository billRepository) {
        this.billRepository = billRepository;
    }


    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bills", billRepository.findAll());
        User user = securityService.findLoggedInUser();
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));
        Integer member_id = student.getScsmembership_id();
        model.addAttribute("member_id",member_id);
        Boolean isAdmin = user.getRole().equals("ADMIN");
        model.addAttribute("isAdmin",isAdmin);
        return "bills/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bills") final Bill bill) {
        return "bills/add";
    }

    @PostMapping("/add")
    public String add(@RequestParam Map<String,String>body) {

        Bill bill = new Bill();
        bill.setTitle(body.get("title"));
        bill.setPurpose(body.get("purpose"));
        bill.setAmount(Integer.parseInt(body.get("amount")));
        bill.setDate_of_billing(LocalDate.now().toString());
        bill.setStatus(Boolean.FALSE);
        User user = securityService.findLoggedInUser();
        bill.setMember_id(studentRepository.get(Integer.parseInt(user.getStud_id())).getScsmembership_id().toString());
        billRepository.create(bill);


        Notification notification = new Notification();
        notification.setType("bills");
        notification.setTime(LocalDateTime.now());
        notification.setUrl_id(String.valueOf(billRepository.getLatest().getBillid()));
        notificationRepository.create(notification);

        Unread unread = new Unread();
        User admin = userRepository.getAdmin("ADMIN");
        unread.setUser_id(String.valueOf(admin.getUser_id()));
        unread.setNotification_id(notificationRepository.getLatest().getNotification_id());
        unreadRepository.create(unread);
        return "redirect:/bills";
    }
    @GetMapping("/{billId}")
    public String get(@PathVariable Integer billId, final Model model){
        model.addAttribute("bill",billRepository.get(billId));
        User user = securityService.findLoggedInUser();
        Bill bill = billRepository.get(billId);
//        Integer verticalId = scsMembersRepository.get(Integer.parseInt(bill.getMember_id())).getVerticalmemberbelongs();
//        model.addAttribute("vertical",vertical);
        Integer verticalId = Integer.parseInt(scsMembersRepository.get(Integer.parseInt(bill.getMember_id())).getVerticalmemberbelongs());
        Vertical vertical = verticalRepository.get(verticalId);
        model.addAttribute("vertical",vertical.getName_of_vertical());
        if(user.getRole().equals("SCS_MEMBER")){
            Student student = studentRepository.getScsMembers(Integer.parseInt(bill.getMember_id()));
            model.addAttribute("madeby",student.getName());
            return "bills/member_bills";
        }
        else if(user.getRole().equals("ADMIN")){
            return "bills/admin_bills";
        }
        return "jiangly";
    }



    @GetMapping("/edit/{billID}")
    public String edit(@PathVariable final Integer billID, final Model model) {
        model.addAttribute("bills", billRepository.get(billID));
        User user = securityService.findLoggedInUser();
        Boolean isAdmin = user.getRole().equals("ADMIN");
        model.addAttribute("isAdmin",isAdmin);
        return "bills/edit";
    }

    @PostMapping("/edit/{billID}")
    public String edit(@PathVariable final Integer billID,
                       @ModelAttribute("bills") @Valid final Bill bill,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bills/edit";
        }
        billRepository.update(billID, bill);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bills.update.success"));
        return "redirect:/bills";
    }

    @PostMapping("/delete/{billID}")
    public String delete(@PathVariable final Integer billID,
                         final RedirectAttributes redirectAttributes) {
        final String referencedWarning = billRepository.getReferencedWarning(billID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            billRepository.delete(billID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bills.delete.success"));
        }
        return "redirect:/bills";
    }

}
