package band.gosrock.api.comment.service;


import band.gosrock.api.comment.mapper.CommentMapper;
import band.gosrock.api.comment.model.request.CreateCommentRequest;
import band.gosrock.api.comment.model.response.CreateCommentResponse;
import band.gosrock.api.common.UserUtils;
import band.gosrock.common.annotation.UseCase;
import band.gosrock.domain.domains.comment.adaptor.CommentAdaptor;
import band.gosrock.domain.domains.comment.domain.Comment;
import band.gosrock.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateCommentUseCase {

    private final UserUtils userUtils;

    private final CommentMapper commentMapper;

    private final CommentAdaptor commentAdaptor;

    @Transactional
    public CreateCommentResponse execute(Long eventId, CreateCommentRequest createDTO) {
        User currentUser = userUtils.getCurrentUser();
        Comment comment =
                commentAdaptor.save(
                        commentMapper.toEntity(currentUser.getId(), eventId, createDTO));
        return commentMapper.toCreateCommentResponse(comment, currentUser);
    }
}
