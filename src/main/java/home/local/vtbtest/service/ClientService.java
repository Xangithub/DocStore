package home.local.vtbtest.service;

import home.local.vtbtest.dto.ClientDto;
import home.local.vtbtest.entity.Client;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.mapper.ClientMapper;
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

    private final ClientMapper clientMapper;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<ClientDto> findOne(Long id) {
//        return clientRepository.findById(id).orElseThrow(() -> new Exception("клиент с id= " + id + "отсутствует"));
        return clientRepository.findById(id).map(client -> clientMapper.toDto(client));
    }

    public Client save(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    public List<Document> getClientDocs(Long idClient) throws Exception {
        final Client client = clientRepository.findById(idClient).orElseThrow(() -> new Exception("клиент с id= " + idClient + "отсутствует"));
        final List<Document> documentRepositoryAllByClient = documentRepository.findAllByClient(client);
        return documentRepositoryAllByClient;
    }

    public void delete(Long idClient) throws Exception {
        final List<Document> documentList = getClientDocs(idClient);
        if(documentList.isEmpty())  clientRepository.deleteById(idClient);
    }

    public Long idByInn(String inn) {
        final Optional<Client> clientByInn = clientRepository.findByInn(inn);
        Long id= clientByInn.isPresent() ? clientByInn.get().getId() : null;
        return id;
    }
}
