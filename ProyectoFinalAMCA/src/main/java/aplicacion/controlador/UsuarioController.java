package aplicacion.controlador;
 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping; 
 
import aplicacion.modelo.Usuario; 
import aplicacion.persistencia.UsuarioDAO;
import aplicacion.persistencia.UsuarioRepo;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	private UsuarioDAO crudUsuario = new UsuarioDAO( ); 

	
	@GetMapping(value={"","/"})
	String Usuarios(Model model) {

		
		List<Usuario> lista = usuarioRepo.findAll();
		model.addAttribute("usuarios",lista );
		model.addAttribute("nuevoUsuario",new Usuario() );
		model.addAttribute("usuarioaEditar", new Usuario());    
		model.addAttribute("nombreNuevo", "");    
		return "usuarios";
	}

	@GetMapping(value="/add/{nombre}")
	public String insertarUsuario(Model model, @PathVariable String usu ) {
		 
		System.out.println("Insertando Usuario nuevo: "+usu);		
		
		return "redirect:/usuarios";
	}
	
	
	@PostMapping("/add")
	public String addUsuario(@ModelAttribute("nuevoUsuario") Usuario usuario, BindingResult bindingResult) {
		usuario.setRol(1);
		usuarioRepo.save(usuario);
		System.out.println("Insertando Usuario nuevo: "+usuario.getNombreApellidos());	
				
		return "redirect:/usuarios";	
	}
	
	@PostMapping("/edit/{id}")
	public String editarUsuario(@PathVariable Integer id, @ModelAttribute("usuarioaEditar") Usuario usuario,BindingResult bindingResult) {
		
		Usuario uActualizar = usuarioRepo.findById(id).get();
		if(usuario.getUsu() != "") {
			uActualizar.setUsu(usuario.getUsu());
		}
		if(usuario.getNombreApellidos() != "") {
			uActualizar.setNombreApellidos(usuario.getNombreApellidos());
		}
		if(usuario.getDireccion() != "") {
			uActualizar.setDireccion(usuario.getDireccion());
		}
		if(usuario.getEmail() != "") {
			uActualizar.setEmail(usuario.getEmail());
		}
		
		usuarioRepo.save(uActualizar);
		
		return "redirect:/usuarios";
		 
	}
	
	  
	
	@GetMapping({"/{id}"})
	public String obtenerUsuario(Model model, @PathVariable Integer id) {
	 
		model.addAttribute("usuario", crudUsuario.buscaUsuario(id));
		
		return "usuario";
	}
	
	@GetMapping({"/buscar/{nombre}"})
	public String obtenerUsuario(@PathVariable String nombre) {
		return "usuario";
	}
	
	@GetMapping({"/delete/{id}"})
	public String borrarUsuario(@PathVariable Integer id) {
		
		usuarioRepo.deleteById(id);
		
		return "redirect:/usuarios";
		 
	}
	
}
