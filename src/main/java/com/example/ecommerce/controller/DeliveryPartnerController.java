package com.example.ecommerce.controller;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.auth.RegistrationRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery-partner")
@CrossOrigin(value = "*", maxAge = 3000)
//TODO: update account endpoint
public class DeliveryPartnerController {

    @Autowired
    private OrderService orderService;
    @Operation (
            summary = "Get all orders",
            description = "Get all orders of delivery partner"
    )
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
                                                "data": [
                                                    {
                                                        "id": 12,
                                                        "storeId": 20,
                                                        "userId": 1,
                                                        "deliveryPartner: 14,
                                                        "orderDate": "2021-10-01T00:00:00.000+00:00",
                                                        "fromAddress": "Street 1 Tiger road Hanoi",
                                                        "toAddress": "150a District 1 Saigon",
                                                        "status": "READY"
                                                    },
                                                    {
                                                        "id": 12,
                                                        "storeId": 20,
                                                        "userId": 1,
                                                        "deliveryPartner: 14,
                                                        "orderDate": "2021-10-01T00:00:00.000+00:00",
                                                        "fromAddress": "Street 1 Tiger road Hanoi",
                                                        "toAddress": "150a District 1 Saigon",
                                                        "status": "IN_PROGRESS"
                                                    },
                                                    {
                                                        "id": 12,
                                                        "storeId": 20,
                                                        "userId": 1,
                                                        "deliveryPartner: 14,
                                                        "orderDate": "2021-10-01T00:00:00.000+00:00",
                                                        "fromAddress": "Street 1 Tiger road Hanoi",
                                                        "toAddress": "150a District 1 Saigon",
                                                        "status": "DELIVERED"
                                                    }
                                                ]
                                                }
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
        return orderService.getAllOrder();
    }

    //TODO: order has the attribute of deliveryTime
    @Operation (
            summary = "Get order by id",
            description = "Get order by id of delivery partner"

    )
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
                                                "data": {
                                                        "id": 12,
                                                        "store": {
                                                            "id": 12,
                                                            "name": "Store 1",
                                                            "email": "ahihi@gmail.com",
                                                            "avatar": "http://cdnaowe/asw-ds.com"
                                                        },
                                                        "user": {
                                                            "id": 1,
                                                            "name": "Hoa",
                                                            "email": "ahihi@gmail.com",
                                                            "avatar": "http://cdnaowe/asw-ds.com"
                                                            "phoneNumber: "3240234235"
                                                        },
                                                        "deliveryPartner": {
                                                            "id": 12,
                                                            "name": "Fast and Care Express",
                                                            "email": "adw@gmail.com",
                                                            "avatar": "http://cdnaowe/asw-ds.com"
                                                        }
                                                        "orderDate": "2021-10-01T00:00:00.000+00:00",
                                                        "fromAddress": "Street 1 Tiger road Hanoi",
                                                        "toAddress": "150a District 1 Saigon",
                                                        "status": "IN_PROGRESS"
                                                    },
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 404,
                                                "message": "Order Not Found",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/orders/{id}")
    public ResponseEntity<Response> getOrderById(@PathVariable @Schema(description = "id of order") Long id) {
        return orderService.getOrderById(id);
    }

    @Operation(
            summary = "Update status of order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationRequest.class),
                            examples = @ExampleObject(value = """
                                        {
                                        "email": "Hainguyen@gmail.com",
                                        "password": "123456",
                                        "name": "Hai Nguyen"
                                        }
                                        """
                            )
                    )
            )

    )
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
    @PutMapping("/update-status-order")
    public ResponseEntity<Response> updateStatusOrder(@RequestBody UpdateOrderRequest accountRequest) {
        return orderService.updateOrder(accountRequest);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
