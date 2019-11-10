package com.galacticapp.services;

import com.galacticapp.commons.extras.CreatorXLS;
import com.galacticapp.commons.mappers.PlanetMapper;
import com.galacticapp.models.Planet;
import com.galacticapp.models.PlanetDto;
import com.galacticapp.repositories.PlanetRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanetService {
    private PlanetRepository planetRepository;
    private PlanetMapper planetMapper;

    public PlanetService(PlanetRepository planetRepository, PlanetMapper planetMapper) {
        this.planetRepository = planetRepository;
        this.planetMapper = planetMapper;
//        mockPlanets();
    }

//    private void mockPlanets() {
//        planetRepository.save(Planet
//                .builder()
//                .planetName("Mars")
//                .distanceFromSun(4642398934L)
//                .lengthOfYear(450)
//                .oneWayLightTimeToTheSun(4.6)
//                .planetImage("image")
//                .planetInfo("mars planet")
//                .planetType("terrain")
//                .build()
//        );
//    }


    public Planet getPlanetByName(String planetName) {
        return Optional
                .ofNullable(planetRepository.findPlanetByName(planetName))
                .map(this::planetNameToUpperCase)
                .orElse(null);
    }


    public List<Planet> getPlanets() {
        return planetRepository.findAll();
    }

    public List<PlanetDto> getPlanetsDto() {
        return planetRepository
                .findAll()
                .stream()
                .map(planetMapper::map)
                .collect(Collectors.toList());
    }

    public List<Planet> getPlanetsInFile(String param) {
        return planetRepository.findPlanetByParam(param);
    }

    public Planet savePlanet(Planet planet) {
        return planetRepository.save(planet);
    }

    public Planet updatePlanet(String planetName, Planet planet) {
        return Optional.ofNullable(planetRepository.findPlanetByName(planetName))
                .map(p -> {
                    p.setPlanetName(planet.getPlanetName());
                    p.setPlanetType(planet.getPlanetType());
                    p.setPlanetInfo(planet.getPlanetInfo());
                    p.setPlanetImage(planet.getPlanetImage());
                    p.setOneWayLightTimeToTheSun(planet.getOneWayLightTimeToTheSun());
                    p.setLengthOfYear(planet.getLengthOfYear());
                    p.setDistanceFromSun(planet.getDistanceFromSun());
                    return planetRepository.save(p);
                })
                .map(this::planetNameToUpperCase)
                .orElse(null);

    }


    public void updatePlanetVoid(String planetName, Planet planet) {
        Optional.ofNullable(planetRepository.findPlanetByName(planetName))
                .ifPresent(p -> {
                    p.setPlanetName(planet.getPlanetName());
                    p.setPlanetType(planet.getPlanetType());
                    p.setPlanetInfo(planet.getPlanetInfo());
                    p.setPlanetImage(planet.getPlanetImage());
                    p.setOneWayLightTimeToTheSun(planet.getOneWayLightTimeToTheSun());
                    p.setLengthOfYear(planet.getLengthOfYear());
                    p.setDistanceFromSun(planet.getDistanceFromSun());

                    planetRepository.save(p);
                });

    }

    public boolean deletePlanetByName(String planetName) {
        if (planetRepository.deletePlanetByName(planetName) == 1) {
            return true;

        }
        return false;
    }

    private Planet planetNameToUpperCase(Planet p) {
        p.setPlanetName(p.getPlanetName().toUpperCase());
        return p;
    }

    private Planet updatePlanetResult(Planet p) {
        return Planet
                .builder()
                .id(p.getId())
                .planetName(p.getPlanetName())
                .planetType(p.getPlanetType())
                .planetInfo(p.getPlanetInfo())
                .planetImage(p.getPlanetImage())
                .oneWayLightTimeToTheSun(p.getOneWayLightTimeToTheSun())
                .lengthOfYear(p.getLengthOfYear())
                .distanceFromSun(p.getDistanceFromSun())
                .build();
    }

    public void getFile(String filename) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        CreatorXLS<Planet> planetFile = new CreatorXLS<>(Planet.class);
        planetFile.createFile(filename, getPlanets());
    }
}
