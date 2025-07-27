package com.skyfleet.rentals.service;

import com.skyfleet.rentals.custom_exceptions.ApiException;
import com.skyfleet.rentals.dto.UndertakingRequestDTO;
import com.skyfleet.rentals.dto.UndertakingResponseDTO;
import com.skyfleet.rentals.entity.Undertaking;
import com.skyfleet.rentals.repository.UndertakingRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UndertakingServiceImpl implements UndertakingService {

    
    private UndertakingRepository undertakingRepository;
    
    private ModelMapper modelMapper;

    @Override
    public UndertakingResponseDTO saveUndertaking(UndertakingRequestDTO undertaking) {
    	
    	Undertaking entity= modelMapper.map(undertaking, Undertaking.class);
    	
    	undertakingRepository.save(entity);
    	
    	
        return modelMapper.map(entity, UndertakingResponseDTO.class);
    }

    @Override
    public List<UndertakingResponseDTO> getAllUndertakings() {
    	
    	
    	
        return undertakingRepository.findAll().stream().map(e-> modelMapper.map(e, UndertakingResponseDTO.class)).toList();
    }

    @Override
    public UndertakingResponseDTO getUndertakingById(Long id) {
    	
    Undertaking entity=	undertakingRepository.findById(id).orElseThrow(()->new ApiException("Undertaking Not Found"));
    	
        return modelMapper.map(entity, UndertakingResponseDTO.class);
    }

    @Override
    public void deleteUndertaking(Long id) {
   
        undertakingRepository.deleteById(id);
    }
}