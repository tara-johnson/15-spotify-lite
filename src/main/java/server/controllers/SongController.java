package server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import server.db.SongDB;
import server.models.Song;

import java.util.Queue;

@Controller
@RequestMapping("/songs")
@SessionAttributes("username")
public class SongController {
    @RequestMapping
    public String index(
            @RequestParam String username,
            Model model
    ) {
        Queue<Song> songsByUserName = SongDB.getSongByUsername(username);
        model.addAttribute("songs", songsByUserName);

        return "songs";
    }
}