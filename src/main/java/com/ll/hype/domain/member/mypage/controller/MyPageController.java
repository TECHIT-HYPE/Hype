package com.ll.hype.domain.member.mypage.controller;

import com.ll.hype.domain.address.address.dto.AddressRequest;
import com.ll.hype.domain.address.address.dto.AddressResponse;
import com.ll.hype.domain.address.address.entity.Address;
import com.ll.hype.domain.address.address.service.AddressService;
import com.ll.hype.domain.member.member.dto.ModifyRequest;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.service.MemberService;
import com.ll.hype.domain.order.buy.dto.response.BuyResponse;
import com.ll.hype.domain.order.order.dto.response.OrderResponse;
import com.ll.hype.domain.order.order.service.OrderService;
import com.ll.hype.domain.order.sale.dto.response.SaleResponse;
import com.ll.hype.domain.wishlist.wishlist.dto.WishListResponse;
import com.ll.hype.domain.wishlist.wishlist.entity.Wishlist;
import com.ll.hype.domain.wishlist.wishlist.service.WishlistService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import com.ll.hype.global.util.ShoesSizeGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final OrderService orderService;

    @GetMapping("/profile")
    public String profileForm(@AuthenticationPrincipal UserPrincipal userPrincipal, ModifyRequest modifyRequest,
                              Model model) {

        Member member = userPrincipal.getMember();

        modifyRequest.setEmail(member.getEmail());
        modifyRequest.setName(member.getName());
        modifyRequest.setNickname(member.getNickname());
        modifyRequest.setPhoneNumber(member.getPhoneNumber());
        modifyRequest.setBirthday(member.getBirthday());
        modifyRequest.setGender(member.getGender());
        modifyRequest.setShoesSize(member.getShoesSize());

        List<String> profilePhoto = memberService.getProfilePhoto(member.getId());

        model.addAttribute("profilePhoto", profilePhoto);
        return loadAndReturnProfileForm(model);
    }

    @PostMapping("/profile")
    public String modify(@AuthenticationPrincipal UserPrincipal userPrincipal,
                         @Valid ModifyRequest modifyRequest,
                         @RequestParam(value = "files") List<MultipartFile> files,
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

        if (memberService.existsByPhoneNumber(modifyRequest.getPhoneNumber()) &&
                !Objects.equals(userPrincipal.getMember().getPhoneNumber(), modifyRequest.getPhoneNumber())
        ) {
            bindingResult.reject("existPhoneNumber", "이미 존재하는 전화번호입니다.");
            return loadAndReturnProfileForm(model);
        }

        memberService.modify(modifyRequest, userPrincipal.getMember(), files);

        return "redirect:/mypage/profile";
    }

    private String loadAndReturnProfileForm(Model model) {
        model.addAttribute("shoesSizeList", ShoesSizeGenerator.getSizes());
        return "domain/member/mypage/profile";
    }

    @GetMapping("/wishlist")
    public String myWishlistForm(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                 Model model) {
        // List<MyWishlistDto> wishlist = wishlistService.getMyWishlist(userPrincipal.getMember().getId());
        List<WishListResponse> wishListResponses = wishlistService.myWishList(userPrincipal.getMember());

        model.addAttribute("data", wishListResponses);
        return "domain/member/mypage/wishlist";
    }

    @DeleteMapping("/wishlist/{id}/delete")
    public String deleteWishlist(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                 @PathVariable long id
    ) {
        Wishlist wishlist = wishlistService.findById(id).orElseThrow(() -> new RuntimeException("해당 게시물을 찾을 수 없습니다."));
        if (!wishlistService.canAccess(userPrincipal.getMember(), wishlist)) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        wishlistService.deleteWishlist(wishlist);

        return "redirect:/mypage/wishlist";
    }

    @GetMapping("/address")
    public String myAddressListForm(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        List<AddressResponse> myAddressList = addressService.getMyAddressList(userPrincipal.getMember().getId());

        model.addAttribute("myAddressList", myAddressList);
        return "domain/member/mypage/address";
    }

    @GetMapping("/address/create")
    public String createAddressForm(@AuthenticationPrincipal UserPrincipal userPrincipal, AddressRequest addressRequest,
                                    Model model) {
        return "domain/member/mypage/addAddress";
    }

    @PostMapping("/address/create")
    public String createAddress(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @Valid AddressRequest addressRequest,
                                BindingResult bindingResult,
                                Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "domain/member/mypage/addAddress";
        }

        addressService.createAddress(userPrincipal, addressRequest);

        return "redirect:/mypage/address";
    }

    @GetMapping("/address/{id}/modify")
    public String modifyAddressForm(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                    @PathVariable long id,
                                    AddressRequest addressRequest,
                                    Model model
    ) {
        Address address = addressService.findById(id).orElseThrow(() -> new RuntimeException("해당 게시물을 찾을 수 없습니다."));
        if (!addressService.canAccess(userPrincipal.getMember(), address)) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        addressRequest.setAddressName(address.getAddressName());
        addressRequest.setPostcode(address.getPostcode());
        addressRequest.setAddress(address.getAddress());
        addressRequest.setDetailAddress(address.getDetailAddress());
        addressRequest.setExtraAddress(address.getExtraAddress());
        addressRequest.setPrimary(address.isPrimary());

        model.addAttribute("addressRequest", addressRequest);

        return "domain/member/mypage/modifyAddress";
    }

    @PutMapping("/address/{id}/modify")
    public String modifyAddress(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @PathVariable long id,
                                AddressRequest addressRequest,
                                BindingResult bindingResult,
                                Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "domain/member/mypage/modifyAddress";
        }

        addressService.modifyAddress(id, addressRequest, userPrincipal.getMember().getId());

        return "redirect:/mypage/address";
    }

    @DeleteMapping("/address/{id}/delete")
    public String deleteAddress(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @PathVariable long id
    ) {
        Address address = addressService.findById(id).orElseThrow(() -> new RuntimeException("해당 게시물을 찾을 수 없습니다."));
        if (!addressService.canAccess(userPrincipal.getMember(), address)) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        addressService.deleteAddress(address);

        return "redirect:/mypage/address";
    }

    @GetMapping("/buy/history")
    public String buyHistory(@AuthenticationPrincipal UserPrincipal user,
                             Model model) {
        List<BuyResponse> buyHistory = memberService.findMyBuyHistory(user.getMember());
        model.addAttribute("data", buyHistory);
        return "domain/member/mypage/buy_history";
    }

    @GetMapping("/sale/history")
    public String saleHistory(@AuthenticationPrincipal UserPrincipal user, Model model) {
        List<SaleResponse> saleHistory = memberService.findMySaleHistory(user.getMember());
        model.addAttribute("data", saleHistory);
        return "domain/member/mypage/sale_history";
    }

    @GetMapping("/order/trading/buy")
    public String tradingOrderBuy(@AuthenticationPrincipal UserPrincipal user, Model model) {
        List<OrderResponse> tradingOrder = orderService.findTradingOrderBuy(user.getMember());
        model.addAttribute("data", tradingOrder);

        return "domain/member/mypage/trading_order_buy";
    }

    @GetMapping("/order/trading/sale")
    public String tradingOrderSale(@AuthenticationPrincipal UserPrincipal user, Model model) {
        List<OrderResponse> tradingOrder = orderService.findTradingOrderSale(user.getMember());
        log.info("[MyPageController.tradingOrderSale] obj size : " + tradingOrder.size());
        model.addAttribute("data", tradingOrder);

        return "domain/member/mypage/trading_order_sale";
    }

    //운송장번호 등록(수정)
    @PostMapping("/order/trading/modify")
    public String tradingOrderModify(@RequestParam("id") long id,
                                     @RequestParam("deliveryNumber") long deliveryNumber,
                                     @AuthenticationPrincipal UserPrincipal user) {
        orderService.updateDeliveryNumber(id, deliveryNumber, user.getMember());
        return "redirect:/mypage/order/trading/sale";
    }

    // 판매자 정산 내역
    @GetMapping("/settlement")
    public String settlementOrder(@AuthenticationPrincipal UserPrincipal user, Model model) {
        List<OrderResponse> saleOrder = orderService.settlementOrder(user.getMember());
        model.addAttribute("data", saleOrder);
        return "domain/member/mypage/settlement";
    }

}
