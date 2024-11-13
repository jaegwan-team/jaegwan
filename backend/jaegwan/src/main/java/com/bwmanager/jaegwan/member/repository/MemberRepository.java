package com.bwmanager.jaegwan.member.repository;

import com.bwmanager.jaegwan.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

}
