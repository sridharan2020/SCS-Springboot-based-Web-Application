package com.example.scs.controller;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.example.scs.model.InductionProgram;
import com.example.scs.model.Inductionmentor;
import com.example.scs.model.ScsMembers;
import com.example.scs.model.Student;
import com.example.scs.model.User;
import com.example.scs.repos.IMGroupsRepository;
import com.example.scs.repos.InductionProgramRepository;
import com.example.scs.repos.InductionmentorRepository;
import com.example.scs.repos.ScsMembersRepository;
import com.example.scs.repos.StudentRepository;
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


@Controller
@RequestMapping("/inductionmentors")
public class InductionmentorController {

    @Autowired
    InductionmentorRepository inductionmentorRepository;
    @Autowired
    InductionProgramRepository inductionProgramRepository;
    @Autowired
    ScsMembersRepository scsMembersRepository;
    @Autowired
    IMGroupsRepository iMGroupsRepository;
    @Autowired
    SecurityService securityService;
    @Autowired
    StudentRepository studentRepository;


//    @ModelAttribute
//    public void prepareContext(final Model model) {
//        model.addAttribute("iMmentoredbyNMValues", inductionmentorRepository.findAll().stream().collect(
//                Collectors.toMap(Inductionmentor::getMentorID, Inductionmentor::getMentorID)));
//        model.addAttribute("iMpartofInductionValues", inductionProgramRepository.findAll().stream().collect(
//                Collectors.toMap(InductionProgram::getInductionId, InductionProgram::getDuration)));
//        model.addAttribute("iMGrpcontainsIMValues", iMGroupsRepository.findAll().stream().collect(
//                Collectors.toMap(IMGroups::getGroupID, IMGroups::getGroupID)));
//    }

    @GetMapping
    public String list(final Model model) {
        User user = securityService.findLoggedInUser();
        model.addAttribute("current_user", user);

        Boolean flag = Boolean.FALSE;
        if(user != null)
        {
            if(Objects.equals(user.getRole(), "SCS_MEMBER"))
            {
                int stud_id = Integer.parseInt(user.getStud_id());
                Student stud = studentRepository.get(stud_id);

                int memb_id = stud.getScsmembership_id();
                String year = String.valueOf(Year.now().getValue());
                Inductionmentor inductionmentor = inductionmentorRepository.getByMemberIDYearSimple(memb_id, year);
                if(inductionmentor == null)
                {
                    flag = Boolean.TRUE;
                    model.addAttribute("member_id", memb_id);
                }
            }
        }
        Boolean admin = Boolean.FALSE;
        if(user != null)
        {if((Objects.equals(user.getRole(), "ADMIN"))) {
        	admin=true;
        }}
        model.addAttribute("admin", admin);
        model.addAttribute("flag", flag);
        model.addAttribute("inductionmentors", inductionmentorRepository.findAllSimple());
        return "inductionmentor/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("inductionmentor") final Inductionmentor inductionmentor,Model model) {
    	List <Integer> yearValues =new ArrayList<>() ;
        for(InductionProgram ip1 : inductionProgramRepository.findAll())
        {
            yearValues.add(ip1.getYear());
        }
        model.addAttribute("yearValues", yearValues);
        
        List <Integer> smembers =new ArrayList<>() ;
        for(ScsMembers s1 : scsMembersRepository.findAll())
        {
        	smembers.add(s1.getMemberId());
        }
        model.addAttribute("smembers", smembers);

        return "inductionmentor/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("inductionmentor") @Valid final Inductionmentor inductionmentor,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inductionmentor/add";
        }
        inductionmentorRepository.create(inductionmentor);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inductionmentor.create.success"));
        return "redirect:/inductionmentors";
    }

    @GetMapping("/edit/{mentorID}")
    public String edit(@PathVariable final Integer mentorID, final Model model) {

        model.addAttribute("inductionmentor", inductionmentorRepository.getSimple(mentorID));
        System.out.println(inductionmentorRepository.getSimple(mentorID).toString());
        return "inductionmentor/edit";
    }

    @PostMapping("/edit/{mentorID}")
    public String edit(@PathVariable final Integer mentorID,
            @ModelAttribute("inductionmentor") @Valid final Inductionmentor inductionmentor,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inductionmentor/edit";
        }
        inductionmentorRepository.update(mentorID, inductionmentor);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inductionmentor.update.success"));
        return "redirect:/inductionmentors";
    }

    @PostMapping("/delete/{mentorID}")
    public String delete(@PathVariable final Integer mentorID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = inductionmentorRepository.getReferencedWarning(mentorID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            inductionmentorRepository.delete(mentorID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("inductionmentor.delete.success"));
        }
        return "redirect:/inductionmentors";
    }

    @PostMapping("/join/{membID}")
    public String join(@PathVariable String membID)
    {
        Inductionmentor inductionmentor = new Inductionmentor();
        String year = String.valueOf(Year.now().getValue());

        inductionmentor.setYear(year);
        inductionmentor.setMember_id(membID);

        inductionmentorRepository.create(inductionmentor);
        return "redirect:/inductionmentors";
    }
}