package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.dto.MemberDto;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.service.MemberService;
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

    @GetMapping("/post/add")
    public String addPost(Model model) {
        PostDto postDto = new PostDto();
        MemberDto memberDto = new MemberDto();

        model.addAttribute("post", postDto);
        model.addAttribute("member", memberDto);
        return "board/addPost";
    }

    @PostMapping("/post/add")
    public String addPost(@ModelAttribute("post") PostDto postDto,@ModelAttribute("member") MemberDto memberDto, RedirectAttributes redirectAttributes) {
        memberDto.setNickname(memberDto.getNickname());
        memberDto.setPassword(memberDto.getPassword());
        Member member = memberService.save(memberDto);
        postDto.setMember(member);
        Post post = postService.savePost(postDto);
        redirectAttributes.addAttribute("postId", post.getId());

        return "redirect:/board/post/{postId}";
    }

    @GetMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        Member member = memberService.findById(post.getMember().getId());
        PostDto postDto = postService.postEntityToDto(post);
        MemberDto memberDto = memberService.memberEntityToDto(member);
        model.addAttribute("post", postDto);
        model.addAttribute("member", memberDto);
        return "editPost";
    }

    @PostMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute("post") PostDto postDto, @ModelAttribute("member") MemberDto memberDto) {
        memberDto.setNickname(memberDto.getNickname());
        memberDto.setPassword(memberDto.getPassword());
        Member member = memberService.save(memberDto);
        postDto.setMember(member);
        postService.updatePost(id, postDto);
        return "redirect:/board/post/{id}";
    }
}
