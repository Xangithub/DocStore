package home.local.vtbtest.service;

import home.local.vtbtest.entity.Client;
import home.local.vtbtest.repository.ClientRepository;
import home.local.vtbtest.repository.DocumentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Client findOne(Long id) throws Exception {
        return clientRepository.findById(id).orElseThrow(() -> new Exception("клиент с id= " + id + "отсутствует"));
    }

    public Client save(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    public void delete(Integer id) {
//        final int i = documentRepository.countDocumentsByClient_Id(id);
//        if(i ==0)  clientRepository.deleteById(id);
    }
}
