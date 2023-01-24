package band.gosrock.api.host.controller;


import band.gosrock.api.host.model.dto.request.*;
import band.gosrock.api.host.model.dto.response.HostDetailResponse;
import band.gosrock.api.host.model.dto.response.HostResponse;
import band.gosrock.api.host.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "access-token")
@Tag(name = "호스트 관련 컨트롤러")
@RestController
@RequestMapping("/v1/hosts")
@RequiredArgsConstructor
public class HostController {

    private final ReadHostUseCase readHostUseCase;
    private final ReadHostListUseCase readHostListUseCase;
    private final CreateHostUseCase createHostUseCase;
    private final UpdateHostProfileUseCase updateHostProfileUseCase;
    private final UpdateHostSlackUrlUseCase updateHostSlackUrlUseCase;
    private final UpdateHostUserRoleUseCase updateHostUserRoleUseCase;
    private final InviteHostUseCase inviteHostUseCase;
    private final JoinHostUseCase joinHostUseCase;

    @Operation(summary = "내가 속한 호스트 리스트를 가져옵니다.")
    @GetMapping
    public List<HostResponse> getAllHosts() {
        return readHostListUseCase.execute();
    }

    @Operation(summary = "내가 속해있는, 고유 아이디에 해당하는 호스트 정보를 가져옵니다.")
    @GetMapping("/{hostId}")
    public HostDetailResponse getHostById(@PathVariable Long hostId) {
        return readHostUseCase.execute(hostId);
    }

    @Operation(summary = "호스트 간편 생성. 호스트를 생성한 유저 자신은 마스터 호스트가 됩니다.")
    @PostMapping
    public HostResponse createHost(@RequestBody @Valid CreateHostRequest createEventRequest) {
        return createHostUseCase.execute(createEventRequest);
    }

    @Operation(summary = "기존 호스트에 가입합니다.")
    @PostMapping("/{hostId}/join")
    public HostDetailResponse joinHost(@PathVariable Long hostId) {
        return joinHostUseCase.execute(hostId);
    }

    @Operation(summary = "다른 유저를 호스트 유저로 초대합니다.")
    @PostMapping("/{hostId}/invite")
    public HostDetailResponse inviteHost(
            @PathVariable Long hostId, @RequestBody @Valid InviteHostRequest inviteHostRequest) {
        return inviteHostUseCase.execute(hostId, inviteHostRequest);
    }

    @Operation(summary = "호스트 유저의 권한을 변경합니다.  슈퍼 호스트 이상만 가능합니다.")
    @PatchMapping("/{hostId}/role")
    public HostDetailResponse patchHostUserRole(
            @PathVariable Long hostId,
            @RequestBody @Valid UpdateHostUserRoleRequest updateHostUserRoleRequest) {
        return updateHostUserRoleUseCase.execute(hostId, updateHostUserRoleRequest);
    }

    // todo :: 슈퍼 호스트 이상으로?
    @Operation(summary = "호스트 정보를 변경합니다. 슈퍼 호스트 이상만 가능합니다.")
    @PatchMapping("/{hostId}/profile")
    public HostDetailResponse patchHostById(
            @PathVariable Long hostId, @RequestBody @Valid UpdateHostRequest updateHostRequest) {
        return updateHostProfileUseCase.execute(hostId, updateHostRequest);
    }

    @Operation(summary = "호스트 슬랙 알람 URL 을 변경합니다. 슈퍼 호스트 이상만 가능합니다.")
    @PatchMapping("/{hostId}/slack")
    public HostDetailResponse patchHostSlackUrlById(
            @PathVariable Long hostId,
            @RequestBody @Valid UpdateHostSlackRequest updateHostSlackRequest) {
        return updateHostSlackUrlUseCase.execute(hostId, updateHostSlackRequest);
    }
}
