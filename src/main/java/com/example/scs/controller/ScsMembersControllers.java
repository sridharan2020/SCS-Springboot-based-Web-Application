package com.example.scs.controller;


import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.example.scs.model.ScsMembers;
import com.example.scs.model.User;
import com.example.scs.repos.ScsMembersRepository;
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
@RequestMapping("/sCSMemberss")
public class ScsMembersControllers {

    @Autowired
    private final ScsMembersRepository scsMembersRepository;
    @Autowired
    SecurityService securityService;
    
//    private final VerticalRepository verticalRepository;
//    private final BillsRepository billsRepository;
//    private final TeammeetingsRepository teammeetingsRepository;
//    private final InductionmentorRepository inductionmentorRepository;

    public ScsMembersControllers(final ScsMembersRepository scsMembersRepository) {
        this.scsMembersRepository = scsMembersRepository;
//        this.verticalRepository = verticalRepository;
//        this.billsRepository = billsRepository;
//        this.teammeetingsRepository = teammeetingsRepository;
//        this.inductionmentorRepository = inductionmentorRepository;
    }

//    @ModelAttribute
//    public void prepareContext(final Model model) {
//        model.addAttribute("verticalmemberbelongsValues", verticalRepository.findAll().stream().collect(
//                Collectors.toMap(Vertical::getVerticalID, Vertical::getNameOfVertical)));
//        model.addAttribute("membersresponsblebillsValues", billsRepository.findAll().stream().collect(
//                Collectors.toMap(Bills::getBillID, Bills::getPurpose)));
//        model.addAttribute("membersattendmeetsValues", teammeetingsRepository.findAll().stream().collect(
//                Collectors.toMap(Teammeetings::getMeetingID, Teammeetings::getKindOfMeeting)));
//        model.addAttribute("memberbecomesIMValues", inductionmentorRepository.findAll().stream().collect(
//                Collectors.toMap(Inductionmentor::getMentorID, Inductionmentor::getMentorID)));
//    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sCSMemberss", scsMembersRepository.findAll());
        User user = securityService.findLoggedInUser();
        Boolean admin = Boolean.FALSE;
        if(user != null)
        {if((Objects.equals(user.getRole(), "ADMIN"))||(Objects.equals(user.getRole(), "SCS_MEMBER"))) {
        	admin=true;
        }}
        model.addAttribute("admin", admin);
        
        return "sCSMembers/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("sCSMembers") final ScsMembers scsMembers) {
        return "sCSMembers/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("sCSMembers") @Valid final ScsMembers scsMembers,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sCSMembers/add";
        }
        scsMembersRepository.create(scsMembers);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sCSMembers.create.success"));
        return "redirect:/sCSMemberss";
    }

    @GetMapping("/edit/{memberId}")
    public String edit(@PathVariable final Integer memberId, final Model model) {
        model.addAttribute("sCSMembers", scsMembersRepository.get(memberId));
        return "sCSMembers/edit";
    }

    @PostMapping("/edit/{memberId}")
    public String edit(@PathVariable final Integer memberId,
                       @ModelAttribute("sCSMembers") @Valid final ScsMembers scsMembers,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sCSMembers/edit";
        }
        scsMembersRepository.update(memberId, scsMembers);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sCSMembers.update.success"));
        return "redirect:/sCSMemberss";
    }

    @PostMapping("/delete/{memberId}")
    public String delete(@PathVariable final Integer memberId,
                         final RedirectAttributes redirectAttributes) {
        final String referencedWarning = scsMembersRepository.getReferencedWarning(memberId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            scsMembersRepository.delete(memberId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sCSMembers.delete.success"));
        }
        return "redirect:/sCSMemberss";
    }

}
