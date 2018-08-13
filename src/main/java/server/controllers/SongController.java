package server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import server.db.MockDB;
import server.db.SongDB;

@Controller
@RequestMapping("/songs")
@SessionAttributes("username")
public class SongController {
    @RequestMapping
    public String index(Model model) {
        model.addAttribute("songs", SongDB.songs);
        return "songs";
    }
}