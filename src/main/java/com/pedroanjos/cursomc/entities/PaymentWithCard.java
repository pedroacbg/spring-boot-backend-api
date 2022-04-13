package com.pedroanjos.cursomc.entities;

import javax.persistence.Entity;

import com.pedroanjos.cursomc.entities.enums.PaymentStatus;

@Entity
public class PaymentWithCard extends Payment {
	private static final long serialVersionUID = 1L;
	
	private Integer installmentsNumber;
	
	public PaymentWithCard() {
	}

	public PaymentWithCard(Long id, PaymentStatus status, Order order, Integer installmentsNumber) {
		super(id, status, order);
		this.installmentsNumber = installmentsNumber;
	}

	public Integer getInstallmentsNumber() {
		return installmentsNumber;
	}

	public void setInstallmentsNumber(Integer installmentsNumber) {
		this.installmentsNumber = installmentsNumber;
	}
}
