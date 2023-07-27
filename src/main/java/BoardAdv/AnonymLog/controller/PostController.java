package BoardAdv.AnonymLog.controller;

import BoardAdv.AnonymLog.dto.AuthDto;
import BoardAdv.AnonymLog.dto.CommentDto;
import BoardAdv.AnonymLog.dto.PostDto;
import BoardAdv.AnonymLog.entity.Member;
import BoardAdv.AnonymLog.entity.Post;
import BoardAdv.AnonymLog.pagination.Pagination;
import BoardAdv.AnonymLog.pagination.PagingConst;
import BoardAdv.AnonymLog.service.MemberService;
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
    private final MemberService memberService;

    /**
     * 페이징 처리 된 '/board/list/{page}' : GET
     */
    @GetMapping("/list/{page}")
    public String list(@PathVariable int page, Model model, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, PagingConst.POST_CNT_PER_PAGE, Sort.by("id").descending());
        List<Post> posts = postService.findAllByPage(pageable);
        Pagination pagination = new Pagination(postService.getPostCnt(),page);

        model.addAttribute("posts", posts);
        model.addAttribute("pagination", pagination);
        model.addAttribute("pagesInCurrentBlock", pagination.pagesInCurrentBlock());

        memberService.addLoginStatusAttribute(request, model);

        return "board/list";
    }


    /**
     * 개별 게시글 조회
     */
    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id, Model model,HttpServletRequest request) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        Member sessionMember = memberService.getTesterFromSession(request);
        if (sessionMember!=null&&sessionMember.getIsHen()) {
            memberService.addLoginStatusAttribute(request, model);
            CommentDto commentDto = new CommentDto();
            model.addAttribute("commentDto", commentDto);
        }
        return "board/post";
    }

    /**
     * Comment 작성 POST
     */
    @PostMapping("/post/{id}")
    public String post(@PathVariable Long id, @ModelAttribute CommentDto commentDto, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        redirectAttributes.addAttribute("id", id);
        log.info("commentDto.content : {}", commentDto.getContent());
        Member sessionMember = memberService.getTesterFromSession(request);
        commentDto.setMember(sessionMember);
        commentDto.setPost(postService.findById(id));
        postService.saveComment(commentDto);
        return "redirect:/board/post/{id}";
    }

    /**
     * 익명 게시글 조회 인증
     */
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

    /**
     * 익명 게시글 조회 Auth시 비밀번호 검증
     */
    @PostMapping("/post/readAuth/{id}")
    public String readAuth(@PathVariable Long id, @ModelAttribute AuthDto authDto, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", id);

        if (!(postService.findById(id).getPassword().equals(authDto.getPassword()))) {
            return "redirect:/board/post/readAuth/{id}?trial=fail";
        }

        return "redirect:/board/post/{id}";
    }


    /**
     * 게시글 수정시 인증 (HEN인 경우 인증 X)
     */
    @GetMapping("/post/editAuth/{id}")
    public String editAuth(@PathVariable Long id, @RequestParam(required = false) Optional<String> trial
            , Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {

        /**
         * getTesterFromSession 메서드로 Tester Member를 가져옴
         * Tester && Hen 인 경우 인증 없이 edit 가능
         */
        Member sessionMember = memberService.getTesterFromSession(request);
        if (sessionMember!=null&&sessionMember.getIsHen()) {
            redirectAttributes.addAttribute("postId", id);
            return "redirect:/board/post/edit/{postId}";
        }

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

    /**
     * 게시글 수정시 Auth 비밀번호 인증
     */
    @PostMapping("/post/editAuth/{id}")
    public String editAuth(@PathVariable Long id, @ModelAttribute AuthDto authDto, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", id);

        if (!(postService.findById(id).getPassword().equals(authDto.getPassword()))) {
            return "redirect:/board/post/editAuth/{id}?trial=fail";
        }

        return "redirect:/board/post/edit/{id}";
    }

    /**
     * 게시글 작성 -> DTO 모델에 전달
     */
    @GetMapping("/post/add")
    public String addPost(Model model) {
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "board/addPost";
    }

    /**
     * 게시글 작성시 -> save
     */
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

    /**
     * 게시글 수정 DTO 전달
     */
    @GetMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        PostDto postDto = postService.postEntityToDto(post);
        model.addAttribute("postId", id);
        log.info("postId = {}", id);
        model.addAttribute("post", postDto);
        return "board/editPost";
    }

    /**
     * 게시글 수정
     */
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

    /**
     * 게시글 삭제
     */
    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return "redirect:/board/list";
    }
}
