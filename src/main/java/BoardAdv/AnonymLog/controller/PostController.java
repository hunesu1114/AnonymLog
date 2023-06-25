package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.dto.AuthDto;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
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
    public String post(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Post post = postService.findById(id);
        redirectAttributes.addAttribute("id", id);
        model.addAttribute("post", post);
        return "board/post";
    }

    @PostMapping("/post/{id}")
    public String post(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", id);
        return "redirect:/board/post/editAuth/{id}";
    }

    @GetMapping("/post/readAuth/{id}")
    public String readAuth(@PathVariable Long id, @RequestParam(required = false) Optional<String> trial, Model model) {
        log.info("trial : {}", trial);
        if (trial.orElse("none").equals("fail")) {
            model.addAttribute("trialFailure", true);
        }
        AuthDto authDto = new AuthDto();
        authDto.setId(id);
        model.addAttribute("authDto", authDto);
        return "board/readPostAuth";
    }

    @PostMapping("/post/readAuth/{id}")
    public String readAuth(@PathVariable Long id, @ModelAttribute AuthDto authDto, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", id);

        if (!(postService.findById(id).getPassword().equals(authDto.getPassword()))) {
            return "redirect:/board/post/readAuth/{id}?trial=fail";
        }

        return "redirect:/board/post/{id}";
    }


    @GetMapping("/post/editAuth/{id}")
    public String editAuth(@PathVariable Long id, @RequestParam(required = false) Optional<String> trial, Model model) {

        Post post = postService.findById(id);
        if (trial.orElse("none").equals("fail")) {
            model.addAttribute("trialFailure", true);
        }
        AuthDto authDto = new AuthDto();
        authDto.setId(id);
        authDto.setTitle(post.getTitle());
        model.addAttribute("authDto", authDto);
        return "board/editPostAuth";
    }

    @PostMapping("/post/editAuth/{id}")
    public String editAuth(@PathVariable Long id, @ModelAttribute AuthDto authDto, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", id);

        if (!(postService.findById(id).getPassword().equals(authDto.getPassword()))) {
            return "redirect:/board/post/editAuth/{id}?trial=fail";
        }

        return "redirect:/board/post/edit/{id}";
    }

    @GetMapping("/post/add")
    public String addPost(Model model) {
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "board/addPost";
    }

    @PostMapping("/post/add")
    public String addPost(@ModelAttribute("post") PostDto postDto, RedirectAttributes redirectAttributes) {
        Post post = postService.savePost(postDto);
        redirectAttributes.addAttribute("postId", post.getId());

        return "redirect:/board/list";
    }

    @GetMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        PostDto postDto = postService.postEntityToDto(post);
        model.addAttribute("postId", id);
        model.addAttribute("post", postDto);
        return "board/editPost";
    }

    @PostMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute("post") PostDto postDto) {
        log.info(postDto.toString());
        Post post = postService.updatePost(id, postDto);
        log.info(post.toString());
        return "redirect:/board/post/{id}";
    }

    @GetMapping("/post/delete/{id}")
    public String delete(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/board/list";
    }
}
