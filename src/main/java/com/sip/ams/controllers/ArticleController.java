package com.sip.ams.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sip.ams.entities.Article;
import com.sip.ams.repositories.ArticleRepository;

@RestController
@RequestMapping({ "/articles" })
@CrossOrigin(origins = "*")
public class ArticleController {
	@Autowired
	private ArticleRepository articleRepository;

	@GetMapping("/list")
	public List<Article> getAllProviders() {
		return (List<Article>) articleRepository.findAll();
	}
	
	@GetMapping("/{articleId}")
	public Article getArticle(@PathVariable Long articleId) {
		Optional<Article> a = articleRepository.findById(articleId);
		return a.get();
	}
	
	@PostMapping("/add")
	public Article createArticle(@Valid @RequestBody Article article) {
		return articleRepository.save(article);
	}
	
	@PutMapping("/{articleId}")
	public Article updateArticle(@PathVariable Long articleId, @Valid @RequestBody Article articleRequest) {
		return articleRepository.findById(articleId).map(article -> {
			article.setLabel(articleRequest.getLabel());
			article.setPrice(articleRequest.getPrice());
			return articleRepository.save(article);
		}).orElseThrow(() -> new IllegalArgumentException("ArticleerId " + articleId + " not found"));
	}

	@DeleteMapping("/{articleId}")
	public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {
		return articleRepository.findById(articleId).map(article -> {
			articleRepository.delete(article);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new IllegalArgumentException("ProviderId " + articleId + " not found"));
	}
}
