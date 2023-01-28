package band.gosrock.api.host.model.dto.response;


import band.gosrock.domain.domains.host.domain.Host;
import band.gosrock.domain.domains.host.domain.HostRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/** 내가 속한 호스트의 간략한 정보에 대한 응답 DTO */
@Getter
@Builder
public class HostProfileResponse {
    @Schema(description = "호스트 고유 아이디")
    private final Long hostId;

    @Schema(description = "호스트 이름")
    private final String name;

    @Schema(description = "호스트 한줄 소개")
    private final String introduce;

    @Schema(description = "호스트 프로필 이미지")
    private final String profileImageUrl;

    @Schema(description = "속한 호스트에서의 역할")
    private HostRole role;

    @Schema(description = "이 호스트의 마스터인지 여부")
    private final Boolean isMaster;

    public static HostProfileResponse of(Host host, Long userId) {
        return HostProfileResponse.builder()
                .hostId(host.getId())
                .name(host.getProfile().getName())
                .introduce(host.getProfile().getIntroduce())
                .profileImageUrl(host.getProfile().getProfileImageUrl())
                .role(host.getHostUserByUserId(userId).getRole())
                .isMaster(host.getMasterUserId().equals(userId))
                .build();
    }
}
