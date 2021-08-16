package br.com.cvc.banktransfer.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.banktransfer.domain.TransferService;
import br.com.cvc.banktransfer.domain.entity.Transfer;
import br.com.cvc.banktransfer.infra.exception.TransferNotFoundException;
import br.com.cvc.banktransfer.infra.response.Response;


@RestController
@Configuration
@RequestMapping(TransferController.TRANSFERS)
public class TransferController {

	public static final String TRANSFERS = "/transfers";

	@Autowired
	private TransferService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@PostMapping
	public ResponseEntity<Response> add(@RequestBody TransferDTO request) throws Exception {
		final Transfer transfer = service.add(request.getOriginAccount(), request.getDestinationAccount(), request.getTransferDate(), request.getValue());
		
		final String message = messageSource.getMessage("success", null, Locale.getDefault());
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response(message, Arrays.asList(transfer)));
	}

	@GetMapping(path = "/{originAccount}")
	public ResponseEntity<Response> getBy(@PathVariable String originAccount) throws Exception {
		List<Transfer> transfers = service.getBy(originAccount);
		if(CollectionUtils.isEmpty(transfers)) {
			throw new TransferNotFoundException();
		}
		return ResponseEntity.ok(new Response(transfers));
	}
	
	@GetMapping
	public ResponseEntity<Response> getAll() throws Exception {
		List<Transfer> transfers = service.getAll();
		if(CollectionUtils.isEmpty(transfers)) {
			throw new TransferNotFoundException();
		}
		return ResponseEntity.ok(new Response(transfers));
	}

}
