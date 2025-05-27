package com.example.controller;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ex-bbs")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("")
    public String index(){
        return "ex-bbs";
    }

    @PostMapping("/insertArticle")
    public String insertArticle(ArticleForm articleForm, Model model){
        Article article = new Article();
        article.setName(articleForm.getName());
        article.setContent(articleForm.getContent());

        articleRepository.insert(article);

        model.addAttribute("articles", article);

        return "/ex-bbs";
    }

    @PostMapping("/insertComment")
    public String insertComment(){
        return "/ex-bbs";
    }

    @PostMapping("/deleteArticle")
    public String deleteArticle(){
        return "/ex-bbs";
    }

    @PostMapping("/deleteComment")
    public String deleteComment(){
        return "/ex-bbs";
    }
}
