package br.gov.mg.uberlandia.decserver.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gov.mg.uberlandia.decserver.dto.ServidorDTO;
import br.gov.mg.uberlandia.decserver.entity.RelServidoresEntity;
import br.gov.mg.uberlandia.decserver.repository.RelServidoresRepository;

@Service
public class ServidorService {

    @Autowired
    private RelServidoresRepository relServidoresRepository;

    public ServidorDTO encontrarServidorPorOidServidor(Long oidSevidor) {

        RelServidoresEntity servidorEntity = relServidoresRepository.findByOidServidor(oidSevidor);
        
        if (servidorEntity != null) {
            ServidorDTO servidorDTO = new ServidorDTO();
            servidorDTO.setIdSecretaria(servidorEntity.getIdSecretaria());
            servidorDTO.setOidServidor(servidorEntity.getOidServidor());
            servidorDTO.setNrCpfServidor(servidorEntity.getNrCpfServidor());
            servidorDTO.setNmServidor(servidorEntity.getNmServidor());
            servidorDTO.setIdCargo(servidorEntity.getIdCargo());
            return servidorDTO;
        } else {
            return null;
        }
    }

    public ServidorDTO encontrarServidorPorCpf(String cpf) {

        Long cpfServidorLong = Long.parseLong(cpf);

        RelServidoresEntity servidorEntity = relServidoresRepository.findByNrCpfServidor(cpfServidorLong);
        
        if (servidorEntity != null) {
            ServidorDTO servidorDTO = new ServidorDTO();
            servidorDTO.setIdSecretaria(servidorEntity.getIdSecretaria());
            servidorDTO.setOidServidor(servidorEntity.getOidServidor());
            servidorDTO.setNrCpfServidor(servidorEntity.getNrCpfServidor());
            servidorDTO.setNmServidor(servidorEntity.getNmServidor());
            servidorDTO.setIdCargo(servidorEntity.getIdCargo());
            return servidorDTO;
        } else {
            return null;
        }
    }

    public List<ServidorDTO> listarServidoresPorIdSecretaria(Long idSecretaria) {
        
        List<RelServidoresEntity> servidoresEntity = relServidoresRepository.findByIdSecretaria(idSecretaria);
        List<ServidorDTO> servidoresDTO = new ArrayList<>();

        for (RelServidoresEntity servidorEntity : servidoresEntity) {
            ServidorDTO servidorDTO = new ServidorDTO();
            servidorDTO.setIdSecretaria(servidorEntity.getIdSecretaria());
            servidorDTO.setOidServidor(servidorEntity.getOidServidor());
            servidorDTO.setNrCpfServidor(servidorEntity.getNrCpfServidor());
            servidorDTO.setNmServidor(servidorEntity.getNmServidor());
            servidorDTO.setIdCargo(servidorEntity.getIdCargo());

            servidoresDTO.add(servidorDTO);
        }

        return servidoresDTO;
    }
}



