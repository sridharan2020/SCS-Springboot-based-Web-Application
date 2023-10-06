package com.example.scs.controller;

import com.example.scs.model.*;
import com.example.scs.repos.*;
import com.example.scs.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class ApproveController {
    @Autowired
    BillRepository billRepository;

    @Autowired
    MonthlyReportsRepository monthlyReportsRepository;

    @Autowired
    ScsMembersRepository scsMembersRepository;

    @Autowired
    VerticalRepository verticalRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UnreadRepository unreadRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/bills/approve")
    public String approve_bill(@RequestParam Map<String,String> body){
        Integer billId = Integer.parseInt(body.get("billId"));
        Bill bill = billRepository.get(billId);
        bill.setStatus(Boolean.TRUE);
        billRepository.update(billId,bill);

        Notification notification = new Notification();
        notification.setTime(LocalDateTime.now());
        notification.setType("bills");
        notification.setUrl_id(body.get("billId"));
        notificationRepository.create(notification);

        Unread unread = new Unread();
        Integer member_id = Integer.parseInt(bill.getMember_id());
        User user = scsMembersRepository.getScsUserId(member_id);
        unread.setUser_id(user.getUser_id().toString());
        unread.setNotification_id(notificationRepository.getLatest().getNotification_id());
        unreadRepository.create(unread);
        return "success";
    }
    @PostMapping("/monthlyReports/approve")
    public String approve_report(@RequestParam Map<String,String>body){
        Integer report_id = Integer.parseInt(body.get("report_id"));
        MonthlyReports monthlyReports = monthlyReportsRepository.get(report_id);
        monthlyReports.setStatus(Boolean.TRUE);
        monthlyReportsRepository.update(report_id,monthlyReports);

        Notification notification = new Notification();
        notification.setTime(LocalDateTime.now());
        notification.setType("monthlyReports");
        notification.setUrl_id(body.get("report_id"));
        notificationRepository.create(notification);

        Unread unread = new Unread();
        Integer member_id = Integer.parseInt(monthlyReports.getStudent_compiles_reports_id());
        User user = scsMembersRepository.getScsUserId(member_id);
        unread.setUser_id(user.getUser_id().toString());
        unread.setNotification_id(notificationRepository.getLatest().getNotification_id());
        unreadRepository.create(unread);
        return "success";
    }

    @PostMapping("/application/approve")
    public String approve_application(@RequestParam Map<String,String>body){
        Application application = applicationRepository.get(Integer.parseInt(body.get("applicationId")));
        application.setStatus(Boolean.TRUE);
        applicationRepository.update(application.getApplication_id().intValue(),application);

        ScsMembers scsMembers = new ScsMembers();
        scsMembers.setFromDate(LocalDate.now().toString());
        scsMembers.setVerticalmemberbelongs(application.getVertical_id());
        scsMembers.setCurrentPosition(application.getPosition());
        scsMembersRepository.create(scsMembers);

        Student student = studentRepository.get(Integer.parseInt(application.getStud_id()));
        student.setProgram(application.getProgram());
        student.setDepartment(application.getDepartment());
        student.setScsmembership_id(scsMembersRepository.getLatest().getMemberId());
        studentRepository.update(student.getStudentRollNo(),student);

        User user = userRepository.findByRollno(student.getStudentRollNo());
        user.setRole("SCS_MEMBER");
        userRepository.update2(user.getUser_id().intValue(),user);

        Notification notification = new Notification();
        notification.setType("application");
        notification.setTime(LocalDateTime.now());
        notification.setUrl_id(String.valueOf(application.getApplication_id()));
        notificationRepository.create(notification);

        Unread unread = new Unread();
        unread.setUser_id(String.valueOf(userRepository.findByRollno(student.getStudentRollNo()).getUser_id()));
        unread.setNotification_id(notificationRepository.getLatest().getNotification_id());
        unreadRepository.create(unread);


//        Notification notification_2 = new Notification();
//        notification_2.setTime(LocalDateTime.now());
//        notification_2.setType("application");
//        notification_2.setUrl_id(body.get("applicationId"));
//        notificationRepository.create(notification);
//
//        Unread unread_2 = new Unread();
//        User user_2 = userRepository.getByStudent(Integer.parseInt(application.getStud_id()))
//        unread_2.setUser_id(user_2.getUser_id().toString());
//        unread.setNotification_id(notificationRepository.getLatest().getNotification_id());
//        unreadRepository.create(unread);
        return "success";
    }

}
