package com.project.irctc.repository;

import com.project.irctc.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

    @Query(value = """
            SELECT DISTINCT t.*
            FROM train t
            JOIN schedule s ON t.id = s.train_id
            JOIN route r ON s.route_id = r.id
            JOIN station sf ON r.from_station_id = sf.id
            JOIN station st ON r.to_station_id = st.id
            WHERE (:fromStation IS NULL 
                   OR LOWER(REPLACE(sf.name, ' ', '')) LIKE CONCAT('%', :fromStation, '%'))
              AND (:toStation IS NULL 
                   OR LOWER(REPLACE(st.name, ' ', '')) LIKE CONCAT('%', :toStation, '%'))
              AND (:date IS NULL OR s.departure_date = CAST(:date AS DATE))
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<Train> findAllWithFiltersAndSearch(
            @Param("fromStation") String fromStation,
            @Param("toStation") String toStation,
            @Param("date") String date,
            @Param("limit") int limit,
            @Param("offset") int offset);

    @Query(value = """
            SELECT COUNT(DISTINCT t.id)
            FROM train t
            JOIN schedule s ON t.id = s.train_id
            JOIN route r ON s.route_id = r.id
            JOIN station sf ON r.from_station_id = sf.id
            JOIN station st ON r.to_station_id = st.id
            WHERE (:fromStation IS NULL 
                   OR LOWER(REPLACE(sf.name, ' ', '')) LIKE CONCAT('%', :fromStation, '%'))
              AND (:toStation IS NULL 
                   OR LOWER(REPLACE(st.name, ' ', '')) LIKE CONCAT('%', :toStation, '%'))
              AND (:date IS NULL OR s.departure_date = CAST(:date AS DATE))
            """, nativeQuery = true)
    long countAllWithFiltersAndSearch(
            @Param("fromStation") String fromStation,
            @Param("toStation") String toStation,
            @Param("date") String date);
}




//
//@Repository
//public interface TrainRepository extends JpaRepository<Train, Long> {
//
//    // Main query for fetching paginated results using LIMIT and OFFSET
//    @Query(value = """
//            SELECT DISTINCT t.*
//            FROM train t
//            JOIN schedule s ON t.id = s.train_id
//            JOIN route r ON s.route_id = r.id
//            JOIN station sf ON r.from_station_id = sf.id
//            JOIN station st ON r.to_station_id = st.id
//            WHERE (:fromStation IS NULL OR sf.name ILIKE CONCAT('%', :fromStation, '%'))
//              AND (:toStation IS NULL OR st.name ILIKE CONCAT('%', :toStation, '%'))
//              AND (:date IS NULL OR s.departure_date = CAST(:date AS DATE))
//            LIMIT :limit OFFSET :offset
//            """, nativeQuery = true)
//    List<Train> findAllWithFiltersNative(
//            @Param("fromStation") String fromStation,
//            @Param("toStation") String toStation,
//            @Param("date") String date,
//            @Param("limit") int limit,
//            @Param("offset") int offset);
//
//    // Separate query to count the total results
//    @Query(value = """
//            SELECT COUNT(DISTINCT t.id)
//            FROM train t
//            JOIN schedule s ON t.id = s.train_id
//            JOIN route r ON s.route_id = r.id
//            JOIN station sf ON r.from_station_id = sf.id
//            JOIN station st ON r.to_station_id = st.id
//            WHERE (:fromStation IS NULL OR sf.name ILIKE CONCAT('%', :fromStation, '%'))
//              AND (:toStation IS NULL OR st.name ILIKE CONCAT('%', :toStation, '%'))
//              AND (:date IS NULL OR s.departure_date = CAST(:date AS DATE))
//            """, nativeQuery = true)
//    long countAllWithFiltersNative(
//            @Param("fromStation") String fromStation,
//            @Param("toStation") String toStation,
//            @Param("date") String date);
//}



//package com.project.irctc.repository;
//
//import com.project.irctc.model.Train;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//
//@Repository
//public interface TrainRepository extends JpaRepository<Train, Long> {
//
//    @Query(value = """
//            SELECT DISTINCT t.*
//            FROM train t
//            JOIN schedule s ON t.id = s.train_id
//            JOIN route r ON s.route_id = r.id
//            JOIN station sf ON r.from_station_id = sf.id
//            JOIN station st ON r.to_station_id = st.id
//            WHERE (:fromStation IS NULL OR sf.name ILIKE %:fromStation%)
//              AND (:toStation IS NULL OR st.name ILIKE %:toStation%)
//              AND (:date IS NULL OR s.departure_date = CAST(:date AS DATE))
//            """,
//            countQuery = """
//            SELECT COUNT(DISTINCT t.id)
//            FROM train t
//            JOIN schedule s ON t.id = s.train_id
//            JOIN route r ON s.route_id = r.id
//            JOIN station sf ON r.from_station_id = sf.id
//            JOIN station st ON r.to_station_id = st.id
//            WHERE (:fromStation IS NULL OR sf.name ILIKE %:fromStation%)
//              AND (:toStation IS NULL OR st.name ILIKE %:toStation%)
//              AND (:date IS NULL OR s.departure_date = CAST(:date AS DATE))
//            """,
//            nativeQuery = true)
//    Page<Train> findAllWithFiltersNative(@Param("fromStation") String fromStation,
//                                         @Param("toStation") String toStation,
//                                         @Param("date") LocalDate date,
//                                         Pageable pageable);
//}
//
