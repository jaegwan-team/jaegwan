package com.bwmanager.jaegwan.restaurant.repository;

import com.bwmanager.jaegwan.member.entity.Member;
import com.bwmanager.jaegwan.restaurant.entity.Restaurant;
import com.bwmanager.jaegwan.restaurant.entity.RestaurantMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantMemberRepository extends JpaRepository<RestaurantMember, Long> {

    boolean existsByMemberIdAndRestaurantId(Long memberId, Long restaurantId);

    boolean existsByMemberAndRestaurant(Member member, Restaurant restaurant);

    Optional<RestaurantMember> findByMemberAndRestaurant(Member member, Restaurant restaurant);

    @Query("SELECT rm.member " +
            "FROM RestaurantMember rm " +
            "WHERE rm.restaurant.id = :restaurantId")
    List<Member> findMembersByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT rm.restaurant " +
            "FROM RestaurantMember rm " +
            "WHERE rm.member.id = :memberId")
    List<Restaurant> findRestaurantsByMemberId(@Param("memberId") Long memberId);

}
