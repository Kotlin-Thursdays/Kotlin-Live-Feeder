package com.example.demo

import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.twitter.api.Twitter
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.lang.IllegalArgumentException
import org.springframework.social.twitter.api.TwitterProfile
import org.springframework.social.twitter.api.CursoredList
import java.util.*


@Controller
class HtmlController(private val connectionRepository: ConnectionRepository,
                     private val twitter: Twitter,
                     private val markdownConverter: MarkdownConverter) {

    /*@GetMapping("/")
    fun app(model: Model): String {

        // instead of model.addAttribute("title", "Application")
        model["title"] = "Kotlin Stream"
        model["cards"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "app"
    }*/

    fun app(model: Model): String {
        if (connectionRepository.findPrimaryConnection(Twitter::class.java) == null) {
                    return "redirect:/connect/twitter"
        }
        // Spring UI Model supplies attributes for rendering views
        // instead of model.addAttribute("title", "Application")
        model["title"] = "Kotlin Stream"
        model["greeting"] = twitter.userOperations().userProfile
        val friends = twitter.friendOperations().friends
        model["cards"] = friends.map{ Card(it.name, it.createdDate.toString(), it.id) }
        return "app"
    }

    @GetMapping("/twitter")
    fun githubLogin() {

    }

    /*@GetMapping("/card/{id}")
    fun card(@PathVariable id: Long, model: Model): String {
        val card = repository
                .findById(id)
                .orElseThrow { IllegalArgumentException("Wrong card id provided") }
                .render()
        model["title"] = card.title
        model["card"] = card
        return "card"
    }*/

    fun Card.render(title: String, headline: String?="",
                    id: Long?=0) = RenderedCard(
            title,
            markdownConverter.invoke(headline),
            //markdownConverter.invoke(content)
            //author,
            id
            //addedAt.format()
    )

    data class RenderedCard(
            val title: String,
            val headline: String,
            //val content: String
            //val author: User,
            val id: Long?
            //val addedAt: String
    )
}