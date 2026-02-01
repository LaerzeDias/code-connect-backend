package br.com.laerze.codeconnect.controller;

import br.com.laerze.codeconnect.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<String>> buscarTags() {
        return ResponseEntity.ok(tagService.buscarTags());
    }
}
