package com.toy.api.springboot.web;

import com.toy.api.springboot.config.auth.LoginUser;
import com.toy.api.springboot.config.auth.dto.SessionUser;
import com.toy.api.springboot.service.posts.PostsService;
import com.toy.api.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) { // Model은? 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음
        // +) postsService.findAllDesc()로 가져온 결과를 posts로 idex.mustache에 전달

        model.addAttribute("posts", postsService.findAllDesc());

        /*//CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성
        //이 부분은 @LoginUser를 사용해서 세션 정보를 가지고 올 수 있게 되어 필요 없게 됨
        SessionUser user = (SessionUser) httpSession.getAttribute("user");*/

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {

        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
