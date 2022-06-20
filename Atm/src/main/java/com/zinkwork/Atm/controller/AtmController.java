package com.zinkwork.Atm.controller;

import com.zinkwork.Atm.controller.dto.WithdrawalRequest;
import com.zinkwork.Atm.service.AtmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name="atm-rest-controller")
@RestController
@RequestMapping("/atm")
@Validated
@RequiredArgsConstructor
public class AtmController {

    @Autowired
    private AtmService atmservice;

    @Operation(tags="atm-rest-controller",summary = "getBalance",description = "rest call to return account details by id")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))

    @GetMapping(path = "/{accountId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccount(@PathVariable String accountId){
        return new ResponseEntity<>(atmservice.getAccount(accountId), HttpStatus.OK);
    }

    @Operation(tags="atm-rest-controller",summary = "getBalance",description = "rest call to return the balance")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))
    @GetMapping(path = "/balance/{accountId}/{pin}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccountBalance(@PathVariable String accountId, @PathVariable String pin){
        return new ResponseEntity<>(atmservice.getAccountBalanceById(accountId, pin), HttpStatus.OK);
    }

    @Operation(tags="atm-rest-controller",summary = "getBalance",description = "rest call to return the balance")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))
    @PostMapping(path = "/withdraw/",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withdraw(@RequestBody WithdrawalRequest request){
        return new ResponseEntity<>(atmservice.withdrawFunds(request.getAmount(), request.getAccountId(), request.getPin()), HttpStatus.OK);
    }


}
