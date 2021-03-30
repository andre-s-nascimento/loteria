/**
 * 
 */
package br.com.caelum.vraptor.controller;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.dao.UsuarioDAO;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.model.Usuario;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

/**
 * @author andre
 *
 */
@Path("cadastrar")
@Controller
public class CadastrarController {
	
	@Inject EntityManager em;
	@Inject Result result;
	@Inject UsuarioDAO usuarioDAO;
	@Inject Validator validator;
	
	@Get("")
	public void cadastrar() {
		
	}
	
	@IncludeParameters
	@Post("salvausuario")
	public void salvaUsuario(@Valid Usuario usuario, String confirmaSenha) {
//		result.include("usuario", usuario);
//		result.include("confirmaSenha", confirmaSenha);
//		
		
		boolean asSenhasSaoIguais = confirmaSenha.equals(usuario.getSenha());
		validator.addIf(!asSenhasSaoIguais, new SimpleMessage("confirmaSenha", "As senhas não coincidem"));
		//validar usuario
		// caso dê erro cadastrar e avisar os erros - DONE
		validator.onErrorRedirectTo(this).cadastrar();
		
		//salvar no banco - DONE
		usuarioDAO.insertOrUpdate(usuario);
					
		//encaminhar para o dashboard - DONE
		result.redirectTo(DashboardController.class).dashboard();
		
	}


}
