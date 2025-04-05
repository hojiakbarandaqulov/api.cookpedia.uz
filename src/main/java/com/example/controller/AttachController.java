package com.example.controller;

import com.example.dto.attach.AttachDTO;
import com.example.service.AttachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@RestController
@RequestMapping("/api/v1/attach")
@Tag(name = "Attach Controller", description = "Api list for Attach, attach and other ....")
public class AttachController {

    private final AttachService attachService;

    public AttachController(AttachService attachService) {
        this.attachService = attachService;
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "upload api", description = "Api list attach create")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        log.info("upload attach  = {}", file.getOriginalFilename());
        AttachDTO response = attachService.saveAttach(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return attachService.open_general(fileName);
    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        Resource file = attachService.download(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping(value = "/pagination")
    public ResponseEntity<PageImpl<AttachDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageImpl<AttachDTO> response = attachService.getAttachPagination(page - 1, size);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        Boolean delete = attachService.delete(id);
        return ResponseEntity.ok().body(delete);
    }
}
