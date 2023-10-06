package com.example.scs.controller;

import com.example.scs.model.*;
import com.example.scs.repos.*;
import com.example.scs.services.SecurityService;
import com.example.scs.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    ApplicationRepository applicationRepository;

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
    VerticalRepository verticalRepository;

    public ApplicationController(final ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("apps", applicationRepository.findAll());
        User user = securityService.findLoggedInUser();
        model.addAttribute("user",user);
        Boolean isAdmin = user.getRole().equals("ADMIN");
        model.addAttribute("isAdmin",isAdmin);
        return "application/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("application") final Application application) {
        return "application/add";
    }

    @PostMapping("/add")
    public String add(@RequestParam Map<String,String> body) {

        Application application = new Application();
        application.setDate_of_application(LocalDate.now().toString());
        application.setStatus(Boolean.FALSE);
//        System.out.println(body.get("vertical"));
        application.setPosition(body.get("position"));
        application.setVertical_id(String.valueOf(verticalRepository.getVertical(body.get("vertical")).getVertical_id()));
        User user = securityService.findLoggedInUser();
        application.setStud_id(user.getStud_id());
        application.setProgram(body.get("program"));
        application.setDepartment(body.get("department"));
        application.setList_of_pors(body.get("list_of_pors"));
        application.setCommitment(Integer.parseInt(body.get("commitment")));
        application.setSkills(body.get("skills"));
        applicationRepository.create(application);

        Notification notification = new Notification();
        notification.setType("application");
        notification.setTime(LocalDateTime.now());
        notification.setUrl_id(String.valueOf(applicationRepository.getLatest().getApplication_id()));
        notificationRepository.create(notification);

        Unread unread = new Unread();
        User admin = userRepository.getAdmin("ADMIN");
        unread.setUser_id(String.valueOf(admin.getUser_id()));
        unread.setNotification_id(notificationRepository.getLatest().getNotification_id());
        unreadRepository.create(unread);

        String last = String.valueOf(applicationRepository.getLatest().getApplication_id());
        return "redirect:/application/"+ last;
    }
    @GetMapping("/{applicationId}")
    public String get(@PathVariable Integer applicationId, final Model model){
        Application application = applicationRepository.get(applicationId);
        model.addAttribute("app",application);
        User user = securityService.findLoggedInUser();
        Vertical vertical = verticalRepository.get(Integer.parseInt(application.getVertical_id()));
        model.addAttribute("vertical",vertical.getName_of_vertical());
        Boolean show = application.getStud_id().equals(user.getStud_id()) | user.getRole().equals("ADMIN");
        Boolean isAdmin = user.getRole().equals("ADMIN");
        model.addAttribute("isAdmin",isAdmin);
        model.addAttribute("show",show);
        if(show){
            return "application/admin_application";
        }
        return "error/403";
    }
    @GetMapping("/edit/{applicationID}")
    public String edit(@PathVariable final Long applicationID, final Model model) {
        model.addAttribute("application", applicationRepository.get(applicationID.intValue()));
        return "application/edit";
    }

    @PostMapping("/edit/{applicationID}")
    public String edit(@PathVariable final Long applicationID,
                       @ModelAttribute("application") @Valid final Application applicationDTO,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "application/edit";
        }
        applicationRepository.update(applicationID.intValue(), applicationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("application.update.success"));
        return "redirect:/application";
    }

    @PostMapping("/delete/{applicationID}")
    public String delete(@PathVariable final Long applicationID,
                         final RedirectAttributes redirectAttributes) {
        applicationRepository.delete(applicationID.intValue());
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("application.delete.success"));
        return "redirect:/application";
    }

}
