package com.mng.java.service;

import java.util.List;
import java.util.Map;

import com.mng.java.model.Product;

public interface ProductService {
	Map<Integer,Product> listAllProducts();
	Product getProductById(Integer id);
	void saveProduct(Integer id,Product product);
	void deleteProduct(Integer id);
	boolean exists(Integer id);
	List<Product> getProductList();
}
