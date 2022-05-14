package com.pedroanjos.cursomc.resources;

import com.pedroanjos.cursomc.dto.ProductDTO;
import com.pedroanjos.cursomc.entities.Product;
import com.pedroanjos.cursomc.resources.utils.URL;
import com.pedroanjos.cursomc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping()
	public ResponseEntity<Page<ProductDTO>> findPage(String name, String categories, Pageable pageable){
		String nameDecoded = URL.decodeParam(name);
		List<Long> ids = URL.decodeLongList(categories);
		Page<Product> list = service.search(nameDecoded, ids, pageable);
		Page<ProductDTO> dto = list.map(x -> new ProductDTO(x));
		return ResponseEntity.ok().body(dto);
	}
	
}
