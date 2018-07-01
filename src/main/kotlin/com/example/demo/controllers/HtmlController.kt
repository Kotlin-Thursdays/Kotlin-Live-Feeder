package com.example.demo.controllers

import com.example.demo.jpa.Card
import com.example.demo.jpa.CardRepository
import com.example.demo.format
import com.example.demo.jpa.AccountRepository
import com.example.demo.jpa.RenderedCard
import com.example.demo.services.markdown.converter.MarkdownConverter
import javax.inject.Provider
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.twitter.api.Twitter
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.lang.IllegalArgumentException
import java.security.Principal

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
                     private val connectionRepositoryProvider: Provider<ConnectionRepository>,
                     private val accountRepository: AccountRepository) {

    /*@GetMapping("/")
    fun app(model: Model): String {

        // instead of model.addAttribute("title", "Application")
        model["title"] = "Kotlin Stream"
        model["cards"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "app"
    }*/

    @GetMapping("/")
    fun app(currentUser: Principal, model: Model): String {
        // Spring UI Model supplies attributes for rendering views
        // instead of model.addAttribute("title", "Application")
        model["title"] = "Kotlin Stream"
        model["connectionsToProviders"] = getConnectionRepository().findAllConnections()
        model["cards"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        if (currentUser != null) {
            model["username"] = accountRepository.findAccountByUsername(currentUser.name)
        }

        return "app"
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

    fun Card.render() = RenderedCard(
            title,
            markdownConverter.invoke(headline),
            markdownConverter.invoke(content),
            author,
            id,
            addedAt.format()
    )

    private fun getConnectionRepository(): ConnectionRepository {
        return connectionRepositoryProvider.get()
    }
}