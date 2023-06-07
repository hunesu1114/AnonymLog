package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final PostService service;

    @GetMapping("/list")
    public String list(Model model) {
        List<Post> posts = service.findAll();
        model.addAttribute("posts", posts);
        return "board/list";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id) {
        //로직 작성 전
        return "board/post";
    }
}
