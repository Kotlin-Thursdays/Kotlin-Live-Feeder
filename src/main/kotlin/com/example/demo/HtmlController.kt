package com.example.demo

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.lang.IllegalArgumentException

@Controller
class HtmlController(private val repository: ArticleRepository,
                     private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun app(model: Model): String {
        // instead of model.addAttribute("title", "Application")
        model["title"] = "Application"
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "app"
    }

    @GetMapping("/article/{id}")
    fun article(@PathVariable id: Long, model: Model): String {
        val article = repository
                .findById(id)
                .orElseThrow { IllegalArgumentException("Wrong article id provided") }
                .render()
        model["title"] = article.title
        model["article"] = article
        return "article"
    }

    fun Article.render() = RenderedArticle(
            title,
            markdownConverter.invoke(headline),
            markdownConverter.invoke(content),
            author,
            id,
            addedAt.format()
    )

    data class RenderedArticle(
            val title: String,
            val headline: String,
            val content: String,
            val author: User,
            val id: Long?,
            val addedAt: String
    )
}