package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.dto.AuthDto;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.pagination.Pagination;
import BoardAdv.AnonymLog.pagination.PagingConst;
import BoardAdv.AnonymLog.service.PostService;
import BoardAdv.AnonymLog.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final PostService postService;

    @GetMapping("/list/{page}")
    public String list(@PathVariable int page, Model model, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, PagingConst.POST_CNT_PER_PAGE, Sort.by("id").descending());
        List<Post> posts = postService.findAllByPage(pageable);
        Pagination pagination = new Pagination(postService.getPostCnt(),page);

        model.addAttribute("posts", posts);
        model.addAttribute("pagination", pagination);
        model.addAttribute("pagesInCurrentBlock", pagination.pagesInCurrentBlock());

        HttpSession session = request.getSession();
        Member testerLogin = (Member) session.getAttribute(SessionConst.TESTER_LOGIN);
        if (testerLogin == null) {
            model.addAttribute("loginStatus", false);
            log.info("loginStatus : {}",model.getAttribute("loginStatus"));
            return "board/list";
        }
        if (testerLogin.getIsTester() == true) {
            model.addAttribute("loginStatus", true);
            log.info("loginStatus : {}",model.getAttribute("loginStatus"));
        }
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
    public String post(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addAttribute("id", id);

        HttpSession session = request.getSession();
        Member sessionMember = (Member) session.getAttribute(SessionConst.TESTER_LOGIN);
        if (sessionMember!=null&&sessionMember.getIsHen()) {
            return "redirect:/board/post/edit/{id}";
        }
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
    public String addPost(@Validated @ModelAttribute("post") PostDto postDto, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "board/addPost";
        }
        Post post = postService.savePost(postDto);
        redirectAttributes.addAttribute("postId", post.getId());

        return "redirect:/board/list/1";
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
    public String editPost(@PathVariable Long id, @Validated @ModelAttribute("post") PostDto postDto,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "board/editPost";
        }

        log.info(postDto.toString());
        Post post = postService.updatePost(id, postDto);
        log.info(post.toString());
        return "redirect:/board/post/{id}";
    }

    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        log.info("==============deletePost 진입");
        postService.deletePost(id);
        log.info("==============deletePost 메서드 완료");
        return "redirect:/board/list";
    }
}
