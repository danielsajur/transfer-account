package br.com.cvc.banktransfer.infra.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.com.cvc.banktransfer.domain.TransferBookingRepository;
import br.com.cvc.banktransfer.domain.entity.Transfer;

@Component
public class TransferBookingRepositoryImpl implements TransferBookingRepository {

	private final Map<String, List<Transfer>> map = new HashMap<>();
		
	public void add(Transfer transfer) {
		if(!map.containsKey(transfer.getOriginAccount())) {
			map.put(transfer.getOriginAccount(), new ArrayList<>());
		}
		map.get(transfer.getOriginAccount()).add(transfer);
	}
	
	public List<Transfer> getBy(String originAccount) {
		return map.get(originAccount);
	}

	@Override
	public List<Transfer> getAll() {
		final List<Transfer> transfers = new ArrayList<>();
		map.values().forEach(list -> transfers.addAll(list));
		return transfers;
	}
	
}
