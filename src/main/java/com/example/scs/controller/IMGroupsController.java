package com.example.scs.controller;

import javax.swing.ListModel;
import javax.validation.Valid;

import com.example.scs.model.*;
import com.example.scs.repos.*;
import com.example.scs.services.SecurityService;
import com.example.scs.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Year;
import java.util.*;


@Controller
@RequestMapping("/iMGroupss")
public class IMGroupsController {
    @Autowired
    IMGroupsRepository imGroupsRepository;
    @Autowired
    InductionProgramRepository inductionProgramRepository;
    @Autowired
    ScsMembersRepository scsMembersRepository;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SecurityService securityService;
    @Autowired
    InductionmentorRepository inductionmentorRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String ind_prog()
    {
        return "redirect:/inductionPrograms";
    }

    @GetMapping("/year/{year}")
    public String list(@PathVariable String year, final Model model) {
        List<IMGroups> all_groups = imGroupsRepository.findAllYear(year);
        Map<Integer, List<Inductionmentor>> group_mentors = new HashMap<>();
        Map<Integer, List<Integer>> group_mentors_ids = new HashMap<>();
        Map<Integer, List<User>> group_users = new HashMap<>();
//        Map<Integer, List<Student>> group_students = new HashMap<>();
        for(IMGroups group : all_groups)
        {
//            String year = String.valueOf(Year.now().getValue());
            List<Inductionmentor> mentors = inductionmentorRepository.getByGrpYear(group.getGroupID(), year);
            group_mentors.put(group.getGroupID(), mentors);
            List<Integer> mentor_ids = new ArrayList<>();
            List<User> users = new ArrayList<>();
            for(Inductionmentor inductionmentor : mentors)
            {
                mentor_ids.add(inductionmentor.getMentorID());

                int memb_id = Integer.parseInt(inductionmentor.getMember_id());
                ScsMembers member = scsMembersRepository.get(memb_id);
                Student student = studentRepository.getScsMembers(member.getMemberId());
                User user = userRepository.getByStudent(student.getStudentRollNo());

                users.add(user);
            }
            group_users.put(group.getGroupID(), users);
            group_mentors_ids.put(group.getGroupID(), mentor_ids);

//            List<Student> studs = studentRepository.getByGrpID(group.getGroupID());
//            group_students.put(group.getGroupID(), studs);
        }
        model.addAttribute("group_users", group_users);
//        model.addAttribute("group_students", group_students);

        User user = securityService.findLoggedInUser();
        model.addAttribute("current_user", user);

        Boolean isMember = Boolean.FALSE;
        Boolean isStudent = Boolean.FALSE;
        int flag1 = 0;
        int flag2 = 0;
        String current_year = String.valueOf(Year.now().getValue());
        if(user != null)
        {
            if((Objects.equals(user.getRole(), "SCS_MEMBER")) & (group_mentors.values().size() < 4))
            {
                isMember = Boolean.TRUE;
                int stud_id = Integer.parseInt(user.getStud_id());
                Student stud = studentRepository.get(stud_id);
                int member_id = stud.getScsmembership_id();
                System.out.println(member_id);
//                String year = String.valueOf(Year.now().getValue());
                Inductionmentor inductionmentor = inductionmentorRepository.getByMemberIDYearSimple(member_id, year);
                if(inductionmentor != null)
                {
                    if((inductionmentor.getIm_grp_id() == null) & (Objects.equals(year, current_year)))
                    {
                        flag1 = 1;
                    }
                    model.addAttribute("current_mentor", inductionmentor);
                }
                else
                {
                    flag1 = 2;
                }
            }
            else if((Objects.equals(user.getRole(), "STUDENT"))  & (group_users.values().size() < 20))
            {
                isStudent = Boolean.TRUE;
                int stud_id = Integer.parseInt(user.getStud_id());
                Student stud = studentRepository.get(stud_id);
                System.out.println(stud.toString());
                if(stud != null)
                {
                    if(stud.getGroup_id() == null)
                    {
                        flag2 = 1;
                    }
//                    else
//                    {
//                        int grpID = stud.getGroup_id();
//                        IMGroups imGroup = imGroupsRepository.get(grpID);
//                        if(!Objects.equals(imGroup.getInduction_year(), year))
//                        {
//                            flag2 = 1;
//                        }
//                    }
                    model.addAttribute("current_student", stud);
                }
                else
                {
                    flag2 = 2;
                }
            }
            else
            {
                System.out.println(user.toString());
                System.out.println(group_users.size());
            }
        }
        else
        {
            System.out.println("User not logged in!!");
        }
        boolean admin =Boolean.FALSE;
        if(user != null)
        {if((Objects.equals(user.getRole(), "ADMIN"))||(Objects.equals(user.getRole(), "SCS_MEMBER"))) {
        	admin=true;
        }}
        model.addAttribute("flag1", flag1);
        model.addAttribute("flag2", flag2);
        model.addAttribute("isMember", isMember);
        model.addAttribute("isStudent", isStudent);
        model.addAttribute("admin", admin);

        model.addAttribute("iMGroupss", all_groups);
        model.addAttribute("group_mentors", group_mentors);
        model.addAttribute("group_mentor_ids", group_mentors_ids);
        return "iMGroups/list";
    }

    @GetMapping("/{groupID}")
    public String group_page(@PathVariable final Integer groupID, final Model model) {
        IMGroups imGroup = imGroupsRepository.get(groupID);
        List<Inductionmentor> mentors = inductionmentorRepository.getByGrpId(groupID);
        List<ScsMembers> members = new ArrayList<>();
        for(Inductionmentor cur_ment : mentors)
        {
            ScsMembers cur_member = scsMembersRepository.get(Integer.parseInt(cur_ment.getMember_id()));
            members.add(cur_member);
        }
        User user = securityService.findLoggedInUser();
        boolean admin =Boolean.FALSE;
        if(user != null)
        {if((Objects.equals(user.getRole(), "ADMIN"))||(Objects.equals(user.getRole(), "SCS_MEMBER"))) {
        	admin=true;
        }}
        model.addAttribute("members", members);
        model.addAttribute("groupID", groupID);
        model.addAttribute("admin", admin);

        List<Student> students = studentRepository.getByGrpID(groupID);
        model.addAttribute("students", students);
        return "iMGroups/group_page";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("iMGroups") final IMGroups iMGroups, Model model) {
    	 List <Integer> yearValues =new ArrayList<>() ;
         for(InductionProgram ip1 : inductionProgramRepository.findAll())
         {
             yearValues.add(ip1.getYear());
         }
         model.addAttribute("yearValues", yearValues);

        return "iMGroups/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("iMGroups") @Valid final IMGroups iMGroups,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "iMGroups/add";
        }
        imGroupsRepository.create(iMGroups);
        String year = iMGroups.getInduction_year();
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("iMGroups.create.success"));
        return "redirect:/iMGroupss/year/" + year;
    }

    @GetMapping("/edit/{groupID}")
    public String edit(@PathVariable final Integer groupID, final Model model) {
        model.addAttribute("iMGroups", imGroupsRepository.get(groupID));
        return "iMGroups/edit";
    }

    @PostMapping("/edit/{groupID}")
    public String edit(@PathVariable final Integer groupID,
            @ModelAttribute("iMGroups") @Valid final IMGroups iMGroups,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "iMGroups/edit";
        }
        imGroupsRepository.update(groupID, iMGroups);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("iMGroups.update.success"));
        return "redirect:/iMGroupss/" + groupID;
    }

    @PostMapping("/delete/{groupID}")
    public String delete(@PathVariable final Integer groupID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = imGroupsRepository.getReferencedWarning(groupID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            imGroupsRepository.delete(groupID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("iMGroups.delete.success"));
        }
        return "redirect:/iMGroupss";
    }

    @PostMapping("/{im_grp_id}/join/{mentor_id}")
    public String join_group(@PathVariable final Integer im_grp_id, @PathVariable final Integer mentor_id,
                           final RedirectAttributes redirectAttributes) {
        System.out.println(">>>>>");
        System.out.println(mentor_id);
        Inductionmentor inductionmentor = inductionmentorRepository.getSimple(mentor_id);
        inductionmentor.setIm_grp_id(String.valueOf(im_grp_id));

        IMGroups imGroup = imGroupsRepository.get(im_grp_id);
        inductionmentor.setImGroup(imGroup);

        inductionmentorRepository.update(mentor_id, inductionmentor);

        return "redirect:/iMGroupss/" + im_grp_id;
    }

    @PostMapping("/{im_grp_id}/join_student/{stud_id}")
    public String join_student(@PathVariable final Integer im_grp_id, @PathVariable final Integer stud_id,
                             final RedirectAttributes redirectAttributes) {
        System.out.println(">>>>>");
        System.out.println(stud_id);

        Student student = studentRepository.get(stud_id);
        student.setGroup_id(im_grp_id);

        studentRepository.update(stud_id, student);

        return "redirect:/iMGroupss/" + im_grp_id;
    }
}
