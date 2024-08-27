package com.craft.stackoverflow.controller;

import com.craft.stackoverflow.dto.QuestionDto;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.service.*;
import com.craft.stackoverflow.util.ValidatorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ValidatorUtil validatorUtil;

    @PostMapping
    @Operation(
            description = "Create Question",
            responses = {
                    @ApiResponse(responseCode = "400",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "201",useReturnTypeSchema = true)
            }
    )
    ResponseEntity<QuestionDto> create(@RequestPart("question") String questionRequest,
                                       @RequestPart(value = "multimedia", required = false) MultipartFile file,
                                       @AuthenticationPrincipal User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        QuestionDto questionDto = objectMapper.readValue(questionRequest, QuestionDto.class);
        validatorUtil.isValid(questionDto);
        return new ResponseEntity(questionService.create(questionDto, file, user), HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}")
    @Operation(
            description = "Get Question",
            responses = {
                    @ApiResponse(responseCode = "400",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "404",ref = "notFound"),
                    @ApiResponse(responseCode = "200",useReturnTypeSchema = true)
            }
    )
    ResponseEntity<QuestionDto> getById(@PathVariable @NotNull Long questionId) {
        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }

}
