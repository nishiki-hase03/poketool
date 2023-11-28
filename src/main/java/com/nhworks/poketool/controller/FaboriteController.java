package com.nhworks.poketool.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nhworks.poketool.entity.Faborite;
import com.nhworks.poketool.entity.RentalParty;
import com.nhworks.poketool.form.FaboriteForm;
import com.nhworks.poketool.repository.FaboriteRepository;
import com.nhworks.poketool.repository.RentalPartyRepository;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/faborite")
@AllArgsConstructor
public class FaboriteController {
    
    private final FaboriteRepository faboriteRepository;
    private final RentalPartyRepository rentalPartyRepository;

    // お気に入り管理画面を表示
    @RequestMapping("/manage/{rentalId}")
    public ModelAndView manage(@PathVariable(name = "rentalId")String renatlId, ModelAndView mv){
        Faborite faborite = faboriteRepository.findByRentalId(renatlId).get(0);
        mv.addObject("faborite", faborite);
        mv.setViewName("faborite/manage");
        return mv;
    }

    // お気に入り追加
    @RequestMapping("/add")
    public String add(@ModelAttribute("rentalId") String rentalId, @AuthenticationPrincipal OidcUser user, Model model){
        // レンタルIDで検索し、お気に入りエンティティに詰める
        RentalParty rentalParty = rentalPartyRepository.findByRentalId(rentalId).get(0);
        Faborite faborite = new Faborite();
        faborite.setMailAddress(user.getEmail());
        faborite.setRentalId(rentalParty.getRentalId());
        faborite.setMemo(rentalParty.getIntroduce());

        // insert処理
        try {
            List<Faborite> faboriteList = faboriteRepository.findByMailAddressAndRentalId(user.getEmail(), rentalId);
            if(faboriteList.size() > 0) {
                throw new DataIntegrityViolationException(rentalId);
            }
            faboriteRepository.saveAndFlush(faborite);
        } catch (DataIntegrityViolationException e) {
            // レンタルパーティランダム取得ページへフォワード
            String message = "チームID：" + rentalId + " はお気に入り登録済みです。";
            model.addAttribute("error", message);
            return "forward:/rental/top";
        }

        // マイページへリダイレクト
        return "redirect:/mypage";
    }

    // お気に入り削除
    @RequestMapping("/delete")
    public String delete(ModelAndView mv, @ModelAttribute("rentalId") String rentalId, @AuthenticationPrincipal OidcUser user){
        faboriteRepository.deleteByMailAddressAndRentalId(user.getEmail(), rentalId);
        // マイページへリダイレクト
        return "redirect:/mypage";
    }

    // お気に入りコメント編集
    @RequestMapping("/update")
    public String update(@ModelAttribute FaboriteForm faboriteform, ModelAndView mv){
        // メモだけ更新
        Faborite faborite =faboriteRepository.findByRentalId(faboriteform.getRentalId()).get(0);
        faborite.setMemo(faboriteform.getMemo());
        faboriteRepository.saveAndFlush(faborite);

        // マイページへリダイレクト
        return "redirect:/mypage";
    }

}
