package com.example.ecommerce.controller;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.UpdateDeliveryPartnerAccountRequest;
import com.example.ecommerce.dto.request.auth.RegistrationRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.service.service.DeliveryPartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/delivery-partner")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @Value("${default.elementPerPage}")
    private String defaultElementPerPage;
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
    public ResponseEntity<Response> getAllOrders(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                 @RequestParam(defaultValue = "0",  required = false) Integer elementsPerPage,
                                                 @RequestParam(defaultValue = "ALL",  required = false)  String status,
                                                 @RequestParam(defaultValue = "createdAt",  required = false) String filter,
                                                 @RequestParam(defaultValue = "desc",  required = false) String sortBy,
                                                 @RequestParam(required = false) String from,
                                                 @RequestParam(required = false) String to) {
        if (elementsPerPage == 0) {
            elementsPerPage = Integer.parseInt(defaultElementPerPage);
        }
        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;

        // the default value for from is 1970, it means that we will get all orders from the beginning
        if (from == null) {
            fromDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        } else {
            fromDateTime = LocalDateTime.parse(from + "T00:00:00"); // start of the day
        }

        // the default value for to is now, the default value for from is null
        if (to == null) {
            toDateTime = LocalDateTime.now();
        } else {
            toDateTime = LocalDateTime.parse(to + "T23:59:59"); // end of the day
        }

        User user = getCurrentUser();
        return deliveryPartnerService.getAllOrderByDeliveryPartners(page, elementsPerPage, user.getId(), status, filter, sortBy, from, to);
    }

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
        User user = getCurrentUser();
        return deliveryPartnerService.getOrderById(id, user.getId());
    }

    @GetMapping("/orders-count")
    public ResponseEntity<Response> getOrders(@RequestParam(required = false) String from,
                                              @RequestParam(required = false) String to) {


        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;

        // the default value for from is 1970, it means that we will get all orders from the beginning
        if (from == null) {
            fromDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        } else {
            fromDateTime = LocalDateTime.parse(from + "T00:00:00"); // start of the day
        }

        // the default value for to is now, the default value for from is null
        if (to == null) {
            toDateTime = LocalDateTime.now();
        } else {
            toDateTime = LocalDateTime.parse(to + "T23:59:59"); // end of the day
        }

        User currentDeliveryPartner = getCurrentUser();
        return deliveryPartnerService.countOrders(currentDeliveryPartner.getId(), fromDateTime, toDateTime);
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
    public ResponseEntity<Response> updateStatusOrder( @RequestBody UpdateOrderRequest updateRequest) {
        User user = getCurrentUser();
        return deliveryPartnerService.updateOrder(user.getId(), updateRequest);
    }

    @GetMapping("/account")
    public ResponseEntity<Response> getAccount() {
        User user = getCurrentUser();
        return deliveryPartnerService.getAccountInformation(user.getId());
    }

    @PutMapping("/account")
    public ResponseEntity<Response> updateAccount(@RequestBody UpdateDeliveryPartnerAccountRequest updateAccountRequest) {
        User user = getCurrentUser();
        return deliveryPartnerService.updateAccount(user.getId(), updateAccountRequest);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
