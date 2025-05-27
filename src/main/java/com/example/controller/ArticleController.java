package com.example.controller;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ex-bbs")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public String index(Model model){
        List<Article> articleList = articleRepository.findAll();

        for(Article article : articleList){
            List<Comment> commentList = commentRepository.findByArticleId(article.getId());
            article.setCommentList(commentList);
        }

        model.addAttribute("articleList", articleList);
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("commentForm", new CommentForm());

        return "ex-bbs";
    }

    @PostMapping("/insertArticle")
    public String insertArticle(ArticleForm articleForm){
        System.out.println("insertArticle");
        Article article = new Article();
        article.setName(articleForm.getName());
        article.setContent(articleForm.getContent());

        articleRepository.insert(article);

        return "redirect:/ex-bbs";
    }

    @PostMapping("/insertComment")
    public String insertComment(CommentForm commentForm, Model model){
        System.out.println("insertComment");
        Comment comment = new Comment();
        comment.setName(commentForm.getName());
        comment.setContent(commentForm.getContent());
        comment.setArticleId(Integer.valueOf(commentForm.getArticleId()));

        commentRepository.insert(comment);

        return "redirect:/ex-bbs";
    }

    @PostMapping("/deleteArticle")
    public String deleteArticle(String articleId, Model model){
        System.out.println("deleteArticle");

        commentRepository.deleteByArticleId(Integer.parseInt(articleId));
        articleRepository.deleteById(Integer.parseInt(articleId));

        return "redirect:/ex-bbs";
    }
}
