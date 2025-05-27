package com.example.form;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * 記事情報を受け取るフォーム.
 */
public class ArticleForm {
    /**
     * 名前
     */
    @NotBlank(message = "投稿者名を入力してください")
    @Length(min = 0, max = 50, message = "投稿者名は50字以内で入力してください")
    private String name;

    /**
     * コンテンツ
     */
    @NotBlank(message = "投稿内容を入力してください")
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
