package com.mng.java.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import com.mng.java.model.Product;
import com.mng.java.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/product")
@Api(value="online store", description="Operations pertaining to products in Online Store")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	//@RequestMapping(value = "/list", method= RequestMethod.GET)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
	@ApiOperation(value="View a list of available products", response=List.class)
	@GetMapping(value = "/list")
	public ResponseEntity<Map<Integer,Product>> list() {
		Map<Integer, Product> productList = productService.listAllProducts();
		
		if (productList == null || productList.isEmpty()){
			System.out.println("no product found");
            return new ResponseEntity<Map<Integer,Product>>(HttpStatus.NO_CONTENT);
        }
		
		System.out.println("productService.listAllProducts() result: " + productList);
		return new ResponseEntity<Map<Integer,Product>>(productList, HttpStatus.OK);
	}

	//@RequestMapping(value = "/show/{id}", method= RequestMethod.GET)
	@ApiOperation(value = "Search a product with an ID",response = Product.class)
	@GetMapping(value = "/show/{id}")
	public ResponseEntity<Product> showProduct(@PathVariable Integer id) {
		Product product = productService.getProductById(id);
		
		if (product == null){
			System.out.println("no product found");
            return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
        }
		
		System.out.println("productService.getProductById() result: " + product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	//@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(value = "Add a product")
	@PostMapping(value = "/add")
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		if(productService.exists(product.getId())) {
			System.out.println("a product with name " + product.getId() + " already exists");
            return new ResponseEntity<String>("alreday exixts", HttpStatus.CONFLICT);
		}
		productService.saveProduct(product.getId(),product);
		System.out.println("productService.saveProduct() result: " + productService.listAllProducts());
		return new ResponseEntity<String>("Product saved successfully", HttpStatus.CREATED);
	}

	//@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update a product")
	@PutMapping(value = "/update/{id}")
	public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        Product productObj= productService.getProductById(id);
		
		if (productObj == null){
			System.out.println("product with id {} not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
		productObj.setProductId(product.getProductId());
		productObj.setDescription(product.getDescription());
		productObj.setImageUrl(product.getImageUrl());
		productObj.setPrice(product.getPrice());
		productService.saveProduct(product.getId(),productObj);
		
		System.out.println("productService.updateProduct() result: " + productService.listAllProducts());
		return new ResponseEntity("Product updated successfully", HttpStatus.OK);
	}

	//@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a product")
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
		System.out.println("Before productService.delete() result: " + productService.listAllProducts());
		System.out.println("Products list size: " + productService.listAllProducts().size());
		
		Product productObj= productService.getProductById(id);
		if(productObj == null) {
			System.out.println("Unable to delete with Id " + id + " not found");
            return new ResponseEntity<String>("Unable to delete with Id",HttpStatus.NOT_FOUND);
		}
		productService.deleteProduct(id);
		System.out.println("after productService.delete() result: " + productService.listAllProducts());
		System.out.println("Products list size: " + productService.listAllProducts().size());
		return new ResponseEntity<String>("Product deleted successfully", HttpStatus.OK);

	}
	
	@GetMapping(value="/getAll",produces= {"application/json"} )
	public List<Product> getProductList(){
		return productService.getProductList();
	}
}