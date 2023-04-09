package com.example.ecommerce.controller;


import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.request.product.CreateProductRequest;
import com.example.ecommerce.dto.request.product.UpdateProductRequest;
import com.example.ecommerce.dto.request.promotion.CreatePromotionRequest;
import com.example.ecommerce.dto.request.store.UpdateStoreRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/store")
@CrossOrigin(value = "*", maxAge = 3000)
public class StoreController {
    @Value("${default.elementPerPage}")
    private String defaultElementPerPage;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PromotionService promotionService;

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
        User currentStore = getCurrentStore();
        return storeService.createProduct(currentStore.getId(), request);
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
        User currentStore = getCurrentStore();
        return storeService.updateProduct(currentStore.getId(), request);
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
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Response> deleteProduct(@PathVariable @Schema(description = "delete product by id") Long productId) {
        User currentStore = getCurrentStore();
        return storeService.deleteProductById(currentStore.getId(), productId);
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
    public ResponseEntity<Response> getOrders(@RequestParam(defaultValue = "0", required = false) Integer page,
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

        User currentStore = getCurrentStore();
        return storeService.getAllOrders(currentStore.getId(), page, elementsPerPage, status, filter, sortBy, fromDateTime, toDateTime);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Response> getOrderById(@PathVariable Long orderId) {
        User currentStore = getCurrentStore();
        return storeService.getOrderById(currentStore.getId(), orderId);
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
        User currentStore = getCurrentStore();
        return storeService.updateOrder(currentStore.getId(), request);
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
        User currentStore = getCurrentStore();
        return storeService.getAllPromotions(currentStore.getId());
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
        User currentStore = getCurrentStore();
        return storeService.createPromotion(currentStore.getId(), promotionRequest);

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
        User currentStore = getCurrentStore();
        return storeService.updatePromotion(currentStore.getId(), updatePromotionRequest);
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

    @GetMapping("/search-order-by-code/{orderCode}")
    public ResponseEntity<Response> searchOrderByCode(@PathVariable String orderCode) {
        User currentStore = getCurrentStore();
        return storeService.searchOrderByCode(currentStore.getId(), orderCode);
    }

    @GetMapping("/search-order-by-customer-name/{customerName}")
    public ResponseEntity<Response> searchOrderByCustomerName(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                            @RequestParam(defaultValue = "0",  required = false) Integer elementsPerPage,
                                                            @PathVariable String customerName) {
        //TODO: if the name contains space, the space will be replaced by %20
        if (elementsPerPage == 0) {
            elementsPerPage = Integer.parseInt(defaultElementPerPage);
        }
        User currentStore = getCurrentStore();
        return storeService.searchOrderByCustomerName(currentStore.getId(), customerName, page, elementsPerPage);
    }



    public ResponseEntity<Response> updateAccountInformation(UpdateStoreRequest updateStoreRequest) {
        User currentStore = getCurrentStore();
        return storeService.updateInformation(currentStore.getId(), updateStoreRequest);
    }



//    @GetMapping("/store/{storeId}/filter-by-review")
//    public ResponseEntity<Response> getProductByStoreIdFilterByReview(@PathVariable("storeId") Long storeId) {
//        return storeService.getProductByStoreFilterByReview(storeId);
//    }
////
//    @GetMapping("store/{storeId}/asc")
//    public ResponseEntity<Response> getProductByStoreSortAscending(@PathVariable("storeId") Long storeId) {
//        return storeService.getProductByStoreSortByPriceAsc(storeId);
//    }
//
//    @GetMapping("store/{storeId}/desc")
//    public ResponseEntity<Response> getProductByStoreSortDescending(@PathVariable("storeId") Long storeId) {
//        return storeService.getProductByStoreSortByPriceDesc(storeId);
//    }

    private User getCurrentStore() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
