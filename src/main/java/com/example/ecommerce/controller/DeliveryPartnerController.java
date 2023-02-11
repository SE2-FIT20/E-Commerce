package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.AccountRequest;
import com.example.ecommerce.dto.response.Response;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery-partner")
public class DeliveryPartnerController {


    @ApiResponses (
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all orders successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get all orders successfully",
                                                "data": null    }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get all orders failed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get all orders failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/orders")
    public ResponseEntity<Response> getAllOrders() {
        return null;
    }

    @ApiResponses (
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get order by id successfully!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get order by id successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Get order by id failed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get order by id failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/orders/{id}")
    public ResponseEntity<Response> getOrderById(@PathVariable Long id) {
        return null;
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update status successfully!",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "Update status successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Update status failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Wrong information",
                                        "data": null
                                    }
                                    """)
                    )
            )
    }
    )
    @PutMapping("/account")
    public ResponseEntity<Response> updateStatusOrder(@RequestBody AccountRequest accountRequest) {
        return null;
    }
}
