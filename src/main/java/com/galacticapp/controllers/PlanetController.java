package com.galacticapp.controllers;

import com.galacticapp.commons.extras.DirectoryCreator;
import com.galacticapp.models.Planet;
import com.galacticapp.models.PlanetDto;
import com.galacticapp.services.PlanetService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.List;

import static com.galacticapp.commons.Naming.LOCAL_PATH;

@CrossOrigin
@RestController
@RequestMapping("/")
public class PlanetController {

    private PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
        DirectoryCreator.createDirectory();
    }

    @GetMapping("/api/v1/planet")
    public ResponseEntity<Planet> getPlanetByName(@RequestParam(value = "name") String planetName) {
        Planet result = planetService.getPlanetByName(planetName);
        if (result != null) {
            return new ResponseEntity<>(planetService.getPlanetByName(planetName), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/api/v1/planets", produces = "application/json")
    public List<Planet> getPlanets() {
        return planetService.getPlanets();
    }

    /**
     * generowanie pliku xls
     **/
    @GetMapping(value = "/api/v1/planets/file")
    public void getPlanetsInFile(@RequestParam String filename) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        planetService.getFile(filename);
    }

    @GetMapping(value = "/api/v1/planets/dto", produces = "application/json")
    public List<PlanetDto> getPlanetsDto() {
        return planetService.getPlanetsDto();
    }

    @GetMapping(value = "/api/v1/planets/dto/xml", produces = "application/xml")
    public List<PlanetDto> getPlanetsDtoXml() {
        return planetService.getPlanetsDto();
    }

    @PostMapping(value = "/api/v1/planet", produces = "application/json")
    public ResponseEntity<Planet> addPlanet(@RequestBody Planet planet) {
        return ResponseEntity
                .ok()
                .header("example_header", "example_header_1")
                .body(planetService.savePlanet(planet));
    }

    @PutMapping(value = "/api/v1/planet", produces = "application/json")
    public ResponseEntity<Planet> updatePlanet(@RequestParam(value = "name") String planetName, @RequestBody Planet planet) {
        Planet result = planetService.updatePlanet(planetName, planet);
        if (result != null) {
            return ResponseEntity
                    .ok()
                    .header("Example_header", "example_header_1")
                    .body(result);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "api/v1/planet", produces = "application/json")
    public ResponseEntity<?> deletePlanetByName(@RequestParam(value = "name") String planetName) {
        if (planetService.deletePlanetByName(planetName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/v1/planets/download/file/xls/{filename}")
    public ResponseEntity<Resource> downloadXls(@PathVariable String filename) throws IOException {
        Resource resource = new UrlResource(Paths.get(LOCAL_PATH + filename).toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFile().getName() + "\"")
                .contentLength(resource.getFile().length())
                .body(resource);

    }
}
