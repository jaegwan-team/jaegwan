package com.bwmanager.jaegwan.member.dto;

import com.bwmanager.jaegwan.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

    private Long id;

    private String name;

    private String imageUrl;

    public static MemberResponse from(final Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .imageUrl(member.getImageUrl())
                .build();
    }

}
