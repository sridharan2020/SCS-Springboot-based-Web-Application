package com.example.scs.controller;

import java.util.Objects;

import javax.validation.Valid;

import com.example.scs.model.User;
import com.example.scs.model.Venue;
import com.example.scs.repos.VenueRepository;
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
@RequestMapping("/venues")
public class VenueController {

    @Autowired
    VenueRepository venueRepository;
    @Autowired
    SecurityService securityService;
    

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("venues", venueRepository.findAll());
        
        User user = securityService.findLoggedInUser();
        Boolean admin = Boolean.FALSE;
        if(user != null)
        {if((Objects.equals(user.getRole(), "ADMIN"))||(Objects.equals(user.getRole(), "SCS_MEMBER"))) {
        	admin=true;
        }}
        model.addAttribute("admin", admin);
        
        
        return "venue/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("venue") final Venue venue) {
        return "venue/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("venue") @Valid final Venue venue,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "venue/add";
        }
        venueRepository.create(venue);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("venue.create.success"));
        return "redirect:/venues";
    }

    @GetMapping("/edit/{venueID}")
    public String edit(@PathVariable final Integer venueID, final Model model) {
        model.addAttribute("venue", venueRepository.get(venueID));
        return "venue/edit";
    }

    @PostMapping("/edit/{venueID}")
    public String edit(@PathVariable final Integer venueID,
            @ModelAttribute("venue") @Valid final Venue venue,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "venue/edit";
        }
        venueRepository.update(venueID, venue);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("venue.update.success"));
        return "redirect:/venues";
    }

    @PostMapping("/delete/{venueID}")
    public String delete(@PathVariable final Integer venueID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = venueRepository.getReferencedWarning(venueID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            venueRepository.delete(venueID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("venue.delete.success"));
        }
        return "redirect:/venues";
    }

}
