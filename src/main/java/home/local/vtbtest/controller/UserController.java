package home.local.vtbtest.controller;

import home.local.vtbtest.dto.UserDto;
import home.local.vtbtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

   private final UserService userService;

    @GetMapping("/read/{id}")
    ResponseEntity<UserDto> read(@PathVariable Long id)  {
        return userService.getUser(id)
                .map( user -> ResponseEntity.ok(user))
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping("/all")
    List<UserDto> readAll()  {
        return userService.getAll();
              /*  .map( user -> ResponseEntity.ok(user))
                .orElseGet( () -> ResponseEntity.notFound().build() );*/
    }

    @PostMapping("/create")
    ResponseEntity<Long> createUser(@RequestBody UserDto user)  {
        final Long saveUserId = userService.saveUser(user);
        return ResponseEntity.ok(saveUserId);
    }

}