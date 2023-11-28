package com.nhworks.poketool.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhworks.poketool.entity.RentalParty;
// import com.nhworks.poketool.entity.User;
import com.nhworks.poketool.repository.FaboriteRepository;
import com.nhworks.poketool.repository.RentalPartyRepository;
// import com.nhworks.poketool.repository.UserRepository;
import com.nhworks.poketool.entity.Faborite;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class TopController {
    
    private final RentalPartyRepository rentalPartyRepository;
    private final FaboriteRepository faboriteRepository;
    // private final UserRepository userRepository;

    @RequestMapping("/")
    public String top() {
        return "top";
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal OidcUser user) {
        // ログインユーザが登録済みかを確認
        // User loginUser = userRepository.findByMailAddress(user.getEmail());
        // if(loginUser == null) {
        //     // 未登録の場合は登録しておく
        // }

        return "top";
    }
    
    // マイページを表示
    @RequestMapping("/mypage")
    public ModelAndView mypage(ModelAndView mv, @AuthenticationPrincipal OidcUser user) {
        // ログインユーザ情報からIDを取得
        String mailAddress = user.getEmail();

        // ログインユーザの登録済みレンタルパーティを取得
        List<RentalParty> rentalPartyList =  rentalPartyRepository.findByMailAddressOrderByRentalId(mailAddress);
        mv.addObject("rentalPartyList", rentalPartyList);

        // ログインユーザのお気に入りレンタルパーティを取得
        List<Faborite> faboriteList = faboriteRepository.findByMailAddressOrderByRentalId(mailAddress);
        mv.addObject("faboriteList", faboriteList);
        mv.setViewName("mypage");
        return mv;
    }

    // @RequestMapping("/inquiry")
    // public String inquiry() {
    //     return "inquiry";
    // }
}
