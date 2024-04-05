package store.service.service;

import org.springframework.stereotype.Service;

import store.service.entity.Manufacturer;
import store.service.repository.ManufactureRepository;

@Service
public class ManufactureService {
	private  ManufactureRepository  mfcRepository;

	public ManufactureService(ManufactureRepository mfcRepository) {
		super();
		mfcRepository = mfcRepository;
	}
	
	
	public boolean addMFc (Manufacturer mfc) {
		mfcRepository.save(mfc);
		return true;
		
	}
	

}
