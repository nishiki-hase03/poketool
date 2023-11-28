package com.nhworks.poketool.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhworks.poketool.entity.RentalParty;
import com.nhworks.poketool.form.RentalPartyForm;
import com.nhworks.poketool.repository.RentalPartyRepository;

import lombok.AllArgsConstructor;

// レンタルパーティに関するコントローラ
@Controller
@RequestMapping("/rental")
@AllArgsConstructor
public class RentalPartyController {

    private final RentalPartyRepository rentalPartyRepository;

    // メニュートップ画面へ
    @RequestMapping("/top")
    public String rentalTop(){
        return "rental/top";
    }

    // レンタルパーティをランダム取得画面へ
    @RequestMapping("/goRandom")
    public ModelAndView goRandom(ModelAndView mv) {
        mv.setViewName("rental/random");
        return mv;
    }

    // レンタルパーティをランダムで取得
    @RequestMapping("/random")
    public ModelAndView random(ModelAndView mv) {
        RentalParty rentalParty = rentalPartyRepository.getRandomParty();
        mv.addObject("rentalParty", rentalParty);
        mv.setViewName("rental/top");
        return mv;
    }

    // レンタルパーティ検索画面へ
    @RequestMapping("/goSearch")
    public ModelAndView goSearch(ModelAndView mv) {
        mv.setViewName("rental/search");
        return mv;
    }

    // レンタルパーティを検索
    @RequestMapping("/search")
    public ModelAndView search(ModelAndView mv, @ModelAttribute("keyword") String keyword) {
        // キーワードが未入力の場合
        if(keyword.isBlank()) {
            mv.setViewName("rental/top");
            return mv;
        }

        // キーワードでレンタルパーティを検索
        List<RentalParty> rentalPartyList = rentalPartyRepository.findByIntroduceLike('%' + keyword + '%');
        mv.setViewName("rental/search");
        mv.addObject("keyword", keyword);
        mv.addObject("rentalPartyList", rentalPartyList);
        return mv;
    }

    // レンタルパーティ登録画面へ
    @RequestMapping("/goRegister")
    public ModelAndView gpRegister(ModelAndView mv) {
        RentalPartyForm rentalPartyForm = new RentalPartyForm();
        mv.addObject("rentalPartyForm", rentalPartyForm);
        mv.setViewName("rental/register");
        return mv;
    }

    // レンタルパーティを登録
    @PostMapping("/register")
    public String register(@ModelAttribute @Validated RentalPartyForm rentalPartyForm, BindingResult result, 
                            @AuthenticationPrincipal OidcUser user,Model model) {
        
        // 入力エラーがあった場合
        if (result.hasErrors()) {
            return "rental/register";
        }
        
        // 登録用エンティティ生成
        RentalParty rentalParty = new RentalParty();
        
        // ログインユーザー情報から取得する
        rentalParty.setMailAddress(user.getEmail());

        // フォームからエンティティに詰めなおし
        rentalParty.setRentalId(rentalPartyForm.getRentalId());
        rentalParty.setIntroduce(rentalPartyForm.getIntroduce());

        // Insert処理
        try {
            rentalPartyRepository.saveAndFlush(rentalParty);
        } catch (DataIntegrityViolationException e) {
            // レンタルパーティランダム取得ページへフォワード
            String message = "チームID：" + rentalPartyForm.getRentalId() + " は登録済みです。";
            model.addAttribute("error", message);
            return "rental/register";
        }

        // マイページへリダイレクト
        return "redirect:/mypage";
    }

    // レンタルパーティ更新画面へ
    @RequestMapping("/goUpdate/{rentalId}")
    public ModelAndView goUpdate(@PathVariable(name = "rentalId")String renatlId, ModelAndView mv) {
        RentalParty rentalParty = rentalPartyRepository.findByRentalId(renatlId).get(0);
        mv.addObject("rentalParty", rentalParty);
        mv.setViewName("rental/update");
        return mv;
    }
    
    // レンタルパーティを更新
    @PostMapping("/update")
    public String update(@ModelAttribute("rentalIdBefore") String rentalIdBefore,
                            @ModelAttribute @Validated RentalPartyForm rentalPartyForm,
                            BindingResult result, @AuthenticationPrincipal OidcUser user, Model model) {
        
        // 入力エラーがあった場合
       if (result.hasErrors()) {
            return "rental/update";
        }

        // 更新対象のエンティティを、更新前のレンタルパーティIDで検索して取得
        RentalParty rentalParty = rentalPartyRepository.findByRentalId(rentalIdBefore).get(0);

        // 更新対象のユーザIDと、ログインしているユーザIDが同一か確認
        if (rentalParty.getMailAddress().equals(user.getEmail())) {
            // フォームからエンティティに詰めなおし
            rentalParty.setRentalId(rentalPartyForm.getRentalId());
            rentalParty.setIntroduce(rentalPartyForm.getIntroduce());

            // update
            rentalPartyRepository.saveAndFlush(rentalParty);
        
        // 更新対象とログインユーザのIDが異なる場合
        } else {
            // システムエラー画面へ
            return "error";
        }

        // マイページへリダイレクト
        return "redirect:/mypage";
    }

    // レンタルパーティを削除
    @PostMapping("/delete")
    public String delete(@ModelAttribute("rentalIdBefore") String rentalId, 
                            @AuthenticationPrincipal OidcUser user,Model model) {
        
        // 削除処理
        RentalParty rentalParty = rentalPartyRepository.findByRentalId(rentalId).get(0);

        // 削除対象のユーザIDと、ログインしているユーザIDが同一か確認
        if (rentalParty.getMailAddress().equals(user.getEmail())) {
            // update
            rentalPartyRepository.delete(rentalParty);
        
        // 削除対象とログインユーザのIDが異なる場合
        } else {
            // システムエラー画面へ
            return "";
        }

        // マイページへリダイレクト
        return "redirect:/mypage";
    }
}
