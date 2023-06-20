package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.dto.MemberDto;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.service.MemberService;
import BoardAdv.AnonymLog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "board/list";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "board/post";
    }

    @PostMapping("/post/{id}")
    public String post(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", id);
        return "redirect:/board/post/editAuth/{id}";
    }

    @GetMapping("/post/editAuth/{id}")
    public String editAuth(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("title", post.getTitle());
        model.addAttribute("password", null);
        return "board/editPostAuth";
    }

    @PostMapping("/post/editAuth/{id}")
    public String editAuth(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", id);
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

        return "redirect:/board/post/{postId}";
    }

    @GetMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        PostDto postDto = postService.postEntityToDto(post);
        model.addAttribute("postId", id);
        model.addAttribute("post", postDto);
        return "board/editPost";
    }

    /**
     * 아래 메서드 수행 시, 작성자 nickname,password, 게시물 title 세 항목 null
     * **************************************************아 설마 내부호출 떄문?**************************************************
     */
    @PostMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute("post") PostDto postDto) {
        log.info(postDto.toString());
        Post post = postService.updatePost(id, postDto);
        log.info(post.toString());
        return "redirect:/board/post/{id}";
    }

    @GetMapping("/post/delete/{id}")
    public String delete(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "list";
    }
}
