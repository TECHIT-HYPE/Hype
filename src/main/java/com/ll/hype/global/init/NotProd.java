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
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.repository.ShoesSizeRepository;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.enums.Status;
import com.ll.hype.global.enums.StatusCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
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
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

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

    @Bean
    @Order(3)
    public ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() throws IOException {
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


        // ===== 이미지 객체 불러오기 시작 =====
        File file = new File(getClass().getClassLoader().getResource("img/KakaoTalk_Photo_2022-09-07-14-41-27.jpeg").getFile());
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

        IntStream.rangeClosed(1, 100).forEach(i -> {
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

            adminService.saveShoes(shoesRequest, sizes);
        });

        IntStream.rangeClosed(1, 100).forEach(i -> {
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

            adminService.saveShoes(shoesRequest, sizes);
        });

        Shoes shoes = shoesRepository.findById(1L).get();
        ShoesSize shoesSize = ShoesSize.builder()
                .shoes(shoes)
                .size(260)
                .build();

        shoesSizeRepository.save(shoesSize);

        Address address = Address.builder()
                .member(findMember)
                .postcode("07355")
                .address("서울시 강서구")
                .detailAddress("화곡동 993-15")
                .extraAddress("501호")
                .build();

        addressRepository.save(address);

        Address address2 = Address.builder()
                .member(findMember)
                .postcode("07355")
                .address("서울시 강서구")
                .detailAddress("화곡동 993-15")
                .extraAddress("502호")
                .build();

        addressRepository.save(address2);

        Buy orderRequest = Buy.builder()
                .shoes(shoes)
                .shoesSize(shoesSize)
                .member(findMember)
                .price(125000L)
                .startDate(LocalDate.of(2024, 1, 25))
                .endDate(LocalDate.of(2024, 1, 30))
                .address(address)
                .status(Status.BIDDING)
                .build();

        orderRequestRepository.save(orderRequest);

        ShoesSize shoesSize2 = ShoesSize.builder()
                .shoes(shoes)
                .size(230)
                .build();

        shoesSizeRepository.save(shoesSize2);

        Buy orderRequest2 = Buy.builder()
                .shoes(shoes)
                .shoesSize(shoesSize2)
                .member(findMember)
                .price(150000L)
                .startDate(LocalDate.of(2024, 1, 26)) // 다른 시작 날짜
                .endDate(LocalDate.of(2024, 2, 5)) // 다른 종료 날짜
                .address(address2)
                .status(Status.BIDDING)
                .build();
        orderRequestRepository.save(orderRequest2);
    }
}
