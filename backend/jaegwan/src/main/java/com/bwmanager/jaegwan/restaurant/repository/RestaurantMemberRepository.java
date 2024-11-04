package com.bwmanager.jaegwan.restaurant.repository;

import com.bwmanager.jaegwan.member.entity.Member;
import com.bwmanager.jaegwan.restaurant.entity.RestaurantMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantMemberRepository extends JpaRepository<RestaurantMember, Long> {

    @Query("SELECT rm.member FROM RestaurantMember rm WHERE rm.restaurant.id = :restaurantId")
    List<Member> findMembersByRestaurantId(@Param("restaurantId") Long restaurantId);

}
