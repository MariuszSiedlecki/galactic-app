package com.galacticapp.repositories;


import com.galacticapp.models.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlanetRepository extends JpaRepository<Planet, Long> {

    @Query("select p from Planet p where p.planetName = ?1 ")
    Planet findPlanetByName(String planetName);

    @Query("select p from Planet p where " +
            "p.planetName  like %?1" +
            " or " +
            "p.planetInfo like %?1" +
            " or " +
            "p.planetType like %?1")
    List<Planet> findPlanetByParam(String param);

    @Transactional
    @Modifying
    @Query("delete from Planet p where p.planetName = ?1")
    int deletePlanetByName(String planetName);
}
