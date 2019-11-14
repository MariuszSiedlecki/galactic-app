package com.galacticapp.controllers;

import com.galacticapp.commons.mappers.PlanetMapper;
import com.galacticapp.models.Planet;
import com.galacticapp.models.PlanetDto;
import com.galacticapp.services.PlanetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Controller
public class HomeController {

    private PlanetService planetService;
    private PlanetMapper planetMapper;

    public HomeController(PlanetService planetService, PlanetMapper planetMapper) {
        this.planetService = planetService;
        this.planetMapper = planetMapper;

    }

    @PreAuthorize("hasAnyRole('ADMIN','USER', 'GUEST')")
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("planets", planetService.getPlanetsDto());
        return "index";
    }

    /**
     * służy do wywołania strony add-planet
     */
    @GetMapping("/add-page")
    public String addPage(Model model) {
        return "add-planet";
    }

    @GetMapping("/delete")
    public String deletePlanet(@RequestParam(value = "planet") String planetName) {
        planetService.deletePlanetByName(planetName);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updatePlanet(@RequestBody() PlanetDto planetName) {
        return "redirect:/";
    }

    @PostMapping("/add")
    public String addPlanet(@ModelAttribute() PlanetDto planetDto) {
        System.out.println(planetDto);
        planetService.savePlanet(planetMapper.reverseMap(planetDto));
        return "redirect:/";
    }

    @GetMapping("/file/xls")
    public String getFileXls() throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException {
        planetService.getFile("planets");
        return "redirect:/";
    }
}
