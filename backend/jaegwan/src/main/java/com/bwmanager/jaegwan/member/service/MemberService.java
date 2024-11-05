package com.bwmanager.jaegwan.member.service;

import com.bwmanager.jaegwan.member.dto.MemberResponse;

public interface MemberService {

    /**
     * 사용자 정보를 조회한다.
     * @param id 사용자 ID
     * @return 사용자 ID에 해당하는 사용자 정보
     */
    public MemberResponse getMember(final Long id);

}
