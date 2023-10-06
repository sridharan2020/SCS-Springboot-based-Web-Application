package com.example.scs.controller;

import java.util.Objects;

import javax.validation.Valid;

import com.example.scs.model.InductionProgram;
import com.example.scs.model.User;
import com.example.scs.repos.InductionProgramRepository;
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
@RequestMapping("/inductionPrograms")
public class InductionProgramController {

    @Autowired
    InductionProgramRepository inductionProgramRepository;
    @Autowired
    SecurityService securityService;
  


    @GetMapping
    public String list(final Model model) {
        model.addAttribute("inductionPrograms", inductionProgramRepository.findAll());
        User user = securityService.findLoggedInUser();
        model.addAttribute("current_user", user);
        Boolean admin = Boolean.FALSE;
        if(user != null)
        {if((Objects.equals(user.getRole(), "ADMIN"))) {
        	admin=true;
        }}
        return "inductionProgram/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("inductionProgram") final InductionProgram inductionProgram) {
        return "inductionProgram/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("inductionProgram") @Valid final InductionProgram inductionProgram,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inductionProgram/add";
        }
        inductionProgramRepository.create(inductionProgram);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inductionProgram.create.success"));
        return "redirect:/inductionPrograms";
    }

    @GetMapping("/edit/{inductionId}")
    public String edit(@PathVariable final Integer inductionId, final Model model) {
        model.addAttribute("inductionProgram", inductionProgramRepository.get(inductionId));
        return "inductionProgram/edit";
    }

    @PostMapping("/edit/{inductionId}")
    public String edit(@PathVariable final Integer inductionId,
            @ModelAttribute("inductionProgram") @Valid final InductionProgram inductionProgram,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inductionProgram/edit";
        }
        inductionProgramRepository.update(inductionId, inductionProgram);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inductionProgram.update.success"));
        return "redirect:/inductionPrograms";
    }

    @PostMapping("/delete/{inductionId}")
    public String delete(@PathVariable final Integer inductionId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = inductionProgramRepository.getReferencedWarning(inductionId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            inductionProgramRepository.delete(inductionId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("inductionProgram.delete.success"));
        }
        return "redirect:/inductionPrograms";
    }
}
