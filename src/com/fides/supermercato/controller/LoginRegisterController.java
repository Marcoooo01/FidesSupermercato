package com.fides.supermercato.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fides.supermercato.model.PersonaDBO;
import com.fides.supermercato.repository.PersonaRepository;
import com.fides.supermercato.services.PersonaService;

@Controller
public class LoginRegisterController{

	@Autowired
	PersonaService personaService;

	@Autowired
	PersonaDBO personaDBO;

	@Autowired
	PersonaRepository personaRepository;

	@PostMapping(value = "/loginProcess")
	public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id) throws SQLException {
		ModelAndView mav = null;
		mav = new ModelAndView("welcome");
		personaDBO = personaService.getPersona(id);
		System.out.println(id);
		if (personaDBO.getId_ruolo() == 1) {
			mav = new ModelAndView("MenuCliente");
		}else if (personaDBO.getId_ruolo() == 2) {
			mav = new ModelAndView("MenuProprietario");
		}else if (personaDBO.getId_ruolo() == 3) {
			mav = new ModelAndView("MenuLadro");
		}
		return mav;
	}

//	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
//	public ModelAndView registerProcess(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam("personaDBO") PersonaDBO personaDBO) {
//		//METODO CHE PRENDE ID MASSIMO
//		//personaDBO.setId(metodoSopra + 1)
//		personaRepository.save(personaDBO);
//		return null;
//	}

}