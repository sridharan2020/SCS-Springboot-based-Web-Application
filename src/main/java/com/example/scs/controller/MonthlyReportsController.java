package com.example.scs.controller;

import com.example.scs.model.*;
import com.example.scs.repos.*;
import com.example.scs.services.SecurityService;
import com.example.scs.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

//public class MontlyReportsController {
//}
@Controller
@RequestMapping("/monthlyReports")
public class MonthlyReportsController {

    @Autowired
    MonthlyReportsRepository monthlyReportsRepository;

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

    public MonthlyReportsController(final MonthlyReportsRepository monthlyReportsRepository) {
        this.monthlyReportsRepository = monthlyReportsRepository;
    }


    @GetMapping
    public String list(final Model model) {
        model.addAttribute("monthlyReports", monthlyReportsRepository.findAll());
        User user = securityService.findLoggedInUser();
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));
        Integer member_id = student.getScsmembership_id();
        model.addAttribute("member_id",member_id);
        Boolean isAdmin = user.getRole().equals("ADMIN");
        model.addAttribute("isAdmin",isAdmin);
        return "monthlyReports/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("monthlyReports") final MonthlyReports monthlyReports) {
        return "monthlyReports/add";
    }

    @PostMapping("/add")
    public String add(@RequestParam Map<String,String> body) {


        User user = securityService.findLoggedInUser();
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));
        MonthlyReports monthlyReports = new MonthlyReports();
        monthlyReports.setCompiled_on(String.valueOf(LocalDate.now()));
        monthlyReports.setTitle(body.get("title"));
        monthlyReports.setFrom_date(body.get("from_date"));
        monthlyReports.setReport_description(body.get("report_description"));
        monthlyReports.setTo_date(body.get("to_date"));
        monthlyReports.setStatus(Boolean.FALSE);
        monthlyReports.setStudent_compiles_reports_id(student.getScsmembership_id().toString());
        monthlyReportsRepository.create(monthlyReports);

        Notification notification = new Notification();
        notification.setType("monthlyReports");
        notification.setTime(LocalDateTime.now());
        notification.setUrl_id(String.valueOf(monthlyReportsRepository.getLatest().getReport_id()));
        notificationRepository.create(notification);

        Unread unread = new Unread();
        User admin = userRepository.getAdmin("ADMIN");
        unread.setUser_id(String.valueOf(admin.getUser_id()));
        unread.setNotification_id(notificationRepository.getLatest().getNotification_id());
        unreadRepository.create(unread);
        return "redirect:/monthlyReports";
    }
    @GetMapping("/{monthlyReportsId}")
    public String get(@PathVariable Integer monthlyReportsId, final Model model){
        model.addAttribute("monthlyReports",monthlyReportsRepository.get(monthlyReportsId));
        MonthlyReports monthlyReports = monthlyReportsRepository.get(monthlyReportsId);
        Student student = studentRepository.getScsMembers(Integer.parseInt(monthlyReports.getStudent_compiles_reports_id()));
        model.addAttribute("compiled_by",student.getName());
        Integer verticalId = Integer.parseInt(scsMembersRepository.get(Integer.parseInt(monthlyReports.getStudent_compiles_reports_id())).getVerticalmemberbelongs());
        Vertical vertical = verticalRepository.get(verticalId);
        model.addAttribute("vertical",vertical.getName_of_vertical());
        User user = securityService.findLoggedInUser();
        if(user.getRole().equals("SCS_MEMBER")){
            return "monthlyReports/monthlyReports_member";
        }
        else if(user.getRole().equals("ADMIN")){
            return "monthlyReports/monthlyReports_admin";
        }
        return "jiangly";
    }



    @GetMapping("/edit/{monthlyReportsID}")
    public String edit(@PathVariable final Integer monthlyReportsID, final Model model) {
        model.addAttribute("monthlyReports", monthlyReportsRepository.get(monthlyReportsID));
        User user = securityService.findLoggedInUser();
        Boolean isAdmin = user.getRole().equals("ADMIN");
        model.addAttribute("isAdmin",isAdmin);
        return "monthlyReports/edit";
    }

    @PostMapping("/edit/{monthlyReportsID}")
    public String edit(@PathVariable final Integer monthlyReportsID,
                       @ModelAttribute("monthlyReports") @Valid final MonthlyReports monthlyReports,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "monthlyReports/edit";
        }
        monthlyReportsRepository.update(monthlyReportsID, monthlyReports);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("monthlyReports.update.success"));
        return "redirect:/monthlyReports";
    }

    @PostMapping("/delete/{monthlyReportsID}")
    public String delete(@PathVariable final Integer monthlyReportsID,
                         final RedirectAttributes redirectAttributes) {
        final String referencedWarning = monthlyReportsRepository.getReferencedWarning(monthlyReportsID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            monthlyReportsRepository.delete(monthlyReportsID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("monthlyReports.delete.success"));
        }
        return "redirect:/monthlyReports";
    }

}
