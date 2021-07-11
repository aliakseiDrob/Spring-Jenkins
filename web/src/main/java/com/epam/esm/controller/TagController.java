package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TagModel;
import com.epam.esm.model.assembler.TagModelAssembler;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;
    private final TagModelAssembler tagModelAssembler;

    @GetMapping
    public PagedModel<TagModel> getAll(Pageable pageable) {
        return pagedResourcesAssembler.toModel(tagService.findAll(pageable),
                tagModelAssembler);
    }

    @GetMapping("/{id}")
    public TagModel getTag(@PathVariable Long id) {
       return  tagModelAssembler.toModel(tagService.findById(id));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public TagModel save(@RequestBody TagDto dto) {
        return tagModelAssembler.toModel(tagService.save(dto));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.delete(id);
    }
}
