package com.ll.hype.global.init;

import com.ll.hype.domain.admin.admin.service.AdminService;
import com.ll.hype.domain.adress.adress.entity.Address;
import com.ll.hype.domain.adress.adress.repository.AddressRepository;
import com.ll.hype.domain.brand.brand.dto.BrandRequest;
import com.ll.hype.domain.brand.brand.repository.BrandRepository;
import com.ll.hype.domain.member.member.dto.JoinRequest;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.entity.MemberRole;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.member.member.service.MemberService;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.order.sale.repository.SaleRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.repository.ShoesSizeRepository;
import com.ll.hype.domain.wishlist.wishlist.entity.Wishlist;
import com.ll.hype.domain.wishlist.wishlist.repository.WishlistRepository;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.enums.Status;
import com.ll.hype.global.enums.StatusCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Profile("!prod")
@RequiredArgsConstructor
@Configuration
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;

    private final MemberService memberService;
    private final AdminService adminService;
    private final BrandRepository brandRepository;
    private final MemberRepository memberRepository;
    private final ShoesSizeRepository shoesSizeRepository;
    private final ShoesRepository shoesRepository;
    private final BuyRepository orderRequestRepository;
    private final AddressRepository addressRepository;
    private final SaleRepository saleRepository;
    private final WishlistRepository wishlistRepository;

    @Bean
    @Order(3)
    public ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() throws IOException {
        if (memberService.existsByEmail("admin@admin.com")) {
            return;
        }

        JoinRequest member = JoinRequest.builder()
                .email("admin@admin.com")
                .password("1234")
                .passwordConfirm("1234")
                .name("admin")
                .nickname("admin")
                .phoneNumber("010-4193-2693")
                .birthday(LocalDate.of(1995, 10, 27))
                .gender(Gender.MALE)
                .shoesSize(265)
                .build();

        memberService.join(member);
        Member findMember = memberRepository.findByEmail("admin@admin.com").get();
        findMember.updateRole(MemberRole.ADMIN);

        JoinRequest member2 = JoinRequest.builder()
                .email("test@test.com")
                .password("test")
                .passwordConfirm("test")
                .name("테스트")
                .nickname("test")
                .phoneNumber("010-0000-1111")
                .birthday(LocalDate.of(2000, 1, 1))
                .gender(Gender.FEMALE)
                .shoesSize(230)
                .build();
        memberService.join(member2);
        Member userMember = memberRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new IllegalArgumentException("NotProd userMember 생성 중 조회 오류 발생"));
        userMember.updateRole(MemberRole.MEMBER);

        // ===== 이미지 객체 불러오기 시작 =====
        File file = new File(
                getClass().getClassLoader().getResource("img/KakaoTalk_Photo_2022-09-07-14-41-27.jpeg").getFile());
        InputStream stream = new FileInputStream(file);
        MultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE,
                stream);

        List<MultipartFile> files = new ArrayList<>();
        files.add(mockMultipartFile);
        // ===== 이미지 객체 불러오기 끝 =====

        IntStream.rangeClosed(1, 2).forEach(i -> {
            BrandRequest brandRequest =
                    BrandRequest.builder()
                            .korName("나이키" + i)
                            .engName("NIKE" + i)
                            .status(StatusCode.ENABLE)
                            .build();

            adminService.saveBrand(brandRequest, files);
        });

        BrandRequest brandRequest =
                BrandRequest.builder()
                        .korName("아디다스")
                        .engName("ADIDAS")
                        .status(StatusCode.ENABLE)
                        .build();

        adminService.saveBrand(brandRequest, files);

        List<Integer> sizes = List.of(220, 230, 240, 250, 260, 270);
        log.info("[size create size(0)] : " + sizes);

        IntStream.rangeClosed(1, 3).forEach(i -> {
            ShoesRequest shoesRequest =
                    ShoesRequest.builder()
                            .brand(brandRepository.findById(1L).get())
                            .korName("나이키 에어맥스" + i)
                            .engName("NIKE AIRMAX" + i)
                            .gender(Gender.MALE)
                            .model("NikeModel" + i)
                            .status(StatusCode.ENABLE)
                            .shoesCategory(ShoesCategory.RUNNING)
                            .release(LocalDate.of(2024, 1, 24))
                            .price(1000 + i)
                            .color("yellow")
                            .build();

            adminService.saveShoes(shoesRequest, sizes, files);
        });

        IntStream.rangeClosed(1, 3).forEach(i -> {
            ShoesRequest shoesRequest =
                    ShoesRequest.builder()
                            .brand(brandRepository.findById(1L).get())
                            .korName("아디다스 쌈바" + i)
                            .engName("ADIDAS SAMBA" + i)
                            .gender(Gender.MALE)
                            .model("ADIDAS SAMBA" + i)
                            .status(StatusCode.ENABLE)
                            .shoesCategory(ShoesCategory.RUNNING)
                            .release(LocalDate.of(2024, 1, 24))
                            .price(1000 + i)
                            .color("yellow")
                            .build();

            adminService.saveShoes(shoesRequest, sizes, files);
        });

        Address address = Address.builder()
                .member(findMember)
                .addressName("관리자 집 지번")
                .postcode("07685")
                .address("서울시 강서구 화곡동 993-15")
                .detailAddress("502호")
                .extraAddress(null)
                .isPrimary(true)
                .build();

        addressRepository.save(address);

        Address address2 = Address.builder()
                .member(findMember)
                .addressName("관리자 집 도로명")
                .postcode("07685")
                .address("서울 강서구 화곡로55길 33-14")
                .detailAddress("502호")
                .extraAddress("(화곡동, 훼밀리빌)")
                .isPrimary(false)
                .build();

        addressRepository.save(address2);

        Address address3 = Address.builder()
                .member(userMember)
                .addressName("TEST 집 지번")
                .postcode("123456")
                .address("경기도 화성시 오산동 17-115")
                .detailAddress("1001동 1002호")
                .extraAddress(null)
                .isPrimary(true)
                .build();

        addressRepository.save(address3);

        Address address4 = Address.builder()
                .member(userMember)
                .addressName("TEST 집 도로명")
                .postcode("123456")
                .address("경기도 화성시 동탄대로 17")
                .detailAddress("1001동 1002호")
                .extraAddress("(오산동, 푸르지오 아파트 2차)")
                .isPrimary(false)
                .build();

        addressRepository.save(address4);

        Shoes shoes = shoesRepository.findById(1L).get();

        //
        log.info("[shoes.size] : " + shoes.getSizes().size());
        log.info("[shoes.size] : " + shoes.getSizes().get(0));

        Buy orderRequest = Buy.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(1))
                .member(userMember)
                .price(125000L)
                .startDate(LocalDate.of(2024, 1, 25))
                .endDate(LocalDate.of(2024, 1, 30))
                .address(address3.getFullAddress())
                .status(Status.BIDDING)
                .build();

        orderRequestRepository.save(orderRequest);

        Buy orderRequest2 = Buy.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(2))
                .member(userMember)
                .price(150000L)
                .startDate(LocalDate.of(2024, 1, 26)) // 다른 시작 날짜
                .endDate(LocalDate.of(2024, 2, 5)) // 다른 종료 날짜
                .address(address4.getFullAddress())
                .status(Status.BIDDING)
                .build();
        orderRequestRepository.save(orderRequest2);

        Sale saleRequest = Sale.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(3))
                .member(findMember)
                .price(300000L)
                .startDate(LocalDate.of(2024, 1, 26)) // 다른 시작 날짜
                .endDate(LocalDate.of(2024, 2, 5)) // 다른 종료 날짜
                .address(address2.getFullAddress())
                .status(Status.BIDDING)
                .account("1234-5678-999999-10")
                .build();
        saleRepository.save(saleRequest);

        Sale saleRequest2 = Sale.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(4))
                .member(findMember)
                .price(400000L)
                .startDate(LocalDate.of(2024, 1, 26)) // 다른 시작 날짜
                .endDate(LocalDate.of(2024, 2, 5)) // 다른 종료 날짜
                .address(address2.getFullAddress())
                .status(Status.BIDDING)
                .account("1234-5678-999999-10")
                .build();
        saleRepository.save(saleRequest2);

        Sale saleRequest3 = Sale.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(5))
                .member(findMember)
                .price(400000L)
                .startDate(LocalDate.of(2024, 1, 1)) // 다른 시작 날짜
                .endDate(LocalDate.of(2024, 1, 31)) // 다른 종료 날짜
                .address(address2.getFullAddress())
                .status(Status.EXPIRED)
                .account("1234-5678-999999-10")
                .build();
        saleRepository.save(saleRequest3);

        Sale saleRequest4 = Sale.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(5))
                .member(findMember)
                .price(450000L)
                .startDate(LocalDate.of(2024, 1, 1)) // 다른 시작 날짜
                .endDate(LocalDate.of(2024, 1, 31)) // 다른 종료 날짜
                .address(address2.getFullAddress())
                .status(Status.EXPIRED)
                .account("1234-5678-999999-10")
                .build();
        saleRepository.save(saleRequest4);

        Wishlist wishlist = Wishlist.builder()
                .member(userMember)
                .shoesSize(shoes.getSizes().get(1))
                .build();
        wishlistRepository.save(wishlist);

        Wishlist wishlist2 = Wishlist.builder()
                .member(userMember)
                .shoesSize(shoes.getSizes().get(2))
                .build();
        wishlistRepository.save(wishlist2);

        Wishlist wishlist3 = Wishlist.builder()
                .member(userMember)
                .shoesSize(shoes.getSizes().get(3))
                .build();
        wishlistRepository.save(wishlist3);
    }
}
