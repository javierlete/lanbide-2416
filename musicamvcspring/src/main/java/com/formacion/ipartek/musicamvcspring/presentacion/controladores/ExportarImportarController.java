package com.formacion.ipartek.musicamvcspring.presentacion.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.formacion.ipartek.musicamvcspring.servicios.ExportacionImportacionService;
import com.formacion.ipartek.musicamvcspring.servicios.ServiciosException;

@Controller
public class ExportarImportarController {
	@Autowired
	private ExportacionImportacionService servicio;
	
	@GetMapping("/admin/exportar")
	public String exportar() {
		return "admin/exportacion-importacion";
	}
	
	@PostMapping("/admin/exportar")
	public String exportarPost(String rutaFichero, Model modelo) {
		try {
			servicio.exportarUsuariosEnCsv(rutaFichero);
		} catch (ServiciosException e) {
			modelo.addAttribute("errorExportar", e.getMessage());
		}
		
		return "admin/exportacion-importacion";
	}
	
	@GetMapping("/admin/importar")
	public String importar() {
		return "admin/exportacion-importacion";
	}
	
	@PostMapping("/admin/importar")
	public String importarPost(String rutaFichero, Model modelo) {
		try {
			servicio.importarUsuariosDeCsv(rutaFichero);
		} catch (ServiciosException e) {
			modelo.addAttribute("errorImportar", e.getMessage());
		}
		
		return "admin/exportacion-importacion";
	}
}
