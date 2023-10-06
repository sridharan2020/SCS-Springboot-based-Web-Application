package com.example.scs.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.example.scs.model.Faculty;
import com.example.scs.model.User;
import com.example.scs.repos.FacultyRepository;
import com.example.scs.repos.UserRepository;
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
@RequestMapping("/facultys")
public class FacultyController {

    @Autowired
    private final FacultyRepository facultyRepository;
    @Autowired
    SecurityService securityService;
    @Autowired
    UserRepository userRepository;
   
//    private final VerticalRepository verticalRepository;

    public FacultyController(final FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
//        this.verticalRepository = verticalRepository;
    }

//    @ModelAttribute
//    public void prepareContext(final Model model) {
//        model.addAttribute("facultycounselsverticalssValues", verticalRepository.findAll().stream().collect(
//                Collectors.toMap(Vertical::getVerticalID, Vertical::getNameOfVertical)));
//    }

    @GetMapping
    public String list(final Model model) {
        List<Faculty> facs = facultyRepository.findAll();
        for(Faculty faculty : facs)
        {
            faculty.setEmail(userRepository.getFacultyEmail(faculty.getFacultyId()));
        }
        model.addAttribute("facultys", facs);
        
        User user = securityService.findLoggedInUser();
        boolean admin =Boolean.FALSE;
        if(user != null)
        {if((Objects.equals(user.getRole(), "ADMIN"))||(Objects.equals(user.getRole(), "SCS_MEMBER"))) {
        	admin=true;
        }}
        model.addAttribute("admin", admin);

        return "faculty/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("faculty") final Faculty faculty) {
        return "faculty/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("faculty") @Valid final Faculty faculty,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "faculty/add";
        }
        facultyRepository.create(faculty);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("faculty.create.success"));
        return "redirect:/facultys";
    }

    @GetMapping("/edit/{facultyId}")
    public String edit(@PathVariable final Integer facultyId, final Model model) {
        model.addAttribute("faculty", facultyRepository.get(facultyId));
        return "faculty/edit";
    }

    @PostMapping("/edit/{facultyId}")
    public String edit(@PathVariable final Integer facultyId,
                       @ModelAttribute("faculty") @Valid final Faculty faculty,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "faculty/edit";
        }
        facultyRepository.update(facultyId, faculty);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("faculty.update.success"));
        return "redirect:/facultys";
    }

    @PostMapping("/delete/{facultyId}")
    public String delete(@PathVariable final Integer facultyId,
                         final RedirectAttributes redirectAttributes) {
        final String referencedWarning = facultyRepository.getReferencedWarning(facultyId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            facultyRepository.delete(facultyId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("faculty.delete.success"));
        }
        return "redirect:/facultys";
    }

}