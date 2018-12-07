package com.mng.java.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.mng.java.model.Product;

@Repository
public class ProductServiceImpl implements ProductService {
	
	Map<Integer,Product> products = new HashMap<>();
	
	@PostConstruct
	public void generateProducts() {
		
		
		/*Product p1 = new Product();
		p1.setId(101);
		p1.setProductId("dell laptop");
		p1.setDescription("Dell product configuration info");
		p1.setImageUrl("http://localhost:8080/delImage");
		p1.setPrice(50000);
		p1.setVersion(5);
		
		products.put(101, p1);
		
		Product p2 = new Product();
		p2.setId(102);
		p2.setProductId("lenovo laptop");
		p2.setDescription("lenovo product configuration info");
		p2.setImageUrl("http://localhost:8080/lenovoImage");
		p2.setPrice(40000);
		p2.setVersion(4);
		
		products.put(102, p2);
		
		Product p3 = new Product();
		p3.setId(103);
		p3.setProductId("HP laptop");
		p3.setDescription("HP product configuration info");
		p3.setImageUrl("http://localhost:8080/hpImage");
		p3.setPrice(60000);
		p3.setVersion(2);
		
		products.put(103, p3);
		
		Product p4 = new Product();
		p4.setId(102);
		p4.setProductId("Acer laptop");
		p4.setDescription("Acer product configuration info");
		p4.setImageUrl("http://localhost:8080/AcerImage");
		p4.setPrice(35000);
		p4.setVersion(6);
		
		products.put(104, p4);
*/		
		
		Product p1 = new Product();
		p1.setId(001);
		p1.setProductId("mock laptop");
		p1.setDescription("mock product configuration info");
		p1.setImageUrl("mock://localhost:8080/delImage");
		p1.setPrice(50000);
		p1.setVersion(5);

		Product p2 = new Product();
		p2.setId(002);
		p2.setProductId("mock laptop");
		p2.setDescription("mock product configuration info");
		p2.setImageUrl("http://localhost:8080/lenovoImage");
		p2.setPrice(40000);
		p2.setVersion(4);
         
		products.put(001, p1);
		products.put(002, p2);
		 
	}

	@Override
	public List<Product> listAllProducts() {
		
		List<Product> list = new ArrayList<Product>();
		 // using values() for iteration over keys 
        for (Product obj : products.values()) {
        	list.add(obj);
        }
		
		return list;
	}

	@Override
	public Product getProductById(Integer id) {
		return products.get(id);
	}

	@Override
	public void saveProduct(Integer id, Product product) {
		products.put(id,product);
	}

	@Override
	public void deleteProduct(Integer id) {
		products.remove(id);
	}

	@Override
	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return products.containsKey(id);
	}

	@Override
	public List<Product> getProductList() {
		
	List<Product> al = new ArrayList<>();
		
		Product p1 = new Product();
		p1.setId(001);
		p1.setProductId("mock laptop");
		p1.setDescription("mock product configuration info");
		p1.setImageUrl("mock://localhost:8080/delImage");
		p1.setPrice(50000);
		p1.setVersion(5);
		
        al.add(p1);
        
		Product p2 = new Product();
		p2.setId(002);
		p2.setProductId("mock laptop");
		p2.setDescription("mock product configuration info");
		p2.setImageUrl("http://localhost:8080/lenovoImage");
		p2.setPrice(40000);
		p2.setVersion(4);
		
		al.add(p2);
		
		return al;
	}

}
