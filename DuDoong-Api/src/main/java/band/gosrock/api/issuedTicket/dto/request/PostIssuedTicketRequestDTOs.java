package band.gosrock.api.issuedTicket.dto.request;


import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostIssuedTicketRequestDTOs {

    private List<PostIssuedTicketRequest> postIssuedTicketRequests;
}
