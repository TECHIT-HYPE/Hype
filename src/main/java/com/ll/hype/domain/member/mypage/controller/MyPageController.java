package com.ll.hype.domain.member.mypage.controller;

import com.ll.hype.domain.adress.adress.dto.AddressRequest;
import com.ll.hype.domain.adress.adress.dto.AddressResponse;
import com.ll.hype.domain.adress.adress.service.AddressService;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.service.MemberService;
import com.ll.hype.domain.member.mypage.dto.ModifyRequest;
import com.ll.hype.domain.member.mypage.dto.MyWishlistDto;
import com.ll.hype.domain.wishlist.wishlist.service.WishlistService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import com.ll.hype.global.util.ShoesSizeGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    private final MemberService memberService;
    private final WishlistService wishlistService;
    private final AddressService addressService;

    @GetMapping("/profile")
    public String profileForm(@AuthenticationPrincipal UserPrincipal userPrincipal, ModifyRequest modifyRequest, Model model) {

        Member member = userPrincipal.getMember();

        modifyRequest.setEmail(member.getEmail());
        modifyRequest.setName(member.getName());
        modifyRequest.setNickname(member.getNickname());
        modifyRequest.setPhoneNumber(member.getPhoneNumber());
        modifyRequest.setBirthday(member.getBirthday());
        modifyRequest.setGender(member.getGender());
        modifyRequest.setShoesSize(member.getShoesSize());

        return loadAndReturnProfileForm(model);
    }

    @PostMapping("/profile")
    public String modify(@AuthenticationPrincipal UserPrincipal userPrincipal,
                         @Valid ModifyRequest modifyRequest,
                         BindingResult bindingResult,
                         Model model
    ) {
        if (bindingResult.hasErrors()) {
            return loadAndReturnProfileForm(model);
        }

        if (!memberService.confirmPassword(modifyRequest.getPassword(), modifyRequest.getPasswordConfirm())) {
            bindingResult.reject("passwordIncorrect", "비밀번호가 일치하지 않습니다.");
            return loadAndReturnProfileForm(model);
        }

        if (memberService.existsByNickname(modifyRequest.getNickname()) &&
                !Objects.equals(userPrincipal.getMember().getNickname(), modifyRequest.getNickname())
        ) {
            bindingResult.reject("existNickname", "이미 존재하는 별명입니다.");
            return loadAndReturnProfileForm(model);
        }

        if (memberService.existsByPhoneNumber(modifyRequest.getPhoneNumber())  &&
                !Objects.equals(userPrincipal.getMember().getPhoneNumber(), modifyRequest.getPhoneNumber())
        ) {
            bindingResult.reject("existPhoneNumber", "이미 존재하는 전화번호입니다.");
            return loadAndReturnProfileForm(model);
        }

        memberService.modify(modifyRequest, userPrincipal.getMember());

        return "redirect:/mypage/profile";
    }

    private String loadAndReturnProfileForm(Model model) {
        model.addAttribute("shoesSizeList", ShoesSizeGenerator.getSizes());
        return "domain/member/mypage/profile";
    }

    @GetMapping("/wishlist")
    public String myWishlistForm(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        List<MyWishlistDto> wishlist = wishlistService.getMyWishlist(userPrincipal.getMember().getId());

        model.addAttribute("wishlist", wishlist);
        return "domain/member/mypage/wishlist";
    }

    @GetMapping("/addressList")
    public String myAddressListForm(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        List<AddressResponse> myAddressList = addressService.getMyAddressList(userPrincipal.getMember().getId());

        model.addAttribute("myAddressList", myAddressList);
        return "domain/member/mypage/address";
    }

    @GetMapping("/newAddress")
    public String createAddressForm(@AuthenticationPrincipal UserPrincipal userPrincipal, AddressRequest addressRequest, Model model) {
        return "domain/member/mypage/addressForm";
    }

    @PostMapping("/newAddress")
    public String createAddress(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @Valid AddressRequest addressRequest,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "domain/member/mypage/addressForm";
        }

        addressService.createAddress(userPrincipal, addressRequest);

        return "redirect:/mypage/addressList";
    }
}
