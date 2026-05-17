package com.workshop.petcareops.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@RestController
@RequestMapping("/api/admin/templates")
public class TemplatePreviewController {

    @GetMapping("/preview")
    public String previewTemplate(@RequestParam String templateName) {
        Path templatePath = Path.of("..", "workshop-assets", "message-templates", templateName);

        try {
            return Files.readString(templatePath);
        } catch (IOException exception) {
            return "Unable to load template: " + exception + "\n" + Arrays.toString(exception.getStackTrace());
        }
    }
}