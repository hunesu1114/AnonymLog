package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "board/list";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id,Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "board/post";
    }

    @GetMapping("/post/add")
    public String addPost(Model model) {
        PostDto dto = new PostDto();
        model.addAttribute("post", dto);
        return "board/addPost";
    }

    @PostMapping("/post/add")
    public String addPost(@ModelAttribute("post") PostDto dto, RedirectAttributes redirectAttributes) {
        Post post = postService.savePost(dto);
        redirectAttributes.addAttribute("postId", post.getId());

        return "redirect:/board/post/{postId}";
    }
}
