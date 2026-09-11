package com.rare.locationservice.repository;

import com.rare.locationservice.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City,Long> {
    boolean existsCityByCityCode(String cityCode);
    boolean existsByCityCodeAndIdNot(String cityCode, Long id);

    Page<City> findByCountryCodeIgnoreCase(String countryCode, Pageable pageable);

    @Query("""
   SELECT c 
   FROM City c 
   WHERE lower(c.name) LIKE lower(concat("%",:keyword,"%" ) )
                OR lower(c.cityCode) LIKE lower(concat("%",:keyword,"%" ) )
                OR lower(c.countryCode) LIKE lower(concat("%",:keyword,"%" ) )
                OR lower(c.countryName) LIKE lower(concat("%",:keyword,"%" ) )
                OR lower(c.regionCode) LIKE lower(concat("%",:keyword,"%" ) )
"""
    )
    Page<City> searchByKeyword(String keyword,Pageable pageable);


}
