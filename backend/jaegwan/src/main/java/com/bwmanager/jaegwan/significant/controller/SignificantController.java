package com.bwmanager.jaegwan.significant.controller;

import com.bwmanager.jaegwan.significant.service.SignificantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/significant")
public class SignificantController {

    private SignificantService significantService;
}
