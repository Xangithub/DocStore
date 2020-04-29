package home.local.vtbtest.controller;

import home.local.vtbtest.dto.ClientDto;
import home.local.vtbtest.dto.DocumentDto;
import home.local.vtbtest.entity.Client;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/***
 CRUD операции (удаление возможно только если нет размещённых документов)

 ·         Поиск по атрибутам

 ·         Получение списка документов клиента
 */

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;


    @PostMapping("/create")
    Client create(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PostMapping("/update")
    Client update(@RequestBody Client client) {
        return clientService.save(client);
    }

    @GetMapping("/all")
    List<ClientDto> readAll() {
        return clientService.findAll();
    }

    @GetMapping("/client/{id}")
    Optional<ClientDto> readOne(@PathVariable Long id) {
        return clientService.findOne(id); //todo проверить стоит ли возвращать опшенал
    }

    @GetMapping("/{id}/docs")
    List<DocumentDto> getClientDocs(@PathVariable Long id) throws Exception {
        return clientService.getClientDocs(id);
    }


    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) throws Exception {
        clientService.delete(id);
    }

    @GetMapping("/client/inn/{inn}")
    ResponseEntity<Long> idByInn(@PathVariable String inn) {
        final Long id = clientService.idByInn(inn);
        if (id == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(id);
    }
}
