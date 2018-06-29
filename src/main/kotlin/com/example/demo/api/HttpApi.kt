package com.example.demo.api

import com.example.demo.jpa.CardRepository
import com.example.demo.services.markdown.converter.MarkdownConverter
import org.springframework.web.bind.annotation.*


/* ------------------------ Spring Web Flux ---------------------------
 * @RestController is a composed annotation through AOP, composed of a
 * @Controller for routing and a @ResponseBody that renders an HTML template.
 *
 * @RequestMapping are HTTP methods that may include
 *  - @GetMapping
 *  - @PostMapping
 *  - @PutMapping
 *  - @DeleteMapping
 *  - @PatchMapping
 */

@RestController
@RequestMapping("/api/card")
class CardController(private val repository: CardRepository,
                     private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun findAll() {
        repository.findAllByOrderByAddedAtDesc()
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long, @RequestParam converter: String?) {
        when (converter) {
            "markdown" -> repository.findById(id).map {
                it.copy(
                        headline = markdownConverter.invoke(it.headline),
                        content = markdownConverter.invoke(it.content)
                )
            }
            null -> repository.findById(id)
            else -> throw IllegalArgumentException("Only markdown converter is supported.")
        }
    }

}