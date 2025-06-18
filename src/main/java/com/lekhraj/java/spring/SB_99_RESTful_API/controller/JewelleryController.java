package com.lekhraj.java.spring.SB_99_RESTful_API.controller;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lekhraj.java.spring.SB_99_RESTful_API.model.StatusEnum;
//import com.lekhraj.java.spring.SB_99_RESTful_API.model.dto.Config2Inject;
import com.lekhraj.java.spring.SB_99_RESTful_API.model.dto.JewelleryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/jewellery")
@Tag(name = "Jewellery API for My-Store", description = "Custom API for demonstrating Jewellery APIs")
@Validated
public class JewelleryController {

    //====================
    // 1. SWAGGER API Doc
    //====================
    @GetMapping(value={"v1/get-one"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Operation(
            summary = "Get random jewellery",
            description = "Gives hardcoded object",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    schema = @Schema(implementation = JewelleryDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Item not found")
            }
    )
    /*
        method arg : @Parameter(
                name =  "firstName",
                description  = "First Name of the user",
                example = "Vatsal",
                required = true)
    */
    JewelleryDTO getJewellery() throws Exception
    {
        JewelleryDTO dto =  JewelleryDTO.builder()
                .name("Jewellery-1")
                .id(1)
                .price(20000)
                .createTime(LocalDateTime.now())
                .build();
        //throw new Exception("testing-exception");     <<<<
        return dto;
    }



    //====================
    // 2. BINDING
    //====================
    @GetMapping(value = {
             "v2/get-one/{pathVariable1}"
            ,"v2/get-one/{pathVariable1}/{pathVariable2}"
    })
    ResponseEntity<JewelleryDTO> getJewelleryRE(
            HttpServletResponse response,

            @RequestParam(value = "ReqParam1", defaultValue = "defaultValue", required = false) String reqParam1,
            @RequestParam Map<String,String> allRequestParams, // <<<< map

            @PathVariable("pathVariable1") String pathVariable1,
            @PathVariable(value="pathVariable2", required = false) String pathVariable2_optional,
            @PathVariable Map<String,String> allPathVariables, // <<<< map

            @RequestHeader("h1") String h1,
            @RequestHeader(value="h2",required = false, defaultValue = "v2-default") String h2_Optional,
            @RequestHeader Map<String,String> allHeader, // <<<< map
            @RequestHeader HttpHeaders httpHeaders

            , @RequestBody JewelleryDTO dto_input
    ) throws Exception
    {
        JewelleryDTO dto =  JewelleryDTO.builder()
                .name("Jewellery-2")
                .id(2)
                .price(2000)
                .createTime(LocalDateTime.now())
                .build();

        log.info(" \n\n >>> pathVariable1|2 : {}|{}, header-1|2: {}|{} \n", pathVariable1,pathVariable2_optional, h1,h2_Optional);
        allHeader.forEach((k,v)-> System.out.println(k+":"+v));
        allPathVariables.forEach((k,v)-> System.out.println(k+"::"+v));
        allRequestParams.forEach((k,v)-> System.out.println(k+":::"+v));

        // Way-1
        // response.setHeader("h1","v1");
        // response.setStatus(200);

        // way-2
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("h1","v1");
        ResponseEntity httpResponse1 = new ResponseEntity<JewelleryDTO>(dto,responseHeader,HttpStatus.OK);

        // WAY-3 ResponseEntity BUILDER
        ResponseEntity httpResponse2 = ResponseEntity
                .status(200)
                .header("h1", "v1")
                .header("h2", "v2")
                .body(dto);

        return httpResponse2;
    }

    //=====================================
    // 3. Validator - Request and response
    // cannot valid ResponseEntity.
    //=====================================
    @Valid
    @PostMapping("v3/get-one")
    // JSON --> dtoInput
    JewelleryDTO getJewellery3(@Valid @RequestBody JewelleryDTO dtoInput) throws Exception
    {
        log.info("dtoInput : {}", dtoInput);

        /*JewelleryDTO dto =  JewelleryDTO.builder()
                .name("Jewellery-3")
                .id(dtoInput.getId())
                .price(300) // change to fail
                .createTime(LocalDateTime.now())
                .build();
        return dto;*/

        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dtoInput);
        JewelleryDTO dto2 = mapper.readValue(json, JewelleryDTO.class);*/

         dtoInput.setStockStatus(StatusEnum.IN_STOCK); // <<<
         return dtoInput;
    }

    // ============= MAIN ==========
    public static void main(String... a){

    }

    // 1. Deserialize JSON with injected values
    void deserialize(){
        ObjectMapper mapper = new ObjectMapper();

        // Example JSON
        String json = "{\n" +
                "    \"jewelleryNameAlias2\": \"Jewellery-3\",\n" +
                "    \"jewelleryId\": 34,\n" +
                "    \"jewelleryPrice\": 300,\n" +
                "    \"derive\": \"XXXXXX\",\n" +
                "    \"category\": {\n" +
                "        \"type\": \"BRACLET\"\n" +
                "    }\n" +
                "}";
    }
}
