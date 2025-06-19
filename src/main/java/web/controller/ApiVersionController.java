package web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiVersionController
{
    // API Vesrioning
    @GetMapping(
            value="test/get",
            produces="application/v1+json"
    )
    private Integer getId() { return 20; }
    // in header, set Accept="application/v1+json"

    @GetMapping(
            value="test/get",
            produces="application/v2+json"
    )
    private String getName() { return "20String"; }
    // in header, set Accept="application/v2+json"
}
