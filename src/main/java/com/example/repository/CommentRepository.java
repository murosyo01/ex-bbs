package com.example.repository;

import com.example.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {
    private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setName(rs.getString("name"));
        comment.setContent(rs.getString("content"));
        comment.setArticleId(rs.getInt("articleId"));

        return comment;
    };

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * 記事IDからコメントのリストを検索する
     * @param articleId 記事ID
     * @return コメントリスト
     */
    public List<Comment> findByArticleId(int articleId){
        String sql = """
                SELECT
                    id, name, content, article_id
                FROM
                    comments
                WHERE
                    article_id = :articleId
                ;
                """;

        SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);

        return template.query(sql, param, COMMENT_ROW_MAPPER);
    }

    /**
     * コメントをcommentsテーブルに挿入する.
     * @param comment コメントオブジェクト
     */
    public void insert(Comment comment){
        String sql = """
                INSERT INTO comments
                    (name, content, article_id)
                VALUES
                    (:name, :content, :articleId)
                ;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("name", comment.getName()).addValue("content", comment.getContent()).addValue("articleId", comment.getArticleId());

        template.update(sql, param);
    }

    /**
     * 記事IDに基づいてcommentsテーブルからコメントオブジェクトを削除する.
     * @param articleId 記事ID
     */
    public void deleteByArticleId(int articleId){
        String sql = """
                DELETE FROM comments
                WHERE
                    article_id = :articleId
                ;
                """;

        SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);

        template.update(sql, param);
    }
}
