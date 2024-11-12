package com.bwmanager.jaegwan.member.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.member.dto.MemberResponse;
import com.bwmanager.jaegwan.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회에 성공했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자 정보가 존재하지 않습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal String email) {
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .data(memberService.getMemberByEmail(email))
                .message("내 정보 조회에 성공했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

}
