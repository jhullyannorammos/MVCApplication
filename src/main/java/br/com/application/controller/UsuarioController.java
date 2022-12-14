package br.com.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.application.model.security.GrupoModel;
import br.com.application.model.security.UsuarioModel;
import br.com.application.service.GrupoService;
import br.com.application.service.UsuarioService;

@Controller
@RequestMapping("/usuario") 
public class UsuarioController {
	

	@Autowired
	private GrupoService grupoService;
	
	@Autowired 
	private UsuarioService usuarioService;

	@RequestMapping(value="/novoCadastro", method= RequestMethod.GET)	
	public ModelAndView novoCadastro(Model model) {	
		model.addAttribute("grupos", grupoService.consultarGrupos());
		model.addAttribute("usuarioModel", new UsuarioModel());
	    return new ModelAndView("novoCadastro");
	}

	@RequestMapping(value="/salvarUsuario", method= RequestMethod.POST)
	public ModelAndView salvarUsuario(@ModelAttribute 
								@Valid UsuarioModel usuarioModel, 
								final BindingResult result,
								Model model,
								RedirectAttributes redirectAttributes){

		if(result.hasErrors()){

			List<GrupoModel> gruposModel =grupoService.consultarGrupos();			
			gruposModel.forEach(grupo ->{
				
				if(usuarioModel.getGrupos() != null && usuarioModel.getGrupos().size() >0){
					
					usuarioModel.getGrupos().forEach(grupoSelecionado->{
						if(grupoSelecionado!= null){
							if(grupo.getCodigo().equals(grupoSelecionado))
								grupo.setChecked(true);
						}					
					});				
				}
				
			});
			
			model.addAttribute("grupos", gruposModel);
			model.addAttribute("usuarioModel", usuarioModel);
			return new ModelAndView("novoCadastro");	
		}
		else{
			usuarioService.salvarUsuario(usuarioModel);
			
		}
		ModelAndView modelAndView = new ModelAndView("redirect:/usuario/novoCadastro");
		redirectAttributes.addFlashAttribute("msg_resultado", "Registro salvo com sucesso!");
		return modelAndView;
	}

	@RequestMapping(value="/consultar", method= RequestMethod.GET)	
	public ModelAndView consultar(Model model) {

		model.addAttribute("usuariosModel", this.usuarioService.consultarUsuarios());
	    return new ModelAndView("consultarCadastros");
	}

	@RequestMapping(value="/excluir", method= RequestMethod.POST)
	public ModelAndView excluir(@RequestParam("codigoUsuario") Long codigoUsuario){
		ModelAndView modelAndView = new ModelAndView("redirect:/usuario/consultar");
		this.usuarioService.excluir(codigoUsuario);
		return modelAndView;
	}

	@RequestMapping(value="/editarCadastro", method= RequestMethod.GET)		
	public ModelAndView editarCadastro(@RequestParam("codigoUsuario") Long codigoUsuario, Model model) {
				
		/*CONSULTA OS GRUPOS CADASTRADOS*/
		List<GrupoModel> gruposModel =grupoService.consultarGrupos();			
		
		/*CONSULTA O USU??RIO PELO C??DIGO*/
		UsuarioModel usuarioModel = this.usuarioService.consultarUsuario(codigoUsuario);
		
		/*DEIXA SELECIONADO OS GRUPOS CADASTRADOS PARA O USU??RIO*/
		gruposModel.forEach(grupo ->{
			
			usuarioModel.getGrupos().forEach(grupoCadastrado->{
				
				if(grupoCadastrado!= null){
					if(grupo.getCodigo().equals(grupoCadastrado))
						grupo.setChecked(true);
				}					
			});				
			
		});
		
		
		/*ADICIONANDO GRUPOS PARA MOSTRAR NA P??GINA(VIEW)*/
		model.addAttribute("grupos", gruposModel);
		
		/*ADICIONANDO INFORMA????ES DO USU??RIO PARA MOSTRAR NA P??GINA(VIEW)*/
		model.addAttribute("usuarioModel", usuarioModel);
		
		/*CHAMA A VIEW /src/main/resources/templates/editarCadastro.html*/
	    return new ModelAndView("editarCadastro");
	}
	
	/***
	 * SALVA AS ALTERA????ES REALIZADAS NO CADASTRO DO USU??RIO
	 * @param usuarioModel
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/salvarAlteracao", method= RequestMethod.POST)
	public ModelAndView salvarAlteracao(@ModelAttribute 
								@Valid 
								UsuarioModel usuarioModel, 
								final BindingResult result,
								Model model,
								RedirectAttributes redirectAttributes){
		
		boolean isErroNullCampos = false;
		
		/*AQUI ESTAMOS VERIFICANDO SE TEM ALGUM CAMPO QUE N??O EST?? PREENCHIDO,
		 * MENOS O CAMPO DA SENHA, POIS SE O USU??RIO N??O INFORMAR VAMOS MANTER A
		 * SENHA J?? CADASTRADA*/
		for (FieldError fieldError : result.getFieldErrors()) {
			if(!fieldError.getField().equals("senha")){
				isErroNullCampos = true;	
			}	
		}
		
		/*SE ENCONTROU ERRO DEVEMOS RETORNAR PARA A VIEW PARA QUE O 
		 * USU??RIO TERMINE DE INFORMAR OS DADOS*/
		if(isErroNullCampos){
			
			List<GrupoModel> gruposModel =grupoService.consultarGrupos();			
			
			gruposModel.forEach(grupo ->{
				
				if(usuarioModel.getGrupos() != null && usuarioModel.getGrupos().size() >0){
					
					/*DEIXA CHECADO OS GRUPOS QUE O USU??RIO SELECIONOU*/
					usuarioModel.getGrupos().forEach(grupoSelecionado->{
						
						if(grupoSelecionado!= null){
							if(grupo.getCodigo().equals(grupoSelecionado))
								grupo.setChecked(true);
						}					
					});				
				}
				
			});
			
			/*ADICIONANDO GRUPOS PARA MOSTRAR NA P??GINA(VIEW)*/
			model.addAttribute("grupos", gruposModel);
			
			/*ADICIONANDO O OBJETO usuarioModel PARA MOSTRAR NA P??GINA(VIEW) AS INFORMA????ES DO USU??RIO*/
			model.addAttribute("usuarioModel", usuarioModel);
			
			/*RETORNANDO A VIEW*/
			return new ModelAndView("editarCadastro");	
		}
		else{
			
			/*SALVANDO AS INFORMA????ES ALTERADAS DO USU??RIO*/
			usuarioService.alterarUsuario(usuarioModel);
			
		}
		
		/*AP??S SALVAR VAMOS REDIRICIONAR O USU??RIO PARA A P??GINA DE CONSULTA*/
		ModelAndView modelAndView = new ModelAndView("redirect:/usuario/consultar");
				
		/*RETORNANDO A VIEW*/
		return modelAndView;
	}
}
