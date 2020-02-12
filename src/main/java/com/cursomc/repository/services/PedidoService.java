package com.cursomc.repository.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.repositories.ItemPedidoRepository;
import com.cursomc.repositories.PagamentoRepository;
import com.cursomc.repositories.PedidoRepository;
import com.cursomc.repository.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public Pedido findById(Long id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(id));
	}
	
	public Pedido insert(Pedido obj) {
		
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pag = (PagamentoComBoleto) obj.getPagamento();
			Calendar cal = Calendar.getInstance();
			cal.setTime(obj.getInstante());
			cal.add(Calendar.DAY_OF_MONTH, 7);
			pag.setDataVencimento(cal.getTime());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido p:obj.getItens()) {
			p.setDesconto(0.0);
			p.setPreco(produtoService.findById(p.getProduto().getId()).getPreço());
			p.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
	
}
