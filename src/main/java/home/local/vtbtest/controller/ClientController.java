package home.local.vtbtest.controller;

import home.local.vtbtest.dto.ClientDto;
import home.local.vtbtest.entity.Client;
import home.local.vtbtest.mapper.ClientMapper;
import home.local.vtbtest.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

   private final ClientService clientService;
   private final ClientMapper clientMapper;

    @GetMapping("/all")
    List<Client> readAll() {
        return clientService.findAll();
    }

    @GetMapping("/client/{id}")
    Optional<ClientDto> readOne(@PathVariable Long id) throws Exception {
        final Optional<Client> clientOpt = clientService.findOne(id);
         return clientOpt.map(client -> clientMapper.toDto(client));
    }

    @GetMapping("/client/inn/{inn}")
    ResponseEntity<Long> idByInn(@PathVariable String inn) {
        final Long id = clientService.idByInn(inn);
        if (id == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(id);
    }

    @PostMapping("/create")
    Client save(@RequestBody Client client)  {
        return clientService.save(client);
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        clientService.delete(id);
    }
}
