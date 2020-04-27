package home.local.vtbtest.controller;

import home.local.vtbtest.entity.Client;
import home.local.vtbtest.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

   private final ClientService clientService;

    @GetMapping("/all")
    List<Client> readAll() {
        return clientService.findAll();
    }

    @GetMapping("/client/{id}")
    Client readOne(@PathVariable Long id) throws Exception {
        return clientService.findOne(id);
    }

    @PostMapping("/create")
    Client save(@RequestBody Client client) throws Exception {
        return clientService.save(client);
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) throws Exception {
        clientService.delete(id);
    }
}
