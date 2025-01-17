package home.local.vtbtest.controller;

import home.local.vtbtest.dto.ClientDto;
import home.local.vtbtest.dto.DocumentDto;
import home.local.vtbtest.entity.Client;
import home.local.vtbtest.exception.EntityWithIdNotFound;
import home.local.vtbtest.service.ClientService;
import home.local.vtbtest.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @GetMapping("/all")
    public List<ClientDto> findAll(@RequestParam(value = "search", required = false) String search) {
        List<SearchCriteria> params = new ArrayList<>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1),
                        matcher.group(2), matcher.group(3)));
            }
        }
        return clientService.searchClient(params);
    }


    @PostMapping("/create")
    public Client create(@RequestBody @NotNull  ClientDto clientDto) {
        return clientService.save(clientDto);
    }

    @PostMapping("/update")
    public Client update(@RequestBody ClientDto clientDto) {
        return clientService.save(clientDto);
    }


    @GetMapping("/{id}")
    public Optional<ClientDto> readOne(@PathVariable Long id) {
        return clientService.findOne(id);
    }

    @GetMapping("/{id}/docs")
    public List<DocumentDto> getClientDocs(@PathVariable Long id) throws EntityWithIdNotFound {
        return clientService.getClientDocs(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) throws EntityWithIdNotFound {
        clientService.delete(id);
    }

}
