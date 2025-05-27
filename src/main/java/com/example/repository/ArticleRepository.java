package com.example.repository;

import com.example.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 記事を操作するリポジトリ.
 */
@Repository
public class ArticleRepository {
    private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setName(rs.getString("name"));
        article.setContent(rs.getString("content"));
        article.setCommentList(new ArrayList<>());

        return article;
    };

    @Autowired
    NamedParameterJdbcTemplate template;

    /**
     * 記事のリストを全件検索する.
     *
     * @return 記事のリスト
     */
    public List<Article> findAll() {
        String sql = """
                SELECT
                    id, name, content
                FROM
                    articles
                ORDER BY
                    id DESC
                """;

        return template.query(sql, ARTICLE_ROW_MAPPER);
    }

    /**
     * 記事を追加する.
     *
     * @param article 記事オブジェクト
     */
    public void insert(Article article) {
        String sql = """
                INSERT INTO articles
                    (name, content)
                VALUES
                    (:name, :content)
                ;
                """;

        SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content", article.getContent());

        template.update(sql, param);
    }

    /**
     * 記事を削除する.
     *
     * @param id 記事ID
     */
    public void deleteById(int id) {
        String sql = """
                DELETE FROM articles
                WHERE
                    id = :id
                ;
                """;

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        template.update(sql, param);
    }

    public List<Article> joinFindAll() {
        String sql = """
                SELECT
                     a.id,
                     a.name,
                     a.content,
                     c.id,
                     c.name,
                     c.content,
                     c.article_id
                 FROM
                     articles AS a
                 LEFT OUTER JOIN
                     comments AS c
                 ON
                     a.id = c.article_id
                 ORDER BY
                    a.id DESC,
                    c.article_id DESC
                 ;
                """;
        return template.query(sql, ARTICLE_ROW_MAPPER);
    }
}
