package com.ll.hype.global.init;

import com.ll.hype.domain.address.address.entity.Address;
import com.ll.hype.domain.address.address.repository.AddressRepository;
import com.ll.hype.domain.admin.admin.service.AdminService;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    
    private MultipartFile getMultipartFile(String imagePath) throws IOException {
        File file = new File(
                getClass().getClassLoader().getResource(imagePath).getFile());
        InputStream stream = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);
    }

    @Transactional
    public void work1() throws IOException {
        if (memberService.existsByEmail("admin@admin.com")) {
            return;
        }
        // ===== 이미지 객체 불러오기 시작 =====
        File profileFile = new File(
                getClass().getClassLoader().getResource("img/profile.webp").getFile());
        InputStream profileStream = new FileInputStream(profileFile);
        MultipartFile profileMockMultipartFile = new MockMultipartFile("file", profileFile.getName(), MediaType.IMAGE_JPEG_VALUE,
                profileStream);

        List<MultipartFile> profileFiles = new ArrayList<>();
        profileFiles.add(profileMockMultipartFile);
        // ===== 이미지 객체 불러오기 끝 =====


        JoinRequest member = JoinRequest.builder()
                .email("admin@admin.com")
                .password("1234")
                .passwordConfirm("1234")
                .name("admin")
                .nickname("admin")
                .phoneNumber(1041932693L)
                .birthday(LocalDate.of(1995, 10, 27))
                .gender(Gender.MALE)
                .shoesSize(265)
                .build();

        memberService.join(member, profileFiles);
        Member admin = memberRepository.findByEmail("admin@admin.com").get();
        admin.updateRole(MemberRole.ADMIN);

        JoinRequest member2 = JoinRequest.builder()
                .email("store@store.com")
                .password("1234")
                .passwordConfirm("1234")
                .name("storeManager")
                .nickname("storeManager")
                .phoneNumber(1041932694L)
                .birthday(LocalDate.of(1995, 10, 27))
                .gender(Gender.MALE)
                .shoesSize(265)
                .build();

        memberService.join(member2, profileFiles);
        Member storeManager = memberRepository.findByEmail("store@store.com").get();
        storeManager.updateRole(MemberRole.STORE_MANAGER);

        JoinRequest member3 = JoinRequest.builder()
                .email("super@super.com")
                .password("1234")
                .passwordConfirm("1234")
                .name("superVisor")
                .nickname("superVisor")
                .phoneNumber(1041932695L)
                .birthday(LocalDate.of(1995, 10, 27))
                .gender(Gender.MALE)
                .shoesSize(265)
                .build();

        memberService.join(member3, profileFiles);
        Member superVisor = memberRepository.findByEmail("super@super.com").get();
        superVisor.updateRole(MemberRole.SUPER_VISOR);

        JoinRequest member4 = JoinRequest.builder()
                .email("test1@test.com")
                .password("test")
                .passwordConfirm("test")
                .name("테스트1")
                .nickname("test")
                .phoneNumber(1000001111L)
                .birthday(LocalDate.of(2000, 1, 1))
                .gender(Gender.FEMALE)
                .shoesSize(230)
                .build();
        memberService.join(member4, profileFiles);
        Member userMember1 = memberRepository.findByEmail("test1@test.com")
                .orElseThrow(() -> new IllegalArgumentException("NotProd userMember 생성 중 조회 오류 발생"));
        userMember1.updateRole(MemberRole.MEMBER);

        JoinRequest member5 = JoinRequest.builder()
                .email("test2@test.com")
                .password("test")
                .passwordConfirm("test")
                .name("테스트2")
                .nickname("test2")
                .phoneNumber(1000001112L)
                .birthday(LocalDate.of(2000, 1, 1))
                .gender(Gender.FEMALE)
                .shoesSize(230)
                .build();
        memberService.join(member5, profileFiles);
        Member userMember2 = memberRepository.findByEmail("test2@test.com")
                .orElseThrow(() -> new IllegalArgumentException("NotProd userMember 생성 중 조회 오류 발생"));
        userMember2.updateRole(MemberRole.MEMBER);

        JoinRequest member6 = JoinRequest.builder()
                .email("test3@test.com")
                .password("test")
                .passwordConfirm("test")
                .name("테스트3")
                .nickname("test3")
                .phoneNumber(1000001113L)
                .birthday(LocalDate.of(2000, 1, 1))
                .gender(Gender.FEMALE)
                .shoesSize(230)
                .build();
        memberService.join(member6, profileFiles);
        Member userMember3 = memberRepository.findByEmail("test3@test.com")
                .orElseThrow(() -> new IllegalArgumentException("NotProd userMember 생성 중 조회 오류 발생"));
        userMember3.updateRole(MemberRole.MEMBER);

        JoinRequest member7 = JoinRequest.builder()
                .email("test4@test.com")
                .password("test")
                .passwordConfirm("test")
                .name("테스트4")
                .nickname("test4")
                .phoneNumber(1000001114L)
                .birthday(LocalDate.of(2000, 1, 1))
                .gender(Gender.FEMALE)
                .shoesSize(230)
                .build();
        memberService.join(member7, profileFiles);
        Member userMember4 = memberRepository.findByEmail("test4@test.com")
                .orElseThrow(() -> new IllegalArgumentException("NotProd userMember 생성 중 조회 오류 발생"));
        userMember4.updateRole(MemberRole.MEMBER);

        JoinRequest member8 = JoinRequest.builder()
                .email("test5@test.com")
                .password("test")
                .passwordConfirm("test")
                .name("테스트5")
                .nickname("test5")
                .phoneNumber(1000001115L)
                .birthday(LocalDate.of(2000, 1, 1))
                .gender(Gender.FEMALE)
                .shoesSize(230)
                .build();
        memberService.join(member8, profileFiles);
        Member userMember5 = memberRepository.findByEmail("test5@test.com")
                .orElseThrow(() -> new IllegalArgumentException("NotProd userMember 생성 중 조회 오류 발생"));
        userMember5.updateRole(MemberRole.MEMBER);

        JoinRequest member9 = JoinRequest.builder()
                .email("test6@test.com")
                .password("test")
                .passwordConfirm("test")
                .name("테스트6")
                .nickname("test6")
                .phoneNumber(1000001116L)
                .birthday(LocalDate.of(2000, 1, 1))
                .gender(Gender.FEMALE)
                .shoesSize(230)
                .build();
        memberService.join(member9, profileFiles);
        Member userMember6 = memberRepository.findByEmail("test6@test.com")
                .orElseThrow(() -> new IllegalArgumentException("NotProd userMember 생성 중 조회 오류 발생"));
        userMember6.updateRole(MemberRole.MEMBER);

        JoinRequest member10 = JoinRequest.builder()
                .email("test7@test.com")
                .password("test")
                .passwordConfirm("test")
                .name("테스트7")
                .nickname("test7")
                .phoneNumber(1000001117L)
                .birthday(LocalDate.of(2000, 1, 1))
                .gender(Gender.FEMALE)
                .shoesSize(230)
                .build();
        memberService.join(member10, profileFiles);
        Member userMember7 = memberRepository.findByEmail("test7@test.com")
                .orElseThrow(() -> new IllegalArgumentException("NotProd userMember 생성 중 조회 오류 발생"));
        userMember7.updateRole(MemberRole.MEMBER);

        MultipartFile mockMultipartFile = getMultipartFile("img/KakaoTalk_Photo_2022-09-07-14-41-27.jpeg");
        MultipartFile mockMultipartFile2 = getMultipartFile("img/profile.webp");
        MultipartFile mockMultipartFile3 = getMultipartFile("img/n_logo.jpeg");
        MultipartFile mockMultipartFile4 = getMultipartFile("img/nike1-1.png");
        MultipartFile mockMultipartFile5 = getMultipartFile("img/nike1-3.webp");
        MultipartFile mockMultipartFile6 = getMultipartFile("img/nike2-1.webp");
        MultipartFile mockMultipartFile7 = getMultipartFile("img/nike2-2.webp");
        MultipartFile mockMultipartFile8 = getMultipartFile("img/nike3-1.webp");
        MultipartFile mockMultipartFile9 = getMultipartFile("img/nike3-3.webp");
        MultipartFile mockMultipartFile10 = getMultipartFile("img/ad1.webp");
        MultipartFile mockMultipartFile11 = getMultipartFile("img/ad2.webp");
        MultipartFile mockMultipartFile12 = getMultipartFile("img/ad3.webp");
        MultipartFile mockMultipartFile13 = getMultipartFile("img/as1-1.webp");
        MultipartFile mockMultipartFile14 = getMultipartFile("img/as1-2.webp");
        MultipartFile mockMultipartFile15 = getMultipartFile("img/as2.webp");
        MultipartFile mockMultipartFile16 = getMultipartFile("img/as3-1.webp");
        MultipartFile mockMultipartFile17 = getMultipartFile("img/as3-2.webp");
        MultipartFile mockMultipartFile18 = getMultipartFile("img/bs1-1.webp");
        MultipartFile mockMultipartFile19 = getMultipartFile("img/bs1-2.webp");
        MultipartFile mockMultipartFile20 = getMultipartFile("img/bs2-1.webp");
        MultipartFile mockMultipartFile21 = getMultipartFile("img/bs3-1.webp");
        MultipartFile mockMultipartFile22 = getMultipartFile("img/bs3-2.webp");
        MultipartFile mockMultipartFile23 = getMultipartFile("img/c1-1.webp");
        MultipartFile mockMultipartFile24 = getMultipartFile("img/c2-1.webp");
        MultipartFile mockMultipartFile25 = getMultipartFile("img/c3-1.webp");
        MultipartFile mockMultipartFile26 = getMultipartFile("img/c3-2.webp");
        MultipartFile mockMultipartFile27 = getMultipartFile("img/j1-1.webp");
        MultipartFile mockMultipartFile28 = getMultipartFile("img/j2-1.webp");
        MultipartFile mockMultipartFile29 = getMultipartFile("img/j2-3.webp");
        MultipartFile mockMultipartFile30 = getMultipartFile("img/j3-1.webp");
        MultipartFile mockMultipartFile31 = getMultipartFile("img/j3-3.webp");
        MultipartFile mockMultipartFile32 = getMultipartFile("img/ma1-1.webp");
        MultipartFile mockMultipartFile33 = getMultipartFile("img/ma1-2.webp");
        MultipartFile mockMultipartFile34 = getMultipartFile("img/ma2-1.webp");
        MultipartFile mockMultipartFile35 = getMultipartFile("img/ma2-2.webp");
        MultipartFile mockMultipartFile36 = getMultipartFile("img/ma3-1.webp");
        MultipartFile mockMultipartFile37 = getMultipartFile("img/nb1-1.webp");
        MultipartFile mockMultipartFile38 = getMultipartFile("img/nb1-3.webp");
        MultipartFile mockMultipartFile39 = getMultipartFile("img/nb2-1.webp");
        MultipartFile mockMultipartFile40 = getMultipartFile("img/nb2-2.webp");
        MultipartFile mockMultipartFile41 = getMultipartFile("img/nb3-1.webp");
        MultipartFile mockMultipartFile42 = getMultipartFile("img/p1-1.webp");
        MultipartFile mockMultipartFile43 = getMultipartFile("img/p2-1.webp");
        MultipartFile mockMultipartFile44 = getMultipartFile("img/p3-1.webp");
        MultipartFile mockMultipartFile45 = getMultipartFile("img/sal1-1.webp");
        MultipartFile mockMultipartFile46 = getMultipartFile("img/sal2-1.webp");
        MultipartFile mockMultipartFile47 = getMultipartFile("img/sal3-1.webp");


        List<MultipartFile> files = new ArrayList<>();
       // List<MultipartFile> filesMulti = new ArrayList<>();

////        files.add(mockMultipartFile);
//        filesMulti.add(mockMultipartFile);
//        filesMulti.add(mockMultipartFile2);
//        filesMulti.add(mockMultipartFile);
//        filesMulti.add(mockMultipartFile);
//        filesMulti.add(mockMultipartFile); // 5개를 똑같은거 놓은것
//        // ===== 이미지 객체 불러오기 끝 =====

            BrandRequest brandRequest1 =
                    BrandRequest.builder()
                            .korName("나이키")
                            .engName("Nike")
                            .status(StatusCode.ENABLE)
                            .build();
            files = List.of(getMultipartFile("img/n_logo.jpeg"));
            adminService.saveBrand(brandRequest1, files);


        BrandRequest brandRequest2 =
                BrandRequest.builder()
                        .korName("아디다스")
                        .engName("Adidias")
                        .status(StatusCode.ENABLE)
                        .build();
        files = List.of(getMultipartFile("img/ad_logo.jpeg"));
        adminService.saveBrand(brandRequest2, files);

        BrandRequest brandRequest3 =
                BrandRequest.builder()
                        .korName("뉴발란스")
                        .engName("New Balance")
                        .status(StatusCode.ENABLE)
                        .build();
        files = List.of(getMultipartFile("img/n_logo.jpeg"));
        adminService.saveBrand(brandRequest3, files);

        BrandRequest brandRequest4 =
                BrandRequest.builder()
                        .korName("아식스")
                        .engName("Asics")
                        .status(StatusCode.ENABLE)
                        .build();
        files = List.of(getMultipartFile("img/as_logo.jpeg"));
        adminService.saveBrand(brandRequest4, files);

        BrandRequest brandRequest5 =
                BrandRequest.builder()
                        .korName("퓨마")
                        .engName("Puma")
                        .status(StatusCode.ENABLE)
                        .build();
        files = List.of(getMultipartFile("img/p_logo.jpeg"));
        adminService.saveBrand(brandRequest5, files);

        BrandRequest brandRequest6 =
                BrandRequest.builder()
                        .korName("조던")
                        .engName("Jordan")
                        .status(StatusCode.ENABLE)
                        .build();
        files = List.of(getMultipartFile("img/j_logo.jpeg"));
        adminService.saveBrand(brandRequest6, files);

        BrandRequest brandRequest7 =
                BrandRequest.builder()
                        .korName("살로몬")
                        .engName("Salomon")
                        .status(StatusCode.ENABLE)
                        .build();
        files = List.of(getMultipartFile("img/s_logo.jpeg"));
        adminService.saveBrand(brandRequest7, files);

        BrandRequest brandRequest8 =
                BrandRequest.builder()
                        .korName("미하라 야스히")
                        .engName("Mihara Yasuhiro")
                        .status(StatusCode.ENABLE)
                        .build();
        files = List.of(getMultipartFile("img/m_logo.jpeg"));
        adminService.saveBrand(brandRequest8, files);

        BrandRequest brandRequest9 =
                BrandRequest.builder()
                        .korName("버켄스탁")
                        .engName("Birkenstock")
                        .status(StatusCode.ENABLE)
                        .build();
        files = List.of(getMultipartFile("img/b_logo.jpeg"));
        adminService.saveBrand(brandRequest9, files);

        BrandRequest brandRequest10 =
                BrandRequest.builder()
                        .korName("크록스")
                        .engName("Crocs")
                        .status(StatusCode.ENABLE)
                        .build();
        files = List.of(getMultipartFile("img/c_logo.jpeg"));
        adminService.saveBrand(brandRequest10, files);


        List<Integer> sizes = List.of(220, 230, 240, 250, 260, 270);
        log.info("[size create size(0)] : " + sizes);

        ShoesRequest shoesRequest1 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("나이키 에어포스 1 '07 WB 플랙스")
                        .engName("Nike Air Force 1 '07 WB Flax")
                        .gender(Gender.MALE)
                        .model("CJ9179-200")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.SNEAKERS)
                        .release(LocalDate.of(2019, 9, 10))
                        .price(169000)
                        .color("Light Brown")
                        .build();
        files = List.of(getMultipartFile("img/nike1-1.png"),getMultipartFile("img/nike1-3.webp"));
        adminService.saveShoes(shoesRequest1, sizes, files);

        ShoesRequest shoesRequest2 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("나이키 줌 보메로 5 포톤 더스트 앤 메탈릭 실버")
                        .engName("Nike Zoom Vomero 5 Photon Dust and Metallic Silver")
                        .gender(Gender.FEMALE)
                        .model("FD0884-025")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2023, 2, 28))
                        .price(189000)
                        .color("Metallic Silver")
                        .build();

        files = List.of(getMultipartFile("img/nike2-1.webp"),getMultipartFile("img/nike2-2.webp"));
        adminService.saveShoes(shoesRequest2, sizes, files);

        ShoesRequest shoesRequest3 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("나이키 x 마틴 로즈 샥스 MR4 블랙")
                        .engName("Nike x Martine Rose Shox MR4 Black")
                        .gender(Gender.FEMALE)
                        .model("DQ2401-001")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2022, 7, 14))
                        .price(239000)
                        .color("Black")
                        .build();

        files = List.of(getMultipartFile("img/nike3-1.webp"),getMultipartFile("img/nike3-3.webp"));
        adminService.saveShoes(shoesRequest3, sizes, files);

        ShoesRequest shoesRequest4 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("아디다스 삼바 OG 블랙 화이트 검")
                        .engName("Adidas Samba OG Black White Gum")
                        .gender(Gender.FEMALE)
                        .model("B75807")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.SNEAKERS)
                        .release(LocalDate.of(2018, 6, 7))
                        .price(139000)
                        .color("Black")
                        .build();

        files = List.of(getMultipartFile("img/ad1.webp"));
        adminService.saveShoes(shoesRequest4, sizes, files);

        ShoesRequest shoesRequest5 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("아디다스 x 웨일스 보너 포니 토널 삼바 코어 블랙 크림 화이트")
                        .engName("Adidas x Wales Bonner Pony Tonal Samba Core Black Cream White")
                        .gender(Gender.FEMALE)
                        .model("IE0580")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.SNEAKERS)
                        .release(LocalDate.of(2023, 11, 8))
                        .price(259000)
                        .color("Black Cream White")
                        .build();
        files = List.of(getMultipartFile("img/ad2.webp"));
        adminService.saveShoes(shoesRequest5, sizes, files);

        ShoesRequest shoesRequest6 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("아디다스 이지 부스트 350 V2 스태틱 2023")
                        .engName("Adidas Yeezy Boost 350 V2 Static 2023")
                        .gender(Gender.MALE)
                        .model("EF2905")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2023, 8, 21))
                        .price(319000)
                        .color("Static")
                        .build();

        files = List.of(getMultipartFile("img/ad3.webp"));
        adminService.saveShoes(shoesRequest6, sizes, files);


        ShoesRequest shoesRequest7 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("푸마 스피드캣 LS 블랙 화이트")
                        .engName("Puma Speedcat LS Black White")
                        .gender(Gender.MALE)
                        .model("380173-01")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2024, 1, 24))
                        .price(139000)
                        .color("Black")
                        .build();
        files = List.of(getMultipartFile("img/p1-1.webp"));
        adminService.saveShoes(shoesRequest7, sizes, files);

        ShoesRequest shoesRequest8 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("푸마 스피드캣 LS 하이 리스크 레드 화이트")
                        .engName("Puma Speedcat LS High Risk Red White")
                        .gender(Gender.MALE)
                        .model("380173-04")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2024, 1, 24))
                        .price(139000)
                        .color("Red")
                        .build();
        files = List.of(getMultipartFile("img/p2-1.webp"));
        adminService.saveShoes(shoesRequest8, sizes, files);

        ShoesRequest shoesRequest9 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("푸마 스피드캣 OG 스파르코 피코트 화이트")
                        .engName("Puma Speedcat OG Sparco Peacoat White")
                        .gender(Gender.MALE)
                        .model("307171-06")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2024, 1, 24))
                        .price(1000)
                        .color("yellow")
                        .build();
        files = List.of(getMultipartFile("img/p3-1.webp"));
        adminService.saveShoes(shoesRequest9, sizes, files);

        ShoesRequest shoesRequest10 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("조던 1 레트로 하이 OG 시카고 2022")
                        .engName("Jordan 1 Retro High OG Chicago 2022")
                        .gender(Gender.MALE)
                        .model("DZ5485-612")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.BASKETBALL)
                        .release(LocalDate.of(2022, 12, 1))
                        .price(209000)
                        .color("Varsity Red")
                        .build();
        files = List.of(getMultipartFile("img/j1-1.webp"));
        adminService.saveShoes(shoesRequest10, sizes, files);

        ShoesRequest shoesRequest11 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("조던 1 x 트래비스 스캇 레트로 로우 OG SP 블랙 팬텀")
                        .engName("Jordan 1 x Travis Scott Retro Low OG SP Black Phantom")
                        .gender(Gender.MALE)
                        .model("DM7866-001")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.SNEAKERS)
                        .release(LocalDate.of(2023, 3, 28))
                        .price(189000)
                        .color("Black")
                        .build();
        files = List.of(getMultipartFile("img/j2-1.webp"),getMultipartFile("img/j2-3.webp"));
        adminService.saveShoes(shoesRequest11, sizes, files);

        ShoesRequest shoesRequest12 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("조던 1 x 트래비스 스캇 x 프라그먼트 레트로 로우 OG SP 밀리터리 블루")
                        .engName("Jordan 1 x Travis Scott x Fragment Retro Low OG SP Military Blue")
                        .gender(Gender.MALE)
                        .model("DM7866-140")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.SNEAKERS)
                        .release(LocalDate.of(2021, 8, 13))
                        .price(189000)
                        .color("Military Blue")
                        .build();
        files = List.of(getMultipartFile("img/j3-1.webp"),getMultipartFile("img/j3-3.webp"));
        adminService.saveShoes(shoesRequest12, sizes, files);

        ShoesRequest shoesRequest13 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("뉴발란스 993 메이드 인 USA 그레이 - D 스탠다드")
                        .engName("New Balance 993 Made in USA Grey - D Standard")
                        .gender(Gender.MALE)
                        .model("MR993GL")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2018, 8, 21))
                        .price(259000)
                        .color("Grey/White")
                        .build();
        files = List.of(getMultipartFile("img/nb1-1.webp"),getMultipartFile("img/nb1-3.webp"));
        adminService.saveShoes(shoesRequest13, sizes, files);

        ShoesRequest shoesRequest14 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("뉴발란스 x JJJ자운드 991 메이드 인 UK 그레이")
                        .engName("New Balance x JJJJound 991 Made in UK Grey")
                        .gender(Gender.MALE)
                        .model("M991JJA")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2023, 2, 17))
                        .price(319000)
                        .color("Grey")
                        .build();
        files = List.of(getMultipartFile("img/nb2-1.webp"),getMultipartFile("img/nb2-2.webp"));
        adminService.saveShoes(shoesRequest14, sizes, files);

        ShoesRequest shoesRequest15 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("뉴발란스 x JJJ자운드 992 메이드 인 USA 그레이")
                        .engName("New Balance x JJJJound 992 Made in USA Grey")
                        .gender(Gender.MALE)
                        .model("M992J2")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2020, 9, 12))
                        .price(319000)
                        .color("Brown")
                        .build();
        files = List.of(getMultipartFile("img/nb3-1.webp"));
        adminService.saveShoes(shoesRequest15, sizes, files);

        ShoesRequest shoesRequest16 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("아식스 x 세실리에 반센 GT-2160 블랙")
                        .engName("Asics x Cecilie Bahnsen GT-2160 Black")
                        .gender(Gender.MALE)
                        .model("1203A321-001")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2023, 7, 19))
                        .price(259000)
                        .color("Black")
                        .build();
        files = List.of(getMultipartFile("img/as1-1.webp"),getMultipartFile("img/as1-2.webp"));
        adminService.saveShoes(shoesRequest16, sizes, files);

        ShoesRequest shoesRequest17 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("아식스 x 비비안 웨스트우드 젤 카야노 26 버치 화이트")
                        .engName("Asics x Vivienne Westwood Gel-Kayano 26 Birch White")
                        .gender(Gender.MALE)
                        .model("1021A320-202")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2020, 9, 28))
                        .price(320000)
                        .color("White")
                        .build();
        files = List.of(getMultipartFile("img/as2.webp"));
        adminService.saveShoes(shoesRequest17, sizes, files);

        ShoesRequest shoesRequest18 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("아식스 x 언어펙티드 젤 카야노 14 실버 문")
                        .engName("Asics x Unaffected Gel-Kayano 14 Silver Moon")
                        .gender(Gender.MALE)
                        .model("1201A922-020")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2023, 10, 27))
                        .price(229000)
                        .color("Dark Shadow")
                        .build();
        files = List.of(getMultipartFile("img/as3-1.webp"),getMultipartFile("img/as3-2.webp"));
        adminService.saveShoes(shoesRequest18, sizes, files);

        ShoesRequest shoesRequest19 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("살로몬 XT-6 ADV 블랙")
                        .engName("Salomon XT-6 ADV Black")
                        .gender(Gender.MALE)
                        .model("L41086600")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.TRAIL_RUNNING)
                        .release(LocalDate.of(2020, 4, 27))
                        .price(260000)
                        .color("yellow")
                        .build();
        files = List.of(getMultipartFile("img/sal1-1.webp"));
        adminService.saveShoes(shoesRequest19, sizes, files);

        ShoesRequest shoesRequest20 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("살로몬 XA 프로 3D 알로이 실버")
                        .engName("Salomon XA Pro 3D Alloy Silver")
                        .gender(Gender.MALE)
                        .model("L41617500")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.TRAIL_RUNNING)
                        .release(LocalDate.of(2024, 1, 24))
                        .price(175000)
                        .color("Silver")
                        .build();
        files = List.of(getMultipartFile("img/sal2-1.webp"));
        adminService.saveShoes(shoesRequest20, sizes, files);

        ShoesRequest shoesRequest21 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("Salomon x The Broken Arm RX Slide 3.0 Chinchilla Blue")
                        .engName("살로몬 x 더 브로큰 암 RX 슬라이드 3.0 친칠라 블루")
                        .gender(Gender.MALE)
                        .model("L40835100")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.MULE)
                        .release(LocalDate.of(2019, 5, 24))
                        .price(164400)
                        .color("Vanilla")
                        .build();
        files = List.of(getMultipartFile("img/sal3-1.webp"));
        adminService.saveShoes(shoesRequest21, sizes, files);

        ShoesRequest shoesRequest22 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("버켄스탁 보스턴 소프트 풋베드 토프 - 내로우")
                        .engName("Birkenstock Boston Soft Footbed Taupe - Narrow")
                        .gender(Gender.MALE)
                        .model("0560773")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.MULE)
                        .release(LocalDate.of(2024, 1, 24))
                        .price(169000)
                        .color("Taupe")
                        .build();
        files = List.of(getMultipartFile("img/bs1-1.webp"),getMultipartFile("img/bs1-2.webp"));
        adminService.saveShoes(shoesRequest22, sizes, files);

        ShoesRequest shoesRequest23 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("버켄스탁 x 스투시 보스턴 클로그 핑크")
                        .engName("Birkenstock x Stussy Boston Clog Pink")
                        .gender(Gender.MALE)
                        .model("1022971")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.MULE)
                        .release(LocalDate.of(2021, 8, 27))
                        .price(280800)
                        .color("Pink")
                        .build();
        files = List.of(getMultipartFile("img/bs2-1.webp"));
        adminService.saveShoes(shoesRequest23, sizes, files);

        ShoesRequest shoesRequest24 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("버켄스탁 보스턴 셔링 모카 - 레귤러")
                        .engName("Birkenstock Boston Shearling Mocha - Regular")
                        .gender(Gender.MALE)
                        .model("1020567")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.MULE)
                        .release(LocalDate.of(2024, 1, 24))
                        .price(225800)
                        .color("Mocha")
                        .build();
        files = List.of(getMultipartFile("img/bs3-1.webp"),getMultipartFile("img/bs3-2.webp"));
        adminService.saveShoes(shoesRequest24, sizes, files);

        ShoesRequest shoesRequest25 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("메종 미하라 야스히로 블레이키 OG 솔 캔버스 로우탑 스니커즈 블랙 화이트")
                        .engName("Maison Mihara Yasuhiro Blakey OG Sole Canvas Low-top Sneakers Black White")
                        .gender(Gender.FEMALE)
                        .model("A08FW735-BLACK")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.SNEAKERS)
                        .release(LocalDate.of(2021, 12, 4))
                        .price(313100)
                        .color("Black")
                        .build();
        files = List.of(getMultipartFile("img/ma1-1.webp"),getMultipartFile("img/ma1-2.webp"));
        adminService.saveShoes(shoesRequest25, sizes, files);

        ShoesRequest shoesRequest26 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("메종 미하라 야스히로 피터슨 OG 솔 캔버스 로우탑 스니커즈 내츄럴")
                        .engName("Maison Mihara Yasuhiro Peterson OG Sole Canvas Low-top Sneakers Natural")
                        .gender(Gender.MALE)
                        .model("A04FW729-NATURAL")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.SNEAKERS)
                        .release(LocalDate.of(2022, 10, 1))
                        .price(293600)
                        .color("yellow")
                        .build();
        files = List.of(getMultipartFile("img/ma2-1.webp"),getMultipartFile("img/ma2-2.webp"));
        adminService.saveShoes(shoesRequest26, sizes, files);

        ShoesRequest shoesRequest27 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("메종 미하라 야스히로 블레이키 VL OG 솔 캔버스 로우탑 스니커즈 블랙")
                        .engName("Maison Mihara Yasuhiro Blakey VL OG Sole Canvas Low-top Sneakers Black")
                        .gender(Gender.MALE)
                        .model("A11FW716-BLACK")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.RUNNING)
                        .release(LocalDate.of(2023, 9, 16))
                        .price(352300)
                        .color("Black")
                        .build();
        files = List.of(getMultipartFile("img/ma3-1.webp"));
        adminService.saveShoes(shoesRequest27, sizes, files);

        ShoesRequest shoesRequest28 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("크록스 x 산쿠안즈 클래식 클로그 블랙")
                        .engName("Crocs x Sankuanz Classic Clog Black")
                        .gender(Gender.MALE)
                        .model("206900-001")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.ETC)
                        .release(LocalDate.of(2021, 8, 5))
                        .price(169000)
                        .color("Black")
                        .build();
        files = List.of(getMultipartFile("img/c1-1.webp"));
        adminService.saveShoes(shoesRequest28, sizes, files);

        ShoesRequest shoesRequest29 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("크록스 에코 마블드 클로그 본 멀티")
                        .engName("Crocs Echo Marbled Clog Bone Multi")
                        .gender(Gender.MALE)
                        .model("208454-2Y3")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.ETC)
                        .release(LocalDate.of(2024, 1, 24))
                        .price(80000)
                        .color("Bone/Multi")
                        .build();
        files = List.of(getMultipartFile("img/c2-1.webp"));
        adminService.saveShoes(shoesRequest29, sizes, files);

        ShoesRequest shoesRequest30 =
                ShoesRequest.builder()
                        .brand(brandRepository.findById(1L).get())
                        .korName("크록스 x 10 꼬르소 꼬모 클래식 클로그 스투코")
                        .engName("Crocs x 10 Corso Como Classic Clog Stucco")
                        .gender(Gender.MALE)
                        .model("208168-160")
                        .status(StatusCode.ENABLE)
                        .shoesCategory(ShoesCategory.ETC)
                        .release(LocalDate.of(2022, 5, 20))
                        .price(1000)
                        .color("yellow")
                        .build();
        files = List.of(getMultipartFile("img/c3-1.webp"),getMultipartFile("img/c3-2.webp"));
        adminService.saveShoes(shoesRequest30, sizes, files);



        Address address = Address.builder()
                .member(admin)
                .addressName("관리자 집 지번")
                .postcode("07685")
                .address("서울시 강서구 화곡동 993-15")
                .detailAddress("502호")
                .extraAddress(null)
                .isPrimary(true)
                .build();

        addressRepository.save(address);

        Address address2 = Address.builder()
                .member(admin)
                .addressName("관리자 집 도로명")
                .postcode("07685")
                .address("서울 강서구 화곡로55길 33-14")
                .detailAddress("502호")
                .extraAddress("(화곡동, 훼밀리빌)")
                .isPrimary(false)
                .build();

        addressRepository.save(address2);

        Address address3 = Address.builder()
                .member(admin)
                .addressName("TEST 집 지번")
                .postcode("123456")
                .address("경기도 화성시 오산동 17-115")
                .detailAddress("1001동 1002호")
                .extraAddress(null)
                .isPrimary(false) // 대표 주소 여부  하나 대표주소 true 나머지 주소 false
                .build();

        addressRepository.save(address3);

        Address address4 = Address.builder()
                .member(storeManager)
                .addressName("TEST 집 도로명")
                .postcode("123456")
                .address("경기도 화성시 동탄대로 17")
                .detailAddress("1001동 1002호")
                .extraAddress("(오산동, 푸르지오 아파트 2차)")
                .isPrimary(true)
                .build();

        addressRepository.save(address4);

        Address address5 = Address.builder()
                .member(storeManager)
                .addressName("TEST 집 도로명")
                .postcode("78910")
                .address("경기도 화성시 동탄대로 13")
                .detailAddress("1001동 1001호")
                .extraAddress("(오산동, 자이 아파트)")
                .isPrimary(false)
                .build();

        addressRepository.save(address5);

        Address address6 = Address.builder()
                .member(storeManager)
                .addressName("TEST 집 도로명")
                .postcode("78910")
                .address("경북 구미시 금오산로6길 12")
                .detailAddress("1001동 1002호")
                .extraAddress("( 원평동, 아이파크더샵 )")
                .isPrimary(false)
                .build();

        addressRepository.save(address6);

        Address address7 = Address.builder()
                .member(superVisor)
                .addressName("TEST 집 도로명")
                .postcode("78910")
                .address("경북 구미시 도봉로5길 11")
                .detailAddress("101동 1002호")
                .extraAddress("(봉곡동, 그린빌)")
                .isPrimary(true)
                .build();

        addressRepository.save(address7);

        Address address8 = Address.builder()
                .member(superVisor)
                .addressName("TEST 집 도로명")
                .postcode("38910")
                .address("대구광역시 중구 국채보상로 655")
                .detailAddress("101동 1002호")
                .extraAddress("(화성파크 드림시티)")
                .isPrimary(false)
                .build();

        addressRepository.save(address8);

        Address address9 = Address.builder()
                .member(userMember1)
                .addressName("TEST 집 도로명")
                .postcode("28910")
                .address("대구 수성구 청수로 274")
                .detailAddress("1014 1005호")
                .extraAddress("(황금동, 힐스테이트)")
                .isPrimary(false)
                .build();

        addressRepository.save(address9);

        Address address10 = Address.builder()
                .member(userMember2)
                .addressName("TEST 집 도로명")
                .postcode("18910")
                .address("대구 수성구 달구벌대로 2435")
                .detailAddress("101동 1003호")
                .extraAddress("(범어동, 제니스 아파트)")
                .isPrimary(false)
                .build();

        addressRepository.save(address10);

        Address address11 = Address.builder()
                .member(userMember3)
                .addressName("TEST 집 도로명")
                .postcode("18910")
                .address("대구 수성구 달구벌대로 2435")
                .detailAddress("101동 1003호")
                .extraAddress("(범어동, 제니스 아파트)")
                .isPrimary(false)
                .build();

        addressRepository.save(address11);

        Address address12 = Address.builder()
                .member(userMember4)
                .addressName("TEST 집 도로명")
                .postcode("18910")
                .address("대구 수성구 달구벌대로 2435")
                .detailAddress("101동 1003호")
                .extraAddress("(범어동, 제니스 아파트)")
                .isPrimary(false)
                .build();

        addressRepository.save(address12);

        Address address13 = Address.builder()
                .member(userMember5)
                .addressName("TEST 집 도로명")
                .postcode("18910")
                .address("대구 수성구 달구벌대로 2435")
                .detailAddress("101동 1003호")
                .extraAddress("(범어동, 제니스 아파트)")
                .isPrimary(false)
                .build();

        addressRepository.save(address13);

        Address address14 = Address.builder()
                .member(userMember6)
                .addressName("TEST 집 도로명")
                .postcode("18910")
                .address("대구 수성구 달구벌대로 2435")
                .detailAddress("101동 1003호")
                .extraAddress("(범어동, 제니스 아파트)")
                .isPrimary(false)
                .build();

        addressRepository.save(address14);

        Address address15 = Address.builder()
                .member(userMember7)
                .addressName("TEST 집 도로명")
                .postcode("18910")
                .address("대구 수성구 달구벌대로 2435")
                .detailAddress("101동 1003호")
                .extraAddress("(범어동, 제니스 아파트)")
                .isPrimary(false)
                .build();

        addressRepository.save(address15);


        Shoes shoes = shoesRepository.findById(1L).get();
        Shoes shoes2 = shoesRepository.findById(2L).get();
        Shoes shoes3 = shoesRepository.findById(3L).get();
        Shoes shoes4 = shoesRepository.findById(4L).get();
        Shoes shoes5 = shoesRepository.findById(5L).get();
        Shoes shoes6 = shoesRepository.findById(6L).get();
        Shoes shoes7 = shoesRepository.findById(7L).get();
        Shoes shoes8 = shoesRepository.findById(8L).get();
        Shoes shoes9 = shoesRepository.findById(9L).get();
        Shoes shoes10 = shoesRepository.findById(10L).get();
        Shoes shoes11 = shoesRepository.findById(11L).get();
        Shoes shoes12 = shoesRepository.findById(12L).get();
        Shoes shoes13 = shoesRepository.findById(13L).get();
        Shoes shoes14 = shoesRepository.findById(14L).get();
        Shoes shoes15 = shoesRepository.findById(15L).get();
        Shoes shoes16 = shoesRepository.findById(16L).get();
        Shoes shoes17 = shoesRepository.findById(17L).get();
        Shoes shoes18 = shoesRepository.findById(18L).get();
        Shoes shoes19 = shoesRepository.findById(19L).get();
        Shoes shoes20 = shoesRepository.findById(20L).get();
        Shoes shoes21 = shoesRepository.findById(21L).get();
        Shoes shoes22 = shoesRepository.findById(22L).get();
        Shoes shoes23 = shoesRepository.findById(23L).get();
        Shoes shoes24 = shoesRepository.findById(24L).get();
        Shoes shoes25 = shoesRepository.findById(25L).get();
        Shoes shoes26 = shoesRepository.findById(26L).get();
        Shoes shoes27 = shoesRepository.findById(27L).get();
        Shoes shoes28 = shoesRepository.findById(28L).get();
        Shoes shoes29 = shoesRepository.findById(29L).get();
        Shoes shoes30 = shoesRepository.findById(30L).get(); // 30 개 다 가지고 와야한다  /buy, sale , wishlist 사용할 신발들


        log.info("[shoes.size] : " + shoes.getSizes().size());
        log.info("[shoes.size] : " + shoes.getSizes().get(0));

        Buy orderRequest = Buy.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(1))
                .member(admin)
                .price(125000L)
                .startDate(LocalDate.of(2024, 1, 25))
                .endDate(LocalDate.of(2024, 1, 30))
                .receiverAddress(address3.getFullAddress())
                .status(Status.BIDDING)
                .build();

        orderRequestRepository.save(orderRequest);

        Buy orderRequest2 = Buy.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(2))
                .member(admin)
                .price(150000L)
                .startDate(LocalDate.of(2024, 1, 26)) // 다른 시작 날짜
                .endDate(LocalDate.of(2024, 2, 5)) // 다른 종료 날짜
                .receiverAddress(address4.getFullAddress())
                .status(Status.BIDDING)
                .build();
        orderRequestRepository.save(orderRequest2);

        Sale saleRequest = Sale.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(3))
                .member(userMember1)
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
                .member(userMember2)
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
                .member(userMember3)
                .price(400000L)
                .startDate(LocalDate.of(2024, 1, 1)) // 다른 시작 날짜
                .endDate(LocalDate.of(2024, 1, 31)) // 다른 종료 날짜
                .address(address2.getFullAddress())
                .status(Status.BID_EXPIRED)
                .account("1234-5678-999999-10")
                .build();
        saleRepository.save(saleRequest3);

        Sale saleRequest4 = Sale.builder()
                .shoes(shoes)
                .shoesSize(shoes.getSizes().get(5))
                .member(userMember4)
                .price(450000L)
                .startDate(LocalDate.of(2024, 1, 1)) // 다른 시작 날짜
                .endDate(LocalDate.of(2024, 1, 31)) // 다른 종료 날짜
                .address(address2.getFullAddress())
                .status(Status.BID_EXPIRED)
                .account("1234-5678-999999-10")
                .build();
        saleRepository.save(saleRequest4);

        Wishlist wishlist = Wishlist.builder()
                .member(admin)
                .shoesSize(shoes.getSizes().get(1))
                .build();
        wishlistRepository.save(wishlist);

        Wishlist wishlist2 = Wishlist.builder()
                .member(admin)
                .shoesSize(shoes.getSizes().get(2))
                .build();
        wishlistRepository.save(wishlist2);

        Wishlist wishlist3 = Wishlist.builder()
                .member(admin)
                .shoesSize(shoes.getSizes().get(3))
                .build();
        wishlistRepository.save(wishlist3);
    }
}
