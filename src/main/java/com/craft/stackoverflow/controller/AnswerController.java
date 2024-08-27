package com.craft.stackoverflow.controller;


import com.craft.stackoverflow.dto.AnswerDto;
import com.craft.stackoverflow.entities.User;
import com.craft.stackoverflow.service.AnswerService;
import com.craft.stackoverflow.util.ValidatorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private ValidatorUtil validatorUtil;
    @PostMapping
    @Operation(
            description = "Create Answer",
            responses = {
                    @ApiResponse(responseCode = "400",ref = "badRequest"),
                    @ApiResponse(responseCode = "500",ref = "internalServerError"),
                    @ApiResponse(responseCode = "201",useReturnTypeSchema = true)
            }
    )
    ResponseEntity<AnswerDto> create(@RequestPart("answer") String answerRequest,
                                     @RequestPart(value = "multimedia", required = false) MultipartFile file,
                                     @AuthenticationPrincipal User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AnswerDto answerDto = objectMapper.readValue(answerRequest, AnswerDto.class);

        validatorUtil.isValid(answerDto);

        return new ResponseEntity(answerService.create(answerDto, user), HttpStatus.CREATED);
    }

}
