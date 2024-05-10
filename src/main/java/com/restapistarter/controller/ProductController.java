package com.restapistarter.controller;

import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapistarter.model.Product;
import com.restapistarter.repository.ProductRepository;


@RestController
@RequestMapping("/api/products/")
public class ProductController {

    @Autowired
    ProductRepository repo;

    @GetMapping("/")
    public Iterable<Product> list() {
		return repo.findAll();
    }

    //ritorna oggetti
	@GetMapping("/service-data")
    public Product[] getServiceData(Model model) {
		Iterable<Product> iterableProducts = repo.findAll();
        // Converti l'Iterable in un array di Prodotto[]
    	Product[] products = StreamSupport.stream(iterableProducts.spliterator(), false).toArray(Product[]::new);
        for (Product product : products) {
            System.out.println("ID: " + product.getId() + ", Nome: " + product.getName());
        }
        return products;
    }


	@GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
		return repo.findById(id).get();
    }


	@PostMapping("/add")
	public String add(@RequestBody Product p) {
		repo.save(p);
		return "redirect:/api/prodotti/";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute("datiProdotto") Product p) {
		repo.save(p);
		return "redirect:/";
	}

	@PatchMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        try {
            if (!repo.existsById(id)) {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }

            Product existingProduct = repo.findById(id).get();

            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescrizione() != null) {
                existingProduct.setDescrizione(updatedProduct.getDescrizione());
            }
            if (updatedProduct.getCategoria() != null) {
                existingProduct.setCategoria(updatedProduct.getCategoria());
            }
            if (updatedProduct.getPrezzo() != 0.0) {
                existingProduct.setPrezzo(updatedProduct.getPrezzo());
            }

            repo.save(existingProduct);

            return new ResponseEntity<>("Prodotto aggiornato con successo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'aggiornamento del prodotto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer idProduct) {
		if (idProduct != null) {
			Optional<Product> pOptional = repo.findById(idProduct);
			if (pOptional.isPresent()) {
				Product p = pOptional.get();
				repo.delete(p);
			} else {
				
			}
		}
		return "redirect:/product";
	}

    
}
