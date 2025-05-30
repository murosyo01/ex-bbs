package com.example.controller;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 記事に対する指示を行うコントローラー.
 */
@Controller
@RequestMapping("/ex-bbs")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    /**
     * 画面を表示.
     *
     * @param model モデル
     * @return 掲示板画面
     */
    @GetMapping("")
    public String index(ArticleForm articleForm, CommentForm commentForm, Model model) {
        List<Article> articleList = articleRepository.findAll();

        for (Article article : articleList) {
            List<Comment> commentList = commentRepository.findByArticleId(article.getId());
            article.setCommentList(commentList);
        }

        model.addAttribute("articleList", articleList);

        return "ex-bbs";
    }

//    @GetMapping("/index2")
//    public String index2(ArticleForm articleForm, CommentForm commentForm, Model model) {
//        List<Article> articleList = articleRepository.joinFindAll();
//
//        System.out.println(articleList);
//
//        model.addAttribute("articleList", articleList);
//
//        return "ex-bbs";
//    }

    /**
     * 記事を挿入する.
     *
     * @param articleForm フォーム
     * @return indexメソッドが定義されているエントリーポイント
     */
    @PostMapping("/insertArticle")
    public String insertArticle(@Validated ArticleForm articleForm, BindingResult result, CommentForm commentForm, Model model) {
        System.out.println("insertArticle");
        if (result.hasErrors()) {
            return index(articleForm, commentForm, model);
        }
        Article article = new Article();
        article.setName(articleForm.getName());
        article.setContent(articleForm.getContent());

        articleRepository.insert(article);

        return "redirect:/ex-bbs";
    }

    /**
     * コメントの挿入.
     *
     * @param commentForm フォーム
     * @param model       モデル
     * @return indexメソッドが定義されているエントリーポイント
     */
    @PostMapping("/insertComment")
    public String insertComment(@Validated CommentForm commentForm, BindingResult result, ArticleForm articleForm, Model model) {
        System.out.println("insertComment");
        if (result.hasErrors()) {
            return index(articleForm, commentForm, model);
        }
        Comment comment = new Comment();
        comment.setName(commentForm.getName());
        comment.setContent(commentForm.getContent());
        comment.setArticleId(Integer.valueOf(commentForm.getArticleId()));

        commentRepository.insert(comment);

        return "redirect:/ex-bbs";
    }

    /**
     * 記事および関連するコメントを削除
     *
     * @param articleId 記事ID
     * @param model     モデル
     * @return indexメソッドが定義されているエントリーポイント
     */
    @PostMapping("/deleteArticle")
    public String deleteArticle(String articleId, Model model) {
        System.out.println("deleteArticle");

        commentRepository.deleteByArticleId(Integer.parseInt(articleId));
        articleRepository.deleteById(Integer.parseInt(articleId));

        return "redirect:/ex-bbs";
    }
}
