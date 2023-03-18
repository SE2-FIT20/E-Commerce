package com.example.ecommerce.controller;


import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Promotion;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.dto.request.promotion.UpdatePromotionRequest;
import com.example.ecommerce.service.impl.StoreService;
import com.example.ecommerce.service.service.OrderService;
import com.example.ecommerce.service.service.ProductService;
import com.example.ecommerce.service.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
@AllArgsConstructor
@CrossOrigin(value = "*", maxAge = 3000)
public class StoreController {
    private final StoreService storeService;
    private final ProductService productService;
    private final OrderService orderService;
    private final PromotionService promotionService;

    @Operation(summary = "Get all products")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all products successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
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
            """))), @ApiResponse(responseCode = "400", description = "Get all products failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Get all products failed",
                "data": null
            }
            """)))

    })
    @GetMapping("/products")
    public ResponseEntity<Response> getProducts() {
        return productService.getAllProduct();
    }

    @Operation(summary = "Get product by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get product by id successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 200,
                "message": "Get product by id successfully",
                "data": {
                    "id": 1,
                    "name" : "iphone15",
                    "description" : "best iphone",
                    "category": "electronic",
                    "price" : 999.99,
                    "image" : "https:link.com"
                }
            }
            """))), @ApiResponse(responseCode = "400", description = "Get product by id failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Get product failed",
                "data": null
            }
            """)))

    })
    @GetMapping("/product/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }


    @Operation(summary = "Create product", description = "Create product", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateProductRequest.class), examples = @ExampleObject(value = """
            {
                "name" : "t-shirt",
                "description" : "best shirt",
                "category": "fashion",
                "price" : 12,
                "image" : "https:link.com"
            }
            """))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Create product successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
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
                                                """))), @ApiResponse(responseCode = "400", description = "Create product failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Create product failed",
                "data": null
            }
            """))), @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Product Not Found ",
                "data": null
            }
            """)))})
    @PostMapping("/products")
    public ResponseEntity<Response> createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }


    @Operation(summary = "Update product", description = "Update product by id", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateProductRequest.class), examples = @ExampleObject(value = """
            {
                "id": 3,
                "name" : "t-shirt",
                "description" : "best shirt",
                "category": "fashion",
                "price" : 12,
                "image" : "https:link.com"
            }
            """))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Update product successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 200,
                "message": "Update product successfully",
                "data": null
            }
            """))), @ApiResponse(responseCode = "400", description = "Update product failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Update product failed",
                "data": null
            }
            """)))})
    @PutMapping("/products")
    public ResponseEntity<Response> updateProduct(@RequestBody UpdateProductRequest request) {
        return productService.updateProduct(request);
    }


    @Operation(summary = "Delete product", description = "Delete product by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Delete product successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 200,
                "message": "Delete product successfully",
                "data": null
            }
            """))), @ApiResponse(responseCode = "400", description = "Delete product failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Delete product failed",
                "data": null
            }
            """)))})
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable @Schema(description = "delete product by id") Long id) {
        return productService.deleteProductById(id);
    }

    @Operation(summary = "Get orders", description = "Get all orders")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get order successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 200,
                "message": "Get order successfully",
                "data": [
                {
                    [
                        {
                            "id": 1,
                            "quantity": 2
                        },
                        {
                            "id": 2,
                            "quantity": 3
                        }
                    ],
                    status: "Delivering"
                    }
                ]
                                                                                                
            """))), @ApiResponse(responseCode = "400", description = "Get order failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Get order failed",
                "data": null
            }
            """)))})
    @GetMapping("/orders")
    public ResponseEntity<Response> getOrders() {
        return orderService.getAllOrder();
    }


    @Operation(summary = "update order ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Update order successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 200,
                "message": "Update order successfully",
                "data": [
                   {
                   orderId: 1,
                   status: "Pending"
                   }
                   ]
            }
            """))), @ApiResponse(responseCode = "400", description = "Update order failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Update order failed",
                "data": null
            }
            """)))})
    @PutMapping("/orders")
    public ResponseEntity<Response> updateOrder(@RequestBody UpdateOrderRequest request) {
        return orderService.updateOrder(request);
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
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get promotion successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
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
            """))), @ApiResponse(responseCode = "400", description = "Get promotion failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Get promotion failed",
                "data": null
            }
            """)))})
    @GetMapping("/promotion")
    public ResponseEntity<Response> getPromotion() {
        return promotionService.getAllPromotions();
    }

    @Operation(summary = "create promotion", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatePromotionRequest.class), examples = @ExampleObject(value = """
            {
                "code": "SHP-123",
                "description": "Discount 50% for all products",
                "percent": 50,
                "storeId": null,
            }
            """))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Create promotion successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 200,
                "message": "Create promotion successfully",
                "data": null
            }
            """))), @ApiResponse(responseCode = "400", description = "Create promotion failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Create promotion failed",
                "data": null
            }
            """)))})
    @PostMapping("/promotion")
    public ResponseEntity<Response> createPromotion(@RequestBody CreatePromotionRequest promotionRequest) {
        return promotionService.createPromotion(promotionRequest);

    }

    @Operation(summary = "update promotion", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdatePromotionRequest.class), examples = @ExampleObject(value = """
            {
                "name": "Promotion 1",
                "percent": 10,
                "storeId": null,
                "isGlobal": true,
            }
            """))))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Update promotion successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 200,
                "message": "Update promotion successfully",
                "data": null
            }
            """))), @ApiResponse(responseCode = "400", description = "Update promotion failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Update promotion failed",
                "data": null
            }
            """)))})
    @PutMapping("/promotion")
    public ResponseEntity<Response> updatePromotionRequest(@RequestBody UpdatePromotionRequest updatePromotionRequest) {
        return promotionService.updatePromotion(updatePromotionRequest);
    }

    @Operation(summary = "Get store information")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get store information successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
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
            """))), @ApiResponse(responseCode = "400", description = "Get store information failed!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class), examples = @ExampleObject(value = """
            {
                "status": 400,
                "message": "Get store information failed",
                "data": null
            }
            """)))})
    @GetMapping("/account")
    public ResponseEntity<Response> getAccountInformation() {
        User currentStore = getCurrentStore();
        return storeService.getStoreInformationById(currentStore.getId());
    }

    private User getCurrentStore() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
