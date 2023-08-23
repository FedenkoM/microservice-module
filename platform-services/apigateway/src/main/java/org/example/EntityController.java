package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntityController {
    @GetMapping("/entity/{id}")
    public Entity findById(@PathVariable("id") long id) {
        return new Entity(id, "StringName - " + id);
    }
}
