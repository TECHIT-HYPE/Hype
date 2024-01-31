package com.ll.hype.global.s3.image.imagebridge.component;


import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.entity.Image;
import com.ll.hype.global.s3.image.image.component.ImageComponent;
import com.ll.hype.global.s3.image.imagebridge.dto.ImageBridgeResponse;
import com.ll.hype.global.s3.image.imagebridge.entity.ImageBridge;
import com.ll.hype.global.s3.image.imagebridge.repository.ImageBridgeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageBridgeComponent {
    private final ImageComponent imageComponent;
    private final ImageBridgeRepository imageBridgeRepository;

    /**
     * 저장할 Type, Id, Files를 주면 버킷과 DB에 저장한 뒤 브릿지Response 객체를 반환한다.
     *
     * @param imageType
     * @param typeId
     * @param files
     * @return ImageBridgeResponse
     */
    public ImageBridgeResponse save(ImageType imageType, long typeId, List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();

        ImageBridge imageBridge = ImageBridge.builder()
                .type(imageType)
                .typeId(typeId)
                .build();

        imageBridgeRepository.save(imageBridge);

        for (MultipartFile file : files) {
            Image image = imageComponent.upload(file, imageType);
            image.updateImageBridge(imageBridge);
            images.add(image);
        }

        imageBridge.updateImages(images);

        return ImageBridgeResponse.of(imageBridge);
    }

    /**
     * Type과 id 값을 전달하면 해당하는 브릿지 엔티티를 찾고 첫번째 이미지의 경로를 List<String>으로 반환한다.
     * @param imageType
     * @param typeId
     * @return List<String> fullPaths
     */
    public List<String> findOneFullPath(ImageType imageType, long typeId) {
        Optional<ImageBridge> byTypeAndTypeId = imageBridgeRepository.findByTypeAndTypeId(imageType, typeId);

        if (byTypeAndTypeId.isEmpty()) {
            return new ArrayList<>();
        }

        ImageBridge imageBridge = byTypeAndTypeId.get();
        List<String> imageFullPaths = new ArrayList<>();
        String fullPath = imageComponent.convertPath(imageBridge.getImages().get(0));
        imageFullPaths.add(fullPath);

        return imageFullPaths;
    }

    /**
     * Type과 id 값을 전달하면 해당하는 브릿지 엔티티를 찾고 전체 이미지의 경로를 List<String>으로 반환한다.
     *
     * @param imageType
     * @param typeId
     * @return List<String> fullPaths
     */
    public List<String> findAllFullPath(ImageType imageType, long typeId) {
        Optional<ImageBridge> byTypeAndTypeId = imageBridgeRepository.findByTypeAndTypeId(imageType, typeId);

        if (byTypeAndTypeId.isEmpty()) {
            return new ArrayList<>();
        }

        ImageBridge imageBridge = byTypeAndTypeId.get();
        List<String> imageFullPaths = new ArrayList<>();

        for (Image image : imageBridge.getImages()) {
            String fullPath = imageComponent.convertPath(image);
            imageFullPaths.add(fullPath);
        }

        return imageFullPaths;
    }

    public void delete(ImageType imageType, Long typeId) {
        ImageBridge imageBridge = imageBridgeRepository.findByTypeAndTypeId(imageType, typeId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 데이터가 없습니다."));
        imageBridgeRepository.delete(imageBridge);
    }
}
