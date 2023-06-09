package com.ipartek.formacion.springmvc.presentacion.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ipartek.formacion.springmvc.entidades.Producto;
import com.ipartek.formacion.springmvc.servicios.ProductoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/producto")
public class ProductoController {
	@Autowired
	private ProductoService servicio;
	
	@GetMapping
	public String mostrarFormulario(Producto producto) {
		return "producto";
	}
	
	@PostMapping
	public String recepcionFormulario(@Valid Producto producto, BindingResult bindingResult) {
		System.out.println(bindingResult);
		System.out.println(producto);
		
		if(bindingResult.hasErrors()) {
			return "producto";
		}
		
		servicio.guardar(producto);
		
		return "redirect:/";
	}
}
