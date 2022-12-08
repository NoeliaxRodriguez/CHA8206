package com.grupo06.controller;

import java.util.List;

import com.grupo06.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.grupo06.domains.Mensaje;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class ChatController {

	@Autowired
    MensajeRepository mensajeRepository;

	@RequestMapping(value = "/mensajes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getConversaciones() {

		try {
			List<Mensaje> entityList = mensajeRepository.findAll();

			return new ResponseEntity<>(entityList, HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@RequestMapping(value = "/usuariomensajes/{emailori}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getMensajesUsuario(@PathVariable(value = "emailori", required = true) String emailori) {

		try {
			
			List<Mensaje> entityList = mensajeRepository.findByEmailori(emailori);

			return new ResponseEntity<>(entityList, HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/mensaje", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> sendMensaje(@RequestBody Mensaje mensaje) {

		try {

			mensajeRepository.save(mensaje);
			return new ResponseEntity<Void>(HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@RequestMapping(value = "/conversaciones/{emailori}/{emaildest}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getConversaciones(@PathVariable(value = "emailori", required = true) String emailori,
			@PathVariable(value = "emaildest", required = true) String emaildest) {

		try {

			List<Mensaje> entityList = mensajeRepository.findByEmailoriAndEmaildest(emailori, emaildest);

			if (entityList.isEmpty()) {
				
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
				
			} else {
	
				return new ResponseEntity<>(entityList, HttpStatus.OK);
			}

		} catch (Exception e) {

			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value = "/mensajeslike/{expression}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getMensajesLike(@PathVariable(value = "expression", required = true) String expresion) {

		try {
			List<Mensaje> entityList = mensajeRepository.findByMensajeLikeOrderByMensajeAsc(expresion);

			return new ResponseEntity<>(entityList, HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}


}
