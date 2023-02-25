package com.example.ecommerce.controller;


import com.example.ecommerce.dto.request.UpdateAccountRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/store")
public class StoreController {

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all products successfully!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "Get all products successfully",
                                        "data": [
                                            {
                                                        "id": 1,
                                                        "name" : "iphone15",
                                                        "description" : "best iphone",
                                                        "category": "electronic",
                                                        "price" : 999.99,
                                                        "image" : "https:link.com"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "name" : "apple",
                                                        "description" : "best fruit",
                                                        "category": "food",
                                                        "price" : 90,
                                                        "image" : "https:link.com"
                                                    },
                                                    {
                                                        "id": 3,
                                                        "name" : "t-shirt",
                                                        "description" : "best shirt",
                                                        "category": "fashion",
                                                        "price" : 12,
                                                        "image" : "https:link.com"
                                                    }
                                        ]
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Get all products failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Get all products failed",
                                        "data": null
                                    }
                                    """)
                    )
            )

    })
    @GetMapping("/products")
    public ResponseEntity<Response> getProducts() {
        return null;
    }

    @Operation(summary = "Create product",
            description = "Create product by id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateProductRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "name" : "t-shirt",
                                        "description" : "best shirt",
                                        "category": "fashion",
                                        "price" : 12,
                                        "image" : "https:link.com"
                                    }
                                    """)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create product successfully!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
>>>>>>> b5db3b5 (check storeController)
                                    {
                                        "status": 200,
                                        "message": "Create product by id successfully",
                                        "data":  {
                                            "id": 3,
                                            "name" : "t-shirt",
                                            "description" : "best shirt",
                                            "category": "fashion",
                                            "price" : 12,
                                            "image" : "https:link.com"
                                        }
                                        
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Create product failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Create product failed",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Product Not Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Product Not Found   ",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @PostMapping("/products")
    public ResponseEntity<Response> createProduct(@RequestBody CreateProductRequest request) {
        return null;
    }


    @Operation(
            summary = "Update product",
            description = "Update product by id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateProductRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": 3,
                                        "name" : "t-shirt",
                                        "description" : "best shirt",
                                        "category": "fashion",
                                        "price" : 12,
                                        "image" : "https:link.com"
                                    }
                                    """)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update product successfully!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "Update product by id successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Update product failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Update product failed",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @PutMapping("/products")
    public ResponseEntity<Response> updateProduct(@RequestBody UpdateProductRequest request) {
        return null;
    }


    @Operation(summary = "Delete product",
            description = "Delete product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete product successfully!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "Delete product by id successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Delete product failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Delete product failed",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @DeleteMapping("/products/{id}")

    public ResponseEntity<Response> deleteProduct(@PathVariable @Schema(description = "delete product by id") Long id) {
        return null;
    }

    @Operation(summary = "Get orders",
            description = "Get all orders"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get order successfully!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "Get order successfully",
                                        "data": null
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Get order failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Get order failed",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @GetMapping("/orders")
    public ResponseEntity<Response> getOrders() {
        return null;
    }


    @Operation(summary = "update order ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update order successfully!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "Update order successfully",
                                        "data": [
                                           {
                                               "id": 12,
                                               "storeId": 20,
                                               "userId": 1,
                                               "deliveryPartner": 14,
                                               "orderDate": "2021-10-01T00:00:00.000+00:00",
                                               "fromAddress": "Street 1 Tiger road Hanoi",
                                               "toAddress": "150a District 1 Saigon",
                                               "status": "READY"
                                           },
                                           {
                                               "id": 12,
                                               "storeId": 20,
                                               "userId": 1,
                                               "deliveryPartner": 14,
                                               "orderDate": "2021-10-01T00:00:00.000+00:00",
                                               "fromAddress": "Street 1 Tiger road Hanoi",
                                               "toAddress": "150a District 1 Saigon",
                                               "status": "IN_PROGRESS"
                                           },
                                           {
                                               "id": 12,
                                               "storeId": 20,
                                               "userId": 1,
                                               "deliveryPartner": 14,
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
            @ApiResponse(responseCode = "400", description = "Update order failed!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 400,
                                        "message": "Update order failed",
                                        "data": null
                                    }
                                    """)
                    )
            )
    })
    @PutMapping("/orders")
    public ResponseEntity<Response> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderRequest request) {
        return null;
    }

    /* this is optional as the result of the team discussion

      @ApiResponses (
              value = {
                      @ApiResponse (responseCode = "200", description = "Get sale report successfully!",
                              content = @Content (mediaType = "application/json",
                                      schema = @Schema (implementation = Response.class),
                                      examples = @ExampleObject (value = """
                                              {
                                                  "status": 200,
                                                  "message": "Get sale report successfully",
                                                  "data":  null
                                              }
                                              """)
                              )
                      ),
                      @ApiResponse(responseCode = "400", description = "Get sale report failed!",
                              content = @Content(mediaType = "application/json",
                                      schema = @Schema(implementation = Response.class),
                                      examples = @ExampleObject(value = """
                                              {
                                                  "status": 400,
                                                  "message": "Get sale report failed",
                                                  "data": null
                                              }
                                              """)
                              )
                      )
              }
      )

      @GetMapping("/sale-report")
      public ResponseEntity<Response> getSaleReport() {
          return null;
      }
  */
    @Operation(summary = "get promotion")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Get promotion successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get promotion successfully",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "name": "Promotion 1",
                                                        "percent": 10,
                                                        "storeId": null,
                                                        "isGlobal": true,
                                                    },
                                                     {
                                                        "id": 2,
                                                        "name": "Promotion 2",
                                                        "percent": 52,
                                                        "storeId": 42,
                                                        "isGlobal": false,
                                                    }
                                                     {
                                                        "id": 3,
                                                        "name": "Promotion 3",
                                                        "percent": 50,
                                                        "storeId": null,
                                                        "isGlobal": true,
                                                    }
                                                ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Get promotion failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get promotion failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/promotion")
    public ResponseEntity<Response> getPromotion() {
        return null;
    }

    @Operation(summary = "create promotion",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatePromotionRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "name": "Promotion 1",
                                        "percent": 10,
                                        "storeId": null,
                                        "isGlobal": true,
                                    }
                                    """)
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Create promotion successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Create promotion successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Create promotion failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Create promotion failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PostMapping("/promotion")
    public ResponseEntity<Response> createPromotion(@RequestBody CreatePromotionRequest promotionRequest) {
        return null;
    }

    @Operation(summary = "update promotion",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdatePromotionRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "name": "Promotion 1",
                                        "percent": 10,
                                        "storeId": null,
                                        "isGlobal": true,
                                    }
                                    """)
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Update store information successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Update store account successfully",
                                                "data": null
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Update store information failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Update store information failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @PutMapping("/account")
    public ResponseEntity<Response> updatePromotionRequest(@RequestBody UpdatePromotionRequest updatePromotionRequest) {
        return null;
    }

    @Operation(summary = "delete promotion")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Get store information successfully!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 200,
                                                "message": "Get store account successfully",
                                                "data": {
                                                    "id": 10,
                                                    "name": "Store 1",
                                                    "email": "ekaopsdk2gmail.com",
                                                    "address": "Hanoi",
                                                    "description": "Our store sells everything!"
                                                }
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Get store information failed!",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": 400,
                                                "message": "Get store information failed",
                                                "data": null
                                            }
                                            """)
                            )
                    )
            }
    )
    @GetMapping("/account")
    public ResponseEntity<Response> getAccountInformation() {
        return null;
    }
}
