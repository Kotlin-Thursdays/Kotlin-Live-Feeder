package com.example.demo.services.social

import org.springframework.social.twitter.api.Twitter
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.inject.Inject

@Controller
class TwitterSearchController @Inject constructor(var twitter: Twitter) {

    @GetMapping("/twitter/search")
    fun showTrends(@RequestParam("query")query: String, model: Model): String {
        model["timeline"] = twitter.searchOperations().search(query).tweets
        return "twitter/timeline"
    }

}