package home.local.vtbtest.service;

import home.local.vtbtest.entity.Client;
import home.local.vtbtest.repository.ClientRepository;
import home.local.vtbtest.repository.DocumentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    @NonNull
    private ClientRepository clientRepository;
    @NonNull
    private DocumentRepository documentRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findOne(Long id) {
//        return clientRepository.findById(id).orElseThrow(() -> new Exception("клиент с id= " + id + "отсутствует"));
        return clientRepository.findById(id);
    }

    public Client save(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    public void delete(Integer id) {
//        final int i = documentRepository.countDocumentsByClient_Id(id);
//        if(i ==0)  clientRepository.deleteById(id);
    }

    public Long idByInn(String inn) {
        final Optional<Client> clientByInn = clientRepository.findByInn(inn);
        Long id= clientByInn.isPresent() ? clientByInn.get().getId() : null;
        return id;
    }
}
