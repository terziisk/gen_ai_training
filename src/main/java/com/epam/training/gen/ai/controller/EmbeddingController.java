package com.epam.training.gen.ai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.training.gen.ai.model.SearchEmbeddingResponse;
import com.epam.training.gen.ai.repository.entity.EmbeddingEntity;
import com.epam.training.gen.ai.service.EmbeddingService;

@RestController
@RequestMapping("/api/embeddings")
public class EmbeddingController {

  @Autowired
  private EmbeddingService embeddingService;

  @PostMapping("/generate")
  public ResponseEntity<List<Float>> generateEmbedding(@RequestBody final String text) {
    return ResponseEntity.ok(embeddingService.generateEmbedding(text));
  }

  @PostMapping("/generate-and-store")
  public ResponseEntity<EmbeddingEntity> generateAndStoreEmbedding(@RequestBody final String text) {
    return ResponseEntity.ok(embeddingService.saveEmbedding(text));
  }

  @GetMapping("/search")
  public ResponseEntity<SearchEmbeddingResponse> search(@RequestParam final String text) {
    return ResponseEntity.ok(embeddingService.findClosestEmbeddings(text));
  }

}
