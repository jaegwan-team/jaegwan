package com.bwmanager.jaegwan.restaurant.controller;

import com.bwmanager.jaegwan.global.dto.CommonResponse;
import com.bwmanager.jaegwan.member.dto.MemberResponse;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantRequest;
import com.bwmanager.jaegwan.restaurant.dto.RestaurantResponse;
import com.bwmanager.jaegwan.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "나의 식당 목록 조회", description = "내가 속해있는 식당의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "나의 식당 목록 조회에 성공했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getMyRestaurants(@AuthenticationPrincipal String currentMemberEmail) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(restaurantService.getMyRestaurants(currentMemberEmail))
                .message("나의 식당 목록 조회에 성공했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "식당 정보 조회", description = "식당 ID에 해당하는 식당의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식당 정보 조회에 성공했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "식당에 대한 권한이 없습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "식당이 존재하지 않습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurant(@AuthenticationPrincipal String currentMemberEmail,
                                           @PathVariable("id") Long id) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(restaurantService.getRestaurant(currentMemberEmail, id))
                .message("식당 정보 조회에 성공했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "식당에 속한 사용자 목록 조회", description = "식당에 속한 사용자들의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식당에 속한 사용자 목록 조회에 성공했습니다.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MemberResponse.class)))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "식당에 대한 권한이 없습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "식당 또는 사용자가 존재하지 않습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @GetMapping("/{id}/member")
    public ResponseEntity<?> getRestaurantMembers(@AuthenticationPrincipal String currentMemberEmail,
                                                  @PathVariable("id") Long id) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(restaurantService.getRestaurantMembers(currentMemberEmail, id))
                .message("식당에 속한 사용자 목록 조회에 성공했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "식당 등록", description = "식당 정보를 통해 식당을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "식당을 등록했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createRestaurant(@AuthenticationPrincipal String currentMemberEmail,
                                              @RequestBody RestaurantRequest request) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(restaurantService.createRestaurant(currentMemberEmail, request))
                .message("식당을 등록했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "식당 정보 수정", description = "식당 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식당 정보를 수정했습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "식당에 대한 권한이 없습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "식당이 존재하지 않습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@AuthenticationPrincipal String currentMemberEmail,
                                              @PathVariable("id") Long id,
                                              @RequestBody RestaurantRequest request) {
        CommonResponse<Object> response = CommonResponse.builder()
                .data(restaurantService.updateRestaurant(currentMemberEmail, id, request))
                .message("식당 정보를 수정했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "식당 삭제", description = "식당을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식당을 삭제했습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "식당에 대한 권한이 없습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "식당이 존재하지 않습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@AuthenticationPrincipal String currentMemberEmail,
                                              @PathVariable("id") Long id) {
        restaurantService.deleteRestaurant(currentMemberEmail, id);

        CommonResponse<Object> response = CommonResponse.builder()
                .message("식당을 삭제했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "식당에 사용자 추가", description = "식당에 사용자를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "식당에 사용자를 추가했습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "식당에 대한 권한이 없습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "식당 또는 사용자가 존재하지 않습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @PostMapping("/{id}/member/{memberId}")
    public ResponseEntity<?> addRestaurantMember(@AuthenticationPrincipal String currentMemberEmail,
                                                 @PathVariable("id") Long id,
                                                 @PathVariable("memberId") Long newMemberId) {
        restaurantService.addRestaurantMember(currentMemberEmail, id, newMemberId);

        CommonResponse<Object> response = CommonResponse.builder()
                .message("식당에 사용자를 추가했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "식당에서 사용자 제외", description = "식당에서 사용자를 제외합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식당에서 사용자를 제외했습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청 데이터입니다.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "식당에 대한 권한이 없습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "식당 또는 사용자가 존재하지 않습니다.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 내부 에러가 발생했습니다.",
                    content = @Content)
    })
    @DeleteMapping("/{id}/member/{memberId}")
    public ResponseEntity<?> removeRestaurantMember(@AuthenticationPrincipal String currentMemberEmail,
                                                    @PathVariable("id") Long id,
                                                    @PathVariable("memberId") Long memberDeleteId) {
        restaurantService.removeRestaurantMember(currentMemberEmail, id, memberDeleteId);

        CommonResponse<Object> response = CommonResponse.builder()
                .message("식당에서 사용자를 제외했습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
