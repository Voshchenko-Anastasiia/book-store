package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying; // Import this
import org.springframework.data.jpa.repository.Query;    // Import this
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

//    @Modifying
//    @Query("DELETE FROM Order o WHERE o.user.id = :userId")
//    void deleteByUserId(@Param("userId") Long userId);
}