package com.cursomc.repository.services;

import org.springframework.mail.SimpleMailMessage;

import com.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmation(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
