package server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import server.db.MockDB;

@Controller
@RequestMapping("/songs")
public class SongController {
    @RequestMapping
    public String index(Model model) {
        model.addAttribute("songs", MockDB.songs);
        return "songs";
    }
}