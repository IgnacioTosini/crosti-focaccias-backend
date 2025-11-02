package com.crostifocaccias.crosti_focaccias.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/cloudinary")
public class CloudinaryController {
    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.upload_preset}")
    private String uploadPreset;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", "No se envió archivo"));
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("upload_preset", uploadPreset);
            params.put("folder", "Crosti Focaccias");
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), params);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("url", result.get("secure_url"));
            response.put("public_id", result.get("public_id"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @PostMapping("/delete-images")
    public ResponseEntity<?> deleteImages(@RequestBody Map<String, Object> body) {
        Object publicIdsObj = body.get("publicIds");
        if (!(publicIdsObj instanceof List) || ((List<?>) publicIdsObj).isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", "publicIds debe ser un array no vacío");
            return ResponseEntity.badRequest().body(error);
        }
        List<?> publicIds = (List<?>) publicIdsObj;
        List<Map<String, Object>> results = new ArrayList<>();
        int successful = 0;
        int failed = 0;
        for (Object idObj : publicIds) {
            String id = String.valueOf(idObj);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("publicId", id);
            try {
                Map<?, ?> result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
                String resultStatus = (String) result.get("result");
                resultMap.put("result", resultStatus);
                resultMap.put("success", true);
                successful++;
            } catch (Exception e) {
                resultMap.put("error", e.getMessage());
                resultMap.put("success", false);
                failed++;
            }
            results.add(resultMap);
        }
        Map<String, Object> summary = new HashMap<>();
        summary.put("total", results.size());
        summary.put("successful", successful);
        summary.put("failed", failed);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("results", results);
        response.put("summary", summary);
        return ResponseEntity.ok(response);
    }

    /**
     * Borra una imagen individual de Cloudinary por su publicId.
     * Ejemplo de uso: DELETE /api/cloudinary/upload?publicId=...
     * 
     * @param publicId el identificador público de la imagen en Cloudinary
     * @return JSON con success y result
     */
    @DeleteMapping("/upload")
    public ResponseEntity<?> deleteImage(@RequestParam String publicId) {
        if (publicId == null || publicId.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", "publicId requerido"));
        }
        try {
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            String resultStatus = (String) result.get("result");
            boolean success = "ok".equalsIgnoreCase(resultStatus);
            return ResponseEntity.ok(Map.of(
                    "success", success,
                    "result", resultStatus));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> corsPreflight() {
        return ResponseEntity.ok().build();
    }
}
