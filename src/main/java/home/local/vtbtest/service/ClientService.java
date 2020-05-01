package home.local.vtbtest.service;

import home.local.vtbtest.dto.ClientDto;
import home.local.vtbtest.dto.DocumentDto;
import home.local.vtbtest.entity.Client;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.mapper.ClientMapper;
import home.local.vtbtest.mapper.DocumentMapper;
import home.local.vtbtest.repository.ClientRepository;
import home.local.vtbtest.repository.ClientSearchRepository;
import home.local.vtbtest.repository.DocumentRepository;
import home.local.vtbtest.util.SearchCriteria;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    @NonNull
    private ClientRepository clientRepository;
    @NonNull
    private DocumentRepository documentRepository;

    @NonNull
    private ClientMapper clientMapper;
    @NonNull
    private DocumentMapper documentMapper;
    @NonNull
    private ClientSearchRepository clientSearchRepository;

    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream().map(client -> clientMapper.toDto(client)).collect(Collectors.toList());
    }

    public Optional<ClientDto> findOne(Long id) {
//        return clientRepository.findById(id).orElseThrow(() -> new Exception("клиент с id= " + id + "отсутствует"));
        return clientRepository.findById(id).map(client -> clientMapper.toDto(client));
    }

    public Client save(ClientDto clientDto) {
        final Client client = clientMapper.toEntity(clientDto);
        return clientRepository.save(client);
    }

    public List<DocumentDto> getClientDocs(Long idClient) throws Exception {
        final Client client = clientRepository.findById(idClient).orElseThrow(() -> new Exception("клиент с id= " + idClient + "отсутствует"));
        final List<Document> documentRepositoryAllByClient = documentRepository.findAllByClient(client);
        return documentRepositoryAllByClient.stream().map(document -> documentMapper.toDto(document)).collect(Collectors.toList());
    }

    public void delete(Long idClient) throws Exception {
        final Client client = clientRepository.findById(idClient).orElseThrow(() -> new Exception("клиент с id= " + idClient + "отсутствует"));
        final List<Document> documentRepositoryAllByClient = documentRepository.findAllByClient(client);
        if (documentRepositoryAllByClient.isEmpty()) clientRepository.deleteById(idClient);
    }

    public Long idByInn(String inn) {
        final Optional<Client> clientByInn = clientRepository.findByInn(inn);
        Long id = clientByInn.isPresent() ? clientByInn.get().getId() : null;
        return id;
    }

    public List<ClientDto> searchClient(List<SearchCriteria> params) {
        return clientSearchRepository.searchClient(params).stream().map(client -> clientMapper.toDto(client)).collect(Collectors.toList());
    }

}
