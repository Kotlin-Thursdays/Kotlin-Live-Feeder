package com.example.demo.services.social

import org.springframework.social.twitter.api.Twitter
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.inject.Inject

@Controller
class TwitterTimelineController @Inject constructor(var twitter: Twitter) {

    @GetMapping("/twitter/timeline")
    fun showTimeline(model: Model): String {
        return showTimeline("Home", model)
    }

    @GetMapping("/twitter/timeline/{timelineType}")
    fun showTimeline(@PathVariable("timelineType") timelineType: String, model: Model): String {
        when(timelineType) {
            "Home" -> model["timeline"] = twitter.timelineOperations().homeTimeline
            "User" -> model["timeline"] = twitter.timelineOperations().userTimeline
            "Mentions" -> model["timeline"] = twitter.timelineOperations().mentions
            "Favorites" -> model["timeline"] = twitter.timelineOperations().favorites
        }

        model["timelineName"] = timelineType

        return "twitter/timeline"
    }

}