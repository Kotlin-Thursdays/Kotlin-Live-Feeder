package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
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

/* Spring introduced the @Autowired annotation for dependency injection.
 * Any of the Spring components can be autowired. These include, components,
 * configurations, services and beans. We’ll look at a few of these in detail below.
 * It’s a common pattern for controllers to be responsible for managing requests
 * and responses while services perform business logic. Let’s look at our
 * GreetingService:
 */

@Controller
class HtmlController(private val repository: CardRepository,
                     private val markdownConverter: MarkdownConverter,
                     private val connectionRepository: ConnectionRepository,
                     private val twitter: Twitter) {

    /*@GetMapping("/")
    fun app(model: Model): String {

        // instead of model.addAttribute("title", "Application")
        model["title"] = "Kotlin Stream"
        model["cards"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "app"
    }*/

    @GetMapping("/")
    fun app(model: Model): String {
        if (connectionRepository.findPrimaryConnection(Twitter::class.java) == null) {
                    return "redirect:/connect/twitter"
        }
        // Spring UI Model supplies attributes for rendering views
        // instead of model.addAttribute("title", "Application")
        model["title"] = "Kotlin Stream"
        model["greeting"] = twitter.userOperations().userProfile
        model["cards"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "app"
    }

    @GetMapping("/twitter")
    fun githubLogin() {

    }

    @GetMapping("/card/{id}")
    fun card(@PathVariable id: Long, model: Model): String {
        val card = repository
                .findById(id)
                .orElseThrow { IllegalArgumentException("Wrong card id provided") }
                .render()
        model["title"] = card.title
        model["card"] = card
        return "card"
    }

    private fun Card.render() = RenderedCard(
            title,
            markdownConverter.invoke(headline),
            markdownConverter.invoke(content),
            author,
            id,
            addedAt.format()
    )

    data class RenderedCard(
            val title: String,
            val headline: String,
            val content: String,
            val author: User,
            val id: Long?,
            val addedAt: String
    )
}